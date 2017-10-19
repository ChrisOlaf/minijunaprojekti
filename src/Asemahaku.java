import java.util.List;
import java.util.Scanner;

public class Asemahaku {
    //Staattinen muuttuja käyttäjän syötettä varten
    static String annettuAsema;
   //testaus main-alue Asemahaku-luokan metodille, POISTETAAN
   public static void main(String[] args) {
        hae();
    }

    //Asemaluokan päämetodi, joka koostuu kahdesta osametodista
    public static void hae() {

        Scanner lueAsema = new Scanner(System.in);
    //Osametodi 1 päämetodissa: Kysytään, minkä aseman junat (lähtevät ja saapuvat) käyttäjä haluaa nähdä
        kysyAsema(lueAsema);
    //Osametodi 2 päämetodissa: Haetaan junat (lähtevät ja saapuvat) käyttäjän antaman aseman mukaan.
        haeJunatAsemanMukaan();

    }
    //Osametodi 1: Kysytään, minkä aseman junat käyttäjä haluaa nähdä
    public static void kysyAsema(Scanner lueAsema) {
        System.out.println("Mikä asema? (kolmikirjaiminen asemakoodi, esim. HKI)");
        annettuAsema = lueAsema.nextLine().toUpperCase();
        while (annettuAsema.length() != 3) {
            System.out.println("\nSyötä kolmikirjaiminen koodi, esim. HKI (Helsinki) tai TPE (Tampere).\n");
            annettuAsema = lueAsema.nextLine().toUpperCase();
        }
    }
    //OSAMETODI 1:N MUOKKAILUA
    //while(true)
    //if (Asema.asemat.contains(annettuAsema)
    //break

    // /live-trains/station/HKI/TPE
    // /live-trains/station/<departure_station_code>/<arrival_station_code>?departure_date=<departure_date>&from=<from>&to=<to>&limit=<limit>
    // Varikko.LueJunanJsonData(String URL)

    //Osametodi 2: Haetaan junat käyttäjän antaman aseman mukaan.
    public static void haeJunatAsemanMukaan() {
        String url = "/live-trains/station/" + annettuAsema + "?minutes_before_departure=120&minutes_before_arrival=120";

        // hae uusin data Varikolle
        Varikko.lueJunanJSONData(url);

        // hae junien lista käyttöön paikalliseen muuttujaan
        List<Juna> asemaJunat = Varikko.junat;

        //Filtteröidään asemalta lähtevät junat.
        // Tulostetaan tiedot:
        //1. Junan koodinimi (trainType + trainNumber)
        //2. Pääteasema (stationShortCode)
        //3. Lähtöaika (scheduledTime)
        //4. Junan tunnus (jos lähijuna): commuterLineID (Juna)
        for (Juna j : asemaJunat) {
            if (j.getTimeTableRows().get(0).getType().equals("DEPARTURE") && j.getTimeTableRows().get(0).getStationShortCode().equals(annettuAsema)) {
                System.out.println(
                        "Lähtevät: " + j.getTrainType() + j.getTrainNumber()
                        + " Pääteasema: " + j.getTimeTableRows().get(j.getTimeTableRows().size()-1).getStationShortCode()
                        + " Lähtöaika: " + j.getTimeTableRows().get(0).getScheduledTime()
                        + " Junan tunnus, jos lähijuna: " + j.getCommuterLineID());

            }
        }
        //Filtteröidään asemalle saapuvat junat.
        //Tulostetaan tiedot:
        //1. Junan koodinimi: trainType + trainNumber (Juna)
        //2. Lähtöasema: stationShortCode (timeTableRows)
        //3. Lähtöaika: scheduledTime (timeTableRows)
        //4. Junan tunnus (jos lähijuna): commuterLineID (Juna)
        for (Juna j: asemaJunat) {

            if(j.getTimeTableRows().get(j.getTimeTableRows().size()-1).getType().equals("ARRIVAL")) {
                System.out.println(
                        "Saapuvat: " + j.getTrainType() + j.getTrainNumber()
                                + " Lähtöasema: " + j.getTimeTableRows().get(0).getStationShortCode()
                                + " Saapumisaika: " + j.getTimeTableRows().get(j.getTimeTableRows().size()-1).getScheduledTime()
                                + " Junan tunnus, jos lähijuna: " + j.getCommuterLineID());
            }
        }
    }


////                => näytä junien tiedot lajiteltuna saapuvat / lähtevät


//        URL: /live-trains/station/<station_shortcode>?arrived_trains=arrived_trains>&arriving_trains=arriving_trains> &departed_trains=<departed_trains>&departing_trains=<departing_trains>&version=<change_number>
//        Esimerkki: /live-trains/station/HKI
//        Esimerkki: /live-trains/station/HKI?arrived_trains=5&arriving_trains=5&departed_trains=5&departing_trains=5&include_nonstopping=false
//

    // Varikko.LueJunanJsonData(String URL)


}
