import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

@JsonIgnoreProperties(ignoreUnknown = true)
public class Asema {

    private boolean passengerTraffic;
    private String countryCode, stationName, stationShortCode, type;
    private double latitude, longitude;
    private int stationUICCode;

    // asemien Liikennepaikkatiedot JSON url
    private static String link = "https://rata.digitraffic.fi/api/v1/metadata/stations";

    // JSON-olioiden listan perustaminen
    public static List<Asema> asemaoliot;

    // Juna-asemat listattuna MAP-listaan; avaimena on 2-3 -kirjaiminen aseman lyhenne ja arvona aseman koko nimi
    public static Map<String, String> asemat = new HashMap<>();

    // Juna-asemien avaimet aakkosjärjestyksessä TreeSet-listalla
    public static Set aakkosAsemat;

    // hakee asemat urlin perusteella ja lisää kaikista asemista lyhenteet ja täydet nimet asemat-MAP-listaan
    public static void haeAsemat() {

        try {

            URL url = new URL(link);

            ObjectMapper mapper = new ObjectMapper();

            CollectionType tarkempiListanTyyppi = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Asema.class);

            asemaoliot = mapper.readValue(url, tarkempiListanTyyppi);

            // käy läpi asemaoliot ja lisää jokaisesta oliosta lyhenne ja koko nimi asemat-MAP-listaan
            for (Asema a : asemaoliot) {

                // korvaa asemien koko nimistä " asema" pois
                String asemaNimi = a.getStationName().replaceFirst(" asema", "");

                asemat.put(a.getStationShortCode(), asemaNimi);
            }

            // aseta aakkosAsemat-lista olemaan TreeSet asemat-listan avaimista
            aakkosAsemat = new TreeSet(asemat.keySet());

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    // listaa kaikki asemat muodossa "lyhenne -- koko nimi"
    public static void listaaAsemat() {

        int count = 0;

        for (Object sobj : aakkosAsemat) {
            String s = (String) sobj;
            System.out.println(s + " -- " + asemat.get(s));
            count++;
        }

        System.out.println("\nLöydetty " + count + " asemaa.\n");

    }

    public String getStationShortCode() {

        return stationShortCode;
    }

    public static Set getAakkosAsemat() {
        return aakkosAsemat;
    }

    public static Map<String, String> getAsemat() {

        return asemat;
    }

    public String getType() {

        return type;
    }

    public String getStationName() {

        return stationName;
    }
}
