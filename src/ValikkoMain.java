import java.util.Scanner;

/**
 * ValikkoMain aloittaa ohjelman, hakee asemien listan Asema-luokkaan
 * kaikkien metodien myöhempää käyttöä varten, kysyy halutun haun tyypin
 * ja ajaa halutun haun .hae()-metodin.
 *
 * @author Antti Pöyhönen
 */

public class ValikkoMain {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        // tulostaa ASCII logon
        ValikkoMain.ASCIIlogo();

        // haetaan lista asemista kaikkia metodeja varten valmiiksi
        Asema.haeAsemat();

        // loop valikko, josta pääse pois lopettamalla
        end:
        for (; ; ) {

            String valinta = "0";

            System.out.println("\nPäävalikko - Mitä haluat tehdä?\n\n"
                    + "1 -- Hae junan tiedot\n"
                    + "2 -- Hae aseman tiedot (lähtevät / saapuvat)\n"
                    + "3 -- Hae matkan perusteella (asemalta asemalle)\n"
                    + "0 -- Lopeta ohjelma\n");

            // välitallennetaan annettu valinta
            valinta = in.nextLine();

            System.out.println("");

            // tarkistetaan switchillä, mitä valinta tarkoittaa
            outer:
            for (; ; ) {
                switch (valinta) {
                    case "1":
                        // System.out.println("Junahaku.hae()");
                        Junahaku.hae();
                        break outer;
                    case "2":
                        // System.out.println("Asemahaku.hae()");
                        Asemahaku.hae();
                        break outer;
                    case "3":
                        // System.out.println("Matkahaku.hae()");
                        Matkahaku.hae();
                        break outer;
                    case "0":
                        System.out.println("Lopetetaan ohjelma. Paina ENTER.");
                        in.nextLine();
                        break end;
                    default:
                        System.out.println("Et valinnut valintaa 1, 2, 3 tai 0. Valitse uudelleen!");
                        break outer;
                }
            }

//            System.out.println("Paina ENTER jatkaaksesi.");
//            in.nextLine();

        }

        // ohjelman lopuksi suljetaan Scanner, kun kaikki metodit lopettaneet
        in.close();

    }

    private static void ASCIIlogo() {

        String [] logo = {
            "",
            " \\ \\    / /  __ \\  |__ \\  / _ \\",
            "  \\ \\  / /| |__) |    ) || | | |",
            "   \\ \\/ / |  _  /    / / | | | |",
            "    \\  /  | | \\ \\   / /_ | |_| |",
            "     \\/   |_|  \\_\\ |____(_)___/ ",
            " "
        };

        for (String s : logo) {
            System.out.println(s);
        }
    }

}
