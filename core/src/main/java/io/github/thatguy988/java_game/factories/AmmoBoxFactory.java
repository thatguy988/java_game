package io.github.thatguy988.java_game.factories;


import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import io.github.thatguy988.java_game.components.AmmoBoxComponent;
import io.github.thatguy988.java_game.components.Box2DComponent;
import io.github.thatguy988.java_game.utils.PositionUtils;
import io.github.thatguy988.java_game.utils.WeaponType;




public class AmmoBoxFactory
{
    private Engine engine;
    private World physicsWorld;
    

    
    public AmmoBoxFactory(Engine engine, World physicsWorld)
    {
        this.engine = engine;
        this.physicsWorld = physicsWorld;

    }

    public Entity createAmmoBox(float boxSpawnX, float boxSpawnY, WeaponType type) {
        Entity ammobox = engine.createEntity();

        AmmoBoxComponent ammoboxComponent = new AmmoBoxComponent(type);
        ammobox.add(ammoboxComponent);

        // define Box2D body for ammobox
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(boxSpawnX, boxSpawnY);
        Body body = physicsWorld.createBody(bodyDef);

        com.badlogic.gdx.math.Vector2 bodyPositions = PositionUtils.getPhysicsBodyPosition(ammoboxComponent.getWidth(), ammoboxComponent.getHeight());

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
        body.setUserData(ammobox); 


        ammobox.add(new Box2DComponent(body));

        return ammobox;
    }

    
}
