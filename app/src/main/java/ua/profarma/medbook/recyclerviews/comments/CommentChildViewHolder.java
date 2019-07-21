package ua.profarma.medbook.recyclerviews.comments;

import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import ua.profarma.medbook.App;
import ua.profarma.medbook.Core;
import ua.profarma.medbook.R;
import ua.profarma.medbook.recyclerviews.base.BaseViewHolder;
import ua.profarma.medbook.types.news.Comment;
import ua.profarma.medbook.utils.TextUtils;

public class CommentChildViewHolder extends BaseViewHolder {

    private TextView tvTime;
    private TextView tvName;
    private TextView tvContent;
    private TextView tvReply;
    private TextView tvIcon;
    private View rootView;


    public CommentChildViewHolder(View itemView) {
        super(itemView);
        tvIcon = itemView.findViewById(R.id.item_comment_child_icon);
        tvTime = itemView.findViewById(R.id.item_comment_child_time);
        tvName = itemView.findViewById(R.id.item_comment_child_name);
        tvContent = itemView.findViewById(R.id.item_comment_child_content);
        rootView = itemView.findViewById(R.id.item_comment_child_root);

    }

    public void init(CommentsRecyclerItem owner, Comment comment) {
        //tvReply.setOnClickListener(owner);
        //rootView.setOnLongClickListener(owner);

        if (comment.owner_firstname != null && comment.owner_firstname.length() > 0 &&
                comment.owner_middlename != null && comment.owner_middlename.length() > 0) {
            String s = comment.owner_firstname.substring(0, 1) + comment.owner_middlename.substring(0, 1);
            tvIcon.setText(s);
        }


        if(comment.created_by == App.getUser().id)
            rootView.setBackgroundResource(R.drawable.rectangle_comment);
        else
            rootView.setBackground(null);

        if(tvTime != null){
            Date date = new Date(comment.created_at * 1000L);
            DateFormat format = new SimpleDateFormat("HH:mm dd MMM yyyy");
            String formatted = format.format(date);
            tvTime.setText(formatted);
        }

        tvContent.setText(/*"id = " + comment.id + ", parentId = " + comment.parentId +" "+*/ TextUtils.getString(comment.content));
        tvName.setText(TextUtils.getString(comment.owner_firstname) + " "+ TextUtils.getString(comment.owner_middlename));

    }
}
