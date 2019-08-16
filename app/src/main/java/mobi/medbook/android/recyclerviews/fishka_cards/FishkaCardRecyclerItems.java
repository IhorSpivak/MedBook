package mobi.medbook.android.recyclerviews.fishka_cards;

import java.util.Iterator;

import mobi.medbook.android.recyclerviews.base.RecyclerItem;
import mobi.medbook.android.recyclerviews.base.RecyclerItems;


public class FishkaCardRecyclerItems extends RecyclerItems {

    public RecyclerItem find(String cardNumber) {
        Iterator<RecyclerItem> it = iterator();
        while (it.hasNext()) {
            RecyclerItem item = it.next();
            if (((FishkaCardRecyclerItem) item).getCardNumber().equals(cardNumber)) {
                return item;
            }
        }
        return null;
    }
}
