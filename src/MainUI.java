import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Weather App");
        GridPane grid = new GridPane();
       // grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Text weatherTitle = new Text("Overcast Clouds");
        Text location = new Text("Nairobi");
        Text date = new Text("Friday 24th Feb 2023");
        Text time = new Text("6:45 pm");
        Text temperature = new Text("20C");
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

        Text feelsLike = new Text("Feels Like");
        feelsLike.setFont(Font.font("Tahoma", FontWeight.NORMAL, 10));
        Text feelsLikeValue = new Text("20C");
        feelsLikeValue.setFont(Font.font("Tahoma", FontWeight.NORMAL, 32));
        Text humidity = new Text("Humidity");
        humidity.setFont(Font.font("Tahoma", FontWeight.NORMAL, 10));
        Text humidityValue = new Text("69%");
        humidityValue.setFont(Font.font("Tahoma", FontWeight.NORMAL, 32));
        Text chanceOfRain = new Text("Chance of Rain");
        chanceOfRain.setFont(Font.font("Tahoma", FontWeight.NORMAL, 10));
        Text chanceOfRainValue = new Text("42%");
        chanceOfRainValue.setFont(Font.font("Tahoma", FontWeight.NORMAL, 32));
        Text windSpeed = new Text("Wind Speed");
        windSpeed.setFont(Font.font("Tahoma", FontWeight.NORMAL, 10));
        Text windSpeedValue = new Text("3.5M/S");
        windSpeedValue.setFont(Font.font("Tahoma", FontWeight.NORMAL, 32));

        grid.add(feelsLike , 7 ,0);
        grid.add(feelsLikeValue , 7 ,1);
        grid.add(humidity , 7 ,2);
        grid.add(humidityValue , 7 ,3);
        grid.add(chanceOfRain , 7 ,4);
        grid.add(chanceOfRainValue , 7 ,5);
        grid.add(windSpeed , 7 ,6);
        grid.add(windSpeedValue , 7 ,7);






        Scene scene = new Scene(grid, 300, 275);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();

    }
}
