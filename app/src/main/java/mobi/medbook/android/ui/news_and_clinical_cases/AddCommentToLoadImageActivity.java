package mobi.medbook.android.ui.news_and_clinical_cases;

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

import java.io.IOException;

import mobi.medbook.android.Core;
import mobi.medbook.android.R;
import mobi.medbook.android.ui.custom_views.MedBookActivity;


public class AddCommentToLoadImageActivity extends MedBookActivity {

    private Button btnAddComment;
    private TextInputLayout edtLayout;
    private TextView tvTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_comment_to_load_image);
        tvTitle = findViewById(R.id.activity_add_comment_to_load_image_toolbar_title);
        ImageView imClose = findViewById(R.id.activity_add_comment_to_load_image_toolbar_close);
        imClose.setOnClickListener(view -> {
            Intent resultIntent = new Intent();
            setResult(RESULT_CANCELED, resultIntent);
            finish();
        });

        ImageView imImage = findViewById(R.id.activity_add_comment_to_load_image_image);

        if (getIntent() != null && getIntent().getData() != null) {
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), getIntent().getData());
                imImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        btnAddComment = findViewById(R.id.activity_add_comment_to_load_image_comment_btn_create);
        btnAddComment.setVisibility(View.INVISIBLE);
        AppCompatEditText edtCommment = findViewById(R.id.activity_add_comment_to_load_image_comment_input);
        edtLayout = findViewById(R.id.activity_add_comment_to_load_image_comment_layout);
        edtCommment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                btnAddComment.setVisibility(s.length() > 5 ? View.VISIBLE : View.INVISIBLE);
            }
        });
        btnAddComment.setOnClickListener(view -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("comment", edtCommment.getText().toString());
            setResult(RESULT_OK, resultIntent);
            finish();
        });
        edtLayout.requestFocus();
        onLocalizationUpdate();
    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        setResult(RESULT_CANCELED, resultIntent);
        super.onBackPressed();
    }

    @Override
    protected void onLocalizationUpdate() {
        btnAddComment.setText(Core.get().LocalizationControl().getText(R.id.activity_add_comment_to_load_image_comment_btn_create));
        tvTitle.setText(Core.get().LocalizationControl().getText(R.id.activity_add_comment_to_load_image_toolbar_title));
        edtLayout.setHint(Core.get().LocalizationControl().getText(R.id.activity_add_comment_to_load_image_comment_layout));
    }
}
