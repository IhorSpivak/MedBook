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
import ua.profarma.medbook.recyclerviews.base.SimpleDividerItemDecoration;
import ua.profarma.medbook.recyclerviews.videos.VideoRecyclerItem;
import ua.profarma.medbook.recyclerviews.videos.VideosRecyclerView;

public class PageVideosFragment extends ViewPagerFragment implements EventListener {

    public VideosRecyclerView list;

    public static PageVideosFragment newInstance(String title) {
        PageVideosFragment fragment = new PageVideosFragment();
        fragment.title = title;
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_page_videos, container, false);
        list = rootView.findViewById(R.id.fragment_page_videos_list);
        list.init();
        list.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
        if (Core.get().UserContentControl().getSelectedMaterial() != null && Core.get().UserContentControl().getSelectedMaterial().videos.length > 0)
            for (int i = 0; i < Core.get().UserContentControl().getSelectedMaterial().videos.length; i++) {
                list.itemAdd(new VideoRecyclerItem(Core.get().UserContentControl().getSelectedMaterial().videos[i]));
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

    }
}
