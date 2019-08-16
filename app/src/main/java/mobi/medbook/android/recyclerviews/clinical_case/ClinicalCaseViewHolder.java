package mobi.medbook.android.recyclerviews.clinical_case;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import mobi.medbook.android.Core;
import mobi.medbook.android.R;
import mobi.medbook.android.recyclerviews.base.BaseViewHolder;
import mobi.medbook.android.types.news.MedicalCaseItem;

public class ClinicalCaseViewHolder extends BaseViewHolder {

    private TextView tvTime;
    private TextView tvTitle;
    private TextView tvStatus;

    private ImageView imv;
    private View rootView;


    public ClinicalCaseViewHolder(View itemView) {
        super(itemView);
        tvStatus = itemView.findViewById(R.id.item_news_status);
        tvTime = itemView.findViewById(R.id.item_news_time);
        tvTitle = itemView.findViewById(R.id.item_news_title);
        imv = itemView.findViewById(R.id.item_news_image);
        rootView = itemView.findViewById(R.id.item_news_root);
    }

    public void init(ClinicalCaseRecyclerItem owner, MedicalCaseItem medicalCaseItem) {
        rootView.setOnClickListener(owner);
        if(tvTime != null){
            Date date = new Date(medicalCaseItem.created_at * 1000L);
            //DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            DateFormat format = new SimpleDateFormat("dd MMM yyyy");
            //format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
            String formatted = format.format(date);
            tvTime.setText(formatted);
        }
        if(tvTitle != null){
            tvTitle.setText(medicalCaseItem.title);
        }

        imv.setImageBitmap(null);
        if(imv != null) {
            if (medicalCaseItem.newsClinicalCaseImages != null && medicalCaseItem.newsClinicalCaseImages.length > 0 &&
                    medicalCaseItem.newsClinicalCaseImages[0].image != null && !medicalCaseItem.newsClinicalCaseImages[0].image.isEmpty())
                Picasso.get().load(medicalCaseItem.newsClinicalCaseImages[0].image).into(imv);
            else {
                imv.setBackgroundResource(R.drawable.empty_photo);
            }
        }
        switch (medicalCaseItem.news_clinical_case_status_id){
            case 1:
                tvStatus.setText(Core.get().LocalizationControl().getText(R.id.medical_case_status_1));
                tvStatus.setTextColor(Color.rgb(54, 54, 54));
                tvStatus.setBackgroundResource(R.drawable.rectangle_medical_case_rounded_status_1);
                break;
            case 2:
                tvStatus.setText(Core.get().LocalizationControl().getText(R.id.medical_case_status_2));
                tvStatus.setTextColor(Color.rgb(156, 165, 172));
                tvStatus.setBackgroundResource(R.drawable.rectangle_medical_case_rounded_status_2);
                break;
            case 3:
                tvStatus.setText(Core.get().LocalizationControl().getText(R.id.medical_case_status_3));
                tvStatus.setTextColor(Color.rgb(255, 255, 255));
                tvStatus.setBackgroundResource(R.drawable.rectangle_medical_case_rounded_status_3);
                break;
            case 4:
                tvStatus.setText(Core.get().LocalizationControl().getText(R.id.medical_case_status_4));
                tvStatus.setTextColor(Color.rgb(255, 255, 255));
                tvStatus.setBackgroundResource(R.drawable.rectangle_medical_case_rounded_status_4);
                break;

        }

    }
}
