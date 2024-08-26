package io.github.thatguy988.java_game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;

import io.github.thatguy988.java_game.components.Box2DComponent;
import io.github.thatguy988.java_game.components.CameraComponent;

public class CameraSystem extends IteratingSystem {
    private ComponentMapper<Box2DComponent> bm = ComponentMapper.getFor(Box2DComponent.class);
    private ComponentMapper<CameraComponent> cm = ComponentMapper.getFor(CameraComponent.class);
    private Entity player;

    public CameraSystem(Entity player) {
        super(Family.all(CameraComponent.class).get());
        this.player = player;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CameraComponent cameraComponent = cm.get(entity);

        // Assuming there's a player entity to follow
        //Entity player = getEngine().getEntitiesFor(Family.all(PlayerComponent.class, Box2DComponent.class).get()).first();
        if (player != null) {
            Box2DComponent playerBox2D = bm.get(player);
            Vector2 playerPosition = playerBox2D.body.getPosition();
            cameraComponent.camera.position.set(playerPosition.x, playerPosition.y, 0);
            cameraComponent.camera.update();
        }
    }
}
