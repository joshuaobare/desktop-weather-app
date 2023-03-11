import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

        VBox center = new VBox();
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

        centerBottom.getChildren().addAll(centerBottomLeft,centerBottomRight);
        center.getChildren().addAll(centerTop,centerBottom);

        VBox right = new VBox();

        borderPane.setLeft(left);
        borderPane.setRight(right);
        borderPane.setCenter(center);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
}
