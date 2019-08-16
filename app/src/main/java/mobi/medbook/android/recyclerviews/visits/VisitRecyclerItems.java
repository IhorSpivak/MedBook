package mobi.medbook.android.recyclerviews.visits;

import java.util.Iterator;

import mobi.medbook.android.recyclerviews.base.RecyclerItem;
import mobi.medbook.android.recyclerviews.base.RecyclerItems;


public class VisitRecyclerItems extends RecyclerItems {

    public RecyclerItem findForDate(long selectDate) {
        Iterator<RecyclerItem> it = iterator();
        while (it.hasNext()) {
            RecyclerItem item = it.next();
            if (((VisitRecyclerItem) item).getUserVisit().time_from > selectDate && ((VisitRecyclerItem) item).getUserVisit().time_from < (selectDate + 86400)) {
                return item;
            }
        }
        return null;
    }
}
