package io.github.thatguy988.java_game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;

import io.github.thatguy988.java_game.components.Box2DComponent;
import io.github.thatguy988.java_game.components.FacingDirectionComponent;
import io.github.thatguy988.java_game.components.PlayerComponent;
import io.github.thatguy988.java_game.components.WeaponsComponent;



public class RecoilSystem extends IteratingSystem
{
    private ComponentMapper<Box2DComponent> bm = ComponentMapper.getFor(Box2DComponent.class);
    private ComponentMapper<PlayerComponent> plcm = ComponentMapper.getFor(PlayerComponent.class);
    private ComponentMapper<WeaponsComponent> wm = ComponentMapper.getFor(WeaponsComponent.class);
    private ComponentMapper<FacingDirectionComponent> fm = ComponentMapper.getFor(FacingDirectionComponent.class);

    public RecoilSystem()
    {
        super(Family.all(Box2DComponent.class,PlayerComponent.class,WeaponsComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {
        PlayerComponent player = plcm.get(entity);
        // recoil only applied when bullets are shot
        if (player.getRecoilTriggered())
        {
            WeaponsComponent weapon = wm.get(entity);
            Box2DComponent box2dbody = bm.get(entity);
            FacingDirectionComponent facing = fm.get(entity);
            applyRecoil(weapon,box2dbody,facing.direction);

            player.setRecoilTriggered(false);


        }
    }

    private void applyRecoil(WeaponsComponent weapon, Box2DComponent playerBody, FacingDirectionComponent.Direction direction)
    {
        float recoilSpeed = calculateRecoil(weapon);
        Vector2 recoilDirection = new Vector2 (0,0);
        if(direction == FacingDirectionComponent.Direction.RIGHT){
            recoilDirection = new Vector2(-1,0);
        }else if (direction == FacingDirectionComponent.Direction.LEFT){
            recoilDirection = new Vector2(1,0);
        }else if (direction == FacingDirectionComponent.Direction.UP){
            recoilDirection = new Vector2(0,-1);
        }else if (direction == FacingDirectionComponent.Direction.DOWN){
            recoilDirection = new Vector2(0,1);
        }else if (direction == FacingDirectionComponent.Direction.UPRIGHT){
            recoilDirection = new Vector2(-1,-1);
        }else if (direction == FacingDirectionComponent.Direction.UPLEFT){
            recoilDirection = new Vector2(1,-1);
        }else if (direction == FacingDirectionComponent.Direction.DOWNRIGHT){
            recoilDirection = new Vector2(-1,1);
        }else if (direction == FacingDirectionComponent.Direction.DOWNLEFT){
            recoilDirection = new Vector2(1,1);
        }

        // Add recoil effect to velocity
        playerBody.playerVelocity.add(recoilDirection.scl(recoilSpeed));
    }


    private float calculateRecoil(WeaponsComponent weapon)
     {
        switch (weapon.getWeaponType()) {
            case SHOTGUN:
                return 1875.0f; 
            case MACHINEGUN:
                return 1000.0f; 
            case MINIGUN:
                return 1250.0f;
            default:
                return 0;
        }
    }
    
}
