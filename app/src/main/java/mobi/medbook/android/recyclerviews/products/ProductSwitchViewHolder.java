package mobi.medbook.android.recyclerviews.products;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import mobi.medbook.android.R;
import mobi.medbook.android.recyclerviews.base.BaseViewHolder;
import mobi.medbook.android.types.visits.Product;
import mobi.medbook.android.types.visits.ProductPlan;
import mobi.medbook.android.ui.calendar.IOnSelectProduct;
import mobi.medbook.android.ui.calendar.IonSelectSwitchProduct;
import mobi.medbook.android.utils.TextUtils;

public class ProductSwitchViewHolder extends BaseViewHolder {

    private View rootView;
    private TextView tvTitle;
    private Switch aSwitch;

    public ProductSwitchViewHolder(View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.item_product_title);
        rootView = itemView.findViewById(R.id.item_product_root);
        aSwitch = itemView.findViewById(R.id.aSwitch);
    }

    public void init(ProductSwitchRecyclerItem owner, ProductPlan product) {
        if (tvTitle != null) {
            tvTitle.setText(TextUtils.getString(product.productName));
        }
        if (aSwitch != null) {
            if (product.infoVoiced == 1)
                aSwitch.setChecked(true);
            else
                aSwitch.setChecked(false);

        }

        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(aSwitch.isChecked()){
                    product.infoVoiced = 1;
                    getActivity(v.getContext()).onSelectSwitchProduct(product);
                }else {
                    product.infoVoiced = 0;
                    getActivity(v.getContext()).onSelectSwitchProduct(product);
                }
            }
        });



    }
    private IonSelectSwitchProduct getActivity(Context context) {
        while (context instanceof ContextWrapper) {
            if (context instanceof IOnSelectProduct) {
                return (IonSelectSwitchProduct) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }
}
