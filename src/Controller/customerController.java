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

public class customerController implements Initializable {
    public Label updateCustomersLabel;
    public TableView<Customer> customersTableView;
    public TableColumn<Object, Object> customerIdTableCol;
    public TableColumn<Object, Object> customerNameTableCol;
    public TableColumn customerAddressTableCol;
    public TableColumn customerPostalCodeTableCol;
    public TableColumn customerPhoneTableCol;
    public TableColumn customerFirstLevelDataTableCol;
    public Label customerIdLabel;
    public TextField customerIdTextField;
    public Label customerNameLabel;
    public TextField customerNameTextField;
    public Label addressLabel;
    public TextField addressTextField;
    public Label postalCodeLabel;
    public TextField postalCodeTextField;
    public Label phoneNumberLabel;
    public TextField phoneNumberTextField;
    public Label countryLabel;
    public ComboBox<Country> countryComboBox;
    public Label stateProvinceLabel;
    public ComboBox<FirstLevelDivision> stateProvinceComboBox;
    public Button saveChangesButton;
    public Button addNewCustomerButton;
    public Button deleteCustomerButton;
    public Button backButton;
    public TableColumn countryTableCol;

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

    public void onAddNewCustomer(ActionEvent actionEvent) throws IOException {
        Parent addCustomer = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/addCustomer.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(addCustomer);
        stage.setScene(scene);
        stage.show();
    }

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
                if (customerIdTextField.getText() != null) {
                    appointmentsQuery.deleteAppointmentsByCustomerId(deletedId);
                    if (customerQuery.deleteCustomerById(deletedId) > 0){
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

    public void onBack(ActionEvent actionEvent) throws IOException {
        Parent directory = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/directory.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(directory);
        stage.setScene(scene);
        stage.show();
    }

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
                    stateProvinceComboBox.setDisable(true);
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

