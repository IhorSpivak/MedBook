package ua.profarma.medbook.recyclerviews.promo;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ua.profarma.medbook.R;
import ua.profarma.medbook.recyclerviews.base.BaseViewHolder;
import ua.profarma.medbook.types.visits.Promo;
import ua.profarma.medbook.types.visits.UserRelation;
import ua.profarma.medbook.utils.TextUtils;

public class PromoViewHolder extends BaseViewHolder {

    private View rootView;
    private TextView tvTitle;
    private TextView tvCount;
    private TextView tvValue;
    private ImageView imPlus;
    private ImageView imMinus;

    public PromoViewHolder(View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.item_promo_title);
        tvCount = itemView.findViewById(R.id.item_promo_count);
        tvValue = itemView.findViewById(R.id.item_promo_value);
        imMinus = itemView.findViewById(R.id.item_promo_minus);
        imPlus = itemView.findViewById(R.id.item_promo_plus);
        rootView = itemView.findViewById(R.id.item_promo_root);
    }

    public void init(PromoRecyclerItem owner, Promo promo) {
        if (tvTitle != null) {
            tvTitle.setText(TextUtils.getString(promo.name));
        }
        if (tvCount != null) {
            tvCount.setText("/"+String.valueOf(promo.count));
        }
        if (tvValue != null) {
            tvValue.setText(String.valueOf(promo.issuedQty));
        }
        imPlus.setOnClickListener(owner);
        imMinus.setOnClickListener(owner);
        //rootView.setOnClickListener(owner);
    }
}
