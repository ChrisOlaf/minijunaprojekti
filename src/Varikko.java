import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Varikko {
    static String baseurl = "https://rata.digitraffic.fi/api/v1";
    //    static URL url = new URL(baseurl+"/live-trains/station/HKI/LH");
    static public List<Juna> junat;

    private static void lueJunanJSONData(String urlparam) {
        URL url = new URL(baseurl + urlparam);

        try {

            ObjectMapper mapper = new ObjectMapper();

            CollectionType tarkempiListanTyyppi = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Juna.class);

            junat = mapper.readValue(url, tarkempiListanTyyppi);  // pelkkä List.class ei riitä tyypiksi

//            System.out.println(junat.get(0).getTrainNumber());

//            Seuraavaa varten on toteutettava TimeTableRow luokka:
//            System.out.println(junat.get(0).getTimeTableRows().get(0).getScheduledTime());

//            System.out.println("\n\n");
//            System.out.println(junat.get(0));

        } catch (Exception ex) {
            System.out.println(ex);
            System.out.println("Data ei saanut Lukea.");
        }
    }
}
