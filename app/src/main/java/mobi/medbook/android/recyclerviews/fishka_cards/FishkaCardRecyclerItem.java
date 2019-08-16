package mobi.medbook.android.recyclerviews.fishka_cards;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;

import mobi.medbook.android.recyclerviews.base.BaseViewHolder;
import mobi.medbook.android.recyclerviews.base.RecyclerItem;
import mobi.medbook.android.ui.points.IOnSelectFishkaCard;


public class FishkaCardRecyclerItem extends RecyclerItem implements View.OnClickListener {

    private String cardNumber;
    private int id;

    public FishkaCardRecyclerItem(int id, String cardNumber) {
        this.cardNumber = cardNumber;
        this.id = id;
    }

    @Override
    public void fill(BaseViewHolder holder) {
        if (holder instanceof FishkaCardViewHolder) {
            ((FishkaCardViewHolder) holder).init(this, cardNumber);
        }
    }

    @Override
    public int getViewType() {
        return 0;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public int getId() {
        return id;
    }

    @Override
    public void onClick(View view) {
        if (getActivity(view.getContext()) != null)
            getActivity(view.getContext()).onSelectFishkaCard(cardNumber);
    }

    private IOnSelectFishkaCard getActivity(Context context) {
        while (context instanceof ContextWrapper) {
            if (context instanceof IOnSelectFishkaCard) {
                return (IOnSelectFishkaCard) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }
}
