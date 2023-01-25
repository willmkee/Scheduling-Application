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

public class addAppointmentController {
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
    public ComboBox addContactComboBox;
    public Label addAppointmentCustomerIdLabel;
    public TextField addAppointmentCustomerIdTextField;
    public Label addAppointmentTypeLabel;
    public TextField addAppointmentTypeTextField;
    public Label addAppointmentStartDateLabel;
    public DatePicker addAppointmentStartDateDatePicker;
    public Label addAppointmentEndDateLabel;
    public DatePicker addAppointmentEndDateDatePicker;
    public Label addAppointmentEndTimeLabel;
    public ComboBox addAppointmentEndTimeComboBox;
    public Label addAppointmentUserIdLabel;
    public TextField addAppointmentUserIdTextField;
    public Button saveAppointmentButton;
    public Button cancelButton;
    public Label addAppointmentStartTimeLabel;
    public ComboBox addAppointmentStartTimeComboBox;
    public ComboBox customerIdComboBox;
    public ComboBox userIdComboBox;

    public void onSaveAppointment(ActionEvent actionEvent) {
    }

    public void onCancel(ActionEvent actionEvent) throws IOException {
        Parent directory = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/directory.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(directory);
        stage.setScene(scene);
        stage.show();
    }
}
