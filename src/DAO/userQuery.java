package DAO;

import com.mysql.cj.x.protobuf.MysqlxPrepare;
import helper.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class userQuery {
    public static int passwordVerification (String userName, String password) throws SQLException {
        try {
            String sql = "SELECT * FROM client_schedule.users\n" +
                    "WHERE Password = \"" + password + "\" AND User_Name = \"" + userName + "\";";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet results = ps.executeQuery();
            results.next();
            if (results.getString("User_Name").equals(userName)) {
                if (results.getString("Password").equals(password)) {
                    return results.getInt("User_ID");

                }
            }
        }
        catch (SQLException exception){
            exception.printStackTrace();
        }
        return -1;
    }
}
