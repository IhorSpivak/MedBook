package mobi.medbook.android.recyclerviews.visit_doctor_question;

import android.view.View;

import mobi.medbook.android.Core;
import mobi.medbook.android.recyclerviews.base.BaseViewHolder;
import mobi.medbook.android.recyclerviews.base.RecyclerItem;
import mobi.medbook.android.types.visits.QuestionVisit;
import mobi.medbook.android.ui.custom_views.VDRadioButton;

public class VisitDoctorQuestionRecyclerItem extends RecyclerItem implements View.OnClickListener {

    private QuestionVisit questionVisit;

    public VisitDoctorQuestionRecyclerItem(QuestionVisit questionVisit) {
        this.questionVisit = questionVisit;
    }

    @Override
    public void fill(BaseViewHolder holder) {
        if (holder instanceof VisitDoctorQuestionViewHolder) {
            ((VisitDoctorQuestionViewHolder) holder).init(this, questionVisit);
        }
    }

    @Override
    public int getViewType() {
        return 0;
    }

    @Override
    public void onClick(View view) {
        if(view instanceof VDRadioButton)
            Core.get().VisitsControl().setResultDoctorAnswer(((VDRadioButton)view).getCustomId());
            //AppUtils.toastMessage(((VDRadioButton)view).getCustomId().translations[0].title, true);
//        if (getActivity(view.getContext()) != null)
//            getActivity(view.getContext()).onSelectMemberVisit(questionVisit);
    }

//    private IOnSelectMemberVisit getActivity(Context context) {
//        while (context instanceof ContextWrapper) {
//            if (context instanceof IOnSelectMemberVisit) {
//                return (IOnSelectMemberVisit) context;
//            }
//            context = ((ContextWrapper) context).getBaseContext();
//        }
//        return null;
//    }

    public QuestionVisit getQuestionVisit() {
        return questionVisit;
    }
}
