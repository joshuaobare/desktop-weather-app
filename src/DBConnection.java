import com.google.gson.internal.LinkedTreeMap;
import org.json.simple.JSONObject;
import java.sql.*;
import java.util.Map;

public class DBConnection {
    private Connection conn = null;
    private Statement statement = null;
    private ResultSet rs = null;
    private PreparedStatement prstatement = null;

    public DBConnection() {
        connect();
    }

    public void connect() {
        // the connection to the database is made. an exception is thrown is the connection fails
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/desktopweatherapp?user=root");
            System.out.println(conn);
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    public Boolean userAuthentication(String emailAddress, String password) throws SQLException {
        try {
            //statement = conn.createStatement();
            //rs = statement.executeQuery(String.format("SELECT * FROM users where email = %s and password = %s", emailAddress , password));
            prstatement = conn.prepareStatement("SELECT * FROM USERS WHERE EMAIL = ? AND PASSWORD = ?");
            prstatement.setString(1, emailAddress);
            prstatement.setString(2, password);
            rs = prstatement.executeQuery();

            //System.out.println(rs.getString(1));
            if( !rs.isBeforeFirst()){
                System.out.println("Empty");
            }



        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        while(rs.next()){
            System.out.println(rs.getString(2));
        }

        if (rs != null){
            return true;
        } else {
            return false;
        }
    }

    public void update(LinkedTreeMap obj, Map locationData) {

        try {
            prstatement = conn.prepareStatement("INSERT INTO  CURRENT_WEATHER (userID , location , " +
                    "weathercode , temperature , windspeed , time , winddirection) VALUES ( 1 ,? ,? ,? ,? , ? , ?)");
            prstatement.setObject(1, locationData.get("name"));
            prstatement.setObject(2, obj.get("weathercode"));
            prstatement.setObject(3, obj.get("temperature"));
            prstatement.setObject(4, obj.get("windspeed"));
            prstatement.setObject(5, obj.get("time"));
            prstatement.setObject(6, obj.get("winddirection"));
            prstatement.execute();

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }


}
