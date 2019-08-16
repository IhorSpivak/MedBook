package mobi.medbook.android.recyclerviews.fishka_cards;

import android.view.View;
import android.widget.TextView;

import mobi.medbook.android.R;
import mobi.medbook.android.recyclerviews.base.BaseViewHolder;


public class FishkaCardViewHolder extends BaseViewHolder {

    private View rootView;
    private TextView tvTitle;

    public FishkaCardViewHolder(View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.item_fishka_card_number);
        rootView = itemView.findViewById(R.id.item_fishka_card_root);
    }

    public void init(FishkaCardRecyclerItem owner, String cardNumber) {
        if (tvTitle != null) {
            tvTitle.setText(cardNumber);
        }

        rootView.setOnClickListener(owner);
    }
}
