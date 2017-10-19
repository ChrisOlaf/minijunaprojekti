import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Asemahaku {
    //Staattinen muuttuja käyttäjän syötettä varten
    static String annettuAsema;
    static String luettu;
    static Date nykyhetki = new Date();
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
        System.out.println("Mikä asema? (Syötä asemakoodi, esim. HKI (Helsinki) tai TPE (Tampere)");
        annettuAsema = lukija.nextLine().toUpperCase();
        while (!(Asema.asemat.containsKey(annettuAsema))) {
            System.out.println("\nSyötä asemakoodi, esim. HKI (Helsinki) tai TPE (Tampere).\n");
            annettuAsema = lukija.nextLine().toUpperCase();
        }
    }
    public static void kysySaapuvatTaiLahtevat(Scanner lukija) {
        System.out.println("Lähtevät vai saapuvat junat? (Lähtevät 1, saapuvat 2)");
        luettu = lukija.nextLine();
        for (; ;) {
            if (luettu.equals("1")) {
                System.out.println("\n" + "Asemalta " + Asema.asemat.get(annettuAsema) + " lähtevät junat: \n");
                haeLahtevatJunatAsemanMukaan();
                break;
            } else if (luettu.equals("2")) {
                System.out.println("\n" + "Asemalle " + Asema.asemat.get(annettuAsema) + " saapuvat junat: \n");
                haeSaapuvatJunatAsemanMukaan();
                break;
            } else {
                System.out.println("Valitse: Lähtevät: 1 Saapuvat: 2");
                luettu = lukija.nextLine();
            }
        }
    }

    // /live-trains/station/HKI/TPE
    // /live-trains/station/<departure_station_code>/<arrival_station_code>?departure_date=<departure_date>&from=<from>&to=<to>&limit=<limit>
    // Varikko.LueJunanJsonData(String URL)

    //Osametodi 3A: Haetaan lähtevät junat käyttäjän antaman aseman mukaan.
    public static void haeLahtevatJunatAsemanMukaan() {
        String url = "/live-trains/station/" + annettuAsema + "?minutes_before_departure=240&minutes_after_departure=0&minutes_before_arrival=0&minutes_after_arrival=0";
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
            for (int i = 0; i < j.getTimeTableRows().size() - 1; i++) {
                if (j.getTimeTableRows().get(i).getStationShortCode().equals(annettuAsema)
                        && j.getTimeTableRows().get(i).getType().equals("DEPARTURE")
                        && ((nykyhetki.getTime()) < j.getTimeTableRows().get(i).getTime().getTime())) {
                    if (!("".equals(j.getCommuterLineID()))) {
                        System.out.println(
                                "" + j.getCommuterLineID() + "-juna"
                                        + " " + Asema.asemat.get(j.getTimeTableRows().get(j.getTimeTableRows().size() - 1).getStationShortCode())
                                        + "\n" + "Lähtöaika " + j.getTimeTableRows().get(i).getActualTime() + "\n");
                    } else {
                        System.out.println(
                                "" + j.getTrainType() + j.getTrainNumber()
                                        + " " + Asema.asemat.get(j.getTimeTableRows().get(j.getTimeTableRows().size() - 1).getStationShortCode())
                                        + "\n" + "Lähtöaika " + j.getTimeTableRows().get(i).getActualTime() + "\n");
                    }
                }
            }
        }
    }

    //Osametodi 3B: Haetaan saapuvat junat käyttäjän antaman aseman mukaan.
    public static void haeSaapuvatJunatAsemanMukaan() {
        String url = "/live-trains/station/" + annettuAsema + "?minutes_before_departure=0&minutes_after_departure=0&minutes_before_arrival=240&minutes_after_arrival=0";

        // hae uusin data Varikolle
        Varikko.lueJunanJSONData(url);

        // hae junien lista käyttöön paikalliseen muuttujaan
        List<Juna> asemaJunat = Varikko.junat;
    //    Collections.sort(asemaJunat);

        //Filtteröidään asemalle saapuvat junat.
        //Tulostetaan tiedot:
        //1. Junan koodinimi: trainType + trainNumber (Juna)
        //2. Lähtöasema: stationShortCode (timeTableRows)
        //3. Lähtöaika: scheduledTime (timeTableRows)
        //4. Junan tunnus (jos lähijuna): commuterLineID (Juna)

        /*
        * Date current = new Date();

        for (TimeTableRow t : j.getTimeTableRows()) {
        if ((current.getTime()) > (t.getTime().getTime())) {
            ...
            }
        }
        * */

        for (Juna j: asemaJunat) {
            for (int i = 0; i < j.getTimeTableRows().size()-1; i++) {
                if (j.getTimeTableRows().get(i).getStationShortCode().equals(annettuAsema)
                        && j.getTimeTableRows().get(i).getType().equals("ARRIVAL")
                        && ((nykyhetki.getTime()) < j.getTimeTableRows().get(i).getTime().getTime())) {
                    if (!("".equals(j.getCommuterLineID()))) {
                        System.out.println(
                                "" + j.getCommuterLineID() + "-juna"
                                        + " " + Asema.asemat.get(j.getTimeTableRows().get(0).getStationShortCode())
                                        + "\n" + "Saapumisaika: " + j.getTimeTableRows().get(i).getActualTime() + "\n");
                    } else {
                        System.out.println(
                                "" + j.getTrainType() + j.getTrainNumber()
                                        + " " + Asema.asemat.get(j.getTimeTableRows().get(0).getStationShortCode())
                                        + "\n" + "Saapumisaika: " + j.getTimeTableRows().get(i).getActualTime() + "\n");
                    }
                }
            }
        }
    }
}
