package ua.profarma.medbook.ui.reference;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.List;

import butterknife.BindView;
import ua.profarma.medbook.R;
import ua.profarma.medbook.models.response.ReferenceItem;

public class PatternListReferenceFragment extends Fragment {




    public static PatternListReferenceFragment newInstance(WrapperReference wrapperReference) {
        PatternListReferenceFragment fragment = new PatternListReferenceFragment();
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_reference_list_patterns, container, false);



        return rootView;
    }
}
