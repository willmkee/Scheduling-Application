package Controller;

import DAO.appointmentsQuery;
import DAO.contactQuery;
import DAO.customerQuery;
import DAO.userQuery;
import Model.Appointment;
import Model.Contact;
import Model.Customer;
import Model.User;
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
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
    public ComboBox<Customer> appointmentsCustomerIdComboBox;
    public ComboBox<Customer> appointmentsCustomerIDComboBox;
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

    public void onUpdateAppointment(ActionEvent actionEvent) {
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

                        try {
                            appointmentsTableView.setItems(appointmentsQuery.getAllAppointments());



                        } catch (SQLException Exception) {
                            Exception.printStackTrace();
                        }
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
        Parent addCustomer = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/addCustomer.fxml")));
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

            LocalTime startBeginTime = LocalTime.of(1, 0);
            LocalTime endBeginTime = LocalTime.of(14, 45);
            LocalTime startEndTime = LocalTime.of(1, 15);
            LocalTime endEndTime = LocalTime.of(15, 0);

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
                System.out.println(allCustomers);
                ObservableList<User> allUsers = userQuery.getAllUsers();
                System.out.println(allUsers);
                ObservableList<Contact> allContacts = contactQuery.getAllContacts();

                    appointmentsCustomerIdComboBox.setItems(allCustomers);
                    appointmentUserIdComboBox.setItems(allUsers);
                    contactComboBox.setItems(allContacts);

            } catch (SQLException exception) {
                exception.printStackTrace();
            }

            appointmentsTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observableValue, Object o, Object t1) {

                    Appointment selectedAppointment = appointmentsTableView.getSelectionModel().getSelectedItem();
                    appointmentTitleTextField.setText(selectedAppointment.getTitle());
                    appointmentIdTextField.setText(String.valueOf(selectedAppointment.getAppointmentId()));
                    appointmentDescriptionTextField.setText(selectedAppointment.getDescription());
                    appointmentLocationTextField.setText(selectedAppointment.getLocation());
                    try {
                        appointmentsCustomerIdComboBox.setValue(customerQuery.getCustomerById(selectedAppointment.getCustomerId()));
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
            });

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
