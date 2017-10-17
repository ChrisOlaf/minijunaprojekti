import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeTableRow {

    boolean cancelled;
    boolean commercialStop;
    String commercialTrack;
    Date scheduledTime;
    String stationShortCode;
    int stationUICCode;
    boolean trainStopping;
    String type;

    public boolean isCancelled() {
        return cancelled;
    }

    public boolean isCommercialStop() {
        return commercialStop;
    }

    public String getCommercialTrack() {
        return commercialTrack;
    }

    public Date getScheduledTime() {
        return scheduledTime;
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

    //haeJuna();  ?
}
