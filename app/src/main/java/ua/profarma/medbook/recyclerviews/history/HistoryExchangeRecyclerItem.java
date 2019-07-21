package ua.profarma.medbook.recyclerviews.history;

import android.content.Intent;
import android.view.View;

import ua.profarma.medbook.Core;
import ua.profarma.medbook.recyclerviews.base.BaseViewHolder;
import ua.profarma.medbook.recyclerviews.base.RecyclerItem;
import ua.profarma.medbook.types.TaskMaterial;
import ua.profarma.medbook.types.materials.Material;
import ua.profarma.medbook.types.materials.Video;
import ua.profarma.medbook.types.points.HistoryExchange;
import ua.profarma.medbook.ui.materials.PresentationActivity;
import ua.profarma.medbook.ui.materials.TestsActivity;
import ua.profarma.medbook.ui.materials.VideoActivity;
import ua.profarma.medbook.utils.AppUtils;
import ua.profarma.medbook.utils.LogUtils;

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
