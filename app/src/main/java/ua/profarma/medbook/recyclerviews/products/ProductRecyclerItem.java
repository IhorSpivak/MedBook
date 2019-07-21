package ua.profarma.medbook.recyclerviews.products;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;

import ua.profarma.medbook.recyclerviews.base.BaseViewHolder;
import ua.profarma.medbook.recyclerviews.base.RecyclerItem;
import ua.profarma.medbook.types.visits.Product;
import ua.profarma.medbook.ui.calendar.IOnSelectProduct;

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
