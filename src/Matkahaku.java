
import java.util.List;
import java.util.Scanner;

/**
 * Matkahaku pyytää käyttäjältä syötteet matkan lähtöasemasta
 * ja pääteasemasta.
 *
 * Syötteitä käytetään Varikko-luokan Varikko.lueJunanJSONData(URL)
 * -haun ajamiseen sopivalla muodostetulla URL-syötteellä.
 *
 * Lopuksi tulostetaan lista junista kyseisellä matkavälillä
 * seuraavan vuorokauden aikana.
 *
 * @author Antti Pöyhönen
 */

public class Matkahaku {

    // lähtöaseman ja kohdeaseman alustus oletus-syötteillä
    private static String lahtoAsema = "TKU";
    private static String kohdeAsema = "HKI";

    public static void hae() {

        // /live-trains/station/HKI/TPE
        // /live-trains/station/<departure_station_code>/<arrival_station_code>?departure_date=<departure_date>&from=<from>&to=<to>&limit=<limit>
        // Varikko.LueJunanJsonData(String URL)
        
        Scanner in = new Scanner(System.in);

        kysyAsemat(in);

        haeJunat();

        printJunat();

    }

    // kysy käyttäjältä kaksi syötettä, lähtöAsema ja kohdeAsema
    private static void kysyAsemat(Scanner in) {

        // kysy lähtöasema
        lahtoasema:
        for (;;) {

            System.out.println("Mikä on matkan lähtöasema? (kaksi- tai kolmikirjaiminen asemakoodi, esim. HKI)");
            lahtoAsema = in.nextLine().toUpperCase();

            if (Asema.asemat.containsKey(lahtoAsema)) {
                break lahtoasema;
            }

            System.out.println("\nSyötä kaksi- tai kolmikirjaiminen koodi, esim. HKI (Helsinki) tai TPE (Tampere).");
            continue;

        }

        // kysy kohdeasema
        kohdeasema:
        for (;;) {

            System.out.println("Mikä on matkan kohdeasema? (kaksi- tai kolmikirjaiminen asemakoodi, esim. TKU)");
            kohdeAsema = in.nextLine().toUpperCase();

            if (Asema.asemat.containsKey(kohdeAsema)) {
                break kohdeasema;
            }

            System.out.println("\nSyötä kaksi- tai kolmikirjaiminen koodi, esim. HKI (Helsinki) tai TPE (Tampere).");
            continue;

        }

    }

    // muodosta matkojen hakemisen URL-pääte syötteistä ja hae junat Varikko-luokan taulukkoon
    private static void haeJunat() {

        System.out.println("\nHaetaan junat "
                + Asema.asemat.get(lahtoAsema)
                + " ja "
                +  Asema.asemat.get(kohdeAsema)
                + " välillä seuraavaan vuorokauden aikana.\n");

        System.out.println("");

        // muodosta haettava url-pääte asemista
        String url = "/live-trains/station/" + lahtoAsema + "/" + kohdeAsema;

        // hae uusin data Varikolle
        Varikko.lueJunanJSONData(url);

    }

    // käy läpi Varikko-luokan junien haettu taulukko ja printtaa kaikki junat
    // eri tuloste riippuen onko kyseessä tunnuksellinen paikallisjuna vai pitkänmatkanjuna
    private static void printJunat() {

        for (Juna j : Varikko.junat) {

            String print;

            // jos junalla on paikallisjunan kirjaintunnus, printtaa paikallisjuna-muodossa
            if (!("".equals(j.getCommuterLineID()))) {

                print = j.getCommuterLineID() + "-juna"
                        + ", Liikkeessä: " + j.isRunningCurrently() + "\n"
                        + "Lähtö -- " + Asema.asemat.get(lahtoAsema) + ": " + j.timeTableRows.get(0).getScheduledTime() + "\n"
                        + "Saapuminen -- " + kohdeAsema + ": " + j.timeTableRows.get(j.timeTableRows.size() - 1).getScheduledTime()
                        + "\n";

                // muuten printtaa yleisessä muodossa junan numerolla
            } else {

                print = "Juna " + j.getTrainType() + " " + j.getTrainNumber()
                        + ", Liikkeessä: " + j.isRunningCurrently() + "\n"
                        + "Lähtö -- " + Asema.asemat.get(kohdeAsema) + ": " + j.timeTableRows.get(0).getScheduledTime() + "\n"
                        + "Saapuminen -- " + kohdeAsema + ": " + j.timeTableRows.get(j.timeTableRows.size() - 1).getScheduledTime()
                        + "\n";

            }

            System.out.println(print);

        }

    }

}
