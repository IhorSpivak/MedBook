package mobi.medbook.android.ui.calendar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import mobi.medbook.android.R;
import mobi.medbook.android.types.visits.UserRelation;

public class SecondMemberListAdapter extends RecyclerView.Adapter<SecondMemberListAdapter.BrandViewHolder> implements Filterable {
    private List<UserRelation> brandsList = new ArrayList<>();
    private List<UserRelation> brandsListFiltered = new ArrayList<>();
    private SecondMemberListAdapter.OnItemClickListener itemClickListener;

    public SecondMemberListAdapter(){

    }

    public void setItems(List<UserRelation> items){
        this.brandsList = items;
        this.brandsListFiltered = items;
    }

    public void setItemClickListener(SecondMemberListAdapter.OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }



    @NonNull
    @Override
    public BrandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_second_member_visit, parent, false);
        return new BrandViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BrandViewHolder holder, int position) {
        holder.bind(brandsListFiltered.get(position));
    }

    @Override
    public int getItemCount() {
        return brandsListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    brandsListFiltered = brandsList;
                } else {
                    List<UserRelation> filteredList = new ArrayList<>();
                    for (UserRelation row : brandsList) {
                        if (row.middle_name.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    brandsListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = brandsListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                brandsListFiltered = (ArrayList<UserRelation>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class BrandViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTitle;
        private TextView tvMainLetter;
        private UserRelation item;
        private RelativeLayout root;

        public BrandViewHolder(View itemView) {
            super(itemView);

        }

        public void bind(UserRelation item){
            this.item = item;
            tvTitle.setText(item.first_name);
            boolean isFirst = isFirstItemInList();
            if(isFirst){
                String mainChar = item.first_name;
                String s = mainChar.substring(0,1);
                tvMainLetter.setText(s);
            } else {
                tvMainLetter.setText("");
            }
            root.setOnClickListener(view -> itemClickListener.onItemClick(item));
        }

        private boolean isFirstItemInList(){
            int position = getAdapterPosition();
            if(position==0||!brandsListFiltered.get(position-1).first_name.substring(0,1).toLowerCase().equals(item.middle_name.substring(0,1).toLowerCase())){
                return true;
            }
            return false;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(UserRelation item);
    }


}

