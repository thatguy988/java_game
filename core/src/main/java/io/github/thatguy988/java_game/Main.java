package  io.github.thatguy988.java_game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import io.github.thatguy988.java_game.factories.BulletFactory;
import io.github.thatguy988.java_game.factories.PlayerFactory;
import io.github.thatguy988.java_game.systems.FiringSystem;
import io.github.thatguy988.java_game.systems.LifetimeSystem;
import io.github.thatguy988.java_game.systems.PhysicsSystem;
import io.github.thatguy988.java_game.systems.PlayerInputSystem;
import io.github.thatguy988.java_game.systems.RecoilSystem;
import io.github.thatguy988.java_game.systems.RenderSystem;
import io.github.thatguy988.java_game.utils.MapManager;

public class Main extends ApplicationAdapter {

    private Engine engine;
    private ShapeRenderer shapeRenderer;
    private BulletFactory bulletFactory;
    private PlayerFactory playerFactory;
    private World physicsWorld;

    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private Box2DDebugRenderer debugRenderer;

    @Override
    public void create() {
        engine = new Engine();
        shapeRenderer = new ShapeRenderer();
        physicsWorld = new World(new Vector2(0, -9.8f), false);
        playerFactory = new PlayerFactory(engine, physicsWorld);
        bulletFactory = new BulletFactory(engine, physicsWorld);


        MapManager newMap = new MapManager("TitledMaps/testingmap1.tmx", physicsWorld);
        map = newMap.loadMap();

        Vector2 playerSpawnPoints = newMap.getPlayerSpawnPoint();
        newMap.createStaticBodies();

        // Create map renderer with scaling
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        debugRenderer = new Box2DDebugRenderer();

        initializeSystems();

        Entity player = playerFactory.createPlayer(playerSpawnPoints.x, playerSpawnPoints.y);
        engine.addEntity(player);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Set up projection matrix manually since no camera is used
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        mapRenderer.getBatch().getProjectionMatrix().setToOrtho2D(0, 0, screenWidth, screenHeight);

        // Render the map
        mapRenderer.setView(mapRenderer.getBatch().getProjectionMatrix(), 0, 0, screenWidth, screenHeight);
        mapRenderer.render();

        // Update and render Box2D world
        engine.update(Gdx.graphics.getDeltaTime());

        // Render Box2D debug outlines
        debugRenderer.render(physicsWorld, mapRenderer.getBatch().getProjectionMatrix());
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        physicsWorld.dispose();
        map.dispose();
        mapRenderer.dispose();
        debugRenderer.dispose();
    }

    private void initializeSystems() {
        engine.addSystem(new PhysicsSystem(physicsWorld));
        engine.addSystem(new PlayerInputSystem());
        engine.addSystem(new FiringSystem(bulletFactory));
        engine.addSystem(new RenderSystem(shapeRenderer));
        engine.addSystem(new LifetimeSystem());
        engine.addSystem(new RecoilSystem());
    }
}
