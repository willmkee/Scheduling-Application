package Controller;

import DAO.appointmentsQuery;
import DAO.customerQuery;
import DAO.userQuery;
import Model.Appointment;
import Model.Contact;
import Model.Customer;
import Model.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import java.util.Objects;
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
    private int selectedIndex;
    private Appointment selectedAppointment;

    public void onAllAppointments(ActionEvent actionEvent) {
    }

    public void onAppointmentsByWeek(ActionEvent actionEvent) {
    }

    public void onAppointmentsByMonth(ActionEvent actionEvent) {
    }

    public void onUpdateAppointment(ActionEvent actionEvent) {
    }

    public void onDeleteAppointment(ActionEvent actionEvent) {
    }

    public void onAddAppointment(ActionEvent actionEvent) {
    }

    public void onMainMenu(ActionEvent actionEvent) throws IOException {
        Parent directory = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/directory.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(directory);
        stage.setScene(scene);
        stage.show();
    }

    /*public void populateAppointment(int selectedIndex, appointments selectedAppointment) {
        this.selectedIndex = selectedIndex;
        this.selectedAppointment = selectedAppointment;
        appointmentIdTextField.setText(String.valueOf(selectedAppointment.getAppointmentId()));
    }*/

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

            LocalTime startBeginTime = LocalTime.of(8, 0);
            LocalTime endBeginTime = LocalTime.of(21, 45);
            LocalTime startEndTime = LocalTime.of(8, 15);
            LocalTime endEndTime = LocalTime.of(22, 0);

            while(startBeginTime.isBefore(endBeginTime.plusSeconds(1))){
                startTimeComboBox.getItems().add(startBeginTime);
                startBeginTime = startBeginTime.plusMinutes(15);
            }

            while(startEndTime.isBefore(endEndTime.plusSeconds(1))){
                endTimeComboBox.getItems().add(startEndTime);
                startEndTime = startEndTime.plusMinutes(15);
            }
            try {
                ObservableList<Customer> customers = customerQuery.getAllCustomers();
                ObservableList<User> allUsers = userQuery.getAllUsers();

                    appointmentsCustomerIdComboBox.setItems(customers);
                    appointmentUserIdComboBox.setItems(allUsers);

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
                        appointmentUserIdComboBox.setValue(userQuery.getUserById(selectedAppointment.getUserId()));
                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }
                    /*contactComboBox;
                    appointmentsCustomerIdComboBox;
                    startTimeComboBox;
                    endTimeComboBox;
                    appointmentUserIdComboBox;*/
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
