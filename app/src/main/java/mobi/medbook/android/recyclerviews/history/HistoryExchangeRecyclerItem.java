package mobi.medbook.android.recyclerviews.history;

import mobi.medbook.android.recyclerviews.base.BaseViewHolder;
import mobi.medbook.android.recyclerviews.base.RecyclerItem;
import mobi.medbook.android.types.points.HistoryExchange;


public class HistoryExchangeRecyclerItem extends RecyclerItem {

    private HistoryExchange historyExchange;

    public HistoryExchangeRecyclerItem(HistoryExchange historyExchange) {
        this.historyExchange = historyExchange;
    }

    @Override
    public void fill(BaseViewHolder holder) {
        if (holder instanceof HistoryExchangeViewHolder) {
            ((HistoryExchangeViewHolder) holder).init(historyExchange);
        }
    }
    @Override
    public int getViewType() {
        return 0;
    }
}
