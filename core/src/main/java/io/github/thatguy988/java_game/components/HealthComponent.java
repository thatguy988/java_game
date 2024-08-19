package io.github.thatguy988.java_game.components;
import com.badlogic.ashley.core.Component;

public class HealthComponent implements Component 
{
    private int currentHealth;
    private int maxHealth;

    //constructor
    public HealthComponent(int maxHealth)
    {
        this.currentHealth = maxHealth;
        this.maxHealth = maxHealth;
    }

    public int getCurrentHealth()
    {
        return this.currentHealth;
    }

    public int getMaxHealth()
    {
        return this.maxHealth;
    }

    public void addCurrentHealth(int amount)
    {
        this.currentHealth = Math.min(this.currentHealth + amount, this.maxHealth);
    }

    public void reduceCurrentHealth(int amount)
    {
        this.currentHealth  = Math.max(this.currentHealth - amount, 0);
    }

    public boolean isOutofHealth()
    {
        return this.currentHealth <= 0;
    }

    public void resetHealth()
    {
        this.currentHealth = this.maxHealth;
    }
    
}
