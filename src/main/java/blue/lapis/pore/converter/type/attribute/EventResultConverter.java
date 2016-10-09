package blue.lapis.pore.converter.type.attribute;

import org.bukkit.event.Event;

public final class EventResultConverter {
    private EventResultConverter() {
    }

    public static Boolean of(Event.Result result) {
        if (result == Event.Result.DENY) {
            return true;
        }
        return false;
    }

    public static Event.Result of(Boolean canceled) {
        return canceled ? Event.Result.DENY : Event.Result.ALLOW;
    }
}
