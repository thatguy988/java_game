package io.github.thatguy988.java_game.systems;


import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;

import io.github.thatguy988.java_game.components.Box2DComponent;
import io.github.thatguy988.java_game.components.BulletComponent;
import io.github.thatguy988.java_game.components.GroundComponent;
import io.github.thatguy988.java_game.components.PlayerComponent;

public class CollisionSystem extends IteratingSystem implements ContactListener {
    private World world;
    private ComponentMapper<PlayerComponent> plcm =  ComponentMapper.getFor(PlayerComponent.class);
    private ComponentMapper<GroundComponent> gm = ComponentMapper.getFor(GroundComponent.class);
    private ComponentMapper<Box2DComponent> bm = ComponentMapper.getFor(Box2DComponent.class);
    private ComponentMapper<BulletComponent> bulletm = ComponentMapper.getFor(BulletComponent.class);

    public CollisionSystem(World world) {
        super(Family.all(Box2DComponent.class).get());
        this.world = world;    
    }

    public void initialize() {
        world.setContactListener(this);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
       
    }

    @Override
    public void beginContact(Contact contact) {
        //immendiate action to take when a collision happens before physics calculations happen
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        Body bodyA = fixtureA.getBody();
        Body bodyB = fixtureB.getBody();

        Entity entityA = (Entity) bodyA.getUserData();
        Entity entityB = (Entity) bodyB.getUserData();

        if (entityA == null || entityB == null) {
            return;
        }
        

        handleCollision(entityA, entityB);
        handleCollision(entityB, entityA);
    }

    private void handleCollision(Entity entityA, Entity entityB) {
        if (bulletm.has(entityA) && gm.has(entityB)) {

            handleBulletGroundCollision(entityA, entityB);
        } 
    }

    private void handleBulletGroundCollision(Entity bullet, Entity collidedEntity) {

        BulletComponent bulletComponent = bulletm.get(bullet);


        if (gm.has(collidedEntity)) {
            bulletComponent.setActiveState(false);
        }
    }


    @Override
    public void endContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        Body bodyA = fixtureA.getBody();
        Body bodyB = fixtureB.getBody();

        Entity entityA = (Entity) bodyA.getUserData();
        Entity entityB = (Entity) bodyB.getUserData();

        if (entityA == null || entityB == null) {
            return;
        }


        handleNoCollision(entityA, entityB);
        handleNoCollision(entityB, entityA);
    }


    private void handleNoCollision(Entity entityA, Entity entityB) {
        
    }
    
    

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        // Modify or cancel collision before it happens for example player has special power up cannot take damage from bullets
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        Body bodyA = fixtureA.getBody();
        Body bodyB = fixtureB.getBody();

        Entity entityA = (Entity) bodyA.getUserData();
        Entity entityB = (Entity) bodyB.getUserData();

        if (entityA == null || entityB == null) {
            return;
        }

        // Check if one entity is the player and the other is a player bullet
        boolean isPlayerAndBulletCollision =
                (plcm.has(entityA) && bulletm.has(entityB) && bulletm.get(entityB).getBulletType() == BulletComponent.BulletType.PLAYER) ||
                (plcm.has(entityB) && bulletm.has(entityA) && bulletm.get(entityA).getBulletType() == BulletComponent.BulletType.PLAYER);

        boolean isBulletAndBulletCollision = 
                (bulletm.has(entityA) && bulletm.has(entityB))||
                (bulletm.has(entityB) && bulletm.has(entityA));


        if (isPlayerAndBulletCollision) {
            
            // Ignore the collision between the player and the player's bullet
            contact.setEnabled(false);
        }

        if(isBulletAndBulletCollision)
        {
            //Ignore collision between two bullets
            contact.setEnabled(false);
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // after physics calulations
        // Respond to collision such as apply damage when bullet collides with enemy
    }
}
