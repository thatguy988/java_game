package io.github.thatguy988.java_game.systems;


import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import io.github.thatguy988.java_game.components.Box2DComponent;
import io.github.thatguy988.java_game.components.FacingDirectionComponent;
import io.github.thatguy988.java_game.components.PlayerComponent;
import io.github.thatguy988.java_game.components.VelocityComponent;
import io.github.thatguy988.java_game.components.WeaponsComponent;
import io.github.thatguy988.java_game.components.WeaponsComponent.WeaponType;


public class PlayerInputSystem extends IteratingSystem
{
    // private ComponentMapper<Shooter> sm = ComponentMapper.getFor(Shooter.class);
    private ComponentMapper<PlayerComponent> plcm = ComponentMapper.getFor(PlayerComponent.class);
    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
    private ComponentMapper<FacingDirectionComponent> fm = ComponentMapper.getFor(FacingDirectionComponent.class);
    private ComponentMapper<WeaponsComponent> wm = ComponentMapper.getFor(WeaponsComponent.class);
    private ComponentMapper<Box2DComponent> bm = ComponentMapper.getFor(Box2DComponent.class);



    public PlayerInputSystem()
    {
        super(Family.all(PlayerComponent.class, Box2DComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {
        PlayerComponent player = plcm.get(entity);
        FacingDirectionComponent facing = fm.get(entity);
        WeaponsComponent weapon = wm.get(entity);
        Box2DComponent box2d = bm.get(entity);


        box2d.playerVelocity.setZero();


        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        {
            box2d.playerVelocity.x = 200;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
        {
            box2d.playerVelocity.x = -200;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.A))
        {
            facing.direction = FacingDirectionComponent.Direction.LEFT;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D))
        {
            facing.direction = FacingDirectionComponent.Direction.RIGHT;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W))
        {
            facing.direction = FacingDirectionComponent.Direction.UP;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S))
        {
            facing.direction = FacingDirectionComponent.Direction.DOWN;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.D))
        {
            facing.direction = FacingDirectionComponent.Direction.UPRIGHT;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.A))
        {
            facing.direction = FacingDirectionComponent.Direction.UPLEFT;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.D))
        {
            facing.direction = FacingDirectionComponent.Direction.DOWNRIGHT;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.A))
        {
            facing.direction = FacingDirectionComponent.Direction.DOWNLEFT;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE))
        {
            player.setisFiring(true); 
             
        }else{
            player.setisFiring(false);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            weapon.setWeaponType(WeaponType.PISTOL);
            weapon.setFiringCooldown(0.25f);
            weapon.setBulletSpeed(800f);
            weapon.setBulletsPerShot(1);
            System.out.println("Switched to Pistol");
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            weapon.setWeaponType(WeaponType.MACHINEGUN);
            weapon.setFiringCooldown(0.1f);
            weapon.setBulletSpeed(1200f);
            weapon.setBulletsPerShot(1);
            System.out.println("Switched to Machine Gun");
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
            weapon.setWeaponType(WeaponType.SHOTGUN);
            weapon.setFiringCooldown(0.5f);
            weapon.setBulletSpeed(1000f);
            weapon.setBulletsPerShot(5);
            System.out.println("Switched to Shotgun");
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)){
            weapon.setWeaponType(WeaponType.MINIGUN);
            weapon.setFiringCooldown(0.01f);
            weapon.setBulletSpeed(1600f);
            weapon.setBulletsPerShot(1);
            System.out.println("Switched to MINIGUN");
        }
    }
    
}
