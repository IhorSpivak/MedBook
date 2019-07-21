package ua.profarma.medbook.ui.materials;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import ua.profarma.medbook.Core;
import ua.profarma.medbook.R;
import ua.profarma.medbook.types.materials.Material;
import ua.profarma.medbook.types.materials.ResultTestQuestion;
import ua.profarma.medbook.types.materials.Test;
import ua.profarma.medbook.utils.LogUtils;


public class ViewCompletedTestActivity extends AppCompatActivity {

    private String TAG = "AppMedbook/ViewCompletedTestActivity";
    public static final String KEY_TEST_USER_CONTENT_ID = "KEY_TEST_USER_CONTENT_ID";
    private ResultTestQuestion[] testResults;
    private int testId;

    private Button btnNext;
    private Button btnPrev;
    private ProgressBar progressBar;
    private TextView tvProgress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests);

        ImageView imClose = findViewById(R.id.activity_tests_toolbar_close);
        imClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (getIntent() != null) {
            testId = getIntent().getIntExtra(KEY_TEST_USER_CONTENT_ID, 0);
            LogUtils.logD(TAG, "testId = " + testId);
            if (Core.get().UserContentControl().getSelectedMaterial() == null) {
                for (Material itemM : Core.get().UserContentControl().getListMaterial()) {
                    for (Test itemT : itemM.tests) {
                        if (itemT.id == testId) {
                            LogUtils.logD(TAG, "UP");
                            testResults = itemT.test.results;
                            LogUtils.logD(TAG, "testResults.length = " + testResults.length);
                            TextView tvTitle = findViewById(R.id.activity_tests_toolbar_title);
                            tvTitle.setText(itemT.test.translations[0].title);
                        }
                    }
                }
            } else
                for (Test item : Core.get().UserContentControl().getSelectedMaterial().tests) {
                    if (item.id == testId) {
                        LogUtils.logD(TAG, "UP");
                        testResults = item.test.results;
                        LogUtils.logD(TAG, "testResults.length = " + testResults.length);
                        TextView tvTitle = findViewById(R.id.activity_tests_toolbar_title);
                        tvTitle.setText(item.test.translations[0].title);
                    }
                }
        }
        if (testResults == null) {
            finish();
        }
        ViewPager pager = findViewById(R.id.activity_tests_pager);
        TestFragmentPagerAdapter pagerAdapter = new TestFragmentPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.setOffscreenPageLimit(1);

        progressBar = findViewById(R.id.activity_tests_progressbar);
        tvProgress = findViewById(R.id.activity_tests_progress_title);
        progressBar.setVisibility(View.GONE);
        btnNext = findViewById(R.id.activity_tests_next);
        btnPrev = findViewById(R.id.activity_tests_prev);

        btnNext.setText(R.string.next_caption);
        btnPrev.setText(R.string.prev_caption);

        btnPrev.setVisibility(View.INVISIBLE);
        if (testResults.length == 1)
            btnNext.setVisibility(View.INVISIBLE);
        else {
            String msg = "1 / " + testResults.length;
            Spannable spannableText = new SpannableString(msg);
            spannableText.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_button_type_2)),
                    0, msg.indexOf(" "), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableText.setSpan(new RelativeSizeSpan(1.5f), 0, msg.indexOf(" "), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvProgress.setText(spannableText);
        }

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(pager.getCurrentItem() + 1);
            }
        });
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(pager.getCurrentItem() - 1);
            }
        });

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                String msg = getString(R.string.slash, String.valueOf(position + 1), String.valueOf(testResults.length));
                Spannable spannableText = new SpannableString(msg);
                spannableText.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_button_type_2)),
                        0, msg.indexOf(" "), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableText.setSpan(new RelativeSizeSpan(1.5f), 0, msg.indexOf(" "), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvProgress.setText(spannableText);

                if (position == 0) {
                    btnPrev.setVisibility(View.INVISIBLE);
                    btnNext.setText(R.string.next_caption);
                    btnNext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pager.setCurrentItem(pager.getCurrentItem() + 1);
                        }
                    });
                } else if (position == (testResults.length - 1)) {
                    btnPrev.setVisibility(View.VISIBLE);
                    btnNext.setText(R.string.title_end_test_results_btn);
                    btnNext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    });
                } else {
                    btnPrev.setVisibility(View.VISIBLE);
                    btnNext.setText(R.string.next_caption);
                    btnNext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pager.setCurrentItem(pager.getCurrentItem() + 1);
                        }
                    });
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private class TestFragmentPagerAdapter extends FragmentStatePagerAdapter {

        public TestFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ViewCompletedTestFragment.newInstance(position, testId);
        }

        @Override
        public int getCount() {
            return testResults.length;
        }
    }
}
