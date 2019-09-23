package mobi.medbook.android.recyclerviews.icod_selected;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mobi.medbook.android.R;
import mobi.medbook.android.recyclerviews.base.BaseRecyclerView;
import mobi.medbook.android.recyclerviews.base.BaseViewHolder;
import mobi.medbook.android.recyclerviews.base.RecyclerItems;



public class IcodSelectedRecyclerView extends BaseRecyclerView {

    private String TAG = "AppMedbook/HistoryExchangeRecyclerView";

    public IcodSelectedRecyclerView(Context context) {
        this(context, null);
    }

    public IcodSelectedRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public IcodSelectedRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected RecyclerItems createItems() {
        return new RecyclerItems();
    }

    @Override
    public BaseViewHolder create(ViewGroup parent, int viewType) {
        View itemView =  LayoutInflater.from(getContext()).inflate(R.layout.item_icod_selected, parent, false);
        return new IcodSelectedViewHolder(itemView);
    }

    @Override
    public void itemRemove(int _position) {
        if (_position >= 0 && _position < items.size()) {
            items.remove(_position);
            adapter.notifyDataSetChanged();
        }
    }

    //    @Override
//    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//
//        int height = (int) getResources().getDimension(R.dimen.item_visit_height);
//        height = height * items.size();
//        LogUtils.logD("jhjghjgjh", "height " + height);
//        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//    }
}
