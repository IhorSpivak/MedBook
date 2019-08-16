package mobi.medbook.android.recyclerviews.tests_choice.tests_single_choice;

import android.view.View;
import android.widget.RadioButton;

import mobi.medbook.android.R;
import mobi.medbook.android.recyclerviews.base.BaseViewHolder;
import mobi.medbook.android.recyclerviews.tests_choice.TestAnswerChecked;


public class TestAnswerSingleChoiceViewHolder extends BaseViewHolder {

    private RadioButton rootView;

    public TestAnswerSingleChoiceViewHolder(View itemView) {
        super(itemView);
        rootView = itemView.findViewById(R.id.item_test_single_choice_root);
    }

    public void init(TestAnswerSingleChoiceRecyclerItem owner, TestAnswerChecked answer) {
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
