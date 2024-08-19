package io.github.thatguy988.java_game.components;
import java.util.EnumMap;
import java.util.Map;

import com.badlogic.ashley.core.Component;

import io.github.thatguy988.java_game.utils.WeaponType;


public class AmmoCounterComponent implements Component 
{

    private Map<WeaponType, Integer> currentAmmo;
    private Map<WeaponType, Integer> maxAmmo;

    //constructor
    public AmmoCounterComponent() {
        // Initialize the ammo counts
        currentAmmo = new EnumMap<>(WeaponType.class);
        maxAmmo = new EnumMap<>(WeaponType.class);

        // Set max ammo for each weapon type
        //pistol is unlimited
        //maxAmmo.put(WeaponType.PISTOL, 100);
        maxAmmo.put(WeaponType.SHOTGUN, 50);
        maxAmmo.put(WeaponType.MACHINEGUN, 300);
        maxAmmo.put(WeaponType.MINIGUN, 500);

        // Start with full ammo
        for (WeaponType type : WeaponType.values()) {
            currentAmmo.put(type, maxAmmo.get(type));
        }
    }

    public int getCurrentAmmo(WeaponType type)
    {
        if(type == WeaponType.PISTOL)
        {
            return Integer.MAX_VALUE;
        }
        //return value of key type or return default of zero
        return currentAmmo.getOrDefault(type, 0);
    }

    public int getMaxAmmo(WeaponType type)
    {
        if(type == WeaponType.PISTOL)
        {
            return Integer.MAX_VALUE;
        }
        return maxAmmo.getOrDefault(type, 0);
    }

    public void setCurrentAmmo(WeaponType type, int amount)
    {
        if(type == WeaponType.PISTOL)
        {
            return;
        }
        currentAmmo.put(type, Math.min(amount,getMaxAmmo(type)));
    }

    public void addAmmo(WeaponType type, int amount)
    {
        if(type == WeaponType.PISTOL)
        {
            return;
        }
        setCurrentAmmo(type, getCurrentAmmo(type) + amount);
    }

    public void reduceAmmo(WeaponType type, int amount)
    {
        if(type == WeaponType.PISTOL)
        {
            return;
        }
        setCurrentAmmo(type, getCurrentAmmo(type) - amount);
    }

    public boolean isOutofAmmo(WeaponType type)
    {
        if (type == WeaponType.PISTOL) {
            return false; // Pistol never runs out of ammo
        }
        return getCurrentAmmo(type)<=0;
    }

    
    
}
