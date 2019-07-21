package ua.profarma.medbook.recyclerviews.tests_choice.tests_multiple_choice;

import android.view.View;
import android.view.ViewParent;

import ua.profarma.medbook.recyclerviews.base.BaseViewHolder;
import ua.profarma.medbook.recyclerviews.base.RecyclerItem;
import ua.profarma.medbook.recyclerviews.tests_choice.TestAnswerChecked;
import ua.profarma.medbook.recyclerviews.tests_choice.tests_single_choice.TestAnswerSingleChoiceRecyclerView;
import ua.profarma.medbook.types.materials.Answer;
import ua.profarma.medbook.ui.materials.OnUpdateResultTest;
import ua.profarma.medbook.utils.LogUtils;

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
