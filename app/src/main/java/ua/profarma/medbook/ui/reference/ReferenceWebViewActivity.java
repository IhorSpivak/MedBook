package ua.profarma.medbook.ui.reference;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ua.profarma.medbook.R;
import ua.profarma.medbook.ui.custom_views.MedBookActivity;

public class ReferenceWebViewActivity extends AppCompatActivity {

    public static final String EXTRA_ID= "extra_id";

    @BindView(R.id.webView)
    WebView webView;

    @BindView(R.id.close)
    ImageView close;

    @BindView(R.id.pb)
    ProgressBar pb;


    public static void startActivity(Activity activity, Integer id) {
        Intent intent = new Intent(activity, ReferenceWebViewActivity.class);
        intent.putExtra(EXTRA_ID,id);
        activity.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_web_view);
        ButterKnife.bind(this);

        int id = getIntent().getIntExtra(EXTRA_ID, 0);

        webView.getSettings().setJavaScriptEnabled(true);

        webView.loadUrl("https://app.medbook.mobi/pharm-advice/" + id);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        webView.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                pb.setVisibility(View.GONE);
            }
        });

    }
}
