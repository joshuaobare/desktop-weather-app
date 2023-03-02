import java.util.ArrayList;
import java.util.HashMap;

public class DataModel {
        private HashMap<String, Object> currentWeather;
        private ArrayList<HashMap<String, Object>> weatherForecastData;

        public String setData(){
            ApiCall caller = new ApiCall("nairobi");
            currentWeather = caller.getCurrentWeather();
            weatherForecastData = caller.getWeatherForecastData();
            return "";
        }

        public void setData(String location){
            ApiCall caller = new ApiCall(location);
            currentWeather = caller.getCurrentWeather();
            weatherForecastData = caller.getWeatherForecastData();
        }

        public HashMap<String, Object> getCurrentWeather(){
            return currentWeather;
        }

        public ArrayList<HashMap<String, Object>> getWeatherForecastData() {
            return weatherForecastData;
        }

}
