package mobi.medbook.android.recyclerviews.tests_choice.tests_multiple_choice;

import android.view.View;
import android.view.ViewParent;

import mobi.medbook.android.recyclerviews.base.BaseViewHolder;
import mobi.medbook.android.recyclerviews.base.RecyclerItem;
import mobi.medbook.android.recyclerviews.tests_choice.TestAnswerChecked;
import mobi.medbook.android.recyclerviews.tests_choice.tests_single_choice.TestAnswerSingleChoiceRecyclerView;
import mobi.medbook.android.types.materials.Answer;
import mobi.medbook.android.ui.materials.OnUpdateResultTest;
import mobi.medbook.android.utils.LogUtils;


public class TestAnswerMultipleChoiceRecyclerItem extends RecyclerItem implements View.OnClickListener {

    private TestAnswerChecked answerChecked;

    public TestAnswerChecked getAnswerChecked() {
        return answerChecked;
    }

    public void setSelected(boolean flag) {
        answerChecked.selected = flag;
    }

    public TestAnswerMultipleChoiceRecyclerItem(Answer answer, int questionId, int questionTypeId) {
        answerChecked = new TestAnswerChecked(answer, questionId, questionTypeId);
    }

    @Override
    public void fill(BaseViewHolder holder) {
        if (holder instanceof TestAnswerMultipleChoiceViewHolder) {
            ((TestAnswerMultipleChoiceViewHolder) holder).init(this, answerChecked);
        }
    }

    @Override
    public int getViewType() {
        return 0;
    }

    @Override
    public void onClick(View view) {
        //EventRouter.send(new EventMaterialDescriptionStart(MaterialsEnum.TEST, answer.test_id));
        answerChecked.selected = !answerChecked.selected;
        if(view.getContext() instanceof OnUpdateResultTest) {
            if (answerChecked.selected) {
                ((OnUpdateResultTest)view.getContext()).addResult(answerChecked.questionTypeId, answerChecked.test_question_id, answerChecked.id, null);
            } else {
                ((OnUpdateResultTest)view.getContext()).removeResult(answerChecked.test_question_id, answerChecked.id);
            }
        }

        ViewParent parent = view.getParent();
        LogUtils.logD("TestAnswerMultipleChoiceRecyclerItem", "parent = " + parent.getClass().getName());
        if(parent instanceof TestAnswerMultipleChoiceRecyclerView)
        {
            ((TestAnswerMultipleChoiceRecyclerView)parent).itemUpdate(this);
        } else
            while(!(parent instanceof TestAnswerSingleChoiceRecyclerView))
            {
                LogUtils.logD("TestAnswerMultipleChoiceRecyclerItem", "while ::: parent = " + parent.getClass().getName());
                parent = parent.getParent();
                if(parent instanceof TestAnswerMultipleChoiceRecyclerView)
                {
                    ((TestAnswerMultipleChoiceRecyclerView)parent).itemUpdate(this);
                    break;
                }
            }
    }
}
