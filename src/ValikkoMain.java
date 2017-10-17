import java.util.Scanner;

public class ValikkoMain {

    public static void main(String[] args) {

        end:
        for (; ; ) {

            String valinta = "0";

            Scanner in = new Scanner(System.in);

            System.out.println("\nPäävalikko - Mitä haluat tehdä?\n\n"
                    + "1 -- Hae junan tiedot\n"
                    + "2 -- Hae aseman tiedot (lähtevät / saapuvat)\n"
                    + "3 -- Hae matkan perusteella (asemalta asemalle)\n"
                    + "0 -- Lopeta ohjelma\n");

            valinta = in.nextLine();

            System.out.println("");

            outer:
            for (; ; ) {
                switch (valinta) {
                    case "1":
                        System.out.println("Junahaku.hae()");
                        // Junahaku.hae();
                        break outer;
                    case "2":
                        System.out.println("Asemahaku.hae()");
                        // Asemahaku.hae();
                        break outer;
                    case "3":
                        System.out.println("Matkahaku.hae()");
                        // Matkahaku.hae();
                        break outer;
                    case "0":
                        System.out.println("Lopetetaan ohjelma.");
                        break end;
                    default:
                        System.out.println("Et valinnut valintaa 1, 2, 3 tai 0. Valitse uudelleen!\n");
                }
            }

//            System.out.println("Paina ENTER jatkaaksesi.");
//            in.nextLine();

        }

    }

}
