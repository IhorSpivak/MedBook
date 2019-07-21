package ua.profarma.medbook.recyclerviews.visits_second_member;

import android.view.View;
import android.widget.TextView;

import ua.profarma.medbook.R;
import ua.profarma.medbook.recyclerviews.base.BaseViewHolder;
import ua.profarma.medbook.types.visits.UserRelation;

public class SecondMemberVisitViewHolder extends BaseViewHolder {

    private View rootView;
    private TextView tvTitle;

    public SecondMemberVisitViewHolder(View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.item_second_member_visit_title);
        rootView = itemView.findViewById(R.id.item_second_member_visit_root);
    }

    public void init(SecondMemberVisitRecyclerItem owner, UserRelation member) {
        if (tvTitle != null) {
            String lastName = "";
            String firstName = "";
            String middleName = "";
            if (member.last_name == null || member.last_name.isEmpty()) lastName = "EmptyLastName";
            else  lastName = member.last_name;
            if (member.first_name == null || member.first_name.isEmpty()) firstName = "EmptyFirstName";
            else  firstName = member.first_name;
            if (member.middle_name == null || member.middle_name.isEmpty()) middleName = "EmptyMiddleName";
            else  middleName = member.middle_name;
            tvTitle.setText(lastName + " " + firstName + " " + middleName);
        }

        rootView.setOnClickListener(owner);
    }
}
