package ua.profarma.medbook.recyclerviews.presentations;

import android.view.View;

import mobi.medbook.android.events.core.EventRouter;
import mobi.medbook.android.events.materials.EventMaterialDescriptionStart;
import mobi.medbook.android.recyclerviews.base.BaseViewHolder;
import mobi.medbook.android.recyclerviews.base.RecyclerItem;
import mobi.medbook.android.recyclerviews.presentations.PresentationViewHolder;
import mobi.medbook.android.types.materials.Presentation;
import mobi.medbook.android.ui.materials.MaterialsEnum;


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
