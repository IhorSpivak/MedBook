package mobi.medbook.android.events.materials;

import mobi.medbook.android.events.core.Event;
import ua.profarma.medbook.types.materials.ResultsCheckTest;

public class EventTestResultLoad extends Event {

    private ResultsCheckTest resultsCheck;
    private String message;

    public EventTestResultLoad(String message, ResultsCheckTest resultsCheck) {
        super(EVENT_MATERIAL_TEST_RESULTS_CHECK);
        this.resultsCheck  = resultsCheck;
        this.message = message;
    }

    public ResultsCheckTest getResultsCheck() {
        return resultsCheck;
    }

    public String getMessage() {
        return message;
    }
}
