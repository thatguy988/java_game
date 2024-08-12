package io.github.thatguy988.java_game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import io.github.thatguy988.java_game.components.CameraComponent;
import io.github.thatguy988.java_game.components.PositionComponent;

public class CameraSystem extends IteratingSystem {
    private final Vector2 mapSize;
    private final Vector2 viewportSize;
    private Entity playerEntity; // Store player entity for tracking

    private ComponentMapper<CameraComponent> cameraMapper = ComponentMapper.getFor(CameraComponent.class);

    public CameraSystem(TiledMap map, float viewportWidth, float viewportHeight, Entity playerEntity) {
        super(Family.all(CameraComponent.class).get());

        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        this.mapSize = new Vector2(
            layer.getWidth() * layer.getTileWidth(),
            layer.getHeight() * layer.getTileHeight()
        );

        this.viewportSize = new Vector2(viewportWidth, viewportHeight);
        this.playerEntity = playerEntity;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CameraComponent cm = cameraMapper.get(entity);

        // Get the player's position
        PositionComponent playerPosition = playerEntity.getComponent(PositionComponent.class);

        // Center the camera on the player
        Vector3 position = cm.camera.position;
        position.x = playerPosition.x;
        position.y = playerPosition.y;

       
        // Update the camera position
        cm.camera.position.set(position);
        cm.camera.update();

      
    }

}
