import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DBConnection {
    private Connection conn = null;
    private Statement statement = null;
    private ResultSet rs = null;
    private PreparedStatement prstatement = null;

    private HashMap<String, Object> userData = new HashMap<>();
    private ArrayList<HashMap<String,Object>> registeredUsers = new ArrayList<>();
    private ArrayList<HashMap<String,Object>> unregisteredUsers = new ArrayList<>();
    private HashMap<String,Object> statistics = new HashMap<>();


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

    public void retrieveRegisteredUsers() throws SQLException {
        prstatement = conn.prepareStatement(
                                "SELECT USERS.* , LICENSE_ALLOCATION.* " +
                                    "FROM LICENSE_ALLOCATION " +
                                    "INNER JOIN USERS ON LICENSE_ALLOCATION.USERID = USERS.USERID");
        rs = prstatement.executeQuery();

        while(rs.next()){
            HashMap <String, Object> hashMap = new HashMap<>();
            hashMap.put("userID",rs.getString(1));
            hashMap.put("name",rs.getString(2));
            hashMap.put("isAdmin",rs.getString(4));
            hashMap.put("signUpDate",rs.getString(5));
            registeredUsers.add(hashMap);
        }

        // Unlicensed users are selected
        prstatement = conn.prepareStatement(
                                        "SELECT * " +
                                                "FROM USERS " +
                                                "WHERE USERID NOT IN " +
                                                "(SELECT USERID " +
                                                "FROM LICENSE_ALLOCATION)"
                                            );
        rs = prstatement.executeQuery();

        while(rs.next()){
            HashMap <String, Object> hashMap = new HashMap<>();
            hashMap.put("name",rs.getString(2));
            hashMap.put("signUpDate",rs.getString(5));
            unregisteredUsers.add(hashMap);
        }
    }

    public Boolean userAuthentication(String emailAddress, String password) throws SQLException {
        String userEmail;
        String userPass;
        try {

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
                    userData.put("signUpDate", String.valueOf(rs.getDate(5)));
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

    private void licenseAllocation(String userID,String licenseID) throws SQLException{
        prstatement = conn.prepareStatement("INSERT INTO LICENSE_ALLOCATION (userID , licenseID) VALUES (?,?)");
        prstatement.setObject(1, userID);
        prstatement.setObject(2, licenseID);
        prstatement.execute();

        prstatement = conn.prepareStatement("UPDATE LICENSES SET ISALLOCATED = 1 WHERE LICENSEID = ?");
        prstatement.setObject(1, licenseID);
        prstatement.execute();
    }

    private void fetchStatistics() throws SQLException {
        prstatement = conn.prepareStatement("SELECT COUNT(*) FROM WEATHER_SEARCHES");
        rs = prstatement.executeQuery();

        while(rs.next()){
            statistics.put("totalSearches", rs.getString(1));
        }

        prstatement = conn.prepareStatement("SELECT COUNT(*) FROM USERS");
        rs = prstatement.executeQuery();

        while(rs.next()){
            statistics.put("userCount", rs.getString(1));

        }

        prstatement = conn.prepareStatement("SELECT COUNT(*) FROM API_CALLS");
        rs = prstatement.executeQuery();

        while(rs.next()){
            statistics.put("apiCalls", rs.getString(1));

        }

    }

    public void apiCallIncrement() throws SQLException {
        prstatement = conn.prepareStatement("INSERT INTO API_CALLS (time) VALUES (CURRENT_TIMESTAMP())");
        prstatement.execute();
    }


    private boolean licenseValidation(String licenseID) throws SQLException {
        statement = conn.createStatement();
        rs = statement.executeQuery("SELECT * FROM LICENSES");
        boolean licenseValid = false;

        while(rs.next()){
            if(((rs.getString(1)).equals(licenseID)) && ((rs.getString(2)).equals("0"))){
                licenseValid = true;
            }
        }
        return licenseValid;

    }

    public void signUp(String name, String email, String password , String licenseID) {
        try {
            prstatement = conn.prepareStatement("INSERT INTO USERS(name , email,isAdmin,signUpDate,password) values ( ? ,? ,0 ,CURRENT_TIMESTAMP() ,?)",
                    Statement.RETURN_GENERATED_KEYS);
            String userID = "";

            if (!name.equals("") && !email.equals("") && !password.equals("")) {
                prstatement.setString(1, name);
                prstatement.setString(2, email);
                prstatement.setString(3, password);
                prstatement.execute();
                ResultSet rs = prstatement.getGeneratedKeys();

                while(rs.next()){
                    userID = rs.getString(1);

                }

            } else {
                throw new RuntimeException("Name, Email or Password are blank");
            }

            if(!licenseID.equals("")){
                boolean licenseValid = licenseValidation(licenseID);

                if(licenseValid){
                    licenseAllocation(userID, licenseID);
                }else {
                    throw new RuntimeException("License is invalid");
                }
            }


        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    public void weatherSearch(Map userData , Map currentWeather) {

        try {
            prstatement = conn.prepareStatement("INSERT INTO  WEATHER_SEARCHES (userID , location , " + "weather_description , temperature , wind_speed , time , feels_like, humidity) " +
                    "VALUES ( ?,? ,? ,? ,? ,CURRENT_TIMESTAMP() , ? , ?)");
            prstatement.setObject(1, userData.get("id"));
            prstatement.setObject(2, currentWeather.get("name"));
            prstatement.setObject(3, currentWeather.get("description"));
            prstatement.setObject(4, currentWeather.get("temp"));
            prstatement.setObject(5, currentWeather.get("speed"));
            prstatement.setObject(6, currentWeather.get("feels_like"));
            prstatement.setObject(7, currentWeather.get("humidity"));
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

    public HashMap<String, Object> getStatistics() throws SQLException {
        fetchStatistics();
        return statistics;
    }

    public ArrayList<HashMap<String, Object>> getRegisteredUsers() {
        return registeredUsers;
    }

    public ArrayList<HashMap<String, Object>> getUnregisteredUsers() {
        return unregisteredUsers;
    }
}
