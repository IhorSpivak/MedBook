package ua.profarma.medbook.recyclerviews.comments;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ua.profarma.medbook.R;
import ua.profarma.medbook.recyclerviews.base.BaseRecyclerView;
import ua.profarma.medbook.recyclerviews.base.BaseViewHolder;
import ua.profarma.medbook.recyclerviews.base.RecyclerItems;

public class CommentsRecyclerView extends BaseRecyclerView {

    private String TAG = "AppMedbook/NewsRecyclerView";

    public CommentsRecyclerView(Context context) {
        this(context, null);
    }

    public CommentsRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CommentsRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected RecyclerItems createItems() {
        return new RecyclerItems();
    }

    @Override
    public BaseViewHolder create(ViewGroup parent, int viewType) {
        View itemView = null;
        if (viewType == 1) {
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_comment_root, parent, false);
            return new CommentRootViewHolder(itemView);
        } else if (viewType == 2) {
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_comment_child, parent, false);
            return new CommentChildViewHolder(itemView);
        }
        return null;
    }
}
