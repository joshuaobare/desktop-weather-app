import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;

// this class stores and updates the data used by the application
public class DataModel {
    private HashMap<String, Object> currentWeather;
    DBConnection db = new DBConnection();
    private ArrayList<HashMap<String, Object>> weatherForecastData;
    private HashMap<String, Object> userData;
    private HashMap<String, Object> statistics;
    private ArrayList<HashMap<String, Object>> registeredUsers = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> unregisteredUsers = new ArrayList<>();
    private HashMap<String,Integer> globalMostSearchedLocations = new HashMap<>();
    private HashMap<String,Object> userMostSearchedLocations = new HashMap<>();
    private TreeMap<Date, Integer> apiCallCount;

    public DataModel(){
        setData();
        try {
            statistics = db.getStatistics();
            db.retrieveRegisteredUsers();
            registeredUsers = db.getRegisteredUsers();
            unregisteredUsers = db.getUnregisteredUsers();
            apiCallCount = db.getApiCallCount();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } catch (ParseException e) {
            throw new RuntimeException(e);
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

    // deleteUser deletes the file from the DB and updates the model so it reflects on the Dashboard
    public void deleteUser(String userID) throws SQLException {
        db.deleteUser(userID);

        int count = 0;
        int index = Integer.MAX_VALUE;

        for(HashMap<String, Object>map : registeredUsers){

            if((map.get("userID")).equals(userID)){
                index = count;
            }
            count++;
        }

        registeredUsers.remove(index);
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

    public ArrayList<HashMap<String, Object>> getRegisteredUsers() {
        return registeredUsers;
    }

    public ArrayList<HashMap<String, Object>> getUnregisteredUsers() {
        return unregisteredUsers;
    }

    public HashMap<String, Integer> getGlobalMostSearchedLocations() {
        return globalMostSearchedLocations;
    }

    public void setGlobalMostSearchedLocations(HashMap<String, Integer> globalMostSearchedLocations) {
        this.globalMostSearchedLocations = globalMostSearchedLocations;
    }

    public HashMap<String, Object> getUserMostSearchedLocations() {
        return userMostSearchedLocations;
    }

    public void setUserMostSearchedLocations(HashMap<String, Object> userMostSearchedLocations) {
        this.userMostSearchedLocations = userMostSearchedLocations;
    }

    public TreeMap<Date, Integer> getApiCallCount() {
        return apiCallCount;
    }
}
