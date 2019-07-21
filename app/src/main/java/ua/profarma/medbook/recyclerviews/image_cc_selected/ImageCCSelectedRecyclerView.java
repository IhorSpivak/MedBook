package ua.profarma.medbook.recyclerviews.image_cc_selected;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;

import ua.profarma.medbook.R;
import ua.profarma.medbook.recyclerviews.base.BaseRecyclerView;
import ua.profarma.medbook.recyclerviews.base.BaseViewHolder;
import ua.profarma.medbook.recyclerviews.base.RecyclerItems;

public class ImageCCSelectedRecyclerView extends BaseRecyclerView {

    public ImageCCSelectedRecyclerView(Context context) {
        this(context, null);
    }

    public ImageCCSelectedRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ImageCCSelectedRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected RecyclerItems createItems() {
        return new RecyclerItems();
    }

    @Override
    public BaseViewHolder create(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_image_cc_selected, parent, false);
        return new ImageCCSelectedViewHolder(itemView);
    }

    @Override
    public void init() {
        super.init();
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        setLayoutManager(layoutManager);

    }
}
