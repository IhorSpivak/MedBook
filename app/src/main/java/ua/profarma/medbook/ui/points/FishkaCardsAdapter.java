package ua.profarma.medbook.ui.points;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ru.rambler.libs.swipe_layout.SwipeLayout;
import ua.profarma.medbook.R;
import ua.profarma.medbook.events.points.EventLoadFishkaCards;
import ua.profarma.medbook.models.CardFishka;
import ua.profarma.medbook.models.response.ReferenceItem;
import ua.profarma.medbook.types.points.FishkaCard;
import ua.profarma.medbook.ui.reference.ReferenceListAdapter;

public class FishkaCardsAdapter extends RecyclerView.Adapter<FishkaCardsAdapter.RecyclerViewHolder> {
    private ArrayList<CardFishka> listItem = new ArrayList<>();
    private FishkaCardsAdapter.OnItemClickListener itemClickListener;
    private Context context;


    public void setItemClickListener(FishkaCardsAdapter.OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void addAll(List<CardFishka> items) {
        listItem.clear();
        listItem.addAll(items);
        notifyDataSetChanged();
    }

    public FishkaCardsAdapter(Context context, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.itemClickListener = onItemClickListener;


    }

    @Override
    public FishkaCardsAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fishka_card, parent, false);
        return new FishkaCardsAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FishkaCardsAdapter.RecyclerViewHolder holder, int position) {
        holder.bind(listItem.get(position), context);
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTittle;
        private TextView tvTime;
        private TextView number;
        private CardView cv;
        private SwipeLayout root;
        private RelativeLayout rlDeleteItem;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            root = (SwipeLayout) itemView.findViewById(R.id.root);
            cv = (CardView) itemView.findViewById(R.id.item_fishka_card_root);
            number = (TextView) itemView.findViewById(R.id.item_fishka_card_number);
            rlDeleteItem = (RelativeLayout) itemView.findViewById(R.id.rlDeleteItem);


        }

        public void bind(CardFishka item, Context context) {
            number.setText(item.getNumber());
            cv.setOnClickListener(view -> itemClickListener.onItemClick(item));
            rlDeleteItem .setOnClickListener(view -> itemClickListener.onIsRefused(item));

        }

    }

    public interface OnItemClickListener {
        void onItemClick(CardFishka item);
        void onIsRefused(CardFishka item);
    }

    public void removeItem(CardFishka item){
        for (int i = 0; i < listItem.size(); i++) {
            if(Objects.equals(item.getId(), listItem.get(i).getId())){
                listItem.remove(i);
                notifyItemRemoved(i);
            }
        }
    }


}
