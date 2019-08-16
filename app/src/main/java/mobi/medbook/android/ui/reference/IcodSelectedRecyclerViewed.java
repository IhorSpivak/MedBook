package mobi.medbook.android.ui.reference;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import mobi.medbook.android.R;
import mobi.medbook.android.recyclerviews.base.BaseViewHolder;
import mobi.medbook.android.recyclerviews.base.RecyclerItems;
import mobi.medbook.android.recyclerviews.icod_selected.IcodSelectedViewHolder;
import ua.profarma.medbook.recyclerviews.base.BaseRecyclerView;


public class IcodSelectedRecyclerViewed extends BaseRecyclerView {

    private String TAG = "AppMedbook/HistoryExchangeRecyclerView";

    public IcodSelectedRecyclerViewed(Context context) {
        this(context, null);
    }

    public IcodSelectedRecyclerViewed(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public IcodSelectedRecyclerViewed(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected RecyclerItems createItems() {
        return new RecyclerItems();
    }

    @Override
    public BaseViewHolder create(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_icod_selected_viewed, parent, false);
        return new IcodSelectedViewHolder(itemView);
    }

    @Override
    public void itemRemove(int _position) {
        if (_position >= 0 && _position < items.size()) {
            items.remove(_position);
            adapter.notifyDataSetChanged();
        }
    }
}
