package mobi.medbook.android.recyclerviews.base;

import android.view.ViewGroup;

public interface HolderCreator {
    BaseViewHolder create(ViewGroup parent, int viewType);
}
