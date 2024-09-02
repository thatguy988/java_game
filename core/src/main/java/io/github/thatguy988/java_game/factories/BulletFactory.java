package io.github.thatguy988.java_game.factories;


import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import io.github.thatguy988.java_game.components.Box2DComponent;
import io.github.thatguy988.java_game.components.BulletComponent;
import io.github.thatguy988.java_game.components.FacingDirectionComponent;
import io.github.thatguy988.java_game.components.LifetimeComponent;
import io.github.thatguy988.java_game.components.Shooter;
import io.github.thatguy988.java_game.components.WeaponsComponent;
import io.github.thatguy988.java_game.utils.PositionUtils;


public class BulletFactory {
    private Engine engine;
    private World physicsWorld;
    private ComponentMapper<Box2DComponent> bm = ComponentMapper.getFor(Box2DComponent.class);



    public BulletFactory(Engine engine, World physicsWorld) {
        this.engine = engine;
        this.physicsWorld = physicsWorld;
    }

    public void createBullet(Entity entity, Shooter shooter, FacingDirectionComponent.Direction direction, WeaponsComponent weapon)
    {
        switch (weapon.getWeaponType())
        {
            case PISTOL:
                createSingleBullet(entity, shooter, direction, weapon.getBulletSpeed());
                break;
            case MACHINEGUN:
                createMachineGunSpread(entity, shooter, direction, weapon.getBulletSpeed());
                break;
            case SHOTGUN:
                createShotgunSpread(entity, shooter, direction, weapon);
                break;
            case MINIGUN:
                createMachineGunSpread(entity, shooter, direction, weapon.getBulletSpeed());
                break;

        }
    }


    public Entity createSingleBullet(Entity entity, Shooter shooter, FacingDirectionComponent.Direction direction, float bulletspeed) {
        Entity bullet = engine.createEntity();

        Box2DComponent shooter_body = bm.get(entity);

        Vector2 shooter_position = shooter_body.body.getPosition();

        
        com.badlogic.gdx.math.Vector2 bulletPositions = PositionUtils.getBulletPosition(new com.badlogic.gdx.math.Vector2(shooter_position.x, shooter_position.y), shooter.getWidth(), shooter.getHeight(), direction);


        // Box2D body for bullet
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody; 
        bodyDef.position.set(bulletPositions);  
        Body body = physicsWorld.createBody(bodyDef);
        body.setGravityScale(0);

        //body.setBullet(true);

        
        CircleShape circle = new CircleShape();
        circle.setRadius(3.0f);


        // Define fixture for the bullet
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0f;  
        fixtureDef.restitution = 0f; 

        body.createFixture(fixtureDef);
        circle.dispose();

        // Set the bullet velocity based on direction
        Vector2 bulletVelocity = new Vector2();

        if (direction == FacingDirectionComponent.Direction.RIGHT)
        {
            bulletVelocity.set(bulletspeed, 0);

        }else if(direction == FacingDirectionComponent.Direction.LEFT)
        {
            bulletVelocity.set(-bulletspeed, 0);

        }else if(direction == FacingDirectionComponent.Direction.UP)
        {
            bulletVelocity.set(0, bulletspeed);

        }else if(direction == FacingDirectionComponent.Direction.DOWN)
        {
            bulletVelocity.set(0, -bulletspeed);

        }else if(direction == FacingDirectionComponent.Direction.UPRIGHT)
        {
            bulletVelocity.set(bulletspeed, bulletspeed);
        }else if(direction == FacingDirectionComponent.Direction.UPLEFT)
        {
            bulletVelocity.set(-bulletspeed, bulletspeed);
        }else if(direction == FacingDirectionComponent.Direction.DOWNRIGHT)
        {
            bulletVelocity.set(bulletspeed, -bulletspeed);
        }else if(direction == FacingDirectionComponent.Direction.DOWNLEFT)
        {
            bulletVelocity.set(-bulletspeed, -bulletspeed);
        }
        body.setLinearVelocity(bulletVelocity);
        body.setUserData(bullet);


        bullet.add(new Box2DComponent(body));
        bullet.add(new BulletComponent(BulletComponent.BulletType.PLAYER));
        bullet.add(new LifetimeComponent(2.0f));


        engine.addEntity(bullet);


        return bullet;
    }

    private void createMachineGunSpread(Entity entity, Shooter shooter, FacingDirectionComponent.Direction direction, float bulletSpeed) {
        float spreadRange = 10.0f; 
        float angleOffset = (MathUtils.random() - 0.5f) * spreadRange; 

        createMachineGunBullet(entity, shooter, direction, bulletSpeed, angleOffset);
    }

    private void createMachineGunBullet(Entity entity, Shooter shooter, FacingDirectionComponent.Direction direction, float bulletSpeed, float angleOffset) {
        Entity bullet = engine.createEntity();


        Box2DComponent shooter_body = bm.get(entity);

        Vector2 shooterPosition = shooter_body.body.getPosition();



        com.badlogic.gdx.math.Vector2 bulletPositions = PositionUtils.getBulletPosition(new com.badlogic.gdx.math.Vector2(shooterPosition.x, shooterPosition.y), shooter.getWidth(), shooter.getHeight(), direction);

        // Box2D body for bullet
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody; 
        bodyDef.position.set(bulletPositions);  
        Body body = physicsWorld.createBody(bodyDef);
        body.setGravityScale(0);


        
        CircleShape circle = new CircleShape();
        circle.setRadius(3.0f);



        // Define fixture for the bullet
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0f;  
        fixtureDef.restitution = 0f;  

        body.createFixture(fixtureDef);
        circle.dispose();

        // Set the bullet velocity based on direction
        Vector2 bulletVelocity = new Vector2();

        float baseAngle = 0;

        if (direction == FacingDirectionComponent.Direction.RIGHT)
        {
            baseAngle = 0;
        }else if(direction == FacingDirectionComponent.Direction.LEFT)
        {
            baseAngle = 180;
        }else if(direction == FacingDirectionComponent.Direction.UP)
        {
            baseAngle = 90;
        }else if(direction == FacingDirectionComponent.Direction.DOWN)
        {
            baseAngle = 270;
        } else if(direction == FacingDirectionComponent.Direction.UPRIGHT)
        {
            baseAngle = 45;
        }else if (direction == FacingDirectionComponent.Direction.UPLEFT)
        {
            baseAngle = 135;
        }else if (direction == FacingDirectionComponent.Direction.DOWNRIGHT)
        {
            baseAngle = 315;
        }else if (direction == FacingDirectionComponent.Direction.DOWNLEFT)
        {
            baseAngle = 225;
        }

        float bulletAngle = baseAngle + angleOffset;

        float velocityX = bulletSpeed * (float) Math.cos(Math.toRadians(bulletAngle));
        float velocityY = bulletSpeed * (float) Math.sin(Math.toRadians(bulletAngle));

        bulletVelocity.set(velocityX, velocityY);


        body.setLinearVelocity(bulletVelocity);
        body.setUserData(bullet);


        bullet.add(new Box2DComponent(body));
        bullet.add(new BulletComponent(BulletComponent.BulletType.PLAYER));
        bullet.add(new LifetimeComponent(10f));



        engine.addEntity(bullet);
    }


    private void createShotgunSpread(Entity entity, Shooter shooter, FacingDirectionComponent.Direction direction, WeaponsComponent weapon)
    {
        int numberofbullets = weapon.getBulletsPerShot();
        float spreadAngle = 15.0f;
        float anglebetweenbullets = spreadAngle / (numberofbullets - 1);

        for (int i = 0; i < numberofbullets; i++) {
            float angleOffset = (i - (numberofbullets - 1) / 2f) * anglebetweenbullets;
            float bulletAngle = angleOffset;
            if (direction == FacingDirectionComponent.Direction.RIGHT)
            {
                bulletAngle = angleOffset;

            }else if(direction == FacingDirectionComponent.Direction.LEFT)
            {
                bulletAngle = 180 + angleOffset;

            }else if(direction == FacingDirectionComponent.Direction.UP)
            {
                bulletAngle = 90 + angleOffset;

            }else if(direction == FacingDirectionComponent.Direction.DOWN)
            {
                bulletAngle = 270 + angleOffset;
            }else if(direction == FacingDirectionComponent.Direction.UPRIGHT)
            {
                bulletAngle = 45 + angleOffset;
            }else if (direction == FacingDirectionComponent.Direction.UPLEFT)
            {
                bulletAngle = 135 + angleOffset;
            }else if (direction == FacingDirectionComponent.Direction.DOWNRIGHT)
            {
                bulletAngle = 315 + angleOffset;
            }else if (direction == FacingDirectionComponent.Direction.DOWNLEFT)
            {
                bulletAngle = 225 + angleOffset;
            }

            float bulletSpeed = weapon.getBulletSpeed();

            float bulletVelocityX = bulletSpeed * (float) Math.cos(Math.toRadians(bulletAngle));
            float bulletVelocityY = bulletSpeed * (float) Math.sin(Math.toRadians(bulletAngle));

            createShotgunBullets(entity, shooter, direction, bulletVelocityX, bulletVelocityY);
        }

    }

    private void createShotgunBullets(Entity entity, Shooter shooter, FacingDirectionComponent.Direction direction, float velocityX, float velocityY) {
        Entity bullet = engine.createEntity();

        Box2DComponent shooter_body = bm.get(entity);

        Vector2 shooterPosition = shooter_body.body.getPosition();



        com.badlogic.gdx.math.Vector2 bulletPositions = PositionUtils.getBulletPosition(new com.badlogic.gdx.math.Vector2(shooterPosition.x, shooterPosition.y), shooter.getWidth(), shooter.getHeight(), direction);


        // Box2D body for bullet
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody; 
        bodyDef.position.set(bulletPositions);  
        Body body = physicsWorld.createBody(bodyDef);
        body.setGravityScale(0);


        
        CircleShape circle = new CircleShape();
        circle.setRadius(3.0f);



        // Define fixture for the bullet
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0f;  
        fixtureDef.restitution = 0f;  

        body.createFixture(fixtureDef);
        circle.dispose();

        // Set the bullet velocity based on direction
        Vector2 bulletVelocity = new Vector2();


        bulletVelocity.set(velocityX, velocityY);


        body.setLinearVelocity(bulletVelocity);

        bullet.add(new Box2DComponent(body));
        bullet.add(new BulletComponent(BulletComponent.BulletType.PLAYER));
        bullet.add(new LifetimeComponent(2f));

        body.setUserData(bullet);

        engine.addEntity(bullet);
    }
}