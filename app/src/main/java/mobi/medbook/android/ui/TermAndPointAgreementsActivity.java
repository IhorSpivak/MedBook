package mobi.medbook.android.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import mobi.medbook.android.App;
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

import static android.text.Html.FROM_HTML_MODE_COMPACT;
import static android.text.Html.fromHtml;


public class TermAndPointAgreementsActivity extends MedBookActivity {

    public static String KEY_TYPE = "KEY_TYPE";

    private Button btnCancel;
    private Button btnOk;
    private int type = -1;
    private ProgressBar progressBar;
    private TextView tv_title;
    private TextView tv_description;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_and_points_agreements);
        tv_title = findViewById(R.id.tv_title);
        tv_description = findViewById(R.id.tv_description);

        progressBar = findViewById(R.id.activity_term_and_points_agreements_progress);
        progressBar.setVisibility(View.VISIBLE);

        type = getIntent().getIntExtra(KEY_TYPE, -1);
        if (type == -1) {
            EventRouter.send(new EventLogout());
            finish();
        } else {
            if(isOnline()) {
                Core.get().UserControl().getAgreements(type);
            } else {
                finish();
                Toast.makeText(this, "Схоже, що нема інтернет-з'єднання", Toast.LENGTH_LONG).show();
            }
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
                App.setTermAgreements();
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

    public boolean isOnline() {
        boolean a = false ;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            a = true;
        }
        return a;
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
                String title = (((EventAgreementsLoad)event).getTitle());
                String htmlTextStr1 = Html.fromHtml(title).toString();

                String description = (((EventAgreementsLoad)event).getDescription());
                String htmlTextStr2 = Html.fromHtml(description).toString();

                tv_title.setText(htmlTextStr1);
                tv_description.setText(htmlTextStr2);

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
