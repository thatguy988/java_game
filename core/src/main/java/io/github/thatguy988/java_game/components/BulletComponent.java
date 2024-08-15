package io.github.thatguy988.java_game.components;

import com.badlogic.ashley.core.Component;

public class BulletComponent implements Component
{
    public enum BulletType{
        PLAYER, 
        ENEMY
    }

    private BulletType type;
    private boolean active;

    public BulletComponent(BulletType type) {
        this.type = type;
        this.active = true;
    }

    public BulletType getBulletType()
    {
        return this.type;
    }

    public void setActiveState(boolean value)
    {
        this.active = value;
    }
    
    public boolean getActiveState()
    {
        return this.active;
    }
    
}
