package mobi.medbook.android.recyclerviews.tests_results;

import mobi.medbook.android.recyclerviews.base.BaseViewHolder;
import mobi.medbook.android.recyclerviews.base.RecyclerItem;


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
