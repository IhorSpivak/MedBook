package mobi.medbook.android.recyclerviews.visits;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;

import mobi.medbook.android.recyclerviews.base.BaseViewHolder;
import mobi.medbook.android.recyclerviews.base.RecyclerItem;
import mobi.medbook.android.types.visits.UserVisit;
import mobi.medbook.android.ui.calendar.IOnVisit;

public class VisitRecyclerItem extends RecyclerItem implements View.OnClickListener {

    private UserVisit userVisit;
    private long today;

    public UserVisit getUserVisit() {
        return userVisit;
    }

    public VisitRecyclerItem(UserVisit userVisit, long today) {
        this.userVisit = userVisit;
        this.today = today;
    }

    @Override
    public void fill(BaseViewHolder holder) {
        if (holder instanceof VisitViewHolder) {
            ((VisitViewHolder) holder).init(this, userVisit, today);
        }
    }

    @Override
    public int getViewType() {
        return 0;
    }

    @Override
    public void onClick(View view) {
        if (getActivity(view.getContext()) != null)
            getActivity(view.getContext()).onSelectVisit(userVisit.id);
    }

    private IOnVisit getActivity(Context context) {
        while (context instanceof ContextWrapper) {
            if (context instanceof IOnVisit) {
                return (IOnVisit) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

}
