package mobi.medbook.android.recyclerviews.notifications;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mobi.medbook.android.R;
import mobi.medbook.android.recyclerviews.base.BaseRecyclerView;
import mobi.medbook.android.recyclerviews.base.BaseViewHolder;
import mobi.medbook.android.recyclerviews.base.RecyclerItems;



public class NotificationsRecyclerView extends BaseRecyclerView {

    private String TAG = "AppMedbook/NotificationsRecyclerView";

    public NotificationsRecyclerView(Context context) {
        this(context, null);
    }

    public NotificationsRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public NotificationsRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected RecyclerItems createItems() {
        return new RecyclerItems();
    }

    @Override
    public BaseViewHolder create(ViewGroup parent, int viewType) {
        View itemView = null;
        itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_notification, parent, false);
//        if (viewType == 1)
//            itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_material_type_list, parent, false);
//        else if (viewType == 2)
//            itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_material_type_tile, parent, false);
        return new NotificationViewHolder(itemView);
    }
}
