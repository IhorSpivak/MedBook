package ua.profarma.medbook.ui.reference;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ua.profarma.medbook.R;


public class ReferenceFragment extends Fragment {

    public static ReferenceFragment newInstance() {
        ReferenceFragment fragment = new ReferenceFragment();
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_reference, container, false);



        return rootView;
    }

}
