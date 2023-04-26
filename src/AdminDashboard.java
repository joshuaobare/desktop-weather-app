import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class AdminDashboard extends Application {
    MainUI mainUI;
    DataModel model;
    DBConnection db = new DBConnection();
    Helper helper = new Helper();

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
        HashMap<String, Object>  statistics = model.getStatistics();
        ArrayList<HashMap<String, Object>> registeredUsers = model.getRegisteredUsers();
        ArrayList<HashMap<String, Object>> unregisteredUsers = model.getUnregisteredUsers();
        HashMap<String,Integer> globalMostSearchedLocations = model.getGlobalMostSearchedLocations();
        TreeMap<Date, Integer> apiCallCount = model.getApiCallCount();
        HashMap<String, Integer> sortedGlobalMostSearchedLocations = helper.sortByValue(globalMostSearchedLocations);

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


        final CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Day");
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Number of Calls");

        final LineChart<String, Number> lineChart = new LineChart<>(xAxis,yAxis);
        lineChart.setTitle("Number of Calls per Day");
        XYChart.Series series = new XYChart.Series();
        series.setName("Calls");


        for(Map.Entry<Date,Integer> day: apiCallCount.entrySet()){
           // System.out.println(day.getKey());
           // System.out.println(day.getValue());
            String dayString = day.getKey().toString().substring(0,10);
            series.getData().add(new XYChart.Data(dayString,day.getValue()));
        }
        lineChart.getData().add(series);
        lineChart.prefHeightProperty().bind(primaryStage.heightProperty().multiply(0.5));
        centerTop.getChildren().addAll(centerHeading,apiCallsHeader,lineChart);


        HBox centerBottom = new HBox();
        centerBottom.setPadding(new Insets(10,0,0,0));
        VBox centerBottomLeft = new VBox();
        Text centerBottomLeftHeader = new Text("Registered Users");
        centerBottomLeftHeader.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));

/*
        TableView regUsersTables = new TableView<>();
        regUsersTables.setEditable(true);
        TableColumn user = new TableColumn<>("User");
        TableColumn dateAdded = new TableColumn<>("Date Added");
        //TableColumn buttonColumn = new TableColumn<>("");
        regUsersTables.getColumns().addAll(user,dateAdded);
        regUsersTables.setPrefWidth(350);

        ArrayList<User> users = new ArrayList<>();

        for(HashMap<String,Object> map :registeredUsers){
            SimpleStringProperty userName = new SimpleStringProperty((String) map.get("name"));
            System.out.println(userName);
            SimpleStringProperty userDateAdded = new SimpleStringProperty((String) map.get("signUpDate"));
            users.add(new User(userName,userDateAdded));
        }

        final ObservableList<User> data = FXCollections.observableArrayList(users);

        user.setCellValueFactory(new PropertyValueFactory<User,String>("name"));
        dateAdded.setCellValueFactory(new PropertyValueFactory<User,String>("dateAdded"));

       /* Callback<TableColumn<User, Void>, TableCell<User, Void>> regUserTableCellFactory = new Callback<TableColumn<User, Void>, TableCell<User, Void>>() {
            @Override
            public TableCell<User, Void> call(final TableColumn<User, Void> param) {
                final TableCell<User, Void> cell = new TableCell<User, Void>() {

                    private final Button btn = new Button("Revoke License");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            //
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
       // buttonColumn.setCellFactory(regUserTableCellFactory);

        regUsersTables.setItems(data);

        */
        centerBottomLeft.getChildren().addAll(centerBottomLeftHeader);


        for(HashMap<String,Object> map:registeredUsers){
            HBox userBox = new HBox();
            userBox.setPadding(new Insets(10 ,0 ,0 ,0));
            userBox.setSpacing(10);
            Text userName = new Text((String) map.get("name"));
            Button btn = new Button("Remove User");
            userBox.getChildren().addAll(userName, btn);
            centerBottomLeft.getChildren().addAll(userBox);
        }




        VBox centerBottomRight = new VBox();
        centerBottomRight.setPadding(new Insets(0,0,0,100));
        Text centerBottomRightHeader = new Text("Statistics");
        centerBottomRightHeader.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
        Text apiCallNumber = new Text(String.format("API Calls: %s", statistics.get("apiCalls")));
        Text apiCallsRemaining = new Text(String.format("API Calls Remaining: %s", (String.valueOf(1000 - Integer.valueOf((String) statistics.get("apiCalls"))))));
        Text totalSearches = new Text(String.format("Total Searches: %s", statistics.get("totalSearches")));
        Set<String> keys = sortedGlobalMostSearchedLocations.keySet();
        String mostSearchedLocation = keys.toArray(new String[keys.size()])[0];
       // System.out.println(mostSearchedLocation);
        Text mostSearched = new Text(String.format("Most Searched Location: %s",mostSearchedLocation));
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

/*
        TableView trialsUsersTable = new TableView<>();
        trialsUsersTable.setEditable(true);
        TableColumn userCol = new TableColumn<>("User");
        TableColumn accessCol = new TableColumn<>("Date Added");
        TableColumn btnCol = new TableColumn<>("");
        trialsUsersTable.getColumns().addAll(userCol,accessCol,btnCol);

        ArrayList<User> users2 = new ArrayList<>();
        System.out.println(unregisteredUsers);

        for(HashMap<String,Object> map :unregisteredUsers){
            if((map.get("isAdmin")).equals("0")){
                SimpleStringProperty userName = new SimpleStringProperty((String) map.get("name"));
                SimpleStringProperty userDateAdded = new SimpleStringProperty((String) map.get("signUpDate"));
                users2.add(new User(userName,userDateAdded));
            }
        }

        final ObservableList<User> data2 = FXCollections.observableArrayList(users2);

        userCol.setCellValueFactory(new PropertyValueFactory<User,String>("name"));
        accessCol.setCellValueFactory(new PropertyValueFactory<User,String>("dateAdded"));

        Callback<TableColumn<User, Void>, TableCell<User, Void>> cellFactory = new Callback<TableColumn<User, Void>, TableCell<User, Void>>() {
            @Override
            public TableCell<User, Void> call(final TableColumn<User, Void> param) {
                final TableCell<User, Void> cell = new TableCell<User, Void>() {

                    private final Button btn = new Button("Extend Period");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            //
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        btnCol.setCellFactory(cellFactory);
        trialsUsersTable.setItems(data2); */

        //rightBottom.getChildren().addAll(rightBottomHeader,trialsUsersTable);
        rightBottom.getChildren().addAll(rightBottomHeader);
        for(HashMap<String,Object> map:unregisteredUsers){
            //int isAdmin = Integer.valueOf((String) map.get("isAdmin"));
            if(map.get("isAdmin").equals("0")){
                HBox userBox = new HBox();
                userBox.setPadding(new Insets(10 ,0 ,0 ,0));
                userBox.setSpacing(10);
                Text userName = new Text((String) map.get("name"));
                //Text joinDate = new Text((String) map.get("signUpDate"));
                Button btn = new Button("Renew Trial");
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

