package ua.profarma.medbook.recyclerviews.base;

import android.view.ViewGroup;

public interface HolderCreator {
    BaseViewHolder create(ViewGroup parent, int viewType);
}
