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
    private HashMap<String, Object> weatherForecastData;
    private HashMap<String, Object> currentWeather = new HashMap<>();;
    public HashMap<String, Object> unparsedCurrentWeather;
    private String location;

    public ApiCall(String location) {
        this.location = location;
        currentWeatherCall(this.location);
        weatherForecastCall(this.location);
        currentWeatherParser(unparsedCurrentWeather);
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
                weatherForecastData = gson.fromJson(data_obj.toString(), type);

            }
        } catch (IOException | ParseException error) {
            System.out.println(error);
        }
    }

    // The nested values within unparsedCurrentWeather make it tiresome to work with
    // currentWeatherParser unpacks the Hashmap so that all keys are at the same level
    public void currentWeatherParser(Map<String, Object> currentWeatherMap){

        // the HashMap is iterated over with a for loop
        for(HashMap.Entry<String,Object> key: currentWeatherMap.entrySet()){

            // each key value has its type checked, as each type will be operated on differently
            String[] parts = (key.getValue()).getClass().toString().split("\\.");
            String objectType = parts[parts.length - 1];


            if (objectType.equals("String")) {
                currentWeather.put(key.getKey(),key.getValue());
            } else if (objectType.equals("Double")) {

                // there's 2 different ids, the location and weather ids
                // the weather array is iterated over first, so its id is captured if no ids are present in the Hashmap
                if (currentWeather.get("id") == null) {
                    if((key.getKey()).equals("id")) {
                        currentWeather.put("weather_id" , String.valueOf(key.getValue()));
                    }
                }
                currentWeather.put(key.getKey() , String.valueOf(key.getValue()));
            } else if (objectType.equals("LinkedTreeMap")) {

                // if there's a nested Map , we call currentWeatherParser recursively
                currentWeatherParser((LinkedTreeMap<String, Object>) key.getValue());
            } else if (objectType.equals("ArrayList")) {
                // this block captures the weather array which only has one value, a nested LinkedTreeMap
                ArrayList<Object> weatherArray = (ArrayList<Object>) key.getValue();
                currentWeatherParser((LinkedTreeMap<String, Object>) weatherArray.get(0));
            }
        }
    }

    public HashMap<String, String> getLocationData() {
        return locationData;
    }

    public HashMap<String, Object> getCurrentWeather() {
        return currentWeather;
    }

    public HashMap<String, Object> getWeatherForecastData() {
        return weatherForecastData;
    }


}
