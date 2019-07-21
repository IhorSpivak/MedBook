package ua.profarma.medbook.ui.materials;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import com.google.android.material.textfield.TextInputLayout;

import ua.profarma.medbook.Core;
import ua.profarma.medbook.R;
import ua.profarma.medbook.events.core.Event;
import ua.profarma.medbook.events.core.EventRouter;
import ua.profarma.medbook.events.materials.EventOnSingleChoice;
import ua.profarma.medbook.recyclerviews.base.RecyclerItems;
import ua.profarma.medbook.recyclerviews.tests_choice.tests_multiple_choice.TestAnswerMultipleChoiceRecyclerItem;
import ua.profarma.medbook.recyclerviews.tests_choice.tests_multiple_choice.TestAnswerMultipleChoiceRecyclerView;
import ua.profarma.medbook.recyclerviews.tests_choice.tests_single_choice.TestAnswerSingleChoiceRecyclerItem;
import ua.profarma.medbook.recyclerviews.tests_choice.tests_single_choice.TestAnswerSingleChoiceRecyclerView;
import ua.profarma.medbook.types.materials.Material;
import ua.profarma.medbook.types.materials.Question;
import ua.profarma.medbook.types.materials.Test;
import ua.profarma.medbook.ui.custom_views.DoneOnEditorActionListener;
import ua.profarma.medbook.ui.custom_views.MedBookFragment;
import ua.profarma.medbook.utils.LogUtils;

public class TestFragment extends MedBookFragment  {

    private static String KEY_BUNDLE_TEST_ID_MATERIAL = "KEY_BUNDLE_TEST_ID_MATERIAL";
    private static String KEY_BUNDLE_TEST_ID = "KEY_BUNDLE_TEST_ID";
    private static String KEY_BUNDLE_POSITION = "KEY_BUNDLE_POSITION";
    private static String KEY_BUNDLE_SELECTED = "KEY_BUNDLE_SELECTED";
    private static String KEY_BUNDLE_TEXT = "KEY_BUNDLE_TEXT";

    private Question[] questions;
    private TestAnswerSingleChoiceRecyclerView listSingleChoice;
    private TestAnswerMultipleChoiceRecyclerView listMultipleChoice;

    private RecyclerItems singleChoiceRecyclerItems = new RecyclerItems();

    private TextInputLayout tilQuestion_type_3;
    private AppCompatEditText tietQuestion_type_3;
    private int testId;
    private int materialId;
    private int position;
    private int[] selected;
    private String text;

    public static TestFragment newInstance(int materialId, int testId, int position, int[] selected, String text) {
        TestFragment fragment = new TestFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_BUNDLE_TEST_ID_MATERIAL, materialId);
        bundle.putInt(KEY_BUNDLE_TEST_ID, testId);
        bundle.putInt(KEY_BUNDLE_POSITION, position);
        bundle.putIntArray(KEY_BUNDLE_SELECTED, selected);
        bundle.putString(KEY_BUNDLE_TEXT, text);

        fragment.setArguments(bundle);
        return fragment;
    }

    /*есть три типа вопросов
     * 1 один ответ правильный
     * 2 несколько ответов правильные
     * 3 свой ответ
     * ответы отправляются все сразу
     * POST {{host}}/api/v1/test-result/batch-save
     * [
     * {
     *   "user_test_content_id": 1,
     *   "test_translation_question_id": 1,
     *	"test_translation_question_answer_id": 1,
     *	"product_id": 1,
     *	"open_answer_text": null
     * },
     * {
     *	"user_test_content_id": 1,
     *	"test_translation_question_id": 2,
     *	"test_translation_question_answer_id": 2,
     *	"product_id": 1,
     *	"open_answer_text": null
     * } .....
     * ]
     * если тип вопроса 2 , создается несколько одинаковых лементов массива JSON
     * с разными test_translation_question_answer_id
     * */

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_test, container, false);
        listSingleChoice = rootView.findViewById(R.id.fragment_test_list_single_choice);
        listMultipleChoice = rootView.findViewById(R.id.fragment_test_list_multiple_choice);
        tilQuestion_type_3 = rootView.findViewById(R.id.fragment_test_type_3_til);
        tietQuestion_type_3 = rootView.findViewById(R.id.fragment_test_type_3_tiet);

        tietQuestion_type_3.setOnEditorActionListener(new DoneOnEditorActionListener());

        materialId = getArguments().getInt(KEY_BUNDLE_TEST_ID_MATERIAL);
        testId = getArguments().getInt(KEY_BUNDLE_TEST_ID);
        position = getArguments().getInt(KEY_BUNDLE_POSITION);
        selected = getArguments().getIntArray(KEY_BUNDLE_SELECTED);
        text = getArguments().getString(KEY_BUNDLE_TEXT);

        LinearLayout llTests = rootView.findViewById(R.id.fragment_test_ll);
        TextView tvTitle = rootView.findViewById(R.id.fragment_test_tv_title);


        LogUtils.logD("hjghjghjghj", "materialId = " + materialId);
        LogUtils.logD("hjghjghjghj", "testId = " + testId);
        for (Material itemMaterial : Core.get().UserContentControl().getListMaterial()) {
            if (itemMaterial.id == materialId)
                for (Test itemTest : itemMaterial.tests) {
                    if (itemTest.id == testId) {
                        questions = itemTest.test.questions;
                        LogUtils.logD("hjghjghjghj", "UPPA");
                    }
                }
        }
        LogUtils.logD("hjghjghjghj", "question_type_id = " + questions[position].question_type_id);
        LogUtils.logD("hjghjghjghj", questions[position].translations[0].title);

        for (int i = 0; i < selected.length; i++) {
            LogUtils.logD("hjhgfyh64ghjghjghj", "* * * selected = " + selected[i]);
        }
        switch (questions[position].question_type_id) {
            case 1:
                tilQuestion_type_3.setVisibility(View.GONE);
                listSingleChoice.setVisibility(View.VISIBLE);
                listMultipleChoice.setVisibility(View.GONE);
                //listSingleChoice.clear();
                singleChoiceRecyclerItems.clear();
                listSingleChoice.init();
                for (int j = 0; j < questions[position].answers.length; j++) {
                    TestAnswerSingleChoiceRecyclerItem item = new TestAnswerSingleChoiceRecyclerItem(questions[position].answers[j], questions[position].id, questions[position].question_type_id);
                    if (selected != null && selected.length > 0)
                        for (int k = 0; k < selected.length; k++) {
                            if (selected[k] == item.getAnswerChecked().id) {
                                item.setSelected(true);
                            }
                        }
                    singleChoiceRecyclerItems.add(item);

                    LogUtils.logD("hjhgfyh64ghjghjghj", questions[position].answers[j].translations[0].title);
                    LogUtils.logD("hjhgfyh64ghjghjghj", "id = " + questions[position].answers[j].id);
                    LogUtils.logD("hjhgfyh64ghjghjghj", "is_true = " + questions[position].answers[j].is_true);
                    LogUtils.logD("hjhgfyh64ghjghjghj", "test_id = " + questions[position].test_id);
                    LogUtils.logD("hjhgfyh64ghjghjghj", "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                }
                listSingleChoice.itemsAdd(singleChoiceRecyclerItems);
                break;
            case 2:
                tilQuestion_type_3.setVisibility(View.GONE);
                listSingleChoice.setVisibility(View.GONE);
                listMultipleChoice.setVisibility(View.VISIBLE);
                listMultipleChoice.init();
                for (int j = 0; j < questions[position].answers.length; j++) {

                    TestAnswerMultipleChoiceRecyclerItem item = new TestAnswerMultipleChoiceRecyclerItem(questions[position].answers[j], questions[position].id, questions[position].question_type_id);
                    if (selected != null && selected.length > 0)
                        for (int k = 0; k < selected.length; k++) {
                            if (selected[k] == item.getAnswerChecked().id) {
                                item.setSelected(true);
                            }
                        }

                    listMultipleChoice.itemAdd(item);

                    LogUtils.logD("hjhgfyh64ghjghjghj", questions[position].answers[j].translations[0].title);
                    LogUtils.logD("hjhgfyh64ghjghjghj", "id = " + questions[position].answers[j].id);
                    LogUtils.logD("hjhgfyh64ghjghjghj", "is_true = " + questions[position].answers[j].is_true);
                    LogUtils.logD("hjhgfyh64ghjghjghj", "test_id = " + questions[position].test_id);
                    LogUtils.logD("hjhgfyh64ghjghjghj", "===================================================");
                }
                break;
            case 3:
                tilQuestion_type_3.setVisibility(View.VISIBLE);
                listSingleChoice.setVisibility(View.GONE);
                listMultipleChoice.setVisibility(View.GONE);
                if (text != null && !text.isEmpty()) {
                    tietQuestion_type_3.setText(text);
                }
                tietQuestion_type_3.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        ((OnUpdateResultTest) rootView.getContext()).addResult(questions[position].question_type_id, questions[position].id, -1, s.toString());
                    }
                });
                break;
        }


        //textView.setText(testId + ", " + position);
        tvTitle.setText(questions[position].translations[0].title);
        EventRouter.register(this);
        return rootView;
    }

    @Override
    public void onDestroy() {
        EventRouter.unregister(this);
        super.onDestroy();
    }

    @Override
    public void onEvent(Event event) {
        super.onEvent(event);
        if (event.getEventId() == Event.EVENT_MATERIAL_TEST_SINGLE_CHOICE) {
            EventOnSingleChoice eventOnSingleChoice = (EventOnSingleChoice) event;
            if (eventOnSingleChoice.getQuestionId() == questions[position].id) {
                LogUtils.logD("yhftghtyhgj65uyjh", "testId = " + testId + ", position = " + position + " OPA " + ((EventOnSingleChoice) event).getId());
                for (int i = 0; i < singleChoiceRecyclerItems.size(); i++) {
                    if (((TestAnswerSingleChoiceRecyclerItem) singleChoiceRecyclerItems.get(i)).getAnswerChecked().selected &&
                            ((TestAnswerSingleChoiceRecyclerItem) singleChoiceRecyclerItems.get(i)).getAnswerChecked().id != eventOnSingleChoice.getId()) {
                        ((TestAnswerSingleChoiceRecyclerItem) singleChoiceRecyclerItems.get(i)).setSelected(false);
                        listSingleChoice.itemUpdate(i);
                    }
                }
            }
        }
    }

    @Override
    protected void onLocalizationUpdate() {

    }
}
