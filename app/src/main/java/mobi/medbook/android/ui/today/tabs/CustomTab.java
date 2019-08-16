package mobi.medbook.android.ui.today.tabs;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import mobi.medbook.android.R;

public class CustomTab extends ConstraintLayout {
    public CustomTab(Context context) {
        super(context);
    }

    public CustomTab(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTitle(String text) {
        ((TextView)findViewById(R.id.custom_tab_today_title)).setText(text);
    }

    public void setSelect() {
        ((TextView)findViewById(R.id.custom_tab_today_title)).setTextColor(getResources().getColor(R.color.color_tab_today_text_select));
        findViewById(R.id.custom_tab_today_dot).setVisibility(VISIBLE);
    }
    public void setUnSelect() {
        ((TextView)findViewById(R.id.custom_tab_today_title)).setTextColor(getResources().getColor(R.color.color_tab_today_text_unselect));
        findViewById(R.id.custom_tab_today_dot).setVisibility(INVISIBLE);
    }
}
