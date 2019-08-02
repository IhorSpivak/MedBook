package ua.profarma.medbook.recyclerviews.image_cc_selected;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;

import com.bumptech.glide.RequestManager;

import ua.profarma.medbook.R;
import ua.profarma.medbook.recyclerviews.base.BaseViewHolder;
import ua.profarma.medbook.recyclerviews.base.RecyclerItem;
import ua.profarma.medbook.types.news.CCImage;
import ua.profarma.medbook.ui.news_and_clinical_cases.IOnDeleteAddCC;
import ua.profarma.medbook.ui.news_and_clinical_cases.IOnSelectImageCC;

public class ImageCCSelectedRecyclerItem extends RecyclerItem implements View.OnClickListener {

    private CCImage ccImage;
    private RequestManager rb;

    public ImageCCSelectedRecyclerItem(CCImage ccImage) {
        this.ccImage = ccImage;
    }

    @Override
    public void fill(BaseViewHolder holder) {
        if (holder instanceof ImageCCSelectedViewHolder) {
            ((ImageCCSelectedViewHolder) holder).init(this, ccImage);
        }
    }

    @Override
    public int getViewType() {
        return 0;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.item_image_cc_selected_delete &&
                getActivityDelete(view.getContext()) != null) {
            getActivityDelete(view.getContext()).onDeleteImage(ccImage);
        } else if (getActivity(view.getContext()) != null)
            getActivity(view.getContext()).onSelectImage(ccImage);
    }

    private IOnSelectImageCC getActivity(Context context) {
        while (context instanceof ContextWrapper) {
            if (context instanceof IOnSelectImageCC) {
                return (IOnSelectImageCC) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    private IOnDeleteAddCC getActivityDelete(Context context) {
        while (context instanceof ContextWrapper) {
            if (context instanceof IOnDeleteAddCC) {
                return (IOnDeleteAddCC) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    public CCImage getCcImage() {
        return ccImage;
    }

}
