package Main;

import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../View/loginForm.fxml")));
        primaryStage.setTitle("Scheduling Application");
        primaryStage.setScene(new Scene(root, 410, 410));
        primaryStage.show();

    }


    public static void main(String[] args) {
        ResourceBundle rb = ResourceBundle.getBundle("language/lang", Locale.getDefault());
        if(Locale.getDefault().getLanguage().equals("fr")) {
            System.out.println(rb.getString("Password"));
        }
        JDBC.openConnection();
        launch(args);
        JDBC.closeConnection();
    }
}
