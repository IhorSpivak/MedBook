package mobi.medbook.android.ui.prescriptions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import mobi.medbook.android.R;


public class PrescriptionsFragment extends Fragment {

    public static PrescriptionsFragment newInstance() {
        PrescriptionsFragment fragment = new PrescriptionsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_prescriptions, container, false);

        return rootView;
    }

}
