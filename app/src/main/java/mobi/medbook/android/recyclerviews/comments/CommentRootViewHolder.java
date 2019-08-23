package mobi.medbook.android.recyclerviews.comments;

import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import mobi.medbook.android.App;
import mobi.medbook.android.Core;
import mobi.medbook.android.R;
import mobi.medbook.android.recyclerviews.base.BaseViewHolder;
import mobi.medbook.android.types.news.Comment;
import mobi.medbook.android.utils.TextUtils;


public class CommentRootViewHolder extends BaseViewHolder {

    private TextView tvTime;
    private TextView tvName;
    private TextView tvReply;
    private TextView tvContent;
    private TextView tvIcon;
    private View rootView;


    public CommentRootViewHolder(View itemView) {
        super(itemView);
        tvIcon = itemView.findViewById(R.id.item_comment_root_icon);
        tvTime = itemView.findViewById(R.id.item_comment_root_time);
        tvName = itemView.findViewById(R.id.item_comment_root_name);
        tvContent = itemView.findViewById(R.id.item_comment_root_content);
        rootView = itemView.findViewById(R.id.item_comment_root_root);
        tvReply = itemView.findViewById(R.id.item_comment_root_reply);
    }

    public void init(CommentsRecyclerItem owner, Comment comment) {
        tvReply.setOnClickListener(owner);
        rootView.setOnLongClickListener(owner);

        if (comment.owner_firstname != null && comment.owner_firstname.length() > 0 &&
                comment.owner_middlename != null && comment.owner_middlename.length() > 0) {
            String s = comment.owner_firstname.substring(0, 1) + comment.owner_middlename.substring(0, 1);
            tvIcon.setText(s);
        }

        if (comment.created_by == App.getUser().id) {
            rootView.setBackgroundResource(R.drawable.rectangle_comment);
            tvReply.setVisibility(View.GONE);
        } else {
            rootView.setBackground(null);
            tvReply.setVisibility(View.VISIBLE);
        }


        if (tvTime != null) {
            Date date = new Date(comment.created_at * 1000L);
            DateFormat format = new SimpleDateFormat("HH:mm dd MMM yyyy");
            String formatted = format.format(date);
            tvTime.setText(formatted);
        }

        tvContent.setText(/*"id = " + comment.id + ", parentId = " + comment.parentId +" "+*/ TextUtils.getString(comment.content));
        tvName.setText(TextUtils.getString(comment.owner_firstname) + " " + TextUtils.getString(comment.owner_middlename));
        tvReply.setText(Core.get().LocalizationControl().getText(R.id.item_comment_root_reply));
    }
}
