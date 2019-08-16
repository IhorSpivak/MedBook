package mobi.medbook.android.recyclerviews.visit_doctor_question;

import android.view.View;
import android.widget.TextView;

import mobi.medbook.android.App;
import mobi.medbook.android.R;
import mobi.medbook.android.recyclerviews.base.BaseViewHolder;
import mobi.medbook.android.types.visits.AnswerVisit;
import mobi.medbook.android.types.visits.QuestionVisit;

import mobi.medbook.android.ui.custom_views.VDRadioButton;
import mobi.medbook.android.utils.TextUtils;


public class VisitDoctorQuestionViewHolder extends BaseViewHolder {

    private View rootView;
    private TextView tvTitle;

    private VDRadioButton vdRadioButton1;
    private VDRadioButton vdRadioButton2;
    private VDRadioButton vdRadioButton3;
    private VDRadioButton vdRadioButton4;
    private VDRadioButton vdRadioButton5;

    public VisitDoctorQuestionViewHolder(View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.item_visit_doctor_question_title);
        vdRadioButton1 = itemView.findViewById(R.id.item_visit_doctor_question_rb_1);
        vdRadioButton2 = itemView.findViewById(R.id.item_visit_doctor_question_rb_2);
        vdRadioButton3 = itemView.findViewById(R.id.item_visit_doctor_question_rb_3);
        vdRadioButton4 = itemView.findViewById(R.id.item_visit_doctor_question_rb_4);
        vdRadioButton5 = itemView.findViewById(R.id.item_visit_doctor_question_rb_5);

        rootView = itemView.findViewById(R.id.item_visit_doctor_question_root);
    }

    public void init(VisitDoctorQuestionRecyclerItem owner, QuestionVisit questionVisit) {
        if (tvTitle != null) {

            int selectLang = -1;
            for (int i = 0; i < questionVisit.translations.length; i++) {
                if (questionVisit.translations[i].language.substring(0, 2).equals(App.getLanguage())) {
                    selectLang = i;
                }
            }
            if (selectLang == -1) {
                for (int i = 0; i < questionVisit.translations.length; i++) {
                    if (questionVisit.translations[i].language.substring(0, 2).equals("uk")) {
                        selectLang = i;
                    }
                }
            }
            tvTitle.setText(questionVisit.translations[selectLang].title);

            switch (questionVisit.answers.length){
                case 1:
                    vdRadioButton2.setVisibility(View.GONE);
                    vdRadioButton3.setVisibility(View.GONE);
                    vdRadioButton4.setVisibility(View.GONE);
                    vdRadioButton5.setVisibility(View.GONE);
                    break;
                case 2:
                    vdRadioButton3.setVisibility(View.GONE);
                    vdRadioButton4.setVisibility(View.GONE);
                    vdRadioButton5.setVisibility(View.GONE);
                    break;
                case 3:
                    vdRadioButton4.setVisibility(View.GONE);
                    vdRadioButton5.setVisibility(View.GONE);
                    break;
                case 4:
                    vdRadioButton5.setVisibility(View.GONE);
                    break;
            }
            for(int i = 0; i < questionVisit.answers.length; i++){
                AnswerVisit answerVisit = questionVisit.answers[i];
                setRB(answerVisit, i);
            }
        }
        vdRadioButton1.setOnClickListener(owner);
        vdRadioButton2.setOnClickListener(owner);
        vdRadioButton3.setOnClickListener(owner);
        vdRadioButton4.setOnClickListener(owner);
        vdRadioButton5.setOnClickListener(owner);


        //rootView.setOnClickListener(owner);
    }

    private void setRB(AnswerVisit answerVisit, int number){

        int selectLangA = -1;
        for (int j = 0; j < answerVisit.translations.length; j++) {
            if (answerVisit.translations[j].language.substring(0, 2).equals(App.getLanguage())) {
                selectLangA = j;
            }
        }
        if (selectLangA == -1) {
            for (int j = 0; j < answerVisit.translations.length; j++) {
                if (answerVisit.translations[j].language.substring(0, 2).equals("uk")) {
                    selectLangA = j;
                }
            }
        }
        switch (number){
            case 0:
                vdRadioButton1.setCustomId(answerVisit);
                vdRadioButton1.setText(TextUtils.getString(answerVisit.translations[selectLangA].title));
                break;
            case 1:
                vdRadioButton2.setCustomId(answerVisit);
                vdRadioButton2.setText(TextUtils.getString(answerVisit.translations[selectLangA].title));
                break;
            case 2:
                vdRadioButton3.setCustomId(answerVisit);
                vdRadioButton3.setText(TextUtils.getString(answerVisit.translations[selectLangA].title));
                break;
            case 3:
                vdRadioButton4.setCustomId(answerVisit);
                vdRadioButton4.setText(TextUtils.getString(answerVisit.translations[selectLangA].title));
                break;
            case 4:
                vdRadioButton5.setCustomId(answerVisit);
                vdRadioButton5.setText(TextUtils.getString(answerVisit.translations[selectLangA].title));
                break;
        }
    }
}
