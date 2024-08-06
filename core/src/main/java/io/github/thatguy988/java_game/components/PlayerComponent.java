package io.github.thatguy988.java_game.components;

import com.badlogic.ashley.core.Component;

public class PlayerComponent implements Component, Shooter {
    private float timeSinceLastShot = 0f;
    private boolean isFiring = false;
    private boolean recoilTriggered = false;
    private float width = 32;
    private float height = 32;

    public void setRecoilTriggered(boolean triggered)
    {
        this.recoilTriggered = triggered;

    }

    public boolean getRecoilTriggered()
    {
        return recoilTriggered;
    }

    @Override
    public float getWidth() {
        return this.width;
    }

    @Override
    public float getHeight() {
        return this.height;
    }

    @Override
    public float getFiringCooldown()
    {
        return 0.0f;//null not needed for player
    }

    @Override
    public float getTimeSinceLastShot()
    {
        return this.timeSinceLastShot;
    }

    @Override
    public void setTimeSinceLastShot(float time) {
        this.timeSinceLastShot = time;
    }

    @Override
    public boolean isFiring() {
        return this.isFiring;
    }

    @Override 
    public void setisFiring(boolean firing)
    {
        this.isFiring = firing;
    }

}
