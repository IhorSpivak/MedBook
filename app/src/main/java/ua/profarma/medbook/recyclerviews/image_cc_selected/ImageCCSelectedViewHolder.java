package ua.profarma.medbook.recyclerviews.image_cc_selected;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.squareup.picasso.Picasso;

import ua.profarma.medbook.R;
import ua.profarma.medbook.recyclerviews.base.BaseViewHolder;
import ua.profarma.medbook.types.news.CCImage;
import ua.profarma.medbook.types.visits.UserRelation;

public class ImageCCSelectedViewHolder extends BaseViewHolder {

    private View rootView;
    private ImageView imvImage;
    private ImageView deleteImage;
    private View view;
    private RequestManager rb;

    public ImageCCSelectedViewHolder(View itemView) {
        super(itemView);
        view = itemView;
        deleteImage = itemView.findViewById(R.id.item_image_cc_selected_delete);
        imvImage = itemView.findViewById(R.id.item_image_cc_selected_image);
        rootView = itemView.findViewById(R.id.item_image_cc_selected_root);

    }

    public void init(ImageCCSelectedRecyclerItem owner, CCImage ccImage) {
        rb = Glide.with(view);
        rb.load(ccImage.image).into(imvImage);
        if (ccImage.viewing) {
            deleteImage.setVisibility(View.GONE);
            rootView.setOnClickListener(owner);
        } else {
            deleteImage.setOnClickListener(owner);
        }
    }
}
