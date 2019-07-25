package ua.profarma.medbook.ui.reference;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.parceler.Parcels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import ua.profarma.medbook.R;
import ua.profarma.medbook.models.response.ReferenceItem;

public class ReferenceListFragment  extends Fragment {

    public static final String EXTRA_LIST = "extra_list";
    private List<ReferenceItem> list = null;
    private WrapperReference wrapperReference;

    @BindView(R.id.rv)
    RecyclerView rv;


    private PatternReferenceListAdapter patternReferenceListAdapter;
    private GridLayoutManager mGridLayoutManager;


    public static ReferenceListFragment newInstance(WrapperReference wrapperReference) {
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_LIST,Parcels.wrap(wrapperReference));
        ReferenceListFragment fragment = new ReferenceListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_reference_list_patterns, container, false);


        wrapperReference = Parcels.unwrap(getArguments().getParcelable(EXTRA_LIST));

        list = wrapperReference.getList();
        rv.setHasFixedSize(false);
        patternReferenceListAdapter = new PatternReferenceListAdapter();
        patternReferenceListAdapter.addAll(list);
        rv.setAdapter(patternReferenceListAdapter);

        return rootView;
    }
}
