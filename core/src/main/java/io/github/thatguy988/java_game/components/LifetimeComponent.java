package io.github.thatguy988.java_game.components;

import com.badlogic.ashley.core.Component;

public class LifetimeComponent implements Component {
    public float lifetime;

    public LifetimeComponent(float lifetime) {
        this.lifetime = lifetime;
    }
}
