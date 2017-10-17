
import java.util.List;
import java.util.Scanner;

public class Matkahaku {

    // Antti
    public static void hae() {

        // /live-trains/station/HKI/TPE
        // /live-trains/station/<departure_station_code>/<arrival_station_code>?departure_date=<departure_date>&from=<from>&to=<to>&limit=<limit>
        
        // Varikko.LueJunanJsonData(String URL)
                
        Scanner matkalue = new Scanner(System.in);

        String lahtoAsema = "HKI";
        String kohdeAsema = "TKU";

        System.out.println("\nHaetaan junat tiettyjen asemien välillä seuraavaan vuorokauden aikana.\n\n");

        // kysy lähtöasema
        lahtoasema:
        for (;;) {
            
            System.out.println("Mikä on lähtöasema? (kolmikirjaiminen asemakoodi, esim. HKI)");
            lahtoAsema = matkalue.nextLine().toUpperCase();
            
            if (lahtoAsema.length() == 3) {
                break lahtoasema;
            }
            
            System.out.println("\nSyötä kolmikirjaiminen koodi, esim. HKI (Helsinki) tai TPE (Tampere).\n");
            continue;

        }
        
        // kysy kohdeasema
        kohdeasema:
        for (;;) {
            
            System.out.println("Mikä on kohdeasema? (kolmikirjaiminen asemakoodi, esim. TKU)");
            kohdeAsema = matkalue.nextLine().toUpperCase();
            
            if (kohdeAsema.length() == 3) {
                break kohdeasema;
            }
            
            System.out.println("\nSyötä kolmikirjaiminen koodi, esim. HKI (Helsinki) tai TPE (Tampere).\n");
            continue;

        }

        String url = "/live-trains/station/" + lahtoAsema + "/" + kohdeAsema;
        
        // hae uusin data Varikolle
        Varikko.LueJunanJsonData(url);
        
        // hae junien lista käyttöön paikalliseen muuttujaan
        List<Juna> matkajunat = Varikko.junat;
        
        
        
    }

}
