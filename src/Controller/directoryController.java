package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class directoryController {
    public Button viewUpdateCustomerButton;
    public Button addCustomerButton;
    public Button viewUpdateAppointmentsButton;
    public Button addAppointmentButton;
    public Button viewReportsButton;
    public Button exitButton;
    public Label directoryLabel;

    public void onViewUpdate(ActionEvent actionEvent) throws IOException {
        Parent customers = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/customer.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(customers);
        stage.setScene(scene);
        stage.show();
    }

    public void onAddCustomer(ActionEvent actionEvent) throws IOException {
        Parent addCustomer = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/addCustomer.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(addCustomer);
        stage.setScene(scene);
        stage.show();
    }

    public void onViewUpdateAppointments(ActionEvent actionEvent) throws IOException {
        Parent appointments = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/appointments.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(appointments);
        stage.setScene(scene);
        stage.show();
    }

    public void onAddAppointment(ActionEvent actionEvent) throws IOException {
        Parent addAppointment = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/addAppointment.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(addAppointment);
        stage.setScene(scene);
        stage.show();
    }

    public void onViewReports(ActionEvent actionEvent) throws IOException {
        Parent reports = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/reports.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(reports);
        stage.setScene(scene);
        stage.show();
    }

    public void onExit(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
