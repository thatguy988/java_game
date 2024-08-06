package io.github.thatguy988.java_game.components;

import com.badlogic.ashley.core.Component;

public class WeaponsComponent implements Component
{
    public enum WeaponType
    {
        PISTOL,
        MACHINEGUN,
        SHOTGUN,
        ENEMYGRUNT
    }

    private WeaponType weaponType;
    private float FiringCooldown;
    private float BulletSpeed;
    private int BulletsPerShot; // for example shotgun fires 5 bullets per shot

    public WeaponsComponent(WeaponType weaponType, float firingcooldown, float bulletspeed, int bulletspershot)
    {
        this.weaponType = weaponType;
        this.FiringCooldown = firingcooldown;
        this.BulletSpeed = bulletspeed;
        this.BulletsPerShot = bulletspershot;
    }

    public void setWeaponType(WeaponType newWeaponType)
    {
        this.weaponType = newWeaponType;
    }

    public WeaponType getWeaponType()
    {
        return this.weaponType;
    }

    public void setFiringCooldown(float newFiringCooldown)
    {
        this.FiringCooldown = newFiringCooldown;
    }

    public float getFiringCooldown()
    {
        return this.FiringCooldown;
    }

    public void setBulletSpeed(float newBulletSpeed)
    {
        this.BulletSpeed = newBulletSpeed;
    }

    public float getBulletSpeed()
    {
        return this.BulletSpeed;
    }

    public void setBulletsPerShot(int newBulletsPerShot)
    {
        this.BulletsPerShot = newBulletsPerShot;
    }

    public int getBulletsPerShot()
    {
        return this.BulletsPerShot;
    }

    
}
