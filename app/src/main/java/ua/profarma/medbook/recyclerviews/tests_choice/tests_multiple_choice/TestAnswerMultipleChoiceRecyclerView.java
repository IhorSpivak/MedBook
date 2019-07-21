package ua.profarma.medbook.recyclerviews.tests_choice.tests_multiple_choice;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ua.profarma.medbook.R;
import ua.profarma.medbook.recyclerviews.base.BaseRecyclerView;
import ua.profarma.medbook.recyclerviews.base.BaseViewHolder;
import ua.profarma.medbook.recyclerviews.base.RecyclerItems;
import ua.profarma.medbook.recyclerviews.tests_choice.tests_single_choice.TestAnswerSingleChoiceRecyclerItem;
import ua.profarma.medbook.utils.LogUtils;

public class TestAnswerMultipleChoiceRecyclerView extends BaseRecyclerView {
    public TestAnswerMultipleChoiceRecyclerView(Context context) {
        this(context, null);
    }

    public TestAnswerMultipleChoiceRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public TestAnswerMultipleChoiceRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected RecyclerItems createItems() {
        return new RecyclerItems();
    }

    @Override
    public BaseViewHolder create(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_test_answer_multiple_choice, parent, false);
        return new TestAnswerMultipleChoiceViewHolder(itemView);
    }

    public void itemUpdate(TestAnswerMultipleChoiceRecyclerItem item) {
        int position = items.indexOf(item);
        LogUtils.logD("TestAnswerSingleChoiceRecyclerView", "itemUpdate");
        itemUpdate(position);
    }

}
