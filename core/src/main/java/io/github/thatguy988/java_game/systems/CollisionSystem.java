package io.github.thatguy988.java_game.systems;


import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;

import io.github.thatguy988.java_game.components.AmmoBoxComponent;
import io.github.thatguy988.java_game.components.AmmoCounterComponent;
import io.github.thatguy988.java_game.components.Box2DComponent;
import io.github.thatguy988.java_game.components.BulletComponent;
import io.github.thatguy988.java_game.components.EnemyComponent;
import io.github.thatguy988.java_game.components.GroundComponent;
import io.github.thatguy988.java_game.components.HealthBoxComponent;
import io.github.thatguy988.java_game.components.HealthComponent;
import io.github.thatguy988.java_game.components.PlayerComponent;
import io.github.thatguy988.java_game.utils.WeaponType;

public class CollisionSystem extends IteratingSystem implements ContactListener {
    private World world;
    private ComponentMapper<PlayerComponent> plcm =  ComponentMapper.getFor(PlayerComponent.class);
    private ComponentMapper<GroundComponent> gm = ComponentMapper.getFor(GroundComponent.class);
    private ComponentMapper<Box2DComponent> bm = ComponentMapper.getFor(Box2DComponent.class);
    private ComponentMapper<BulletComponent> bulletm = ComponentMapper.getFor(BulletComponent.class);
    private ComponentMapper<HealthBoxComponent> hbm = ComponentMapper.getFor(HealthBoxComponent.class);
    private ComponentMapper<HealthComponent> playerhp = ComponentMapper.getFor(HealthComponent.class);
    private ComponentMapper<EnemyComponent> em = ComponentMapper.getFor(EnemyComponent.class);
    private ComponentMapper<AmmoBoxComponent> abm = ComponentMapper.getFor(AmmoBoxComponent.class);
    private ComponentMapper<AmmoCounterComponent> acm = ComponentMapper.getFor(AmmoCounterComponent.class);

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

        boolean isBulletAndGroundCollision =
                (bulletm.has(entityA) && gm.has(entityB))||
                (bulletm.has(entityB) && gm.has(entityA));

        if(isBulletAndGroundCollision)
        {
            Entity bulletEntity = bulletm.has(entityA) ? entityA : entityB;
            //Entity ground = gm.has(entityA) ? entityA : entityB;

            BulletComponent bulletComponent = bulletm.get(bulletEntity);
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

        // // Check if one entity is the player and the other is a player bullet
        boolean isPlayerAndPlayerBulletCollision =
                (plcm.has(entityA) && bulletm.has(entityB) && bulletm.get(entityB).getBulletType() == BulletComponent.BulletType.PLAYER) ||
                (plcm.has(entityB) && bulletm.has(entityA) && bulletm.get(entityA).getBulletType() == BulletComponent.BulletType.PLAYER);

        
        boolean isEnemyAndEnemyBulletCollision = 
                (em.has(entityA) && bulletm.has(entityB) && bulletm.get(entityB).getBulletType() == BulletComponent.BulletType.ENEMY) ||
                (em.has(entityB) && bulletm.has(entityA) && bulletm.get(entityA).getBulletType() == BulletComponent.BulletType.ENEMY);
        
        
        boolean isBulletAndBulletCollision = 
                (bulletm.has(entityA) && bulletm.has(entityB))||
                (bulletm.has(entityB) && bulletm.has(entityA));

        boolean isPlayerAndHealthBoxCollision = 
                (plcm.has(entityA) && hbm.has(entityB))||
                (plcm.has(entityB) && hbm.has(entityA));

        boolean isEnemyAndHealthBoxCollision = 
                (em.has(entityA) && hbm.has(entityB))||
                (em.has(entityB) && hbm.has(entityA));

        boolean isBulletAndHealthBoxCollision =
                (bulletm.has(entityA) && hbm.has(entityB))||
                (bulletm.has(entityB) && hbm.has(entityA));

        boolean isPlayerAndAmmoBoxCollision = 
                (plcm.has(entityA) && abm.has(entityB))||
                (plcm.has(entityB) && abm.has(entityA));

        boolean isEnemyAndAmmoBoxCollision = 
                (em.has(entityA) && abm.has(entityB))||
                (em.has(entityB) && abm.has(entityA));
        
        boolean isBulletAndAmmoBoxCollisiion = 
                (bulletm.has(entityA) && abm.has(entityB))||
                (bulletm.has(entityB) && abm.has(entityA));
        


        if (isPlayerAndPlayerBulletCollision || isBulletAndBulletCollision || 
            isEnemyAndHealthBoxCollision || isEnemyAndEnemyBulletCollision || 
            isBulletAndHealthBoxCollision || isEnemyAndAmmoBoxCollision ||
            isBulletAndAmmoBoxCollisiion) { 
            // ignore the collision between objects
            contact.setEnabled(false);
            return;
        }

       
        if(isPlayerAndHealthBoxCollision)
        {
            //figure out what is entityA and what is entityB
            Entity playerEntity = plcm.has(entityA) ? entityA:entityB;
            Entity healthboxEntity = hbm.has(entityA) ? entityA:entityB;

            HealthComponent playerHealth = playerhp.get(playerEntity);
            HealthBoxComponent healthbox = hbm.get(healthboxEntity);
            if(playerHealth.getCurrentHealth()>=playerHealth.getMaxHealth())
            {
                contact.setEnabled(false);
            }else
            {
                playerHealth.addCurrentHealth(healthbox.getHealth());
                healthbox.setActiveState(false);

            }
            return;

        }

        if(isPlayerAndAmmoBoxCollision)
        {
            //figure out entity is player and which is ammobox
            Entity playerEntity = plcm.has(entityA) ? entityA:entityB;
            Entity ammoBoxEntity = abm.has(entityA) ? entityA:entityB;

            AmmoCounterComponent playerAmmo = acm.get(playerEntity);
            AmmoBoxComponent ammoBox = abm.get(ammoBoxEntity);
            WeaponType ammoBoxWeapon = ammoBox.getWeaponType();
            if(playerAmmo.getCurrentAmmo(ammoBoxWeapon)>=playerAmmo.getMaxAmmo(ammoBoxWeapon))
            {
                contact.setEnabled(false);
            }else
            {
                playerAmmo.addAmmo(ammoBoxWeapon, ammoBox.getAmmo()); //pass weapon type and the amount of ammo for weapon type
                ammoBox.setActiveState(false);
            }
            return;
            
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        Body bodyA = fixtureA.getBody();
        Body bodyB = fixtureB.getBody();

        Entity entityA = (Entity) bodyA.getUserData();
        Entity entityB = (Entity) bodyB.getUserData();

        if (entityA == null || entityB == null) {
            return;
        }

        boolean isPlayerAndEnemyBulletCollision =
                (plcm.has(entityA) && bulletm.has(entityB) && bulletm.get(entityB).getBulletType() == BulletComponent.BulletType.ENEMY) ||
                (plcm.has(entityB) && bulletm.has(entityA) && bulletm.get(entityA).getBulletType() == BulletComponent.BulletType.ENEMY);
        
        boolean isEnemyAndPlayerBulletCollision = 
                (em.has(entityA) && bulletm.has(entityB) && bulletm.get(entityB).getBulletType() == BulletComponent.BulletType.PLAYER) ||
                (em.has(entityB) && bulletm.has(entityA) && bulletm.get(entityA).getBulletType() == BulletComponent.BulletType.PLAYER);




        if(isPlayerAndEnemyBulletCollision)
        {
        
        }

        if(isEnemyAndPlayerBulletCollision)
        {
            Entity enemyEntity = em.has(entityA) ? entityA : entityB;
            Entity bulletEntity = bulletm.has(entityA) ? entityA : entityB;

            Box2DComponent bulletBox2D = bm.get(bulletEntity);
            Vector2 knockbackForce = bulletBox2D.body.getLinearVelocity().cpy().scl(10f); // control knockback strength
            em.get(enemyEntity).setKnockBackForce(knockbackForce);

            BulletComponent bulletComponent = bulletm.get(bulletEntity);
            bulletComponent.setActiveState(false);

        }
        // after physics calulations
        // Respond to collision such as apply damage when bullet collides with enemy
    }
}
