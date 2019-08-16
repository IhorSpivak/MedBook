package mobi.medbook.android.events.materials;

import mobi.medbook.android.events.core.Event;


public class EventOnSendResultPresentationSuccess extends Event {

    private int materialId;
    private int presentationId;
    private String resultTime;

    public EventOnSendResultPresentationSuccess(int materialId, int presentationId, String resultTime) {
        super(EVENT_MATERIAL_PRESENTATION_RESULT_SUCCESS);
        this.materialId  = materialId;
        this.presentationId = presentationId;
        this.resultTime = resultTime;
    }

    public int getPresentationId() {
        return presentationId;
    }

    public int getMaterialId() {
        return materialId;
    }

    public String getResultTime() {
        return resultTime;
    }
}
