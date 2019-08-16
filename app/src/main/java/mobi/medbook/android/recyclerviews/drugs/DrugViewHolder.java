package mobi.medbook.android.recyclerviews.drugs;

import android.view.View;
import android.widget.TextView;



import mobi.medbook.android.R;
import mobi.medbook.android.recyclerviews.base.BaseViewHolder;
import mobi.medbook.android.types.news.Drug;


public class DrugViewHolder extends BaseViewHolder {

    private View rootView;
    private TextView tvTitle;

    public DrugViewHolder(View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.item_drugs_title);
        rootView = itemView.findViewById(R.id.item_drugs_root);
    }

    public void init(DrugRecyclerItem owner, Drug drug) {
        if (tvTitle != null) {
            tvTitle.setText(drug.title);
        }
        rootView.setOnClickListener(owner);

    }
}
