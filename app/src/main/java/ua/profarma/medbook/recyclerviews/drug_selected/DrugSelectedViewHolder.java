package ua.profarma.medbook.recyclerviews.drug_selected;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ua.profarma.medbook.R;
import ua.profarma.medbook.recyclerviews.base.BaseViewHolder;
import ua.profarma.medbook.types.news.Drug;
import ua.profarma.medbook.types.news.DrugSelected;

public class DrugSelectedViewHolder extends BaseViewHolder {

    private View rootView;
    private TextView tvTitle;
    private ImageView imvDelete;

    public DrugSelectedViewHolder(View itemView) {
        super(itemView);
        rootView = itemView.findViewById(R.id.item_drug_selected_root);
        tvTitle = itemView.findViewById(R.id.item_drug_selected_title);
        imvDelete = itemView.findViewById(R.id.item_drug_selected_delete);
    }

    public void init(DrugSelectedRecyclerItem owner, DrugSelected drugSelected) {
        tvTitle.setText(drugSelected.title);
        if(drugSelected.viewing) imvDelete.setVisibility(View.GONE);
        imvDelete.setOnClickListener(owner);
    }
}
