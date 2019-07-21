package ua.profarma.medbook.ui.points;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import ua.profarma.medbook.Core;
import ua.profarma.medbook.R;
import ua.profarma.medbook.ui.custom_views.MedBookActivity;

public class FishkaRulesActivity extends MedBookActivity {

    private TextView mTvTitle;
    private WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fishka_rules);
        ImageView imBack = findViewById(R.id.activity_fishka_rules_toolbar_close);
        imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTvTitle = findViewById(R.id.activity_fishka_rules_toolbar_title);
        mWebView = findViewById(R.id.activity_fishka_rules_webview);
        mWebView.loadUrl("https://myfishka.com/rules");
        onLocalizationUpdate();
    }

    @Override
    protected void onLocalizationUpdate() {
        mTvTitle.setText(Core.get().LocalizationControl().getText(R.id.activity_fishka_rules_toolbar_title));
    }
}
