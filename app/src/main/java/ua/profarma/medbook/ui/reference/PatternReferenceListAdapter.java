package ua.profarma.medbook.ui.reference;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ua.profarma.medbook.R;
import ua.profarma.medbook.models.response.ReferenceItem;

public class PatternReferenceListAdapter extends RecyclerView.Adapter<PatternReferenceListAdapter.RecyclerViewHolder> {
    private ArrayList<ReferenceItem> listItem = new ArrayList<>();
    private OnItemClickListener itemClickListener;
    private Context context;



    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void addAll(List<ReferenceItem> items) {
        listItem.clear();
        listItem.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reference_list, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.bind(listItem.get(position), context);
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTittle;
        private TextView tvTime;
        private TextView tvId;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            tvTittle = (TextView) itemView.findViewById(R.id.tv_name);
            tvTime = (TextView) itemView.findViewById(R.id.tv_date);
            tvId = (TextView) itemView.findViewById(R.id.tv_id);

        }

        public void bind(ReferenceItem item, Context context) {
            tvTittle.setText(item.getTitle());
            tvTime.setText(item.getDate());
            tvId.setText(item.getId());

        }

    }

    public interface OnItemClickListener {
        void onItemClick(ReferenceItem item);
    }
}
