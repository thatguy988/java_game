package io.github.thatguy988.java_game.components;

import com.badlogic.ashley.core.Component;

public class PlayerComponent implements Component, Shooter {
    private float firingCooldown = 0.5f;
    private float timeSinceLastShot = 0f;
    private boolean isFiring = false;
    private float width = 32;
    private float height = 32;

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public float getFiringCooldown()
    {
        return firingCooldown;
    }

    @Override
    public float getTimeSinceLastShot()
    {
        return timeSinceLastShot;
    }

    @Override
    public void setTimeSinceLastShot(float time) {
        this.timeSinceLastShot = time;
    }

    @Override
    public boolean isFiring() {
        return isFiring;
    }

    @Override 
    public void setisFiring(boolean firing)
    {
        this.isFiring = firing;
    }

}
