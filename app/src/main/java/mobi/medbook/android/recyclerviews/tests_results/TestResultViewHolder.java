package mobi.medbook.android.recyclerviews.tests_results;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import mobi.medbook.android.App;
import mobi.medbook.android.R;
import mobi.medbook.android.recyclerviews.base.BaseViewHolder;


public class TestResultViewHolder extends BaseViewHolder {

    private LinearLayout rootView;
    private TextView tvTitle;
    private ImageView iconResult;

    public TestResultViewHolder(View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.item_test_result_text);
        iconResult  = itemView.findViewById(R.id.item_test_result_img);
        rootView = itemView.findViewById(R.id.item_test_result_root);
    }

    public void init(String text, TestResults state) {
        if (rootView != null) {
            tvTitle.setText(text);
            int color = 0;
            switch (state){
                case NO:
                    color = App.getAppContext().getResources().getColor(R.color.answer_open);
                    iconResult.setVisibility(View.INVISIBLE);
                    break;
                case TRUE:
                    color = App.getAppContext().getResources().getColor(R.color.answer_true);
                    iconResult.setVisibility(View.VISIBLE);
                    iconResult.setImageResource(R.drawable.ic_answer_true);
                    break;
                case FALSE:
                    color = App.getAppContext().getResources().getColor(R.color.answer_false);
                    iconResult.setVisibility(View.VISIBLE);
                    iconResult.setImageResource(R.drawable.ic_answer_false);
                    break;
                case TRUE_YOU:
                    color = App.getAppContext().getResources().getColor(R.color.answer_true_you);
                    iconResult.setVisibility(View.VISIBLE);
                    iconResult.setImageResource(R.drawable.ic_answer_true_you);
                    break;
            }
            rootView.setBackgroundColor(color);
        }
    }
}
