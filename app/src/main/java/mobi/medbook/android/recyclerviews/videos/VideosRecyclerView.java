package mobi.medbook.android.recyclerviews.videos;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mobi.medbook.android.R;
import mobi.medbook.android.recyclerviews.base.BaseViewHolder;
import mobi.medbook.android.recyclerviews.base.RecyclerItems;

import ua.profarma.medbook.recyclerviews.base.BaseRecyclerView;


public class VideosRecyclerView extends BaseRecyclerView {
    public VideosRecyclerView(Context context) {
        this(context, null);
    }

    public VideosRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public VideosRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected RecyclerItems createItems() {
        return new RecyclerItems();
    }

    @Override
    public BaseViewHolder create(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_videos, parent, false);
        return new VideoViewHolder(itemView);
    }
}
