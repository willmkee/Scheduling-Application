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

public class reportsController {
    public Label reportsTitleLabel;
    public Button reportsBackButton;
    public Label typeAndMonthTitleLabel;
    public TableView appointmentsByTypeTableView;
    public TableColumn appointmentsByTypeTypeCol;
    public TableColumn appointmentsByTypeTotalCol;
    public TableView appointmentsByMonthTableView;
    public TableColumn appointmentsByMonthMonthCol;
    public TableColumn appointmentsByMonthTotalCol;
    public Label appointmentsByContactTitleLabel;
    public Label contactComboBoxLabel;
    public ComboBox reportsContactComboBox;
    public TableView appointmentsByContactTableView;
    public TableColumn appointmentsByContactAppointmentIdCol;
    public TableColumn appByContactTitleCol;
    public TableColumn appByContactTypeCol;
    public TableColumn appByContactDescriptionCol;
    public TableColumn appByContactStartDateTimeCol;
    public TableColumn appByContactEndDateTimeCol;
    public TableColumn appByContactCustomerIdCol;
    public Label appByLocationTitleLabel;
    public TableView appByLocationTableView;
    public TableColumn appByLocationLocationCol;
    public TableColumn appByLocationTotalCol;

    public void onBackButton(ActionEvent actionEvent) throws IOException {
        Parent directory = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/directory.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(directory);
        stage.setScene(scene);
        stage.show();
    }
}
