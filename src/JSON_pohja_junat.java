import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
Vaatii Jackson kirjaston:
File | Project Structure
Libraries >> Add >> Maven
Etsi "jackson-databind", valitse versio 2.0.5
Asentuu Jacksonin databind, sekä core ja annotations
 */

public class JSON_pohja_junat {
    public static void main(String[] args) {
        lueJunanJSONData();
    }



}
//
//
//@JsonIgnoreProperties(ignoreUnknown = true)
//class TimeTableRow {
//}