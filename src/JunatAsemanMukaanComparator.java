import java.util.Comparator;
import java.util.Date;

class JunatAsemanMukaanComparator implements Comparator<Juna> {
    @Override
    public int compare(Juna a, Juna b) {
        String asema = Asemahaku.annettuAsema;
        long aikaerotus;
        Date aAika = null, bAika = null;

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
