import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.google.gson.*;
import com.google.gson.internal.LinkedTreeMap;
import java.lang.reflect.Type;
import java.util.Map;
import com.google.gson.reflect.TypeToken;
import org.json.simple.parser.ParseException;

public class ApiCall {
    private HashMap<String, String> locationData = new HashMap<>();
    public ArrayList<HashMap<String, Object>> weatherForecastData = new ArrayList<>();
    private HashMap<String, Object> currentWeather = new HashMap<>();;
    public HashMap<String, Object> unparsedCurrentWeather;
    public HashMap<String, Object> unparsedWeatherForecastData;
    private String location;

    public ApiCall(String location) {
        this.location = location;
        currentWeatherCall(this.location);
        weatherForecastCall(this.location);
        weatherParser(unparsedCurrentWeather , currentWeather);
        weatherForecastParser(unparsedWeatherForecastData);
    }

    private void currentWeatherCall(String location){
        // a Gson object is initialized to convert JSON objects to HashMap objects
        Gson gson = new Gson();

        // the TypeToken class is part of the Gson library and is used to help convert JSON objects to
        // a specified format in this case HashMap<String, Object>
        Type type = new TypeToken<HashMap<String, Object>>() {
        }.getType();
        try{

            // a URL object is created based on the API endpoint
            URL url = new URL(String.format("http://api.openweathermap.org/data/2.5/weather?q=%s&APPID=62d50fd38f48aba39355b8ae5a3ae053", location));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // we then connect to the API endpoint , the response code is collected
            conn.connect();
            int resCode = conn.getResponseCode();

            // if the connection fails an error is thrown, else the data provided is parsed into a JSON object
            if (resCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + resCode);
            } else {

                // Using the JSON simple library parse the string into a json object
                JSONParser parse = new JSONParser();
                JSONObject data_obj = (JSONObject) parse.parse(new InputStreamReader(conn.getInputStream()));
                System.out.println(data_obj);
                // the JSON object is converted to a Hashmap object
                unparsedCurrentWeather = gson.fromJson(data_obj.toString(), type);

                // the location coordinates are nested within currentWeather, so they'll have to be isolated
                // the following block is essentially a .get().get()
                LinkedTreeMap<String,Object> sys = (LinkedTreeMap<String,Object>) unparsedCurrentWeather.get("sys");
                LinkedTreeMap<String,Object> coord = (LinkedTreeMap<String,Object>) unparsedCurrentWeather.get("coord");
                String country = (String) sys.get("country");
                String lon = String.valueOf(coord.get("lon"));
                String lat = String.valueOf(coord.get("lat"));
                String locationName = (String) unparsedCurrentWeather.get("name");

                // the retrieved values are added to locationData, and the coordinates will be used to fetch the weather forecast
                locationData.put("country" , country);
                locationData.put("lon" , lon);
                locationData.put("lat" , lat);
                locationData.put("locationName" , locationName);

            }
        } catch (IOException | ParseException error) {
            System.out.println(error);
        }
    }

    private void weatherForecastCall(String location) {

        // a Gson object is initialized to convert JSON objects to HashMap objects
        Gson gson = new Gson();

        //the TypeToken class is part of the Gson library and is used to help convert JSON objects to
        // a specified format in this case HashMap<String, Object>
        Type type = new TypeToken<HashMap<String, Object>>() {
        }.getType();

        try {
            // a URL object is created based on the API endpoint
            // the URL object will use coordinates retrieved by the currentWeatherCall method
            URL url = new URL(String.format("https://api.openweathermap.org/data/2.5/onecall?lat=%s&lon=%s&exclude=minutely,current,hourly,alerts&units=metric&appid=62d50fd38f48aba39355b8ae5a3ae053"
                    , locationData.get("lat"), locationData.get("lon")));

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // we then connect to the API endpoint , the response code is collected
            conn.connect();
            int resCode = conn.getResponseCode();

            // if the connection fails an error is thrown, else the data provided is parsed into a JSON object
            if (resCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + resCode);
            } else {

                // Using the JSON simple library parse the string into a JSON object
                JSONParser parse = new JSONParser();
                JSONObject data_obj = (JSONObject) parse.parse(new InputStreamReader(conn.getInputStream()));

                // the JSONObject is converted to a Hashmap object by the Gson object
                unparsedWeatherForecastData = gson.fromJson(data_obj.toString(), type);

            }
        } catch (IOException | ParseException error) {
            System.out.println(error);
        }
    }

    // The nested values within the unparsed Map Objects make it tiresome to work with
    // weatherParser unpacks the Hashmap so that all keys are at the same level
    // depending on which dataset is being used, the data will be pushed to that map(mapToPushTo)
    private void weatherParser(Map<String, Object> currentWeatherMap,Map<String, Object> mapToPushTo){

        // the HashMap is iterated over with a for loop
        for(HashMap.Entry<String,Object> key: currentWeatherMap.entrySet()){

            // each key value has its type checked, as each type will be operated on differently
            String[] parts = (key.getValue()).getClass().toString().split("\\.");
            String objectType = parts[parts.length - 1];


            if (objectType.equals("String")) {
                mapToPushTo.put(key.getKey(),key.getValue());
            } else if (objectType.equals("Double")) {

                // there's 2 different ids within currentWeather, the location and weather ids
                // the weather array is iterated over first, so its id is captured if no ids are present in the Hashmap
                if (mapToPushTo.get("id") == null) {
                    if((key.getKey()).equals("id")) {
                        mapToPushTo.put("weather_id" , String.valueOf(key.getValue()));
                    }
                }
                mapToPushTo.put(key.getKey() , String.valueOf(key.getValue()));
            } else if (objectType.equals("LinkedTreeMap")) {

                // if there's a nested Map , we call weatherParser recursively
                weatherParser((LinkedTreeMap<String, Object>) key.getValue(), mapToPushTo);
            } else if (objectType.equals("ArrayList")) {
                // this block captures the weather array which only has one value, a nested LinkedTreeMap
                ArrayList<Object> weatherArray = (ArrayList<Object>) key.getValue();
                weatherParser((LinkedTreeMap<String, Object>) weatherArray.get(0), mapToPushTo);
            }
        }
    }

    // weatherForecastParser parses a different dataset to weatherParser
    // the data is pushed to an ArrayList unlike a HashMap with weatherParser
    private void weatherForecastParser(Map<String, Object> weatherForecastMap){

        // the 7 day weatherForecast array within unparsedWeatherForecastData is retrieved
        ArrayList<Map<String, Object>> dailyWeather = (ArrayList<Map<String, Object>>) weatherForecastMap.get("daily");
        int counter = 0;

        for(Map<String, Object> weatherAttribute: dailyWeather){
            // this Hashmap collects all the data each iteration, which is pushed to the weatherForecast ArrayList
            // at the end of each iteration and emptied at the start of the next iteration
            HashMap <String,Object> currentIteration = new HashMap<>();

            // for the first day, the 'pop'(probability of precipitation) i.e. chance of rain is retrieved and pushed
            // to the currentWeather Hashmap as it wouldn't be retrievable otherwise
            if(counter == 0){
                currentWeather.put("pop", weatherAttribute.get("pop"));
            }

            // the nested for loop loops through each Hashmap within the daily ArrayList
            for(Map.Entry<String, Object> entry: weatherAttribute.entrySet()){
                String[] parts = entry.getValue().getClass().toString().split("\\.");
                String objectType = parts[parts.length - 1];

                // depending on the Hashmap's type, the data within it is handled differently
                if(objectType.equals("Double")) {
                    currentIteration.put(entry.getKey() , entry.getValue());
                } else if (objectType.equals("ArrayList")) {

                    // the nested ArrayLists only have one value, a LinkedTreeMap
                    // the weatherParser method will be called on that data
                    ArrayList<Object> weatherArray = (ArrayList<Object>) entry.getValue();
                    weatherParser((LinkedTreeMap<String, Object>) weatherArray.get(0),currentIteration);
                } else if (objectType.equals("LinkedTreeMap")) {
                    weatherParser((LinkedTreeMap<String, Object>) entry.getValue(),currentIteration);
                }




            }
            // the hashmap initialized at the beginning of this loop is pushed to the weatherForecastData Hashmap
            weatherForecastData.add(currentIteration);

            counter++;
        }
    }


    public HashMap<String, String> getLocationData() {
        return locationData;
    }

    public HashMap<String, Object> getCurrentWeather() {
        return currentWeather;
    }

    public ArrayList<HashMap<String, Object>> getWeatherForecastData() {
        return weatherForecastData;
    }


}
