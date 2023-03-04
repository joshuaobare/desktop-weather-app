import afester.javafx.svg.SvgLoader;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UIController extends Application {
    DataModel model = new DataModel();
    String data = model.setData();

    public static void main(String[] args) {
        launch(args);
    }

    public class LoginScene extends Scene {

        public LoginScene(Parent parent, Stage primaryStage, Scene otherScene) {
            super(parent);
            //LOGIN
            GridPane grid2 = new GridPane();
            grid2.setAlignment(Pos.CENTER);
            grid2.setHgap(10);
            grid2.setVgap(10);
            grid2.setPadding(new Insets(25, 25, 25, 25));
            Text scenetitle = new Text("Welcome Back");
            Text welcometitle = new Text("Please enter your details");
            scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            welcometitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
            grid2.add(scenetitle, 0, 0, 2, 1);
            grid2.add(welcometitle, 0, 1, 2, 1);

            Label userName = new Label("User Name:");
            grid2.add(userName, 0, 2);

            TextField userTextField = new TextField();
            grid2.add(userTextField, 1, 2);

            Label pw = new Label("Password:");
            grid2.add(pw, 0, 3);

            PasswordField pwBox = new PasswordField();
            grid2.add(pwBox, 1, 3);


            Button btn = new Button("Sign in");
            HBox hbBtn = new HBox(10);
            hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
            hbBtn.getChildren().add(btn);
            grid2.add(hbBtn, 1, 4);
            final Text actiontarget = new Text();
            grid2.add(actiontarget, 1, 6);

            new Scene(grid2, 300, 275);

            /*Scene loginScene = new Scene(grid2, 300, 275);
            primaryStage.setScene(loginScene);
            primaryStage.hide();
            primaryStage.setMaximized(true);
            primaryStage.show();

            btn.setOnAction((event) -> {
                primaryStage.se
            });*/
        }
    }

    public class MainUIScene extends Scene {

        public MainUIScene(Parent parent, Stage primaryStage) {
            super(parent);
            // MAINUI
            Helper helper = new Helper();

            HashMap<String, Object> currentWeather = model.getCurrentWeather();
            ArrayList<HashMap<String, Object>> weatherForecastData = model.getWeatherForecastData();

            primaryStage.setTitle("Weather App");

            GridPane grid = new GridPane();
            grid.setPadding(new Insets(25, 25, 25, 25));
            Text weatherTitle = new Text(helper.capitalizeDescription((String) currentWeather.get("description")));
            Text location = new Text((String) currentWeather.get("name") + " " + (String) currentWeather.get("country") );
            helper.dateConverter((String) currentWeather.get("timezone"));
            Text date = new Text(helper.getDate());
            Text time = new Text(helper.getTime());
            Text temperature = new Text( helper.tempConverter((String) currentWeather.get("temp")) + "\u00B0C");
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

            InputStream svgFile =
                    getClass().getResourceAsStream(helper.weatherIconFetcher((String) currentWeather.get("icon")));

            SvgLoader svgLoader = new SvgLoader();
            Group svgImage = svgLoader.loadSvg(svgFile);
            svgImage.setScaleX(4);
            svgImage.setScaleY(4);
            Group graphic = new Group(svgImage);
            grid.add(graphic, 0, 6);
            TextField locationSearchField = new TextField();
            locationSearchField.setPromptText("Search location");
            grid.add(locationSearchField, 0, 7);
            Button submitButton = new Button("Submit");
            grid.add(submitButton, 1,7);

            GridPane rightPane = new GridPane();

            Text feelsLike = new Text("Feels Like");
            feelsLike.setFont(Font.font("Tahoma", FontWeight.NORMAL, 10));
            Text feelsLikeValue = new Text(helper.tempConverter((String) currentWeather.get("feels_like")) + "\u00B0C");
            feelsLikeValue.setFont(Font.font("Tahoma", FontWeight.NORMAL, 32));
            Text humidity = new Text("Humidity");
            humidity.setFont(Font.font("Tahoma", FontWeight.NORMAL, 10));
            Text humidityValue = new Text((String) currentWeather.get("humidity") + "%");
            humidityValue.setFont(Font.font("Tahoma", FontWeight.NORMAL, 32));
            Text chanceOfRain = new Text("Chance of Rain");
            chanceOfRain.setFont(Font.font("Tahoma", FontWeight.NORMAL, 10));
            Text chanceOfRainValue = new Text(String.valueOf(Math.round((Double) currentWeather.get("pop") * 100)) + "%");
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


            int counter = 0;
            GridPane bottomPane = new GridPane();
            bottomPane.setPadding(new Insets(50,0,0,0));

            // this for loop loops over the forecastData and created a miniDisplay for each day and then appends it to the grid
            for(Map<String, Object> key: weatherForecastData){
                Helper forecastHelper = new Helper();
                Double datetime = Double.valueOf((String) currentWeather.get("dt")) + (counter * (86400.0));
                forecastHelper.dateConverter((String) currentWeather.get("timezone"), String.valueOf(datetime));
                MiniDisplay day = new MiniDisplay(forecastHelper.getDay(), String.valueOf(key.get("max")), String.valueOf(key.get("min")), helper.capitalizeDescription((String) key.get("description")),
                        String.valueOf(key.get("icon")));
                day.setUserData(counter);
                bottomPane.add(day,counter,0);
                counter++;
            }

            grid.add(bottomPane, 1,9);


            grid.setBackground(new Background(new BackgroundFill(Color.DARKGRAY,  new CornerRadii(0), new Insets(0))));
            Scene mainUIScene = new Scene(grid, 300, 275);

            //primaryStage.setScene(scene);

            /*primaryStage.hide();
            primaryStage.setMaximized(true);
            primaryStage.show();*/

            submitButton.setOnAction((event) -> {
                try {
                    String searchedLocation = locationSearchField.getText();
                    model.setData(searchedLocation);
                    //primaryStage.setScene(mainUIScene);
                    //start(primaryStage);
                    primaryStage.hide();
                    primaryStage.show();


                } catch (Exception e) {
                    e.printStackTrace();
                }

            });

        }
    }
    @Override
    public void start(Stage primaryStage) throws IOException {

    MainUI mainUI = new MainUI();
    mainUI.start(primaryStage);



    }
}
