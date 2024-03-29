package mobi.medbook.android.ui.today.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import mobi.medbook.android.R;
import mobi.medbook.android.ui.custom_views.MedBookFragment;


public class PageCalendarEventsFragment extends MedBookFragment {

    public static PageCalendarEventsFragment newInstance() {
        PageCalendarEventsFragment fragment = new PageCalendarEventsFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_calendar_events, container, false);
        return rootView;
    }

    @Override
    protected void onLocalizationUpdate() {}
}
