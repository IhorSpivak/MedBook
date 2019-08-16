package mobi.medbook.android.ui.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import com.google.android.material.textfield.TextInputLayout;

import mobi.medbook.android.R;
import mobi.medbook.android.ui.custom_views.MedBookActivity;
import mobi.medbook.android.utils.TextUtils;


public class EditNumberFieldActivity extends MedBookActivity {

    public final static String KEY_FIELD_SAVE = "KEY_FIELD_SAVE";
    public static final String KEY_TITLE = "KEY_TITLE";
    public static final String KEY_TEXT = "KEY_TEXT";
    public static final String KEY_VALUE = "KEY_VALUE";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_number_field);

        ImageView imvClose = findViewById(R.id.activity_edit_number_field_toolbar_close);
        imvClose.setOnClickListener(view -> finish());

        int value = -1;
        String title = null;
        String text = null;
        if (getIntent() == null) {
            finish();
        } else {
            value = getIntent().getIntExtra(KEY_VALUE, -1);
            title = getIntent().getStringExtra(KEY_TITLE);
            text = getIntent().getStringExtra(KEY_TEXT);
        }
        TextView tvTitle = findViewById(R.id.activity_edit_number_field_toolbar_title);
        tvTitle.setText(TextUtils.getString(title));

        TextInputLayout inputLayot = findViewById(R.id.activity_edit_number_field_input_layout);
        inputLayot.setHint(text);
        AppCompatEditText input = findViewById(R.id.activity_edit_number_field_input);
        if (value > 0) {
            input.setText(String.valueOf(value));
            input.setSelection(input.getText().length());
        }
        Button btnSave = findViewById(R.id.activity_edit_number_field_save);
        btnSave.setOnClickListener(view -> {
            Intent resultIntent = new Intent();
            if (input.getText() == null || input.getText().toString().isEmpty())
                resultIntent.putExtra(KEY_FIELD_SAVE, 0);
            else
                resultIntent.putExtra(KEY_FIELD_SAVE, Integer.parseInt(input.getText().toString()));
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }

    @Override
    protected void onLocalizationUpdate() {

    }
}
