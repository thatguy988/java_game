package io.github.thatguy988.java_game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.World;

import io.github.thatguy988.java_game.components.Box2DComponent;
import io.github.thatguy988.java_game.components.LifetimeComponent;


public class LifetimeSystem extends IteratingSystem
{
    private World world;
    private ComponentMapper<LifetimeComponent> lm = ComponentMapper.getFor(LifetimeComponent.class);
    private ComponentMapper<Box2DComponent> boxm = ComponentMapper.getFor(Box2DComponent.class);

    public LifetimeSystem(World world)
    {
        super(Family.all(LifetimeComponent.class).get());
        this.world = world;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {
        LifetimeComponent lifeTime = lm.get(entity);
        lifeTime.lifetime -= deltaTime;

        if (lifeTime.lifetime <= 0)
        {

            Box2DComponent box2D = boxm.get(entity);
            
            // Remove the Box2D body from the world
            if (box2D != null && box2D.body != null)
            {
                world.destroyBody(box2D.body);
       
            }
            getEngine().removeEntity(entity);
        }

    }
    
}
