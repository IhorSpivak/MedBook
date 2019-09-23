package mobi.medbook.android.ui.calendar;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import mobi.medbook.android.R;
import mobi.medbook.android.models.requests.AnswerRequest;
import mobi.medbook.android.models.response.ReferenceItem;
import mobi.medbook.android.singltones.SingletoneForMPTest;
import mobi.medbook.android.singltones.SingltonForPatterns;
import mobi.medbook.android.types.visits.AnswerMP;
import mobi.medbook.android.ui.reference.PatternReferenceListAdapter;

public class AnswerListAdapter extends RecyclerView.Adapter<AnswerListAdapter.RecyclerViewHolder> {
    private ArrayList<AnswerMP> listItem = new ArrayList<>();
    private AnswerListAdapter.OnItemClickListener itemClickListener;
    private Context context;
    private String idQuestion;

    public AnswerListAdapter(String idQuestion) {
        this.idQuestion = idQuestion;
    }

    private int lastCheckedPosition = -1;



    public void setItemClickListener(AnswerListAdapter.OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void addAll(List<AnswerMP> items) {
        listItem.clear();
        listItem.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public AnswerListAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_anceta_item, parent, false);
        return new AnswerListAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AnswerListAdapter.RecyclerViewHolder holder, int position) {
        holder.bind(listItem.get(position), context);
        holder.radioButton.setChecked(position == lastCheckedPosition);
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private RadioButton radioButton;


        RecyclerViewHolder(View itemView) {
            super(itemView);
            radioButton = (RadioButton) itemView.findViewById(R.id.item_test_single_choice_root);


        }

        public void bind(AnswerMP item, Context context) {
            radioButton.setText(item.awnser_description);


            radioButton.setOnClickListener(v -> {
                lastCheckedPosition = getAdapterPosition();
                SingletoneForMPTest.getInstance().getList().add(new AnswerRequest(idQuestion,item.awnser_id,item.awnser_title,item.awnser_description));
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                }, 200);
                itemClickListener.onItemClick(item);

            });


        }

    }

    public interface OnItemClickListener {
        void onItemClick(AnswerMP item);
    }
}
