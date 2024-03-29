package mobi.medbook.android.ui.news_and_clinical_cases;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import mobi.medbook.android.Core;
import mobi.medbook.android.R;
import mobi.medbook.android.events.core.Event;
import mobi.medbook.android.events.news.EventLoadComments;
import mobi.medbook.android.recyclerviews.comments.CommentsRecyclerItem;
import mobi.medbook.android.recyclerviews.comments.CommentsRecyclerView;
import mobi.medbook.android.types.news.Comment;
import mobi.medbook.android.types.requests.NewCommentRequest;
import mobi.medbook.android.ui.custom_views.MedBookActivity;
import mobi.medbook.android.utils.DialogBuilder;
import mobi.medbook.android.utils.LogUtils;


public class CommentsActivity extends MedBookActivity implements IOnClicksComment, IOnDialogCommentDelete {

    private int NEED_SYMBOL_FOR_ADD_COMMENTS = 2;
    private int idNews;
    private Integer selectParentId;
    public static String KEY_ID = "KEY_ID";
    public static String KEY_TITLE = "KEY_TITLE";
    public CommentsRecyclerView list;
    private TextView tvSubTitle;
    private TextView tvTitleCount;
    private AppCompatEditText edInputComment;
    private ImageButton ibAddComment;
    private LinearLayout llReply;
    private TextView tvReplyName;
    private ImageView ivRelpyClear;
    private Integer position = 1;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_comments);

        llReply = findViewById(R.id.activity_comments_ll_reply);
        tvReplyName = findViewById(R.id.activity_comments_reply_name);
        ivRelpyClear = findViewById(R.id.activity_comments_reply_clear);
        ivRelpyClear.setOnClickListener(view -> {
            selectParentId = null;
            tvReplyName.setText("");
            llReply.setVisibility(View.GONE);
            int valueInPixelsOther = (int) getResources().getDimension(R.dimen.padding_default);
            edInputComment.setPadding(valueInPixelsOther, valueInPixelsOther, valueInPixelsOther, valueInPixelsOther);
        });


        TextView tvTitle = findViewById(R.id.activity_comments_toolbar_title);
        edInputComment = findViewById(R.id.activity_comments_input);
        edInputComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= NEED_SYMBOL_FOR_ADD_COMMENTS) {
                    ibAddComment.setEnabled(true);
                    ibAddComment.setImageResource(R.drawable.ic_add_comment_active);
                } else {
                    ibAddComment.setEnabled(false);
                    ibAddComment.setImageResource(R.drawable.ic_add_comment_inactive);
                }
            }
        });
        ibAddComment = findViewById(R.id.activity_comments_add);
        ibAddComment.setEnabled(false);

        ibAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        v.setEnabled(true);
                    }
                }, 2000);
                ibAddComment.setEnabled(false);
                ibAddComment.setImageResource(R.drawable.ic_add_comment_inactive);
                NewCommentRequest newCommentRequest = new NewCommentRequest();
                newCommentRequest.content = edInputComment.getText().toString();
                newCommentRequest.entityId = idNews;
                newCommentRequest.parentId = selectParentId;
                Core.get().CommentsControl().newComment(newCommentRequest);
                edInputComment.setText("");
                selectParentId = null;
                tvReplyName.setText("");
                llReply.setVisibility(View.GONE);
                int valueInPixelsOther = (int) getResources().getDimension(R.dimen.padding_default);
                edInputComment.setPadding(valueInPixelsOther, valueInPixelsOther, valueInPixelsOther, valueInPixelsOther);
            }
        });

        tvSubTitle = findViewById(R.id.activity_comments_toolbar_subtitle);
        ImageView imvClose = findViewById(R.id.activity_comments_toolbar_close);
        imvClose.setOnClickListener(view -> finish());

        list = findViewById(R.id.activity_comments_list);
        list.init();

        if (getIntent() == null) finish();
        idNews = getIntent().getIntExtra(KEY_ID, -1);
        if (idNews == -1) finish();
        Core.get().CommentsControl().getComments(idNews);
        tvTitle.setText( getIntent().getStringExtra(KEY_TITLE));
        onLocalizationUpdate();
    }

    @Override
    protected void onLocalizationUpdate() {
        tvSubTitle.setText(Core.get().LocalizationControl().getText(R.id.activity_comments_toolbar_subtitle));
    }

    @Override
    public void onEvent(Event event) {
        super.onEvent(event);
        switch (event.getEventId()) {
            case Event.EVENT_LOAD_COMMENTS:
                list.clear();
                Comment[] items;
                EventLoadComments eventLoadComments = (EventLoadComments) event;
                LogUtils.logD("jghjghmb", "eventLoadComments.getComments().length = " + eventLoadComments.getComments().length);
                items = new Comment[eventLoadComments.getComments().length];
                for (int i = 0; i < eventLoadComments.getComments().length; i++) {
                    items[i] = eventLoadComments.getComments()[i];
                }

                Arrays.sort(items);

                ArrayList<Comment> itemsSort = new ArrayList<>();
                ArrayList<Comment> itemsSortChild = new ArrayList<>();

                HashMap<Integer, ArrayList<Comment>> hashChild = new HashMap<>();

                for (int i = 0; i < items.length; i++) {
                    if (items[i].level == 1)
                        itemsSort.add((items[i]));
                    if (items[i].level == 2) {
                        itemsSortChild.add((items[i]));

                    }
                }

                for (int i = 0; i < itemsSort.size(); i++) {
                    ArrayList<Comment> temp = new ArrayList<>();
                    for (int j = 0; j < itemsSortChild.size(); j++) {
                        if (itemsSort.get(i).id.intValue() == itemsSortChild.get(j).parentId.intValue()) {
                            temp.add(itemsSortChild.get(j));
                        }
                        itemsSort.get(i).childSize = temp.size();
                    }
                    if (temp.size() > 0) {
                        hashChild.put(itemsSort.get(i).id, temp);
                    }
                }

                for (HashMap.Entry me : hashChild.entrySet()) {

                    for (int i = 0; i < itemsSort.size(); i++) {
                        if (me.getKey() == itemsSort.get(i).id) {
                            itemsSort.addAll(i + 1, (ArrayList<Comment>) me.getValue());
                            break;
                        }
                    }
                }


                for (int i = 0; i < itemsSort.size(); i++) {
                    itemsSort.get(i).position = i;
                    list.itemAdd(new CommentsRecyclerItem(itemsSort.get(i)));
                }

                list.smoothScrollToPosition(position);
                position = 0;



                break;
        }
    }

    @Override
    public void onSelectReplyComment(Integer selectReply, Integer pos, String name) {
        llReply.setVisibility(View.VISIBLE);
        tvReplyName.setText(" " + name);
        selectParentId  = selectReply;
        int valueInPixelsTop = (int) getResources().getDimension(R.dimen.padding_add_comment);
        int valueInPixelsOther = (int) getResources().getDimension(R.dimen.padding_default);
        position = pos;
        edInputComment.setPadding(valueInPixelsOther, valueInPixelsTop, valueInPixelsOther, valueInPixelsOther);
        edInputComment.requestFocus();
        InputMethodManager imm=(InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

    }

    @Override
    public void onDeleteComment(Integer id, Integer position) {
        //Core.get().CommentsControl().deleteComments(id, idNews);
        DialogBuilder.showCommentDeleteDialog(this, id, position,
                Core.get().LocalizationControl().getText(R.id.delete_comment_dialog_title),
                Core.get().LocalizationControl().getText(R.id.delete_comment_dialog_text));
    }

    @Override
    public void onOkCommentDialogDialog(int id, int pos) {
        Core.get().CommentsControl().deleteComments(id, idNews);
        position = pos;
    }

    @Override
    public void onCancelCommentDialogDialog(int id) {

    }
}
