package ua.profarma.medbook.ui.materials;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ua.profarma.medbook.App;
import ua.profarma.medbook.Core;
import ua.profarma.medbook.R;
import ua.profarma.medbook.events.core.EventRouter;
import ua.profarma.medbook.events.materials.EventMaterialDescriptionClose;
import ua.profarma.medbook.events.materials.EventMaterialFragmentClose;
import ua.profarma.medbook.events.materials.EventTestActivityClose;
import ua.profarma.medbook.types.materials.Test;
import ua.profarma.medbook.utils.LogUtils;

public class TestResultActivity extends AppCompatActivity {

    private String TAG = "AppMedbook/TestResultActivity";
    public static final String KEY_ID_TESTS             = "KEY_ID_TESTS";
    public static final String KEY_ID_QUESTIONS         = "KEY_ID_QUESTIONS";
    public static final String KEY_ID_CORRECT_ANSWERS   = "KEY_ID_CORRECT_ANSWERS";
    public static final String KEY_ID_POINTS_EARNED     = "KEY_ID_POINTS_EARNED";
    public static final String KEY_ID_BALANCE           = "KEY_ID_BALANCE";
    public static final String KEY_TITLE           = "KEY_TITLE";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);
        ImageView imvClose = findViewById(R.id.activity_test_result_close);
        imvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventRouter.send(new EventTestActivityClose());
                finish();
            }
        });

        if (getIntent() != null) {

            int idTest          = getIntent().getIntExtra(KEY_ID_TESTS, -1);
            Integer questions       = getIntent().getIntExtra(KEY_ID_QUESTIONS, 0);
            Integer correctAnswers  = getIntent().getIntExtra(KEY_ID_CORRECT_ANSWERS, 0);
            Double  pointsEarned    = getIntent().getDoubleExtra(KEY_ID_POINTS_EARNED, 0);
            Double  balance         = getIntent().getDoubleExtra(KEY_ID_BALANCE, 0);
            String title            = getIntent().getStringExtra(KEY_TITLE);

            LogUtils.logD(TAG, "correctAnswers = " + correctAnswers);
            LogUtils.logD(TAG, "questions = " + questions);
            LogUtils.logD(TAG, "PointsEarned = " + pointsEarned);
            LogUtils.logD(TAG, "balance = " + balance);

            TextView tvTitle = findViewById(R.id.activity_test_result_title);
            TextView tvQuestion = findViewById(R.id.activity_test_result_questions_count);
            TextView tvCorrectAnswers = findViewById(R.id.activity_test_result_answers_true);
            TextView tvPointsEarned = findViewById(R.id.activity_test_result_points_received);
            TextView tvBalance = findViewById(R.id.activity_test_result_points_general);

            tvTitle.setText(title);
            tvQuestion.setText(String.valueOf(questions));
            tvCorrectAnswers.setText(String.valueOf(correctAnswers));
            tvPointsEarned.setText(String.valueOf(Math.round(pointsEarned)));
            tvBalance.setText(String.valueOf(Math.round(balance)));

            Button btnOk = findViewById(R.id.activity_test_result_ok);
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventRouter.send(new EventTestActivityClose());
                    EventRouter.send(new EventMaterialDescriptionClose());

                    Intent intentTests = new Intent(TestResultActivity.this, ViewCompletedTestActivity.class);
                    LogUtils.logD(TAG, "testId = " + idTest);
                    intentTests.putExtra(ViewCompletedTestActivity.KEY_TEST_USER_CONTENT_ID, idTest);
                    startActivity(intentTests);
                    finish();
                }
            });

//            int testId = getIntent().getIntExtra(KEY_ID_TEST, 0);
//            for (Test item : Core.get().UserContentControl().getSelectedMaterial().tests) {
//                if (item.test_id == testId) {
//                    tvQuestion.setText(item.test.translations[0].title);
//                    tvQuestionsCount.setText(String.valueOf(item.test.translations[0].questions.length));
//                }
//            }
        }

//        TextView tvPointGeneral = findViewById(R.id.activity_test_result_points_general);
//        tvPointGeneral.setText(App.getUser().points);
    }
}
