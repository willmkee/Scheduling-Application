package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class addCustomerController {
    public Label addCustomerLabel;
    public Label addCustomerIdLabel;
    public TextField addCustomerIdTextField;
    public Label addCustomerNameLabel;
    public TextField addCustomerNameTextField;
    public Label addAddressLabel;
    public TextField addAddressTextField;
    public Label addPostalCodeLabel;
    public TextField addPostalCodeTextField;
    public Label addPhoneNumberLabel;
    public TextField addPhoneNumberTextField;
    public Label addCountryLabel;
    public ComboBox addCountryComboBox;
    public Label addStateProvinceLabel;
    public ComboBox addStateProvinceComboBox;
    public Button addSaveChangesButton;
    public Button cancelButton;

    public void onAddSaveChanges(ActionEvent actionEvent) {
    }

    public void onCancel(ActionEvent actionEvent) throws IOException {
        Parent directory = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/directory.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(directory);
        stage.setScene(scene);
        stage.show();
    }
}
