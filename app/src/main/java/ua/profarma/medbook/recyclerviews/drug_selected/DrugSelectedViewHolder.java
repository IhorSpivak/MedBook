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
    private ImageView minus;
    private ImageView plus;
    private TextView qty;
    private ImageView imvDelete;

    public DrugSelectedViewHolder(View itemView) {
        super(itemView);
        rootView = itemView.findViewById(R.id.item_drug_selected_root);
        tvTitle = itemView.findViewById(R.id.item_drug_selected_title);
        minus = itemView.findViewById(R.id.btn_minus);
        plus = itemView.findViewById(R.id.btn_plus);
        qty = itemView.findViewById(R.id.tv_qty);

    }

    public void init(DrugSelectedRecyclerItem owner, DrugSelected drugSelected) {
        tvTitle.setText(drugSelected.title);
        if(drugSelected.id == 1){
            qty.setVisibility(View.GONE);
        }
        qty.setText(Integer.toString(drugSelected.qty));
        if(drugSelected.viewing) plus.setVisibility(View.GONE);
        if(drugSelected.viewing) minus.setVisibility(View.GONE);
        rootView.setOnLongClickListener(owner);
        rootView.setOnClickListener(owner);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = Integer.parseInt(qty.getText().toString());
                    int b = a + 1;
                    qty.setText(Integer.toString(b));
                drugSelected.qty = Integer.parseInt(qty.getText().toString());
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = Integer.parseInt(qty.getText().toString());
                if(a == 1){

                } else {
                    int b = a - 1;
                    qty.setText(Integer.toString(b));
                    drugSelected.qty = Integer.parseInt(qty.getText().toString());
                }
            }
        });


    }
}
