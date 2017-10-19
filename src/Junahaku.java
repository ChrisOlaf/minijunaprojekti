import java.util.Scanner;

public class Junahaku {
    /**
     * Haetaan junaa tyypin ja numeron yhdistelmällä.
     * Tulostetaan onnistuneella haulla viimeisimmän junan tietoja.
     *
     * @author Risto Rautanen
     */
    public static void main(String[] args) {
        Asema.haeAsemat();
        hae();
    }

    static public void hae() {

        String jnaTyyppi = "IC";
        int jnaNumero = 404;
        Scanner jnaScnr = new Scanner(System.in);
        System.out.println("Löydä haluamasi junan aikataulu. Anna junan tunnus, esim: IC 404.");

        junatunnus:
        for (; ; ) {

            String jnaHaku = jnaScnr.nextLine().toUpperCase();
            jnaTyyppi = jnaHaku.replaceAll("[^A-Z]", "");
            try {
                jnaNumero = Integer.parseInt(jnaHaku.replaceAll("[^0-9]", ""));
            } catch (NumberFormatException e) {
                System.out.println("Junan numero puuttui. Yritä uudelleen!\n");
                continue;
            }
            System.out.println("Tehdään haku junatunnuksella: " + jnaTyyppi + jnaNumero + ".\n");

            String url = "/trains/latest/" + jnaNumero;         // Kootaan URL josta dataa haetaan.
            Varikko.lueJunanJSONData(url);

            if (Varikko.junat.size() == 0) {                    // Tarkistetaan onko junan numerolla taulukkoa.
                System.out.println("Antamallasi junan numerolla ei löytynyt junaa. Anna tunnus uudelleen.\n");
                continue;
            }

            for (Juna j : Varikko.junat) {                      // Käydään läpi löytyykö junan tyyppi+numero -yhdistelmällä tietoa.
                if (jnaTyyppi.equals(j.getTrainType()) && (jnaNumero == j.getTrainNumber())) {
                    String jasema = j.getTimeTableRows().get(0).getStationShortCode();
                    System.out.println(j.getTrainType() + j.getTrainNumber()
                            + " lähtee " + j.getTimeTableRows().get(0).getScheduledTime()
                            + " asemalta: " + Asema.asemat.get(jasema)
                            + " / " + jasema + ".");
                    break junatunnus;
                }
                System.out.println("Antamallasi tunnuksella ei löytynyt junaa. Anna tunnus uudelleen.\n");
                continue junatunnus;
            }
        }
    }
}
