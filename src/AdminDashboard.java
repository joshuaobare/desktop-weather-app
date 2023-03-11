import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AdminDashboard extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Weather App");
        BorderPane borderPane = new BorderPane();

        VBox left = new VBox();
        Button mainUIBtn = new Button("Weather App");
        left.getChildren().add(mainUIBtn);
        left.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.20));

        VBox center = new VBox();

        center.setAlignment(Pos.TOP_CENTER);
        center.setBackground(new Background(new BackgroundFill(Color.DARKGRAY,  new CornerRadii(0), new Insets(0))));
        VBox centerTop = new VBox();

        Text centerHeading = new Text("Dashboard");
        Text apiCallsHeader = new Text("API Calls This Week");
        centerTop.getChildren().addAll(centerHeading,apiCallsHeader);

        HBox centerBottom = new HBox();
        VBox centerBottomLeft = new VBox();
        Text centerBottomLeftHeader = new Text("Registered Users");
        TableView regUsersTables = new TableView<>();
        regUsersTables.setEditable(true);
        TableColumn user = new TableColumn<>("User");
        TableColumn dateAdded = new TableColumn<>("Date Added");
        TableColumn revokeUser = new TableColumn<>("");
        regUsersTables.getColumns().addAll(user,dateAdded,revokeUser);

        centerBottomLeft.getChildren().addAll(centerBottomLeftHeader,regUsersTables);
        VBox centerBottomRight = new VBox();
        Text centerBottomRightHeader = new Text("Statistics");
        Text apiCallNumber = new Text("API Calls");
        Text apiCallsRemaining = new Text("API Calls Remaining");
        Text totalSearches = new Text("Total Searches");
        Text mostSearched = new Text("Most Searched Location");
        Text usersCount = new Text("Number of Active Users");

        centerBottomRight.getChildren().addAll(centerBottomRightHeader,apiCallNumber,apiCallsRemaining,totalSearches,mostSearched,usersCount);
        centerBottom.getChildren().addAll(centerBottomLeft,centerBottomRight);
        center.getChildren().addAll(centerTop,centerBottom);

        VBox right = new VBox();
        VBox rightTop = new VBox();
        Text adminName = new Text("John Oloo");
        Label adminLabel = new Label("ADMIN");

        rightTop.getChildren().addAll(adminName,adminLabel);

        VBox rightCenter = new VBox();
        Text rightCenterHeader = new Text("Most Searched Locations");
        Text locationone = new Text("Nairobi: 100");
        Text locationtwo = new Text("Kampala: 100");
        Text locationthree = new Text("Cape Town: 100");
        Text locationfour = new Text("London: 100");
        Text locationfive = new Text("Jamaica: 100");
        Text locationsix = new Text("Togo: 100");

        rightCenter.getChildren().addAll(rightCenterHeader,locationone,locationtwo, locationthree, locationfour, locationfive, locationsix);

        VBox rightBottom = new VBox();
        Text rightBottomHeader = new Text("Free Trials");
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

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
}
