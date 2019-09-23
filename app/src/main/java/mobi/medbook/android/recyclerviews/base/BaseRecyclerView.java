package mobi.medbook.android.recyclerviews.base;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import mobi.medbook.android.recyclerviews.base.HolderCreator;
import mobi.medbook.android.recyclerviews.base.RecyclerAdapter;
import mobi.medbook.android.recyclerviews.base.RecyclerItem;
import mobi.medbook.android.recyclerviews.base.RecyclerItems;
import mobi.medbook.android.recyclerviews.base.TouchHelper;

public abstract class BaseRecyclerView extends RecyclerView implements HolderCreator {

    protected RecyclerAdapter adapter = null;
    protected RecyclerItems items = null;

    public BaseRecyclerView(Context context) {
        this(context, null);
    }

    public BaseRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void AnimationChangeDisable() {
        ItemAnimator animator = getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            animator.setChangeDuration(0);
        }
    }

    public void AnimationMoveDisable() {
        ItemAnimator animator = getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            animator.setMoveDuration(0);
        }
    }


    protected void initMoveItems() {
        ItemTouchHelper.Callback callback = new TouchHelper(adapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(this);
    }

    public void init() {
        items = createItems();

        createAdapter();

        setLayoutManager(new LinearLayoutManager(getContext()));

        setHasFixedSize(true);
        setAdapter(adapter);
    }

    protected void createAdapter() {
        adapter = new RecyclerAdapter(items, this);
    }

    public void update() {
        adapter.notifyDataSetChanged();
    }

    public void itemUpdate(int position) {
        if (position >= 0 && position < items.size()) {
            adapter.notifyItemChanged(position);
        }
    }

    public void itemRemove(int _position) {
        if (_position >= 0 && _position < items.size()) {
            items.remove(_position);
            adapter.notifyItemRemoved(_position);
        }
    }

    public void itemAdd(int position, RecyclerItem item) {
        items.add(position, item);
        adapter.notifyItemInserted(position);
    }

    public void itemAdd(RecyclerItem item) {
        items.add(item);
        adapter.notifyItemInserted(items.size() - 1);
    }

    public void itemMove(int positionFrom, int positionTo) {
        RecyclerItem item = items.get(positionFrom);
        items.remove(positionFrom);
        items.add(positionTo, item);
        adapter.notifyItemMoved(positionFrom, positionTo);
    }

    public void animateTo(RecyclerItems itemsNew) {
        applyAndAnimateRemovals(itemsNew);
        applyAndAnimateAdditions(itemsNew);
        applyAndAnimateMovedItems(itemsNew);
        scrollToPosition(0);
    }

    public void itemsAdd(RecyclerItems itemsNew) {
        items.clear();
        items.addAll(itemsNew);
        adapter.notifyDataSetChanged();
    }

    protected void applyAndAnimateRemovals(RecyclerItems itemsNew) {
        for (int i = items.size() - 1; i >= 0; i--) {
            final RecyclerItem model = items.get(i);
            if (!itemsNew.contains(model)) {
                itemRemove(i);
            }
        }
    }

    protected void applyAndAnimateAdditions(RecyclerItems itemsNew) {
        for (int i = 0, count = itemsNew.size(); i < count; i++) {
            final RecyclerItem model = itemsNew.get(i);
            if (!items.contains(model)) {
                itemAdd(i, model);
            }
        }
    }

    protected void applyAndAnimateMovedItems(RecyclerItems itemsNew) {
        for (int toPosition = itemsNew.size() - 1; toPosition >= 0; toPosition--) {
            final RecyclerItem model = itemsNew.get(toPosition);
            final int fromPosition = items.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                itemMove(fromPosition, toPosition);
            }
        }
    }

    protected abstract RecyclerItems createItems();

    public void clear() {
        while (items.size() > 0) {
            itemRemove(0);
        }
    }

}
