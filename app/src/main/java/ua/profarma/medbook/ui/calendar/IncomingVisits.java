package ua.profarma.medbook.ui.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import ua.profarma.medbook.Core;
import ua.profarma.medbook.R;
import ua.profarma.medbook.events.core.Event;
import ua.profarma.medbook.recyclerviews.visits.VisitRecyclerItem;
import ua.profarma.medbook.recyclerviews.visits.VisitsRecyclerView;
import ua.profarma.medbook.ui.custom_views.MedBookActivity;
import ua.profarma.medbook.utils.DialogBuilder;

public class IncomingVisits extends MedBookActivity implements IOnVisit {

    private VisitsRecyclerView list;
    private TextView tvTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_visits);
        ImageView imClose = findViewById(R.id.activity_incoming_toolbar_close);
        tvTitle = findViewById(R.id.activity_incoming_toolbar_title);
        imClose.setOnClickListener(view -> finish());
        list = findViewById(R.id.activity_incoming_list);
        list.init();
        for (int i = 0; i < Core.get().VisitsControl().getUnaccepted().length; i++)
            list.itemAdd(new VisitRecyclerItem(Core.get().VisitsControl().getUnaccepted()[i], VisitUtils.getTodayTime()));

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
