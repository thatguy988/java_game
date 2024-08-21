package io.github.thatguy988.java_game.components;

import com.badlogic.ashley.core.Component;

import io.github.thatguy988.java_game.utils.WeaponType;


public class AmmoBoxComponent implements Component
{
    private WeaponType weaponType;
    private int ammo;
    private float width = 4;
    private float height = 4;

    public AmmoBoxComponent(WeaponType type, int numberOfAmmo)
    {
        this.weaponType = type;
        this.ammo = numberOfAmmo;
    }
    
    public WeaponType getWeaponType()
    {
        return this.weaponType;
    }

    public int getAmmo()
    {
        return this.ammo;
    }

    public void setWeaponType(WeaponType type)
    {
        this.weaponType = type;
    }

    public void setAmmo(int ammo)
    {
        this.ammo = ammo;
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
