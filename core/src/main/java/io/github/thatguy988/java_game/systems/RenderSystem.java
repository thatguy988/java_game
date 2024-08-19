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
import io.github.thatguy988.java_game.utils.PositionUtils;

public class RenderSystem extends IteratingSystem {
    private ComponentMapper<PlayerComponent> plcm = ComponentMapper.getFor(PlayerComponent.class);
    private ComponentMapper<BulletComponent> bm = ComponentMapper.getFor(BulletComponent.class);
    private ComponentMapper<Box2DComponent> boxm = ComponentMapper.getFor(Box2DComponent.class);
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;

    public RenderSystem(ShapeRenderer shapeRenderer, OrthographicCamera camera) {
        super(Family.one(PlayerComponent.class, BulletComponent.class).get());
        this.shapeRenderer = shapeRenderer;
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
            Vector2 bulletbodyposition = bo.body.getPosition();
            shapeRenderer.setColor(0, 1, 0, 1); // Green color for bullets
            shapeRenderer.circle(bulletbodyposition.x, bulletbodyposition.y, 3); // 5 pixel radius circle
        }
    }
}