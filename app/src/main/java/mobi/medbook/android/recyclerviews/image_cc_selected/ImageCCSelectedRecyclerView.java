package mobi.medbook.android.recyclerviews.image_cc_selected;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;

import mobi.medbook.android.R;
import mobi.medbook.android.recyclerviews.base.BaseViewHolder;
import mobi.medbook.android.recyclerviews.base.RecyclerItems;
import ua.profarma.medbook.recyclerviews.base.BaseRecyclerView;


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
