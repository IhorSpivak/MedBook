package mobi.medbook.android.recyclerviews.products;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;

import mobi.medbook.android.recyclerviews.base.BaseViewHolder;
import mobi.medbook.android.recyclerviews.base.RecyclerItem;

import mobi.medbook.android.types.visits.Product;
import mobi.medbook.android.ui.calendar.IOnSelectProduct;

public class ProductRecyclerItem extends RecyclerItem implements View.OnClickListener {

    private Product product;

    public ProductRecyclerItem(Product product) {
        this.product = product;
    }

    @Override
    public void fill(BaseViewHolder holder) {
        if (holder instanceof ProductViewHolder) {
            ((ProductViewHolder) holder).init(this, product);
        }
    }

    @Override
    public int getViewType() {
        return 0;
    }

    @Override
    public void onClick(View view) {
        if (getActivity(view.getContext()) != null)
            getActivity(view.getContext()).onSelectProduct(product);
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
