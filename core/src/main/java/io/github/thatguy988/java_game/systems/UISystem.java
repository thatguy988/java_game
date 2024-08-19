package io.github.thatguy988.java_game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.thatguy988.java_game.components.AmmoCounterComponent;
import io.github.thatguy988.java_game.components.HealthComponent;
import io.github.thatguy988.java_game.components.PlayerComponent;
import io.github.thatguy988.java_game.components.WeaponsComponent;
import io.github.thatguy988.java_game.utils.WeaponType;

public class UISystem extends IteratingSystem
{
    private ComponentMapper<AmmoCounterComponent> am = ComponentMapper.getFor(AmmoCounterComponent.class);
    private ComponentMapper<WeaponsComponent> wm = ComponentMapper.getFor(WeaponsComponent.class);
    private ComponentMapper<HealthComponent> hm = ComponentMapper.getFor(HealthComponent.class);
    private SpriteBatch spritebatch;
    private BitmapFont font;

    public UISystem(SpriteBatch spritebatch)
    {
        super(Family.all(PlayerComponent.class).get());
        this.spritebatch = spritebatch;
        this.font = new BitmapFont();
    }

    @Override
    public void update(float deltaTime)
    {
        spritebatch.begin();
        super.update(deltaTime);
        spritebatch.end();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {
        AmmoCounterComponent ammo = am.get(entity);
        WeaponsComponent weapon = wm.get(entity);
        HealthComponent health = hm.get(entity);
        String ammoText;

        if(weapon.getWeaponType() == WeaponType.PISTOL)
        {
            ammoText = "Ammo: " + "Pistol: Unlimited";

        }else
        {
            ammoText = "Ammo: " + weapon.getWeaponType() + ": " + ammo.getCurrentAmmo(weapon.getWeaponType()) + "/" + ammo.getMaxAmmo(weapon.getWeaponType()); 
        }

        String healthText = "Health: " + health.getCurrentHealth() + "/" + health.getMaxHealth();

        font.draw(spritebatch, ammoText, 20, 100);    
        font.draw(spritebatch, healthText, 20, 120);    

    }
    
}
