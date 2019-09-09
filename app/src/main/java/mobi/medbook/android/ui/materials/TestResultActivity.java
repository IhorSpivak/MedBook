package mobi.medbook.android.ui.materials;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import mobi.medbook.android.R;
import mobi.medbook.android.events.core.EventRouter;
import mobi.medbook.android.events.materials.EventMaterialDescriptionClose;
import mobi.medbook.android.events.materials.EventTestActivityClose;
import mobi.medbook.android.utils.LogUtils;


public class TestResultActivity extends AppCompatActivity {

    private String TAG = "AppMedbook/TestResultActivity";
    public static final String KEY_ID_TESTS             = "KEY_ID_TESTS";
    public static final String KEY_ID_QUESTIONS         = "KEY_ID_QUESTIONS";
    public static final String KEY_ID_CORRECT_ANSWERS   = "KEY_ID_CORRECT_ANSWERS";
    public static final String KEY_ID_POINTS_EARNED     = "KEY_ID_POINTS_EARNED";
    public static final String KEY_ID_BALANCE           = "KEY_ID_BALANCE";
    public static final String KEY_TITLE           = "KEY_TITLE";

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EventRouter.send(new EventTestActivityClose());
        finish();
    }

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

        }


    }
}
