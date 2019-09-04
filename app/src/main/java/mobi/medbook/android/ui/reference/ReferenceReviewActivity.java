package mobi.medbook.android.ui.reference;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.gson.Gson;

import butterknife.ButterKnife;
import mobi.medbook.android.App;
import mobi.medbook.android.R;
import mobi.medbook.android.models.response.ReferenceItem;
import mobi.medbook.android.recyclerviews.base.RecyclerItems;
import mobi.medbook.android.recyclerviews.drug_selected.DrugSelectedRecyclerItem;
import mobi.medbook.android.recyclerviews.icod_selected.IcodSelectedRecyclerItem;
import mobi.medbook.android.types.news.DrugSelected;
import mobi.medbook.android.types.news.IcodSelected;
import mobi.medbook.android.ui.custom_views.MedBookActivity;


public class ReferenceReviewActivity extends MedBookActivity {


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

    @Override
    protected void onLocalizationUpdate() {

    }

    private void showWebView() {
       ReferenceWebViewActivity.startActivity(this, itemReference.getId());
    }


    private void setDataReference(ReferenceItem item) {
        ed_phone.setText(item.getPatients().get(0).getPhone());
        edTitle.setText(item.getTitle());
        edDetails.setText(item.getDescription());
        if (item.getDrugs() != null && item.getDrugs().size() > 0) {
            listDrug.init();
            itemsDrug = new RecyclerItems();
            for (int i = 0; i < item.getDrugs().size() ; i++)
                itemsDrug.add(new DrugSelectedRecyclerItem(new DrugSelected(item.getDrugs().get(i).getAuthor_id(), item.getDrugs().get(i).getDrug().getTitle(), true,item.getDrugs().get(i).getQty())));
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
                        item.getIcods().get(i).getIcod().getCode_icod().toString(),
                        item.getIcods().get(i).getIcod().getTranslations().get(selectLang).getTitle(), true)));
            }
            listIcod.itemsAdd(itemsIcod);
        }

        aSwitch.setChecked(item.getTemplate().equals(0));
        aSwitch.setClickable(false);
    }




}
