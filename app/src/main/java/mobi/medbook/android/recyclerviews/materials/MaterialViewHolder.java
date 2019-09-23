package mobi.medbook.android.recyclerviews.materials;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import mobi.medbook.android.R;
import mobi.medbook.android.recyclerviews.base.BaseViewHolder;
import mobi.medbook.android.types.materials.Material;
import mobi.medbook.android.types.materials.Test;

public class MaterialViewHolder extends BaseViewHolder {

    private View rootView;
    private TextView tvVideo;
    private TextView tvPresentations;
    private TextView tvTests;

    private TextView tvtitle;
    private TextView tvBalls;

    private ImageView imvLogoProd;
    private ImageView imvLogoCompany;
    private ImageView imvDot;
    private ImageView imvInfo;

    public MaterialViewHolder(View itemView) {
        super(itemView);
        imvDot = itemView.findViewById(R.id.item_materials_type_list_dot);
        tvVideo = itemView.findViewById(R.id.item_materials_type_list_tv_videos);
        tvPresentations = itemView.findViewById(R.id.item_materials_type_list_tv_presentations);
        tvTests = itemView.findViewById(R.id.item_materials_type_list_tv_tests);
        tvtitle = itemView.findViewById(R.id.item_materials_type_list_tv_title);
        tvBalls = itemView.findViewById(R.id.item_materials_type_list_tv_balls);

        imvLogoProd = itemView.findViewById(R.id.item_materials_type_list_logo_prod);

        imvLogoCompany = itemView.findViewById(R.id.item_materials_type_list_logo_company);
        imvInfo = itemView.findViewById(R.id.item_materials_type_list_imv_info);

        rootView = itemView.findViewById(R.id.item_list_of_list_materials);
    }

    public void init(MaterialListRecyclerItem owner, Material material) {

        int testSize = material.tests.length;
        int presentationSize = material.presentations.length;
        int videoSize = material.videos.length;

            if (material.tests.length > 0) {
                for (int i = 0; i < material.tests.length; i++) {
                    if (material.tests[i].time_from > System.currentTimeMillis() / 1000 || material.tests[i].time_to < System.currentTimeMillis()  /1000 ) {
                        testSize = testSize - 1;
                    }
                }
            }

         if (material.presentations.length > 0 ) {
             for (int i = 0; i < material.presentations.length; i++) {
                 if (material.presentations[i].time_from > System.currentTimeMillis() / 1000 || material.presentations[i].time_to < System.currentTimeMillis() /1000) {
                     presentationSize = presentationSize - 1;
                 }
             }
         }


        if (material.videos.length > 0) {

            for (int i = 0; i < material.videos.length; i++) {
                if (material.tests[i].time_from > System.currentTimeMillis() / 1000 || material.videos[i].time_to < System.currentTimeMillis()  /1000) {
                    videoSize = videoSize - 1;
                }
            }
        }


        if (material.count.total == 0) {
            imvDot.setVisibility(View.INVISIBLE);
        }

        if (tvtitle != null) {
            tvtitle.setText(material.translations[1].name);
        }
        if (tvTests != null) {
            if (testSize > 0) {
                tvTests.setVisibility(View.VISIBLE);
                tvTests.setText(String.valueOf(testSize));
            } else
                tvTests.setVisibility(View.GONE);
        }
        if (tvPresentations != null) {
            if (presentationSize > 0) {
                tvPresentations.setVisibility(View.VISIBLE);
                tvPresentations.setText(String.valueOf(presentationSize));
            } else
                tvPresentations.setVisibility(View.GONE);
        }
        if (tvVideo != null) {
            if (videoSize > 0) {
                tvVideo.setVisibility(View.VISIBLE);
                tvVideo.setText(String.valueOf(videoSize));
            } else
                tvVideo.setVisibility(View.GONE);
        }
        if (tvBalls != null) {
            if (material.count.points_available > 0) {
                tvBalls.setVisibility(View.VISIBLE);
                tvBalls.setText(String.valueOf(material.count.points_available));
            } else
                tvBalls.setVisibility(View.GONE);
        }
        if(material.translations != null)
        if(!material.translations[0].logo.isEmpty()){
            Picasso.get().load(material.translations[0].logo).into(imvLogoProd);
        }
        if(material.manufacturer != null)
        if(!material.manufacturer.translations[0].logo.isEmpty() && material.manufacturer.translations[0].logo != null){
            Picasso.get().load(material.manufacturer.translations[0].logo).into(imvLogoCompany);
        }


        imvInfo.setOnClickListener(owner);

        rootView.setOnClickListener(owner);
    }
}
