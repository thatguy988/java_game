package io.github.thatguy988.java_game.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import io.github.thatguy988.java_game.Main;
import io.github.thatguy988.java_game.components.CameraComponent;
import io.github.thatguy988.java_game.components.HealthComponent;
import io.github.thatguy988.java_game.components.PlayerComponent;
import io.github.thatguy988.java_game.factories.AmmoBoxFactory;
import io.github.thatguy988.java_game.factories.BulletFactory;
import io.github.thatguy988.java_game.factories.EnemyFactory;
import io.github.thatguy988.java_game.factories.HealthBoxFactory;
import io.github.thatguy988.java_game.factories.LevelFactory;
import io.github.thatguy988.java_game.factories.PlayerFactory;
import io.github.thatguy988.java_game.systems.CameraSystem;
import io.github.thatguy988.java_game.systems.CollisionSystem;
import io.github.thatguy988.java_game.systems.EnemySpawnSystem;
import io.github.thatguy988.java_game.systems.FiringSystem;
import io.github.thatguy988.java_game.systems.ItemSpawnSystem;
import io.github.thatguy988.java_game.systems.LifetimeSystem;
import io.github.thatguy988.java_game.systems.PhysicsSystem;
import io.github.thatguy988.java_game.systems.PlayerInputSystem;
import io.github.thatguy988.java_game.systems.RecoilSystem;
import io.github.thatguy988.java_game.systems.RemovalSystem;
import io.github.thatguy988.java_game.systems.RenderSystem;
import io.github.thatguy988.java_game.systems.UISystem;
import io.github.thatguy988.java_game.utils.MapManager;

public class GameScreen extends ScreenAdapter 
{
    private Main game;
    private boolean isPaused = false;
    private boolean isGameOver = false;
    private Engine engine;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;
    private BulletFactory bulletFactory;
    private PlayerFactory playerFactory;
    private EnemyFactory enemyFactory;
    private HealthBoxFactory healthBoxFactory;
    private AmmoBoxFactory ammoBoxFactory;
    private World physicsWorld;
    

    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private Box2DDebugRenderer debugRenderer;

    public GameScreen(Main game)
    {
        //initialize the game
        this.game = game;
        this.spriteBatch = game.spriteBatch;
        initializeGame();
    }

    private void initializeGame()
    {
        System.out.println("Initializing game...");
        engine = new Engine();
        //shapeRenderer = new ShapeRenderer();
        this.spriteBatch = game.spriteBatch;
        this.shapeRenderer = game.shapeRenderer;


        //spriteBatch = new SpriteBatch();
        physicsWorld = new World(new Vector2(0, -220f), false);
        playerFactory = new PlayerFactory(engine, physicsWorld);
        bulletFactory = new BulletFactory(engine, physicsWorld);
        enemyFactory = new EnemyFactory(engine, physicsWorld);
        healthBoxFactory = new HealthBoxFactory(engine, physicsWorld);
        ammoBoxFactory = new AmmoBoxFactory(engine, physicsWorld);

        // Create the camera component with viewport size
        CameraComponent cameraComponent = new CameraComponent(Gdx.graphics.getWidth() / 2.0f, Gdx.graphics.getHeight() / 2.0f);

        Entity cameraEntity = new Entity();
        cameraEntity.add(cameraComponent);
        engine.addEntity(cameraEntity);


        MapManager newMap = new MapManager("TitledMaps/testingmap1.tmx");
        LevelFactory level = new LevelFactory(engine, physicsWorld);
        map = newMap.loadMap();

        Vector2 playerSpawnPoints = newMap.getPlayerSpawnPoint();
        level.createStaticBodies(map);

        Array<Vector2> enemySpawnPoints = new Array<>(newMap.getEnemySpawnPoints());
        Array<Vector2> healthBoxSpawnPoints = new Array<>(newMap.getHealthBoxSpawnPoints());
        Array<Vector2> ammoBoxSpawnPoints = new Array<>(newMap.getAmmoBoxSpawnPoints());

        mapRenderer = new OrthogonalTiledMapRenderer(map);
        debugRenderer = new Box2DDebugRenderer();

        Entity player = playerFactory.createPlayer(playerSpawnPoints.x, playerSpawnPoints.y);

        initializeSystems(player, enemySpawnPoints, healthBoxSpawnPoints, ammoBoxSpawnPoints);

        engine.addEntity(player);

    }
    
    private void initializeSystems(Entity player, Array<Vector2> enemySpawnPoints, Array<Vector2> healthBoxSpawnPoints, Array<Vector2> ammoBoxSpawnPoints)
    {
        CameraComponent cameraComponent = engine.getEntitiesFor(Family.all(CameraComponent.class).get()).first().getComponent(CameraComponent.class);
        CollisionSystem collisionSystem = new CollisionSystem(physicsWorld);
        collisionSystem.initialize(); 

        engine.addSystem(collisionSystem);


        engine.addSystem(new RemovalSystem(physicsWorld, engine));


        engine.addSystem(new PhysicsSystem(physicsWorld));

        engine.addSystem(new PlayerInputSystem(game));
        


        engine.addSystem(new RecoilSystem());
        engine.addSystem(new FiringSystem(bulletFactory));

        //engine.addSystem(collisionSystem);

        engine.addSystem(new LifetimeSystem(physicsWorld,engine));
        engine.addSystem(new CameraSystem(player));
        engine.addSystem(new RenderSystem(shapeRenderer, cameraComponent.camera));
        engine.addSystem(new UISystem(spriteBatch));
        engine.addSystem(new EnemySpawnSystem(engine, cameraComponent.camera, enemySpawnPoints, enemyFactory));
        ItemSpawnSystem itemSpawnSystem = new ItemSpawnSystem(engine, healthBoxSpawnPoints, healthBoxFactory, ammoBoxSpawnPoints, ammoBoxFactory);
        itemSpawnSystem.initialize();
        engine.addSystem(itemSpawnSystem);
    }

    @Override 
    public void hide()
    {
        System.out.println("GameScreen hide");
        isPaused = true;  
    }

    @Override
    public void show()
    {
        if(isGameOver)
        {
            System.out.println("Restarting game...");
            // Reset the game state
            initializeGame();
            isGameOver = false;
        }
        System.out.println("GameScreen show");
        isPaused = false;
    }


    @Override
    public void render(float deltaTime) 
    {
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(!isPaused)
        {
            if(isPlayerDead())
            {
                isGameOver = true;
            }
            // Set the view of the map renderer to the camera
            CameraComponent cameraComponent = engine.getEntitiesFor(Family.all(CameraComponent.class).get()).first().getComponent(CameraComponent.class);
            mapRenderer.setView(cameraComponent.camera);
            mapRenderer.render();

            
            // Update the engine systems
            engine.update(deltaTime);
            
            
            
            // Render Box2D debug outlines
            debugRenderer.render(physicsWorld, cameraComponent.camera.combined);
        }
    }

    @Override
    public void dispose()
    {
        // safely dispose all resources for engine
        if (engine != null) {
            engine.removeAllEntities();
            engine.removeAllSystems();
            engine = null;
        }
        
        // Dispose of all box2d resources
        if (physicsWorld != null) {
            Array<Body> bodies = new Array<>();
            physicsWorld.getBodies(bodies);
            for (Body body : bodies) {
                physicsWorld.destroyBody(body);
            }
            physicsWorld.dispose();
        }

        shapeRenderer.dispose();
        spriteBatch.dispose();
        map.dispose();
        mapRenderer.dispose();
        debugRenderer.dispose();
    }

    private boolean isPlayerDead() 
    {
        for (Entity entity : engine.getEntitiesFor(Family.all(PlayerComponent.class, HealthComponent.class).get())) {
            HealthComponent health = entity.getComponent(HealthComponent.class);
            return health.isOutofHealth();
        }
        return false;
    }
}
