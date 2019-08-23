package mobi.medbook.android.singltones;

import java.util.ArrayList;

import mobi.medbook.android.models.response.ReferenceItem;

public class SingltonRegistrationData {

        private static SingltonRegistrationData uniqInstance;
        public int idInstit = -1;
        public int idProf = -1;
        public String prof = "Профільна спеціалізація";
        public String intst = "Місце роботи";


        private SingltonRegistrationData() {
        }

        public static SingltonRegistrationData getInstance() {
            if (uniqInstance == null) {
                {
                    if (uniqInstance == null)
                        uniqInstance = new SingltonRegistrationData();
                }
            }
            return uniqInstance;
        }


    public static void setUniqInstance(SingltonRegistrationData uniqInstance) {
        SingltonRegistrationData.uniqInstance = uniqInstance;
    }

    public int getIdInstit() {
        return idInstit;
    }

    public void setIdInstit(int idInstit) {
        this.idInstit = idInstit;
    }

    public int getIdProf() {
        return idProf;
    }

    public void setIdProf(int idProf) {
        this.idProf = idProf;
    }

    public String getProf() {
        return prof;
    }

    public void setProf(String prof) {
        this.prof = prof;
    }

    public String getIntst() {
        return intst;
    }

    public void setIntst(String intst) {
        this.intst = intst;
    }
}
