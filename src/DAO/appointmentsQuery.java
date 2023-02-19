package DAO;

import Model.Appointment;
import helper.JDBC;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;

import java.sql.*;
import java.time.*;

/**
 * The type Appointments query.
 */
public class appointmentsQuery {
    /**
     * Gets all appointments.
     *
     * @return the all appointments
     * @throws SQLException the sql exception
     */
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

    /**
     * Delete appointment by id int.
     *
     * @param deletedId the deleted id
     * @return the int
     * @throws SQLException the sql exception
     */
    public static int deleteAppointmentById(int deletedId) throws SQLException {
        String sql = "DELETE FROM client_schedule.appointments WHERE Appointment_ID=" + deletedId + ";";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        int rs = ps.executeUpdate();
        return rs;
    }

    /**
     * Delete appointments by customer id int.
     *
     * @param customerId the customer id
     * @return the int
     * @throws SQLException the sql exception
     */
    public static int deleteAppointmentsByCustomerId(int customerId) throws SQLException {
        String sql = "DELETE FROM client_schedule.appointments WHERE Customer_ID=" + customerId + ";";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        int rs = ps.executeUpdate();
        return rs;
    }

    /**
     * Update appointment int.
     *
     * @param appointmentId the appointment id
     * @param start         the start
     * @param end           the end
     * @param title         the title
     * @param description   the description
     * @param location      the location
     * @param contactId     the contact id
     * @param customerId    the customer id
     * @param userId        the user id
     * @param type          the type
     * @return the int
     * @throws SQLException the sql exception
     */
    public static int updateAppointment(int appointmentId, String start, String end, String title, String description, String location, int contactId, int customerId, int userId, String type) throws SQLException {
        String sql = "UPDATE client_schedule.appointments SET Start =\"" + start + "\", End =\"" + end + "\", Title=\"" + title + "\", Description=\"" + description + "\", Location=\"" + location + "\", Contact_ID=" + contactId +", Customer_ID=" + customerId + ", User_ID=" + userId + ", Type =\"" + type +"\", Last_Update = NOW(), Last_Updated_By = 'script' WHERE Appointment_ID =" + appointmentId + ";";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        int rs = ps.executeUpdate();
        return rs;
    }

    /**
     * Gets appointments by customer id.
     *
     * @param customerId the customer id
     * @return the appointments by customer id
     * @throws SQLException the sql exception
     */
    public static ObservableList<Appointment> getAppointmentsByCustomerId(int customerId) throws SQLException {
        ObservableList<Appointment> customerAppointments = FXCollections.observableArrayList();
        String sql = "SELECT * FROM client_schedule.appointments WHERE Customer_ID=" + customerId + ";";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int appointmentId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            Timestamp startTime = rs.getTimestamp("Start");
            LocalDateTime startDateTime = startTime.toLocalDateTime();
            Timestamp endTime = rs.getTimestamp("End");
            LocalDateTime endDateTime = endTime.toLocalDateTime();
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");
            Appointment appointment = new Appointment(appointmentId, title, description, location, type, startDateTime, endDateTime,customerId, userId, contactId);
            customerAppointments.add(appointment);
        }
        return customerAppointments;
    }

    /**
     * Add new appointment int.
     *
     * @param start       the start
     * @param end         the end
     * @param title       the title
     * @param description the description
     * @param location    the location
     * @param contactId   the contact id
     * @param customerId  the customer id
     * @param userId      the user id
     * @param type        the type
     * @return the int
     * @throws SQLException the sql exception
     */
    public static int addNewAppointment(String start, String end, String title, String description, String location, int contactId, int customerId, int userId, String type) throws SQLException {
        String sql = "INSERT INTO client_schedule.appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID)\n" +
                "VALUES (\"" + title + "\", \"" + description + "\", \"" + location + "\", \"" + type + "\", \"" + start + "\", \"" + end + "\", NOW(), 'script', NOW(), 'script', \"" + customerId + "\", \"" + userId + "\", \"" + contactId + "\");";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        int rs = ps.executeUpdate();
        return rs;
    }

    /**
     * Display current week observable list.
     *
     * @param currentTimeUtc the current time utc
     * @param nextWeekUtc    the next week utc
     * @return the observable list
     * @throws SQLException the sql exception
     */
    public static ObservableList<Appointment> displayCurrentWeek(String currentTimeUtc, String nextWeekUtc) throws SQLException {
        ObservableList<Appointment> currentWeekAppointments = FXCollections.observableArrayList();
        String sql = "SELECT * FROM client_schedule.appointments WHERE Start BETWEEN \"" + currentTimeUtc + "\" AND \"" +nextWeekUtc + "\";";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
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
            currentWeekAppointments.add(appointment);
        }

        return currentWeekAppointments;
    }

    /**
     * Gets appointmentsby contact.
     *
     * @param contactId the contact id
     * @return the appointmentsby contact
     * @throws SQLException the sql exception
     */
    public static ObservableList<Appointment> getAppointmentsbyContact(int contactId) throws SQLException {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        String sql = "SELECT * FROM client_schedule.appointments WHERE Contact_ID=" + contactId + ";";
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
            Appointment appointment = new Appointment(appointmentId, title, description, location, type, startDateTime, endDateTime,customerId, userId, contactId);
            allAppointments.add(appointment);
        }

        return allAppointments;
    }
}
