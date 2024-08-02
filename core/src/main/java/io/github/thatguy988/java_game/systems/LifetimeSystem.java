package io.github.thatguy988.java_game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import io.github.thatguy988.java_game.components.LifetimeComponent;

public class LifetimeSystem extends IteratingSystem
{
    private ComponentMapper<LifetimeComponent> lm = ComponentMapper.getFor(LifetimeComponent.class);

    public LifetimeSystem()
    {
        super(Family.all(LifetimeComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {
        LifetimeComponent lifeTime = lm.get(entity);
        lifeTime.lifetime -= deltaTime;

        if (lifeTime.lifetime <= 0)
        {
            getEngine().removeEntity(entity);
        }

    }
    
}
