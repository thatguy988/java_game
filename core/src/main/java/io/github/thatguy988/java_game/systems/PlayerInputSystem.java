package io.github.thatguy988.java_game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import io.github.thatguy988.java_game.components.FacingDirectionComponent;
import io.github.thatguy988.java_game.components.PlayerComponent;
import io.github.thatguy988.java_game.components.VelocityComponent;


public class PlayerInputSystem extends IteratingSystem
{
    // private ComponentMapper<Shooter> sm = ComponentMapper.getFor(Shooter.class);
    private ComponentMapper<PlayerComponent> plcm = ComponentMapper.getFor(PlayerComponent.class);
    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
    private ComponentMapper<FacingDirectionComponent> fm = ComponentMapper.getFor(FacingDirectionComponent.class);


    public PlayerInputSystem()
    {
        super(Family.all(PlayerComponent.class, VelocityComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {
        PlayerComponent player = plcm.get(entity);
        VelocityComponent velocity = vm.get(entity);
        FacingDirectionComponent facing = fm.get(entity);

        velocity.x = 0;
        player.setisFiring(false);

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
        {
            velocity.x = -200;
            facing.direction = FacingDirectionComponent.Direction.LEFT;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        {
            velocity.x = 200;
            facing.direction = FacingDirectionComponent.Direction.RIGHT;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE))
        {
            player.setisFiring(true);  
        }
    }
    
}
