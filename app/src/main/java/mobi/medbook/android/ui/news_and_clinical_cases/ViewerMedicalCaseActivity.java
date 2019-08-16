package mobi.medbook.android.ui.news_and_clinical_cases;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import mobi.medbook.android.App;
import mobi.medbook.android.Core;
import mobi.medbook.android.R;
import mobi.medbook.android.events.core.Event;
import mobi.medbook.android.events.news.EventClinicalCaseLoad;
import mobi.medbook.android.recyclerviews.base.RecyclerItems;
import mobi.medbook.android.recyclerviews.drug_selected.DrugSelectedRecyclerItem;
import mobi.medbook.android.recyclerviews.drug_selected.DrugsSelectedRecyclerView;
import mobi.medbook.android.recyclerviews.icod_selected.IcodSelectedRecyclerItem;
import mobi.medbook.android.recyclerviews.icod_selected.IcodSelectedRecyclerView;
import mobi.medbook.android.recyclerviews.image_cc_selected.ImageCCSelectedRecyclerItem;
import mobi.medbook.android.recyclerviews.image_cc_selected.ImageCCSelectedRecyclerView;
import mobi.medbook.android.types.news.CCImage;
import mobi.medbook.android.types.news.DrugSelected;
import mobi.medbook.android.types.news.IcodSelected;
import mobi.medbook.android.types.news.MedicalCaseItem;
import mobi.medbook.android.ui.custom_views.MedBookActivity;

public class ViewerMedicalCaseActivity extends MedBookActivity implements IOnSelectImageCC {

    public static final String KEY_ID_CC = "KEY_ID_CC";
    public static final String KEY_MY_CC = "KEY_MY_CC";
    public static final String KEY_NA_ID_CC = "KEY_NA_ID_CC";

    private int idCC;
    private int news_article_id;
    private boolean myClinicalCase;
    private MedicalCaseItem medicalCaseItem;

    private RequestManager rb;

    private TextView tvTitle;
    private TextView tvTitleIcods;
    private TextView tvTitleDrugs;
    private TextView tvDetails;
    private TextView tvTitleDetails;
    private TextView tv_date;
    private TextView tv_title;
    private View view1;
    private IcodSelectedRecyclerView icodList;
    private DrugsSelectedRecyclerView drugList;
    private ImageCCSelectedRecyclerView listImages;
    private RecyclerItems itemsIcod;
    private RecyclerItems itemsDrug;
    private RecyclerItems itemsImages;

    private Button btnDelete;
    private RelativeLayout rlBlockLikeComments;
    private ImageView imvLike;
    private TextView tvCountLike;
    private ImageView imvComments;
    private ImageView iv_main;
    private TextView tvCountComments;
    private boolean stateLike = false;
    private int countLike = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinical_case_viewer);

        btnDelete = findViewById(R.id.activity_clinical_case_viewer_btn_delete);
        rlBlockLikeComments = findViewById(R.id.activity_clinical_case_viewer_block_like_comments);
        imvLike = findViewById(R.id.activity_clinical_case_viewer_like);
        tvCountLike = findViewById(R.id.activity_clinical_case_viewer_like_count);
        imvComments = findViewById(R.id.activity_clinical_case_viewer_comments_comments);
        tvCountComments = findViewById(R.id.activity_clinical_case_viewer_comments_count);
        iv_main = findViewById(R.id.iv_main);
        tv_date = findViewById(R.id.tv_date);
        tv_title = findViewById(R.id.tv_title);
        view1 = findViewById(R.id.view1);




        tvTitle = findViewById(R.id.activity_clinical_case_viewer_toolbar_title);
        tvTitleIcods = findViewById(R.id.activity_clinical_case_viewer_add_icod_caption);
        tvTitleDrugs = findViewById(R.id.activity_clinical_case_viewer_add_drug_caption);
        tvDetails = findViewById(R.id.activity_clinical_case_viewer_details);
        tvTitleDetails = findViewById(R.id.activity_clinical_case_viewer_details_caption);
        icodList = findViewById(R.id.activity_clinical_case_viewer_icod_list);
        drugList = findViewById(R.id.activity_clinical_case_viewer_drug_list);
        listImages = findViewById(R.id.activity_clinical_case_viewer_image_list);
        initCC();

        ImageView imvClose = findViewById(R.id.activity_clinical_case_viewer_toolbar_close);
        imvClose.setOnClickListener(view -> finish());

        onLocalizationUpdate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!myClinicalCase)
            Core.get().NewsControl().getMedicalCase(idCC);

    }

    private void initCC() {
        idCC = -1;
        myClinicalCase = false;
        if (getIntent() == null) {
            finish();
        }
        idCC = getIntent().getIntExtra(KEY_ID_CC, -1);
        news_article_id = getIntent().getIntExtra(KEY_NA_ID_CC, -1);
        myClinicalCase = getIntent().getBooleanExtra(KEY_MY_CC, false);
        if (idCC == -1 & news_article_id == -1) {
            finish();
        }

        if (myClinicalCase) {
            btnDelete.setVisibility(View.VISIBLE);
            btnDelete.setOnClickListener(view -> {
                Core.get().NewsControl().deleteCC(idCC);
                finish();
            });
            medicalCaseItem = null;


            for (MedicalCaseItem item : Core.get().NewsControl().getClinicalCaseList()) {
                if (item.id == idCC)
                    medicalCaseItem = item;
            }
            if (medicalCaseItem == null) {
                finish();
            }
            setDataCC();
        }
    }

    private void setDataCC() {

        Date date = new Date(medicalCaseItem.created_at * 1000L);
        //DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        DateFormat format = new SimpleDateFormat("dd MMM yyyy");
        String formatted = format.format(date);
        tv_date.setText(formatted);
        tv_title.setText(medicalCaseItem.title);


        if (medicalCaseItem.news_clinical_case_status_id == 4 && medicalCaseItem.newsArticles != null &&
                medicalCaseItem.newsArticles.length > 0) {
            rlBlockLikeComments.setVisibility(View.VISIBLE);

            if (medicalCaseItem.newsArticles[0].liked == 1) {
                stateLike = true;
                imvLike.setImageResource(R.drawable.ic_like_liked);
            }
            countLike = medicalCaseItem.newsArticles[0].like;
            if (countLike > 0)
                tvCountLike.setText(String.valueOf(countLike));

            imvComments.setImageResource(medicalCaseItem.newsArticles[0].comments_count > 0 ? R.drawable.ic_comments_exist : R.drawable.ic_comment);
            imvComments.setOnClickListener(view -> {
                Intent intent = new Intent(this, CommentsActivity.class);
                intent.putExtra(CommentsActivity.KEY_ID, news_article_id);
                intent.putExtra(CommentsActivity.KEY_TITLE, medicalCaseItem.title);
                startActivity(intent);
            });
            tvCountComments.setText(String.valueOf(medicalCaseItem.newsArticles[0].comments_count));

            imvLike.setOnClickListener(view -> Core.get().NewsControl().like_unlike(news_article_id));
        }

        tvTitle.setText(medicalCaseItem.title);
        tvDetails.setText(medicalCaseItem.description);
        if (medicalCaseItem.newsClinicalCaseDrugs != null && medicalCaseItem.newsClinicalCaseDrugs.length > 0) {
            drugList.init();
            itemsDrug = new RecyclerItems();
            for (int i = 0; i < medicalCaseItem.newsClinicalCaseDrugs.length; i++)
                itemsDrug.add(new DrugSelectedRecyclerItem(new DrugSelected(1, medicalCaseItem.newsClinicalCaseDrugs[i].drug.title, true)));
            drugList.itemsAdd(itemsDrug);
        }
        if (medicalCaseItem.newsClinicalCaseIcods != null && medicalCaseItem.newsClinicalCaseIcods.length > 0) {
            icodList.init();
            itemsIcod = new RecyclerItems();
            for (int i = 0; i < medicalCaseItem.newsClinicalCaseIcods.length; i++) {
                int selectLang = -1;
                for (int j = 0; j < medicalCaseItem.newsClinicalCaseIcods[i].icod.translations.length; j++) {
                    if (medicalCaseItem.newsClinicalCaseIcods[i].icod.translations[j].language.substring(0, 2).equals(App.getLanguage())) {
                        selectLang = j;
                    }
                }
                if (selectLang == -1) {
                    for (int j = 0; j < medicalCaseItem.newsClinicalCaseIcods[i].icod.translations.length; j++) {
                        if (medicalCaseItem.newsClinicalCaseIcods[i].icod.translations[j].language.substring(0, 2).equals("uk")) {
                            selectLang = j;
                        }
                    }
                }
                itemsIcod.add(new IcodSelectedRecyclerItem(new IcodSelected(medicalCaseItem.newsClinicalCaseIcods[i].icod_id,
                        medicalCaseItem.newsClinicalCaseIcods[i].icod.code_icod,
                        medicalCaseItem.newsClinicalCaseIcods[i].icod.translations[selectLang].title, true)));
            }
            icodList.itemsAdd(itemsIcod);
        }
        if (medicalCaseItem.newsClinicalCaseImages != null && medicalCaseItem.newsClinicalCaseImages.length > 0) {
            rb = Glide.with(this);
            rb.load(medicalCaseItem.newsClinicalCaseImages[0].image).into(iv_main);

            listImages.init();
            itemsImages = new RecyclerItems();
            for (int i = 0; i < medicalCaseItem.newsClinicalCaseImages.length; i++)
                itemsImages.add(new ImageCCSelectedRecyclerItem(new CCImage(medicalCaseItem.newsClinicalCaseImages[i].image, medicalCaseItem.newsClinicalCaseImages[i].comment, true)));
            listImages.itemsAdd(itemsImages);
        }
        else {
            RelativeLayout layoutImages = findViewById(R.id.activity_clinical_case_viewer_image_list_layout);
            layoutImages.setVisibility(View.GONE);
            view1.setVisibility(View.GONE);
            iv_main.setVisibility(View.GONE);
        }
    }

    @Override
    public void onEvent(Event event) {
        super.onEvent(event);
        switch (event.getEventId()) {
            case Event.EVENT_LOAD_CLINIC_CASE:
                medicalCaseItem = ((EventClinicalCaseLoad) event).getItem();
                setDataCC();
                break;
            case Event.EVENT_LIKE_UNLIKE:
                stateLike = !stateLike;
                imvLike.setImageResource(stateLike ? R.drawable.ic_like_liked : R.drawable.ic_like_unliked);
                if (stateLike) countLike = countLike + 1;
                else countLike = countLike - 1;
                if (countLike > 0)
                    tvCountLike.setText(String.valueOf(countLike));
                break;
        }
    }

    @Override
    protected void onLocalizationUpdate() {
        tvTitleIcods.setText(Core.get().LocalizationControl().getText(R.id.activity_add_clinical_cases_add_icod_caption));
        tvTitleDrugs.setText(Core.get().LocalizationControl().getText(R.id.activity_add_clinical_cases_add_drug_caption));
        tvTitleDetails.setText(Core.get().LocalizationControl().getText(R.id.activity_add_clinical_cases_add_details_caption));
        btnDelete.setText(Core.get().LocalizationControl().getText(R.id.activity_clinical_case_viewer_btn_delete));
    }

    @Override
    public void onSelectImage(CCImage ccImage) {
        Intent intent = new Intent(this, ImageViewerCCActivity.class);
        intent.putExtra(ImageViewerCCActivity.KEY_URL, ccImage.image);
        intent.putExtra(ImageViewerCCActivity.KEY_COMMENT, ccImage.comment);
        startActivity(intent);
    }
}
