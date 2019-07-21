package ua.profarma.medbook.recyclerviews.fishka_cards;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.view.View;

import ua.profarma.medbook.App;
import ua.profarma.medbook.Core;
import ua.profarma.medbook.R;
import ua.profarma.medbook.recyclerviews.base.BaseViewHolder;
import ua.profarma.medbook.recyclerviews.base.RecyclerItem;
import ua.profarma.medbook.ui.news_and_clinical_cases.IOnSelectDrug;
import ua.profarma.medbook.ui.points.ExchangePointsForFishkaActivity;
import ua.profarma.medbook.ui.points.IOnDeleteFishkaCard;
import ua.profarma.medbook.ui.points.IOnSelectFishkaCard;
import ua.profarma.medbook.utils.DialogBuilder;

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
