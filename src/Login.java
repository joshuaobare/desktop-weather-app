import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Login extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        DBConnection db = new DBConnection();
        primaryStage.setTitle("Weather App");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Image weatherIcon = new Image("assets/snowy.png");
        ImageView imageView = new ImageView(weatherIcon);
        Text loginTitle = new Text(" WEATHER APP");
        loginTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));
        HBox loginHeader = new HBox(imageView,loginTitle);
        loginHeader.setAlignment(Pos.CENTER);
        loginHeader.setPadding(new Insets(50,0,30,0));

        Text scenetitle = new Text("Welcome Back");
        scenetitle.setTextAlignment(TextAlignment.CENTER);
        Text welcometitle = new Text("Please enter your details");
        welcometitle.setTextAlignment(TextAlignment.CENTER);
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        welcometitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
        grid.add(loginHeader, 0, 0, 2, 1);
        grid.add(scenetitle, 0, 1, 2, 1);
        grid.add(welcometitle, 0, 2, 2, 1);

        Label email = new Label("Email:");
        grid.add(email, 0, 3);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 3);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 4);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 4);


        Button signInBtn = new Button("Sign in");
        Button signUpBtn = new Button("Sign up");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(signInBtn);
        hbBtn.getChildren().add(signUpBtn);
        grid.add(hbBtn, 1, 5);
        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 7);


        Scene scene = new Scene(grid, 300, 275);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();

        // An EventListener is attached to the Login button, if the user's credentials are accurate
        // the user is logged in and the view switches to the MainUI
        signInBtn.setOnAction((event) -> {
            DataModel model = new DataModel();
            String userEmail = userTextField.getText();
            String userPass = pwBox.getText();
            Boolean isAuthenticated;
            HashMap userData;

            // the user's input is compared to what's in the DB
            try {
                isAuthenticated = db.userAuthentication(userEmail,userPass);
                userData = db.getUserData();
                db.userMostSearched((String) userData.get("id"));
                model.setUserData(userData);
                model.setUserMostSearchedLocations(db.getUserMostSearchedLocations());
                model.setGlobalMostSearchedLocations(db.getGlobalMostSearchedLocations());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            MainUI mainUI = new MainUI(model);
            // if authentication is successful, the view is switched to MainUI
            if(isAuthenticated){
                Stage stage = new Stage();
                if((userData.get("isAdmin").equals(true))){
                    AdminDashboard adminDashboard = new AdminDashboard(mainUI);
                    adminDashboard.start(stage);
                } else{
                    try {
                        mainUI.start(stage);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

                primaryStage.close();
            } else {
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("Incorrect details, try again");
            }
        });

        signUpBtn.setOnAction((event) -> {
            SignUp signUp = new SignUp();
            Stage stage = new Stage();
            signUp.start(stage);
            primaryStage.close();
        });

    }
}

class expiredUser extends Scene {

    public expiredUser(Parent parent) {
        super(parent);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        Text textMessage = new Text("Your trial period has expired\n Contact your administrator");
        grid.add(textMessage,0,0);
    }
}