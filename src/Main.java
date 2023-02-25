import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import com.google.gson.internal.LinkedTreeMap;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What location?");
        String location = scanner.nextLine();

        ApiCall caller = new ApiCall(location);

        Map<String, String> locationData = caller.getLocationData();
        //LinkedTreeMap <String, Object> currentWeather = caller.getCurrentWeather();
        HashMap<String, Object> weatherData = caller.getWeatherData();

        caller.currentWeatherCall("nairobi");
        /*System.out.println(location);
        System.out.println(currentWeather);
        System.out.println(weatherData);*/

        /*DBConnection db = new DBConnection();
        db.update(currentWeather , locationData);*/

    }
}