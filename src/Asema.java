import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// tarvittaessa: @JsonIgnoreProperties(ignoreUnknown = true)
public class Asema {

    boolean passengerTraffic;
    String countryCode, stationName, stationShortCode, type;
    double latitude, longitude;
    int stationUICCode;

    // asemien Liikennepaikkatiedot JSON url
    static String baseurl = "https://rata.digitraffic.fi/api/v1/metadata/stations";

    // JSON-olioiden listan perustaminen
    static public List<Asema> asemaoliot;

    // Juna-asemat listattuna MAP-listaan; avaimena on 2-3 -kirjaiminen aseman lyhenne ja arvona aseman koko nimi
    static public Map<String, String> asemat = new HashMap<>();

    public static void haeAsemat() {

        try {

            URL url = new URL(baseurl);

            ObjectMapper mapper = new ObjectMapper();

            CollectionType tarkempiListanTyyppi = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Asema.class);

            asemaoliot = mapper.readValue(url, tarkempiListanTyyppi);

            for (Asema a : asemaoliot) {
                asemat.put(a.getStationShortCode(), a.getStationName());
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public int getStationUICCode() {
        return stationUICCode;
    }

    public void setStationUICCode(int stationUICCode) {
        this.stationUICCode = stationUICCode;
    }

    public double getLongitude() {

        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {

        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getType() {

        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStationShortCode() {

        return stationShortCode;
    }

    public void setStationShortCode(String stationShortCode) {
        this.stationShortCode = stationShortCode;
    }

    public String getStationName() {

        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getCountryCode() {

        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public boolean isPassengerTraffic() {

        return passengerTraffic;
    }

    public void setPassengerTraffic(boolean passengerTraffic) {
        this.passengerTraffic = passengerTraffic;
    }
}
