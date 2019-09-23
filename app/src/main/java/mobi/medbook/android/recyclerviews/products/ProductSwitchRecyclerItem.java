package mobi.medbook.android.recyclerviews.products;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;

import mobi.medbook.android.recyclerviews.base.BaseViewHolder;
import mobi.medbook.android.recyclerviews.base.RecyclerItem;
import mobi.medbook.android.types.visits.Product;
import mobi.medbook.android.types.visits.ProductPlan;
import mobi.medbook.android.ui.calendar.IOnSelectProduct;

public class ProductSwitchRecyclerItem extends RecyclerItem {

    private ProductPlan product;

    public ProductSwitchRecyclerItem(ProductPlan product) {
        this.product = product;
    }

    @Override
    public void fill(BaseViewHolder holder) {
        if (holder instanceof ProductSwitchViewHolder) {
            ((ProductSwitchViewHolder) holder).init(this, product);
        }
    }

    @Override
    public int getViewType() {
        return 0;
    }



    private IOnSelectProduct getActivity(Context context) {
        while (context instanceof ContextWrapper) {
            if (context instanceof IOnSelectProduct) {
                return (IOnSelectProduct) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    public ProductPlan getProduct() {
        return product;
    }

    public void setProduct(ProductPlan product) {
        this.product = product;
    }
}
