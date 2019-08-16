package mobi.medbook.android.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import mobi.medbook.android.R;
import mobi.medbook.android.ui.custom_views.MedBookActivity;
import mobi.medbook.android.utils.LogUtils;


public class UpdateActivity extends MedBookActivity {

    private Button btn_update;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.logD("AuthorizationControl", "AuthorizationActivity onCreate");
        setContentView(R.layout.activity_update);
        btn_update = findViewById(R.id.btn_update);

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://play.google.com/store/apps/details?id=mobi.medbook.android&hl=ua";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                finish();
            }
        });

    }

    @Override
    protected void onLocalizationUpdate() {

    }

    @Override
    public void onBackPressed() {

    }
}
