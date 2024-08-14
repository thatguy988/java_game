package io.github.thatguy988.java_game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import io.github.thatguy988.java_game.components.Box2DComponent;
import io.github.thatguy988.java_game.components.BulletComponent;
import io.github.thatguy988.java_game.components.PlayerComponent;



public class PhysicsSystem extends IteratingSystem
{
    private World world;
    private ComponentMapper<Box2DComponent> bm = ComponentMapper.getFor(Box2DComponent.class);
    private ComponentMapper<PlayerComponent> plcm = ComponentMapper.getFor(PlayerComponent.class);
    private ComponentMapper<BulletComponent> bulletm = ComponentMapper.getFor(BulletComponent.class);
    public PhysicsSystem(World world)
    {
        super(Family.all(Box2DComponent.class).get());
        this.world = world;        
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Box2DComponent box2D = bm.get(entity);
        PlayerComponent player = plcm.get(entity);
        BulletComponent bullet = bulletm.get(entity);
       

        if(player != null)
        {
            // Calculate desired velocity based on player input
            Vector2 desiredVelocity = new Vector2(box2D.playerVelocity);

            // Get the current velocity of the body
            Vector2 currentVelocity = box2D.body.getLinearVelocity();

            // Combine desired velocity with the current velocity
            Vector2 velocityChange = desiredVelocity.sub(currentVelocity);

            // Apply the velocity change to the body
            box2D.body.applyLinearImpulse(velocityChange, box2D.body.getWorldCenter(), true);
        }

        if(bullet != null)
        {
            //
            if(box2D.body.getType() == BodyDef.BodyType.KinematicBody)
            {
                //do nothing to kinematic bodies
            }else if(box2D.body.getType() == BodyDef.BodyType.DynamicBody)
            {
                //do something to dynamic bodies
            }

        }


    }

    @Override
    public void update(float deltaTime) {
        world.step(deltaTime, 6, 2); 
        super.update(deltaTime);
    }

    
}
