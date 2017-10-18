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
        int jnaNumero = 6;
        Scanner jnaScnr = new Scanner(System.in);

        junatunnus:
        for (;;) {

            System.out.println("Löydä haluamasi junan aikataulu. Anna junan tunnus, esim: " +jnaTyyppi+jnaNumero);
            String jnaHaku = jnaScnr.nextLine().toUpperCase();
            jnaTyyppi = jnaHaku.replaceAll("[^A-Z]", "");
            jnaNumero = Integer.parseInt(jnaHaku.replaceAll("[^0-9]", ""));
//            System.out.println(jnaTyyppi+jnaNumero);

            for (Juna j : Varikko.junat) {
                if ((j.getTrainType().equals(jnaTyyppi)) && (j.getTrainNumber() == jnaNumero)) {
                    System.out.println(j);
                }
            }


        }

    }

}
