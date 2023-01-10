package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class appointmentsController {
    public Label appointmentsLabel;
    public TableView appointmentsTableView;
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
    public ComboBox contactComboBox;
    public Label typeLabel;
    public TextField typeTextField;
    public Label startDateLabel;
    public DatePicker startDateDatePicker;
    public Label startTimeLabel;
    public ComboBox startTimeComboBox;
    public Label endDateLabel;
    public DatePicker endDateDatePicker;
    public Label endTimeLabel;
    public ComboBox endTimeComboBox;
    public Button updateAppointmentButton;
    public Button deleteAppointmentButton;
    public Button addAppointmentButton;
    public Button mainMenuButton;
    public Label customerIdLabel;
    public TextField customerIdTextField;
    public Label userIdLabel;
    public TextField userIdTextField;

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
}
