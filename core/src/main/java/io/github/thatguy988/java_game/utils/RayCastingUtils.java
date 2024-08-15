// File: io.github.thatguy988.java_game.utils.RaycastingUtils.java

package io.github.thatguy988.java_game.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef;

public class RayCastingUtils {

    public static boolean isPlayerOnGround(World world, Vector2 position, float rayLength) {
        final boolean[] hitGround = {false};

        RayCastCallback callback = new RayCastCallback() {
            @Override
            public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
                if (fixture.getBody().getType() == BodyDef.BodyType.StaticBody) {
                    hitGround[0] = true;
                    return 0; // Stop the raycast
                }
                return 1; // Continue the raycast
            }
        };

        // Cast a ray from the player's feet downward
        world.rayCast(callback, position, position.cpy().sub(0, rayLength));

        return hitGround[0];
    }
}
