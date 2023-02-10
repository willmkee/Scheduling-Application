package Controller;

import DAO.appointmentsQuery;
import DAO.contactQuery;
import DAO.customerQuery;
import DAO.userQuery;
import Model.Appointment;
import Model.Contact;
import Model.Customer;
import Model.User;
import Time.loginTime;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class appointmentsController implements Initializable {
    public Label appointmentsLabel;
    public TableView<Appointment> appointmentsTableView;
    public TableColumn appointmentTableCol;
    public TableColumn titleTableCol;
    public TableColumn descriptionTableCol;
    public TableColumn locationTableCol;
    public TableColumn contactTableCol;
    public TableColumn typeTableCol;
    public TableColumn startDatetimeTableCol;
    public TableColumn endDatetimeTableCol;
    public TableColumn customerIDTableCol;
    public TableColumn userIdTableCol;
    public RadioButton allAppointmentsRadioButton;
    public ToggleGroup appointmentRadioButtons;
    public RadioButton appointmentsByWeekRadioButton;
    public RadioButton appointmentsByMonthRadioButton;
    public Label appointmentIdLabel;
    public TextField appointmentIdTextField;
    public Label appointmentTitleLabel;
    public TextField appointmentTitleTextField;
    public Label appointmentDescriptionLabel;
    public TextField appointmentDescriptionTextField;
    public Label appointmentLocationLabel;
    public TextField appointmentLocationTextField;
    public Label contactLabel;
    public ComboBox<Contact> contactComboBox;
    public Label typeLabel;
    public TextField typeTextField;
    public Label startDateLabel;
    public DatePicker startDateDatePicker;
    public Label startTimeLabel;
    public ComboBox<LocalTime> startTimeComboBox;
    public Label endDateLabel;
    public DatePicker endDateDatePicker;
    public Label endTimeLabel;
    public ComboBox<LocalTime> endTimeComboBox;
    public Button updateAppointmentButton;
    public Button deleteAppointmentButton;
    public Button addAppointmentButton;
    public Button mainMenuButton;
    public Label customerIdLabel;
    public TextField customerIdTextField;
    public Label userIdLabel;
    public TextField userIdTextField;
    public ComboBox<User> appointmentUserIdComboBox;
    public ComboBox appointmentsCustomerIdComboBox;
    //public ComboBox<Customer> appointmentsCustomerIDComboBox;
    private int selectedIndex;
    private Appointment selectedAppointment;

    public void onAllAppointments(ActionEvent actionEvent) throws SQLException {
        appointmentsTableView.setItems(appointmentsQuery.getAllAppointments());
        appointmentTableCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleTableCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionTableCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationTableCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactTableCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        typeTableCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDatetimeTableCol.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        endDatetimeTableCol.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        customerIDTableCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userIdTableCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
    }

    public void onAppointmentsByWeek(ActionEvent actionEvent) {
    }

    public void onAppointmentsByMonth(ActionEvent actionEvent) {
    }

    public void onUpdateAppointment(ActionEvent actionEvent) throws SQLException {
        if (appointmentTitleTextField.getText().length() > 0 && appointmentIdTextField.getText().length() > 0 && appointmentDescriptionTextField.getText().length() >0 && appointmentLocationTextField.getText().length() > 0 && contactComboBox.getValue() != null && appointmentsCustomerIdComboBox.getValue() != null && typeTextField.getText().length() > 0 && endTimeComboBox.getValue() != null && startDateDatePicker != null && startTimeComboBox.getValue() != null && appointmentUserIdComboBox.getValue() != null) {
            int id = Integer.parseInt(appointmentIdTextField.getText());
            LocalDate startDate = startDateDatePicker.getValue();
            LocalTime startTime = startTimeComboBox.getValue();
            String utcStart = loginTime.convertToUtc(startDate, startTime);
            LocalDate endDate = endDateDatePicker.getValue();
            LocalTime endTime = endTimeComboBox.getValue();
            String utcEnd = loginTime.convertToUtc(endDate, endTime);
            String title = appointmentTitleTextField.getText();
            String description = appointmentDescriptionTextField.getText();
            String location = appointmentLocationTextField.getText();
            int contactId = contactComboBox.getValue().getContactId();
            int customerId = Integer.parseInt(String.valueOf(appointmentsCustomerIdComboBox.getValue()));
            String type = typeTextField.getText();
            int userId = Integer.parseInt(String.valueOf(appointmentUserIdComboBox.getValue()));
            LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
            LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);
            if (startDateTime.isAfter(endDateTime)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Time Error");
                alert.setContentText("Start Date and Time must be before End Date and Time");

                alert.showAndWait();
            }
            else if(!loginTime.appointmentUpdateOverlap(id, customerId, startDateTime, endDateTime)){
                int i = appointmentsQuery.updateAppointment(id, utcStart, utcEnd, title, description, location, contactId, customerId, userId, type);
                if (i > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Appointment successfully updated!");

                    alert.showAndWait();
                    appointmentsTableView.setItems(appointmentsQuery.getAllAppointments());
                    appointmentIdTextField.setText("");
                    appointmentTitleTextField.setText("");
                    appointmentDescriptionTextField.setText("");
                    appointmentLocationTextField.setText("");
                    contactComboBox.setValue(null);
                    appointmentsCustomerIdComboBox.setValue(null);
                    typeTextField.setText("");
                    startDateDatePicker.setValue(null);
                    endDateDatePicker.setValue(null);
                    startTimeComboBox.setValue(null);
                    endTimeComboBox.setValue(null);
                    appointmentUserIdComboBox.setValue(null);

                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText("Failure");
                    alert.setContentText("Appointment failed to update.");

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
        appointmentsTableView.setItems(appointmentsQuery.getAllAppointments());
    }

    public void onDeleteAppointment(ActionEvent actionEvent) throws SQLException {
        int deletedId;
        if (appointmentIdTextField.getText().length() > 0) {
            deletedId = Integer.parseInt(appointmentIdTextField.getText());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Appointment?");
            alert.setHeaderText("Appointment will be deleted.");
            alert.setContentText("Are you ok with this?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                if (appointmentIdTextField.getText() != null) {
                    if(appointmentsQuery.deleteAppointmentById(deletedId) > 0){
                        Alert deleteSuccessfulAlert = new Alert(Alert.AlertType.ERROR);
                        deleteSuccessfulAlert.setTitle("Appointment Deleted");
                        deleteSuccessfulAlert.setHeaderText("Success!");
                        deleteSuccessfulAlert.setContentText("Selected Appointment Successfully Deleted");

                        deleteSuccessfulAlert.showAndWait();

                        appointmentTitleTextField.setText(null);
                        appointmentIdTextField.setText("");
                        appointmentDescriptionTextField.setText(null);
                        appointmentLocationTextField.setText(null);
                        appointmentsCustomerIdComboBox.setValue(null);
                        appointmentUserIdComboBox.setValue(null);
                        contactComboBox.setValue(null);
                        startTimeComboBox.setValue(null);
                        endTimeComboBox.setValue(null);
                        typeTextField.setText(null);
                        startDateDatePicker.setValue(null);
                        endDateDatePicker.setValue(null);
                        appointmentsTableView.setItems(appointmentsQuery.getAllAppointments());



                    }
                }
        } } else {
            Alert noIdAlert = new Alert(Alert.AlertType.ERROR);
            noIdAlert.setTitle("Appointment Not Deleted");
            noIdAlert.setHeaderText("No Appointment Selected");
            noIdAlert.setContentText("Please select an appointment to delete.");

            noIdAlert.showAndWait();
        }

    }

    public void onAddAppointment(ActionEvent actionEvent) throws IOException {
        Parent addCustomer = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/addAppointment.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(addCustomer);
        stage.setScene(scene);
        stage.show();
    }

    public void onMainMenu(ActionEvent actionEvent) throws IOException {
        Parent directory = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/directory.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(directory);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            appointmentsTableView.setItems(appointmentsQuery.getAllAppointments());
            appointmentTableCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            titleTableCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            descriptionTableCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            locationTableCol.setCellValueFactory(new PropertyValueFactory<>("location"));
            contactTableCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
            typeTableCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            startDatetimeTableCol.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
            endDatetimeTableCol.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
            customerIDTableCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            userIdTableCol.setCellValueFactory(new PropertyValueFactory<>("userId"));


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
                startTimeComboBox.getItems().add(startBeginTime);
                startBeginTime = startBeginTime.plusMinutes(15);
            }

            while(startEndTime.isBefore(endEndTime.plusSeconds(1))){
                endTimeComboBox.getItems().add(startEndTime);
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

                    appointmentsCustomerIdComboBox.setItems(allCustomers2);
                    appointmentUserIdComboBox.setItems(allUsers);
                    contactComboBox.setItems(allContacts);

            } catch (SQLException exception) {
                exception.printStackTrace();
            }

            appointmentsTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observableValue, Object o, Object t1) {

                    Appointment selectedAppointment = appointmentsTableView.getSelectionModel().getSelectedItem();
                    if (selectedAppointment != null) {
                        appointmentTitleTextField.setText(selectedAppointment.getTitle());
                        appointmentIdTextField.setText(String.valueOf(selectedAppointment.getAppointmentId()));
                        appointmentDescriptionTextField.setText(selectedAppointment.getDescription());
                        appointmentLocationTextField.setText(selectedAppointment.getLocation());
                        try {
                            appointmentsCustomerIdComboBox.setValue(customerQuery.getCustomerById(selectedAppointment.getCustomerId()).toString());
                            //System.out.println(appointmentsCustomerIdComboBox.getValue());
                            appointmentUserIdComboBox.setValue(userQuery.getUserById(selectedAppointment.getUserId()));
                            contactComboBox.setValue(contactQuery.getContactById(selectedAppointment.getContactId()));
                        } catch (SQLException exception) {
                            exception.printStackTrace();
                        }
                        startTimeComboBox.setValue(selectedAppointment.getStartDateTime().toLocalTime());
                        endTimeComboBox.setValue(selectedAppointment.getEndDateTime().toLocalTime());
                        typeTextField.setText(selectedAppointment.getType());
                        startDateDatePicker.setValue(selectedAppointment.getStartDateTime().toLocalDate());
                        endDateDatePicker.setValue(selectedAppointment.getEndDateTime().toLocalDate());

                    }
                }
            });

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
