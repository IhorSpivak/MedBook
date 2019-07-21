package ua.profarma.medbook.ui.points;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import com.google.android.material.textfield.TextInputLayout;

import ua.profarma.medbook.App;
import ua.profarma.medbook.Core;
import ua.profarma.medbook.R;
import ua.profarma.medbook.events.core.Event;
import ua.profarma.medbook.events.points.EventAddCardFishka;
import ua.profarma.medbook.ui.custom_views.MedBookActivity;
import ua.profarma.medbook.utils.AppUtils;

public class AddFishkaCardActivity extends MedBookActivity {

    private TextView mTvTitle;
    private AppCompatEditText mInputCard;
    private TextInputLayout mInputLayoutCard;
    private AppCompatEditText mInputPhone;
    private TextInputLayout mInputLayoutPhone;
    private Button mBtnSave;

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
        imClose.setOnClickListener((View v) -> finish());
        mBtnSave.setOnClickListener((View v) -> {
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
                finish();
                break;
            case Event.EVENT_ADD_FISHKA_CARD:
                mBtnSave.setEnabled(true);
                EventAddCardFishka eventAddCardFishka = (EventAddCardFishka)event;
                if(eventAddCardFishka.isState()){
                    Core.get().PointControl().getUserFishkaCrads();
                    finish();
                    AppUtils.toastOk(Core.get().LocalizationControl().getText(R.id.card_add_success), true);
                }
                else if(eventAddCardFishka.getMessage() != null && !eventAddCardFishka.getMessage().isEmpty()){
                    AppUtils.toastError(eventAddCardFishka.getMessage(), false);
                }
                break;
        }
    }
}
