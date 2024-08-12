package io.github.thatguy988.java_game.utils;

import com.badlogic.gdx.math.Vector2;

import io.github.thatguy988.java_game.components.FacingDirectionComponent;


public class PositionUtils {
    public static Vector2 getRenderPosition(float positionx, float positiony, float width, float height) {
       return new Vector2(positionx - width / 2, positiony - height / 2);
    }

    public static Vector2 getPhysicsBodyPosition(float width, float height) {
        return new Vector2(width / 2, height / 2);
    }

    public static Vector2 getBulletPosition(Vector2 shooterPosition, float shooterWidth, float shooterHeight, FacingDirectionComponent.Direction direction) {
        if (direction == FacingDirectionComponent.Direction.RIGHT) {
            return new Vector2(shooterPosition.x + shooterWidth / 2, shooterPosition.y);
        } else if (direction == FacingDirectionComponent.Direction.LEFT) {
            return new Vector2(shooterPosition.x - shooterWidth / 2, shooterPosition.y);
        } else if (direction == FacingDirectionComponent.Direction.UP){
            return new Vector2(shooterPosition.x, shooterPosition.y + shooterHeight / 2);
        } else if (direction == FacingDirectionComponent.Direction.DOWN){
            return new Vector2(shooterPosition.x, shooterPosition.y - shooterHeight / 2);
        }else if (direction == FacingDirectionComponent.Direction.UPRIGHT){
            return new Vector2(shooterPosition.x + shooterWidth/2, shooterPosition.y + shooterHeight/2);
        }else if (direction == FacingDirectionComponent.Direction.UPLEFT){
            return new Vector2(shooterPosition.x - shooterWidth/2, shooterPosition.y+shooterHeight/2);
        }else if (direction == FacingDirectionComponent.Direction.DOWNRIGHT){
            return new Vector2(shooterPosition.x + shooterWidth/2, shooterPosition.y - shooterHeight/2);
        }else//downleft
        {
            return new Vector2(shooterPosition.x - shooterWidth/2, shooterPosition.y - shooterHeight /2);
        }
    }
}