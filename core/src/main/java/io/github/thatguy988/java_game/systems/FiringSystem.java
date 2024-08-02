package io.github.thatguy988.java_game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import io.github.thatguy988.java_game.components.FacingDirectionComponent;
import io.github.thatguy988.java_game.components.PlayerComponent;
import io.github.thatguy988.java_game.components.Shooter;
import io.github.thatguy988.java_game.factories.BulletFactory;

public class FiringSystem extends IteratingSystem {
    private ComponentMapper<FacingDirectionComponent> fm = ComponentMapper.getFor(FacingDirectionComponent.class);
    private ComponentMapper<PlayerComponent> sm = ComponentMapper.getFor(PlayerComponent.class);

    private BulletFactory bulletFactory;

    public FiringSystem(BulletFactory bulletFactory) {
        super(Family.all(PlayerComponent.class, FacingDirectionComponent.class).get());
        this.bulletFactory = bulletFactory;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Shooter shooter = sm.get(entity);
        FacingDirectionComponent facing = fm.get(entity);

        if (shooter == null) {
            System.out.println("Shooter component is missing from entity: " + entity);
            return;
        }
        if (facing == null) {
            System.out.println("FacingDirectionComponent is missing from entity: " + entity);
            return;
        }

        shooter.setTimeSinceLastShot(shooter.getTimeSinceLastShot() + deltaTime);

        if (shooter.isFiring() && shooter.getTimeSinceLastShot() >= shooter.getFiringCooldown()) {
            bulletFactory.createBullet(entity, shooter, facing.direction, 200f);
            shooter.setTimeSinceLastShot(0f);
        }
    }
}
