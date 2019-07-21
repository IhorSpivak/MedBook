package ua.profarma.medbook.types.materials;

public class Question {

    public int id;
    public int test_id;
    public int question_type_id;


    public QuestionTranslation[] translations;
    public Answer[] answers;
}
