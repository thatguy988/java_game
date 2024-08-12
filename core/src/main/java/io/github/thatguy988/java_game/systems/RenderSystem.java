// package io.github.thatguy988.java_game.systems;

// import com.badlogic.ashley.core.ComponentMapper;
// import com.badlogic.ashley.core.Entity;
// import com.badlogic.ashley.core.Family;
// import com.badlogic.ashley.systems.IteratingSystem;
// import com.badlogic.gdx.graphics.OrthographicCamera;
// import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

// import io.github.thatguy988.java_game.components.BulletComponent;
// import io.github.thatguy988.java_game.components.PlayerComponent;
// import io.github.thatguy988.java_game.components.PositionComponent;
// import io.github.thatguy988.java_game.utils.PositionUtils;

// public class RenderSystem extends IteratingSystem {
//     private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
//     private ComponentMapper<PlayerComponent> plcm = ComponentMapper.getFor(PlayerComponent.class);
//     private ComponentMapper<BulletComponent> bm = ComponentMapper.getFor(BulletComponent.class);
//     private ShapeRenderer shapeRenderer;


//     public RenderSystem(ShapeRenderer shapeRenderer) {
//         super(Family.one(PlayerComponent.class, BulletComponent.class).get());
//         this.shapeRenderer = shapeRenderer;
//     }

//     @Override
//     public void update(float deltaTime) {
//         shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//         super.update(deltaTime);
//         shapeRenderer.end();
//     }

//     @Override
//     protected void processEntity(Entity entity, float deltaTime) {
//         PositionComponent position = pm.get(entity);
//         PlayerComponent player = plcm.get(entity);
//         BulletComponent bullet = bm.get(entity);


//         if (player != null) {
//             float width = player.getWidth();
//             float height = player.getHeight();
//             shapeRenderer.setColor(1, 0, 0, 1); // Red color for player
//             com.badlogic.gdx.math.Vector2 renderPositions = PositionUtils.getRenderPosition(new com.badlogic.gdx.math.Vector2(position.x, position.y), width, height);
//            // System.out.println("Player render position: " + renderPositions);

//             shapeRenderer.rect(renderPositions.x, renderPositions.y, width, height);
//         } else if (bullet != null) {
//             shapeRenderer.setColor(0, 1, 0, 1); // Green color for bullets
//             shapeRenderer.circle(position.x, position.y, 5); // 5 pixel radius circle
//         }
//     }


// }


package io.github.thatguy988.java_game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import io.github.thatguy988.java_game.components.Box2DComponent;
import io.github.thatguy988.java_game.components.BulletComponent;
import io.github.thatguy988.java_game.components.PlayerComponent;
import io.github.thatguy988.java_game.components.PositionComponent;
import io.github.thatguy988.java_game.utils.PositionUtils;

public class RenderSystem extends IteratingSystem {
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<PlayerComponent> plcm = ComponentMapper.getFor(PlayerComponent.class);
    private ComponentMapper<BulletComponent> bm = ComponentMapper.getFor(BulletComponent.class);
    private ComponentMapper<Box2DComponent> boxm = ComponentMapper.getFor(Box2DComponent.class);
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;

    public RenderSystem(ShapeRenderer shapeRenderer) {
        super(Family.one(PlayerComponent.class, BulletComponent.class).get());
        this.shapeRenderer = shapeRenderer;
    }

    public void setCamera(OrthographicCamera camera) {
        this.camera = camera;
    }

    @Override
    public void update(float deltaTime) {
        if (camera != null) {
            shapeRenderer.setProjectionMatrix(camera.combined);
        }
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        super.update(deltaTime);
        shapeRenderer.end();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent position = pm.get(entity);
        PlayerComponent player = plcm.get(entity);
        BulletComponent bullet = bm.get(entity);
        Box2DComponent  bo = boxm.get(entity);

        if (player != null) {
            Vector2 bodyPosition = bo.body.getPosition();

            float width = player.getWidth();
            float height = player.getHeight();
            shapeRenderer.setColor(1, 0, 0, 1); // Red color for player

            Vector2 renderPosition = PositionUtils.getRenderPosition(bodyPosition.x, bodyPosition.y, width, height);
            shapeRenderer.rect(renderPosition.x, renderPosition.y, width, height);
        } else if (bullet != null) {
            shapeRenderer.setColor(0, 1, 0, 1); // Green color for bullets
            shapeRenderer.circle(position.x, position.y, 5); // 5 pixel radius circle
        }
    }
}