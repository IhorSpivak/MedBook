package mobi.medbook.android.recyclerviews.news;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mobi.medbook.android.R;
import mobi.medbook.android.recyclerviews.base.BaseViewHolder;
import mobi.medbook.android.recyclerviews.base.RecyclerItems;

import ua.profarma.medbook.recyclerviews.base.BaseRecyclerView;


public class NewsRecyclerView extends BaseRecyclerView {

    private String TAG = "AppMedbook/NewsRecyclerView";

    public NewsRecyclerView(Context context) {
        this(context, null);
    }

    public NewsRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public NewsRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected RecyclerItems createItems() {
        return new RecyclerItems();
    }

    @Override
    public BaseViewHolder create(ViewGroup parent, int viewType) {
        View itemView = null;
        itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_news, parent, false);
//        if (viewType == 1)
//            itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_material_type_list, parent, false);
//        else if (viewType == 2)
//            itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_material_type_tile, parent, false);
        return new NewsViewHolder(itemView);
    }
}
