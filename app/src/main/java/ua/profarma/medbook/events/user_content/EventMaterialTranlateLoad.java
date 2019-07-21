package ua.profarma.medbook.events.user_content;

import ua.profarma.medbook.events.core.Event;
import ua.profarma.medbook.types.materials.MaterialTranslation;

public class EventMaterialTranlateLoad extends Event {

    private boolean success;
    private String message;
    private MaterialTranslation materialTranslation;

    public EventMaterialTranlateLoad(boolean success, String message, MaterialTranslation materialTranslation) {
        super(EVENT_MATERIAL_TRANSLATE_LOAD);
        this.success = success;
        this.message = message;
        this.materialTranslation = materialTranslation;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public MaterialTranslation getMaterialTranslation() {
        return materialTranslation;
    }
}
