package ua.profarma.medbook.ui.materials;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import ua.profarma.medbook.Core;
import ua.profarma.medbook.R;
import ua.profarma.medbook.events.core.Event;
import ua.profarma.medbook.events.core.EventRouter;
import ua.profarma.medbook.events.materials.EventMaterialFragmentClose;
import ua.profarma.medbook.ui.custom_views.MedBookFragment;
import ua.profarma.medbook.ui.materials.pages.PagePresentationsFragment;
import ua.profarma.medbook.ui.materials.pages.PageTestsFragment;
import ua.profarma.medbook.ui.materials.pages.PageVideosFragment;

public class MaterialDetailsFragment extends MedBookFragment {

    public static MaterialDetailsFragment newInstance() {
        MaterialDetailsFragment fragment = new MaterialDetailsFragment();
        return fragment;
    }

    private int selectedTab = -1;
    private TabLayout tabLayout;
    private CustomTabMaterial viewTab1;
    private CustomTabMaterial viewTab2;
    private CustomTabMaterial viewTab3;

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_material, container, false);

        ImageView imvBack = rootView.findViewById(R.id.fragment_material_close);
        imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventRouter.send(new EventMaterialFragmentClose());
            }
        });

        TextView tvTitle = rootView.findViewById(R.id.fragment_material_title);
        tvTitle.setText(Core.get().UserContentControl().getSelectedMaterial().translations[0].name);

        final ViewPager viewPager = rootView.findViewById(R.id.viewpager);
        SampleFragmentPagerAdapter adapter = new SampleFragmentPagerAdapter(getChildFragmentManager());


        viewPager.setAdapter(adapter);

        tabLayout = rootView.findViewById(R.id.sliding_tabs);



        viewTab1 = (CustomTabMaterial) LayoutInflater.from(getContext()).inflate(R.layout.custom_tab_material, null, true);
        //viewTab1.setPadding(-10, 0,-10, 0);
        viewTab1.setTitle(Core.get().LocalizationControl().getText(R.id.material_tests));
        //viewTab1.setBackgroundResource(R.drawable.rectangle_rounded_8);
        if (Core.get().UserContentControl().getSelectedMaterial().count.tests == 0)
            viewTab1.setDotVivible(View.INVISIBLE);
        viewTab2 = (CustomTabMaterial) LayoutInflater.from(getContext()).inflate(R.layout.custom_tab_material, null, true);
        //viewTab2.setPadding(-10, 0,-10, 0);
        viewTab2.setTitle(Core.get().LocalizationControl().getText(R.id.material_presentations));
        if (Core.get().UserContentControl().getSelectedMaterial().count.presentations == 0)
            viewTab2.setDotVivible(View.INVISIBLE);
        viewTab3 = (CustomTabMaterial) LayoutInflater.from(getContext()).inflate(R.layout.custom_tab_material, null, true);
        //viewTab3.setPadding(-10, 0,-10, 0);
        viewTab3.setTitle(Core.get().LocalizationControl().getText(R.id.material_video));
        if (Core.get().UserContentControl().getSelectedMaterial().count.videos == 0)
            viewTab3.setDotVivible(View.INVISIBLE);
        tabLayout.addTab(tabLayout.newTab().setCustomView(viewTab1), 0);
        tabLayout.addTab(tabLayout.newTab().setCustomView(viewTab2), 1);
        tabLayout.addTab(tabLayout.newTab().setCustomView(viewTab3), 2);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        viewTab1.setSelect();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition(), true);
                selectedTab = tab.getPosition();
                switch (selectedTab) {
                    case 0:
                        viewTab1.setSelect();
                        viewTab2.setUnSelect();
                        viewTab3.setUnSelect();
                        break;
                    case 1:
                        viewTab2.setSelect();
                        viewTab1.setUnSelect();
                        viewTab3.setUnSelect();
                        break;
                    case 2:
                        viewTab3.setSelect();
                        viewTab2.setUnSelect();
                        viewTab1.setUnSelect();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (selectedTab != -1) {
            tabLayout.getTabAt(selectedTab).select();
        }
    }

    @Override
    public void onEvent(Event event) {
        super.onEvent(event);
        if (event.getEventId() == Event.EVENT_MATERIAL_SELECTED_UPDATE) {
            if (Core.get().UserContentControl().getSelectedMaterial().count.tests == 0)
                viewTab1.setDotVivible(View.INVISIBLE);
            if (Core.get().UserContentControl().getSelectedMaterial().count.presentations == 0)
                viewTab2.setDotVivible(View.INVISIBLE);
            if (Core.get().UserContentControl().getSelectedMaterial().count.videos == 0)
                viewTab3.setDotVivible(View.INVISIBLE);
        }
    }

    @Override
    protected void onLocalizationUpdate() {

    }

    public class SampleFragmentPagerAdapter extends FragmentStatePagerAdapter {

        protected final int FRAGMENTS_COUNT = 3;
        private String[] titles;

        public SampleFragmentPagerAdapter(FragmentManager fm) {
            super(fm);

            titles = getResources().getStringArray(R.array.tabs_materials);
        }

        @Override
        public int getCount() {
            return FRAGMENTS_COUNT;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return PageTestsFragment.newInstance(titles[0]);
                case 1:
                    return PagePresentationsFragment.newInstance(titles[1]);
                case 2:
                    return PageVideosFragment.newInstance(titles[2]);

            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

    private void setTabBG(int tab1, int tab2){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            ViewGroup tabStrip = (ViewGroup) tabLayout.getChildAt(0);
            View tabView1 = tabStrip.getChildAt(0);
            View tabView2 = tabStrip.getChildAt(3);
            if (tabView1 != null) {
                int paddingStart = tabView1.getPaddingStart();
                int paddingTop = tabView1.getPaddingTop();
                int paddingEnd = tabView1.getPaddingEnd();
                int paddingBottom = tabView1.getPaddingBottom();
                ViewCompat.setBackground(tabView1, AppCompatResources.getDrawable(tabView1.getContext(), tab1));
                ViewCompat.setPaddingRelative(tabView1, paddingStart, paddingTop, paddingEnd, paddingBottom);
            }

            if (tabView2 != null) {
                int paddingStart = tabView2.getPaddingStart();
                int paddingTop = tabView2.getPaddingTop();
                int paddingEnd = tabView2.getPaddingEnd();
                int paddingBottom = tabView2.getPaddingBottom();
                ViewCompat.setBackground(tabView2, AppCompatResources.getDrawable(tabView2.getContext(), tab2));
                ViewCompat.setPaddingRelative(tabView2, paddingStart, paddingTop, paddingEnd, paddingBottom);
            }
        }
    }
}
