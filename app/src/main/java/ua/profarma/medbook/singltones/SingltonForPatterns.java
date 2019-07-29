package ua.profarma.medbook.singltones;

import java.util.ArrayList;

import ua.profarma.medbook.models.response.ReferenceItem;

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
