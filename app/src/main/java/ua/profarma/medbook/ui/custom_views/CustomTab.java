package ua.profarma.medbook.ui.custom_views;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ua.profarma.medbook.R;

public class CustomTab extends RelativeLayout {
    public CustomTab(Context context) {
        super(context);
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.custom_tab_exchange, this);
    }

    public void setSelect() {
        ((TextView)findViewById(R.id.item_tab_text)).setTextColor(getResources().getColor(R.color.color_tab_text_select));
    }
    public void setUnSelect() {
        ((TextView)findViewById(R.id.item_tab_text)).setTextColor(getResources().getColor(R.color.color_tab_text_unselect));
    }

    public void setTitle(String text) {
        ((TextView)findViewById(R.id.item_tab_text)).setText(text);
    }
}
