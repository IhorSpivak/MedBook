package mobi.medbook.android.recyclerviews.visits_second_member;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;

import mobi.medbook.android.recyclerviews.base.BaseViewHolder;
import mobi.medbook.android.recyclerviews.base.RecyclerItem;
import mobi.medbook.android.types.visits.UserRelation;
import mobi.medbook.android.ui.calendar.IOnSelectMemberVisit;

public class SecondMemberVisitRecyclerItem extends RecyclerItem implements View.OnClickListener {

    private UserRelation userRelation;

    public SecondMemberVisitRecyclerItem(UserRelation userRelation) {
        this.userRelation = userRelation;
    }

    @Override
    public void fill(BaseViewHolder holder) {
        if (holder instanceof SecondMemberVisitViewHolder) {
            ((SecondMemberVisitViewHolder) holder).init(this, userRelation);
        }
    }

    @Override
    public int getViewType() {
        return 0;
    }

    @Override
    public void onClick(View view) {
        if (getActivity(view.getContext()) != null)
            getActivity(view.getContext()).onSelectMemberVisit(userRelation);
    }

    private IOnSelectMemberVisit getActivity(Context context) {
        while (context instanceof ContextWrapper) {
            if (context instanceof IOnSelectMemberVisit) {
                return (IOnSelectMemberVisit) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    public UserRelation getUserRelation() {
        return userRelation;
    }
}
