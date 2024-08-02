package io.github.thatguy988.java_game.factories;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;

import io.github.thatguy988.java_game.components.BulletComponent;
import io.github.thatguy988.java_game.components.FacingDirectionComponent;
import io.github.thatguy988.java_game.components.LifetimeComponent;
import io.github.thatguy988.java_game.components.PositionComponent;
import io.github.thatguy988.java_game.components.Shooter;
import io.github.thatguy988.java_game.components.VelocityComponent;


public class BulletFactory {
    private Engine engine;
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);


    public BulletFactory(Engine engine) {
        this.engine = engine;
    }

    public Entity createBullet(Entity entity, Shooter shooter, FacingDirectionComponent.Direction direction, float bulletspeed) {
        Entity bullet = engine.createEntity();

        float bulletoffset = 5;

        PositionComponent position = new PositionComponent();
        PositionComponent shooter_position = pm.get(entity);
        


        if (direction == FacingDirectionComponent.Direction.RIGHT)
        {
            position.x = shooter_position.x + shooter.getWidth();
            position.y = shooter_position.y + shooter.getHeight()/2 - bulletoffset;
        }else if(direction == FacingDirectionComponent.Direction.LEFT)
        {
            position.x = shooter_position.x;
            position.y = shooter_position.y + shooter.getHeight() / 2 - bulletoffset;
        }
        

        bullet.add(position);

        VelocityComponent velocity = new VelocityComponent();
        velocity.x = direction == FacingDirectionComponent.Direction.RIGHT ? bulletspeed : -bulletspeed;
        bullet.add(velocity);

        BulletComponent bulletComponent = new BulletComponent();
        bulletComponent.speed = bulletspeed;
        bullet.add(bulletComponent);

        bullet.add(new LifetimeComponent(2f));

        engine.addEntity(bullet);
        return bullet;
    }
}