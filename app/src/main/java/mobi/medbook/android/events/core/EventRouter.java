package mobi.medbook.android.events.core;

import java.util.Iterator;


public class EventRouter {
    private ConcurrentArrayList<EventListener> listeners;
    protected static EventRouter router = new EventRouter();


    public EventRouter() {
        listeners = new ConcurrentArrayList<>();
    }

    public static void register(EventListener listener) {
        router.register_(listener);
    }

    public static void unregister(EventListener listener) {
        router.unregister_(listener);
    }

    public static void send(Event event) {
        router.send_(event);
    }

    private void register_(EventListener listener) {
        if (listeners.indexOf(listener) == -1) {
            listeners.add(listener);
        }
    }

    private void unregister_(EventListener listener) {
        listeners.remove(listener);
    }

    private void send_(Event event) {
//        for (EventListener listener : listeners) {
//            Log.e("hghvjhjghj", "send_");
//            listener.onEvent(event);
//        }

        // Create an iterator for the list
        // using iterator() method
        Iterator<EventListener> iter = listeners.iterator();

        // Displaying the values after iterating
        // through the list
        while (iter.hasNext()) {
            iter.next().onEvent(event);
        }
    }
}
