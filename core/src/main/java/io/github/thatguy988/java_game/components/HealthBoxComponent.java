package io.github.thatguy988.java_game.components;

import com.badlogic.ashley.core.Component;

public class HealthBoxComponent implements Component 
{
    private int health;
    private float width = 4;
    private float height = 4;

    public HealthBoxComponent(int numberOfHealth)
    {
        this.health = numberOfHealth;
    }

    public int getHealth()
    {
        return this.health;
    }

    public void setHealth(int numberOfHealth)
    {
        this.health = numberOfHealth;
    }

    public float getWidth()
    {
        return this.width;
    }

    public float getHeight()
    {
        return this.height;
    }
    
}
