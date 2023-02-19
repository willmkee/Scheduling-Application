package Controller;

import DAO.appointmentsQuery;
import DAO.contactQuery;
import DAO.firstLevelDivisionAccess;
import Model.Appointment;
import Model.Contact;
import helper.JDBC;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * The type Reports controller.
 */
public class reportsController implements Initializable {
    /**
     * The Reports title label.
     */
    public Label reportsTitleLabel;
    /**
     * The Reports back button.
     */
    public Button reportsBackButton;
    /**
     * The Type and month title label.
     */
    public Label typeAndMonthTitleLabel;
    /**
     * The Appointments by type table view.
     */
    public TableView appointmentsByTypeTableView;
    /**
     * The Appointments by contact title label.
     */
    public Label appointmentsByContactTitleLabel;
    /**
     * The Contact combo box label.
     */
    public Label contactComboBoxLabel;
    /**
     * The Reports contact combo box.
     */
    public ComboBox<Contact> reportsContactComboBox;
    /**
     * The Appointments by contact table view.
     */
    public TableView appointmentsByContactTableView;
    /**
     * The Appointments by contact appointment id col.
     */
    public TableColumn appointmentsByContactAppointmentIdCol;
    /**
     * The App by contact title col.
     */
    public TableColumn appByContactTitleCol;
    /**
     * The App by contact type col.
     */
    public TableColumn appByContactTypeCol;
    /**
     * The App by contact description col.
     */
    public TableColumn appByContactDescriptionCol;
    /**
     * The App by contact start date time col.
     */
    public TableColumn appByContactStartDateTimeCol;
    /**
     * The App by contact end date time col.
     */
    public TableColumn appByContactEndDateTimeCol;
    /**
     * The App by contact customer id col.
     */
    public TableColumn appByContactCustomerIdCol;
    /**
     * The App by location title label.
     */
    public Label appByLocationTitleLabel;
    /**
     * The App by location table view.
     */
    public TableView appByLocationTableView;
    /**
     * The App by location location col.
     */
    public TableColumn appByLocationLocationCol;
    /**
     * The App by location total col.
     */
    public TableColumn appByLocationTotalCol;

    /**
     * On back button.
     * Returns to directory screen
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    public void onBackButton(ActionEvent actionEvent) throws IOException {
        Parent directory = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/directory.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(directory);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Initializes combobox and Report tables
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<Contact> allContacts = FXCollections.observableArrayList();
            allContacts = contactQuery.getAllContacts();
            reportsContactComboBox.setItems(allContacts);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Connection c;
        ObservableList<ObservableList> data = FXCollections.observableArrayList();
        try {
            c = JDBC.getConnection();
            //SQL FOR SELECTING ALL OF CUSTOMER
            String SQL = "select Type, monthname(Start) as Month, count(*) as Total from appointments group by Type, monthname(Start) order by month;";
            //ResultSet
            ResultSet rs = c.createStatement().executeQuery(SQL);

            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                appointmentsByTypeTableView.getColumns().addAll(col);
            }

            while (rs.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                data.add(row);
            }
            //FINALLY ADDED TO TableView
            appointmentsByTypeTableView.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }

        reportsContactComboBox.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    int contactId = reportsContactComboBox.getValue().getContactId();
                    ObservableList<Appointment> contactAppointments = FXCollections.observableArrayList();
                    contactAppointments = appointmentsQuery.getAppointmentsbyContact(contactId);

                    appointmentsByContactTableView.setItems(contactAppointments);
                    appointmentsByContactAppointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
                    appByContactTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
                    appByContactDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
                    //appByLocationLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
                    appByContactTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
                    appByContactStartDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
                    appByContactEndDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
                    appByContactCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
                    //userIdTableCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        });

        Connection d;
        ObservableList<ObservableList> customerdata = FXCollections.observableArrayList();
        try {
            d = JDBC.getConnection();
            //SQL FOR SELECTING ALL OF CUSTOMER
            String SQL = "SELECT client_schedule.customers.Customer_Name, COUNT(*) as \"Total Appointments\" FROM client_schedule.appointments\n" +
                    "INNER JOIN client_schedule.customers ON client_schedule.appointments.Customer_ID=client_schedule.customers.Customer_ID\n" +
                    "GROUP BY client_schedule.customers.Customer_Name;";
            //ResultSet
            ResultSet customerRs = d.createStatement().executeQuery(SQL);

            for (int i = 0; i < customerRs.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(customerRs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                appByLocationTableView.getColumns().addAll(col);
            }

            while (customerRs.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= customerRs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(customerRs.getString(i));
                }
                customerdata.add(row);
            }
            //FINALLY ADDED TO TableView
            appByLocationTableView.setItems(customerdata);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }

    }
}
