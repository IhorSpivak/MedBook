package mobi.medbook.android.recyclerviews.products;

import android.view.View;
import android.widget.TextView;

import mobi.medbook.android.R;
import mobi.medbook.android.recyclerviews.base.BaseViewHolder;

import mobi.medbook.android.types.visits.Product;
import mobi.medbook.android.utils.TextUtils;


public class ProductViewHolder extends BaseViewHolder {

    private View rootView;
    private TextView tvTitle;

    public ProductViewHolder(View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.item_product_title);
        rootView = itemView.findViewById(R.id.item_product_root);
    }

    public void init(ProductRecyclerItem owner, Product product) {
        if (tvTitle != null) {
            tvTitle.setText(TextUtils.getString(product.productName));
        }
        if (rootView != null) {
            if (product.impossible == 1)
                rootView.setBackgroundResource(R.drawable.rectangle_mp_anketa_product_type_3);
            else if ((product.factRec <= 0 && product.productType != 1) || (product.newPlanRec <= 0 && product.productType != 2))
                rootView.setBackgroundResource(R.drawable.rectangle_mp_anketa_product_type_1);
            else
                rootView.setBackgroundResource(R.drawable.rectangle_mp_anketa_product_type_2);

        }

        rootView.setOnClickListener(owner);
    }
}
