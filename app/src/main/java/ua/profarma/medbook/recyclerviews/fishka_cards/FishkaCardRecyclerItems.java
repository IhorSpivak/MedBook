package ua.profarma.medbook.recyclerviews.fishka_cards;

import java.util.Iterator;

import ua.profarma.medbook.recyclerviews.base.RecyclerItem;
import ua.profarma.medbook.recyclerviews.base.RecyclerItems;
import ua.profarma.medbook.recyclerviews.fishka_cards.FishkaCardRecyclerItem;

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
