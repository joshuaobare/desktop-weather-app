import java.util.ArrayList;
import java.util.HashMap;

public class Model {
        private HashMap<String, Object> currentWeather;
        private ArrayList<HashMap<String, Object>> weatherForecastData;

        public void setData(){
            ApiCall caller = new ApiCall("nairobi");
            currentWeather = caller.getCurrentWeather();
            weatherForecastData = caller.getWeatherForecastData();
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
