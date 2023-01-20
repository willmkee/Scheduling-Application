package Time;

import DAO.appointmentsQuery;
import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.temporal.ChronoUnit;

public class loginTime {

    public static ZonedDateTime currentTime;
    public static ZonedDateTime fifteenMinutesPast;
    public static ZoneId zid;

    public static ObservableList<Appointment> viewUpcomingAppointments() throws SQLException {
        ObservableList<Appointment> upcomingAppointments = FXCollections.observableArrayList();
        ObservableList<Appointment> allAppointments = appointmentsQuery.getAllAppointments();
        zid = ZoneId.systemDefault();
        currentTime = ZonedDateTime.now(zid);
        fifteenMinutesPast = currentTime.plus(15, ChronoUnit.MINUTES);

        for (Appointment appointment : allAppointments)
        {
            //System.out.println(appointment.getStartDateTime());
            if (appointment.getStartDateTime().atZone(zid).isAfter(ChronoZonedDateTime.from(currentTime))
                    && appointment.getStartDateTime().atZone(zid).isBefore(ChronoZonedDateTime.from(fifteenMinutesPast)))
            {
                upcomingAppointments.add(appointment);
            }
        }

        return upcomingAppointments;
    }







    /*private static ChronoLocalDateTime<?> currentTime = LocalDateTime.now(ZoneId.systemDefault());

    public static ObservableList<Timestamp> getAllAppointmentTimes() throws SQLException {
        ObservableList<Timestamp> allAppointmentTimes = FXCollections.observableArrayList();
        String sql = "SELECT Start FROM client_schedule.appointments;";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            Timestamp ts = rs.getTimestamp("Start");
            allAppointmentTimes.add(ts);
        }
        return allAppointmentTimes;
    }

    public static ObservableList<LocalDateTime> timestampListtoLocalDateTime(ObservableList<Timestamp> tsDates) {
        ObservableList<LocalDateTime> allAppointmentLocalDateTimes = FXCollections.observableArrayList();
        for (int i = 0; i < tsDates.size(); ++i) {
            LocalDateTime ldt = tsDates.get(i).toLocalDateTime();
            allAppointmentLocalDateTimes.add(ldt);
        }
        return allAppointmentLocalDateTimes;
    }

    public static ObservableList<LocalDateTime> appsNextFifteenMinutes(ObservableList<LocalDateTime> appsLocalDateTimes) {
        boolean appointmentsSoon = false;
        ChronoLocalDateTime next15 = currentTime.plus(15, ChronoUnit.MINUTES);
        ObservableList<LocalDateTime> nextFifteenMinutes = FXCollections.observableArrayList();
        for (int i = 0; i < appsLocalDateTimes.size(); ++i) {
            if (appsLocalDateTimes.get(i).isAfter(currentTime) && appsLocalDateTimes.get(i).isBefore(next15) ) {
                appointmentsSoon = true;
                nextFifteenMinutes.add(appsLocalDateTimes.get(i));
            }
        }
        if(appointmentsSoon) {
            return nextFifteenMinutes;
        }
    return nextFifteenMinutes;
    }*/
}


