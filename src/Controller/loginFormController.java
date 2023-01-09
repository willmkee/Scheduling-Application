package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class loginFormController {
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
        /*Parent directory = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/directory.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(directory);
        stage.setScene(scene);
        stage.show();*/
    }

    public void onExit(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
