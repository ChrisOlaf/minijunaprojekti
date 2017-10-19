import java.util.List;
import java.util.Scanner;

public class Asemahaku {
    //Staattinen muuttuja käyttäjän syötettä varten
    static String annettuAsema;
    static String luettu;
   //testaus main-alue Asemahaku-luokan metodille, POISTETAAN
   public static void main(String[] args) {
       Asema.haeAsemat();
       hae();
    }

    //Asemaluokan päämetodi, joka koostuu kahdesta osametodista
    public static void hae() {

    //Osametodi 1 päämetodissa: Kysytään, minkä aseman junat käyttäjä haluaa nähdä
        Scanner lukija = new Scanner(System.in);
        kysyAsema(lukija);
    //Osametodi 2 päämetodissa: Kysytään, haluaako käyttäjä saapuvat vai lähtevät junat
        kysySaapuvatTaiLahtevat(lukija);
    }
    //Osametodi 1: Kysytään, minkä aseman junat käyttäjä haluaa nähdä
    public static void kysyAsema(Scanner lukija) {
        System.out.println("Mikä asema? (asemakoodi, esim. HKI)");
        annettuAsema = lukija.nextLine().toUpperCase();
        while (!(Asema.asemat.containsKey(annettuAsema))) {
            System.out.println("\nSyötä asemakoodi, esim. HKI (Helsinki) tai TPE (Tampere).\n");
            annettuAsema = lukija.nextLine().toUpperCase();
        }
    }
    //Osametodi 2: Kysytään, haluaako käyttäjä saapuvat vai lähtevät junat.
    public static void kysySaapuvatTaiLahtevat(Scanner lukija) {
        System.out.println("Saapuvat vai lähtevät junat? Saapuvat: 1 Lähtevät: 2");
        luettu = lukija.nextLine();
        for (; ;) {
            if (luettu.equals("1")) {
                haeSaapuvatJunatAsemanMukaan();
                break;
            } else if (luettu.equals("2")) {
                haeLahtevatJunatAsemanMukaan();
                break;
            } else {
                System.out.println("Valitse: Saapuvat: 1 Lähtevät: 2");
                luettu = lukija.nextLine();
            }
        }
    }
    // /live-trains/station/HKI/TPE
    // /live-trains/station/<departure_station_code>/<arrival_station_code>?departure_date=<departure_date>&from=<from>&to=<to>&limit=<limit>
    // Varikko.LueJunanJsonData(String URL)

    //Osametodi 3A: Haetaan saapuvat junat käyttäjän antaman aseman mukaan.
    public static void haeSaapuvatJunatAsemanMukaan() {
        String url = "/live-trains/station/" + annettuAsema + "?minutes_before_arrival=120";

        // hae uusin data Varikolle
        Varikko.lueJunanJSONData(url);

        // hae junien lista käyttöön paikalliseen muuttujaan
        List<Juna> asemaJunat = Varikko.junat;

        //Filtteröidään asemalle saapuvat junat.
        //Tulostetaan tiedot:
        //1. Junan koodinimi: trainType + trainNumber (Juna)
        //2. Lähtöasema: stationShortCode (timeTableRows)
        //3. Lähtöaika: scheduledTime (timeTableRows)
        //4. Junan tunnus (jos lähijuna): commuterLineID (Juna)

        for (Juna j: asemaJunat) {
            for (int i = 0; i < j.getTimeTableRows().size()-1; i++) {
                if (j.getTimeTableRows().get(i).getStationShortCode().equals(annettuAsema) && j.getTimeTableRows().get(i).getType().equals("ARRIVAL")) {
                    System.out.println(
                            "Saapuvat: " + j.getTrainType() + j.getTrainNumber()
                                    + " Lähtöasema: " + j.getTimeTableRows().get(0).getStationShortCode()
                                    + " Saapumisaika: " + j.getTimeTableRows().get(i).getScheduledTime()
                                    + " Junan tunnus, jos lähijuna: " + j.getCommuterLineID());
                }
            }
        }

    }
    //Osametodi 3B: Haetaan lähtevät junat käyttäjän antaman aseman mukaan.
    public static void haeLahtevatJunatAsemanMukaan() {
        String url = "/live-trains/station/" + annettuAsema + "?minutes_before_departure=120";

        // hae uusin data Varikolle
        Varikko.lueJunanJSONData(url);

        // hae junien lista käyttöön paikalliseen muuttujaan
        List<Juna> asemaJunat = Varikko.junat;

        //Filtteröidään asemalta lähtevät junat.
        // Tulostetaan tiedot:
        //1. Junan koodinimi: trainType + trainNumber (Juna)
        //2. Pääteasema: stationShortCode (timeTableRows)
        //3. Lähtöaika: scheduledTime (timeTableRows)
        //4. Junan tunnus (jos lähijuna): commuterLineID (Juna)
        for (Juna j : asemaJunat) {
            for (int i = 0; i < j.getTimeTableRows().size()-1; i++) {
                if (j.getTimeTableRows().get(i).getStationShortCode().equals(annettuAsema) && j.getTimeTableRows().get(i).getType().equals("DEPARTURE")) {
                    System.out.println(
                            "Lähtevät: " + j.getTrainType() + j.getTrainNumber()
                                    + " Pääteasema: " + j.getTimeTableRows().get(j.getTimeTableRows().size()-1).getStationShortCode()
                                    + " Lähtöaika: " + j.getTimeTableRows().get(i).getScheduledTime()
                                    + " Junan tunnus, jos lähijuna: " + j.getCommuterLineID());
                }
            }
        }
    }


//


//        URL: /live-trains/station/<station_shortcode>?arrived_trains=arrived_trains>&arriving_trains=arriving_trains> &departed_trains=<departed_trains>&departing_trains=<departing_trains>&version=<change_number>
//        Esimerkki: /live-trains/station/HKI
//        Esimerkki: /live-trains/station/HKI?arrived_trains=5&arriving_trains=5&departed_trains=5&departing_trains=5&include_nonstopping=false
//

    // Varikko.LueJunanJsonData(String URL)


}
