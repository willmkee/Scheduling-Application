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

public class reportsController implements Initializable {
    public Label reportsTitleLabel;
    public Button reportsBackButton;
    public Label typeAndMonthTitleLabel;
    public TableView appointmentsByTypeTableView;
    public Label appointmentsByContactTitleLabel;
    public Label contactComboBoxLabel;
    public ComboBox<Contact> reportsContactComboBox;
    public TableView appointmentsByContactTableView;
    public TableColumn appointmentsByContactAppointmentIdCol;
    public TableColumn appByContactTitleCol;
    public TableColumn appByContactTypeCol;
    public TableColumn appByContactDescriptionCol;
    public TableColumn appByContactStartDateTimeCol;
    public TableColumn appByContactEndDateTimeCol;
    public TableColumn appByContactCustomerIdCol;
    public Label appByLocationTitleLabel;
    public TableView appByLocationTableView;
    public TableColumn appByLocationLocationCol;
    public TableColumn appByLocationTotalCol;

    public void onBackButton(ActionEvent actionEvent) throws IOException {
        Parent directory = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/directory.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(directory);
        stage.setScene(scene);
        stage.show();
    }

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
                    appByLocationLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
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

    }
}
