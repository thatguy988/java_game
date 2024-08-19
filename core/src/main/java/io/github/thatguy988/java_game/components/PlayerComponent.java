package io.github.thatguy988.java_game.components;

import com.badlogic.ashley.core.Component;

public class PlayerComponent implements Component, Shooter {
    

    private float timeSinceLastShot = 0f;
    private boolean isFiring = false;
    private boolean recoilTriggered = false;
    private float width = 8;
    private float height = 20;
    private boolean canJump = false;
    private float jumpRaylength = this.height * 0.625f;
    private float movementspeed = 500f;
    private float jumpSpeed = 50000f;




    

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

    


    // public float calculateVelocity(float originalVelocity, float originalWidth, float originalHeight, float currentWidth, float currentHeight) {
    //     float originalArea = originalWidth * originalHeight;
    //     float currentArea = currentWidth * currentHeight;
        
    //     float newVelocity = originalVelocity * (currentArea / originalArea);
    //     return newVelocity;
    // }


    // public float calculateJumpSpeed(float originalJumpSpeed, float originalWidth, float originalHeight, float currentWidth, float currentHeight) {
    //     // Calculate the area of the original and current player sizes
    //     float originalArea = originalWidth * originalHeight;
    //     float currentArea = currentWidth * currentHeight;
    
    //     // Scale the jump speed based on the ratio of the current area to the original area
    //     float newJumpSpeed = originalJumpSpeed * (currentArea / originalArea);
    
    //     return newJumpSpeed;
    // }

    // public float calculateRecoilForce(float originalRecoilForce, float originalWidth, float originalHeight, float currentWidth, float currentHeight) {
    //     // Calculate the area of the original and current player sizes
    //     float originalArea = originalWidth * originalHeight;
    //     float currentArea = currentWidth * currentHeight;
    
    //     // Scale the recoil force based on the ratio of the current area to the original area
    //     float newRecoilForce = originalRecoilForce * (currentArea / originalArea);
    
    //     return newRecoilForce;
    // }
    
    
    

    
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
