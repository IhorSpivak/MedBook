package mobi.medbook.android.recyclerviews.fishka_cards;

import android.content.Context;
import android.content.ContextWrapper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mobi.medbook.android.R;
import mobi.medbook.android.recyclerviews.base.BaseViewHolder;
import mobi.medbook.android.recyclerviews.base.RecyclerItems;

import mobi.medbook.android.ui.points.IOnDeleteFishkaCard;
import ua.profarma.medbook.recyclerviews.base.BaseRecyclerView;


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
