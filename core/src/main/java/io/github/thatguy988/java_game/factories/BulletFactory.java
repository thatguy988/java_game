package io.github.thatguy988.java_game.factories;

import java.util.Random;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;

import io.github.thatguy988.java_game.components.BulletComponent;
import io.github.thatguy988.java_game.components.FacingDirectionComponent;
import io.github.thatguy988.java_game.components.LifetimeComponent;
import io.github.thatguy988.java_game.components.PositionComponent;
import io.github.thatguy988.java_game.components.Shooter;
import io.github.thatguy988.java_game.components.VelocityComponent;
import io.github.thatguy988.java_game.components.WeaponsComponent;
import io.github.thatguy988.java_game.utils.PositionUtils;

public class BulletFactory {
    private Engine engine;
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private Random random = new Random();



    public BulletFactory(Engine engine) {
        this.engine = engine;
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

        }
    }


    public Entity createSingleBullet(Entity entity, Shooter shooter, FacingDirectionComponent.Direction direction, float bulletspeed) {
        Entity bullet = engine.createEntity();


        PositionComponent shooter_position = pm.get(entity);
        
        com.badlogic.gdx.math.Vector2 bulletPositions = PositionUtils.getBulletPosition(new com.badlogic.gdx.math.Vector2(shooter_position.x, shooter_position.y), shooter.getWidth(), direction);

        PositionComponent position = new PositionComponent(bulletPositions.x, bulletPositions.y);

        
        bullet.add(position);

        VelocityComponent velocity = new VelocityComponent();
        velocity.x = direction == FacingDirectionComponent.Direction.RIGHT ? bulletspeed : -bulletspeed;
        bullet.add(velocity);

        BulletComponent bulletComponent = new BulletComponent();
        bulletComponent.speed = bulletspeed;
        bullet.add(bulletComponent);

        bullet.add(new LifetimeComponent(2f));

        engine.addEntity(bullet);
        return bullet;
    }

    private void createMachineGunSpread(Entity entity, Shooter shooter, FacingDirectionComponent.Direction direction, float bulletSpeed) {
        float spreadRange = 10.0f; 
        float angleOffset = (random.nextFloat() - 0.5f) * spreadRange; 

        createMachineGunBullet(entity, shooter, direction, bulletSpeed, angleOffset);
    }

    private void createMachineGunBullet(Entity entity, Shooter shooter, FacingDirectionComponent.Direction direction, float bulletSpeed, float angleOffset) {
        Entity bullet = engine.createEntity();


        PositionComponent shooterPosition = pm.get(entity);

        com.badlogic.gdx.math.Vector2 bulletPositions = PositionUtils.getBulletPosition(new com.badlogic.gdx.math.Vector2(shooterPosition.x, shooterPosition.y), shooter.getWidth(), direction);

        

        PositionComponent bulletPosition = new PositionComponent(bulletPositions.x, bulletPositions.y);


        bullet.add(bulletPosition);

        float baseAngle = (direction == FacingDirectionComponent.Direction.RIGHT) ? 0 : 180;
        float bulletAngle = baseAngle + angleOffset;

        float velocityX = bulletSpeed * (float) Math.cos(Math.toRadians(bulletAngle));
        float velocityY = bulletSpeed * (float) Math.sin(Math.toRadians(bulletAngle));

        VelocityComponent velocity = new VelocityComponent();
        velocity.x = velocityX;
        velocity.y = velocityY;
        bullet.add(velocity);

        BulletComponent bulletComponent = new BulletComponent();
        bulletComponent.speed = bulletSpeed;
        bullet.add(bulletComponent);

        bullet.add(new LifetimeComponent(2f));

        engine.addEntity(bullet);
    }


    private void createShotgunSpread(Entity entity, Shooter shooter, FacingDirectionComponent.Direction direction, WeaponsComponent weapon)
    {
        int numberofbullets = weapon.getBulletsPerShot();
        float spreadAngle = 15.0f;
        float anglebetweenbullets = spreadAngle / (numberofbullets - 1);

        for (int i = 0; i < numberofbullets; i++) {
            float angleOffset = (i - (numberofbullets - 1) / 2f) * anglebetweenbullets;
            float bulletAngle = direction == FacingDirectionComponent.Direction.RIGHT ? angleOffset : 180 + angleOffset;

            float bulletSpeed = weapon.getBulletSpeed();

            float bulletVelocityX = bulletSpeed * (float) Math.cos(Math.toRadians(bulletAngle));
            float bulletVelocityY = bulletSpeed * (float) Math.sin(Math.toRadians(bulletAngle));

            createShotgunBullets(entity, shooter, direction, bulletVelocityX, bulletVelocityY);
        }

    }

    private void createShotgunBullets(Entity entity, Shooter shooter, FacingDirectionComponent.Direction direction, float velocityX, float velocityY) {
        Entity bullet = engine.createEntity();


        PositionComponent shooterPosition = pm.get(entity);

        com.badlogic.gdx.math.Vector2 bulletPositions = PositionUtils.getBulletPosition(new com.badlogic.gdx.math.Vector2(shooterPosition.x, shooterPosition.y), shooter.getWidth(), direction);

        PositionComponent bulletPosition = new PositionComponent(bulletPositions.x, bulletPositions.y);


        bullet.add(bulletPosition);

        VelocityComponent velocity = new VelocityComponent();
        velocity.x = velocityX;
        velocity.y = velocityY;
        bullet.add(velocity);

        BulletComponent bulletComponent = new BulletComponent();
        bulletComponent.speed = (float) Math.sqrt(velocityX * velocityX + velocityY * velocityY);
        bullet.add(bulletComponent);

        bullet.add(new LifetimeComponent(2f));

        engine.addEntity(bullet);
    }
}