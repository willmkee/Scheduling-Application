package Main;

import DAO.appointmentsQuery;
import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Objects;

/**
 * The type Main.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../View/loginForm.fxml")));
        primaryStage.setTitle("Scheduling Application");
        primaryStage.setScene(new Scene(root, 410, 410));
        primaryStage.show();

    }


    /**
     * Main.
     *
     * @param args the args
     */
    public static void main(String[] args){
        JDBC.openConnection();
        launch(args);
        JDBC.closeConnection();
    }
}
