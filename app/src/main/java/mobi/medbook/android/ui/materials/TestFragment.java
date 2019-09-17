package mobi.medbook.android.ui.materials;

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

import mobi.medbook.android.Core;
import mobi.medbook.android.R;
import mobi.medbook.android.events.core.Event;
import mobi.medbook.android.events.core.EventRouter;
import mobi.medbook.android.events.materials.EventOnSingleChoice;
import mobi.medbook.android.recyclerviews.base.RecyclerItems;
import mobi.medbook.android.recyclerviews.tests_choice.tests_multiple_choice.TestAnswerMultipleChoiceRecyclerItem;
import mobi.medbook.android.recyclerviews.tests_choice.tests_multiple_choice.TestAnswerMultipleChoiceRecyclerView;
import mobi.medbook.android.recyclerviews.tests_choice.tests_single_choice.TestAnswerSingleChoiceRecyclerItem;
import mobi.medbook.android.recyclerviews.tests_choice.tests_single_choice.TestAnswerSingleChoiceRecyclerView;
import mobi.medbook.android.types.materials.Material;
import mobi.medbook.android.types.materials.Question;
import mobi.medbook.android.types.materials.Test;
import mobi.medbook.android.ui.custom_views.DoneOnEditorActionListener;
import mobi.medbook.android.ui.custom_views.MedBookFragment;
import mobi.medbook.android.utils.LogUtils;


public class TestFragment extends MedBookFragment {

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


        for (Material itemMaterial : Core.get().UserContentControl().getListMaterial()) {
            if (itemMaterial.id == materialId)
                for (Test itemTest : itemMaterial.tests) {
                    if (itemTest.id == testId) {
                        questions = itemTest.test.questions;
                        LogUtils.logD("hjghjghjghj", "UPPA");
                    }
                }
        }
        for (int i = 0; i < selected.length; i++) {
            LogUtils.logD("hjhgfyh64ghjghjghj", "* * * selected = " + selected[i]);
        }
        switch (questions[position].question_type_id) {
            case 1:
                tilQuestion_type_3.setVisibility(View.GONE);
                listSingleChoice.setVisibility(View.VISIBLE);
                listMultipleChoice.setVisibility(View.GONE);
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
