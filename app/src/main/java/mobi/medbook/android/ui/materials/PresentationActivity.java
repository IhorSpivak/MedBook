package mobi.medbook.android.ui.materials;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import mobi.medbook.android.Core;
import mobi.medbook.android.R;
import mobi.medbook.android.types.materials.ImageP;
import mobi.medbook.android.types.materials.Material;
import mobi.medbook.android.types.materials.Presentation;
import mobi.medbook.android.utils.AppUtils;
import mobi.medbook.android.utils.LogUtils;


public class PresentationActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String KEY_ID_PRESENTATION = "KEY_ID_PRESENTATION";
    public static final String KEY_ID_MATERIAL_PRESENTATION = "KEY_ID_MATERIAL_PRESENTATION";
    private ImageP[] images;
    private Presentation presentation;
    private int presentationId;
    private int materialId;
    private TextView tvIndicator;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentation);

        tvIndicator = findViewById(R.id.activity_presentation_pager_indicator);

        ConstraintLayout rootView = findViewById(R.id.activity_presentation_root);

        ImageView imBack = findViewById(R.id.activity_presentation_toolbar_close);
        imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (getIntent() != null) {
            presentationId = getIntent().getIntExtra(KEY_ID_PRESENTATION, 0);
            materialId = getIntent().getIntExtra(KEY_ID_MATERIAL_PRESENTATION, -1);

            if (materialId == -1)
                for (Presentation item : Core.get().UserContentControl().getSelectedMaterial().presentations) {
                    if (item.id == presentationId) {
                        presentation = item;
                        images = item.presentation.translations[0].images;
                        ((TextView) findViewById(R.id.activity_presentation_toolbar_title)).setText(item.presentation.translations[0].title);
                    }
                }
            else
                for (Material itemMaterial : Core.get().UserContentControl().getListMaterial())
                    if (itemMaterial.id == materialId)
                        for (Presentation item : itemMaterial.presentations) {
                            if (item.id == presentationId) {
                                presentation = item;
                                images = item.presentation.translations[0].images;
                                ((TextView) findViewById(R.id.activity_presentation_toolbar_title)).setText(item.presentation.translations[0].title);
                            }
                        }
            if (presentation.presentation.translations[0].images.length <= 1)
                tvIndicator.setVisibility(View.INVISIBLE);
            else {
                String msg = "1 / " + presentation.presentation.translations[0].images.length;
                Spannable spannableText = new SpannableString(msg);
                spannableText.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_button_type_2)),
                        0, msg.indexOf(" "), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableText.setSpan(new RelativeSizeSpan(1.5f), 0, msg.indexOf(" "), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvIndicator.setText(spannableText);
            }

        }

        ViewPager viewPager = findViewById(R.id.activity_presentation_pager);
        if (images != null && images.length > 0) {
            ImageAdapter adapter = new ImageAdapter(this, images);
            viewPager.setAdapter(adapter);
            //viewPager.setCurrentItem(adapter.getCount() - 1);
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    String msg = (position + 1) + " / " + presentation.presentation.translations[0].images.length;
                    Spannable spannableText = new SpannableString(msg);
                    spannableText.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_button_type_2)),
                            0, msg.indexOf(" "), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spannableText.setSpan(new RelativeSizeSpan(1.5f), 0, msg.indexOf(" "), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tvIndicator.setText(spannableText);
                    LogUtils.logD("http", "position = " + position + ", images.length = " + images.length);
                    if (position == (images.length - 1) && (presentation.result_time == null || presentation.result_time.isEmpty())) {
                        long time = System.currentTimeMillis() / 1000;
                        if (time < presentation.time_to)
                            Core.get().UserContentControl().sendTimeResultPresentation(materialId, presentationId, time);
                        else
                            AppUtils.toastError("Сплив час для передачі результата о перегляді презентації", true);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
        } else {
            AppUtils.toastError("Нажаль презентації відсутні. Спробуйте пізніше, або сплив термін показу", true);
        }
    }

    @Override
    public void onClick(View v) {

    }
}
