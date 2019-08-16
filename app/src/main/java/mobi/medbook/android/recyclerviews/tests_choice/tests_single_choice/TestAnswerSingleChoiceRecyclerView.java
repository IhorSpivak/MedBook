package mobi.medbook.android.recyclerviews.tests_choice.tests_single_choice;

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


public class TestAnswerSingleChoiceRecyclerView extends BaseRecyclerView {
    public TestAnswerSingleChoiceRecyclerView(Context context) {
        this(context, null);
    }

    public TestAnswerSingleChoiceRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public TestAnswerSingleChoiceRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected RecyclerItems createItems() {
        return new RecyclerItems();
    }

    @Override
    public BaseViewHolder create(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_test_answer_single_choice, parent, false);
        return new TestAnswerSingleChoiceViewHolder(itemView);
    }

    public void itemUpdate(TestAnswerSingleChoiceRecyclerItem item) {
        int position = items.indexOf(item);
        LogUtils.logD("TestAnswerSingleChoiceRecyclerView", "itemUpdate");
        itemUpdate(position);
    }
}
