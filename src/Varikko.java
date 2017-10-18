import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Varikko parseroi digitrafficilta tulevan junadatan Juna- ja
 * TimeTableRow-luokkien pyytämillä parametreilla.
 *
 * @author Risto Rautanen
 */

public class Varikko {

    static String baseurl = "https://rata.digitraffic.fi/api/v1";

    static public List<Juna> junat;

    public static void lueJunanJSONData(String urlparam) {

        try {

            URL url = new URL(baseurl + urlparam);
            // = new URL(baseurl + "/live-trains/station/HKI/LH");

            ObjectMapper mapper = new ObjectMapper();

            CollectionType tyypitys = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Juna.class);

            junat = mapper.readValue(url, tyypitys);

            // käyttönä esimerkiksi:

            // System.out.println(Varikko.junat.get(0).getTrainNumber());
            // System.out.println(Varikko.junat.get(0).getTimeTableRows().get(0).getScheduledTime());
            // System.out.println(Varikko.junat.get(0));

        } catch (Exception e) {
            // System.out.println("Data ei saanut Lukea.");
            System.out.println("Tällä haulla ei löytynyt mitään tietoja.");
        }
    }
}