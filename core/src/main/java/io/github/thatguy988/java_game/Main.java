package io.github.thatguy988.java_game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import io.github.thatguy988.java_game.components.FacingDirectionComponent;
import io.github.thatguy988.java_game.components.PlayerComponent;
import io.github.thatguy988.java_game.components.PositionComponent;
import io.github.thatguy988.java_game.components.VelocityComponent;
import io.github.thatguy988.java_game.factories.BulletFactory;
import io.github.thatguy988.java_game.systems.FiringSystem;
import io.github.thatguy988.java_game.systems.LifetimeSystem;
import io.github.thatguy988.java_game.systems.MovementSystem;
import io.github.thatguy988.java_game.systems.PlayerInputSystem;
import io.github.thatguy988.java_game.systems.RenderSystem;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private Engine engine;
    private ShapeRenderer shapeRenderer;
    private BulletFactory bulletFactory;

    @Override
    public void create() {
        engine = new Engine();
        shapeRenderer = new ShapeRenderer();
        bulletFactory = new BulletFactory(engine);

        engine.addSystem(new MovementSystem());
        engine.addSystem(new PlayerInputSystem());
        engine.addSystem(new FiringSystem(bulletFactory));
        engine.addSystem(new RenderSystem(shapeRenderer));
        engine.addSystem(new LifetimeSystem());

        Entity player = engine.createEntity();
        player.add(new PositionComponent());
        player.add(new VelocityComponent());
        player.add(new PlayerComponent());
        player.add(new FacingDirectionComponent());
        engine.addEntity(player);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        engine.update(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
