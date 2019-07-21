package ua.profarma.medbook.recyclerviews.products;

import android.view.View;
import android.widget.TextView;

import ua.profarma.medbook.App;
import ua.profarma.medbook.R;
import ua.profarma.medbook.recyclerviews.base.BaseViewHolder;
import ua.profarma.medbook.types.visits.Product;
import ua.profarma.medbook.types.visits.UserRelation;
import ua.profarma.medbook.utils.LogUtils;
import ua.profarma.medbook.utils.TextUtils;

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


//        case 1:
//        llLastPlanRec.setVisibility(View.GONE);
//        llFactRec.setVisibility(View.GONE);
//        tvNewPlanRecValue.setText(String.valueOf(product.newPlanRec));
//        break;
//        case 2:
//        llNewPlanRec.setVisibility(View.GONE);
//        tvLastPlanRecValue.setText(String.valueOf(product.lastPlanRec));
//        tvFactRecValue.setText(String.valueOf(product.factRec));
//        break;
//        case 3:
//        tvLastPlanRecValue.setText(String.valueOf(product.lastPlanRec));
//        tvFactRecValue.setText(String.valueOf(product.factRec));
//        tvNewPlanRecValue.setText(String.valueOf(product.newPlanRec));
//        break;
        rootView.setOnClickListener(owner);
    }
}
