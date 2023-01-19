package Controller;

import Model.appointments;
import Time.loginTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import static DAO.userQuery.passwordVerification;

public class loginFormController implements Initializable {
    public Label loginTitle;
    public Label loginUsernameLable;
    public TextField loginUsernameTextField;
    public Label loginPasswordLable;
    public TextField loginPasswordTextField;
    public Label loginLocationLabel;
    public Button loginButton;
    public Button loginExitButton;

    public void onLogin(ActionEvent actionEvent) throws IOException, SQLException {
        String userName = loginUsernameTextField.getText();
        String password = loginPasswordTextField.getText();
        int validUser = passwordVerification(userName, password);
        ObservableList<appointments> upcomingAppointments;

        ResourceBundle rb = ResourceBundle.getBundle("language/lang", Locale.getDefault());
        if (validUser != -1) {
            upcomingAppointments = loginTime.viewUpcomingAppointments();
            if(upcomingAppointments.size() > 0) {
                appointments appointment = upcomingAppointments.get(0);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Upcoming Appointment");
                alert.setHeaderText("Appointment within next 15 minutes");
                alert.setContentText("Appointment ID: " + appointment.getAppointmentId() +
                        "\nAppointment Start Time" + appointment.getStartDateTime());

                alert.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No Upcoming Appointments");
                alert.setHeaderText("No Upcoming Appointments");
                alert.setContentText("No appointments within the next 15 minutes");

                alert.showAndWait();
            }
            Parent directory = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/directory.fxml")));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(directory);
            stage.setScene(scene);
            stage.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(rb.getString("error"));
            alert.setHeaderText(rb.getString("error"));
            alert.setContentText(rb.getString("invalid"));

            alert.showAndWait();
        }
    }

    public void onExit(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginLocationLabel.setText(String.valueOf(ZoneId.systemDefault()));
        ResourceBundle rb = ResourceBundle.getBundle("language/lang", Locale.getDefault());
        if(Locale.getDefault().getLanguage().equals("fr")) {
            loginUsernameLable.setText(rb.getString("Username"));
            loginPasswordLable.setText(rb.getString("Password"));
            loginTitle.setText(rb.getString("schedulingLogin"));
            loginButton.setText(rb.getString("login"));
            loginExitButton.setText(rb.getString("exit"));
        }
        else {
            rb = ResourceBundle.getBundle("language/lang", Locale.forLanguageTag("en"));
            loginUsernameLable.setText(rb.getString("Username"));
            loginPasswordLable.setText(rb.getString("Password"));
            loginTitle.setText(rb.getString("schedulingLogin"));
            loginButton.setText(rb.getString("login"));
            loginExitButton.setText(rb.getString("exit"));
        }

    }
}
