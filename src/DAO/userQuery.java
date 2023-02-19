package DAO;

import Model.Customer;
import Model.User;
import com.mysql.cj.x.protobuf.MysqlxPrepare;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The type User query.
 */
public abstract class userQuery {
    /**
     * Password verification int.
     *
     * @param userName the user name
     * @param password the password
     * @return the int
     * @throws SQLException the sql exception
     */
    public static int passwordVerification (String userName, String password) throws SQLException {
        try {
            String sql = "SELECT * FROM client_schedule.users\n" +
                    "WHERE Password = ? AND User_Name = ?;";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, password);
            ps.setString(2, userName);
            ResultSet results = ps.executeQuery();
            if (results.next()) {
                    return results.getInt("User_ID");
            }
        }
        catch (SQLException exception){
            exception.printStackTrace();
        }
        return -1;
    }

    /**
     * Gets all users.
     *
     * @return the all users
     * @throws SQLException the sql exception
     */
    public static ObservableList<User> getAllUsers() throws SQLException {
        ObservableList<User> allUsers = FXCollections.observableArrayList();
        String sql = "SELECT users.User_ID, users.User_Name, users.Password\n" +
                "FROM client_schedule.users";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int userId = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            String password = rs.getString("Password");
            User user = new User(userId, userName, password);
            allUsers.add(user);
        }
        return allUsers;
    }

    /**
     * Gets user by id.
     *
     * @param userId the user id
     * @return the user by id
     * @throws SQLException the sql exception
     */
    public static User getUserById(int userId) throws SQLException {
        String sql = "SELECT * FROM client_schedule.users WHERE users.User_ID=" + userId;
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        rs.next();
        String userName = rs.getString("User_Name");
        String password = rs.getString(("Password"));
        return new User(userId, userName, password);
    }
}
