import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.net.URL;
import java.util.Set;
import java.util.TreeSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * Asema-luokka hakee haeAsemat()-metodilla kaikkien juna-asemien
 * tiedot JSON-tiedostona sekä listaa asemien lyhennenimet ja
 * nimet asemat-MAP-listaan. listaaAsemat() -metodi listaa
 * asemien lyhenteet ja nimet aakkosjärjestyksessä.
 *
 * @author Antti Pöyhönen
 */

// tarvittaessa: @JsonIgnoreProperties(ignoreUnknown = true)
public class Asema {

    boolean passengerTraffic;
    String countryCode, stationName, stationShortCode, type;
    double latitude, longitude;
    int stationUICCode;

    // asemien Liikennepaikkatiedot JSON url
    static String link = "https://rata.digitraffic.fi/api/v1/metadata/stations";

    // JSON-olioiden listan perustaminen
    static public List<Asema> asemaoliot;

    // Juna-asemat listattuna MAP-listaan; avaimena on 2-3 -kirjaiminen aseman lyhenne ja arvona aseman koko nimi
    static public Map<String, String> asemat = new HashMap<>();

    // Juna-asemien avaimet aakkosjärjestyksessä TreeSet-listalla
    static public Set aakkosAsemat;

    // hakee asemat urlin perusteella ja lisää kaikista asemista lyhenteet ja täydet nimet asemat-MAP-listaan
    public static void haeAsemat() {

        try {

            URL url = new URL(link);

            ObjectMapper mapper = new ObjectMapper();

            CollectionType tarkempiListanTyyppi = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Asema.class);

            asemaoliot = mapper.readValue(url, tarkempiListanTyyppi);

            // käy läpi asemaoliot ja lisää jokaisesta oliosta lyhenne ja koko nimi asemat-MAP-listaan
            for (Asema a : asemaoliot) {
                asemat.put(a.getStationShortCode(), a.getStationName());
            }

            // aseta aakkosAsemat-lista olemaan TreeSet asemat-listan avaimista
            aakkosAsemat = new TreeSet(asemat.keySet());

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    // listaa kaikki asemat muodossa "lyhenne -- koko nimi"
    public static void listaaAsemat() {

        for (Object sobj : aakkosAsemat) {
            String s = (String) sobj;
            System.out.println(s + " -- " + asemat.get(s));
        }
        System.out.println("");

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
