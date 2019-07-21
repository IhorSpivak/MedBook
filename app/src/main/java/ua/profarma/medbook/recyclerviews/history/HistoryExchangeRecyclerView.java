package ua.profarma.medbook.recyclerviews.history;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ua.profarma.medbook.R;
import ua.profarma.medbook.recyclerviews.base.BaseRecyclerView;
import ua.profarma.medbook.recyclerviews.base.BaseViewHolder;
import ua.profarma.medbook.recyclerviews.base.RecyclerItems;

public class HistoryExchangeRecyclerView extends BaseRecyclerView {

    private String TAG = "AppMedbook/HistoryExchangeRecyclerView";

    public HistoryExchangeRecyclerView(Context context) {
        this(context, null);
    }

    public HistoryExchangeRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public HistoryExchangeRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected RecyclerItems createItems() {
        return new RecyclerItems();
    }

    @Override
    public BaseViewHolder create(ViewGroup parent, int viewType) {
        View itemView =  LayoutInflater.from(getContext()).inflate(R.layout.item_history_exchange, parent, false);
        return new HistoryExchangeViewHolder(itemView);
    }
}
