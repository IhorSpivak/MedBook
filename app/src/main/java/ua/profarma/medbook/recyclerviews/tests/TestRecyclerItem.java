package ua.profarma.medbook.recyclerviews.tests;

import android.view.View;

import ua.profarma.medbook.events.materials.EventMaterialDescriptionStart;
import ua.profarma.medbook.events.core.EventRouter;
import ua.profarma.medbook.recyclerviews.base.BaseViewHolder;
import ua.profarma.medbook.recyclerviews.base.RecyclerItem;
import ua.profarma.medbook.types.materials.Test;
import ua.profarma.medbook.ui.materials.MaterialsEnum;
import ua.profarma.medbook.utils.LogUtils;

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
