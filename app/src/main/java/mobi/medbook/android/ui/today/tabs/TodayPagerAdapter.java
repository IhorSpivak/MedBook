package mobi.medbook.android.ui.today.tabs;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class TodayPagerAdapter extends FragmentStatePagerAdapter {
    protected final int FRAGMENTS_COUNT = 3;
    private String[] titles;

    public TodayPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return FRAGMENTS_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:

                return PageNewsFragment.newInstance();
            case 1:
                return PageClinicalCasesFragment.newInstance();
            case 2:
                return PageCalendarEventsFragment.newInstance();

        }
        return null;
    }
}
