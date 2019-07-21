package ua.profarma.medbook.recyclerviews.visits;

import java.util.Iterator;

import ua.profarma.medbook.recyclerviews.base.RecyclerItem;
import ua.profarma.medbook.recyclerviews.base.RecyclerItems;
import ua.profarma.medbook.recyclerviews.fishka_cards.FishkaCardRecyclerItem;

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
