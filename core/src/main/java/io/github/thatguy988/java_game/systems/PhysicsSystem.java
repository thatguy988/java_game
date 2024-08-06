package io.github.thatguy988.java_game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import io.github.thatguy988.java_game.components.Box2DComponent;
import io.github.thatguy988.java_game.components.PositionComponent;

public class PhysicsSystem extends IteratingSystem
{
    private World world;
    private ComponentMapper<Box2DComponent> bm = ComponentMapper.getFor(Box2DComponent.class);
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);

    public PhysicsSystem(World world)
    {
        super(Family.all(Box2DComponent.class, PositionComponent.class).get());
        this.world = world;        
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Box2DComponent box2D = bm.get(entity);
        PositionComponent position = pm.get(entity);

        // Update entity position from Box2D body
        Vector2 bodyPosition = box2D.body.getPosition();
        position.x = bodyPosition.x;
        position.y = bodyPosition.y;

        box2D.body.setLinearVelocity(box2D.playerVelocity);


    }

    @Override
    public void update(float deltaTime) {
        world.step(deltaTime, 6, 2); // Update physics world
        super.update(deltaTime);
    }

    
}
