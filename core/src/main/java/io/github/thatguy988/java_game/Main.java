package io.github.thatguy988.java_game;

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

import io.github.thatguy988.java_game.components.CameraComponent;
import io.github.thatguy988.java_game.factories.BulletFactory;
import io.github.thatguy988.java_game.factories.PlayerFactory;
import io.github.thatguy988.java_game.systems.CameraSystem;
import io.github.thatguy988.java_game.systems.FiringSystem;
import io.github.thatguy988.java_game.systems.LifetimeSystem;
import io.github.thatguy988.java_game.systems.MovementSystem;
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
    private Box2DDebugRenderer debugRenderer; // Add debug renderer


    private Entity cameraEntity;



    


    @Override
    public void create() {
        engine = new Engine();
        shapeRenderer = new ShapeRenderer();
        bulletFactory = new BulletFactory(engine);
        physicsWorld = new World(new Vector2(0, -235.2f),false);
        playerFactory = new PlayerFactory(engine, physicsWorld);


        


        MapManager newMap = new MapManager("TitledMaps/testingmap1.tmx", physicsWorld);

        map = newMap.loadMap();

        Vector2 PlayerSpawnPoints = newMap.getPlayerSpawnPoint();
        newMap.createStaticBodies();

        

        mapRenderer = new OrthogonalTiledMapRenderer(map);
        debugRenderer = new Box2DDebugRenderer(); // Initialize the Box2D debug renderer


    
        initializeSystems();
        
        Entity player = playerFactory.createPlayer(PlayerSpawnPoints.x, PlayerSpawnPoints.y);


        engine.addEntity(player);

        cameraEntity = initializeCamera();
            

        // Add the CameraSystem
        engine.addSystem(new CameraSystem(map, 200, 300, player));

      
    }

    @Override
    public void render() {


        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Get the camera from the camera component
        CameraComponent cameraComponent = cameraEntity.getComponent(CameraComponent.class);

        // Update the camera
        cameraComponent.camera.update();

        // Set the view of the map renderer to the camera
        mapRenderer.setView(cameraComponent.camera);

        // Render the map
        mapRenderer.render();
        
        debugRenderer.render(physicsWorld, cameraEntity.getComponent(CameraComponent.class).camera.combined);

        
       

        engine.update(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        physicsWorld.dispose();
        map.dispose();
        mapRenderer.dispose(); 
        debugRenderer.dispose(); 


    }

    private void initializeSystems()
    {
        engine.addSystem(new PhysicsSystem(physicsWorld));
        engine.addSystem(new MovementSystem());
        engine.addSystem(new PlayerInputSystem());
        engine.addSystem(new FiringSystem(bulletFactory));
        engine.addSystem(new RenderSystem(shapeRenderer));
        engine.addSystem(new LifetimeSystem());
        engine.addSystem(new RecoilSystem());
    }

    private Entity initializeCamera()
    {

        Entity newCamera = new Entity();
        CameraComponent cameraComponent = new CameraComponent(200, 300);
        newCamera.add(cameraComponent);
        engine.addEntity(newCamera);
        return newCamera;
    }
}
