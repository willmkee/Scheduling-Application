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

/**
 * The type Add customer controller.
 */
public class addCustomerController implements Initializable {
    /**
     * The Add customer label.
     */
    public Label addCustomerLabel;
    /**
     * The Add customer id label.
     */
    public Label addCustomerIdLabel;
    /**
     * The Add customer id text field.
     */
    public TextField addCustomerIdTextField;
    /**
     * The Add customer name label.
     */
    public Label addCustomerNameLabel;
    /**
     * The Add customer name text field.
     */
    public TextField addCustomerNameTextField;
    /**
     * The Add address label.
     */
    public Label addAddressLabel;
    /**
     * The Add address text field.
     */
    public TextField addAddressTextField;
    /**
     * The Add postal code label.
     */
    public Label addPostalCodeLabel;
    /**
     * The Add postal code text field.
     */
    public TextField addPostalCodeTextField;
    /**
     * The Add phone number label.
     */
    public Label addPhoneNumberLabel;
    /**
     * The Add phone number text field.
     */
    public TextField addPhoneNumberTextField;
    /**
     * The Add country label.
     */
    public Label addCountryLabel;
    /**
     * The Add country combo box.
     */
    public ComboBox<Country> addCountryComboBox;
    /**
     * The Add state province label.
     */
    public Label addStateProvinceLabel;
    /**
     * The Add state province combo box.
     */
    public ComboBox<FirstLevelDivision> addStateProvinceComboBox;
    /**
     * The Add save changes button.
     */
    public Button addSaveChangesButton;
    /**
     * The Cancel button.
     */
    public Button cancelButton;

    /**
     * On add save changes.
     *
     * @param actionEvent the action event
     * @throws SQLException the sql exception
     * @throws IOException  the io exception
     */
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

    /**
     * On cancel.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void onCancel(ActionEvent actionEvent) throws IOException {
        Parent directory = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/directory.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(directory);
        stage.setScene(scene);
        stage.show();
    }

    /**
     *
     * @param url
     * @param resourceBundle
     */
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
