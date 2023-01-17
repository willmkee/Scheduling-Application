package Controller;

import DAO.appointmentsQuery;
import DAO.customerQuery;
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
    public TableView customersTableView;
    public TableColumn customerIdTableCol;
    public TableColumn customerNameTableCol;
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
    public ComboBox countryComboBox;
    public Label stateProvinceLabel;
    public ComboBox stateProvinceComboBox;
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



        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
    }

