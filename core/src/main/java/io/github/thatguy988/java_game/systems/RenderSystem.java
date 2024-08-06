package io.github.thatguy988.java_game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import io.github.thatguy988.java_game.components.BulletComponent;
import io.github.thatguy988.java_game.components.PlayerComponent;
import io.github.thatguy988.java_game.components.PositionComponent;
import io.github.thatguy988.java_game.utils.PositionUtils;

public class RenderSystem extends IteratingSystem {
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<PlayerComponent> plcm = ComponentMapper.getFor(PlayerComponent.class);
    private ShapeRenderer shapeRenderer;

    public RenderSystem(ShapeRenderer shapeRenderer) {
        super(Family.one(PlayerComponent.class, BulletComponent.class).get());
        this.shapeRenderer = shapeRenderer;
    }

    @Override
    public void update(float deltaTime) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        super.update(deltaTime);
        shapeRenderer.end();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent position = pm.get(entity);
        PlayerComponent player = plcm.get(entity);


        if (entity.getComponent(PlayerComponent.class) != null) {
            float width = player.getWidth();
            float height = player.getHeight();
            shapeRenderer.setColor(1, 0, 0, 1); // Red color for player
            com.badlogic.gdx.math.Vector2 renderPositions = PositionUtils.getRenderPosition(new com.badlogic.gdx.math.Vector2(position.x, position.y), width, height);
            shapeRenderer.rect(renderPositions.x, renderPositions.y, width, height);

            //shapeRenderer.rect(position.x, position.y, player.getWidth(), player.getHeight()); // 32x32 square
        } else if (entity.getComponent(BulletComponent.class) != null) {
            shapeRenderer.setColor(0, 1, 0, 1); // Green color for bullets
            shapeRenderer.circle(position.x, position.y, 5); // 5 pixel radius circle
        }
    }
}