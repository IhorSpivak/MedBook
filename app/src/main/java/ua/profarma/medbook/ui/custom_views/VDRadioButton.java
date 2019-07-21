package ua.profarma.medbook.ui.custom_views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioButton;

import ua.profarma.medbook.types.visits.AnswerVisit;

public class VDRadioButton extends RadioButton {

    private AnswerVisit answerVisit;

    public VDRadioButton(Context context) {
        this(context, null);
    }

    public VDRadioButton(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public VDRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setCustomId(AnswerVisit answerVisit) {
        this.answerVisit = answerVisit;
    }

    public AnswerVisit getCustomId() {
        return answerVisit;
    }
}
