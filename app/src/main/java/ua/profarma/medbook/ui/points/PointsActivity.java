package ua.profarma.medbook.ui.points;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import ua.profarma.medbook.Core;
import ua.profarma.medbook.R;
import ua.profarma.medbook.events.core.Event;
import ua.profarma.medbook.ui.custom_views.CustomTab;
import ua.profarma.medbook.ui.custom_views.MedBookActivity;

public class PointsActivity extends MedBookActivity {

    private TextView mTvTitle;
    private ImageView mImvClose;
    private ImageView mImvFilter;
    private int mSelectedTab = -1;
    private CustomTab viewTab1;
    private CustomTab viewTab2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points);

        mTvTitle = findViewById(R.id.activity_points_title);
        mImvClose = findViewById(R.id.activity_points_close);
        mImvFilter = findViewById(R.id.activity_points_filter);

        mImvClose.setOnClickListener(view -> finish());
        mImvFilter.setVisibility(View.GONE);
        mImvFilter.setOnClickListener(view -> {
            //TODO FILTER
        });

        ViewPager viewPager = findViewById(R.id.activity_points_viewpager);
        viewPager.setOffscreenPageLimit(1);
        SampleFragmentPagerAdapter adapter = new SampleFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);

        TabLayout tabLayout = findViewById(R.id.activity_points_sliding_tabs);

        viewTab1 = new CustomTab(getBaseContext());
        viewTab2 = new CustomTab(getBaseContext());

        tabLayout.addTab(tabLayout.newTab().setCustomView(viewTab1), 0);
        tabLayout.addTab(tabLayout.newTab().setCustomView(viewTab2), 1);
        //tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewTab1.setSelect();


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition(), true);
                mSelectedTab = tab.getPosition();
                switch (mSelectedTab) {
                    case 0:
                        viewTab1.setSelect();
                        viewTab2.setUnSelect();
                        mImvFilter.setVisibility(View.GONE);
                        break;
                    case 1:
                        mImvFilter.setVisibility(View.VISIBLE);
                        viewTab2.setSelect();
                        viewTab1.setUnSelect();
                        break;
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        onLocalizationUpdate();
    }

    @Override
    protected void onLocalizationUpdate() {
        mTvTitle.setText(Core.get().LocalizationControl().getText(R.id.activity_points_title));

        viewTab1.setTitle(Core.get().LocalizationControl().getText(R.id.activity_points_tab1_title));
        viewTab2.setTitle(Core.get().LocalizationControl().getText(R.id.activity_points_tab2_title));
    }

    @Override
    public void onEvent(Event event) {
        super.onEvent(event);
        if(event.getEventId() == Event.EVENT_LOGOUT)
            finish();
    }

    public class SampleFragmentPagerAdapter extends FragmentStatePagerAdapter {

        protected final int FRAGMENTS_COUNT = 2;

        public SampleFragmentPagerAdapter(FragmentManager fm) {
            super(fm);

            //titles = getResources().getStringArray(R.array.tabs_materials);
        }

        @Override
        public int getCount() {
            return FRAGMENTS_COUNT;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return PageExchangeFragment.newInstance();
                case 1:
                    return PageHistoryFragment.newInstance();

            }
            return null;
        }

//        @Override
//        public CharSequence getPageTitle(int position) {
//            return titles[position];
//        }
    }

}