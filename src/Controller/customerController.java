package Controller;

import DAO.appointmentsQuery;
import DAO.countryAccess;
import DAO.customerQuery;
import DAO.firstLevelDivisionAccess;
import Model.Country;
import Model.Customer;
import Model.FirstLevelDivision;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The type Customer controller.
 */
public class customerController implements Initializable {
    /**
     * The Update customers label.
     */
    public Label updateCustomersLabel;
    /**
     * The Customers table view.
     */
    public TableView<Customer> customersTableView;
    /**
     * The Customer id table col.
     */
    public TableColumn<Object, Object> customerIdTableCol;
    /**
     * The Customer name table col.
     */
    public TableColumn<Object, Object> customerNameTableCol;
    /**
     * The Customer address table col.
     */
    public TableColumn customerAddressTableCol;
    /**
     * The Customer postal code table col.
     */
    public TableColumn customerPostalCodeTableCol;
    /**
     * The Customer phone table col.
     */
    public TableColumn customerPhoneTableCol;
    /**
     * The Customer first level data table col.
     */
    public TableColumn customerFirstLevelDataTableCol;
    /**
     * The Customer id label.
     */
    public Label customerIdLabel;
    /**
     * The Customer id text field.
     */
    public TextField customerIdTextField;
    /**
     * The Customer name label.
     */
    public Label customerNameLabel;
    /**
     * The Customer name text field.
     */
    public TextField customerNameTextField;
    /**
     * The Address label.
     */
    public Label addressLabel;
    /**
     * The Address text field.
     */
    public TextField addressTextField;
    /**
     * The Postal code label.
     */
    public Label postalCodeLabel;
    /**
     * The Postal code text field.
     */
    public TextField postalCodeTextField;
    /**
     * The Phone number label.
     */
    public Label phoneNumberLabel;
    /**
     * The Phone number text field.
     */
    public TextField phoneNumberTextField;
    /**
     * The Country label.
     */
    public Label countryLabel;
    /**
     * The Country combo box.
     */
    public ComboBox<Country> countryComboBox;
    /**
     * The State province label.
     */
    public Label stateProvinceLabel;
    /**
     * The State province combo box.
     */
    public ComboBox<FirstLevelDivision> stateProvinceComboBox;
    /**
     * The Save changes button.
     */
    public Button saveChangesButton;
    /**
     * The Add new customer button.
     */
    public Button addNewCustomerButton;
    /**
     * The Delete customer button.
     */
    public Button deleteCustomerButton;
    /**
     * The Back button.
     */
    public Button backButton;
    /**
     * The Country table col.
     */
    public TableColumn countryTableCol;

    /**
     * On save changes.
     *
     * @param actionEvent the action event
     * @throws SQLException the sql exception
     */
    public void onSaveChanges(ActionEvent actionEvent) throws SQLException {
        if((customerNameTextField.getText().length() > 0) && (addressTextField.getText().length() > 0) && (postalCodeTextField.getText().length() > 0) && (phoneNumberTextField.getText().length() > 0) && (countryComboBox.getValue() != null) && (stateProvinceComboBox != null)) {
            String customerName = customerNameTextField.getText();
            String customerAddress = addressTextField.getText();
            String postalCode = postalCodeTextField.getText();
            String phone = phoneNumberTextField.getText();
            int customerId = Integer.parseInt(customerIdTextField.getText());
            FirstLevelDivision stateProvince = stateProvinceComboBox.getValue();
            int stateProvinceId = stateProvince.getDivisionId();
            int updateSuccess = customerQuery.updateCustomer(customerId, customerName, customerAddress, postalCode, phone, stateProvinceId);

            if (updateSuccess > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Customer successfully updated!");

                alert.showAndWait();
                customersTableView.setItems(customerQuery.getAllCustomers());
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Failure");
                alert.setContentText("Customer failed to update.");

                alert.showAndWait();
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
     * On add new customer.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void onAddNewCustomer(ActionEvent actionEvent) throws IOException {
        Parent addCustomer = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/addCustomer.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(addCustomer);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * On delete customer.
     *
     * @param actionEvent the action event
     * @throws SQLException the sql exception
     */
    public void onDeleteCustomer(ActionEvent actionEvent) throws SQLException {
        int deletedId;
        if (customerIdTextField.getText().length() > 0) {
            deletedId = Integer.parseInt(customerIdTextField.getText());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Customer?");
            alert.setHeaderText("Customer and all associated appointments will be deleted.");
            alert.setContentText("Are you ok with this?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                if (customerIdTextField.getText().length() > 0) {
                    appointmentsQuery.deleteAppointmentsByCustomerId(deletedId);
                    int deletedCustomers = customerQuery.deleteCustomerById(deletedId);
                    if (deletedCustomers > 0){
                        Alert customerDeletedAlert = new Alert(Alert.AlertType.INFORMATION);
                        customerDeletedAlert.setTitle("Customer Deleted");
                        customerDeletedAlert.setHeaderText("Success!");
                        customerDeletedAlert.setContentText("Customer and appointments successfully deleted.");
                        customerDeletedAlert.showAndWait();

                        customerIdTextField.setText("");
                        customerNameTextField.setText(null);
                        addressTextField.setText(null);
                        postalCodeTextField.setText(null);
                        phoneNumberTextField.setText(null);
                        countryComboBox.setValue(null);
                        stateProvinceComboBox.setValue(null);
                        customersTableView.setItems(customerQuery.getAllCustomers());
                    }
                }
            } } else {
            Alert noIdAlert = new Alert(Alert.AlertType.ERROR);
            noIdAlert.setTitle("Customer Not Deleted");
            noIdAlert.setHeaderText("No Customer Selected");
            noIdAlert.setContentText("Please select a customer to delete.");

            noIdAlert.showAndWait();
        }
    }

    /**
     * On back.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void onBack(ActionEvent actionEvent) throws IOException {
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
            customersTableView.setItems(customerQuery.getAllCustomers());
            customerIdTableCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            customerNameTableCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            customerAddressTableCol.setCellValueFactory(new PropertyValueFactory<>("address"));
            customerPostalCodeTableCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
            customerPhoneTableCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            customerFirstLevelDataTableCol.setCellValueFactory(new PropertyValueFactory<>("stateProvince"));
            countryTableCol.setCellValueFactory(new PropertyValueFactory<>("country"));

            try {
                countryComboBox.setItems(countryAccess.getAllCountries());
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }


            countryComboBox.valueProperty().addListener((obs, oldValue, newValue) -> {
                if (newValue == null) {
                    stateProvinceComboBox.getItems().clear();
                    //stateProvinceComboBox.setDisable(true);
                }
                else {
                    try {
                        stateProvinceComboBox.setItems(firstLevelDivisionAccess.getStatesProvincesbyCountryName(countryComboBox.getValue().getCountryName()));
                    } catch (SQLException sqlException) {
                        sqlException.printStackTrace();
                    }
                }
            });



            customersTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observableValue, Object o, Object t1) {
                    Customer selectedCustomer = customersTableView.getSelectionModel().getSelectedItem();
                    if(selectedCustomer != null) {
                    customerIdTextField.setText(String.valueOf(selectedCustomer.getCustomerId()));
                    customerNameTextField.setText(selectedCustomer.getCustomerName());
                    addressTextField.setText(selectedCustomer.getAddress());
                    postalCodeTextField.setText(selectedCustomer.getPostalCode());
                    phoneNumberTextField.setText(selectedCustomer.getPhoneNumber());
                    try {
                        countryComboBox.setValue(countryAccess.getCountryByName(selectedCustomer.getCountry()));
                    } catch (SQLException sqlException) {
                        sqlException.printStackTrace();
                    }
                    try {
                        stateProvinceComboBox.setValue(firstLevelDivisionAccess.getStateProvincesByName(selectedCustomer.getStateProvince()));
                    } catch (SQLException sqlException) {
                        sqlException.printStackTrace();
                    }
                }
                }
            });


        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

}

