package io.github.thatguy988.java_game.events;

import com.badlogic.ashley.signals.Signal;

public class EventManager {
    // Define a signal for entity removal events
    public static final Signal<EntityRemovedEvent> entityRemovedSignal = new Signal<>();
}