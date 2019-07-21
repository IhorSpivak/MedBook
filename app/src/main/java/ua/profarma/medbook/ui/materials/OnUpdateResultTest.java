package ua.profarma.medbook.ui.materials;

public interface OnUpdateResultTest {
    public void addResult(int type_test, int test_translation_question_id, int test_translation_question_answer_id, String open_answer_text);

    public void removeResult(int test_translation_question_id, int test_translation_question_answer_id);
}
