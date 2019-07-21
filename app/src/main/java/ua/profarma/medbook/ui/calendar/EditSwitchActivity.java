package ua.profarma.medbook.ui.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.SwitchCompat;

import com.google.android.material.textfield.TextInputLayout;

import ua.profarma.medbook.R;
import ua.profarma.medbook.ui.custom_views.MedBookActivity;
import ua.profarma.medbook.utils.DialogBuilder;

public class EditSwitchActivity extends MedBookActivity {

    public static final String KEY_TITLE = "KEY_TITLE";
    public static final String KEY_VALUE = "KEY_VALUE";
    public static final String KEY_NOTE = "KEY_NOTE";

    public static final String KEY_FIELD_SAVE_CHECK = "KEY_FIELD_SAVE_CHECK";
    public static final String KEY_FIELD_SAVE_NOTE = "KEY_FIELD_SAVE_NOTE";

    private SwitchCompat mSwitch;
    private TextInputLayout inputLayout;
    private AppCompatEditText input;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_switch);

        ImageView imvBack = findViewById(R.id.activity_edit_switch_toolbar_close);
        imvBack.setOnClickListener(view -> {
//            if (mSwitch.isChecked() && (input.getText() == null || input.getText().toString().isEmpty())) {
//                DialogBuilder.showInfoDialog(this, "Повідомлення", "Потрібно ввести причину неможливого продукту");
//            } else
//            Intent resultIntent = new Intent();
//            resultIntent.putExtra(KEY_FIELD_SAVE_CHECK, 0);
//            if (input.getText() == null || input.getText().toString().isEmpty()) {
//                resultIntent.putExtra(KEY_FIELD_SAVE_NOTE, input.getText().toString());
//                resultIntent.putExtra(KEY_FIELD_SAVE_CHECK, 0);
//            } else
//            if (mSwitch.isChecked()) {
//                resultIntent.putExtra(KEY_FIELD_SAVE_CHECK, 1);
//            }
//            else {
//                resultIntent.putExtra(KEY_FIELD_SAVE_CHECK, 0);
//            }
//
            Intent resultIntent = new Intent();
            setResult(RESULT_CANCELED, resultIntent);
            finish();
        });

        int value = -1;
        String note = null;
        String title = null;
        if (getIntent() == null) {
            finish();
        } else {
            note = getIntent().getStringExtra(KEY_NOTE);
            value = getIntent().getIntExtra(KEY_VALUE, -1);
            title = getIntent().getStringExtra(KEY_TITLE);
            TextView tvTitle = findViewById(R.id.activity_edit_switch_toolbar_title);
            tvTitle.setText(title);
        }

        mSwitch = findViewById(R.id.activity_edit_switch_switch);
        inputLayout = findViewById(R.id.activity_edit_switch_input_layout);
        inputLayout.setHint("Введіть причину");
        input = findViewById(R.id.activity_edit_switch_input);

        mSwitch.setChecked(value == 1);
        inputLayout.setEnabled(value == 1);
        if (note != null)
            input.setText(note);
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                inputLayout.setEnabled(isChecked);
            }
        });

        Button btnSave = findViewById(R.id.activity_edit_switch_save);
        btnSave.setOnClickListener(view -> {
            Intent resultIntent = new Intent();
            if (mSwitch.isChecked()) {
                if (input.getText() == null || input.getText().toString().isEmpty()) {
                    DialogBuilder.showInfoDialog(this, "Повідомлення", "Потрібно ввести причину неможливого продукту");
                } else {
                    resultIntent.putExtra(KEY_FIELD_SAVE_CHECK, 1);
                    resultIntent.putExtra(KEY_FIELD_SAVE_NOTE, input.getText().toString());
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
            } else {
                resultIntent.putExtra(KEY_FIELD_SAVE_CHECK, 0);
                setResult(RESULT_OK, resultIntent);
                finish();
            }

        });
    }

    @Override
    protected void onLocalizationUpdate() {

    }
}
