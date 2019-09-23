package mobi.medbook.android.recyclerviews.presentations;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mobi.medbook.android.R;
import mobi.medbook.android.recyclerviews.base.BaseRecyclerView;
import mobi.medbook.android.recyclerviews.base.BaseViewHolder;
import mobi.medbook.android.recyclerviews.base.RecyclerItems;


public class PresentationsRecyclerView extends BaseRecyclerView {
    public PresentationsRecyclerView(Context context) {
        this(context, null);
    }

    public PresentationsRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public PresentationsRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected RecyclerItems createItems() {
        return new RecyclerItems();
    }

    @Override
    public BaseViewHolder create(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_presentations, parent, false);
        return new PresentationViewHolder(itemView);
    }
}
