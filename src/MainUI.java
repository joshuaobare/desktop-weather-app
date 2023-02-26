import com.google.gson.internal.LinkedTreeMap;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MainUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What location?");
        String locationChoice = scanner.nextLine();
        ApiCall caller = new ApiCall(locationChoice);
        HashMap<String, Object> currentWeather = caller.getCurrentWeather();
        Map<String, String> locationData = caller.getLocationData();
        String locationName = String.valueOf(locationData.get("name"));
        String capitalizedLocation = locationName.substring(0, 1).toUpperCase() + locationName.substring(1);

        primaryStage.setTitle("Weather App");
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(25, 25, 25, 25));
        Text weatherTitle = new Text((String) currentWeather.get("description"));
        Text location = new Text((String) currentWeather.get("name") + " " + (String) currentWeather.get("country") );
        Text date = new Text("Friday 24th Feb 2023");
        Text time = new Text("6:45 pm");
        Text temperature = new Text((String) currentWeather.get("temp") + "\u00B0C");
        weatherTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 32));
        location.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
        date.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
        time.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
        temperature.setFont(Font.font("Tahoma", FontWeight.NORMAL, 50));
        grid.add(weatherTitle , 0 ,0);
        grid.add(location , 0 ,1);
        grid.add(date , 0 ,2);
        grid.add(time , 0 ,3);
        grid.add(temperature , 0 ,4);

        TextField locationSearchField = new TextField();
        locationSearchField.setPromptText("Search location");
        grid.add(locationSearchField, 0, 5);

        GridPane rightPane = new GridPane();

        Text feelsLike = new Text("Feels Like");
        feelsLike.setFont(Font.font("Tahoma", FontWeight.NORMAL, 10));
        Text feelsLikeValue = new Text((String) currentWeather.get("feels_like") + "\u00B0C");
        feelsLikeValue.setFont(Font.font("Tahoma", FontWeight.NORMAL, 32));
        Text humidity = new Text("Humidity");
        humidity.setFont(Font.font("Tahoma", FontWeight.NORMAL, 10));
        Text humidityValue = new Text((String) currentWeather.get("humidity") + "%");
        humidityValue.setFont(Font.font("Tahoma", FontWeight.NORMAL, 32));
        Text chanceOfRain = new Text("Chance of Rain");
        chanceOfRain.setFont(Font.font("Tahoma", FontWeight.NORMAL, 10));
        Text chanceOfRainValue = new Text("42%");
        chanceOfRainValue.setFont(Font.font("Tahoma", FontWeight.NORMAL, 32));
        Text windSpeed = new Text("Wind Speed");
        windSpeed.setFont(Font.font("Tahoma", FontWeight.NORMAL, 10));
        Text windSpeedValue = new Text((String) currentWeather.get("speed") + "m/s");
        windSpeedValue.setFont(Font.font("Tahoma", FontWeight.NORMAL, 32));

        rightPane.add(feelsLike , 0 ,0);
        rightPane.add(feelsLikeValue , 0 ,1);
        rightPane.add(humidity , 0 ,2);
        rightPane.add(humidityValue , 0 ,3);
        rightPane.add(chanceOfRain , 0 ,4);
        rightPane.add(chanceOfRainValue , 0 ,5);
        rightPane.add(windSpeed , 0 ,6);
        rightPane.add(windSpeedValue , 0 ,7);
        grid.add(rightPane , 7 ,0,1 ,7);

        MiniDisplay dayOne = new MiniDisplay("Sunday", "30C", "10C", "cloudy");
        MiniDisplay dayTwo = new MiniDisplay("Sunday", "30C", "10C", "cloudy");
        MiniDisplay dayThree = new MiniDisplay("Sunday", "30C", "10C", "cloudy");
        MiniDisplay dayFour = new MiniDisplay("Sunday", "30C", "10C", "cloudy");
        MiniDisplay dayFive = new MiniDisplay("Sunday", "30C", "10C", "cloudy");
        MiniDisplay daySix = new MiniDisplay("Sunday", "30C", "10C", "cloudy");
        MiniDisplay daySeven = new MiniDisplay("Sunday", "30C", "10C", "cloudy");

        grid.add(dayOne , 0 ,8);
        grid.add(dayTwo , 1 ,8);
        grid.add(dayThree , 2 ,8);
        grid.add(dayFour , 3 ,8);
        grid.add(dayFive , 4 ,8);
        grid.add(daySix , 5 ,8);
        grid.add(daySeven , 6 ,8);






        Scene scene = new Scene(grid, 300, 275);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();

    }
}
