package mobi.medbook.android.singltones;

import java.util.ArrayList;
import java.util.List;

import mobi.medbook.android.models.response.ReferenceItem;
import mobi.medbook.android.types.visits.Questions;

public class SingltonForAncetaTest {
    private static SingltonForAncetaTest uniqInstance;
    public List<Questions> list = new ArrayList<>();
    private SingltonForAncetaTest() {
    }
    public static SingltonForAncetaTest getInstance() {
        if (uniqInstance == null) {
            {
                if (uniqInstance == null)
                    uniqInstance = new SingltonForAncetaTest();
            }
        }
        return uniqInstance;
    }


    public List<Questions> getList() {
        return list;
    }

    public void setList(List<Questions> list) {
        this.list = list;
    }
}
