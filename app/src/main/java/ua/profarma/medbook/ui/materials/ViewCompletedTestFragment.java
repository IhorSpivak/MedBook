package ua.profarma.medbook.ui.materials;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ua.profarma.medbook.Core;
import ua.profarma.medbook.R;
import ua.profarma.medbook.recyclerviews.tests_results.TestResultRecyclerItem;
import ua.profarma.medbook.recyclerviews.tests_results.TestResultRecyclerView;
import ua.profarma.medbook.recyclerviews.tests_results.TestResults;
import ua.profarma.medbook.types.materials.Material;
import ua.profarma.medbook.types.materials.Question;
import ua.profarma.medbook.types.materials.ResultTestQuestion;
import ua.profarma.medbook.types.materials.Test;

public class ViewCompletedTestFragment extends Fragment {

    private static String KEY_BUNDLE_TEST_ID = "KEY_BUNDLE_TEST_ID";
    private static String KEY_BUNDLE_POSITION = "KEY_BUNDLE_POSITION";

    private String TAG = "AppMedbook/ViewCompletedTestFragment";

    private TestResultRecyclerView listResult;
    private TextView tvOpenQuestion;
    private LinearLayout llOpenQuestion;
    private int testId;
    private int position;


    public static ViewCompletedTestFragment newInstance(int position, int testId) {
        ViewCompletedTestFragment fragment = new ViewCompletedTestFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_BUNDLE_TEST_ID, testId);
        bundle.putInt(KEY_BUNDLE_POSITION, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_test_result, container, false);
        listResult = rootView.findViewById(R.id.fragment_test_result_list);
        tvOpenQuestion = rootView.findViewById(R.id.fragment_test_result_open_answer_text);
        llOpenQuestion = rootView.findViewById(R.id.fragment_test_result_open_answer_ll);

        testId = getArguments().getInt(KEY_BUNDLE_TEST_ID);
        position = getArguments().getInt(KEY_BUNDLE_POSITION);
        TextView tvTitle = rootView.findViewById(R.id.fragment_test_result_tv_title);
        ResultTestQuestion resultTestQuestion = null;
        Question question = null;

        if (Core.get().UserContentControl().getSelectedMaterial() == null) {
            for (Material itemM : Core.get().UserContentControl().getListMaterial()) {
                for (Test itemT : itemM.tests) {
                    if (itemT.id == testId) {
                        resultTestQuestion = itemT.test.results[position];
                        question = itemT.test.questions[position];
                    }
                }
            }
        } else
        for (Test item : Core.get().UserContentControl().getSelectedMaterial().tests) {
            if (item.id == testId) {
                resultTestQuestion = item.test.results[position];
                question = item.test.questions[position];
            }
        }
        if (resultTestQuestion != null) {

            if (resultTestQuestion.question.question_type_id == 3) {
                listResult.setVisibility(View.GONE);
                llOpenQuestion.setVisibility(View.VISIBLE);
                if (resultTestQuestion.open_answer_text != null && !resultTestQuestion.open_answer_text.isEmpty())
                    tvOpenQuestion.setText(resultTestQuestion.open_answer_text);
                else
                    tvOpenQuestion.setText("");
            } else if (resultTestQuestion.question.question_type_id == 1 || resultTestQuestion.question.question_type_id == 2) {
                listResult.setVisibility(View.VISIBLE);
                llOpenQuestion.setVisibility(View.GONE);
                listResult.init();

                for (int i = 0; i < question.answers.length; i++) {
                    TestResults state = TestResults.NO;
                    for (int j = 0; j < resultTestQuestion.answers.length; j++) {

                        if (question.answers[i].id == resultTestQuestion.answers[j].id) {
                            if (question.answers[i].is_true == 1) {
                                state = TestResults.TRUE_YOU;
                                listResult.itemAdd(new TestResultRecyclerItem(question.answers[i].translations[0].title, state));
                                break;
                            } else {
                                state = TestResults.FALSE;
                                listResult.itemAdd(new TestResultRecyclerItem(question.answers[i].translations[0].title, state));
                                break;
                            }
                        }
                    }
                    if (question.answers[i].is_true == 1 && state == TestResults.NO){
                        state = TestResults.TRUE;
                        listResult.itemAdd(new TestResultRecyclerItem(question.answers[i].translations[0].title, state));

                    }
                    if (state == TestResults.NO)
                        listResult.itemAdd(new TestResultRecyclerItem(question.answers[i].translations[0].title, state));
                }

            }

            if (resultTestQuestion.question.translations[0].title != null && !resultTestQuestion.question.translations[0].title.isEmpty())
                tvTitle.setText(resultTestQuestion.question.translations[0].title);
            else
                tvTitle.setText("");
        }

        return rootView;
    }

}
