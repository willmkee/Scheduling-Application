package Controller;

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

    public void onSaveChanges(ActionEvent actionEvent) {
    }

    public void onAddNewCustomer(ActionEvent actionEvent) {
    }

    public void onDeleteCustomer(ActionEvent actionEvent) {
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
                        /*try {
                            if (newValue == "U.S") {
                                ObservableList<FirstLevelDivision> states = firstLevelDivisionAccess.getAllStates();
                                stateProvinceComboBox.getItems().setAll(states);
                                stateProvinceComboBox.setDisable(false);
                            }
                        } catch (SQLException sqlException) {
                            sqlException.printStackTrace();
                        }*/


                }
            });



            customersTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observableValue, Object o, Object t1) {
                    Customer selectedCustomer = customersTableView.getSelectionModel().getSelectedItem();
                    customerIdTextField.setText(String.valueOf(selectedCustomer.getCustomerId()));
                    customerNameTextField.setText(selectedCustomer.getCustomerName());
                    addressTextField.setText(selectedCustomer.getAddress());
                    postalCodeTextField.setText(selectedCustomer.getPostalCode());
                    phoneNumberTextField.setText(selectedCustomer.getPhoneNumber());

                    /*
                    countryComboBox;
                    stateProvinceComboBox;

                     */

                }
            });


        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

}

