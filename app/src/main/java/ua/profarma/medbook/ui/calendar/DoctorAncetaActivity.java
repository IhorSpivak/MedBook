package ua.profarma.medbook.ui.calendar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.Calendar;

import ua.profarma.medbook.Core;
import ua.profarma.medbook.R;
import ua.profarma.medbook.events.core.Event;
import ua.profarma.medbook.recyclerviews.base.RecyclerItems;
import ua.profarma.medbook.recyclerviews.visit_doctor_question.VisitDoctorQuestionRecyclerItem;
import ua.profarma.medbook.recyclerviews.visit_doctor_question.VisitDoctorQuestionsRecyclerView;
import ua.profarma.medbook.types.visits.QuestionVisit;
import ua.profarma.medbook.types.visits.UserVisit;
import ua.profarma.medbook.ui.custom_views.MedBookActivity;
import ua.profarma.medbook.utils.DialogBuilder;
import ua.profarma.medbook.utils.TextUtils;
import ua.profarma.medbook.utils.TimeUtils;

public class DoctorAncetaActivity extends MedBookActivity {

    private Button btnSend;
    private VisitDoctorQuestionsRecyclerView list;
    private RecyclerItems items;
    private UserVisit userVisit;
    public static String KEY_ID_VISIT = "KEY_ID_VISIT";

    private TextView tvDate;
    private TextView tvTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int idVisit;
        setContentView(R.layout.activity_visit_doctor_anketa);
        if (getIntent() == null) {
            finish();
        } else {
            idVisit = getIntent().getIntExtra(KEY_ID_VISIT, -1);
            if (idVisit == -1)
                finish();
            else
                userVisit = Core.get().VisitsControl().getUserVisitForId(idVisit);
        }
        if (userVisit == null)
            finish();

        tvTitle = findViewById(R.id.activity_visit_doctor_anceta_toolbar_title);
        ImageView imvClose = findViewById(R.id.activity_visit_doctor_anceta_toolbar_close);
        imvClose.setOnClickListener(view -> finish());
        items = new RecyclerItems();
        list = findViewById(R.id.activity_visit_doctor_list);
        list.init();


        tvDate = findViewById(R.id.activity_visit_doctor_anceta_time);
        TextView tvName = findViewById(R.id.activity_visit_doctor_anceta_name);
        TextView tvTitleVisit = findViewById(R.id.activity_visit_doctor_anceta_title_of_visit);
        TextView tvDescription = findViewById(R.id.activity_visit_doctor_anceta_description_of_visit);

        ImageView dotStatus = findViewById(R.id.activity_visit_doctor_anceta_dot);
        TextView tvStatus = findViewById(R.id.activity_visit_doctor_anceta_status);

        tvStatus.setTextColor(getResources().getColor(R.color.color_circle_visit_started));
        tvStatus.setText(Core.get().LocalizationControl().getText(R.id.status_visits_started));
        dotStatus.setImageResource(R.drawable.circle_visit_started);

        String nameMember = TextUtils.getString(userVisit.partner.partner.last_name) + " " + TextUtils.getString(userVisit.partner.partner.first_name)
                + " " + TextUtils.getString(userVisit.partner.partner.middle_name);
        tvName.setText(nameMember);

        if (userVisit.visit.title == null || userVisit.visit.title.isEmpty())
            tvTitleVisit.setVisibility(View.GONE);
        else
            tvTitleVisit.setText(userVisit.visit.title);

        if (userVisit.visit.description == null || userVisit.visit.description.isEmpty())
            tvDescription.setVisibility(View.GONE);
        else
            tvDescription.setText(userVisit.visit.description);

        setDate();
        btnSend = findViewById(R.id.activity_visit_doctor_anceta_btn_send);
        btnSend.setOnClickListener(view -> {
            if (Core.get().VisitsControl().isStartSendDoctorAnceta()) {
                Core.get().VisitsControl().visitDoctorResult(userVisit.id);
                finish();
            } else
                DialogBuilder.showInfoDialog(this, "Повідомлення", "Не проставлені усі оцінки");

        });
        Core.get().VisitsControl().getUserVisitQuestionnaireDoctor(userVisit.id, userVisit.visit_id);
        onLocalizationUpdate();
    }

    private void setDate() {
        Calendar calFrom = Calendar.getInstance();
        calFrom.setTimeInMillis(userVisit.time_from * 1000);
        Calendar calTo = Calendar.getInstance();
        calTo.setTimeInMillis(userVisit.time_to * 1000);
        int year = calFrom.get(Calendar.YEAR);
        int month = calFrom.get(Calendar.MONTH);
        int day = calFrom.get(Calendar.DAY_OF_MONTH);
        int dayWeek = calFrom.get(Calendar.DAY_OF_WEEK);
        int hourFrom = calFrom.get(Calendar.HOUR_OF_DAY);
        int minutesFrom = calFrom.get(Calendar.MINUTE);

        int hourTo = calTo.get(Calendar.HOUR_OF_DAY);
        int minutesTo = calTo.get(Calendar.MINUTE);


        String dateText = TimeUtils.getDayOfWeek(dayWeek) + ", " + day + " " + TimeUtils.getMonthName_s(month)
                + " " + year + "\n" + hourFrom + ":" + TimeUtils.getFormatMinutes(minutesFrom) + " - " + hourTo + ":" + TimeUtils.getFormatMinutes(minutesTo);
        tvDate.setText(dateText);
    }

    @Override
    protected void onLocalizationUpdate() {
        btnSend.setText(Core.get().LocalizationControl().getText(R.id.activity_visit_doctor_anceta_btn_send));
        tvTitle.setText(Core.get().LocalizationControl().getText(R.id.activity_visit_doctor_anceta_toolbar_title));
    }

    @Override
    public void onEvent(Event event) {
        super.onEvent(event);
        if (event.getEventId() == Event.EVENT_USER_VISIT_QUESTIONNAIRE_DOCTOR_LOAD) {
            list.clear();
            items.clear();
            QuestionVisit[] questionsVisit = Core.get().VisitsControl().getUserVisitQuestionnaireDoctor().data;
            for (int i = 0; i < questionsVisit.length; i++) {
                items.add(new VisitDoctorQuestionRecyclerItem(questionsVisit[i]));
            }
            list.itemsAdd(items);
        }
    }
}
