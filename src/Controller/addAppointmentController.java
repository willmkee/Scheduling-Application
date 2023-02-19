package Controller;

import DAO.appointmentsQuery;
import DAO.contactQuery;
import DAO.customerQuery;
import DAO.userQuery;
import Model.Contact;
import Model.Customer;
import Model.User;
import Time.Time;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * The type Add appointment controller.
 */
public class addAppointmentController implements Initializable {
    /**
     * The Title Label.
     */
    public Label addAppointmentLabel;
    /**
     * The appointment id label.
     */
    public Label addAppointmentIdLabel;
    /**
     * The appointment id text field.
     */
    public TextField addAppointmentIdTextField;
    /**
     * The appointment title label.
     */
    public Label addAppointmentTitleLabel;
    /**
     * The appointment title text field.
     */
    public TextField addAppointmentTitleTextField;
    /**
     * The appointment description label.
     */
    public Label addAppointmentDescriptionLabel;
    /**
     * The appointment description text field.
     */
    public TextField addAppointmentDescriptionTextField;
    /**
     * The appointment location label.
     */
    public Label addAppointmentLocationLabel;
    /**
     * The appointment location text field.
     */
    public TextField addAppointmentLocationTextField;
    /**
     * The contact label.
     */
    public Label addContactLabel;
    /**
     * The contact combo box.
     */
    public ComboBox<Contact> addContactComboBox;
    /**
     * The customer id label.
     */
    public Label addAppointmentCustomerIdLabel;
    /**
     * The appointment type label.
     */
    public Label addAppointmentTypeLabel;
    /**
     * The appointment type text field.
     */
    public TextField addAppointmentTypeTextField;
    /**
     * The appointment start date label.
     */
    public Label addAppointmentStartDateLabel;
    /**
     * The appointment start date date picker.
     */
    public DatePicker addAppointmentStartDateDatePicker;
    /**
     * The appointment end date label.
     */
    public Label addAppointmentEndDateLabel;
    /**
     * The appointment end date date picker.
     */
    public DatePicker addAppointmentEndDateDatePicker;
    /**
     * The appointment end time label.
     */
    public Label addAppointmentEndTimeLabel;
    /**
     * The appointment end time combo box.
     */
    public ComboBox<LocalTime> addAppointmentEndTimeComboBox;
    /**
     * The user id label.
     */
    public Label addAppointmentUserIdLabel;
    /**
     * The Save appointment button.
     */
    public Button saveAppointmentButton;
    /**
     * The Cancel button.
     */
    public Button cancelButton;
    /**
     * The appointment start time label.
     */
    public Label addAppointmentStartTimeLabel;
    /**
     * The appointment start time combo box.
     */
    public ComboBox<LocalTime> addAppointmentStartTimeComboBox;
    /**
     * The Customer id combo box.
     */
    public ComboBox<String> customerIdComboBox;
    /**
     * The User id combo box.
     */
    public ComboBox<User> userIdComboBox;

    /**
     * On save appointment.
     * Creates new appointment if all fields are filled in
     *
     * @param actionEvent the action event
     * @throws SQLException the sql exception
     * @throws IOException  the io exception
     */
    public void onSaveAppointment(ActionEvent actionEvent) throws SQLException, IOException {
        if (addAppointmentTitleTextField.getText().length() > 0 && addAppointmentDescriptionTextField.getText().length() >0 && addAppointmentLocationTextField.getText().length() > 0 && addContactComboBox.getValue() != null && customerIdComboBox.getValue() != null && addAppointmentTypeTextField.getText().length() > 0 && addAppointmentEndDateDatePicker.getValue() != null && addAppointmentEndTimeComboBox.getValue() != null && addAppointmentStartDateDatePicker != null && addAppointmentStartTimeComboBox.getValue() != null && userIdComboBox.getValue() != null) {
            LocalDate startDate = addAppointmentStartDateDatePicker.getValue();
            LocalTime startTime = addAppointmentStartTimeComboBox.getValue();
            String utcStart = Time.convertToUtc(startDate, startTime);
            LocalDate endDate = addAppointmentEndDateDatePicker.getValue();
            LocalTime endTime = addAppointmentEndTimeComboBox.getValue();
            String utcEnd = Time.convertToUtc(endDate, endTime);
            String title = addAppointmentTitleTextField.getText();
            String description = addAppointmentDescriptionTextField.getText();
            String location = addAppointmentLocationTextField.getText();
            int contactId = addContactComboBox.getValue().getContactId();
            int customerId = Integer.parseInt(String.valueOf(customerIdComboBox.getValue()));
            String type = addAppointmentTypeTextField.getText();
            int userId = Integer.parseInt(String.valueOf(userIdComboBox.getValue()));
            LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
            LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);
            if (startDateTime.isAfter(endDateTime)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Time Error");
                alert.setContentText("Start Date and Time must be before End Date and Time");

                alert.showAndWait();
            }
            else if(!Time.addAppointmentOverlap(customerId, startDateTime, endDateTime)){
                int i = appointmentsQuery.addNewAppointment(utcStart, utcEnd, title, description, location, contactId, customerId, userId, type);
                if (i > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Appointment successfully added!");

                    alert.showAndWait();

                    Parent appointments = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/appointments.fxml")));
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(appointments);
                    stage.setScene(scene);
                    stage.show();

                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText("Failure");
                    alert.setContentText("Failed to add appointment.");

                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Failure");
                alert.setContentText("Appointment time overlaps");

                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Fields left blank");
            alert.setContentText("Complete all fields");

            alert.showAndWait();
        }
    }

    /**
     * On cancel.
     * Returns to directory view
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void onCancel(ActionEvent actionEvent) throws IOException {
        Parent directory = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/directory.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(directory);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Initialize
     * Initializes combo boxes
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LocalDateTime startEastern = LocalDateTime.of(LocalDate.now(), LocalTime.of(8, 0));
        ZonedDateTime zoneStartEastern = startEastern.atZone(ZoneId.of("America/New_York"));
        ZonedDateTime zoneStartLocal = zoneStartEastern.withZoneSameInstant(ZoneId.systemDefault());
        LocalDateTime startLocal = zoneStartLocal.toLocalDateTime();
        LocalTime startBeginTime = startLocal.toLocalTime();

        LocalDateTime endEastern = LocalDateTime.of(LocalDate.now(), LocalTime.of(21, 45));
        ZonedDateTime zoneEndEastern = endEastern.atZone(ZoneId.of("America/New_York"));
        ZonedDateTime zoneEndLocal = zoneEndEastern.withZoneSameInstant(ZoneId.systemDefault());
        LocalDateTime endLocal = zoneEndLocal.toLocalDateTime();
        LocalTime endBeginTime = endLocal.toLocalTime();

        LocalDateTime appointmentEndEastern = LocalDateTime.of(LocalDate.now(), LocalTime.of(8, 15));
        ZonedDateTime zoneAppointmentEndEastern = appointmentEndEastern.atZone(ZoneId.of("America/New_York"));
        ZonedDateTime zoneAppointmentEndLocal = zoneAppointmentEndEastern.withZoneSameInstant(ZoneId.systemDefault());
        LocalDateTime appointmentEndLocal = zoneAppointmentEndLocal.toLocalDateTime();
        LocalTime startEndTime = appointmentEndLocal.toLocalTime();

        LocalDateTime appointmentEndTimeEastern = LocalDateTime.of(LocalDate.now(), LocalTime.of(22, 00));
        ZonedDateTime zoneAppointmentEndTimeEastern = appointmentEndTimeEastern.atZone(ZoneId.of("America/New_York"));
        ZonedDateTime zoneAppointmentEndTimeLocal = zoneAppointmentEndTimeEastern.withZoneSameInstant(ZoneId.systemDefault());
        LocalDateTime appointmentEndTimeLocal = zoneAppointmentEndTimeLocal.toLocalDateTime();
        LocalTime endEndTime = appointmentEndTimeLocal.toLocalTime();


        while(startBeginTime.isBefore(endBeginTime.plusSeconds(1))){
            addAppointmentStartTimeComboBox.getItems().add(startBeginTime);
            startBeginTime = startBeginTime.plusMinutes(15);
        }

        while(startEndTime.isBefore(endEndTime.plusSeconds(1))){
            addAppointmentEndTimeComboBox.getItems().add(startEndTime);
            startEndTime = startEndTime.plusMinutes(15);
        }
        try {
            ObservableList<Customer> allCustomers = customerQuery.getAllCustomers();
            ObservableList<String> allCustomers2 = FXCollections.observableArrayList();

            for (Customer c: allCustomers) {
                allCustomers2.add(c.toString());
            }
            ObservableList<User> allUsers = userQuery.getAllUsers();
            ObservableList<Contact> allContacts = contactQuery.getAllContacts();

            customerIdComboBox.setItems(allCustomers2);
            userIdComboBox.setItems(allUsers);
            addContactComboBox.setItems(allContacts);

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
