package ua.profarma.medbook.ui.materials.pages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ua.profarma.medbook.Core;
import ua.profarma.medbook.R;
import ua.profarma.medbook.events.core.Event;
import ua.profarma.medbook.events.core.EventListener;
import ua.profarma.medbook.events.core.EventRouter;
import ua.profarma.medbook.recyclerviews.base.RecyclerItems;
import ua.profarma.medbook.recyclerviews.base.SimpleDividerItemDecoration;
import ua.profarma.medbook.recyclerviews.tests.TestRecyclerItem;
import ua.profarma.medbook.recyclerviews.tests.TestsRecyclerView;

public class PageTestsFragment extends ViewPagerFragment implements EventListener {


    private TestsRecyclerView list;
    private RecyclerItems items;

    public static PageTestsFragment newInstance(String title) {
        PageTestsFragment fragment = new PageTestsFragment();
        fragment.title = title;
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_page_tests, container, false);
        list = rootView.findViewById(R.id.fragment_page_tests_list);
        list.init();
        list.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
        if (Core.get().UserContentControl().getSelectedMaterial() != null && Core.get().UserContentControl().getSelectedMaterial().tests.length > 0) {
            items = new RecyclerItems();
            for (int i = 0; i < Core.get().UserContentControl().getSelectedMaterial().tests.length; i++) {
                items.add(new TestRecyclerItem(Core.get().UserContentControl().getSelectedMaterial().tests[i]));
                list.itemAdd(new TestRecyclerItem(Core.get().UserContentControl().getSelectedMaterial().tests[i]));
            }
        }
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventRouter.register(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventRouter.unregister(this);
    }

    @Override
    public void onEvent(Event event) {

        switch (event.getEventId()) {
            case Event.EVENT_MATERIAL_SELECTED_UPDATE:
                for (int i = 0; i < items.size(); i++) {
                    if (((TestRecyclerItem) items.get(i)).getTest().test.results.length == 0) {
                        for (int j = 0; j < Core.get().UserContentControl().getSelectedMaterial().tests.length; j++) {
                            if (Core.get().UserContentControl().getSelectedMaterial().tests[j].id == ((TestRecyclerItem) items.get(i)).getTest().id &&
                                    Core.get().UserContentControl().getSelectedMaterial().tests[j].test.results.length > 0)
                                list.itemUpdate(i);
                        }
                    }
                }
                //list.itemUpdate();
                break;
        }
    }

}
