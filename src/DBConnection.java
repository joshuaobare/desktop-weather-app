import com.google.gson.internal.LinkedTreeMap;
import org.json.simple.JSONObject;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DBConnection {
    private Connection conn = null;
    private Statement statement = null;
    private ResultSet rs = null;
    private PreparedStatement prstatement = null;

    private HashMap<String, Object> userData = new HashMap<>();

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
        String userEmail;
        String userPass;
        try {
            //statement = conn.createStatement();
            //rs = statement.executeQuery(String.format("SELECT * FROM users where email = %s and password = %s", emailAddress , password));
            prstatement = conn.prepareStatement("SELECT * FROM USERS WHERE EMAIL = ? AND PASSWORD = ?");
            prstatement.setString(1, emailAddress);
            prstatement.setString(2, password);
            rs = prstatement.executeQuery();

            if (rs != null) {
                while (rs.next()) {

                    userEmail = rs.getString(3);
                    userPass = rs.getString(6);
                    userData.put("id", rs.getString(1));
                    userData.put("name", rs.getString(2));
                    userData.put("email", rs.getString(3));
                    userData.put("isAdmin", rs.getBoolean(4));
                    // userData.put("signUpDate", String.valueOf(rs.getDate(5)));
                    userData.put("password", rs.getString(6));
                }
            } else {
                return false;
            }


        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } finally {
            // the resources are released in reverse-order of their creation
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) {
                } // ignore

                rs = null;
            }

            if (prstatement != null) {
                try {
                    prstatement.close();
                } catch (SQLException sqlEx) {
                } // ignore

                prstatement = null;
            }
        }

        try {
            if (userData.get("email").equals(emailAddress) && userData.get("password").equals(password)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }


    }

    public void signUp(String name, String email, String password){
        try{
            prstatement = conn.prepareStatement("INSERT INTO USERS(name , email,isAdmin,signUpDate,password) values ( ? ,? ,0 ,CURRENT_TIMESTAMP() ,?)");
            prstatement.setString(1, name);
            prstatement.setString(2, email);
            prstatement.setString(3, password);
            prstatement.execute();

        }catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    public void update(LinkedTreeMap obj, Map locationData) {

        try {
            prstatement = conn.prepareStatement("INSERT INTO  CURRENT_WEATHER (userID , location , " + "weathercode , temperature , windspeed , time , winddirection) VALUES ( 1 ,? ,? ,? ,? , ? , ?)");
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

    public HashMap<String, Object> getUserData() {
        return userData;
    }

}
