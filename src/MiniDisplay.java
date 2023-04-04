import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class MiniDisplay extends GridPane {
    public MiniDisplay(String daytitle,String hightemptitle,String lowtemptitle,String weathericontitle, String icon) {
        super();
        setAlignment(Pos.CENTER);
        Text dayTitle = new Text(daytitle);
        dayTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
        dayTitle.setFill(Color.WHITE);
        add(dayTitle, 0 ,0);
        Text highTemperature = new Text(String.valueOf(Math.round(Double.valueOf(hightemptitle)))+ "\u00B0C");
        highTemperature.setFont(Font.font("Tahoma", FontWeight.NORMAL, 24));
        highTemperature.setFill(Color.WHITE);
        add(highTemperature, 0 ,1);
        Image image = new Image(String.format("http://openweathermap.org/img/wn/%s@2x.png", icon));
        ImageView imageView = new ImageView(image);
        Group mainImg = new Group(imageView);
        add(mainImg, 0, 2);

        Text lowTemperature = new Text(String.valueOf(Math.round(Double.valueOf(lowtemptitle)))+ "\u00B0C");
        lowTemperature.setFont(Font.font("Tahoma", FontWeight.NORMAL, 10));
        lowTemperature.setFill(Color.WHITE);
        add(lowTemperature, 0 ,3);
        Text weatherIcon = new Text(weathericontitle);
        weatherIcon.setFont(Font.font("Tahoma", FontWeight.NORMAL, 10));
        weatherIcon.setFill(Color.WHITE);
        add(weatherIcon, 0 ,4);
        setPrefWidth(300);

    }
}
