package io.github.thatguy988.java_game.events;

import com.badlogic.ashley.core.Entity;

public class EntityRemovedEvent {
    public final Entity entity;

    public EntityRemovedEvent(Entity entity) {
        this.entity = entity;
    }
}