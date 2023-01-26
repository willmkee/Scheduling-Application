package Controller;

import DAO.countryAccess;
import DAO.customerQuery;
import DAO.firstLevelDivisionAccess;
import Model.Country;
import Model.FirstLevelDivision;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class addCustomerController implements Initializable {
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
    public ComboBox<Country> addCountryComboBox;
    public Label addStateProvinceLabel;
    public ComboBox<FirstLevelDivision> addStateProvinceComboBox;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            int newCustomerId = customerQuery.generateNewCustomerId();
            addCustomerIdTextField.setText(String.valueOf(newCustomerId));
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        try {
            addCountryComboBox.setItems(countryAccess.getAllCountries());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }


        addCountryComboBox.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue == null) {
                addStateProvinceComboBox.getItems().clear();
                addStateProvinceComboBox.setDisable(true);
            }
            else {
                try {
                    addStateProvinceComboBox.setItems(firstLevelDivisionAccess.getStatesProvincesbyCountryName(addCountryComboBox.getValue().getCountryName()));
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
            }
        });
    }
}
