package ua.profarma.medbook.recyclerviews.drugs;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import ua.profarma.medbook.App;
import ua.profarma.medbook.R;
import ua.profarma.medbook.recyclerviews.base.BaseViewHolder;
import ua.profarma.medbook.types.news.Drug;
import ua.profarma.medbook.types.points.HistoryExchange;

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
