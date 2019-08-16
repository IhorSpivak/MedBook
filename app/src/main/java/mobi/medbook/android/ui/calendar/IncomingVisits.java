package mobi.medbook.android.ui.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import mobi.medbook.android.Core;
import mobi.medbook.android.R;
import mobi.medbook.android.events.core.Event;
import mobi.medbook.android.recyclerviews.visits.VisitRecyclerItem;
import mobi.medbook.android.recyclerviews.visits.VisitsRecyclerView;
import mobi.medbook.android.ui.custom_views.MedBookActivity;
import mobi.medbook.android.utils.DialogBuilder;


public class IncomingVisits extends MedBookActivity implements IOnVisit {

    private VisitsRecyclerView list;
    private TextView tvTitle;
    private TextView tv_empty;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_visits);
        ImageView imClose = findViewById(R.id.activity_incoming_toolbar_close);
        tvTitle = findViewById(R.id.activity_incoming_toolbar_title);
        tv_empty = findViewById(R.id.tv_empty);
        imClose.setOnClickListener(view -> finish());
        list = findViewById(R.id.activity_incoming_list);
        list.init();
        if (Core.get().VisitsControl().getUnaccepted() != null) {
            for (int i = 0; i < Core.get().VisitsControl().getUnaccepted().length; i++)
                list.itemAdd(new VisitRecyclerItem(Core.get().VisitsControl().getUnaccepted()[i], VisitUtils.getTodayTime()));
        } else {
            tv_empty.setVisibility(View.VISIBLE);
        }

        onLocalizationUpdate();
    }

    @Override
    protected void onLocalizationUpdate() {
        tvTitle.setText(Core.get().LocalizationControl().getText(R.id.activity_incoming_toolbar_title));
    }

    @Override
    public void onSelectVisit(int idVisit) {
        if (Core.get().VisitsControl().getUserVisitForId(idVisit) != null) {
            Intent intent = new Intent(this, VisitViewerActivity.class);
            intent.putExtra(VisitViewerActivity.KEY_ID_VISIT, idVisit);
            startActivity(intent);
        } else {
            DialogBuilder.showInfoDialog(this, "Повідомлення", "Зустріч не знайдена");
        }
    }

    @Override
    public void onEvent(Event event) {
        super.onEvent(event);
        if (event.getEventId() == Event.EVENT_LOAD_DASHBOARD_VISITS) {
            list.clear();
            if (Core.get().VisitsControl().getUnaccepted() != null)
                for (int i = 0; i < Core.get().VisitsControl().getUnaccepted().length; i++)
                    list.itemAdd(new VisitRecyclerItem(Core.get().VisitsControl().getUnaccepted()[i], VisitUtils.getTodayTime()));

        }
    }
}
