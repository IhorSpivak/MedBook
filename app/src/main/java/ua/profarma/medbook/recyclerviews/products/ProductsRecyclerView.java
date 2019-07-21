package ua.profarma.medbook.recyclerviews.products;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;

import ua.profarma.medbook.R;
import ua.profarma.medbook.recyclerviews.base.BaseRecyclerView;
import ua.profarma.medbook.recyclerviews.base.BaseViewHolder;
import ua.profarma.medbook.recyclerviews.base.RecyclerItems;
import ua.profarma.medbook.recyclerviews.base.WCLinearLayoutManager;

public class ProductsRecyclerView extends BaseRecyclerView {

    public ProductsRecyclerView(Context context) {
        this(context, null);
    }

    public ProductsRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ProductsRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected RecyclerItems createItems() {
        return new RecyclerItems();
    }

    @Override
    public BaseViewHolder create(ViewGroup parent, int viewType) {
        View itemView =  LayoutInflater.from(getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(itemView);
    }

    @Override
    public void init() {
        super.init();
        //setLayoutManager(new WCLinearLayoutManager(getContext()));
        setNestedScrollingEnabled(false);
    }
}
