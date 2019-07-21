package ua.profarma.medbook.recyclerviews.tests_choice;

import ua.profarma.medbook.types.materials.Answer;

public class TestAnswerChecked extends Answer {
    public boolean selected = false;
    public int questionId;
    public int questionTypeId;

    public TestAnswerChecked(Answer answer, int questionId, int questionTypeId){
        id = answer.id;
        test_question_id = answer.test_question_id;
        translations = answer.translations;
        is_true = answer.is_true;
        this.questionId = questionId;
        this.questionTypeId = questionTypeId;
    }
}
