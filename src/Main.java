import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import com.google.gson.internal.LinkedTreeMap;

public class Main {
    public static void main(String[] args) throws SQLException {
        /*Scanner scanner = new Scanner(System.in);
        System.out.println("What location?");
        String location = scanner.nextLine();

        ApiCall caller = new ApiCall(location);

        Map<String, String> locationData = caller.getLocationData();
        HashMap <String, Object> currentWeather = caller.getCurrentWeather();
        ArrayList<HashMap<String, Object>> weatherForecastData = caller.getWeatherForecastData(); */

        //caller.currentWeatherCall("nairobi");
        //System.out.println(location);
        /*System.out.println(caller.unparsedCurrentWeather);
        System.out.println(currentWeather);*/
        //System.out.println(weatherForecastData);
        //System.out.println(caller.unparsedWeatherForecastData);

       // System.out.println(locationData);

        DBConnection db = new DBConnection();
        Boolean checker = db.userAuthentication("jobare.obare@gmail.com","test" );
        System.out.println(checker);
        db.retrieveRegisteredUsers();
    }
}