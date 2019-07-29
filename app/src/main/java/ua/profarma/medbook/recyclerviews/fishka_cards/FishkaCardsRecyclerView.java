package ua.profarma.medbook.recyclerviews.fishka_cards;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import ua.profarma.medbook.R;
import ua.profarma.medbook.recyclerviews.base.BaseRecyclerView;
import ua.profarma.medbook.recyclerviews.base.BaseViewHolder;
import ua.profarma.medbook.recyclerviews.base.RecyclerItems;
import ua.profarma.medbook.recyclerviews.base.SimpleDividerItemDecoration;
import ua.profarma.medbook.recyclerviews.base.SwipeToDeleteCallback;
import ua.profarma.medbook.ui.points.IOnDeleteFishkaCard;

public class FishkaCardsRecyclerView extends BaseRecyclerView {

    public FishkaCardsRecyclerView(Context context) {
        this(context, null);
    }

    public FishkaCardsRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public FishkaCardsRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected RecyclerItems createItems() {
        return new RecyclerItems();
    }

//    @Override
//    public void init() {
//        super.init();
//
//        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getContext()) {
//            @Override
//            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
//
//                final int position = viewHolder.getAdapterPosition();
//                if (getActivity(getContext()) instanceof IOnDeleteFishkaCard)
//                    getActivity(getContext()).onDeleteFishkaCardRecyclerView(position);
//
//            }
//        };
//        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
//        itemTouchhelper.attachToRecyclerView(this);
//
//        addItemDecoration(new SimpleDividerItemDecoration(getContext()));
//    }

    @Override
    public BaseViewHolder create(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_fishka_card, parent, false);
        return new FishkaCardViewHolder(itemView);
    }

    private IOnDeleteFishkaCard getActivity(Context context) {
        while (context instanceof ContextWrapper) {
            if (context instanceof IOnDeleteFishkaCard) {
                return (IOnDeleteFishkaCard) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }
}
