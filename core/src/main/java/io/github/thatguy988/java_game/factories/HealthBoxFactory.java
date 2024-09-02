package io.github.thatguy988.java_game.factories;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import io.github.thatguy988.java_game.components.Box2DComponent;
import io.github.thatguy988.java_game.components.HealthBoxComponent;
import io.github.thatguy988.java_game.utils.PositionUtils;



public class HealthBoxFactory
{
    private Engine engine;
    private World physicsWorld;

    
    public HealthBoxFactory(Engine engine, World physicsWorld)
    {
        this.engine = engine;
        this.physicsWorld = physicsWorld;

    }

    public Entity createHealthBox(float boxSpawnX, float boxSpawnY) {
        Entity healthbox = engine.createEntity();

        int randomHealthvalue = generateRandomHealth(); 

        
        HealthBoxComponent healthboxComponent = new HealthBoxComponent(randomHealthvalue);
        healthbox.add(healthboxComponent);

        // define Box2D body for healthbox
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(boxSpawnX, boxSpawnY);
        Body body = physicsWorld.createBody(bodyDef);

        com.badlogic.gdx.math.Vector2 bodyPositions = PositionUtils.getPhysicsBodyPosition(healthboxComponent.getWidth(), healthboxComponent.getHeight());

        PolygonShape rectangle = new PolygonShape();
        rectangle.setAsBox(bodyPositions.x , bodyPositions.y);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = rectangle;
        fixtureDef.density = 1.0f; // mass and inertia
        fixtureDef.friction = 0.3f; // friction when sliding along surfaces
        fixtureDef.restitution = 0.0f; // no bounce

        body.createFixture(fixtureDef);
        rectangle.dispose();

        body.setFixedRotation(true);
        body.setLinearDamping(5.0f);
        body.setUserData(healthbox); 


        healthbox.add(new Box2DComponent(body));

        return healthbox;
    }

    private int generateRandomHealth() {
        return MathUtils.random(41) + 10; 
    }
    
}
