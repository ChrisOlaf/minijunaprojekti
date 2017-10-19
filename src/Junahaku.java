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

            String url = "/trains/latest/" + jnaNumero;         // Kootaan URL josta dataa haetaan.
            Varikko.lueJunanJSONData(url);

            if (Varikko.junat.size() == 0) {                    // Tarkistetaan onko junan numerolla taulukkoa.
                virheCounter ++;
                System.out.println("*** Antamallasi junan numerolla ei löytynyt junaa.\n*** Anna tunnus uudelleen.\n");
                continue;
            }

            for (Juna j : Varikko.junat) {                      // Käydään läpi löytyykö junan tyyppi+numero -yhdistelmällä tietoa.
                if (jnaTyyppi.equals(j.getTrainType()) && (jnaNumero == j.getTrainNumber())) {
                    String jnaTunnus = jnaTyyppi + jnaNumero;
                    String jnaAsema1 = "", jnaAsema2 = "";
                    String jnaAika1 = "", jnaAika2 = "";
                    String jnaTapahtuma1 = "", jnaTapahtuma2 = "";
                    String jnaRaide1 = "", jnaRaide2 = "";
                    Date current = new Date();
                    TreeMap<Long, String> jnaMenneet = new TreeMap<>();
                    TreeMap<Long, String> jnaTulevat = new TreeMap<>();

                    for (TimeTableRow t : j.getTimeTableRows()) {
                        if (t.isTrainStopping() == false) {
                            continue;
                        }
                        Long aika = (current.getTime()) - (t.getTime().getTime());
                        if (aika > 0.00) {
                            jnaMenneet.put(aika, t.getStationShortCode());
                        } else if (aika <= 0.00){
                            jnaTulevat.put(Math.abs(aika), t.getStationShortCode());
                        }

                    }

                    try {
                        jnaAsema1 = jnaMenneet.get(jnaMenneet.firstKey());
                        jnaAsema2 = jnaTulevat.get(jnaTulevat.firstKey());
                    } catch (Exception e) {
                        virheCounter ++;
                        System.out.println("*** Antamallasi junatunnuksella ei löydy aktiivisia junia.\n*** Anna tunnus uudelleen.\n");
                        break;
                    }

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

                    for (TimeTableRow t : j.getTimeTableRows()) {
                        if (t.getStationShortCode().equals(jnaAsema2) /*&& t.getType().equals("DEPARTURE")*/) {
                            jnaAika2 = t.getActualTime();
                            jnaTapahtuma2 = t.getType();
                            jnaRaide2 = t.getCommercialTrack();
                            break;
                        }
                    }


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

                    break junatunnus;
                }
                virheCounter ++;
                System.out.println("*** Antamallasi tunnuksella ei löytynyt junaa.\n*** Anna tunnus uudelleen.\n");
                continue junatunnus;
            }
        }
//        jnaScnr.close();
    }
}