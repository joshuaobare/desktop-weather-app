import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class AdminDashboard extends Application {
    MainUI mainUI;
    DataModel model;
    DBConnection db = new DBConnection();
    Helper helper = new Helper();

    // the AdminDashboard is overloaded depending on whether a mainUI object is provided
    public AdminDashboard(MainUI mainUI) {
        this.mainUI = mainUI;
        model = mainUI.getModel();
    }

    public AdminDashboard() throws SQLException {
        model = new DataModel();
        model.setGlobalMostSearchedLocations(db.getGlobalMostSearchedLocations());
        Stage stage = new Stage();
        mainUI = new MainUI(model);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // variables are initialized from the model Class
        HashMap<String, Object>  statistics = model.getStatistics();
        ArrayList<HashMap<String, Object>> registeredUsers = model.getRegisteredUsers();
        ArrayList<HashMap<String, Object>> unregisteredUsers = model.getUnregisteredUsers();
        HashMap<String,Integer> globalMostSearchedLocations = model.getGlobalMostSearchedLocations();
        TreeMap<Date, Integer> apiCallCount = model.getApiCallCount();
        HashMap<String, Object> userData = model.getUserData();

        // this helper method sorts the most searched locations by search count
        HashMap<String, Integer> sortedGlobalMostSearchedLocations = helper.sortByValue(globalMostSearchedLocations);


        primaryStage.setTitle("Weather App");
        BorderPane borderPane = new BorderPane();

    // LEFT SECTION
        VBox left = new VBox();
        left.setPadding(new Insets(100,0,0,10));

        Image weatherIcon = new Image("assets/snowy.png");
        ImageView imageView = new ImageView(weatherIcon);
        Button mainUIBtn = new Button("Weather App", imageView);
        mainUIBtn.setAlignment(Pos.CENTER);
        mainUIBtn.setStyle(" -fx-background-color: transparent;-fx-border:none;-fx-cursor: hand;");
        left.getChildren().add(mainUIBtn);
        left.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.20));
        left.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY,  new CornerRadii(0), new Insets(0))));

    // CENTER SECTION
        VBox center = new VBox();
        center.setPadding(new Insets(25, 25, 25, 25));
        center.setAlignment(Pos.TOP_CENTER);
        center.setStyle("-fx-border-width: 0 4 0 4;-fx-border-color: black");
        VBox centerTop = new VBox();

        Text centerHeading = new Text("Dashboard");
        centerHeading.setFont(Font.font("Tahoma", FontWeight.BOLD, 32));
        Text apiCallsHeader = new Text("API Calls This Week");
        apiCallsHeader.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));

        // the API call chart is created, and the axis labels are set
        final CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Day");
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Number of Calls");

        final LineChart<String, Number> lineChart = new LineChart<>(xAxis,yAxis);
        lineChart.setTitle("Number of Calls per Day");
        XYChart.Series series = new XYChart.Series();
        series.setName("Calls");

        // the apiCallCount data is looped through, and data added to the chart
        for(Map.Entry<Date,Integer> day: apiCallCount.entrySet()){
            String dayString = day.getKey().toString().substring(0,10);
            series.getData().add(new XYChart.Data(dayString,day.getValue()));
        }
        lineChart.getData().add(series);
        lineChart.prefHeightProperty().bind(primaryStage.heightProperty().multiply(0.5));
        centerTop.getChildren().addAll(centerHeading,apiCallsHeader,lineChart);

        // CENTER BOTTOM LEFT
        HBox centerBottom = new HBox();
        centerBottom.setPadding(new Insets(10,0,0,0));
        VBox centerBottomLeft = new VBox();
        Text centerBottomLeftHeader = new Text("Registered Users");
        centerBottomLeftHeader.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));

        centerBottomLeft.getChildren().addAll(centerBottomLeftHeader);

        // loops through the registeredUsers arrayList
        for(HashMap<String,Object> map:registeredUsers){
            HBox userBox = new HBox();
            userBox.setPadding(new Insets(10 ,0 ,0 ,0));
            userBox.setSpacing(10);
            Text userName = new Text((String) map.get("name"));
            Button btn = new Button("Remove User");

            // when the remove btn is clicked the user is deleted
            btn.setOnAction((event) -> {
                try {
                    model.deleteUser((String) map.get("userID"));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                start(primaryStage);
                primaryStage.hide();
                primaryStage.setMaximized(true);
                primaryStage.show();

            });

            userBox.getChildren().addAll(userName, btn);
            centerBottomLeft.getChildren().addAll(userBox);
        }


        // CENTER BOTTOM RIGHT

        VBox centerBottomRight = new VBox();
        centerBottomRight.setPadding(new Insets(0,0,0,100));
        Text centerBottomRightHeader = new Text("Statistics");
        centerBottomRightHeader.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
        Text apiCallNumber = new Text(String.format("API Calls: %s", statistics.get("apiCalls")));
        Text apiCallsRemaining = new Text(String.format("API Calls Remaining: %s", (String.valueOf(1000 - Integer.valueOf((String) statistics.get("apiCalls"))))));
        Text totalSearches = new Text(String.format("Total Searches: %s", statistics.get("totalSearches")));
        Set<String> keys = sortedGlobalMostSearchedLocations.keySet();
        String mostSearchedLocation = keys.toArray(new String[keys.size()])[0];
        Text mostSearched = new Text(String.format("Most Searched Location: %s",mostSearchedLocation));
        Text usersCount = new Text(String.format("Number of Active Users: %s", statistics.get("userCount")));

        VBox.setMargin(centerBottomLeft, new Insets(50,0,0,0));

        centerBottomRight.getChildren().addAll(centerBottomRightHeader,apiCallNumber,apiCallsRemaining,totalSearches,mostSearched,usersCount);
        centerBottom.getChildren().addAll(centerBottomLeft,centerBottomRight);
        center.getChildren().addAll(centerTop,centerBottom);

    // RIGHT SECTION
        VBox right = new VBox();
        right.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.20));
        right.setPadding(new Insets(0, 25, 25, 25));

        VBox rightTop = new VBox();
        rightTop.setAlignment(Pos.TOP_RIGHT);
        VBox.setMargin(rightTop, new Insets(10,0,0,0));
        Text adminName = new Text((String) userData.get("name"));
        adminName.setStyle("-fx-font-weight: bold;");
        Label adminLabel = new Label("ADMIN");
        adminLabel.setStyle("-fx-font-size: 10;");
        rightTop.getChildren().addAll(adminName,adminLabel);

        VBox rightCenter = new VBox();
        VBox.setMargin(rightCenter, new Insets(30,0,0,0));

        Text rightCenterHeader = new Text("Most Searched Locations: ");
        rightCenterHeader.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
        rightCenter.getChildren().add(rightCenterHeader);


        for(Map.Entry<String,Integer> value: sortedGlobalMostSearchedLocations.entrySet()){
            rightCenter.getChildren().add(new Text(value.getKey() +": " + value.getValue()));
        }


        VBox rightBottom = new VBox();
        VBox.setMargin(rightBottom, new Insets(100,0,0,0));
        Text rightBottomHeader = new Text("Free Trials");
        rightBottomHeader.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
        rightBottom.getChildren().addAll(rightBottomHeader);

        // unregisteredUsers are looped through, any admins are removed
        for(HashMap<String,Object> map:unregisteredUsers){
            if(map.get("isAdmin").equals("0")){
                HBox userBox = new HBox();
                userBox.setPadding(new Insets(10 ,0 ,0 ,0));
                userBox.setSpacing(10);
                Text userName = new Text((String) map.get("name"));
                Button btn = new Button("Renew Trial");

                btn.setOnAction((event) -> {
                    try {
                        db.renewTrial((String) map.get("userID"));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });

                userBox.getChildren().addAll(userName, btn);
                rightBottom.getChildren().addAll(userBox);
            }
        }

        right.getChildren().addAll(rightTop,rightCenter,rightBottom);
        borderPane.setLeft(left);
        borderPane.setRight(right);
        borderPane.setCenter(center);

        mainUIBtn.setOnAction((event)->{
            Stage stage = new Stage();
            try {
                mainUI.start(stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            primaryStage.close();
        });

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
}

