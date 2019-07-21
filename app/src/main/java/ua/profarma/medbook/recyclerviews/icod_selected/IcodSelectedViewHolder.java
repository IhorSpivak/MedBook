package ua.profarma.medbook.recyclerviews.icod_selected;

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
import ua.profarma.medbook.recyclerviews.tasks.TaskRecyclerItem;
import ua.profarma.medbook.types.news.IcodSelected;
import ua.profarma.medbook.types.points.HistoryExchange;

public class IcodSelectedViewHolder extends BaseViewHolder {

    private View rootView;
    private TextView tvCode;
    private TextView tvTitle;
    private ImageView imvDelete;

    public IcodSelectedViewHolder(View itemView) {
        super(itemView);
        rootView = itemView.findViewById(R.id.item_icod_selected_root);
        tvCode = itemView.findViewById(R.id.item_icod_selected_code);
        tvTitle = itemView.findViewById(R.id.item_icod_selected_title);
        imvDelete = itemView.findViewById(R.id.item_icod_selected_delete);
    }

    public void init(IcodSelectedRecyclerItem owner, IcodSelected icodSelected) {
        tvCode.setText("["+icodSelected.code+"]");
        tvTitle.setText(icodSelected.title);
        if(icodSelected.viewing) imvDelete.setVisibility(View.GONE);
        imvDelete.setOnClickListener(owner);
    }
}
