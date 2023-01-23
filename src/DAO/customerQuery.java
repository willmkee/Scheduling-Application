package DAO;

import Model.Customer;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class customerQuery {

    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        String sql = "SELECT customers.Customer_ID,\n" +
                "    customers.Customer_Name,\n" +
                "    customers.Address,\n" +
                "    customers.Postal_Code,\n" +
                "    customers.Phone,\n" +
                "    customers.Division_ID,\n" +
                "    first_level_divisions.Division,\n" +
                "    countries.Country\n" +
                "    FROM client_schedule.customers\n" +
                "    INNER JOIN client_schedule.first_level_divisions ON customers.Division_ID=first_level_divisions.Division_ID\n" +
                "    INNER JOIN client_schedule.countries ON first_level_divisions.Country_ID=countries.Country_ID;";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int customerId = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phoneNumber = rs.getString("Phone");
            int divisionId = rs.getInt("Division_ID");
            String stateProvince = rs.getString("Division");
            String country = rs.getString("Country");
            Customer customer = new Customer(customerId, customerName, address, postalCode, phoneNumber, divisionId, stateProvince, country);
            allCustomers.add(customer);
    }
    return allCustomers;
}

    public static Customer getCustomerById(int customerId) throws SQLException {
        String sql = "SELECT customers.Customer_ID,\n" +
                "    customers.Customer_Name,\n" +
                "    customers.Address,\n" +
                "    customers.Postal_Code,\n" +
                "    customers.Phone,\n" +
                "    customers.Division_ID,\n" +
                "    first_level_divisions.Division,\n" +
                "    countries.Country\n" +
                "    FROM client_schedule.customers\n" +
                "INNER JOIN client_schedule.first_level_divisions ON customers.Division_ID=first_level_divisions.Division_ID\n" +
                "INNER JOIN client_schedule.countries ON first_level_divisions.Country_ID=countries.Country_ID\n" +
                "WHERE customers.Customer_ID=" + customerId;
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        rs.next();
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phoneNumber = rs.getString("Phone");
            int divisionId = rs.getInt("Division_ID");
            String stateProvince = rs.getString("Division");
            String country = rs.getString("Country");

        return new Customer(customerId, customerName, address, postalCode, phoneNumber, divisionId, stateProvince, country);
    }
    }

