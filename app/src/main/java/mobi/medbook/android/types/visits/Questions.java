package mobi.medbook.android.types.visits;

import java.util.ArrayList;

import mobi.medbook.android.types.materials.Answer;

public class Questions {
    public String question;
    public String question_id;
    public ArrayList<AnswerMP> awnsersArr;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<AnswerMP> getAwnsersArr() {
        return awnsersArr;
    }

    public void setAwnsersArr(ArrayList<AnswerMP> awnsersArr) {
        this.awnsersArr = awnsersArr;
    }

}
