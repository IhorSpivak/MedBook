package ua.profarma.medbook.recyclerviews.tests_choice.tests_multiple_choice;

import android.view.View;
import android.widget.CheckBox;

import ua.profarma.medbook.R;
import ua.profarma.medbook.recyclerviews.base.BaseViewHolder;
import ua.profarma.medbook.recyclerviews.tests_choice.TestAnswerChecked;
import ua.profarma.medbook.types.materials.Answer;

public class TestAnswerMultipleChoiceViewHolder extends BaseViewHolder {

    private CheckBox rootView;



    public TestAnswerMultipleChoiceViewHolder(View itemView) {
        super(itemView);

        rootView = itemView.findViewById(R.id.item_test_multiple_choice_root);
    }

    public void init(TestAnswerMultipleChoiceRecyclerItem owner, TestAnswerChecked answer) {
        if (rootView != null) {
            rootView.setText(answer.translations[0].title);
            rootView.setChecked(answer.selected);
            if(answer.selected){
                rootView.setBackgroundColor(rootView.getContext().getResources().getColor(R.color.ballBgColor));
            } else {
                rootView.setBackground(null);
            }
        }

        rootView.setOnClickListener(owner);
    }
}
