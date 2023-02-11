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

public class addAppointmentController implements Initializable {
    public Label addAppointmentLabel;
    public Label addAppointmentIdLabel;
    public TextField addAppointmentIdTextField;
    public Label addAppointmentTitleLabel;
    public TextField addAppointmentTitleTextField;
    public Label addAppointmentDescriptionLabel;
    public TextField addAppointmentDescriptionTextField;
    public Label addAppointmentLocationLabel;
    public TextField addAppointmentLocationTextField;
    public Label addContactLabel;
    public ComboBox<Contact> addContactComboBox;
    public Label addAppointmentCustomerIdLabel;
    public TextField addAppointmentCustomerIdTextField;
    public Label addAppointmentTypeLabel;
    public TextField addAppointmentTypeTextField;
    public Label addAppointmentStartDateLabel;
    public DatePicker addAppointmentStartDateDatePicker;
    public Label addAppointmentEndDateLabel;
    public DatePicker addAppointmentEndDateDatePicker;
    public Label addAppointmentEndTimeLabel;
    public ComboBox<LocalTime> addAppointmentEndTimeComboBox;
    public Label addAppointmentUserIdLabel;
    public TextField addAppointmentUserIdTextField;
    public Button saveAppointmentButton;
    public Button cancelButton;
    public Label addAppointmentStartTimeLabel;
    public ComboBox<LocalTime> addAppointmentStartTimeComboBox;
    public ComboBox<String> customerIdComboBox;
    public ComboBox<User> userIdComboBox;

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

    public void onCancel(ActionEvent actionEvent) throws IOException {
        Parent directory = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/directory.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(directory);
        stage.setScene(scene);
        stage.show();
    }

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
