package ua.profarma.medbook.ui.news_and_clinical_cases;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import ua.profarma.medbook.App;
import ua.profarma.medbook.Core;
import ua.profarma.medbook.R;
import ua.profarma.medbook.events.core.Event;
import ua.profarma.medbook.events.news.EventImageLoadClinicCase;
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
import ua.profarma.medbook.utils.DialogBuilder;
import ua.profarma.medbook.utils.FileUtils;
import ua.profarma.medbook.utils.LogUtils;


//"items": [
//        {
//        "id": 1,
//        "alias": "New"
//        },
//        {
//        "id": 2,
//        "alias": "Review"
//        },
//        {
//        "id": 3,
//        "alias": "Remarks"
//        },
//        {
//        "id": 4,
//        "alias": "Publish"
//        }
//        ]

public class AddClinicalCaseActivity extends MedBookActivity implements IOnDeleteAddCC {

    public static final String KEY_ID_CC = "KEY_ID_CC";

    public String commentImage = null;
    private MedicalCaseItem medicalCaseItem = null;
    private Uri selectedImage;
    private TextView tvTitle;
    private TextView tvAddTitle;
    private TextView tvTitleIcod;
    private TextView tvAddIcodBtn;
    private TextView tvTitleDrug;
    private TextView tvAddDrugBtn;
    private TextView tvAddImageBtn;
    private IcodSelectedRecyclerView listIcod;
    private DrugsSelectedRecyclerView listDrug;
    private ImageCCSelectedRecyclerView listImages;
    private RecyclerItems itemsIcod;
    private RecyclerItems itemsDrug;
    private RecyclerItems itemsImages;
    private Button btnSend;
    private Button btnSave;

    private TextView tvDetailsTitle;
    private AppCompatEditText edDetails;
    private AppCompatEditText edTitle;

    private ProgressBar pbAddImageLoad;

    private SwitchCompat switchAsAuthor;


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
        setContentView(R.layout.activity_add_medical_cases);
        switchAsAuthor = findViewById(R.id.activity_add_clinical_cases_as_author);
        pbAddImageLoad = findViewById(R.id.activity_add_clinical_cases_add_image_progress_bar);
        edTitle = findViewById(R.id.activity_add_clinical_cases_add_title_input);
        listImages = findViewById(R.id.activity_add_clinical_cases_image_list);
        listImages.init();
        itemsImages = new RecyclerItems();
        edDetails = findViewById(R.id.activity_add_clinical_cases_add_details_input);
        tvDetailsTitle = findViewById(R.id.activity_add_clinical_cases_add_details_caption);
        btnSend = findViewById(R.id.activity_add_clinical_cases_btn_send);
        btnSave = findViewById(R.id.activity_add_clinical_cases_btn_save);
        listIcod = findViewById(R.id.activity_add_clinical_cases_icod_list);
        listIcod.init();
        itemsIcod = new RecyclerItems();
        listDrug = findViewById(R.id.activity_add_clinical_cases_drug_list);
        listDrug.init();
        itemsDrug = new RecyclerItems();
        ImageView imClose = findViewById(R.id.activity_add_clinical_cases_toolbar_close);
        imClose.setOnClickListener(view -> finish());
        tvTitle = findViewById(R.id.activity_add_clinical_cases_toolbar_title);
        tvAddTitle = findViewById(R.id.activity_add_clinical_cases_add_title_caption);
        tvTitleIcod = findViewById(R.id.activity_add_clinical_cases_add_icod_caption);
        tvAddIcodBtn = findViewById(R.id.activity_add_clinical_cases_add_icod_btn);
        tvTitleDrug = findViewById(R.id.activity_add_clinical_cases_add_drug_caption);
        tvAddDrugBtn = findViewById(R.id.activity_add_clinical_cases_add_drug_btn);
        tvAddImageBtn = findViewById(R.id.activity_add_clinical_cases_add_image_btn);
        tvAddImageBtn.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            // Sets the type as image/*. This ensures only components of type image are selected
            intent.setType("image/*");
            //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
            String[] mimeTypes = {"image/jpeg", "image/png"};
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            // Launching the Intent
            startActivityForResult(intent, GALLERY_REQUEST_CODE);
        });
        tvAddIcodBtn.setOnClickListener(view -> startActivityForResult(new Intent(this, IcodSelectActivity.class), REQUEST_SELECT_ICOD));
        tvAddDrugBtn.setOnClickListener(view -> startActivityForResult(new Intent(this, DrugSelectActivity.class), REQUEST_SELECT_DRUG));
        btnSend.setOnClickListener(view -> {
            RequestMedicalCaseBody requestMedicalCaseBody = new RequestMedicalCaseBody();
            requestMedicalCaseBody.description = edDetails.getText().toString();
            requestMedicalCaseBody.title = edTitle.getText().toString();
            requestMedicalCaseBody.news_clinical_case_status_id = 2;//REVIEW
            if (itemsImages.size() > 0) {
                requestMedicalCaseBody.news_clinical_case_image = new CCImage[itemsImages.size()];
                for (int i = 0; i < requestMedicalCaseBody.news_clinical_case_image.length; i++)
                    requestMedicalCaseBody.news_clinical_case_image[i] = new CCImage(((ImageCCSelectedRecyclerItem) itemsImages.get(i)).getCcImage().image,
                            ((ImageCCSelectedRecyclerItem) itemsImages.get(i)).getCcImage().comment, false);
            }
            if (itemsDrug.size() > 0) {
                requestMedicalCaseBody.news_clinical_case_drug_id = new IdCCDrug[itemsDrug.size()];
                for (int i = 0; i < requestMedicalCaseBody.news_clinical_case_drug_id.length; i++)
                    requestMedicalCaseBody.news_clinical_case_drug_id[i] = new IdCCDrug(((DrugSelectedRecyclerItem) itemsDrug.get(i)).getDrug().id);
            }
            if (itemsIcod.size() > 0) {
                requestMedicalCaseBody.news_clinical_case_icod_id = new IdCCIcod[itemsIcod.size()];
                for (int i = 0; i < requestMedicalCaseBody.news_clinical_case_icod_id.length; i++)
                    requestMedicalCaseBody.news_clinical_case_icod_id[i] = new IdCCIcod(((IcodSelectedRecyclerItem) itemsIcod.get(i)).getIcodSelected().id);
            }
            requestMedicalCaseBody.show_author = switchAsAuthor.isChecked() ? 1 : 0;
            if (requestMedicalCaseBody.title == null || requestMedicalCaseBody.title.isEmpty() || requestMedicalCaseBody.title.length() < 3) {
                DialogBuilder.showInfoDialog(AddClinicalCaseActivity.this, Core.get().LocalizationControl().getText(R.id.general_message), Core.get().LocalizationControl().getText(R.id.add_md_case_title_min_symbols));
            } else if (requestMedicalCaseBody.description == null || requestMedicalCaseBody.description.isEmpty() || requestMedicalCaseBody.description.length() < 3) {
                DialogBuilder.showInfoDialog(AddClinicalCaseActivity.this, Core.get().LocalizationControl().getText(R.id.general_message), Core.get().LocalizationControl().getText(R.id.add_md_case_details_min_symbols));
            } else if (requestMedicalCaseBody.news_clinical_case_icod_id == null || requestMedicalCaseBody.news_clinical_case_icod_id.length == 0) {
                DialogBuilder.showInfoDialog(AddClinicalCaseActivity.this, Core.get().LocalizationControl().getText(R.id.general_message), Core.get().LocalizationControl().getText(R.id.add_md_case_icods_empty));
            } else if (requestMedicalCaseBody.news_clinical_case_drug_id == null || requestMedicalCaseBody.news_clinical_case_drug_id.length == 0) {
                DialogBuilder.showInfoDialog(AddClinicalCaseActivity.this, Core.get().LocalizationControl().getText(R.id.general_message), Core.get().LocalizationControl().getText(R.id.add_md_case_drugs_empty));
            } else {
                Core.get().NewsControl().createMedicalCase(requestMedicalCaseBody);
                finish();
            }

        });
        btnSave.setOnClickListener(view -> {
            RequestMedicalCaseBody requestMedicalCaseBody = new RequestMedicalCaseBody();
            requestMedicalCaseBody.description = edDetails.getText().toString();
            requestMedicalCaseBody.title = edTitle.getText().toString();
            requestMedicalCaseBody.news_clinical_case_status_id = 1;//NEW
            if (itemsImages.size() > 0) {
                requestMedicalCaseBody.news_clinical_case_image = new CCImage[itemsImages.size()];
                for (int i = 0; i < requestMedicalCaseBody.news_clinical_case_image.length; i++)
                    requestMedicalCaseBody.news_clinical_case_image[i] = new CCImage(((ImageCCSelectedRecyclerItem) itemsImages.get(i)).getCcImage().image,
                            ((ImageCCSelectedRecyclerItem) itemsImages.get(i)).getCcImage().comment, false);
            }
            if (itemsDrug.size() > 0) {
                requestMedicalCaseBody.news_clinical_case_drug_id = new IdCCDrug[itemsDrug.size()];
                for (int i = 0; i < requestMedicalCaseBody.news_clinical_case_drug_id.length; i++)
                    requestMedicalCaseBody.news_clinical_case_drug_id[i] = new IdCCDrug(((DrugSelectedRecyclerItem) itemsDrug.get(i)).getDrug().id);
            }
            if (itemsIcod.size() > 0) {
                requestMedicalCaseBody.news_clinical_case_icod_id = new IdCCIcod[itemsIcod.size()];
                for (int i = 0; i < requestMedicalCaseBody.news_clinical_case_icod_id.length; i++)
                    requestMedicalCaseBody.news_clinical_case_icod_id[i] = new IdCCIcod(((IcodSelectedRecyclerItem) itemsIcod.get(i)).getIcodSelected().id);
            }
            requestMedicalCaseBody.show_author = switchAsAuthor.isChecked() ? 1 : 0;
            if (medicalCaseItem == null)
                Core.get().NewsControl().createMedicalCase(requestMedicalCaseBody);
            else
                Core.get().NewsControl().editMedicalCase(medicalCaseItem.id, requestMedicalCaseBody);
            finish();
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
        onLocalizationUpdate();
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
        if (medicalCaseItem.newsClinicalCaseImages != null && medicalCaseItem.newsClinicalCaseImages.length > 0) {
            listImages.init();
            itemsImages = new RecyclerItems();
            for (int i = 0; i < medicalCaseItem.newsClinicalCaseImages.length; i++)
                itemsImages.add(new ImageCCSelectedRecyclerItem(new CCImage(medicalCaseItem.newsClinicalCaseImages[i].image, medicalCaseItem.newsClinicalCaseImages[i].comment, false)));
            listImages.itemsAdd(itemsImages);
        }
        switchAsAuthor.setChecked(medicalCaseItem.author_id == 1);
    }

    @Override
    protected void onLocalizationUpdate() {
        tvTitle.setText(Core.get().LocalizationControl().getText(R.id.activity_add_clinical_cases_toolbar_title));
        tvAddTitle.setText(Core.get().LocalizationControl().getText(R.id.activity_add_clinical_cases_add_title_caption));
        tvTitleIcod.setText(Core.get().LocalizationControl().getText(R.id.activity_add_clinical_cases_add_icod_caption));
        tvAddIcodBtn.setText(Core.get().LocalizationControl().getText(R.id.activity_add_clinical_cases_add_icod_btn));
        tvTitleDrug.setText(Core.get().LocalizationControl().getText(R.id.activity_add_clinical_cases_add_drug_caption));
        tvAddDrugBtn.setText(Core.get().LocalizationControl().getText(R.id.activity_add_clinical_cases_add_drug_btn));
        tvAddImageBtn.setText(Core.get().LocalizationControl().getText(R.id.activity_add_clinical_cases_add_image_btn));
        btnSend.setText(Core.get().LocalizationControl().getText(R.id.activity_add_clinical_cases_btn_send));
        edDetails.setHint(Core.get().LocalizationControl().getText(R.id.activity_add_clinical_cases_add_details_input));
        tvDetailsTitle.setText(Core.get().LocalizationControl().getText(R.id.activity_add_clinical_cases_add_details_caption));
        btnSave.setText(Core.get().LocalizationControl().getText(R.id.activity_add_clinical_cases_btn_save));
        switchAsAuthor.setText(Core.get().LocalizationControl().getText(R.id.activity_add_clinical_cases_as_author));
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

                case GALLERY_REQUEST_CODE:
                    //data.getData returns the content URI for the selected Image
                    selectedImage = data.getData();
                    LogUtils.logD("htfhyghh", "selectedImage = " + getPathFromURI(selectedImage));

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                                PackageManager.PERMISSION_GRANTED) {
                            showMessageForNeedPermission();
                        } else preStartUploadImage();
                    } else preStartUploadImage();
                    break;
                case ADD_COMMENT_TO_PRE_UPLOAD_IMAGE:
                    commentImage = data.getStringExtra("comment");
                    startUploadImage();
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
                            AddClinicalCaseActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    dialog.dismiss();
                    break;
            }
        }
    };

    private void preStartUploadImage(){
        Intent intent = new Intent(this, AddCommentToLoadImageActivity.class);
        intent.setData(selectedImage);
        startActivityForResult(intent, ADD_COMMENT_TO_PRE_UPLOAD_IMAGE);
    }
    private void startUploadImage() {
        pbAddImageLoad.setVisibility(View.VISIBLE);
        File file = FileUtils.getFile(this, selectedImage);

        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(getContentResolver().getType(selectedImage)),
                        file
                );
        Core.get().NewsControl().uploadImage(file.getName(), requestFile);
    }

    private String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

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
    public void onEvent(Event event) {
        super.onEvent(event);
        switch (event.getEventId()) {
            case Event.EVENT_LOAD_IMAGE_CLINIC_CASE_STOP:
                pbAddImageLoad.setVisibility(View.INVISIBLE);
                break;
            case Event.EVENT_LOAD_IMAGE_CLINIC_CASE:
                listImages.setVisibility(View.VISIBLE);
                //AppUtils.toastOk(((EventImageLoadClinicCase) (event)).getUrlImage(), false);
                if (itemsImages.isEmpty()) {
                    itemsImages.add(new ImageCCSelectedRecyclerItem(new CCImage(((EventImageLoadClinicCase) (event)).getImage(), commentImage, false)));
                    listImages.itemsAdd(itemsImages);
                } else {
                    itemsImages.add(new ImageCCSelectedRecyclerItem(new CCImage(((EventImageLoadClinicCase) (event)).getImage(), commentImage, false)));
                    listImages.itemAdd(new ImageCCSelectedRecyclerItem(new CCImage(((EventImageLoadClinicCase) (event)).getImage(), commentImage, false)));
                }
                commentImage = null;
                pbAddImageLoad.setVisibility(View.INVISIBLE);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    preStartUploadImage();
                } else pbAddImageLoad.setVisibility(View.INVISIBLE);
                break;

        }
    }

    @Override
    public void onDeleteImage(CCImage ccImage) {
        int deletePos = -1;
        for (int i = 0; i < itemsImages.size(); i++) {
            if (((ImageCCSelectedRecyclerItem) itemsImages.get(i)).getCcImage().image.equals(ccImage.image))
                deletePos = i;
        }
        if (deletePos != -1) {
            if(itemsImages.size() == 1) listImages.setVisibility(View.GONE);
            itemsImages.remove(deletePos);
            listImages.itemRemove(deletePos);
        }
    }
}
