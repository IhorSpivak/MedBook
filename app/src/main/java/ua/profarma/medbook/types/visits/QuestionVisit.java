package ua.profarma.medbook.types.visits;

public class QuestionVisit {
    public int id;
    public int question_type_id;
    public TranslationQuestionVisit[] translations;
    public AnswerVisit[] answers;

}
