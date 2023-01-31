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
import javafx.scene.control.*;
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

    public void onAddSaveChanges(ActionEvent actionEvent) throws SQLException, IOException {
        if((addCustomerNameTextField.getText().length() > 0) && (addAddressTextField.getText().length() > 0) && (addPostalCodeTextField.getText().length() > 0) && (addPhoneNumberTextField.getText().length() > 0) && (addCountryComboBox.getValue() != null) && (addStateProvinceComboBox != null)) {
            String customerName = addCustomerNameTextField.getText();
            String address = addAddressTextField.getText();
            String postalCode = addPostalCodeTextField.getText();
            FirstLevelDivision state = addStateProvinceComboBox.getValue();
            int stateId = state.getDivisionId();
            String phone = addPhoneNumberTextField.getText();

            int addCustomer = customerQuery.addNewCustomer(customerName, address, postalCode, phone, stateId);

            if (addCustomer > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("New customer successfully added!");

                alert.showAndWait();

                Parent directory = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/directory.fxml")));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(directory);
                stage.setScene(scene);
                stage.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Empty Fields");
            alert.setContentText("Please fill in all customer information.");

            alert.showAndWait();
        }
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
