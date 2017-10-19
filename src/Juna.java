import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Juna  {

    private boolean cancelled;
    private String commuterLineID;

    //LocalDate departureDate;  // Jackson ei oikein pidä Java 8 päivistä oletuksena
    private Date departureDate;

    private String operatorShortCode;
    private int operatorUICCode;

    private boolean runningCurrently;

    private List<TimeTableRow> timeTableRows;
    private Date timetableAcceptanceDate;
    private String timetableType;

    private String trainCategory;
    private int trainNumber;
    private String trainType;
    private long version;

    //@Override
    //public int compareTo(Juna o) {
    //    return getTimeTableRows().get(i).getScheduledTime().compareTo(o.getTimeTableRows().get(i).getScheduledTime());
    //}

    @Override
    public String toString() {
        return "Juna{" + "cancelled=" + cancelled + ", commuterLineID='" + commuterLineID + '\'' + ", departureDate=" + departureDate + ", operatorShortCode='" + operatorShortCode + '\'' + ", operatorUICCode=" + operatorUICCode + ", runningCurrently=" + runningCurrently + ", timeTableRows=" + timeTableRows + ", timetableAcceptanceDate=" + timetableAcceptanceDate + ", timetableType='" + timetableType + '\'' + ", trainCategory='" + trainCategory + '\'' + ", trainNumber=" + trainNumber + ", trainType='" + trainType + '\'' + ", version=" + version + '}';
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public String getCommuterLineID() {
        return commuterLineID;
    }

    public String getDepartureDate() {

        Locale fi = new Locale("fi", "FI");
        DateFormat fiDate = DateFormat.getDateInstance(DateFormat.LONG, fi);

        return fiDate.format(departureDate);

    }

    public String getOperatorShortCode() {
        return operatorShortCode;
    }

    public int getOperatorUICCode() {
        return operatorUICCode;
    }

    public boolean isRunningCurrently() {
        return runningCurrently;
    }

    public List<TimeTableRow> getTimeTableRows() {
        return timeTableRows;
    }

    public Date getTimetableAcceptanceDate() {
        return timetableAcceptanceDate;
    }

    public String getTimetableType() {
        return timetableType;
    }

    public String getTrainCategory() {
        return trainCategory;
    }

    public int getTrainNumber() {
        return trainNumber;
    }

    public String getTrainType() {
        return trainType;
    }

    public long getVersion() {
        return version;
    }

}

