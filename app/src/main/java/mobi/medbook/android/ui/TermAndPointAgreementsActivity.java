package mobi.medbook.android.ui;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import mobi.medbook.android.Core;
import mobi.medbook.android.R;
import mobi.medbook.android.events.EventLogout;
import mobi.medbook.android.events.EventTermsAndAgreementsOk;
import mobi.medbook.android.events.core.Event;
import mobi.medbook.android.events.core.EventRouter;
import mobi.medbook.android.events.profile.EventAgreementsLoad;
import mobi.medbook.android.events.profile.EventPointsAgreementsOk;
import mobi.medbook.android.ui.custom_views.MedBookActivity;
import mobi.medbook.android.utils.LogUtils;


public class TermAndPointAgreementsActivity extends MedBookActivity {

    public static String KEY_TYPE = "KEY_TYPE";

    private Button btnCancel;
    private Button btnOk;
    private int type = -1;
    private ProgressBar progressBar;
    private WebView tvTitle;
    private WebView tvText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_and_points_agreements);
        tvTitle = findViewById(R.id.activity_term_and_points_agreements_title);
        tvText = findViewById(R.id.activity_term_and_points_agreements_text);

        progressBar = findViewById(R.id.activity_term_and_points_agreements_progress);
        progressBar.setVisibility(View.VISIBLE);

        type = getIntent().getIntExtra(KEY_TYPE, -1);
        if (type == -1) {
            EventRouter.send(new EventLogout());
            finish();
        } else {
            Core.get().UserControl().getAgreements(type);
        }
        btnCancel = findViewById(R.id.activity_term_and_points_agreements_cancel);
        btnOk = findViewById(R.id.activity_term_and_points_agreements_ok);

        btnCancel.setEnabled(false);
        btnOk.setEnabled(false);

        btnCancel.setOnClickListener(view ->
        {
            if (type == 1) {
                LogUtils.logD("TermAndPointAgreementsActivity", "EventLogout");
                EventRouter.send(new EventLogout());
            }
            finish();
        });
        btnOk.setOnClickListener(view -> {
            if (type == 1) {
                Core.get().UserControl().updateStatusTermsAndAgreements(1);
                EventRouter.send(new EventTermsAndAgreementsOk());
                finish();
            }
            if (type == 2) {
                Core.get().UserControl().updateStatusPointsAgreements(1);
            }
            if(type == 3){
                EventRouter.send(new EventPointsAgreementsOk());
            }
        });
    }

    @Override
    protected void onLocalizationUpdate() {

    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onEvent(Event event) {
        super.onEvent(event);
        switch (event.getEventId()) {
            case Event.EVENT_AGREEMENTS_LOAD:
                LogUtils.logD("TermAndPointAgreementsActivity", "EVENT_AGREEMENTS_LOAD");
                btnCancel.setEnabled(true);
                btnOk.setEnabled(true);
                progressBar.setVisibility(View.GONE);
                tvTitle.loadData(((EventAgreementsLoad)event).getTitle(), "text/html", "UTF-8");//.setText(Html.fromHtml(((EventAgreementsLoad)event).getTitle()));
                tvText.loadData(((EventAgreementsLoad)event).getDescription(), "text/html", "UTF-8");// .setText(Html.fromHtml(((EventAgreementsLoad)event).getDescription()));
                break;

            case Event.EVENT_POINTS_AGREEMENTS_OK:
                LogUtils.logD("TermAndPointAgreementsActivity", "EVENT_POINTS_AGREEMENTS_OK");
                finish();
                break;

            case Event.EVENT_AGREEMENTS_DONT_LOAD:
                LogUtils.logD("TermAndPointAgreementsActivity", "EVENT_AGREEMENTS_DONT_LOAD");
                break;
        }
    }
}
