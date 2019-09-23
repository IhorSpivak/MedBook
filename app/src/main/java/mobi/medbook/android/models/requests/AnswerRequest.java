package mobi.medbook.android.models.requests;

import java.util.ArrayList;

import mobi.medbook.android.types.visits.AnswerMP;

public class AnswerRequest {
    public String question_id;
    public String awnser_id;
    public String question;
    public String awnser_description;


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public String getAwnser_id() {
        return awnser_id;
    }

    public void setAwnser_id(String awnser_id) {
        this.awnser_id = awnser_id;
    }

    public String getAwnser_description() {
        return awnser_description;
    }

    public void setAwnser_description(String awnser_description) {
        this.awnser_description = awnser_description;
    }

    public AnswerRequest(String question_id, String awnser_id, String question, String awnser_description) {
        this.question_id = question_id;
        this.awnser_id = awnser_id;
        this.question = question;
        this.awnser_description = awnser_description;
    }
}
