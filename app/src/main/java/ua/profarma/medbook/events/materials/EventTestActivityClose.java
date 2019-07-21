package ua.profarma.medbook.events.materials;

import ua.profarma.medbook.events.core.Event;
import ua.profarma.medbook.utils.LogUtils;

public class EventTestActivityClose extends Event {
    public EventTestActivityClose(){
        super(EVENT_MATERIAL_TEST_ACTIVITY_CLOSE);
    }
}
