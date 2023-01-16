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
}
