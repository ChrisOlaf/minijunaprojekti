import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * TimeTableRow sisältää luokan Juna-olioiden aikatauluriveille sekä metodeja
 * tietojen tulostamiseen ja pyytämiseen.
 *
 * Erityisesti getActualTime ja getScheduledTime on muokattu palauttamaan
 * String-muotoisen tulosteen ajasta Date-olion sijaan. Lisäksi on luotu
 * getTime() -metodi, joka palauttaa Date-olion kyseisestä aikataulurivistä.
 *
 * @author Antti Pöyhönen
 */

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

    // palauttaa mahdollisimman ajankohtaisen Date-olion (live-ennuste > todennettu aika > aikataulutettu aika)
    public Date getTime() {
        if (liveEstimateTime != null) {
            return liveEstimateTime;
        } else if (actualTime != null) {
            return actualTime;
        } else {
            return scheduledTime;
        }
    }

    // palauta String-tyyppinen lokalisoitu ajan esitys, actualTime, jos olemassa, muuten scheduledTime
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

    // palauttaa String-muotoisen lokalisoidun aikataulutetun ajan
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
