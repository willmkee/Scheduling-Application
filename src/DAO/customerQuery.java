package DAO;

import Model.Customer;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The Customer query.
 */
public class customerQuery {

    /**
     * Gets all customers.
     *
     * @return All customers
     * @throws SQLException
     */
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

    /**
     * Gets customer by id.
     *
     * @param customerId the customer id
     * @return the customer by id
     * @throws SQLException
     */
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

    /**
     * Delete customer by id int.
     *
     * @param deletedId the deleted id
     * @return the int
     * @throws SQLException
     */
    public static int deleteCustomerById(int deletedId) throws SQLException {
        String sql = "DELETE FROM client_schedule.customers WHERE customers.Customer_ID=" + deletedId + ";";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        int result = ps.executeUpdate();
        return result;
    }

    /**
     * Add new customer int.
     *
     * @param customerName the customer name
     * @param address      the address
     * @param postalCode   the postal code
     * @param phone        the phone
     * @param divisionId   the division id
     * @return the int
     * @throws SQLException the sql exception
     */
    public static int addNewCustomer(String customerName, String address, String postalCode, String phone, int divisionId) throws SQLException {
        String sql = "INSERT INTO client_schedule.customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID)\n" +
                "VALUES('" + customerName + "', '" + address + "', '" + postalCode + "', '" + phone + "', NOW(), 'script', NOW(), 'script', " + divisionId + ");";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        int results = ps.executeUpdate();
        return results;
    }

    /**
     * Update customer int.
     *
     * @param customerId   the customer id
     * @param customerName the customer name
     * @param address      the address
     * @param postalCode   the postal code
     * @param phone        the phone
     * @param divisionId   the division id
     * @return the int
     * @throws SQLException the sql exception
     */
    public static int updateCustomer(int customerId, String customerName, String address, String postalCode, String phone, int divisionId) throws SQLException {
        String sql = "UPDATE client_schedule.customers\n" +
                "SET Customer_Name = \"" + customerName + "\", Address = \"" + address + "\", Postal_Code = \"" + postalCode + "\", Phone = \"" + phone + "\", Division_ID = " + divisionId + ", Last_Update = NOW(), Last_Updated_By = 'script' \n" +
                "WHERE Customer_ID = " + customerId + ";";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        int results = ps.executeUpdate();
        return results;
    }
}

