package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class loginFormController implements Initializable {
    public Label loginTitle;
    public Label loginUsernameLable;
    public TextField loginUsernameTextField;
    public Label loginPasswordLable;
    public TextField loginPasswordTextField;
    public Label loginLocationLabel;
    public TextField loginLocationTextField;
    public Button loginButton;
    public Button loginExitButton;

    public void onLogin(ActionEvent actionEvent) throws IOException {
        Parent directory = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/directory.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(directory);
        stage.setScene(scene);
        stage.show();
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

    }
}
