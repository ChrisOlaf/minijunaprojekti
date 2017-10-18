import java.util.Scanner;

public class Junahaku {
    /**
     * Haetaan junaa tyypin ja numeron yhdistelmällä.
     * Tulostetaan onnistuneella haulla viimeisimmän junan tietoja.
     *
     * @author Risto Rautanen
     */
    public static void main(String[] args) {
        hae();
    }

    static public void hae() {

        String jnaTyyppi = "IC";
        int jnaNumero = 404;
        Scanner jnaScnr = new Scanner(System.in);

        junatunnus:
        for (; ; ) {

            System.out.println("Löydä haluamasi junan aikataulu. Anna junan tunnus, esim: IC 404.");
            String jnaHaku = jnaScnr.nextLine().toUpperCase();
            jnaTyyppi = jnaHaku.replaceAll("[^A-Z]", "");
            jnaNumero = Integer.parseInt(jnaHaku.replaceAll("[^0-9]", ""));
            System.out.println("Tehdään haku junatunnuksella: " + jnaTyyppi + jnaNumero + ".\n");
//            System.out.println(jnaTyyppi+jnaNumero);
            String url = "/trains/latest/" + jnaNumero;
            System.out.println(url);

            Varikko.lueJunanJSONData(url);
            if (Varikko.junat.size() == 0) {                    // Tarkistetaan onko junan numerolla taulukkoa.
                System.out.println("Antamallasi tunnuksella ei löytynyt junaa. Anna tunnus uudelleen.\n");
                continue;
            }

            for (Juna j : Varikko.junat) {                      // Käydään läpi löytyykö junan tyyppi+numero -yhdistelmällä tietoa.
                if (jnaTyyppi.equals(j.getTrainType()) && (jnaNumero == j.getTrainNumber())) {
                    System.out.println(j.getTrainType() + j.getTrainNumber() + " lähtee " + j.getDepartureDate());
                    break junatunnus;
                }
                System.out.println("Antamallasi tunnuksella ei löytynyt junaa. Anna tunnus uudelleen.\n");
                continue junatunnus;
            }
        }
    }
}
