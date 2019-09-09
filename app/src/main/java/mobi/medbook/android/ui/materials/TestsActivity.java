package mobi.medbook.android.ui.materials;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

import mobi.medbook.android.Core;
import mobi.medbook.android.IOnDialogInterface;
import mobi.medbook.android.R;
import mobi.medbook.android.events.core.ConcurrentArrayList;
import mobi.medbook.android.events.core.Event;
import mobi.medbook.android.events.core.EventRouter;
import mobi.medbook.android.events.materials.EventTestResultLoad;
import mobi.medbook.android.events.materials.EventUserNotificationReaction;
import mobi.medbook.android.types.RequestTest;
import mobi.medbook.android.types.materials.Material;
import mobi.medbook.android.types.materials.Question;
import mobi.medbook.android.types.materials.Test;
import mobi.medbook.android.ui.custom_views.MedBookActivity;
import mobi.medbook.android.utils.AppUtils;
import mobi.medbook.android.utils.LogUtils;
import ua.profarma.medbook.types.materials.ResultsCheckTest;


public class TestsActivity extends MedBookActivity implements OnUpdateResultTest, IOnDialogInterface {
    public static final String KEY_ID_TEST = "KEY_ID_TEST";
    public static final String KEY_ID_MATERIAL_TEST = "KEY_ID_MATERIAL_TEST";
    private Question[] questions;
    private int testId;
    private int materialId;
    private int userTestContentId;
    private int productId;
    private ProgressBar progressBar;
    private TextView tvProgress;
    private ConcurrentArrayList<RequestTest> testResults;
    private Button btnNext;
    private Button btnPrev;
    private String mTitle;
    private TextView tv_title;

    private String TAG = "AppMedbook/TestsActivity";

    private int minRecationNotification;
    private int minRecationNotificationTest;


    @Override
    protected void onDestroy() {
        EventRouter.unregister(this);
        super.onDestroy();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests);

        EventRouter.register(this);
        tv_title = findViewById(R.id.tv_title);

        ImageView imClose = findViewById(R.id.activity_tests_toolbar_close);
        imClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();
                dialogExit();
            }
        });
        testResults = new ConcurrentArrayList<>();

        if (getIntent() != null) {
            testId = getIntent().getIntExtra(KEY_ID_TEST, 0);
            materialId = getIntent().getIntExtra(KEY_ID_MATERIAL_TEST, -1);
            if (materialId == -1)
                for (Test item : Core.get().UserContentControl().getSelectedMaterial().tests) {
                    if (item.id == testId) {
                        minRecationNotificationTest = item.minimum_notification_reactions;
                        userTestContentId = item.id;
                        questions = item.test.questions;
                        productId = item.product_id;
                        TextView tvTitle = findViewById(R.id.activity_tests_toolbar_title);
                        tvTitle.setText(item.test.translations[0].title);
                        mTitle = item.test.translations[0].title;
                        tv_title.setText(item.test.translations[0].title);
                        materialId = Core.get().UserContentControl().getSelectedMaterial().id;
                    }
                }
            else
                for (Material itemMaterial : Core.get().UserContentControl().getListMaterial()) {
                    if (itemMaterial.id == materialId)
                        for (Test itemTest : itemMaterial.tests) {
                            if (itemTest.id == testId) {
                                minRecationNotificationTest = itemTest.minimum_notification_reactions;
                                userTestContentId = itemTest.id;
                                questions = itemTest.test.questions;
                                productId = itemTest.product_id;
                                TextView tvTitle = findViewById(R.id.activity_tests_toolbar_title);
                                tvTitle.setText(itemTest.test.translations[0].title);
                                mTitle = itemTest.test.translations[0].title;
                                tv_title.setText(itemTest.test.translations[0].title);
                            }
                        }
                }

        }
        ViewPager pager = findViewById(R.id.activity_tests_pager);
        TestFragmentPagerAdapter pagerAdapter = new TestFragmentPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.setOffscreenPageLimit(1);

        progressBar = findViewById(R.id.activity_tests_progressbar);
        tvProgress = findViewById(R.id.activity_tests_progress_title);
        btnNext = findViewById(R.id.activity_tests_next);
        btnPrev = findViewById(R.id.activity_tests_prev);

        btnNext.setText(R.string.next_caption);
        btnPrev.setText(R.string.prev_caption);
        btnPrev.setVisibility(View.INVISIBLE);

        if (questions.length == 1) {
            progressBar.setVisibility(View.INVISIBLE);
            tvProgress.setVisibility(View.INVISIBLE);
            btnNext.setText(R.string.title_end_test_btn);
        } else if (questions.length > 1) {
            String msg = "1 / " + questions.length;
            Spannable spannableText = new SpannableString(msg);
            spannableText.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)),
                    0, msg.indexOf(" "), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableText.setSpan(new RelativeSizeSpan(1.5f), 0, msg.indexOf(" "), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvProgress.setText(spannableText);
        }
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                LogUtils.logD("jyt78tjgh", "position/questions.length * 100 = " + (position * 100 / questions.length));
                progressBar.setProgress(Math.round(position * 100 / questions.length));
                //tvProgress.setText((position + 1) + "/" + questions.length);

                String msg = getString(R.string.slash, String.valueOf(position + 1), String.valueOf(questions.length));
                Spannable spannableText = new SpannableString(msg);
                spannableText.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)),
                        0, msg.indexOf(" "), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableText.setSpan(new RelativeSizeSpan(1.5f), 0, msg.indexOf(" "), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvProgress.setText(spannableText);
                if (position == 0) {
                    btnPrev.setVisibility(View.INVISIBLE);
                    btnNext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pager.setCurrentItem(pager.getCurrentItem() + 1);
                        }
                    });
                    if (questions.length == 1) {
                        btnNext.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (testResults != null && testResults.size() >= questions.length) {
                                    Core.get().UserContentControl().sendTestResult(testResults);
                                    //finish();
                                    //dialogResult();
                                } else
                                    AppUtils.toastError("Не усі відповіді заповнені.", true);
                            }
                        });
                    }
                } else if (position == (questions.length - 1)) {
                    btnPrev.setVisibility(View.VISIBLE);
                    btnNext.setText(R.string.title_end_test_btn);
                    btnNext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (testResults != null && testResults.size() >= questions.length) {
                                Core.get().UserContentControl().sendTestResult(testResults);
                                //dialogResult();
                                //finish();
                            } else
                                AppUtils.toastError("Не усі відповіді заповнені.", true);
                        }
                    });
                } else {
                    btnPrev.setVisibility(View.VISIBLE);
                    btnNext.setText(R.string.next_caption);
                    btnNext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pager.setCurrentItem(pager.getCurrentItem() + 1);
                        }
                    });
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(pager.getCurrentItem() + 1);
            }
        });
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(pager.getCurrentItem() - 1);
            }
        });
        if (questions.length == 1) {
            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (testResults != null && testResults.size() >= questions.length) {
                        Core.get().UserContentControl().sendTestResult(testResults);
                        //finish();
                        //dialogResult();
                    } else
                        AppUtils.toastError("Не усі відповіді заповнені.", true);
                }
            });
        }
        minRecationNotification = -1;
        Core.get().UserContentControl().userNotificationReaction();
    }

    @Override
    public void addResult(int questionTypeId, int test_question_id, int test_question_answer_id, String open_answer_text) {
        LogUtils.logD("hfyj65utyjghm65ry", "userTestContentId = " + userTestContentId);
        LogUtils.logD("hfyj65utyjghm65ry", "questionTypeId = " + questionTypeId + ", question_id = " + test_question_id + ", answer_id = " + test_question_answer_id);
        RequestTest requestTest = new RequestTest();
        requestTest.user_test_content_id = userTestContentId;
        requestTest.test_question_id = test_question_id;
        if (test_question_answer_id == -1)
            requestTest.test_question_answer_id = null;
        else
            requestTest.test_question_answer_id = test_question_answer_id;
        requestTest.product_id = productId;
        requestTest.open_answer_text = open_answer_text;
        LogUtils.logD("hfyj65utyjghm65ry", "ADD " + "questionTypeId = " + questionTypeId + ", question_id = " + test_question_id + ", answer_id = " + test_question_answer_id);
        switch (questionTypeId) {
            case 1:
                for (int i = 0; i < testResults.size(); i++) {
                    RequestTest item = testResults.get(i);
                    if (item.test_question_id == test_question_id && item.test_question_answer_id != test_question_answer_id) {
                        LogUtils.logD("hfyj65utyjghm65ry", "REMOVE " + " question_id = " + test_question_id + ", answer_id = " + test_question_answer_id);
                        testResults.remove(item);
                    }
                }
                break;
            case 2:
                for (int i = 0; i < testResults.size(); i++) {
                    RequestTest item = testResults.get(i);
                    if (item.test_question_id == test_question_id && item.test_question_answer_id == test_question_answer_id) {
                        LogUtils.logD("hfyj65utyjghm65ry", "REMOVE " + " question_id = " + test_question_id + ", answer_id = " + test_question_answer_id);
                        testResults.remove(item);
                    }
                }
                break;
            case 3:
                for (int i = 0; i < testResults.size(); i++) {
                    RequestTest item = testResults.get(i);
                    if (item.test_question_id == test_question_id) {
                        LogUtils.logD("hfyj65utyjghm65ry", "REMOVE " + " question_id = " + test_question_id + ", answer_id = " + test_question_answer_id);
                        testResults.remove(item);
                    }
                }
                break;
        }
        testResults.add(requestTest);
    }

    @Override
    public void removeResult(int test_translation_question_id, int test_translation_question_answer_id) {
        for (int i = 0; i < testResults.size(); i++) {
            RequestTest item = testResults.get(i);
            if (item.test_question_id == test_translation_question_id && item.test_question_answer_id == test_translation_question_answer_id) {
                LogUtils.logD("hfyj65utyjghm65ry", "REMOVE " + " question_id = " + test_translation_question_id + ", answer_id = " + test_translation_question_answer_id);

                testResults.remove(item);
            }
        }
    }

    @Override
    public void onEvent(Event event) {
        super.onEvent(event);
        switch (event.getEventId()) {
            case Event.EVENT_USER_NOTIFICATION_REACTION:
                minRecationNotification = ((EventUserNotificationReaction) event).getReactions();
                if(minRecationNotification < minRecationNotificationTest){
                    int a = minRecationNotificationTest - minRecationNotification;
                    String qty = Integer.toString(a);
                    String text =  "Вам потрібно відреагувати ще на ";
                    String text2 =  "повідомлення";
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
                    builder.setTitle(text + " "+ qty + " " + text2)
                            .setPositiveButton(R.string.exit, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                    android.app.AlertDialog alert = builder.create();
                    alert.show();

                }
                break;
            case Event.EVENT_MATERIAL_TEST_ACTIVITY_CLOSE:
                LogUtils.logD("GETMATERIAL", "TestsActivity EVENT_MATERIAL_TEST_ACTIVITY_CLOSE");
                Core.get().UserContentControl().getMaterials();
                finish();
                break;
            case Event.EVENT_MATERIAL_TEST_RESULTS_CHECK:
                LogUtils.logD("GETMATERIAL", "TestsActivity EVENT_MATERIAL_TEST_RESULTS_CHECK");
                Core.get().UserContentControl().getMaterials();
                ResultsCheckTest resultsCheck = ((EventTestResultLoad) event).getResultsCheck();
                if (resultsCheck == null) {
                    AppUtils.toastError(((EventTestResultLoad) event).getMessage(), false);
                    finish();
                } else
                    dialogResult(resultsCheck.questions, resultsCheck.correctAnswers, resultsCheck.pointsEarned, resultsCheck.balance);
                break;
        }
    }

    @Override
    protected void onLocalizationUpdate() {

    }

    @Override
    public void onOkDialog() {
        onBackPressed();
    }

    private class TestFragmentPagerAdapter extends FragmentStatePagerAdapter {

        public TestFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            ArrayList<Integer> selected = new ArrayList<>();
            String text = null;

            if (questions[position].question_type_id == 3) {
                LogUtils.logD("hjhgfyh64ghjghjghj", "* question_type_id 3 ");
                for (int i = 0; i < testResults.size(); i++) {
                    if (testResults.get(i).test_question_id == questions[position].id)
                        text = testResults.get(i).open_answer_text;
                }
            } else {
                LogUtils.logD("hjhgfyh64ghjghjghj", "* question_type_id != 3 ");
                for (int i = 0; i < testResults.size(); i++) {
                    LogUtils.logD("hjhgfyh64ghjghjghj", "!  testResults.get(i).test_translation_question_id =  " + testResults.get(i).test_question_id);
                    LogUtils.logD("hjhgfyh64ghjghjghj", "!  questions[position].id =  " + questions[position].id);

                    if (testResults.get(i).test_question_id == questions[position].id)
                        selected.add(testResults.get(i).test_question_answer_id);
                }
            }

            int[] selectedResult = new int[selected.size()];
            for (int i = 0; i < selectedResult.length; i++)
                selectedResult[i] = selected.get(i);

            LogUtils.logD("hjhgfyh64ghjghjghj", " ============================== TestFragment newInstance ============================");
            return TestFragment.newInstance(materialId, testId, position, selectedResult, text);
        }

        @Override
        public int getCount() {
            return questions.length;
        }
    }

    @Override
    public void onBackPressed() {
        dialogExit();
    }

    private void dialogExit() {
        new AlertDialog.Builder(this)
                .setMessage("Завершити тестування?")
                .setPositiveButton(getString(R.string.btn_ok), listener)
                .setNegativeButton(getString(R.string.btn_cancel), listener)
                .create()
                .show();
    }

    private void dialogResult(Integer questions, Integer correctAnswers, Double pointsEarned, Double balance) {
        Intent intent = new Intent(this, TestResultActivity.class);
        intent.putExtra(TestResultActivity.KEY_ID_TESTS, testId);
        intent.putExtra(TestResultActivity.KEY_ID_QUESTIONS, questions);
        intent.putExtra(TestResultActivity.KEY_ID_CORRECT_ANSWERS, correctAnswers);
        intent.putExtra(TestResultActivity.KEY_ID_POINTS_EARNED, pointsEarned);
        intent.putExtra(TestResultActivity.KEY_ID_BALANCE, balance);
        intent.putExtra(TestResultActivity.KEY_TITLE, mTitle);
        startActivity(intent);
    }


    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {

        final int BUTTON_NEGATIVE = -2;
        final int BUTTON_POSITIVE = -1;

        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case BUTTON_NEGATIVE:
                    dialog.dismiss();
                    break;

                case BUTTON_POSITIVE:
                    dialog.dismiss();
                    finish();
                    break;
            }
        }
    };
}
