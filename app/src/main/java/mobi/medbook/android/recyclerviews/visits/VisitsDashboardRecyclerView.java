package mobi.medbook.android.recyclerviews.visits;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import mobi.medbook.android.R;
import mobi.medbook.android.recyclerviews.base.BaseViewHolder;
import mobi.medbook.android.recyclerviews.base.RecyclerItems;
import mobi.medbook.android.utils.LogUtils;
import ua.profarma.medbook.recyclerviews.base.BaseRecyclerView;


public class VisitsDashboardRecyclerView extends BaseRecyclerView {
    public VisitsDashboardRecyclerView(Context context) {
        super(context);
    }

    public VisitsDashboardRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VisitsDashboardRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected RecyclerItems createItems() {
        return new RecyclerItems();
    }

    @Override
    public BaseViewHolder create(ViewGroup parent, int viewType) {
        View itemView =  LayoutInflater.from(getContext()).inflate(R.layout.item_visit, parent, false);
        return new VisitViewHolder(itemView);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int height = (int) getResources().getDimension(R.dimen.item_visit_height);
        height = height * items.size();
        LogUtils.logD("hfghgjghjt7756uryhg", "height " + height);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
