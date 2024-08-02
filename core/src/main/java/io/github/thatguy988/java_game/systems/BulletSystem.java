package io.github.thatguy988.java_game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import io.github.thatguy988.java_game.components.BulletComponent;
import io.github.thatguy988.java_game.components.VelocityComponent;


public class BulletSystem extends IteratingSystem 
{
    private ComponentMapper<BulletComponent> bm = ComponentMapper.getFor(BulletComponent.class);
    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);

    public BulletSystem()
    {
        super(Family.all(BulletComponent.class, VelocityComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {
        BulletComponent bullet = bm.get(entity);
        VelocityComponent velocity = vm.get(entity);

        float direction = Math.signum(velocity.x);
        velocity.x = bullet.speed * direction;
    }

    
}
