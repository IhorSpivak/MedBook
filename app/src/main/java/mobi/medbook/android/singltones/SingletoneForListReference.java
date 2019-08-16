package mobi.medbook.android.singltones;

import java.util.ArrayList;

import mobi.medbook.android.models.response.ReferenceItem;

public class SingletoneForListReference {
    private static SingletoneForListReference uniqInstance;
    public ArrayList<ReferenceItem> list = new ArrayList<>();
    private SingletoneForListReference() {
    }
    public static SingletoneForListReference getInstance() {
        if (uniqInstance == null) {
            {
                if (uniqInstance == null)
                    uniqInstance = new SingletoneForListReference();
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
