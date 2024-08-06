package io.github.thatguy988.java_game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import io.github.thatguy988.java_game.factories.BulletFactory;
import io.github.thatguy988.java_game.factories.PlayerFactory;
import io.github.thatguy988.java_game.systems.FiringSystem;
import io.github.thatguy988.java_game.systems.LifetimeSystem;
import io.github.thatguy988.java_game.systems.MovementSystem;
import io.github.thatguy988.java_game.systems.PhysicsSystem;
import io.github.thatguy988.java_game.systems.PlayerInputSystem;
import io.github.thatguy988.java_game.systems.RecoilSystem;
import io.github.thatguy988.java_game.systems.RenderSystem;



/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private Engine engine;
    private ShapeRenderer shapeRenderer;
    private BulletFactory bulletFactory;
    private PlayerFactory playerFactory;
    private World physicsWorld;
    private Box2DDebugRenderer debugRenderer;


    @Override
    public void create() {
        engine = new Engine();
        shapeRenderer = new ShapeRenderer();
        bulletFactory = new BulletFactory(engine);
        physicsWorld = new World(new Vector2(0, 0),true);
        debugRenderer = new Box2DDebugRenderer();
        playerFactory = new PlayerFactory(engine, physicsWorld);



        engine.addSystem(new PhysicsSystem(physicsWorld));
        engine.addSystem(new MovementSystem());
        engine.addSystem(new PlayerInputSystem());
        engine.addSystem(new FiringSystem(bulletFactory));
        engine.addSystem(new RenderSystem(shapeRenderer));
        engine.addSystem(new LifetimeSystem());
        engine.addSystem(new RecoilSystem());

        Entity player = playerFactory.createPlayer();


        engine.addEntity(player);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        engine.update(Gdx.graphics.getDeltaTime());
        debugRenderer.render(physicsWorld, shapeRenderer.getProjectionMatrix());

    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        physicsWorld.dispose();
        debugRenderer.dispose();
    }
}
