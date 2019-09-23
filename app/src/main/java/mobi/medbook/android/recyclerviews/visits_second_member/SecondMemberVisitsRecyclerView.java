package mobi.medbook.android.recyclerviews.visits_second_member;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mobi.medbook.android.R;
import mobi.medbook.android.recyclerviews.base.BaseRecyclerView;
import mobi.medbook.android.recyclerviews.base.BaseViewHolder;
import mobi.medbook.android.recyclerviews.base.RecyclerItems;


public class SecondMemberVisitsRecyclerView extends BaseRecyclerView {

    public SecondMemberVisitsRecyclerView(Context context) {
        this(context, null);
    }

    public SecondMemberVisitsRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public SecondMemberVisitsRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected RecyclerItems createItems() {
        return new RecyclerItems();
    }

    @Override
    public BaseViewHolder create(ViewGroup parent, int viewType) {
        View itemView =  LayoutInflater.from(getContext()).inflate(R.layout.item_second_member_visit, parent, false);
        return new SecondMemberVisitViewHolder(itemView);
    }
}
