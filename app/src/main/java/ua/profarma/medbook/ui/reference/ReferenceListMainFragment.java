package ua.profarma.medbook.ui.reference;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ua.profarma.medbook.R;
import ua.profarma.medbook.models.response.ReferenceItem;
import ua.profarma.medbook.singltones.SingletoneForListReference;
import ua.profarma.medbook.singltones.SingltonForPatterns;
import ua.profarma.medbook.ui.custom_views.MedBookFragment;


public class ReferenceListMainFragment extends MedBookFragment implements ReferenceContract.View  {

    @BindView(R.id.tab_bar)
    TabLayout tab_bar;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.pb)
    ProgressBar pb;

    private ReferenceContract.Presenter presenter;


    public static ReferenceListMainFragment newInstance() {
        ReferenceListMainFragment fragment = new ReferenceListMainFragment();
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter = new ReferencePresenter(this, getContext());
        presenter.loadLitReference();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_reference, container, false);

        ButterKnife.bind(this, rootView);

        return rootView;
    }


    @OnClick(R.id.fab)
    void onStartReferenceActivity() {
        Intent intent;
        intent = new Intent(getActivity(), ReferenceActivity.class);
        startActivity(intent);
    }

    @Override
    public void onReferenceListLoaded(List<ReferenceItem> list) {
        ArrayList<String> titles = new ArrayList<>();
        ArrayList<Fragment> fragments = new ArrayList<>();
        ArrayList<ReferenceItem> listItemsPatterns = new ArrayList<>();
        ArrayList<ReferenceItem> listItemsReferences = new ArrayList<>();


        for(int i = 0; i < list.size(); i++) {
            if(list.get(i).getTemplate().equals(0)){
                listItemsPatterns.add(list.get(i));
            } else {
                listItemsReferences.add(list.get(i));
            }
        }

        SingltonForPatterns.getInstance().setList(listItemsPatterns);
        SingletoneForListReference.getInstance().setList(listItemsReferences);

        titles.add(getString(R.string.reference_list));
        titles.add(getString(R.string.patterns));

        fragments.add(ReferenceListFragment.newInstance());
        fragments.add(PatternListReferenceFragment.newInstance());

        PagerFragmentAdapter adapter = new PagerFragmentAdapter(getChildFragmentManager());
        adapter.setFragments(fragments, titles);
        viewpager.setAdapter(adapter);

        tab_bar.setupWithViewPager(viewpager);
        TabLayout.Tab tab = tab_bar.getTabAt(1);
        tab.select();
        pb.setVisibility(View.GONE);
    }

    @Override
    protected void onLocalizationUpdate() {

    }
}
