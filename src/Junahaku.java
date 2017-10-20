import java.util.*;

public class Junahaku {
    /**
     * Haetaan junaa tyypin ja numeron yhdistelmällä.
     * Tulostetaan onnistuneella haulla viimeisimmän junan tietoja.
     *
     * @author Risto Rautanen
     */

    static public void hae() {

        String jnaTyyppi;
        int jnaNumero;
        int virheCounter = 0;
        Scanner jnaScnr = new Scanner(System.in);
        System.out.println("Hae junan tapahtumatietoja junan tunnuksella. Anna junatunnus, esim: IC 404.");

        junatunnus:
        for (; ; ) {

            // Tarkistetaan virheellisten yritysten määrä ja tarjotaan mahdollisuutta palata päävalikkoon.
            if (virheCounter >= 2) {
                System.out.println("Haluatko palata päävalikkoon?\nVastaa: K/E\n");
                String virheVastaus = jnaScnr.nextLine();
                for (; ;) {
                    if (virheVastaus.toUpperCase().equals("K")) {
                        break junatunnus;
                    }
                    else {
                        System.out.println("Hae junan tapahtumatietoja junan tunnuksella. Anna junatunnus, esim: IC 404.");
                        virheCounter = 0;
                        break;
                    }
                }
            }

            // Parsitaan käyttäjän syötteestä asianmukainen junatunnus.
            String jnaHaku = jnaScnr.nextLine().toUpperCase();
            jnaTyyppi = jnaHaku.replaceAll("[^A-Z]", "");
            try {
                jnaNumero = Integer.parseInt(jnaHaku.replaceAll("[^0-9]", ""));
            } catch (NumberFormatException e) {
                virheCounter ++;
                System.out.println("*** Junan numero puuttui.\n*** Anna tunnus uudelleen.\n");
                continue;
            }
            System.out.println("Tehdään haku junatunnuksella: " + jnaTyyppi + jnaNumero + ".\n");

            // Kootaan URL josta dataa haetaan.
            String url = "/trains/latest/" + jnaNumero;
            Varikko.lueJunanJSONData(url);

            // Tarkistetaan onko junan numerolla taulukkoa.
            if (Varikko.junat.size() == 0) {
                virheCounter ++;
                System.out.println("*** Antamallasi junan numerolla ei löytynyt junaa.\n*** Anna tunnus uudelleen.\n");
                continue;
            }

            // Käydään läpi löytyykö junan tyyppi+numero -yhdistelmällä tietoa.
            for (Juna j : Varikko.junat) {

                // Jos junatyypillä JA junanumerolla löytyy datasta tietoa, muodostetaan puu menneitä junia ja toinen puu tulevia junia varten.
                if (jnaTyyppi.equals(j.getTrainType()) && (jnaNumero == j.getTrainNumber())) {
                    String jnaTunnus = jnaTyyppi + jnaNumero;
                    String jnaAsema1 = "", jnaAsema2 = "";
                    String jnaAika1 = "", jnaAika2 = "";
                    String jnaTapahtuma1 = "", jnaTapahtuma2 = "";
                    String jnaRaide1 = "", jnaRaide2 = "";
                    Date current = new Date();
                    TreeMap<Long, String> jnaMenneet = new TreeMap<>();
                    TreeMap<Long, String> jnaTulevat = new TreeMap<>();

                    // Käydään läpi onnistuneen haun jälkeen kyseisen junan aikataulu.
                    for (TimeTableRow t : j.getTimeTableRows()) {

                        // Tiputetaan hausta pois asemat joilla ei pysähdytä.
                        if (t.isTrainStopping() == false) {
                            continue;
                        }

                        // Jaetaan juna-aikataulun menneisiin ja tuleviin tapahtumiin.
                        Long aika = (current.getTime()) - (t.getTime().getTime());
                        if (aika > 0.00) {
                            jnaMenneet.put(aika, t.getStationShortCode());
                        } else if (aika <= 0.00){
                            jnaTulevat.put(Math.abs(aika), t.getStationShortCode());
                        }

                    }

                    // Tarkistetaan löytyykö junatunnuksella menneitä ja tulevia asemia.
                    try {
                        jnaAsema1 = jnaMenneet.get(jnaMenneet.firstKey());
                        jnaAsema2 = jnaTulevat.get(jnaTulevat.firstKey());
                    } catch (Exception e) {
                        virheCounter ++;
                        System.out.println("*** Antamallasi junatunnuksella ei löydy aktiivisia junia.\n*** Anna tunnus uudelleen.\n");
                        break;
                    }

                    // Poimitaan tietoa viimeisimmästä tapahtumasta ennen nykyhetkeä.
                    for (TimeTableRow t : j.getTimeTableRows()) {
                        if (t.getTime().getTime() > current.getTime()) {
                            break;
                        }
                        if (t.getStationShortCode().equals(jnaAsema1) /*&& t.getType().equals("ARRIVAL")*/) {
                            jnaAika1 = t.getActualTime();
                            jnaTapahtuma1 = t.getType();
                            jnaRaide1 = t.getCommercialTrack();
                        }
                    }

                    // Poimitaan tietoa seuraavasta tapahtumasta nyyhetken jälkeen.
                    for (TimeTableRow t : j.getTimeTableRows()) {
                        if (t.getStationShortCode().equals(jnaAsema2) /*&& t.getType().equals("DEPARTURE")*/) {
                            jnaAika2 = t.getActualTime();
                            jnaTapahtuma2 = t.getType();
                            jnaRaide2 = t.getCommercialTrack();
                            break;
                        }
                    }

                    // Tulostetaan onnistuneen junatunnushaun pyydetyt tiedot.
                    // TODO - Junasta olisi ollut kiva saada ulos myös päivän koko aikataulu, mutta aika loppui kesken.

                    System.out.println(jnaTunnus
                            + "\n* * *\nViimeisin tapahtuma: "+ jnaTapahtuma1
                            + " / "+ jnaAika1
                            + " / Asemalla: " + jnaAsema1
                            + " - "+ Asema.asemat.get(jnaAsema1)
                            + ", Raide: " + jnaRaide1
                            + "\nSeuraava tapahtuma: "+ jnaTapahtuma2
                            + " / "+ jnaAika2
                            + " / Asemalla: " + jnaAsema2
                            + " - "+ Asema.asemat.get(jnaAsema2)
                            + ", Raide: " + jnaRaide2
                    );

                    // Onnistuneen haun jälkeen rikotaan looppi ja palataan päävalikkoon.
                    break junatunnus;
                }
                // Jos junatunnuksen tyyppi ja numero olivat oikein, mutta
                virheCounter ++;
                System.out.println("*** Antamallasi tunnuksella ei löytynyt junaa.\n*** Anna tunnus uudelleen.\n");
                continue junatunnus;
            }
        }
    }
}