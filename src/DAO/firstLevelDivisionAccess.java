package DAO;

import Model.FirstLevelDivision;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The First level division access.
 */
public class firstLevelDivisionAccess {
    /**
     * Gets all states.
     *
     * @return All states
     * @throws SQLException
     */
    public static ObservableList<FirstLevelDivision> getAllStates() throws SQLException {
        ObservableList<FirstLevelDivision> allStates = FXCollections.observableArrayList();
        String sql = "SELECT Division_ID, Division, Country_ID FROM client_schedule.first_level_divisions\n" +
                "WHERE Country_ID=1\n" +
                "order by Division ASC;";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int divisionId = rs.getInt("Division_ID");
            String divisionName = rs.getString("Division");
            int countryId = rs.getInt("Country_ID");
            FirstLevelDivision newState = new FirstLevelDivision(divisionId, divisionName, countryId);
            allStates.add(newState);
        }
        return allStates;
    }

    /**
     * Gets all uk countries.
     *
     * @return All uk countries
     * @throws SQLException
     */
    public static ObservableList<FirstLevelDivision> getAllUKCountries() throws SQLException {
        ObservableList<FirstLevelDivision> allCountries = FXCollections.observableArrayList();
        String sql = "SELECT Division_ID, Division, Country_ID FROM client_schedule.first_level_divisions\n" +
                "WHERE Country_ID=2\n" +
                "order by Division ASC;";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int divisionId = rs.getInt("Division_ID");
            String divisionName = rs.getString("Division");
            int countryId = rs.getInt("Country_ID");
            FirstLevelDivision newState = new FirstLevelDivision(divisionId, divisionName, countryId);
            allCountries.add(newState);
        }
        return allCountries;
    }

    /**
     * Gets all canadian provinces.
     *
     * @return All canadian provinces
     * @throws SQLException
     */
    public static ObservableList<FirstLevelDivision> getAllCanadianProvinces() throws SQLException {
        ObservableList<FirstLevelDivision> allProvinces = FXCollections.observableArrayList();
        String sql = "SELECT Division_ID, Division, Country_ID FROM client_schedule.first_level_divisions\n" +
                "WHERE Country_ID=3\n" +
                "order by Division ASC;";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int divisionId = rs.getInt("Division_ID");
            String divisionName = rs.getString("Division");
            int countryId = rs.getInt("Country_ID");
            FirstLevelDivision newState = new FirstLevelDivision(divisionId, divisionName, countryId);
            allProvinces.add(newState);
        }
        return allProvinces;
    }

    /**
     * Gets states provinces by country name.
     *
     * @param countryName the country name
     * @return the states/provinces by country name
     * @throws SQLException
     */
    public static ObservableList<FirstLevelDivision> getStatesProvincesbyCountryName(String countryName) throws SQLException {
        ObservableList<FirstLevelDivision> allProvinces = FXCollections.observableArrayList();
        String sql;
        if(countryName.equals("U.S")) {
            sql = "SELECT Division_ID, Division, Country_ID FROM client_schedule.first_level_divisions\n" +
                    "WHERE Country_ID=1\n" +
                    "order by Division ASC;";
        }
        else if (countryName.equals("UK")) {
            sql = "SELECT Division_ID, Division, Country_ID FROM client_schedule.first_level_divisions\n" +
                    "WHERE Country_ID=2\n" +
                    "order by Division ASC;";
        }
        else if (countryName.equals("Canada")){
            sql = "SELECT Division_ID, Division, Country_ID FROM client_schedule.first_level_divisions\n" +
                    "WHERE Country_ID=3\n" +
                    "order by Division ASC;";
        }
        else {
            sql = "SELECT Division_ID, Division, Country_ID FROM client_schedule.first_level_divisions\n" +
                    "WHERE Country_ID=1\n" +
                    "order by Division ASC;";
        }
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int divisionId = rs.getInt("Division_ID");
            String divisionName = rs.getString("Division");
            int countryId = rs.getInt("Country_ID");
            FirstLevelDivision newState = new FirstLevelDivision(divisionId, divisionName, countryId);
            allProvinces.add(newState);
        }
        return allProvinces;
    }

    /**
     * Gets state/provinces by name.
     *
     * @param stateProvince the state province
     * @return the state/province by name
     * @throws SQLException
     */
    public static FirstLevelDivision getStateProvincesByName(String stateProvince) throws SQLException {
        String sql = "SELECT Division_ID, Division, Country_ID FROM client_schedule.first_level_divisions WHERE Division=\"" + stateProvince +"\";";

        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int divisionId = rs.getInt("Division_ID");
        String divisionName = rs.getString("Division");
        int countryId = rs.getInt("Country_ID");
        FirstLevelDivision newState = new FirstLevelDivision(divisionId, divisionName, countryId);
        return newState;
    }
}
