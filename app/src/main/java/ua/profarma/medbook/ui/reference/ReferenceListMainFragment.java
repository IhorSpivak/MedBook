package ua.profarma.medbook.ui.reference;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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


public class ReferenceListMainFragment extends Fragment implements ReferenceContract.View  {

    @BindView(R.id.tab_bar)
    TabLayout tab_bar;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    private ReferenceContract.Presenter presenter;


    public static ReferenceListMainFragment newInstance() {
        ReferenceListMainFragment fragment = new ReferenceListMainFragment();
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_reference, container, false);

        ButterKnife.bind(this, rootView);


        presenter = new ReferencePresenter(this, getContext());
        presenter.loadLitReference();



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
        List<String> titles = new ArrayList<>();
        List<Fragment> fragments = new ArrayList<>();
        List<ReferenceItem> listItemsPatterns = new ArrayList<>();
        List<ReferenceItem> listItemsReferences = new ArrayList<>();


        titles.add(getString(R.string.reference_list));
        titles.add(getString(R.string.patterns));


        for(int i = 0; i < list.size(); i++) {
            if(list.get(i).getTemplate().equals(0)){
                listItemsPatterns.add(list.get(i));
            } else {
                listItemsReferences.add(list.get(i));
            }
        }

        WrapperReference wrapperReferencePatterns = new WrapperReference();
        wrapperReferencePatterns.setList(listItemsPatterns);

        WrapperReference wrapperReferenceList = new WrapperReference();
        wrapperReferenceList.setList(listItemsReferences);



        fragments.add(PatternListReferenceFragment.newInstance(wrapperReferencePatterns));
        fragments.add(ReferenceListFragment.newInstance(wrapperReferenceList));




        PagerFragmentAdapter adapter = new PagerFragmentAdapter(getFragmentManager());
        adapter.setFragments(fragments, titles);
        viewpager.setAdapter(adapter);

        tab_bar.setupWithViewPager(viewpager);
        TabLayout.Tab tab = tab_bar.getTabAt(1);
        tab.select();
    }
}
