package io.github.thatguy988.java_game.utils;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;



public class MapManager {
    private String mapFilePath;
    private TiledMap map;
    
    
    public MapManager(String mapfilepath)
    {
        this.mapFilePath = mapfilepath;
    }

    public TiledMap loadMap()
    {
        map = new TmxMapLoader().load(mapFilePath);
        return map;
    }

    public Vector2 getPlayerSpawnPoint() {
        MapLayer objectLayer = map.getLayers().get("SpawnPoint");
        if (objectLayer == null) {
            System.err.println("SpawnPoint layer not found in map.");
            return new Vector2(0, 0);
        }

        for (MapObject object : objectLayer.getObjects()) {
            if (object instanceof RectangleMapObject && "PlayerSpawn".equals(object.getName())) {
                Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
                return new Vector2(rectangle.x, rectangle.y);
            }
        }

        System.err.println("PlayerSpawn object not found.");
        return new Vector2(0, 0);
    }

    public Array<Vector2> getEnemySpawnPoints() {
        MapLayer objectLayer = map.getLayers().get("EnemySpawnPointsLayer");
        Array<Vector2> spawnPoints = new Array<>();
        
        if (objectLayer == null) {
            System.err.println("SpawnPoint layer not found in map.");
            return spawnPoints;
        }

        for (MapObject object : objectLayer.getObjects()) {
            if (object instanceof RectangleMapObject && "EnemySpawn".equals(object.getName())) {
                Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
                spawnPoints.add(new Vector2(rectangle.x, rectangle.y));
            }
        }

        if (spawnPoints.isEmpty()) {
            System.err.println("No EnemySpawn objects found.");
        }

        return spawnPoints;
    }


    public Array<Vector2> getHealthBoxSpawnPoints() {
        MapLayer objectLayer = map.getLayers().get("HealthBoxSpawnPointsLayer");
        Array<Vector2> spawnPoints = new Array<>();
        
        if (objectLayer == null) {
            System.err.println("SpawnPoint layer not found in map.");
            return spawnPoints;
        }

        for (MapObject object : objectLayer.getObjects()) {
            if (object instanceof RectangleMapObject && "HealthSpawn".equals(object.getName())) {
                Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
                spawnPoints.add(new Vector2(rectangle.x, rectangle.y));
            }
        }

        if (spawnPoints.isEmpty()) {
            System.err.println("No HealthSpawn objects found.");
        }

        return spawnPoints;
    }

    public Array<Vector2> getAmmoBoxSpawnPoints() {
        MapLayer objectLayer = map.getLayers().get("AmmoBoxSpawnPointsLayer");
        Array<Vector2> spawnPoints = new Array<>();
        
        if (objectLayer == null) {
            System.err.println("SpawnPoint layer not found in map.");
            return spawnPoints;
        }

        for (MapObject object : objectLayer.getObjects()) {
            if (object instanceof RectangleMapObject && "AmmoSpawn".equals(object.getName())) {
                Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
                spawnPoints.add(new Vector2(rectangle.x, rectangle.y));
            }
        }

        if (spawnPoints.isEmpty()) {
            System.err.println("No AmmoSpawn objects found.");
        }

        return spawnPoints;
    }

}
