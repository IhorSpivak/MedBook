package ua.profarma.medbook.recyclerviews.presentations;

import android.view.View;

import ua.profarma.medbook.events.materials.EventMaterialDescriptionStart;
import ua.profarma.medbook.events.core.EventRouter;
import ua.profarma.medbook.recyclerviews.base.BaseViewHolder;
import ua.profarma.medbook.recyclerviews.base.RecyclerItem;
import ua.profarma.medbook.types.materials.Presentation;
import ua.profarma.medbook.ui.materials.MaterialsEnum;
import ua.profarma.medbook.utils.LogUtils;

public class PresentationRecyclerItem extends RecyclerItem implements View.OnClickListener {

    private Presentation presentation;

    public Presentation getPresentation() {
        return presentation;
    }

    public void setResultTime(String resultTime){
        presentation.result_time = resultTime;
    }

    public PresentationRecyclerItem(Presentation presentation) {
        this.presentation = presentation;
    }

    @Override
    public void fill(BaseViewHolder holder) {
        if (holder instanceof PresentationViewHolder) {
            ((PresentationViewHolder) holder).init(this, presentation);
        }
    }

    @Override
    public int getViewType() {
        return 0;
    }

    @Override
    public void onClick(View view) {
        EventRouter.send(new EventMaterialDescriptionStart(MaterialsEnum.PRESENTATION, presentation.id));
    }
}
