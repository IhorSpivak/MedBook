package mobi.medbook.android.events.materials;

import mobi.medbook.android.events.core.Event;

public class EventOnSingleChoice extends Event {
    private int id;
    private int questionId;
    public EventOnSingleChoice(int questionId, int id) {
        super(EVENT_MATERIAL_TEST_SINGLE_CHOICE);
        this.id = id;
        this.questionId = questionId;
    }

    public int getId() {
        return id;
    }

    public int getQuestionId() {
        return questionId;
    }
}
