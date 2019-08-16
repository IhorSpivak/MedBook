package mobi.medbook.android.ui.today;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import mobi.medbook.android.Core;
import mobi.medbook.android.R;
import mobi.medbook.android.ui.custom_views.MedBookActivity;


public class MainDepartmentsActivity extends MedBookActivity {

    private TextView tvTitle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_departments);

        tvTitle = findViewById(R.id.activity_main_departments_title);

        ImageView imvFinish = findViewById(R.id.activity_main_departments_close);
        imvFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        RelativeLayout llUmch = findViewById(R.id.activity_main_departments_umch);
        RelativeLayout llMdwworld = findViewById(R.id.activity_main_departments_mdwworld);
        RelativeLayout llGeoapteka = findViewById(R.id.activity_main_departments_geoapteka);
        RelativeLayout llCompedium = findViewById(R.id.activity_main_departments_compedium);

        llUmch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.umj.com.ua/"));
                startActivity(browserIntent);
            }
        });

        llMdwworld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://mdmworld.com/"));
                startActivity(browserIntent);
            }
        });

        llGeoapteka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://geoapteka.ua/"));
                startActivity(browserIntent);
            }
        });

        llCompedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://compendium.com.ua"));
                startActivity(browserIntent);
            }
        });

        onLocalizationUpdate();
    }

    @Override
    protected void onLocalizationUpdate() {
        tvTitle.setText(Core.get().LocalizationControl().getText(R.id.activity_main_departments_title));
    }
}
