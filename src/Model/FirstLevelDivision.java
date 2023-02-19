package Model;

/**
 * The type First level division.
 */
public class FirstLevelDivision {
    private int divisionId;
    private String divisionName;
    /**
     * The Country id.
     */
    public int countryId;

    /**
     * Instantiates a new First level division.
     *
     * @param divisionId   the division id
     * @param divisionName the division name
     * @param countryId    the country id
     */
    public FirstLevelDivision(int divisionId, String divisionName, int countryId) {
        this.divisionId = divisionId;
        this.divisionName = divisionName;
        this.countryId = countryId;
    }

    /**
     * Gets division id.
     *
     * @return the division id
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Sets division id.
     *
     * @param divisionId the division id
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * Gets division name.
     *
     * @return the division name
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * Sets division name.
     *
     * @param divisionName the division name
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**
     * Gets country id.
     *
     * @return the country id
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Sets country id.
     *
     * @param countryId the country id
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    @Override
    public String toString() {return divisionName;}
}
