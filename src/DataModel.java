import java.util.ArrayList;
import java.util.HashMap;

// this class stores and updates the data used by the application
public class DataModel {
        private HashMap<String, Object> currentWeather;
        private ArrayList<HashMap<String, Object>> weatherForecastData;

        private HashMap<String,Object> userData;

        // the setData method is overloaded, with no parameter it calls the API with a default location
        public String setData(){
            ApiCall caller = new ApiCall("nairobi");
            currentWeather = caller.getCurrentWeather();
            weatherForecastData = caller.getWeatherForecastData();
            return "";
        }

        // it can also be called with a location parameter.
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

        public HashMap<String,Object> getUserData(){
            return userData;
        }

        public void setUserData(HashMap<String,Object> userData) {
            this.userData = userData;
        }

}
