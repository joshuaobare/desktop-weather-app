import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminDashboard extends Application {
    MainUI mainUI;
    DataModel model;

    public AdminDashboard(MainUI mainUI) {
        this.mainUI = mainUI;
        model = mainUI.getModel();
    }

    public AdminDashboard() {
        model = new DataModel();
        Stage stage = new Stage();
        mainUI = new MainUI(model);

    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        HashMap<String, Object>  statistics = model.getStatistics();
        ArrayList<HashMap<String, Object>> registeredUsers = model.getRegisteredUsers();
        ArrayList<HashMap<String, Object>> unRegisteredUsers = model.getUnregisteredUsers();
        System.out.println(unRegisteredUsers);
        primaryStage.setTitle("Weather App");
        BorderPane borderPane = new BorderPane();

        VBox left = new VBox();
        left.setPadding(new Insets(100,0,0,10));

        Image weatherIcon = new Image("assets/snowy.png");
        ImageView imageView = new ImageView(weatherIcon);
        Button mainUIBtn = new Button("Weather App", imageView);
        mainUIBtn.setAlignment(Pos.CENTER);
        //mainUIBtn.setPadding(new Insets(5,5,10,10));
        left.getChildren().add(mainUIBtn);
        left.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.20));

        VBox center = new VBox();
        center.setPadding(new Insets(25, 25, 25, 25));
        center.setAlignment(Pos.TOP_CENTER);
        center.setBackground(new Background(new BackgroundFill(Color.DARKGRAY,  new CornerRadii(0), new Insets(0))));
        VBox centerTop = new VBox();

        Text centerHeading = new Text("Dashboard");
        centerHeading.setFont(Font.font("Tahoma", FontWeight.BOLD, 32));
        Text apiCallsHeader = new Text("API Calls This Week");
        apiCallsHeader.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
        centerTop.getChildren().addAll(centerHeading,apiCallsHeader);

        HBox centerBottom = new HBox();
        centerBottom.setPadding(new Insets(200,0,0,0));
        VBox centerBottomLeft = new VBox();
        Text centerBottomLeftHeader = new Text("Registered Users");
        centerBottomLeftHeader.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
        /*TableView regUsersTables = new TableView<>();
        regUsersTables.setEditable(true);
        TableColumn user = new TableColumn<>("User");
        TableColumn dateAdded = new TableColumn<>("Date Added");
        TableColumn revokeUser = new TableColumn<>("");
        regUsersTables.getColumns().addAll(user,dateAdded,revokeUser);*/

        centerBottomLeft.getChildren().addAll(centerBottomLeftHeader);

        for(Map.Entry<String,Object> user: unRegisteredUsers.entrySet()){
            System.out.println(unRegisteredUsers);
            HBox hBox = new HBox();
            Text name = new Text(user.getKey());
            Text date = new Text((String) user.getValue());
            hBox.getChildren().addAll(name,date);
            centerBottomLeft.getChildren().add(hBox);
        }


        VBox centerBottomRight = new VBox();
        centerBottomRight.setPadding(new Insets(0,0,0,100));
        Text centerBottomRightHeader = new Text("Statistics");
        centerBottomRightHeader.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
        Text apiCallNumber = new Text(String.format("API Calls: %s", statistics.get("apiCalls")));
        Text apiCallsRemaining = new Text(String.format("API Calls Remaining: %s", (String.valueOf(1000 - Integer.valueOf((String) statistics.get("apiCalls"))))));
        Text totalSearches = new Text(String.format("Total Searches: %s", statistics.get("totalSearches")));
        Text mostSearched = new Text("Most Searched Location");
        Text usersCount = new Text(String.format("Number of Active Users: %s", statistics.get("userCount")));

        VBox.setMargin(centerBottomLeft, new Insets(50,0,0,0));

        centerBottomRight.getChildren().addAll(centerBottomRightHeader,apiCallNumber,apiCallsRemaining,totalSearches,mostSearched,usersCount);
        centerBottom.getChildren().addAll(centerBottomLeft,centerBottomRight);
        center.getChildren().addAll(centerTop,centerBottom);

        VBox right = new VBox();
        right.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.20));
        right.setPadding(new Insets(0, 25, 25, 25));

        VBox rightTop = new VBox();
        rightTop.setAlignment(Pos.TOP_RIGHT);
        VBox.setMargin(rightTop, new Insets(10,0,0,0));
        Text adminName = new Text("John Oloo");
        Label adminLabel = new Label("ADMIN");

        rightTop.getChildren().addAll(adminName,adminLabel);

        VBox rightCenter = new VBox();
        VBox.setMargin(rightCenter, new Insets(30,0,0,0));
        Text rightCenterHeader = new Text("Most Searched Locations");
        rightCenterHeader.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
        Text locationone = new Text("Nairobi: 100");
        Text locationtwo = new Text("Kampala: 100");
        Text locationthree = new Text("Cape Town: 100");
        Text locationfour = new Text("London: 100");
        Text locationfive = new Text("Jamaica: 100");
        Text locationsix = new Text("Togo: 100");

        rightCenter.getChildren().addAll(rightCenterHeader,locationone,locationtwo, locationthree, locationfour, locationfive, locationsix);

        VBox rightBottom = new VBox();
        VBox.setMargin(rightBottom, new Insets(30,0,0,0));
        Text rightBottomHeader = new Text("Free Trials");
        rightBottomHeader.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
        TableView trialsUsersTable = new TableView<>();
        trialsUsersTable.setEditable(true);
        TableColumn userCol = new TableColumn<>("User");
        TableColumn accessCol = new TableColumn<>("");
        trialsUsersTable.getColumns().addAll(userCol,accessCol);

        rightBottom.getChildren().addAll(rightBottomHeader,trialsUsersTable);

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
