package mobi.medbook.android.recyclerviews.base;

import android.view.ViewGroup;

import java.util.Collections;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    public RecyclerAdapter(RecyclerItems items, HolderCreator holderCreator) {
        this.items = items;
        this.holderCreator = holderCreator;
        //setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        items.get(position).fill(holder);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int _position) {
        return items.get(_position).getViewType();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (holderCreator != null) {
            return holderCreator.create(parent, viewType);
        }
        return null;
    }

    public void swap(int _firstPosition, int _secondPosition) {
        Collections.swap(items, _firstPosition, _secondPosition);
        notifyItemMoved(_firstPosition, _secondPosition);
    }

    public void remove(int _position) {
        notifyItemRemoved(_position);
    }


    protected RecyclerItems items = null;
    protected HolderCreator holderCreator = null;
}
