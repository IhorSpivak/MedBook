package mobi.medbook.android.ui.calendar;

import android.app.Activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.SwitchCompat;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.android.material.textfield.TextInputLayout;

import mobi.medbook.android.Core;
import mobi.medbook.android.R;
import mobi.medbook.android.types.visits.Product;
import mobi.medbook.android.ui.custom_views.MedBookActivity;
import mobi.medbook.android.utils.TextUtils;


public class ProductActivity extends MedBookActivity {

    public static final String KEY_PRODUCT_ID = "KEY_PRODUCT_ID";

    public static final int REQUEST_CODE_LAST_PLAN_REC = 1111;
    public static final int REQUEST_CODE_FACT_REC = 2222;
    public static final int REQUEST_CODE_NEW_PLAN_REC = 3333;
    public static final int REQUEST_CODE_IMPOSSIBLE = 4444;

    private Product product;

    private RelativeLayout llLastPlanRec;
    private RelativeLayout llFactRec;
    private RelativeLayout llNewPlanRec;
    private RelativeLayout llImpossible;
    private RelativeLayout rl_product_note;
    private TextInputLayout ed_ll_reason;
    private AppCompatEditText ed_reason;
    private ImageView iv_info_1;
    private ImageView iv_info_2;
    private ImageView iv_info_3;


    private TextView tvLastPlanRecValue;
    private EditText tvFactRecValue;
    private EditText tvNewPlanRecValue;
    private SwitchCompat swImpossible;

    private ExpandableRelativeLayout expandableLayout;
    private ExpandableRelativeLayout expandableLayout1;
    private ExpandableRelativeLayout expandableLayout2;




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

        expandableLayout = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout);
        expandableLayout2 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout2);
        iv_info_1 = (ImageView) findViewById(R.id.iv_info_1);
        iv_info_2 = (ImageView) findViewById(R.id.iv_info_2);
        iv_info_3 = (ImageView) findViewById(R.id.iv_info_3);


        iv_info_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableButton1(v);
            }
        });

        iv_info_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableButton2(v);
            }
        });

        iv_info_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableButton3(v);
            }
        });

        if (product == null)
            finish();



        ImageView imvClose = findViewById(R.id.activity_product_toolbar_close);
        imvClose.setOnClickListener(view -> onFinish());
        TextView tvTitle = findViewById(R.id.activity_product_toolbar_title);
        tvTitle.setText(TextUtils.getString(product.productName));

        llLastPlanRec = findViewById(R.id.activity_product_block_last_plan_rec);
        ed_ll_reason = findViewById(R.id.ed_ll_reason);
        ed_reason = findViewById(R.id.ed_reason);
        iv_info_2 = findViewById(R.id.iv_info_2);
        expandableLayout1 = findViewById(R.id.expandableLayout1);

        llFactRec = findViewById(R.id.activity_product_block_fact_rec);
        llNewPlanRec = findViewById(R.id.activity_product_block_new_plan_rec);
        llImpossible = findViewById(R.id.activity_product_block_impossible);
        rl_product_note = findViewById(R.id.rl_product_note);

        tvLastPlanRecValue = findViewById(R.id.activity_product_block_last_plan_rec_value);
        tvFactRecValue = findViewById(R.id.activity_product_block_fact_rec_value);
        tvNewPlanRecValue = findViewById(R.id.activity_product_block_new_plan_rec_value);
        swImpossible = findViewById(R.id.activity_product_impossible_switch);
        if(product.impossible == 1){
            llLastPlanRec.setVisibility(View.GONE);
            llNewPlanRec.setVisibility(View.GONE);
            llFactRec.setVisibility(View.GONE);
            rl_product_note.setVisibility(View.VISIBLE);
            swImpossible.setChecked(true);
            ed_reason.setText(product.note);
        }




        tvFactRecValue.setBackgroundResource(android.R.color.transparent);
        llFactRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showKeyBoard1();

            }
        });



        tvFactRecValue.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    Toast.makeText(ProductActivity.this, "Збережено", Toast.LENGTH_SHORT).show();
                    tvFactRecValue.clearFocus();
                    setFactValue(Integer.valueOf(tvFactRecValue.getText().toString()));
                    return true;
                }
                return false;
            }
        });

        tvNewPlanRecValue.setBackgroundResource(android.R.color.transparent);
        llNewPlanRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showKeyBoard2();
            }
        });

        tvNewPlanRecValue.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    Toast.makeText(ProductActivity.this, "Збережено", Toast.LENGTH_SHORT).show();
                    tvNewPlanRecValue.clearFocus();
                    setNewValue(Integer.valueOf(tvNewPlanRecValue.getText().toString()));
                    return true;
                }
                return false;
            }
        });

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

//        llImpossible.setOnClickListener(view -> {
//                    swImpossible.setChecked(!swImpossible.isChecked());
//                    product.impossible = (swImpossible.isChecked() ? 1 : 0);
//                    if (product.impossible == 1) {
//                        Intent intent = new Intent(ProductActivity.this, EditSwitchActivity.class);
//                        intent.putExtra(EditSwitchActivity.KEY_VALUE, product.impossible);
//                        intent.putExtra(EditSwitchActivity.KEY_TITLE, product.productName);
//                        intent.putExtra(EditSwitchActivity.KEY_NOTE, product.note);
//                        startActivityForResult(intent, REQUEST_CODE_IMPOSSIBLE);
//                    }
//                }
//        );

        swImpossible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    llLastPlanRec.setVisibility(View.GONE);
                    llNewPlanRec.setVisibility(View.GONE);
                    llFactRec.setVisibility(View.GONE);
                    rl_product_note.setVisibility(View.VISIBLE);
                    product.impossible = 1;
                    product.note = ed_reason.getText().toString();
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

                }else {
                    llLastPlanRec.setVisibility(View.VISIBLE);
                    llNewPlanRec.setVisibility(View.VISIBLE);
                    llFactRec.setVisibility(View.VISIBLE);
                    rl_product_note.setVisibility(View.GONE);
                    product.note = "";
                    product.note = ed_reason.getText().toString();
                    product.impossible = 0;
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
                }
            }
        });

        Button btnSave = findViewById(R.id.activity_product_save);
        btnSave.setOnClickListener(view -> {
            product.note = ed_reason.getText().toString();
            product.newPlanRec = Integer.parseInt(tvNewPlanRecValue.getText().toString());
            product.factRec = Integer.parseInt(tvFactRecValue.getText().toString());
            Core.get().VisitsControl().setProductResultMP(product);
            finish();
        });
    }

    private void showKeyBoard2() {
        tvNewPlanRecValue.requestFocus();
        InputMethodManager imm = (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    private void showKeyBoard1() {
        tvFactRecValue.requestFocus();
        InputMethodManager imm = (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    private void expandableButton3(View v) {
        expandableLayout2.toggle();
    }

    private void expandableButton2(View v) {
        expandableLayout1.toggle(); //
    }

    public void expandableButton1(View view) {

        expandableLayout.toggle(); // toggle expand and collapse
    }





    private void setNewValue(int value) {
        tvNewPlanRecValue.setText(String.valueOf(value));
        product.newPlanRec = value;
    }

    private void setFactValue(int a) {
        tvFactRecValue.setText(String.valueOf(a));
        product.factRec = a;
    }




    private void onFinish() {
        Core.get().VisitsControl().setProductResultMP(product);
        finish();

    }

    @Override
    protected void onLocalizationUpdate() {

    }
}
