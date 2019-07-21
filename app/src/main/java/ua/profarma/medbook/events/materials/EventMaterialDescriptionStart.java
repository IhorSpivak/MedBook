package ua.profarma.medbook.events.materials;

import ua.profarma.medbook.events.core.Event;
import ua.profarma.medbook.ui.materials.MaterialsEnum;

public class EventMaterialDescriptionStart extends Event {

    private MaterialsEnum mType;
    private int mIdType;

    public EventMaterialDescriptionStart(MaterialsEnum type, int idType) {
        super(EVENT_MATERIAL_DESCRIPTION_FRAGMENT_START);
        mType = type;
        mIdType = idType;
    }

    public int getIdType() {
        return mIdType;
    }

    public MaterialsEnum getType() {
        return mType;
    }
}
