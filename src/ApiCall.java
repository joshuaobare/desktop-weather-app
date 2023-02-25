import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.google.gson.*;
import com.google.gson.internal.LinkedTreeMap;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import org.json.simple.parser.ParseException;

public class ApiCall {
    private HashMap<String, String> locationData = new HashMap<>();
    private HashMap<String, Object> weatherForecastData;
    private HashMap<String, Object> currentWeather;
    private String location;

    public ApiCall(String location) {
        this.location = location;
        currentWeatherCall(this.location);
        weatherForecastCall(this.location);
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

                // the JSON object is converted to a Hashmap object
                currentWeather = gson.fromJson(data_obj.toString(), type);

                // the location coordinates are nested within currentWeather, so they'll have to be isolated
                // the following block is essentially a .get().get()
                LinkedTreeMap<String,Object> sys = (LinkedTreeMap<String,Object>) currentWeather.get("sys");
                LinkedTreeMap<String,Object> coord = (LinkedTreeMap<String,Object>) currentWeather.get("coord");
                String country = (String) sys.get("country");
                String lon = String.valueOf(coord.get("lon"));
                String lat = String.valueOf(coord.get("lat"));
                String locationName = (String) currentWeather.get("name");

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
            URL url = new URL(String.format("https://api.open-meteo.com/v1/forecast?latitude=%s&longitude=%s&daily=weathercode,temperature_2m_max,temperature_2m_min,apparent_temperature_max,apparent_temperature_min,sunrise,sunset,uv_index_max,uv_index_clear_sky_max,precipitation_sum,rain_sum,precipitation_hours,windspeed_10m_max,winddirection_10m_dominant&current_weather=true&timezone=auto"
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
