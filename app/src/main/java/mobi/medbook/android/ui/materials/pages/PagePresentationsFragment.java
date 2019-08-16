package mobi.medbook.android.ui.materials.pages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import mobi.medbook.android.Core;
import mobi.medbook.android.R;
import mobi.medbook.android.events.core.Event;
import mobi.medbook.android.events.core.EventListener;
import mobi.medbook.android.events.core.EventRouter;
import mobi.medbook.android.events.materials.EventOnSendResultPresentationFailure;
import mobi.medbook.android.events.materials.EventOnSendResultPresentationSuccess;
import mobi.medbook.android.recyclerviews.base.RecyclerItems;
import mobi.medbook.android.recyclerviews.base.SimpleDividerItemDecoration;
import mobi.medbook.android.recyclerviews.presentations.PresentationsRecyclerView;
import mobi.medbook.android.utils.AppUtils;

import ua.profarma.medbook.recyclerviews.presentations.PresentationRecyclerItem;

public class PagePresentationsFragment extends ViewPagerFragment implements EventListener {

    public static PagePresentationsFragment newInstance(String title) {
        PagePresentationsFragment fragment = new PagePresentationsFragment();
        fragment.title = title;
        return fragment;
    }

    private PresentationsRecyclerView list;
    private RecyclerItems items;

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_page_presentations, container, false);
        list = rootView.findViewById(R.id.fragment_page_presentations_list);
        list.init();
        items = new RecyclerItems();
        list.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
        if (Core.get().UserContentControl().getSelectedMaterial() != null && Core.get().UserContentControl().getSelectedMaterial().presentations.length > 0) {
            for (int i = 0; i < Core.get().UserContentControl().getSelectedMaterial().presentations.length; i++) {
                items.add(new PresentationRecyclerItem(Core.get().UserContentControl().getSelectedMaterial().presentations[i]));
            }
            list.itemsAdd(items);
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
            case Event.EVENT_MATERIAL_PRESENTATION_RESULT_SUCCESS:
                EventOnSendResultPresentationSuccess eventOnSendResultPresentationSuccess = (EventOnSendResultPresentationSuccess) event;
                for (int i = 0; i < items.size(); i++) {
                    if (((PresentationRecyclerItem) items.get(i)).getPresentation().id == eventOnSendResultPresentationSuccess.getPresentationId()) {
                        ((PresentationRecyclerItem) items.get(i)).setResultTime(eventOnSendResultPresentationSuccess.getResultTime());
                        list.itemUpdate(i);
                    }
                }
                //list.itemUpdate();
                break;
            case Event.EVENT_MATERIAL_PRESENTATION_RESULT_FAILURE:
                AppUtils.toastError(((EventOnSendResultPresentationFailure) event).getMessage(), false);
                break;
        }
    }
}
