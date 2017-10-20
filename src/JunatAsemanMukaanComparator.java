import java.util.Comparator;
import java.util.Date;
/**
 *
 *JunatAsemanMukaanComparator-luokkaa käytetään, jotta asemahaussa
 * saadut junat saadaan järjestettyä ajan mukaan.
 *
 * @author Titta Kivikoski
 */
class JunatAsemanMukaanComparator implements Comparator<Juna> {
    @Override
    public int compare(Juna a, Juna b) {
//Asemahaussa käyttäjältä kysytään asemaa (annettuAsema).
        String asema = Asemahaku.annettuAsema;
        long aikaerotus;
        Date aAika = null, bAika = null;
//Käydään läpi juna-olioiden TimeTableRows-lista, josta löytyy junan aseman StationShortCode.
//Katsotaan, täsmääkö se annettuun asemaan. Jos täsmää, otetaan aika.
        for (TimeTableRow t : a.getTimeTableRows()) {
            if (t.getStationShortCode().equals(asema)) {
                aAika = t.getTime();
            }
        }
        for  (TimeTableRow t : b.getTimeTableRows()) {
            if (t.getStationShortCode().equals(asema)) {
                bAika = t.getTime();
            }
        }
// Vertaillaan junien aikoja aikaerotuksen avulla.
        aikaerotus = aAika.getTime() - bAika.getTime();

        if (aikaerotus == 0) {
            return 0;
        } else if (aikaerotus < 0) {
            return -1;
        } else {
            return 1;
        }

    }
}
