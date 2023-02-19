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

/**
 * The type Directory controller.
 */
public class directoryController {
    /**
     * The View update customer button.
     */
    public Button viewUpdateCustomerButton;
    /**
     * The Add customer button.
     */
    public Button addCustomerButton;
    /**
     * The View update appointments button.
     */
    public Button viewUpdateAppointmentsButton;
    /**
     * The Add appointment button.
     */
    public Button addAppointmentButton;
    /**
     * The View reports button.
     */
    public Button viewReportsButton;
    /**
     * The Exit button.
     */
    public Button exitButton;
    /**
     * The Directory label.
     */
    public Label directoryLabel;

    /**
     * On view update.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void onViewUpdate(ActionEvent actionEvent) throws IOException {
        Parent customers = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/customer.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(customers);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * On add customer.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void onAddCustomer(ActionEvent actionEvent) throws IOException {
        Parent addCustomer = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/addCustomer.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(addCustomer);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * On view update appointments.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void onViewUpdateAppointments(ActionEvent actionEvent) throws IOException {
        Parent appointments = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/appointments.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(appointments);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * On add appointment.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void onAddAppointment(ActionEvent actionEvent) throws IOException {
        Parent addAppointment = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/addAppointment.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(addAppointment);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * On view reports.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void onViewReports(ActionEvent actionEvent) throws IOException {
        Parent reports = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/reports.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(reports);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * On exit.
     *
     * @param actionEvent the action event
     */
    public void onExit(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
