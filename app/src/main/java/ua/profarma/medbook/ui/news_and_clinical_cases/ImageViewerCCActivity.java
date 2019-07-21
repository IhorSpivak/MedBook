package ua.profarma.medbook.ui.news_and_clinical_cases;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import ua.profarma.medbook.Core;
import ua.profarma.medbook.R;
import ua.profarma.medbook.ui.custom_views.MedBookActivity;

public class ImageViewerCCActivity extends MedBookActivity {

    private TextView tvTitle;
    public static final String KEY_URL = "KEY_URL";
    public static final String KEY_COMMENT = "KEY_COMMENT";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_viewer_image_cc);
        tvTitle = findViewById(R.id.activity_viewer_image_cc_toolbar_title);
        ImageView imClose = findViewById(R.id.activity_viewer_image_cc_toolbar_close);
        imClose.setOnClickListener(view -> finish());

        String url = getIntent().getStringExtra(KEY_URL);
        String comment = getIntent().getStringExtra(KEY_COMMENT);

        ImageView imImage = findViewById(R.id.activity_viewer_image_cc_image);
        Picasso.get().load(url).into(imImage);

        TextView tvComment = findViewById(R.id.activity_viewer_image_cc_comment);
        tvComment.setText(comment);


        onLocalizationUpdate();
    }

    @Override
    protected void onLocalizationUpdate() {
        tvTitle.setText(Core.get().LocalizationControl().getText(R.id.activity_add_comment_to_load_image_toolbar_title));
    }
}
