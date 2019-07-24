package ua.profarma.medbook.ui.news_and_clinical_cases.icods_viewer;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import ua.profarma.medbook.App;
import ua.profarma.medbook.Core;
import ua.profarma.medbook.R;
import ua.profarma.medbook.events.core.EventRouter;
import ua.profarma.medbook.events.news.EventAddIcod;
import ua.profarma.medbook.events.news.EventRemoveIcod;
import ua.profarma.medbook.events.news.EventStartProgress;
import ua.profarma.medbook.events.news.EventUpdateIcod;
import ua.profarma.medbook.types.news.Icod;

public class ItemIcod extends LinearLayout {

    private int level;
    private Icod item;
    private boolean expand = false;
    private boolean checkable = false;
    private boolean checked = false;

    public void setCheckable(boolean checkable) {
        this.checkable = checkable;
    }

    public boolean isCheckable() {
        return checkable;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
        findViewById(R.id.item_expand_icod_icon_check).setVisibility(VISIBLE);
        EventRouter.send(new EventAddIcod(item));
    }

    public boolean isChecked() {
        return checked;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public boolean isExpand() {
        return expand;
    }

    public ItemIcod(Context context) {
        super(context);
        //EventRouter.register(this);
    }

    public ItemIcod(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //EventRouter.register(this);
    }

    public ItemIcod(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //ventRouter.register(this);
    }

//    @Override
//    protected void onDetachedFromWindow() {
//        EventRouter.unregister(this);
//        super.onDetachedFromWindow();
//    }

    public void setItem(Icod _item, int _level) {
        level = _level;
        item = _item;
        ((TextView) findViewById(R.id.item_expand_icod_icod)).setText(item.code_icod);

        int selectLang = -1;
        for (int i = 0; i < item.translations.length; i++) {
            if (item.translations[i].language.substring(0, 2).equals(App.getLanguage())) {
                selectLang = i;
            }
        }
        if (selectLang == -1) {
            for (int i = 0; i < item.translations.length; i++) {
                if (item.translations[i].language.substring(0, 2).equals("uk")) {
                    selectLang = i;
                }
            }
        }
        ((TextView) findViewById(R.id.item_expand_icod_title)).setText(item.translations[selectLang].title);

        if(level == 0){
            ((TextView) findViewById(R.id.item_expand_icod_icod)).setTextColor(Color.rgb(54, 54, 54));
            ((TextView) findViewById(R.id.item_expand_icod_title)).setTextColor(Color.rgb(41, 89, 157));
        }

        if(level == 1){
            ((TextView) findViewById(R.id.item_expand_icod_icod)).setTextColor(Color.rgb(54, 54, 54));
            ((TextView) findViewById(R.id.item_expand_icod_title)).setTextColor(Color.rgb(54, 54, 54));
        }

        if(level > 2){
            ((TextView) findViewById(R.id.item_expand_icod_icod)).setTextColor(Color.rgb(73, 121, 167));
            ((TextView) findViewById(R.id.item_expand_icod_title)).setTextColor(Color.rgb(73, 121, 167));
        }

        switch (level) {
            case 1:
                //((TextView) findViewById(R.id.item_expand_icod_icod)).setTextColor(Color.GREEN);
                //((TextView) findViewById(R.id.item_expand_icod_title)).setTextColor(Color.GREEN);
                setPadding(50, 0, 0, 0);
                break;
            case 2:
                //((TextView) findViewById(R.id.item_expand_icod_icod)).setTextColor(Color.DKGRAY);
                //((TextView) findViewById(R.id.item_expand_icod_title)).setTextColor(Color.DKGRAY);
               setPadding(80, 0, 0, 0);
                break;
            case 3:
                //((TextView) findViewById(R.id.item_expand_icod_icod)).setTextColor(Color.RED);
                //((TextView) findViewById(R.id.item_expand_icod_title)).setTextColor(Color.RED);
                setPadding(120, 0, 0, 0);
                break;
            case 4:
                //((TextView) findViewById(R.id.item_expand_icod_icod)).setTextColor(Color.MAGENTA);
                //((TextView) findViewById(R.id.item_expand_icod_title)).setTextColor(Color.MAGENTA);
                setPadding(160, 0, 0, 0);
                break;
            case 5:
                //((TextView) findViewById(R.id.item_expand_icod_icod)).setTextColor(Color.YELLOW);
                //((TextView) findViewById(R.id.item_expand_icod_title)).setTextColor(Color.YELLOW);
                setPadding(200, 30, 0, 0);
                break;
            case 6:
                //((TextView) findViewById(R.id.item_expand_icod_icod)).setTextColor(Color.CYAN);
                //((TextView) findViewById(R.id.item_expand_icod_title)).setTextColor(Color.CYAN);
                setPadding(240, 0, 0, 0);
                break;
        }

        setOnClickListener(v -> {
            if (!checkable) {
                if (level == 0 && expand) {
                    EventRouter.send(new EventStartProgress());
                    Core.get().NewsControl().getIcodLevelRoot();
                } else if (!expand) {
                    EventRouter.send(new EventStartProgress());
                    Core.get().NewsControl().getIcod(level + 1, item.id);
                } else {
                    EventRouter.send(new EventUpdateIcod(item.id));
                }
            } else {
                checked = !checked;
                if (checked)
                    EventRouter.send(new EventAddIcod(item));
                else
                    EventRouter.send(new EventRemoveIcod(item));
                findViewById(R.id.item_expand_icod_icon_check).setVisibility(checked ? VISIBLE : INVISIBLE);
            }

        });
    }

    public int getLevel() {
        return level;
    }

    public int getId() {
        return item.id;
    }
}
