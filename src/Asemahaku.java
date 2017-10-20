import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Asemahaku pyytää käyttäjältä aseman ja kysyy, haluaako käyttäjä lähtevien vai saapuvien junien tiedot.
 * Pyydetyille junille tulostetaan junan numero/kirjain, pääte- tai lähtöasema ja lähtö- tai saapumisaika.
 *
 * @author Titta Kivikoski
 * @author Anna-Reetta Kohonen
 */

public class Asemahaku {
    //Staattinen muuttuja käyttäjän syötettä varten
    static String annettuAsema;
    static String luettu;
    static Date nykyhetki = new Date();

    //Asemaluokan päämetodi, joka koostuu kahdesta osametodista
    public static void hae() {
    //Kysytään, minkä aseman junat käyttäjä haluaa nähdä
        Scanner lukija = new Scanner(System.in);
        kysyAsema(lukija);
    //Kysytään, haluaako käyttäjä saapuvat vai lähtevät junat
        kysySaapuvatTaiLahtevat(lukija);
    }

    //Kysytään, minkä aseman junat käyttäjä haluaa nähdä
    public static void kysyAsema(Scanner lukija) {
        System.out.println("Mikä asema? (Syötä asemakoodi, esim. HKI (Helsinki) tai TPE (Tampere)\n"
                    + "Jätä kenttä tyhjäksi, jos haluat nähdä listan asemista. Numero '0' lopettaa.");
        annettuAsema = lukija.nextLine().toUpperCase();

        if ("".equals(annettuAsema)) {
            Asema.listaaAsemat();
        } else if ("0".equals(annettuAsema)) {
            return;
        }

        while (!(Asema.asemat.containsKey(annettuAsema))) {
            System.out.println("\nSyötä asemakoodi, esim. HKI (Helsinki) tai TPE (Tampere).\n");
            annettuAsema = lukija.nextLine().toUpperCase();

            if ("".equals(annettuAsema)) {
                Asema.listaaAsemat();
            } else if ("0".equals(annettuAsema)) {
                return;
            }
        }
    }

    //Kysytään, haluaako käyttäjä saapuvat vai lähtevät junat
    public static void kysySaapuvatTaiLahtevat(Scanner lukija) {
        if ("0".equals(annettuAsema)) {
            return;
        }
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

    //Haetaan lähtevät junat käyttäjän antaman aseman mukaan
    public static void haeLahtevatJunatAsemanMukaan() {
        String url = "/live-trains/station/" + annettuAsema + "?minutes_before_departure=120&minutes_after_departure=0&minutes_before_arrival=0&minutes_after_arrival=0";
        // hae uusin data Varikolle
        Varikko.lueJunanJSONData(url);
        // hae junien lista käyttöön paikalliseen muuttujaan
        List<Juna> asemaJunat = Varikko.junat;
        //Järjestetään junat ajan mukaan
        Collections.sort(asemaJunat, new JunatAsemanMukaanComparator());

        //Filtteröidään asemalta lähtevät junat ja tulostetaan junan numero/lähijunan kirjain, pääteasema ja lähtöaika
        for (Juna j : asemaJunat) {
            for (int i = 0; i < j.getTimeTableRows().size() - 1; i++) {
                if (j.getTimeTableRows().get(i).getStationShortCode().equals(annettuAsema)
                        && j.getTimeTableRows().get(i).getType().equals("DEPARTURE")
                        && ((nykyhetki.getTime()) < j.getTimeTableRows().get(i).getTime().getTime())) {
                    if (!("".equals(j.getCommuterLineID()))) {
                        System.out.println(
                                "" + j.getCommuterLineID() + "-juna"
                                        + " " + Asema.asemat.get(j.getTimeTableRows().get(j.getTimeTableRows().size() - 1).getStationShortCode())
                                        + "\n" + "Lähtöaika " + j.getTimeTableRows().get(i).getActualTime() + "\n"
                                        + "Laiturilta " + j.getTimeTableRows().get(i).getCommercialTrack() + "\n");
                    } else {
                        System.out.println(
                                "" + j.getTrainType() + j.getTrainNumber()
                                        + " " + Asema.asemat.get(j.getTimeTableRows().get(j.getTimeTableRows().size() - 1).getStationShortCode())
                                        + "\n" + "Lähtöaika " + j.getTimeTableRows().get(i).getActualTime() + "\n"
                                        + "Laiturilta " + j.getTimeTableRows().get(i).getCommercialTrack() + "\n");
                    }
                }
            }
        }
    }

    //Haetaan saapuvat junat käyttäjän antaman aseman mukaan
    public static void haeSaapuvatJunatAsemanMukaan() {
        String url = "/live-trains/station/" + annettuAsema + "?minutes_before_departure=0&minutes_after_departure=0&minutes_before_arrival=120&minutes_after_arrival=0";

        // hae uusin data Varikolle
        Varikko.lueJunanJSONData(url);

        // hae junien lista käyttöön paikalliseen muuttujaan
        List<Juna> asemaJunat = Varikko.junat;
        //Järjestetään junat ajan mukaan
        Collections.sort(asemaJunat, new JunatAsemanMukaanComparator());

        //Filtteröidään asemalle saapuvat junat ja tulostetaan junan numero/lähijunan kirjain, lähtöasema ja saapumisaika
        for (Juna j: asemaJunat) {
            for (int i = 0; i < j.getTimeTableRows().size()-1; i++) {
                if (j.getTimeTableRows().get(i).getStationShortCode().equals(annettuAsema)
                        && j.getTimeTableRows().get(i).getType().equals("ARRIVAL")
                        && ((nykyhetki.getTime()) < j.getTimeTableRows().get(i).getTime().getTime())) {
                    if (!("".equals(j.getCommuterLineID()))) {
                        System.out.println(
                                "" + j.getCommuterLineID() + "-juna"
                                        + " " + Asema.asemat.get(j.getTimeTableRows().get(0).getStationShortCode())
                                        + "\n" + "Saapumisaika: " + j.getTimeTableRows().get(i).getActualTime() + "\n"
                                        + "Laiturille " + j.getTimeTableRows().get(i).getCommercialTrack() + "\n");
                    } else {
                        System.out.println(
                                "" + j.getTrainType() + j.getTrainNumber()
                                        + " " + Asema.asemat.get(j.getTimeTableRows().get(0).getStationShortCode())
                                        + "\n" + "Saapumisaika: " + j.getTimeTableRows().get(i).getActualTime() + "\n"
                                        + "Laiturille " + j.getTimeTableRows().get(i).getCommercialTrack() + "\n");
                    }
                }
            }
        }
    }
}
