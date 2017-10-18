import java.util.Scanner;

public class Junahaku {
    /**
     * Haetaan junaa tyypin ja numeron yhdistelmällä.
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
        for (; ;) {

            System.out.println("Löydä haluamasi junan aikataulu. Anna junan tunnus, esim: IC 404.");
            String jnaHaku = jnaScnr.nextLine().toUpperCase();
            jnaTyyppi = jnaHaku.replaceAll("[^A-Z]", "");
            jnaNumero = Integer.parseInt(jnaHaku.replaceAll("[^0-9]", ""));
            System.out.println("Tehdään haku junatunnuksella: " + jnaTyyppi + jnaNumero + ".");
//            System.out.println(jnaTyyppi+jnaNumero);
            String url = "/trains/latest/" + jnaNumero;
            Varikko.lueJunanJSONData(url);

            for (Juna j : Varikko.junat) {
//                System.out.println("testi");
                if ((j.getTrainType().equals(jnaTyyppi)) && (j.getTrainNumber() == jnaNumero)) {
                    System.out.println(j.getTrainType() + j.getTrainNumber() + " lähtee " + j.departureDate);
                    break junatunnus;
                }
                System.out.println("Antamallasi tunnuksella ei löytynyt junaa. Anna tunnus uudelleen.");
                continue;
//                System.out.println("testi2");
            }
        }
    }
}
