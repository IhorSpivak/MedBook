package ua.profarma.medbook.recyclerviews.tests_results;

import ua.profarma.medbook.recyclerviews.base.BaseViewHolder;
import ua.profarma.medbook.recyclerviews.base.RecyclerItem;
import ua.profarma.medbook.types.materials.Answer;
import ua.profarma.medbook.types.materials.ResultTestQuestion;

public class TestResultRecyclerItem extends RecyclerItem {

    private String text;
    private TestResults state;

    public TestResultRecyclerItem(String text, TestResults state) {
        this.text = text;
        this.state = state;
    }

    @Override
    public void fill(BaseViewHolder holder) {
        if (holder instanceof TestResultViewHolder) {
            ((TestResultViewHolder) holder).init(text, state);
        }
    }

    @Override
    public int getViewType() {
        return 0;
    }

}
