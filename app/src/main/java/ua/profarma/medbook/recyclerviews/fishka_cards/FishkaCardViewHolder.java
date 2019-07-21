package ua.profarma.medbook.recyclerviews.fishka_cards;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ua.profarma.medbook.R;
import ua.profarma.medbook.recyclerviews.base.BaseViewHolder;
import ua.profarma.medbook.types.TaskMaterial;
import ua.profarma.medbook.ui.materials.MaterialsEnum;

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
