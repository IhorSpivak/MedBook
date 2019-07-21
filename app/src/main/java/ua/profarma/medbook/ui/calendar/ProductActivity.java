package ua.profarma.medbook.ui.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;

import ua.profarma.medbook.Core;
import ua.profarma.medbook.R;
import ua.profarma.medbook.types.visits.Product;
import ua.profarma.medbook.ui.custom_views.MedBookActivity;
import ua.profarma.medbook.utils.LogUtils;
import ua.profarma.medbook.utils.TextUtils;

public class ProductActivity extends MedBookActivity {

    public static final String KEY_PRODUCT_ID = "KEY_PRODUCT_ID";

    public static final int REQUEST_CODE_LAST_PLAN_REC = 1111;
    public static final int REQUEST_CODE_FACT_REC = 2222;
    public static final int REQUEST_CODE_NEW_PLAN_REC = 3333;
    public static final int REQUEST_CODE_IMPOSSIBLE = 4444;

    private Product product;

    private LinearLayout llLastPlanRec;
    private LinearLayout llFactRec;
    private LinearLayout llNewPlanRec;
    private LinearLayout llImpossible;

    private TextView tvLastPlanRecValue;
    private TextView tvFactRecValue;
    private TextView tvNewPlanRecValue;
    private SwitchCompat swImpossible;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        if (getIntent() != null) {
            String idProduct = getIntent().getStringExtra(KEY_PRODUCT_ID);
            if (idProduct == null || idProduct.isEmpty())
                finish();
            else {
                if (Core.get().VisitsControl().getUserVisitQuestionnaireMP() != null &&
                        Core.get().VisitsControl().getUserVisitQuestionnaireMP().data != null &&
                        Core.get().VisitsControl().getUserVisitQuestionnaireMP().data.productsArr != null) {
                    for (int i = 0; i < Core.get().VisitsControl().getUserVisitQuestionnaireMP().data.productsArr.length; i++)
                        if (Core.get().VisitsControl().getUserVisitQuestionnaireMP().data.productsArr[i].productId.equals(idProduct))
                            product = Core.get().VisitsControl().getUserVisitQuestionnaireMP().data.productsArr[i];
                }
            }
        } else finish();

        if (product == null)
            finish();

        ImageView imvClose = findViewById(R.id.activity_product_toolbar_close);
        imvClose.setOnClickListener(view -> finish());
        TextView tvTitle = findViewById(R.id.activity_product_toolbar_title);
        tvTitle.setText(TextUtils.getString(product.productName));

        llLastPlanRec = findViewById(R.id.activity_product_block_last_plan_rec);
        llFactRec = findViewById(R.id.activity_product_block_fact_rec);
        llNewPlanRec = findViewById(R.id.activity_product_block_new_plan_rec);
        llImpossible = findViewById(R.id.activity_product_block_impossible);

        tvLastPlanRecValue = findViewById(R.id.activity_product_block_last_plan_rec_value);
        tvFactRecValue = findViewById(R.id.activity_product_block_fact_rec_value);
        tvNewPlanRecValue = findViewById(R.id.activity_product_block_new_plan_rec_value);
        swImpossible = findViewById(R.id.activity_product_impossible_switch);

        switch (product.productType) {
            case 1:
                llLastPlanRec.setVisibility(View.GONE);
                llFactRec.setVisibility(View.GONE);
                tvNewPlanRecValue.setText(String.valueOf(product.newPlanRec));
                break;
            case 2:
                llNewPlanRec.setVisibility(View.GONE);
                tvLastPlanRecValue.setText(String.valueOf(product.lastPlanRec));
                tvFactRecValue.setText(String.valueOf(product.factRec));
                break;
            case 3:
                tvLastPlanRecValue.setText(String.valueOf(product.lastPlanRec));
                tvFactRecValue.setText(String.valueOf(product.factRec));
                tvNewPlanRecValue.setText(String.valueOf(product.newPlanRec));
                break;
        }

        llFactRec.setOnClickListener(view -> {
            if (product.impossible == 0) startActivityEdit(product.factRec,
                    REQUEST_CODE_FACT_REC, "Фактична кількість паціентів");
        });
//        llLastPlanRec.setOnClickListener(view -> startActivityEdit(product.lastPlanRec,
//                REQUEST_CODE_LAST_PLAN_REC, "Попередній прогноз кількості паціентів"));
        llNewPlanRec.setOnClickListener(view -> {
            if (product.impossible == 0) startActivityEdit(product.newPlanRec,
                    REQUEST_CODE_NEW_PLAN_REC, "Новий прогноз кількісті паціентів");
        });

        llImpossible.setOnClickListener(view -> {
                    swImpossible.setChecked(!swImpossible.isChecked());
                    product.impossible = (swImpossible.isChecked() ? 1 : 0);
                    if (product.impossible == 1) {
                        Intent intent = new Intent(ProductActivity.this, EditSwitchActivity.class);
                        intent.putExtra(EditSwitchActivity.KEY_VALUE, product.impossible);
                        intent.putExtra(EditSwitchActivity.KEY_TITLE, product.productName);
                        intent.putExtra(EditSwitchActivity.KEY_NOTE, product.note);
                        startActivityForResult(intent, REQUEST_CODE_IMPOSSIBLE);
                    }
                }
        );

        Button btnSave = findViewById(R.id.activity_product_save);
        btnSave.setOnClickListener(view -> {
            Core.get().VisitsControl().setProductResultMP(product);
            finish();
        });
        swImpossible.setChecked(product.impossible == 1);

        swImpossible.setOnClickListener(view -> {
            swImpossible.setChecked(!swImpossible.isChecked());
            product.impossible = (swImpossible.isChecked() ? 1 : 0);
            Intent intent = new Intent(ProductActivity.this, EditSwitchActivity.class);
            intent.putExtra(EditSwitchActivity.KEY_VALUE, product.impossible);
            intent.putExtra(EditSwitchActivity.KEY_TITLE, product.productName);
            intent.putExtra(EditSwitchActivity.KEY_NOTE, product.note);
            startActivityForResult(intent, REQUEST_CODE_IMPOSSIBLE);
        });
        swImpossible.setClickable(false);
        onLocalizationUpdate();
    }

    private void startActivityEdit(int value, int request_code, String text) {
        Intent intent = new Intent(ProductActivity.this, EditNumberFieldActivity.class);
        intent.putExtra(EditNumberFieldActivity.KEY_VALUE, value);
        intent.putExtra(EditNumberFieldActivity.KEY_TITLE, product.productName);
        intent.putExtra(EditNumberFieldActivity.KEY_TEXT, text);
        startActivityForResult(intent, request_code);
    }

    @Override
    protected void onLocalizationUpdate() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data != null) {
                switch (requestCode) {
                    case REQUEST_CODE_FACT_REC:
                        LogUtils.logD("ProductActivity", "product.factRec = " + product.factRec);
                        product.factRec = data.getIntExtra(EditNumberFieldActivity.KEY_FIELD_SAVE, 0);
                        LogUtils.logD("ProductActivity", "product.factRec = " + product.factRec);
                        tvFactRecValue.setText(String.valueOf(product.factRec));
                        break;
                    case REQUEST_CODE_LAST_PLAN_REC:
                        LogUtils.logD("ProductActivity", "product.lastPlanRec = " + product.lastPlanRec);
                        product.lastPlanRec = data.getIntExtra(EditNumberFieldActivity.KEY_FIELD_SAVE, 0);
                        LogUtils.logD("ProductActivity", "product.lastPlanRec = " + product.lastPlanRec);
                        tvLastPlanRecValue.setText(String.valueOf(product.lastPlanRec));
                        break;
                    case REQUEST_CODE_NEW_PLAN_REC:
                        LogUtils.logD("ProductActivity", "product.newPlanRec = " + product.newPlanRec);
                        product.newPlanRec = data.getIntExtra(EditNumberFieldActivity.KEY_FIELD_SAVE, 0);
                        LogUtils.logD("ProductActivity", "product.newPlanRec = " + product.newPlanRec);
                        tvNewPlanRecValue.setText(String.valueOf(product.newPlanRec));
                        break;
                    case REQUEST_CODE_IMPOSSIBLE:
                        //swImpossible.setOnCheckedChangeListener(null);
                        product.impossible = data.getIntExtra(EditSwitchActivity.KEY_FIELD_SAVE_CHECK, 0);
                        product.note = data.getStringExtra(EditSwitchActivity.KEY_FIELD_SAVE_NOTE);
                        swImpossible.setChecked(product.impossible == 1);
                        //swImpossible.setOnCheckedChangeListener(onCheckedChangeListener);
                        break;
                }
            }
        }
        if (resultCode == RESULT_CANCELED && requestCode == REQUEST_CODE_IMPOSSIBLE) {
            //swImpossible.setOnCheckedChangeListener(null);
            if (product.note == null || product.note.isEmpty())
                product.impossible = 0;
            swImpossible.setChecked(product.impossible == 1);
            //swImpossible.setOnCheckedChangeListener(onCheckedChangeListener);

        }
    }

    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked)
                product.impossible = 1;
            else
                product.impossible = 0;

            Intent intent = new Intent(ProductActivity.this, EditSwitchActivity.class);
            intent.putExtra(EditSwitchActivity.KEY_VALUE, product.impossible);
            intent.putExtra(EditSwitchActivity.KEY_TITLE, product.productName);
            intent.putExtra(EditSwitchActivity.KEY_NOTE, product.note);
            startActivityForResult(intent, REQUEST_CODE_IMPOSSIBLE);
        }
    };
}
