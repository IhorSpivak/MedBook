package mobi.medbook.android.events.materials;

import mobi.medbook.android.events.core.Event;
import mobi.medbook.android.ui.materials.MaterialsEnum;

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
