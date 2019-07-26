package ua.profarma.medbook.ui.reference;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.app.ActivityCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import butterknife.ButterKnife;
import ua.profarma.medbook.App;
import ua.profarma.medbook.Core;
import ua.profarma.medbook.R;
import ua.profarma.medbook.events.core.Event;
import ua.profarma.medbook.models.response.ReferenceItem;
import ua.profarma.medbook.recyclerviews.base.RecyclerItem;
import ua.profarma.medbook.recyclerviews.base.RecyclerItems;
import ua.profarma.medbook.recyclerviews.drug_selected.DrugSelectedRecyclerItem;
import ua.profarma.medbook.recyclerviews.drug_selected.DrugsSelectedRecyclerView;
import ua.profarma.medbook.recyclerviews.icod_selected.IcodSelectedRecyclerItem;
import ua.profarma.medbook.recyclerviews.icod_selected.IcodSelectedRecyclerView;
import ua.profarma.medbook.recyclerviews.image_cc_selected.ImageCCSelectedRecyclerItem;
import ua.profarma.medbook.types.news.CCImage;
import ua.profarma.medbook.types.news.DrugSelected;
import ua.profarma.medbook.types.news.IcodSelected;
import ua.profarma.medbook.types.news.IdCCDrug;
import ua.profarma.medbook.types.news.IdCCIcod;
import ua.profarma.medbook.types.news.MedicalCaseItem;
import ua.profarma.medbook.types.requests.RequestMedicalCaseBody;
import ua.profarma.medbook.ui.custom_views.MedBookActivity;
import ua.profarma.medbook.ui.news_and_clinical_cases.DrugSelectActivity;
import ua.profarma.medbook.ui.news_and_clinical_cases.IcodSelectActivity;
import ua.profarma.medbook.utils.DialogBuilder;
import ua.profarma.medbook.utils.LogUtils;

public class ReferenceReviewActivity extends AppCompatActivity {


    private IcodSelectedRecyclerViewed listIcod;

    private DrugsSelectedRecyclerViewed listDrug;

    private RecyclerItems itemsIcod;
    private RecyclerItems itemsDrug;

    private Button btn_send;
    private ImageView close;
    private TextView edDetails;
    private TextView edTitle;
    private TextView ed_phone;
    private TextView ed_mail;

    private ProgressBar pbAddImageLoad;

    private Switch aSwitch;

    private ReferenceItem itemReference;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reference_review);
        ButterKnife.bind(this);
        aSwitch = findViewById(R.id.sw);
        edTitle = findViewById(R.id.tv_name);
        btn_send = findViewById(R.id.btn_send);
        edDetails = findViewById(R.id.tv_description);
        close = findViewById(R.id.close);
        listIcod = findViewById(R.id.activity_add_clinical_cases_icod_list);
        ed_mail = findViewById(R.id.tv_mail);
        listIcod.init();
        itemsIcod = new RecyclerItems();
        listDrug = findViewById(R.id.activity_add_clinical_cases_drug_list);
        ed_phone = findViewById(R.id.tv_phone);
        btn_send = findViewById(R.id.btn_check);
        listDrug.init();
        itemsDrug = new RecyclerItems();




        close.setOnClickListener(view -> onBackPressed());
        btn_send.setOnClickListener(view -> showWebView());

        Gson gson = new Gson();
        itemReference = gson.fromJson(getIntent().getStringExtra("myjson"), ReferenceItem.class);
        if(itemReference != null) {
            setDataReference(itemReference);
        }
    }

    private void showWebView() {
        ReferenceWebViewActivity.startActivity(this, itemReference.getId());
    }


    private void setDataReference(ReferenceItem item) {
        edTitle.setText(item.getTitle());
        edDetails.setText(item.getDescription());
        if (item.getDrugs() != null && item.getDrugs().size() > 0) {
            listDrug.init();
            itemsDrug = new RecyclerItems();
            for (int i = 0; i < item.getDrugs().size() ; i++)
                itemsDrug.add(new DrugSelectedRecyclerItem(new DrugSelected(item.getDrugs().get(i).getId(), item.getDrugs().get(i).getDrug().getTitle(), false)));
            listDrug.itemsAdd(itemsDrug);
        }
        if (item.getIcods() != null && item.getIcods().size() > 0) {
            listIcod.init();
            itemsIcod = new RecyclerItems();
            for (int i = 0; i < item.getIcods().size(); i++) {
                int selectLang = -1;
                for (int j = 0; j < item.getIcods().get(i).getIcod().getTranslations().size(); j++) {
                    if ( item.getIcods().get(i).getIcod().getTranslations().get(j).getLanguage().substring(0, 2).equals(App.getLanguage())) {
                        selectLang = j;
                    }
                }
                if (selectLang == -1) {
                    for (int j = 0; j < item.getIcods().get(i).getIcod().getTranslations().size(); j++) {
                        if (item.getIcods().get(i).getIcod().getTranslations().get(j).getLanguage().substring(0, 2).equals("uk")) {
                            selectLang = j;
                        }
                    }
                }
                itemsIcod.add(new IcodSelectedRecyclerItem(new IcodSelected(item.getIcods().get(i).getId(),
                        item.getIcods().get(i).getIcod().getId().toString(),
                        item.getIcods().get(i).getIcod().getTranslations().get(selectLang).getTitle(), false)));
            }
            listIcod.itemsAdd(itemsIcod);
        }

        aSwitch.setChecked(item.getTemplate().equals(0));
        aSwitch.setClickable(false);
    }




}
