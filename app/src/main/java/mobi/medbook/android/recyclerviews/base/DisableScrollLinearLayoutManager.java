package mobi.medbook.android.recyclerviews.base;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;


public class DisableScrollLinearLayoutManager extends LinearLayoutManager {

    public DisableScrollLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    // it will always pass false to RecyclerView when calling "canScrollVertically()" method.
    @Override
    public boolean canScrollVertically() {
        return false;
    }
}
