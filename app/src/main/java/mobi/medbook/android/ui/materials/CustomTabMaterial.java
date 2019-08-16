package mobi.medbook.android.ui.materials;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import mobi.medbook.android.R;

public class CustomTabMaterial extends ConstraintLayout {
    public CustomTabMaterial(Context context) {
        super(context);
    }

    public CustomTabMaterial(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTabMaterial(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTitle(String text) {
        ((TextView)findViewById(R.id.custom_tab_material_title)).setText(text);
    }

    public void setSelect() {
        ((TextView)findViewById(R.id.custom_tab_material_title)).setTextColor(getResources().getColor(R.color.color_tab_text_select));
    }
    public void setUnSelect() {
        ((TextView)findViewById(R.id.custom_tab_material_title)).setTextColor(getResources().getColor(R.color.color_tab_text_unselect));
    }

    public void setDotVivible(int visible) {
        findViewById(R.id.custom_tab_material_dot).setVisibility(visible);
    }
}
