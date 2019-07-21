package ua.profarma.medbook.recyclerviews.materials;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ua.profarma.medbook.R;
import ua.profarma.medbook.recyclerviews.base.BaseViewHolder;
import ua.profarma.medbook.types.materials.Material;

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

        if (material.count.total == 0) {
            imvDot.setVisibility(View.INVISIBLE);
        }

        if (tvtitle != null) {
            tvtitle.setText(material.translations[0].name);
        }
        if (tvTests != null) {
            if (material.tests.length > 0) {
                tvTests.setVisibility(View.VISIBLE);
                tvTests.setText(String.valueOf(material.tests.length));
            } else
                tvTests.setVisibility(View.GONE);
        }
        if (tvPresentations != null) {
            if (material.presentations.length > 0) {
                tvPresentations.setVisibility(View.VISIBLE);
                tvPresentations.setText(String.valueOf(material.presentations.length));
            } else
                tvPresentations.setVisibility(View.GONE);
        }
        if (tvVideo != null) {
            if (material.videos.length > 0) {
                tvVideo.setVisibility(View.VISIBLE);
                tvVideo.setText(String.valueOf(material.videos.length));
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
        Picasso.get().load(material.translations[0].logo).into(imvLogoProd);
        Picasso.get().load(material.manufacturer.translations[0].logo).into(imvLogoCompany);

        imvInfo.setOnClickListener(owner);

        rootView.setOnClickListener(owner);
    }
}
