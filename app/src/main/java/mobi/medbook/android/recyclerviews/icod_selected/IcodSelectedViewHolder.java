package mobi.medbook.android.recyclerviews.icod_selected;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import mobi.medbook.android.R;
import mobi.medbook.android.recyclerviews.base.BaseViewHolder;

import mobi.medbook.android.types.news.IcodSelected;


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

    }

    public void init(IcodSelectedRecyclerItem owner, IcodSelected icodSelected) {
        tvCode.setText(icodSelected.code);
        tvTitle.setText(icodSelected.title);

        rootView.setOnLongClickListener(owner);
    }
}
