import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeTableRow {

    private boolean cancelled;
    private boolean commercialStop;
    private String commercialTrack;
    private Date scheduledTime;
    private String stationShortCode;
    private int stationUICCode;
    private boolean trainStopping;
    private String type;
    private Date actualTime;
    private Date liveEstimateTime;

    public Date getTime() {
        if (liveEstimateTime != null) {
            return liveEstimateTime;
        } else if (actualTime != null) {
            return actualTime;
        } else {
            return scheduledTime;
        }
    }

    public String getActualTime() {

        Locale fi = new Locale("fi", "FI");
        DateFormat fiAika = DateFormat.getTimeInstance(DateFormat.SHORT, fi);
        DateFormat fiDate = DateFormat.getDateInstance(DateFormat.LONG, fi);

        if (actualTime != null) {
            return fiAika.format(actualTime) + ", " + fiDate.format(actualTime);
        } else {
            return this.getScheduledTime();
        }

    }

    public String getScheduledTime() {

        Locale fi = new Locale("fi", "FI");
        DateFormat fiAika = DateFormat.getTimeInstance(DateFormat.SHORT, fi);
        DateFormat fiDate = DateFormat.getDateInstance(DateFormat.LONG, fi);

        return fiAika.format(scheduledTime) + ", " + fiDate.format(scheduledTime);

    }

    public void setActualTime(Date actualTime) {
        this.actualTime = actualTime;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public boolean isCommercialStop() {
        return commercialStop;
    }

    public String getCommercialTrack() {
        return commercialTrack;
    }

    public String getStationShortCode() {
        return stationShortCode;
    }

    public int getStationUICCode() {
        return stationUICCode;
    }

    public boolean isTrainStopping() {
        return trainStopping;
    }

    public String getType() {
        return type;
    }

}
