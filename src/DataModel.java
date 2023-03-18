import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

// this class stores and updates the data used by the application
public class DataModel {
    private HashMap<String, Object> currentWeather;
    DBConnection db = new DBConnection();
    private ArrayList<HashMap<String, Object>> weatherForecastData;
    private HashMap<String, Object> userData;
    private HashMap<String, Object> statistics;

    public DataModel(){
        try {
            statistics = db.getStatistics();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    // the setData method is overloaded, with no parameter it calls the API with a default location
    public void setData() {
        ApiCall caller = new ApiCall("nairobi");
        currentWeather = caller.getCurrentWeather();
        weatherForecastData = caller.getWeatherForecastData();

    }

    // it can also be called with a location parameter.
    public void setData(String location) {
        ApiCall caller = new ApiCall(location);
        currentWeather = caller.getCurrentWeather();
        weatherForecastData = caller.getWeatherForecastData();

        db.weatherSearch(userData, currentWeather);

        // each time a user makes a search the statistics hashmap will be updated
        try {
            statistics = db.getStatistics();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

    }

    public HashMap<String, Object> getCurrentWeather() {
        return currentWeather;
    }

    public ArrayList<HashMap<String, Object>> getWeatherForecastData() {
        return weatherForecastData;
    }

    public HashMap<String, Object> getUserData() {
        return userData;
    }

    public void setUserData(HashMap<String, Object> userData) {
        this.userData = userData;
    }

    public HashMap<String, Object> getStatistics() {
        return statistics;
    }
}
