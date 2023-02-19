package Controller;

import DAO.appointmentsQuery;
import DAO.contactQuery;
import DAO.customerQuery;
import DAO.userQuery;
import Model.Appointment;
import Model.Contact;
import Model.Customer;
import Model.User;
import Time.Time;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import java.time.*;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The type Appointments controller.
 */
public class appointmentsController implements Initializable {
    /**
     * The Appointments label.
     */
    public Label appointmentsLabel;
    /**
     * The Appointments table view.
     */
    public TableView<Appointment> appointmentsTableView;
    /**
     * The Appointment table col.
     */
    public TableColumn appointmentTableCol;
    /**
     * The Title table col.
     */
    public TableColumn titleTableCol;
    /**
     * The Description table col.
     */
    public TableColumn descriptionTableCol;
    /**
     * The Location table col.
     */
    public TableColumn locationTableCol;
    /**
     * The Contact table col.
     */
    public TableColumn contactTableCol;
    /**
     * The Type table col.
     */
    public TableColumn typeTableCol;
    /**
     * The Start datetime table col.
     */
    public TableColumn startDatetimeTableCol;
    /**
     * The End datetime table col.
     */
    public TableColumn endDatetimeTableCol;
    /**
     * The Customer id table col.
     */
    public TableColumn customerIDTableCol;
    /**
     * The User id table col.
     */
    public TableColumn userIdTableCol;
    /**
     * The All appointments radio button.
     */
    public RadioButton allAppointmentsRadioButton;
    /**
     * The Appointment radio buttons.
     */
    public ToggleGroup appointmentRadioButtons;
    /**
     * The Appointments by week radio button.
     */
    public RadioButton appointmentsByWeekRadioButton;
    /**
     * The Appointments by month radio button.
     */
    public RadioButton appointmentsByMonthRadioButton;
    /**
     * The Appointment id label.
     */
    public Label appointmentIdLabel;
    /**
     * The Appointment id text field.
     */
    public TextField appointmentIdTextField;
    /**
     * The Appointment title label.
     */
    public Label appointmentTitleLabel;
    /**
     * The Appointment title text field.
     */
    public TextField appointmentTitleTextField;
    /**
     * The Appointment description label.
     */
    public Label appointmentDescriptionLabel;
    /**
     * The Appointment description text field.
     */
    public TextField appointmentDescriptionTextField;
    /**
     * The Appointment location label.
     */
    public Label appointmentLocationLabel;
    /**
     * The Appointment location text field.
     */
    public TextField appointmentLocationTextField;
    /**
     * The Contact label.
     */
    public Label contactLabel;
    /**
     * The Contact combo box.
     */
    public ComboBox<Contact> contactComboBox;
    /**
     * The Type label.
     */
    public Label typeLabel;
    /**
     * The Type text field.
     */
    public TextField typeTextField;
    /**
     * The Start date label.
     */
    public Label startDateLabel;
    /**
     * The Start date date picker.
     */
    public DatePicker startDateDatePicker;
    /**
     * The Start time label.
     */
    public Label startTimeLabel;
    /**
     * The Start time combo box.
     */
    public ComboBox<LocalTime> startTimeComboBox;
    /**
     * The End date label.
     */
    public Label endDateLabel;
    /**
     * The End date date picker.
     */
    public DatePicker endDateDatePicker;
    /**
     * The End time label.
     */
    public Label endTimeLabel;
    /**
     * The End time combo box.
     */
    public ComboBox<LocalTime> endTimeComboBox;
    /**
     * The Update appointment button.
     */
    public Button updateAppointmentButton;
    /**
     * The Delete appointment button.
     */
    public Button deleteAppointmentButton;
    /**
     * The Add appointment button.
     */
    public Button addAppointmentButton;
    /**
     * The Main menu button.
     */
    public Button mainMenuButton;
    /**
     * The Customer id label.
     */
    public Label customerIdLabel;
    /**
     * The Customer id text field.
     */
    public TextField customerIdTextField;
    /**
     * The User id label.
     */
    public Label userIdLabel;
    /**
     * The User id text field.
     */
    public TextField userIdTextField;
    /**
     * The Appointment user id combo box.
     */
    public ComboBox<User> appointmentUserIdComboBox;
    /**
     * The Appointments customer id combo box.
     */
    public ComboBox appointmentsCustomerIdComboBox;
    //public ComboBox<Customer> appointmentsCustomerIDComboBox;
    private int selectedIndex;
    private Appointment selectedAppointment;

    /**
     * On all appointments.
     * Displays all appointments in table
     *
     * @param actionEvent the action event
     * @throws SQLException the sql exception
     */
    public void onAllAppointments(ActionEvent actionEvent) throws SQLException {
        appointmentsTableView.setItems(appointmentsQuery.getAllAppointments());
    }

    /**
     * On appointments by week.
     * Displays appointments for the upcoming week in table.
     *
     * Lambda function is used in lieu of doing a separate sql query.
     * Lambda function uses the getAllAppointments() method and then fills each row based on start time.
     *
     *
     * @param actionEvent the action event
     * @throws SQLException the sql exception
     */
    public void onAppointmentsByWeek(ActionEvent actionEvent) throws SQLException {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nowPlus7 = now.plusDays(7);
        FilteredList<Appointment> filteredData = new FilteredList<>(appointmentsQuery.getAllAppointments());
        filteredData.setPredicate(row -> {

            LocalDateTime rowDate = row.getStartDateTime();

            return rowDate.isAfter(now) && rowDate.isBefore(nowPlus7);
        });
        appointmentsTableView.setItems(filteredData);
    }

    /**
     * On appointments by month.
     * Shows appointments for the upcoming month in the appointments table.
     *
     *
     * @param actionEvent the action event
     * @throws SQLException the sql exception
     */
    public void onAppointmentsByMonth(ActionEvent actionEvent) throws SQLException {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nowPlusMonth = now.plusMonths(1);
        FilteredList<Appointment> filteredData = new FilteredList<>(appointmentsQuery.getAllAppointments());
        filteredData.setPredicate(row -> {

            LocalDateTime rowDate = row.getStartDateTime();

            return rowDate.isAfter(now) && rowDate.isBefore(nowPlusMonth);
        });
        appointmentsTableView.setItems(filteredData);
    }

    /**
     * On update appointment.
     * If all fields are filled in and appointment times do not overlap this will update the selected appointment.
     *
     *
     * @param actionEvent the action event
     * @throws SQLException the sql exception
     */
    public void onUpdateAppointment(ActionEvent actionEvent) throws SQLException {
        if (appointmentIdTextField.getText().length() > 0 &&
                appointmentTitleTextField.getText().length() > 0 &&
                appointmentDescriptionTextField.getText().length() > 0 &&
                appointmentLocationTextField.getText().length() > 0 &&
                contactComboBox.getValue() != null &&
                appointmentsCustomerIdComboBox.getValue() != null &&
                typeTextField.getText().length() > 0 &&
                endTimeComboBox.getValue() != null &&
                endDateDatePicker.getValue() != null &&
                startDateDatePicker.getValue() != null &&
                startTimeComboBox.getValue() != null &&
                appointmentUserIdComboBox.getValue() != null) {
            int id = Integer.parseInt(appointmentIdTextField.getText());
            LocalDate startDate = startDateDatePicker.getValue();
            LocalTime startTime = startTimeComboBox.getValue();
            String utcStart = Time.convertToUtc(startDate, startTime);
            LocalDate endDate = endDateDatePicker.getValue();
            LocalTime endTime = endTimeComboBox.getValue();
            String utcEnd = Time.convertToUtc(endDate, endTime);
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
            else if(!Time.appointmentUpdateOverlap(id, customerId, startDateTime, endDateTime)){
                int i = appointmentsQuery.updateAppointment(id, utcStart, utcEnd, title, description, location, contactId, customerId, userId, type);
                if (i > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Appointment successfully updated!");

                    alert.showAndWait();
                    allAppointmentsRadioButton.fire();
                    allAppointmentsRadioButton.fireEvent(actionEvent);
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
        allAppointmentsRadioButton.fire();
        allAppointmentsRadioButton.fireEvent(actionEvent);
    }

    /**
     * On delete appointment.
     * Deletes selected appointment
     *
     * @param actionEvent the action event
     * @throws SQLException the sql exception
     */
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
                        //appointmentsTableView.setItems(appointmentsQuery.getAllAppointments());
                        allAppointmentsRadioButton.fire();
                        allAppointmentsRadioButton.fireEvent(actionEvent);



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

    /**
     * On add appointment.
     * Opens the add appointment screen
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void onAddAppointment(ActionEvent actionEvent) throws IOException {
        Parent addCustomer = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/addAppointment.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(addCustomer);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * On main menu.
     * Returns to the directory screen
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void onMainMenu(ActionEvent actionEvent) throws IOException {
        Parent directory = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/directory.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(directory);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Initializes the appointment table and comboboxes
     * @param url
     * @param resourceBundle
     */
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
