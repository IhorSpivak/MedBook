package mobi.medbook.android.singltones;

import java.util.ArrayList;

import mobi.medbook.android.models.response.ReferenceItem;

public class SingltonForPatterns {

    private static SingltonForPatterns uniqInstance;
    public ArrayList<ReferenceItem> list = new ArrayList<>();
    private SingltonForPatterns() {
    }
    public static SingltonForPatterns getInstance() {
        if (uniqInstance == null) {
            {
                if (uniqInstance == null)
                    uniqInstance = new SingltonForPatterns();
            }
        }
        return uniqInstance;
    }


    public ArrayList<ReferenceItem> getList() {
        return list;
    }

    public void setList(ArrayList<ReferenceItem> list) {
        this.list = list;
    }
}
