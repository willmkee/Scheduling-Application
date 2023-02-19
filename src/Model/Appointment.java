package Model;

import java.time.LocalDateTime;

/**
 * The type Appointment.
 */
public class Appointment {
    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    /**
     * The Customer id.
     */
    public int customerId;
    /**
     * The User id.
     */
    public int userId;
    /**
     * The Contact id.
     */
    public int contactId;

    /**
     * Instantiates a new Appointment.
     *
     * @param appointmentId the appointment id
     * @param title         the title
     * @param description   the description
     * @param location      the location
     * @param type          the type
     * @param startDateTime the start date time
     * @param endDateTime   the end date time
     * @param customerId    the customer id
     * @param userId        the user id
     * @param contactId     the contact id
     */
    public Appointment(int appointmentId, String title, String description, String location, String type, LocalDateTime startDateTime, LocalDateTime endDateTime, int customerId, int userId, int contactId) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    /**
     * Gets appointment id.
     *
     * @return the appointment id
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * Sets appointment id.
     *
     * @param appointmentId the appointment id
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets location.
     *
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets location.
     *
     * @param location the location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets start date time.
     *
     * @return the start date time
     */
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    /**
     * Sets start date time.
     *
     * @param startDateTime the start date time
     */
    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    /**
     * Gets end date time.
     *
     * @return the end date time
     */
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    /**
     * Sets end date time.
     *
     * @param endDateTime the end date time
     */
    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    /**
     * Gets customer id.
     *
     * @return the customer id
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Sets customer id.
     *
     * @param customerId the customer id
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets contact id.
     *
     * @return the contact id
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Sets contact id.
     *
     * @param contactId the contact id
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "startDateTime=" + startDateTime +
                '}';
    }
}
