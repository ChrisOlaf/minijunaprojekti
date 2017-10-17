
import java.util.List;
import java.util.Scanner;

public class Matkahaku {

    // Antti

    public static void hae() {

        // /live-trains/station/HKI/TPE
        // /live-trains/station/<departure_station_code>/<arrival_station_code>?departure_date=<departure_date>&from=<from>&to=<to>&limit=<limit>
        // Varikko.LueJunanJsonData(String URL)
        
        Scanner matkalue = new Scanner(System.in);

        String lahtoAsema = "HKI";
        String kohdeAsema = "TKU";

        System.out.println("\nHaetaan junat tiettyjen asemien välillä seuraavaan vuorokauden aikana.\n");

        // kysy lähtöasema
        lahtoasema:
        for (;;) {

            System.out.println("Mikä on lähtöasema? (kolmikirjaiminen asemakoodi, esim. HKI)");
            lahtoAsema = matkalue.nextLine().toUpperCase();

            if (lahtoAsema.length() == 3) {
                break lahtoasema;
            }

            System.out.println("\nSyötä kolmikirjaiminen koodi, esim. HKI (Helsinki) tai TPE (Tampere).");
            continue;

        }

        // kysy kohdeasema
        kohdeasema:
        for (;;) {

            System.out.println("Mikä on kohdeasema? (kolmikirjaiminen asemakoodi, esim. TKU)");
            kohdeAsema = matkalue.nextLine().toUpperCase();

            if (kohdeAsema.length() == 3) {
                break kohdeasema;
            }

            System.out.println("\nSyötä kolmikirjaiminen koodi, esim. HKI (Helsinki) tai TPE (Tampere).");
            continue;

        }

        System.out.println("");

        String url = "/live-trains/station/" + lahtoAsema + "/" + kohdeAsema;

        // hae uusin data Varikolle
        Varikko.lueJunanJSONData(url);

        // hae junien lista käyttöön paikalliseen muuttujaan
        List<Juna> matkajunat = Varikko.junat;

        for (Juna j : Varikko.junat) {

            String print;

            if (!("".equals(j.getCommuterLineID()))) {

                print = j.getCommuterLineID() + "-juna"
                        + ", Liikkeessä: " + j.isRunningCurrently() + "\n"
                        + "Lähtö -- " + lahtoAsema + ": " + j.timeTableRows.get(0).getScheduledTime() + "\n"
                        + "Saapuminen -- " + kohdeAsema + ": " + j.timeTableRows.get(j.timeTableRows.size() - 1).getScheduledTime()
                        + "\n";

            } else {

                print = "Juna " + j.getTrainType() + " " + j.getTrainNumber()
                        + ", Liikkeessä: " + j.isRunningCurrently() + "\n"
                        + "Lähtö -- " + lahtoAsema + ": " + j.timeTableRows.get(0).getScheduledTime() + "\n"
                        + "Saapuminen -- " + kohdeAsema + ": " + j.timeTableRows.get(j.timeTableRows.size() - 1).getScheduledTime()
                        + "\n";

            }

            System.out.println(print);

        }

    }

}
