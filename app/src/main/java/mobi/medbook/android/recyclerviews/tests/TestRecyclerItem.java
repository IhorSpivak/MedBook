package mobi.medbook.android.recyclerviews.tests;

import android.view.View;

import mobi.medbook.android.events.core.EventRouter;
import mobi.medbook.android.events.materials.EventMaterialDescriptionStart;
import mobi.medbook.android.recyclerviews.base.BaseViewHolder;
import mobi.medbook.android.recyclerviews.base.RecyclerItem;
import mobi.medbook.android.types.materials.Test;
import mobi.medbook.android.ui.materials.MaterialsEnum;


public class TestRecyclerItem extends RecyclerItem implements View.OnClickListener {

    private Test test;

    public TestRecyclerItem(Test test) {
        this.test = test;
    }

    @Override
    public void fill(BaseViewHolder holder) {
        if (holder instanceof TestViewHolder) {
            ((TestViewHolder) holder).init(this, test);
        }
    }

    @Override
    public int getViewType() {
        return 0;
    }

    @Override
    public void onClick(View view) {
        EventRouter.send(new EventMaterialDescriptionStart(MaterialsEnum.TEST, test.id));
    }

    public Test getTest() {
        return test;
    }
}
