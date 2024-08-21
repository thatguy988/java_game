package io.github.thatguy988.java_game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import io.github.thatguy988.java_game.components.AmmoCounterComponent;
import io.github.thatguy988.java_game.components.FacingDirectionComponent;
import io.github.thatguy988.java_game.components.PlayerComponent;
import io.github.thatguy988.java_game.components.Shooter;
import io.github.thatguy988.java_game.components.WeaponsComponent;
import io.github.thatguy988.java_game.factories.BulletFactory;

//handle when next bullet is fired
public class FiringSystem extends IteratingSystem {
    private ComponentMapper<FacingDirectionComponent> fm = ComponentMapper.getFor(FacingDirectionComponent.class);
    private ComponentMapper<PlayerComponent> sm = ComponentMapper.getFor(PlayerComponent.class);
    private ComponentMapper<WeaponsComponent> wm = ComponentMapper.getFor(WeaponsComponent.class);
    private ComponentMapper<PlayerComponent> plcm = ComponentMapper.getFor(PlayerComponent.class);
    private ComponentMapper<AmmoCounterComponent> am = ComponentMapper.getFor(AmmoCounterComponent.class);
    private BulletFactory bulletFactory;


    public FiringSystem(BulletFactory bulletFactory) {
        super(Family.all(PlayerComponent.class, FacingDirectionComponent.class, WeaponsComponent.class, AmmoCounterComponent.class).get());
        this.bulletFactory = bulletFactory;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Shooter shooter = sm.get(entity);
        PlayerComponent player = plcm.get(entity);
        FacingDirectionComponent facing = fm.get(entity);
        WeaponsComponent weapon = wm.get(entity);
        AmmoCounterComponent ammo = am.get(entity);

        shooter.setTimeSinceLastShot(shooter.getTimeSinceLastShot() + deltaTime);

        boolean noAmmo = ammo.isOutofAmmo(weapon.getWeaponType());


        if (shooter.isFiring() && shooter.getTimeSinceLastShot() >= weapon.getFiringCooldown() && !noAmmo) 
        {
            bulletFactory.createBullet(entity, shooter, facing.direction, weapon);
            shooter.setTimeSinceLastShot(0f);
            ammo.reduceAmmo(weapon.getWeaponType(), 1);
            player.setRecoilTriggered(true);
        }
    }

    
}
