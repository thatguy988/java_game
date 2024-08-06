package io.github.thatguy988.java_game.utils;

import com.badlogic.gdx.math.Vector2;

import io.github.thatguy988.java_game.components.FacingDirectionComponent;


public class PositionUtils {
    public static Vector2 getRenderPosition(Vector2 position, float width, float height) {
        return new Vector2(position.x - width / 2, position.y - height / 2);
    }

    public static Vector2 getPhysicsBodyPosition(float width, float height) {
        return new Vector2(width / 2, height / 2);
    }

    public static Vector2 getBulletPosition(Vector2 shooterPosition, float shooterWidth, FacingDirectionComponent.Direction direction) {
        if (direction == FacingDirectionComponent.Direction.RIGHT) {
            return new Vector2(shooterPosition.x + shooterWidth / 2, shooterPosition.y);
        } else {
            return new Vector2(shooterPosition.x - shooterWidth / 2, shooterPosition.y);
        }
    }
}