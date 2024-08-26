package io.github.thatguy988.java_game.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import io.github.thatguy988.java_game.factories.EnemyFactory;
public class EnemySpawnSystem extends EntitySystem
{
    private Engine engine;
    private OrthographicCamera playerCamera;
    private Array<Vector2> enemySpawnPoints;
    private EnemyFactory enemyFactory;
    private float lastSpawnTime; //last time enemy was spawned in
    private float spawnInterval = 3.0f; //time between spawning enemies
    private int maxNumberOfEnemies = 10;
    private int currentNumberOfEnemies = 0;

    public EnemySpawnSystem(Engine engine, OrthographicCamera camera, Array<Vector2> enemySpawnPoints, EnemyFactory enemyFactory)
    {
        this.engine = engine;
        this.playerCamera = camera;
        this.enemySpawnPoints = enemySpawnPoints;
        this.enemyFactory = enemyFactory;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        lastSpawnTime += deltaTime;

        if (lastSpawnTime > spawnInterval && (currentNumberOfEnemies < maxNumberOfEnemies)) {
            spawnEnemies();
            lastSpawnTime = 0;  // Reset spawn timer
        }

    }

    private void spawnEnemies() {
        // camera's view bounds
        float cameraLeft = playerCamera.position.x - playerCamera.viewportWidth / 2;
        float cameraRight = playerCamera.position.x + playerCamera.viewportWidth / 2;
        float cameraBottom = playerCamera.position.y - playerCamera.viewportHeight / 2;
        float cameraTop = playerCamera.position.y + playerCamera.viewportHeight / 2;

        Array<Vector2> validSpawnPoints = new Array<>();
        float margin = 10f; // margin offset to avoid spawning near the screen edges

        // spawn points that are off-screen
        for (Vector2 spawnPoint : enemySpawnPoints) {
            if (spawnPoint.x < cameraLeft - margin || spawnPoint.x > cameraRight + margin || 
                spawnPoint.y < cameraBottom - margin || spawnPoint.y > cameraTop + margin) {
                validSpawnPoints.add(spawnPoint);
            }
        }

        // randomly select one valid spawn point to spawn an enemy
        if (validSpawnPoints.size > 0) {
            Vector2 spawnLocation = validSpawnPoints.random();
            spawnEnemyAtLocation(spawnLocation);
        } else {
            System.out.println("No valid off-screen spawn points available.");
        }
    }

    private void spawnEnemyAtLocation(Vector2 location) {
        Entity enemy = enemyFactory.createEnemy(location.x, location.y);
        engine.addEntity(enemy);
        currentNumberOfEnemies+=1;
    }
    
}
