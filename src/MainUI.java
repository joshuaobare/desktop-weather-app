import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

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




        Scene scene = new Scene(grid, 300, 275);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();

    }
}
