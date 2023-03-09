import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.SQLException;

public class SignUp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        DBConnection db = new DBConnection();
        primaryStage.setTitle("Weather App");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);

        Text sceneTitle = new Text("WEATHER APP");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));
        Text createAccountTitle = new Text("Create an account");
        createAccountTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        Label nameLabel = new Label("Name");
        TextField nameField = new TextField();

        Label emailLabel = new Label("Email");
        TextField emailField = new TextField();

        Label passwordLabel = new Label("Password");
        PasswordField passwordField = new PasswordField();

        Label licenseLabel = new Label("License");
        TextField licenseField = new TextField();

        Button signUpBtn = new Button("Sign Up");
        final Text actiontarget = new Text();


        grid.add(sceneTitle,0,0);
        grid.add(createAccountTitle,0,1);
        grid.add(nameLabel,0,2);
        grid.add(nameField,0,3);
        grid.add(emailLabel,0,4);
        grid.add(emailField,0,5);
        grid.add(passwordLabel,0,6);
        grid.add(passwordField,0,7);
        grid.add(licenseLabel,0,8);
        grid.add(licenseField,0,9);
        grid.add(signUpBtn,0,10,2,1);
        grid.add(actiontarget, 0, 11);

        Scene scene = new Scene(grid, 300, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setMaximized(true);

        signUpBtn.setOnAction((event) -> {
            String name = nameField.getText();
            String password = passwordField.getText();
            String email = emailField.getText();
            String license = licenseField.getText();
            Boolean signUpSuccessful = false;

            try{
                db.signUp(name,email,password,license);
                signUpSuccessful = true;

            } catch (RuntimeException e){
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText(e.getMessage());
            }

            if(signUpSuccessful){
                Stage stage = new Stage();
                Login login = new Login();
                login.start(stage);
                primaryStage.close();
            }
        });
    }
}
