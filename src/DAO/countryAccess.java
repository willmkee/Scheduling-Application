package DAO;

import Model.Country;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The Country access.
 */
public class countryAccess {
    /**
     * Gets all countries.
     *
     * @return All countries
     * @throws SQLException
     */
    public static ObservableList<Country> getAllCountries() throws SQLException {
        ObservableList<Country> allCountries = FXCollections.observableArrayList();
        String sql = "SELECT Country_ID, Country FROM client_schedule.countries;";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int countryId = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");
            Country newCountry = new Country(countryId, countryName);
            allCountries.add(newCountry);
        }
        return allCountries;
    }

    /**
     * Gets country by name.
     *
     * @param countryName the country name
     * @return the country by name
     * @throws SQLException
     */
    public static Country getCountryByName(String countryName) throws SQLException {
        String sql = "SELECT * FROM client_schedule.countries WHERE Country=\"" + countryName + "\";";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int countryId = rs.getInt("Country_ID");
        Country newCountry = new Country(countryId, countryName);
        return newCountry;
    }
}
