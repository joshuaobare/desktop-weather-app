import afester.javafx.svg.SvgLoader;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainUI extends Application {

    // The model object is initalized globally to handle data changes when the user searches
    private DataModel model;


    public static void main(String[] args) {
        launch(args);
    }

    public MainUI(DataModel model) {
        this.model = model;

    }

    public DataModel getModel() {
        return model;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        // the Helper object is initialized as well as currentWeather and weatherForecastData
        Helper helper = new Helper();
        HashMap<String, Object> currentWeather = model.getCurrentWeather();
        ArrayList<HashMap<String, Object>> weatherForecastData = model.getWeatherForecastData();
        HashMap<String, Object> userData = model.getUserData();

        // The window's title is set, as well as the GridPane object that holds all the data
        primaryStage.setTitle("Weather App");
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(25, 25, 25, 25));

        // TOP SECTION - This section displays the currentWeather data and is divided into the right and left sections
        // LEFT SECTION

        // different Text nodes are created and populated with data from the currentWeather HashMap
        Text weatherTitle = new Text(helper.capitalizeDescription((String) currentWeather.get("description")));
        weatherTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 32));
        weatherTitle.setFill(Color.WHITE);
        Text location = new Text((String) currentWeather.get("name") + " " + (String) currentWeather.get("country") );
        location.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
        location.setFill(Color.WHITE);

        /* this helper method is run to retrieve the location's local time, which is retrieved and then added
           to some Text nodes */
        helper.dateConverter((String) currentWeather.get("timezone"));
        Text date = new Text(helper.getDate());
        date.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
        date.setFill(Color.WHITE);
        Text time = new Text(helper.getTime());
        time.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
        time.setFill(Color.WHITE);

        // the temperature provided by the API is in Kelvin, so it has to be converted to Celsius
        Text temperature = new Text( helper.tempConverter((String) currentWeather.get("temp")) + "\u00B0C");
        temperature.setFont(Font.font("Tahoma", FontWeight.NORMAL, 50));
        temperature.setFill(Color.WHITE);

        // The nodes are added to the main GridPane object
        grid.add(weatherTitle , 0 ,0);
        grid.add(location , 0 ,1);
        grid.add(date , 0 ,2);
        grid.add(time , 0 ,3);
        grid.add(temperature , 0 ,4);

        /* the description image is an SVG file, and is handled differently compared to if it was jpg or png
           Stream is used to retrieve the file's local URL, and the helper object will retrieve that specific
           icon based on the current weather description */

        InputStream svgFile =
                getClass()
                .getResourceAsStream(helper.weatherIconFetcher((String) currentWeather.get("icon")));

        SvgLoader svgLoader = new SvgLoader();
        Group svgImage = svgLoader.loadSvg(svgFile);
        svgImage.setScaleX(4);
        svgImage.setScaleY(4);
        Group graphic = new Group(svgImage);
        grid.add(graphic, 0, 6);

        // the remaining relevant Text objects are created and added to the GridPane object
        HBox search = new HBox();
        search.setPadding(new Insets(10,0,0,0));
        TextField locationSearchField = new TextField();
        locationSearchField.setPromptText("Search location");
        Button submitButton = new Button("Submit");
        Text searchErrorMessage = new Text();
        search.getChildren().add(locationSearchField);
        search.getChildren().add(submitButton);
        search.getChildren().add(searchErrorMessage);
        grid.add(search, 0,7);

        // RIGHT SECTION

        // a GridPane object for the right section is created and populated
        GridPane rightPane = new GridPane();
        Text feelsLike = new Text("Feels Like");
        feelsLike.setFont(Font.font("Tahoma", FontWeight.NORMAL, 10));
        feelsLike.setFill(Color.WHITE);

        Text feelsLikeValue = new Text(helper.tempConverter((String) currentWeather.get("feels_like")) + "\u00B0C");
        feelsLikeValue.setFont(Font.font("Tahoma", FontWeight.NORMAL, 32));
        feelsLikeValue.setFill(Color.WHITE);
        Text humidity = new Text("Humidity");
        humidity.setFont(Font.font("Tahoma", FontWeight.NORMAL, 10));
        humidity.setFill(Color.WHITE);
        Text humidityValue = new Text((String) currentWeather.get("humidity") + "%");
        humidityValue.setFont(Font.font("Tahoma", FontWeight.NORMAL, 32));
        humidityValue.setFill(Color.WHITE);
        Text chanceOfRain = new Text("Chance of Rain");
        chanceOfRain.setFont(Font.font("Tahoma", FontWeight.NORMAL, 10));
        chanceOfRain.setFill(Color.WHITE);
        Text chanceOfRainValue = new Text(String.valueOf(Math.round((Double) currentWeather.get("pop") * 100)) + "%");
        chanceOfRainValue.setFont(Font.font("Tahoma", FontWeight.NORMAL, 32));
        chanceOfRainValue.setFill(Color.WHITE);
        Text windSpeed = new Text("Wind Speed");
        windSpeed.setFont(Font.font("Tahoma", FontWeight.NORMAL, 10));
        windSpeed.setFill(Color.WHITE);
        Text windSpeedValue = new Text((String) currentWeather.get("speed") + "m/s");
        windSpeedValue.setFont(Font.font("Tahoma", FontWeight.NORMAL, 32));
        windSpeedValue.setFill(Color.WHITE);

        rightPane.add(feelsLike , 0 ,0);
        rightPane.add(feelsLikeValue , 0 ,1);
        rightPane.add(humidity , 0 ,2);
        rightPane.add(humidityValue , 0 ,3);
        rightPane.add(chanceOfRain , 0 ,4);
        rightPane.add(chanceOfRainValue , 0 ,5);
        rightPane.add(windSpeed , 0 ,6);
        rightPane.add(windSpeedValue , 0 ,7);

        // the rightPane is then added to the main GridPane
        grid.add(rightPane , 7 ,0,1 ,7);


        // BOTTOM SECTION - Displays the weather forecast data

        // a counter variable is used to keep track of the current iteration in the upcoming loop
        int counter = 0;
        GridPane bottomPane = new GridPane();
        bottomPane.setPadding(new Insets(50,0,0,0));

        // this loops over the forecastData and creates a miniDisplay for each day and then appends it to the grid
        for(Map<String, Object> key: weatherForecastData){
            Helper forecastHelper = new Helper();

            // the datetime value of any given day is calculated before being passed into the dateConverter
            // so day x will have a datetime value of current dt value + (x * 86400 seconds) - 86400 being the number of seconds in a day
            Double datetime = Double.valueOf((String) currentWeather.get("dt")) + (counter * (86400.0));
            forecastHelper.dateConverter((String) currentWeather.get("timezone"), String.valueOf(datetime));

            // The weather attributes for that day are passed into a MiniDisplay object which is then added
            // to the bottomPane
            MiniDisplay day = new MiniDisplay(forecastHelper.getDay(), String.valueOf(key.get("max")), String.valueOf(key.get("min")), helper.capitalizeDescription((String) key.get("description")),
                    String.valueOf(key.get("icon")));

            // the index of the day in the forecast array is added to each MiniDisplay object
            day.setUserData(counter);
            bottomPane.add(day,counter,0);
            counter++;
        }

        // the bottomPane is added to the main GridPane object
        grid.add(bottomPane, 1,9);
        //grid.setBackground(new Background(new BackgroundFill(Color.DARKGRAY,  new CornerRadii(0), new Insets(0))));





        BorderPane logoutBar = new BorderPane();
        logoutBar.setPadding(new Insets(5,5,5,5));
        BorderPane.setAlignment(logoutBar,Pos.CENTER);
        GridPane fullApp = new GridPane();
       // fullApp.setAlignment(Pos.TOP);
        fullApp.add(logoutBar,0,0,10,1);
        Text userName = new Text((String) userData.get("name"));
        userName.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
        userName.setFill(Color.WHITE);
        Button logout = new Button("Logout");
        HBox menu = new HBox(userName,logout);
        menu.setSpacing(10);
        menu.setAlignment(Pos.CENTER);
        logout.setStyle("-fx-background-color: transparent;-fx-text-fill: #ffffff;-fx-border-color: white;-fx-cursor: hand;");
        logoutBar.setRight(menu);
        fullApp.add(grid,0,1);

        String backgroundUrl = helper.getBackgroundUrl((String) currentWeather.get("icon"));
        File file = new File(backgroundUrl);
        URL url = file.toURI().toURL();
        System.out.println(url);
        Image backgroundImage = new Image(backgroundUrl);
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(Screen.getPrimary().getVisualBounds().getWidth());
        backgroundImageView.setFitHeight(Screen.getPrimary().getVisualBounds().getHeight());
        fullApp.setBackground(new Background(new BackgroundImage(backgroundImageView.getImage(),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(grid.getWidth(), grid.getHeight(), true, true, true, true))));

        // the gridPane is added to a Scene which is set to the Window display
        Scene scene = new Scene(fullApp, 300, 275);
        primaryStage.setScene(scene);
        primaryStage.hide();
        primaryStage.setMaximized(true);
        primaryStage.show();

        // this event listener handles user searches and resets the data in the model and Window
        submitButton.setOnAction((event) -> {
            try {
                String searchedLocation = locationSearchField.getText();
                try{
                    model.setData(searchedLocation);
                    start(primaryStage);
                } catch(Exception e){
                    searchErrorMessage.setFill(Color.FIREBRICK);
                    searchErrorMessage.setText("Error, try again");

                }



            } catch (Exception e) {
                e.printStackTrace();
            }

        });

    }
}
