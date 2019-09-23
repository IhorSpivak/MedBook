package mobi.medbook.android.ui.calendar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class TestAncetaPagerAdapters extends FragmentStatePagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();


    public TestAncetaPagerAdapters(FragmentManager fm) {
        super(fm);
    }



    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void setFragments(List<Fragment> fragments, List<String> titles) {
        mFragmentList.addAll(fragments);
        mFragmentTitleList.addAll(titles);
        notifyDataSetChanged();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}