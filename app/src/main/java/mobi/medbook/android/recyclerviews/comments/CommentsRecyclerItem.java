package mobi.medbook.android.recyclerviews.comments;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;

import mobi.medbook.android.App;
import mobi.medbook.android.recyclerviews.base.BaseViewHolder;
import mobi.medbook.android.recyclerviews.base.RecyclerItem;
import mobi.medbook.android.types.news.Comment;
import mobi.medbook.android.ui.news_and_clinical_cases.IOnClicksComment;


public class CommentsRecyclerItem extends RecyclerItem implements View.OnClickListener, View.OnLongClickListener {

    private Comment comment;

    public CommentsRecyclerItem(Comment comment) {
        this.comment = comment;
    }

    @Override
    public void fill(BaseViewHolder holder) {
        if (holder instanceof CommentChildViewHolder) {
            ((CommentChildViewHolder) holder).init(this, comment);
        } else if (holder instanceof CommentRootViewHolder) {
            ((CommentRootViewHolder) holder).init(this, comment);
        }

    }

    @Override
    public int getViewType() {
        return comment.level;
    }

    @Override
    public void onClick(View view) {
        if (getActivity(view.getContext()) != null) {
            if (getViewType() == 1)
                getActivity(view.getContext()).onSelectReplyComment(comment.id, comment.position + comment.childSize, comment.owner_firstname + " " + comment.owner_middlename);
            else if (getViewType() == 2)
                getActivity(view.getContext()).onSelectReplyComment(comment.parentId,comment.position, comment.owner_firstname + " " + comment.owner_middlename);
        }
    }

    private IOnClicksComment getActivity(Context context) {
        while (context instanceof ContextWrapper) {
            if (context instanceof IOnClicksComment) {
                return (IOnClicksComment) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }


    @Override
    public boolean onLongClick(View view) {
        if (comment.created_by == App.getUser().id)
            getActivity(view.getContext()).onDeleteComment(comment.id, comment.position);
        return true;
    }
}
