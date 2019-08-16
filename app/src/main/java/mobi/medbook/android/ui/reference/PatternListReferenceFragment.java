package mobi.medbook.android.ui.reference;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mobi.medbook.android.R;
import mobi.medbook.android.models.response.ReferenceItem;
import mobi.medbook.android.singltones.SingletoneForListReference;


public class PatternListReferenceFragment extends Fragment {

    private List<ReferenceItem> list = null;

    private ReferenceListAdapter referenceListAdapter;
    private GridLayoutManager mGridLayoutManager;

    @BindView(R.id.rv)
    RecyclerView rv;


    public static PatternListReferenceFragment newInstance() {
        PatternListReferenceFragment fragment = new PatternListReferenceFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_reference_list_patterns, container, false);
        ButterKnife.bind(this, v);


        list = SingletoneForListReference.getInstance().getList();

        rv.setHasFixedSize(true);
        mGridLayoutManager = new GridLayoutManager(getActivity(), 1);
        rv.setLayoutManager(mGridLayoutManager);

        referenceListAdapter = new ReferenceListAdapter();
        referenceListAdapter.setItemClickListener(new ReferenceListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ReferenceItem item) {
                Intent intent;
                intent = new Intent(getActivity(), ReferenceActivity.class);
                Gson gson = new Gson();
                String myJson = gson.toJson(item);
                intent.putExtra("myjson", myJson);
                startActivity(intent);
            }
        });
        referenceListAdapter.addAll(list);
        rv.setAdapter(referenceListAdapter);


        return v;
    }
}
