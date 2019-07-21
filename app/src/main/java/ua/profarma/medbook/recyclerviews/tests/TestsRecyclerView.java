package ua.profarma.medbook.recyclerviews.tests;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ua.profarma.medbook.R;
import ua.profarma.medbook.recyclerviews.base.BaseRecyclerView;
import ua.profarma.medbook.recyclerviews.base.BaseViewHolder;
import ua.profarma.medbook.recyclerviews.base.RecyclerItems;

public class TestsRecyclerView extends BaseRecyclerView {
    public TestsRecyclerView(Context context) {
        this(context, null);
    }

    public TestsRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public TestsRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected RecyclerItems createItems() {
        return new RecyclerItems();
    }

    @Override
    public BaseViewHolder create(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_tests, parent, false);
        return new TestViewHolder(itemView);
    }
}
