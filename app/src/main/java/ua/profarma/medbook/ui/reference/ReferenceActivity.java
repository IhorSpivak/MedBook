package ua.profarma.medbook.ui.reference;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import ua.profarma.medbook.App;
import ua.profarma.medbook.Core;
import ua.profarma.medbook.R;
import ua.profarma.medbook.events.core.Event;
import ua.profarma.medbook.events.news.EventImageLoadClinicCase;
import ua.profarma.medbook.models.response.ReferenceItem;
import ua.profarma.medbook.recyclerviews.base.RecyclerItem;
import ua.profarma.medbook.recyclerviews.base.RecyclerItems;
import ua.profarma.medbook.recyclerviews.drug_selected.DrugSelectedRecyclerItem;
import ua.profarma.medbook.recyclerviews.drug_selected.DrugsSelectedRecyclerView;
import ua.profarma.medbook.recyclerviews.icod_selected.IcodSelectedRecyclerItem;
import ua.profarma.medbook.recyclerviews.icod_selected.IcodSelectedRecyclerView;
import ua.profarma.medbook.recyclerviews.image_cc_selected.ImageCCSelectedRecyclerItem;
import ua.profarma.medbook.recyclerviews.image_cc_selected.ImageCCSelectedRecyclerView;
import ua.profarma.medbook.types.news.DrugSelected;
import ua.profarma.medbook.types.news.IcodSelected;
import ua.profarma.medbook.types.news.IdCCDrug;
import ua.profarma.medbook.types.news.IdCCIcod;
import ua.profarma.medbook.types.news.CCImage;
import ua.profarma.medbook.types.news.MedicalCaseItem;
import ua.profarma.medbook.types.requests.RequestMedicalCaseBody;
import ua.profarma.medbook.ui.custom_views.MedBookActivity;
import ua.profarma.medbook.ui.news_and_clinical_cases.AddClinicalCaseActivity;
import ua.profarma.medbook.ui.news_and_clinical_cases.AddCommentToLoadImageActivity;
import ua.profarma.medbook.ui.news_and_clinical_cases.DrugSelectActivity;
import ua.profarma.medbook.ui.news_and_clinical_cases.IOnDeleteAddCC;
import ua.profarma.medbook.ui.news_and_clinical_cases.IcodSelectActivity;
import ua.profarma.medbook.utils.DialogBuilder;
import ua.profarma.medbook.utils.FileUtils;
import ua.profarma.medbook.utils.LogUtils;



public class ReferenceActivity extends MedBookActivity implements IOnDeleteAddCC {

    public static final String KEY_ID_CC = "KEY_ID_CC";
    private static final int RESULT_PICK_CONTACT1= 1;

    public String commentImage = null;
    private MedicalCaseItem medicalCaseItem = null;
    private Uri selectedImage;

    private TextView tvAddIcodBtn;
    private TextView tvTitleDrug;
    private TextView tvAddDrugBtn;
    private TextView tvAddImageBtn;
    private IcodSelectedRecyclerView listIcod;
    private DrugsSelectedRecyclerView listDrug;

    private RecyclerItems itemsIcod;
    private RecyclerItems itemsDrug;
    private RecyclerItems itemsImages;
    private Button btnSend;
    private Button btnSave;
    private FloatingActionButton iv_user;

    private TextView tvDetailsTitle;
    private ImageView close;
    private AppCompatEditText edDetails;
    private AppCompatEditText edTitle;
    private AppCompatEditText ed_phone;
    private AppCompatEditText ed_mail;

    private ProgressBar pbAddImageLoad;

    private Switch aSwitch;


    public static final int REQUEST_SELECT_ICOD = 3311;
    public static final int REQUEST_SELECT_DRUG = 3322;
    public static final int GALLERY_REQUEST_CODE = 3333;
    public static final int ADD_COMMENT_TO_PRE_UPLOAD_IMAGE = 4444;
    public static final int PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 3344;

    public static final String SELECT_ICOD_ID = "SELECT_ICOD_ID";
    public static final String SELECT_ICOD_CODE = "SELECT_ICOD_CODE";
    public static final String SELECT_ICOD_TITLE = "SELECT_ICOD_TITLE";
    public static final String SELECT_DRUG_ID = "SELECT_DRUG_ID";
    public static final String SELECT_DRUG_TITLE = "SELECT_DRUG_TITLE";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reference);
        ButterKnife.bind(this);
        aSwitch = findViewById(R.id.sw);
        edTitle = findViewById(R.id.ed_name);
        edDetails = findViewById(R.id.ed_description);
        tvDetailsTitle = findViewById(R.id.activity_add_clinical_cases_add_details_caption);
        btnSend = findViewById(R.id.btn_send);
        btnSave = findViewById(R.id.btn_check);
        close = findViewById(R.id.close);
        listIcod = findViewById(R.id.activity_add_clinical_cases_icod_list);
        ed_mail = findViewById(R.id.ed_mail);
        iv_user = findViewById(R.id.iv_user);
        listIcod.init();
        itemsIcod = new RecyclerItems();
        listDrug = findViewById(R.id.activity_add_clinical_cases_drug_list);
        ed_phone = findViewById(R.id.ed_phone);
        listDrug.init();
        itemsDrug = new RecyclerItems();
        tvAddIcodBtn = findViewById(R.id.activity_add_clinical_cases_add_icod_btn);
        tvTitleDrug = findViewById(R.id.activity_add_clinical_cases_add_drug_caption);
        tvAddDrugBtn = findViewById(R.id.activity_add_clinical_cases_add_drug_btn);
        tvAddImageBtn = findViewById(R.id.activity_add_clinical_cases_add_image_btn);


        tvAddIcodBtn.setOnClickListener(view -> startActivityForResult(new Intent(this, IcodSelectActivity.class), REQUEST_SELECT_ICOD));
        close.setOnClickListener(view -> onBackPressed());
        tvAddDrugBtn.setOnClickListener(view -> startActivityForResult(new Intent(this, DrugSelectActivity.class), REQUEST_SELECT_DRUG));
        iv_user.setOnClickListener(view -> startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI), RESULT_PICK_CONTACT1));
        btnSend.setOnClickListener(view -> {

            sendReference();

        });
        btnSave.setOnClickListener(view -> {
//            RequestMedicalCaseBody requestMedicalCaseBody = new RequestMedicalCaseBody();
//            requestMedicalCaseBody.description = edDetails.getText().toString();
//            requestMedicalCaseBody.title = edTitle.getText().toString();
//            requestMedicalCaseBody.news_clinical_case_status_id = 1;//NEW
//            if (itemsImages.size() > 0) {
//                requestMedicalCaseBody.news_clinical_case_image = new CCImage[itemsImages.size()];
//                for (int i = 0; i < requestMedicalCaseBody.news_clinical_case_image.length; i++)
//                    requestMedicalCaseBody.news_clinical_case_image[i] = new CCImage(((ImageCCSelectedRecyclerItem) itemsImages.get(i)).getCcImage().image,
//                            ((ImageCCSelectedRecyclerItem) itemsImages.get(i)).getCcImage().comment, false);
//            }
//            if (itemsDrug.size() > 0) {
//                requestMedicalCaseBody.news_clinical_case_drug_id = new IdCCDrug[itemsDrug.size()];
//                for (int i = 0; i < requestMedicalCaseBody.news_clinical_case_drug_id.length; i++)
//                    requestMedicalCaseBody.news_clinical_case_drug_id[i] = new IdCCDrug(((DrugSelectedRecyclerItem) itemsDrug.get(i)).getDrug().id);
//            }
//            if (itemsIcod.size() > 0) {
//                requestMedicalCaseBody.news_clinical_case_icod_id = new IdCCIcod[itemsIcod.size()];
//                for (int i = 0; i < requestMedicalCaseBody.news_clinical_case_icod_id.length; i++)
//                    requestMedicalCaseBody.news_clinical_case_icod_id[i] = new IdCCIcod(((IcodSelectedRecyclerItem) itemsIcod.get(i)).getIcodSelected().id);
//            }
//            requestMedicalCaseBody.show_author = aSwitch.isChecked() ? 1 : 0;
//            if (medicalCaseItem == null)
//                Core.get().NewsControl().createMedicalCase(requestMedicalCaseBody);
//            else
//                Core.get().NewsControl().editMedicalCase(medicalCaseItem.id, requestMedicalCaseBody);
//            finish();
        });



        if (getIntent() != null) {
            int idCC = getIntent().getIntExtra(KEY_ID_CC, -1);
            if (idCC != -1) {
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
        Gson gson = new Gson();
        ReferenceItem itemReference = gson.fromJson(getIntent().getStringExtra("myjson"), ReferenceItem.class);
        if(itemReference != null){
            setDataReference(itemReference);
        } else {
            btnSave.setVisibility(View.GONE);
        }
        onLocalizationUpdate();
    }

    private void sendReference() {
        if(itemsIcod.size() == 0){
            Toast.makeText(this, R.string.must_chose_icod, Toast.LENGTH_LONG).show();
        } else if(itemsDrug.size() == 0){
            Toast.makeText(this, R.string.must_chose_drugs, Toast.LENGTH_LONG).show();
        } else if(itemsDrug.size() > 0 && itemsIcod.size() > 0) {
            RequestMedicalCaseBody requestMedicalCaseBody = new RequestMedicalCaseBody();
            requestMedicalCaseBody.description = edDetails.getText().toString();
            requestMedicalCaseBody.title = edTitle.getText().toString();
            requestMedicalCaseBody.news_clinical_case_status_id = 2;//REVIEW

//            if (itemsDrug.size() > 0) {
//                requestMedicalCaseBody.news_clinical_case_drug_id = new IdCCDrug[itemsDrug.size()];
//                for (int i = 0; i < requestMedicalCaseBody.news_clinical_case_drug_id.length; i++)
//                    requestMedicalCaseBody.news_clinical_case_drug_id[i] = new IdCCDrug(((DrugSelectedRecyclerItem) itemsDrug.get(i)).getDrug().id);
//            }
//            if (itemsIcod.size() > 0) {
//                requestMedicalCaseBody.news_clinical_case_icod_id = new IdCCIcod[itemsIcod.size()];
//                for (int i = 0; i < requestMedicalCaseBody.news_clinical_case_icod_id.length; i++)
//                    requestMedicalCaseBody.news_clinical_case_icod_id[i] = new IdCCIcod(((IcodSelectedRecyclerItem) itemsIcod.get(i)).getIcodSelected().id);
//            }
//            requestMedicalCaseBody.show_author = aSwitch.isChecked() ? 1 : 0;
//            if (requestMedicalCaseBody.title == null || requestMedicalCaseBody.title.isEmpty() || requestMedicalCaseBody.title.length() < 3) {
//                DialogBuilder.showInfoDialog(ReferenceActivity.this, Core.get().LocalizationControl().getText(R.id.general_message), Core.get().LocalizationControl().getText(R.id.add_md_case_title_min_symbols));
//            } else if (requestMedicalCaseBody.description == null || requestMedicalCaseBody.description.isEmpty() || requestMedicalCaseBody.description.length() < 3) {
//                DialogBuilder.showInfoDialog(ReferenceActivity.this, Core.get().LocalizationControl().getText(R.id.general_message), Core.get().LocalizationControl().getText(R.id.add_md_case_details_min_symbols));
//            } else if (requestMedicalCaseBody.news_clinical_case_icod_id == null || requestMedicalCaseBody.news_clinical_case_icod_id.length == 0) {
//                DialogBuilder.showInfoDialog(ReferenceActivity.this, Core.get().LocalizationControl().getText(R.id.general_message), Core.get().LocalizationControl().getText(R.id.add_md_case_icods_empty));
//            } else if (requestMedicalCaseBody.news_clinical_case_drug_id == null || requestMedicalCaseBody.news_clinical_case_drug_id.length == 0) {
//                DialogBuilder.showInfoDialog(ReferenceActivity.this, Core.get().LocalizationControl().getText(R.id.general_message), Core.get().LocalizationControl().getText(R.id.add_md_case_drugs_empty));
//            } else {
//                Core.get().NewsControl().createMedicalCase(requestMedicalCaseBody);
//                finish();
//            }
        }
    }


    private void setDataCC() {
        edTitle.setText(medicalCaseItem.title);
        edDetails.setText(medicalCaseItem.description);
        if (medicalCaseItem.newsClinicalCaseDrugs != null && medicalCaseItem.newsClinicalCaseDrugs.length > 0) {
            listDrug.init();
            itemsDrug = new RecyclerItems();
            for (int i = 0; i < medicalCaseItem.newsClinicalCaseDrugs.length; i++)
                itemsDrug.add(new DrugSelectedRecyclerItem(new DrugSelected(medicalCaseItem.newsClinicalCaseDrugs[i].drug_id, medicalCaseItem.newsClinicalCaseDrugs[i].drug.title, false)));
            listDrug.itemsAdd(itemsDrug);
        }
        if (medicalCaseItem.newsClinicalCaseIcods != null && medicalCaseItem.newsClinicalCaseIcods.length > 0) {
            listIcod.init();
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
                        medicalCaseItem.newsClinicalCaseIcods[i].icod.translations[selectLang].title, false)));
            }
            listIcod.itemsAdd(itemsIcod);
        }

        aSwitch.setChecked(medicalCaseItem.author_id == 1);
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
    }




    private void contactPicked1 (Intent data){
        Cursor cursor = null;
        try {
            String phoneNo = null;
            String mail = null;
            Uri uri = data.getData();
            cursor = getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            int mailIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA);
            mail = cursor.getString(mailIndex);
            phoneNo = cursor.getString(phoneIndex);
            ed_phone.setText(phoneNo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onLocalizationUpdate() {

        tvAddIcodBtn.setText(Core.get().LocalizationControl().getText(R.id.activity_add_clinical_cases_add_icod_btn));
        tvAddDrugBtn.setText(Core.get().LocalizationControl().getText(R.id.activity_add_clinical_cases_add_drug_btn));
//        btnSend.setText(Core.get().LocalizationControl().getText(R.id.btn_send));
//        tvDetailsTitle.setText(Core.get().LocalizationControl().getText(R.id.activity_add_clinical_cases_add_details_caption));
//        btnSave.setText(Core.get().LocalizationControl().getText(R.id.btn_check));
//        aSwitch.setText(Core.get().LocalizationControl().getText(R.id.activity_add_clinical_cases_as_author));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
            switch (requestCode) {
                case REQUEST_SELECT_ICOD:
                    int[] id = data.getIntArrayExtra(SELECT_ICOD_ID);
                    String[] code = data.getStringArrayExtra(SELECT_ICOD_CODE);
                    String[] title = data.getStringArrayExtra(SELECT_ICOD_TITLE);
                    for (int i = 0; i < id.length; i++) {
                        LogUtils.logD("jhjghjgjh", id[i] + " " + code[i] + " " + title[i]);
                        if (checkIcodItemExist(id[i]))
                            itemsIcod.add(new IcodSelectedRecyclerItem(new IcodSelected(id[i], code[i], title[i], false)));
                    }

                    listIcod.itemsAdd(itemsIcod);
                    break;
                case REQUEST_SELECT_DRUG:
                    int idD = data.getIntExtra(SELECT_DRUG_ID, 0);
                    String titleD = data.getStringExtra(SELECT_DRUG_TITLE);
                    LogUtils.logD("jhjghjgjh", idD + " " + titleD);
                    if (checkDrugItemExist(idD))
                        itemsDrug.add(new DrugSelectedRecyclerItem(new DrugSelected(idD, titleD, false)));

                    listDrug.itemsAdd(itemsDrug);
                    break;

                case RESULT_PICK_CONTACT1:
                    contactPicked1(data);
                    break;
            }
        if (resultCode == RESULT_CANCELED){
            if(requestCode == ADD_COMMENT_TO_PRE_UPLOAD_IMAGE){
                commentImage = null;
                selectedImage = null;
            }
        }
    }

    private void showMessageForNeedPermission() {
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.need_access_read_storage))
                .setPositiveButton(getString(R.string.btn_ok), listener)
                .setNegativeButton(getString(R.string.btn_cancel), listener)
                .create()
                .show();
    }

    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {

        final int BUTTON_NEGATIVE = -2;
        final int BUTTON_POSITIVE = -1;

        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case BUTTON_NEGATIVE:
                    dialog.dismiss();
                    pbAddImageLoad.setVisibility(View.INVISIBLE);
                    break;

                case BUTTON_POSITIVE:
                    ActivityCompat.requestPermissions(
                            ReferenceActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    dialog.dismiss();
                    break;
            }
        }
    };


    private boolean checkIcodItemExist(int id) {
        for (RecyclerItem item : itemsIcod) {
            if (((IcodSelectedRecyclerItem) item).getIcodSelected().id == id) return false;
        }
        return true;
    }

    private boolean checkDrugItemExist(int id) {
        for (RecyclerItem item : itemsDrug) {
            if (((DrugSelectedRecyclerItem) item).getDrugSelected().id == id) return false;
        }
        return true;
    }

    @Override
    public void onDeleteIcod(IcodSelected icodSelected) {
        int deletePos = -1;
        for (int i = 0; i < itemsIcod.size(); i++) {
            if (((IcodSelectedRecyclerItem) itemsIcod.get(i)).getIcodSelected().id == icodSelected.id)
                deletePos = i;
        }
        if (deletePos != -1) {
            itemsIcod.remove(deletePos);
            listIcod.itemRemove(deletePos);
        }
    }

    @Override
    public void onDeleteDrug(DrugSelected drugSelected) {
        int deletePos = -1;
        for (int i = 0; i < itemsDrug.size(); i++) {
            if (((DrugSelectedRecyclerItem) itemsDrug.get(i)).getDrugSelected().id == drugSelected.id)
                deletePos = i;
        }
        if (deletePos != -1) {
            itemsDrug.remove(deletePos);
            listDrug.itemRemove(deletePos);
        }
    }

    @Override
    public void onDeleteImage(CCImage ccImage) {

    }

    @Override
    public void onEvent(Event event) {
        super.onEvent(event);
        switch (event.getEventId()) {
            case Event.EVENT_LOAD_IMAGE_CLINIC_CASE_STOP:
                pbAddImageLoad.setVisibility(View.INVISIBLE);
                break;

        }
    }


}
