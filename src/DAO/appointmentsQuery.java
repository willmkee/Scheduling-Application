package DAO;

import Model.Appointment;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;

public class appointmentsQuery {
    public static ObservableList<Appointment> getAllAppointments() throws SQLException {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        String sql = "SELECT * FROM client_schedule.appointments;";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        final ZoneId localZone = ZoneId.systemDefault();
        while(rs.next()) {
            int appointmentId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            Timestamp startTime = rs.getTimestamp("Start");
            LocalDateTime startDateTime = startTime.toLocalDateTime();
            Timestamp endTime = rs.getTimestamp("End");
            LocalDateTime endDateTime = endTime.toLocalDateTime();
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");
            Appointment appointment = new Appointment(appointmentId, title, description, location, type, startDateTime, endDateTime,customerId, userId, contactId);
            allAppointments.add(appointment);
        }

    return allAppointments;
    }

    public static int deleteAppointmentById(int deletedId) throws SQLException {
        String sql = "DELETE FROM client_schedule.appointments WHERE Appointment_ID=" + deletedId + ";";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        int rs = ps.executeUpdate();
        return rs;
    }

    public static void deleteAppointmentsByCustomerId(int customerId) throws SQLException {
        String sql = "DELETE FROM client_schedule.appointments WHERE Customer_ID=" + customerId + ";";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
    }

    public static int updateAppointment(int appointmentId, String ldt) throws SQLException {
        String sql = "UPDATE client_schedule.appointments SET Start =\"" + ldt + "\" WHERE Appointment_ID =" + appointmentId + ";";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        int rs = ps.executeUpdate();
        return rs;
    }
}
