package ua.profarma.medbook.ui.reference;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import org.parceler.Parcels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ua.profarma.medbook.R;
import ua.profarma.medbook.models.response.ReferenceItem;
import ua.profarma.medbook.singltones.SingltonForPatterns;

public class ReferenceListFragment  extends Fragment {

    private List<ReferenceItem> list = null;


    @BindView(R.id.rv)
    RecyclerView rv;




    private PatternReferenceListAdapter patternReferenceListAdapter;
    private GridLayoutManager mGridLayoutManager;


    public static ReferenceListFragment newInstance() {
        Bundle args = new Bundle();
        ReferenceListFragment fragment = new ReferenceListFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_reference_list_patterns1, container, false);
        ButterKnife.bind(this, v);


        list = SingltonForPatterns.getInstance().getList();

        rv.setHasFixedSize(true);
        mGridLayoutManager = new GridLayoutManager(getActivity(), 1);
        rv.setLayoutManager(mGridLayoutManager);

        patternReferenceListAdapter = new PatternReferenceListAdapter();
        patternReferenceListAdapter.setItemClickListener(new PatternReferenceListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ReferenceItem item) {
//                ReferenceWebViewActivity.startActivity(getActivity(), item.getId());
                Intent intent;
                intent = new Intent(getActivity(), ReferenceReviewActivity.class);
                Gson gson = new Gson();
                String myJson = gson.toJson(item);
                intent.putExtra("myjson", myJson);
                startActivity(intent);

            }
        });
        patternReferenceListAdapter.addAll(list);
        rv.setAdapter(patternReferenceListAdapter);


        return v;
    }
}
