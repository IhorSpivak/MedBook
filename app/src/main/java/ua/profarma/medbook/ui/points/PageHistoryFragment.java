package ua.profarma.medbook.ui.points;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ua.profarma.medbook.Core;
import ua.profarma.medbook.R;
import ua.profarma.medbook.events.points.EventLoadTransactionHistory;
import ua.profarma.medbook.events.core.Event;
import ua.profarma.medbook.recyclerviews.base.RecyclerItems;
import ua.profarma.medbook.recyclerviews.history.HistoryExchangeRecyclerItem;
import ua.profarma.medbook.recyclerviews.history.HistoryExchangeRecyclerView;
import ua.profarma.medbook.types.points.HistoryExchange;
import ua.profarma.medbook.ui.custom_views.MedBookFragment;

public class PageHistoryFragment extends MedBookFragment {

    private HistoryExchangeRecyclerView list;
    private RecyclerItems items;
    public TextView tvListEmpty;

    public static Fragment newInstance() {
        PageHistoryFragment pageHistoryFragment = new PageHistoryFragment();
        return pageHistoryFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page_history, container, false);
        list = rootView.findViewById(R.id.fragment_page_history_list);
        tvListEmpty = rootView.findViewById(R.id.fragment_page_history_list_empty);
        tvListEmpty.setText(Core.get().LocalizationControl().getText(R.id.fragment_page_history_list_empty));
        list.init();
        list.setVisibility(View.GONE);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Core.get().PointControl().getTransactionHistoryAll();
    }

    @Override
    protected void onLocalizationUpdate() {
        tvListEmpty.setText(Core.get().LocalizationControl().getText(R.id.fragment_page_history_list_empty));
    }

    @Override
    public void onEvent(Event event) {
        super.onEvent(event);
        switch (event.getEventId()) {
            case Event.EVENT_LOAD_TRANSACTION_HISTORY:
                if (items == null)
                    items = new RecyclerItems();

                EventLoadTransactionHistory eventLoadTransactionHistory = (EventLoadTransactionHistory) event;

                if (eventLoadTransactionHistory.getItems().length > 0) {
                    for (HistoryExchange item : eventLoadTransactionHistory.getItems()) {
                        items.add(new HistoryExchangeRecyclerItem(item));
                    }
                    list.itemsAdd(items);
                    list.setVisibility(View.VISIBLE);
                    tvListEmpty.setVisibility(View.GONE);
                }
                else {
                    list.setVisibility(View.GONE);
                    tvListEmpty.setVisibility(View.VISIBLE);
                }
                break;
        }
    }


}
