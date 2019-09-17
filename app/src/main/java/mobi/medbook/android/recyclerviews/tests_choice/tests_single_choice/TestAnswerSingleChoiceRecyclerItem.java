package mobi.medbook.android.recyclerviews.tests_choice.tests_single_choice;

import android.view.View;
import android.view.ViewParent;

import mobi.medbook.android.events.core.EventRouter;
import mobi.medbook.android.events.materials.EventOnSingleChoice;
import mobi.medbook.android.recyclerviews.base.BaseViewHolder;
import mobi.medbook.android.recyclerviews.base.RecyclerItem;
import mobi.medbook.android.recyclerviews.tests_choice.TestAnswerChecked;
import mobi.medbook.android.types.materials.Answer;
import mobi.medbook.android.ui.materials.OnUpdateResultTest;
import mobi.medbook.android.utils.LogUtils;


public class TestAnswerSingleChoiceRecyclerItem extends RecyclerItem implements View.OnClickListener {

    private TestAnswerChecked answerChecked;

    public TestAnswerChecked getAnswerChecked() {
        return answerChecked;
    }

    public void setSelected(boolean flag){
        answerChecked.selected = flag;
    }
    public TestAnswerSingleChoiceRecyclerItem(Answer answer, int questionId, int questionTypeId) {
        answerChecked = new TestAnswerChecked(answer, questionId, questionTypeId);
    }

    @Override
    public void fill(BaseViewHolder holder) {
        if (holder instanceof TestAnswerSingleChoiceViewHolder) {
            ((TestAnswerSingleChoiceViewHolder) holder).init(this, answerChecked);
        }
    }

    @Override
    public int getViewType() {
        return 0;
    }

    @Override
    public void onClick(View view) {
        LogUtils.logD("yhftghtyhgj65uyjh", "onClick");
        if (!answerChecked.selected) {
            answerChecked.selected = true;
            EventRouter.send(new EventOnSingleChoice(answerChecked.questionId, answerChecked.id));
            if(view.getContext() instanceof OnUpdateResultTest){
                ((OnUpdateResultTest)view.getContext()).addResult(answerChecked.questionTypeId, answerChecked.test_question_id, answerChecked.id,  null);
            }
        }

        ViewParent parent = view.getParent();
        LogUtils.logD("TestAnswerSingleChoiceRecyclerItem", "parent = " + parent.getClass().getName());
        if(parent instanceof TestAnswerSingleChoiceRecyclerView)
        {
            ((TestAnswerSingleChoiceRecyclerView)parent).itemUpdate(this);
        } else
            while(!(parent instanceof TestAnswerSingleChoiceRecyclerView))
            {
                LogUtils.logD("TestAnswerSingleChoiceRecyclerItem", "while ::: parent = " + parent.getClass().getName());
                parent = parent.getParent();
                if(parent instanceof TestAnswerSingleChoiceRecyclerView)
                {
                    ((TestAnswerSingleChoiceRecyclerView)parent).itemUpdate(this);
                    break;
                }
            }
    }
}
