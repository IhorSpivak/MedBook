package mobi.medbook.android.ui.points;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import com.google.android.material.textfield.TextInputLayout;

import mobi.medbook.android.App;
import mobi.medbook.android.Core;
import mobi.medbook.android.R;
import mobi.medbook.android.events.core.Event;
import mobi.medbook.android.events.points.EventAddCardFishka;
import mobi.medbook.android.ui.custom_views.MedBookActivity;
import mobi.medbook.android.utils.AppUtils;

public class AddFishkaCardActivity extends MedBookActivity {

    private TextView mTvTitle;
    private AppCompatEditText mInputCard;
    private TextInputLayout mInputLayoutCard;
    private AppCompatEditText mInputPhone;
    private TextInputLayout mInputLayoutPhone;
    private Button mBtnSave;
    private ProgressBar pb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fishka);
        mTvTitle = findViewById(R.id.activity_fishka_card_add_toolbar_title);
        mInputCard = findViewById(R.id.activity_fishka_card_add_input_card);
        mInputLayoutCard = findViewById(R.id.activity_fishka_card_add_input_layout_card);
        mInputPhone = findViewById(R.id.activity_fishka_card_add_input_phone);
        mInputLayoutPhone = findViewById(R.id.activity_fishka_card_add_input_layout_phone);
        mBtnSave = findViewById(R.id.activity_fishka_card_add_btn_save);
        ImageView imClose = findViewById(R.id.activity_fishka_card_add_toolbar_close);
        pb = findViewById(R.id.pb);
        imClose.setOnClickListener((View v) -> finish());
        mBtnSave.setOnClickListener((View v) -> {
            pb.setVisibility(View.VISIBLE);
                    mBtnSave.setEnabled(false);
                    if (mInputCard.getText() == null || mInputCard.getText().toString().isEmpty()) {
                        AppUtils.toastError(Core.get().LocalizationControl().getText(R.id.activity_points_add_card_input_card_error), true);
                        mBtnSave.setEnabled(true);
                    } else {
                        if (mInputPhone.getText() == null || mInputPhone.getText().toString().isEmpty() || mInputPhone.getText().toString().length() != 4) {
                            AppUtils.toastError(Core.get().LocalizationControl().getText(R.id.activity_points_add_card_input_phone_error), true);
                            mBtnSave.setEnabled(true);
                        } else {
                            Core.get().Api2Control().addFishkaCard(App.getUser().id, mInputCard.getText().toString(), mInputPhone.getText().toString());
                        }
                    }
                }
        );
        onLocalizationUpdate();
    }

    @Override
    protected void onLocalizationUpdate() {
        mTvTitle.setText(Core.get().LocalizationControl().getText(R.id.activity_fishka_card_add_toolbar_title));
        mBtnSave.setText(Core.get().LocalizationControl().getText(R.id.activity_fishka_card_add_btn_save));
        mInputLayoutCard.setHint(Core.get().LocalizationControl().getText(R.id.activity_fishka_card_add_input_layout_card));
        mInputLayoutPhone.setHint(Core.get().LocalizationControl().getText(R.id.activity_fishka_card_add_input_layout_phone));
    }

    @Override
    public void onEvent(Event event) {
        super.onEvent(event);
        switch (event.getEventId()) {
            case Event.EVENT_LOGOUT:
                pb.setVisibility(View.GONE);
                finish();
                break;
            case Event.EVENT_ADD_FISHKA_CARD:
                mBtnSave.setEnabled(true);
                pb.setVisibility(View.GONE);
                EventAddCardFishka eventAddCardFishka = (EventAddCardFishka)event;
                if(eventAddCardFishka.isState()){
                    Core.get().PointControl().getUserFishkaCrads();
                    finish();
                    Toast.makeText(this, R.string.card_add_success, Toast.LENGTH_LONG).show();
                }
                else if(eventAddCardFishka.getMessage() != null && !eventAddCardFishka.getMessage().isEmpty()){
                    Toast.makeText(this, eventAddCardFishka.getMessage(), Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}
