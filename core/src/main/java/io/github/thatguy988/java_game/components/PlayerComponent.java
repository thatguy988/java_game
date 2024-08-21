package io.github.thatguy988.java_game.components;

import com.badlogic.ashley.core.Component;

public class PlayerComponent implements Component, Shooter {
    

    private float timeSinceLastShot = 0f;
    private boolean isFiring = false;
    private boolean recoilTriggered = false;
    private float width = 8;
    private float height = 16;
    private boolean canJump = false;
    private boolean isJumping = false;
    private float jumpRaylength = this.height * 0.625f;
    private float movementspeed = 500f;
    private float jumpSpeed = 100000f;
    private float jumpTimer = 0.0f;
    private float jumpDuration = 0.5f;


    public void setHoldingJump(boolean jumping)
    {
        this.isJumping = jumping;
    }

    public boolean isHoldingJump()
    {
        return this.isJumping;
    }


    public float getJumpTimer()
    {
        return this.jumpTimer;
    }

    public void setJumpTimer(float time)
    {
        this.jumpTimer = time;
    }

    public float getJumpDuration()
    {
        return this.jumpDuration;
    }

    public float getJumpRaylength()
    {
        return this.jumpRaylength;
    }

    public float getMovementSpeed()
    {
        return this.movementspeed;
    }

    public float getJumpSpeed()
    {
        return this.jumpSpeed;
    }
 
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
