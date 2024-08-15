package io.github.thatguy988.java_game.components;

import com.badlogic.ashley.core.Component;

public class PlayerComponent implements Component, Shooter {
    private float timeSinceLastShot = 0f;
    private boolean isFiring = false;
    private boolean recoilTriggered = false;
    private float width = 8;
    private float height = 8;
    private boolean canJump = false;

    
    public void setCanJump(boolean jump)
    {
        this.canJump = jump;
    }

    public boolean getCanJump()
    {
        return this.canJump;
    }
    
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
