package Controller;

import Model.Appointment;
import Time.Time;
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

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import static DAO.userQuery.passwordVerification;

/**
 * The type Login form controller.
 */
public class loginFormController implements Initializable {
    /**
     * The Login title.
     */
    public Label loginTitle;
    /**
     * The Login username lable.
     */
    public Label loginUsernameLable;
    /**
     * The Login username text field.
     */
    public TextField loginUsernameTextField;
    /**
     * The Login password lable.
     */
    public Label loginPasswordLable;
    /**
     * The Login password text field.
     */
    public TextField loginPasswordTextField;
    /**
     * The Login location label.
     */
    public Label loginLocationLabel;
    /**
     * The Login button.
     */
    public Button loginButton;
    /**
     * The Login exit button.
     */
    public Button loginExitButton;

    /**
     * On login.
     * Logs into app
     * Keeps a log of login attempts
     * Notifies user of upcoming appointments.
     *
     * @param actionEvent the action event
     * @throws IOException  the io exception
     * @throws SQLException the sql exception
     */
    public void onLogin(ActionEvent actionEvent) throws IOException, SQLException {
        String userName = loginUsernameTextField.getText();
        String password = loginPasswordTextField.getText();
        String fileName = "login_activity.txt";
        FileWriter loginLog = new FileWriter(fileName, true);
        PrintWriter logOutput = new PrintWriter(loginLog);
        int validUser = passwordVerification(userName, password);
        ObservableList<Appointment> upcomingAppointments;

        ResourceBundle rb = ResourceBundle.getBundle("language/lang", Locale.getDefault());
        if (validUser != -1) {
            upcomingAppointments = Time.viewUpcomingAppointments();
            if(upcomingAppointments.size() > 0) {
                Appointment appointment = upcomingAppointments.get(0);
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

            logOutput.print(userName + " successfully logged in at: " + Timestamp.valueOf(LocalDateTime.now()) + "\n");
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(rb.getString("error"));
            alert.setHeaderText(rb.getString("error"));
            alert.setContentText(rb.getString("invalid"));

            alert.showAndWait();
            logOutput.print(userName + " failed login attempt at: " + Timestamp.valueOf(LocalDateTime.now()) + "\n");
        }
        logOutput.close();
    }

    /**
     * On exit.
     * Closes application
     *
     * @param actionEvent the action event
     */
    public void onExit(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Initializes language settings.
     * @param url
     * @param resourceBundle
     */
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
