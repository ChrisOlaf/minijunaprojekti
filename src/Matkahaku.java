import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
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

    // lähtöaseman ja kohdeaseman alustus
    private static String lahtoAsema = "";
    private static String kohdeAsema = "";

    public static void hae() {

        Scanner in = new Scanner(System.in);

        kysyAsema(in, "lähtöasema");

        System.out.println("");

        kysyAsema(in, "kohdeasema");

        haeJunat(in);

        printJunat();

    }

    // kysy käyttäjältä kaksi syötettä, lähtöAsema ja kohdeAsema
    private static void kysyAsema(Scanner in, String s) {

        outer:
        for (;;) {

            System.out.println("Mikä on matkan " + s + "? (2-4 -kirjaiminen asemakoodi, esim. 'HKI')\n"
                    + "Jos haluat nähdä listan asemien lyhenteistä ja nimistä, jätä asemakoodi tyhjäksi ja paina ENTER");

            String check = "";

            if ("lähtöasema".equals(s)) {
                lahtoAsema = in.nextLine().toUpperCase();
                check = lahtoAsema;
            } else if ("kohdeasema".equals(s)) {
                kohdeAsema = in.nextLine().toUpperCase();
                check = kohdeAsema;
            }

            if ("".equals(check)) {
                Asema.listaaAsemat();
                continue;
            }

            if (Asema.asemat.containsKey(lahtoAsema)) {
                break outer;
            }

            System.out.println("\nSyötä 2-4 -kirjaiminen koodi, esim. 'HKI' (Helsinki) tai 'tpe' (Tampere).");
            continue;

        }

    }

    // Avoimen datan esimerkkilinkit:
    // /live-trains/station/HKI/TPE
    // /live-trains/station/<departure_station_code>/<arrival_station_code>?departure_date=<departure_date>&from=<from>&to=<to>&limit=<limit>
    // Varikko.LueJunanJsonData(String URL)

    // muodosta matkojen hakemisen URL-pääte syötteistä ja hae junat Varikko-luokan taulukkoon
    private static void haeJunat(Scanner in) {

        System.out.println("\nHaetaan junat "
                + Asema.asemat.get(lahtoAsema)
                + " ja "
                +  Asema.asemat.get(kohdeAsema)
                + " välillä seuraavaan vuorokauden aikana."
                + "\n\n"
                + "Paina ENTER");

        in.nextLine();

        // muodosta haettava url-pääte asemista
        String url = "/live-trains/station/" + lahtoAsema + "/" + kohdeAsema + "/";

        // hae uusin data Varikolle
        Varikko.lueJunanJSONData(url);

    }

    // käy läpi Varikko-luokan junien haettu taulukko ja printtaa kaikki junat
    // eri tuloste riippuen onko kyseessä tunnuksellinen paikallisjuna vai pitkänmatkanjuna
    private static void printJunat() {

        if (Varikko.junat == null) return;

        for (Juna j : Varikko.junat) {

            // hakee liikkuuko juna jo
            String liikkuu = "";

            if (j.isRunningCurrently()) {
                liikkuu = "Lähtenyt";
            } else {
                liikkuu = "Ei vielä lähtenyt";
            }

            // jos junalla on paikallisjunan kirjaintunnus, printtaa paikallisjuna-muodossa
            // muuten printtaa yleisessä muodossa junan numerolla
            String junanNimi = "";

            if (!("".equals(j.getCommuterLineID()))) {
                junanNimi = j.getCommuterLineID() + "-juna";
            } else {
                junanNimi = "Juna " + j.getTrainType() + " " + j.getTrainNumber();
            }

            // hakee junan lähtö- ja saapumisajat, lokalisoitu TimeTableRow getScheduledTime()-metodissa
            String lahtoAika = "", kohdeAika = "";

            for (TimeTableRow t : j.getTimeTableRows()) {
                if (lahtoAsema.equals(t.getStationShortCode()) && "DEPARTURE".equals(t.getType())) {
                    lahtoAika = t.getScheduledTime();
                }
            }

            for (TimeTableRow t : j.getTimeTableRows()) {
                if (kohdeAsema.equals(t.getStationShortCode()) && "ARRIVAL".equals(t.getType())) {
                    kohdeAika = t.getScheduledTime();
                }
            }

            // printtaa koonnin kaikesta tiedosta
            System.out.println(junanNimi
                    + ", Liikkeessä: " + liikkuu + "\n"
                    + "Lähtö -- " + Asema.asemat.get(lahtoAsema) + " -- " + lahtoAika + "\n"
                    + "Saapuminen -- " + Asema.asemat.get(kohdeAsema) + " -- " + kohdeAika
                    + "\n");

        }

        System.out.println("Löytyi " + Varikko.junat.size() + " junaa.");

    }

}
