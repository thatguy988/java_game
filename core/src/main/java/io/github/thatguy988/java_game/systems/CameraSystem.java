// package io.github.thatguy988.java_game.systems;

// import com.badlogic.ashley.core.ComponentMapper;
// import com.badlogic.ashley.core.Entity;
// import com.badlogic.ashley.core.Family;
// import com.badlogic.ashley.systems.IteratingSystem;
// import com.badlogic.gdx.maps.tiled.TiledMap;
// import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
// import com.badlogic.gdx.math.Vector2;
// import com.badlogic.gdx.math.Vector3;

// import io.github.thatguy988.java_game.components.Box2DComponent;
// import io.github.thatguy988.java_game.components.CameraComponent;

// public class CameraSystem extends IteratingSystem {
//     private final Vector2 mapSize;
//     private final Vector2 viewportSize;
//     private Entity playerEntity; // Store player entity for tracking

//     private ComponentMapper<CameraComponent> cameraMapper = ComponentMapper.getFor(CameraComponent.class);
//     private ComponentMapper<Box2DComponent> box2dMapper = ComponentMapper.getFor(Box2DComponent.class);

//     public CameraSystem(TiledMap map, float viewportWidth, float viewportHeight, Entity playerEntity) {
//         super(Family.all(CameraComponent.class).get());

//         TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
//         this.mapSize = new Vector2(
//             layer.getWidth() * layer.getTileWidth(),
//             layer.getHeight() * layer.getTileHeight()
//         );

//         this.viewportSize = new Vector2(viewportWidth, viewportHeight);
//         this.playerEntity = playerEntity;
//     }

//     @Override
//     protected void processEntity(Entity entity, float deltaTime) {
//         CameraComponent cm = cameraMapper.get(entity);
//         Box2DComponent bm = box2dMapper.get(entity);




//         if (bm != null){
//             // Get the player's position
//             //PositionComponent playerPosition = playerEntity.getComponent(PositionComponent.class);
//             //Box2DComponent playerbody = playerEntity.getComponent(Box2DComponent.class);

//             Vector2 playerbodyPosition = bm.body.getPosition();

//             // Center the camera on the player
//             Vector3 position = cm.camera.position;
//             position.x = playerbodyPosition.x;
//             position.y = playerbodyPosition.y;

        
//             // Update the camera position
//             cm.camera.position.set(position);
//             cm.camera.update();
//         }

      
//     }

// }
