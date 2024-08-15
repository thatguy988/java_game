package io.github.thatguy988.java_game.utils;
import java.util.HashSet;
import java.util.Set;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import io.github.thatguy988.java_game.components.Box2DComponent;
import io.github.thatguy988.java_game.components.GroundComponent;



public class MapManager {
    private String mapFilePath;
    private TiledMap map;
    private World world;
    private Engine engine;
    
    public MapManager(String mapfilepath, World world, Engine engine)
    {
        this.mapFilePath = mapfilepath;
        this.world = world;
        this.engine = engine;
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

    public void createStaticBodies() {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0); // first layer is for static tiles

        float tileWidth = layer.getTileWidth();
        float tileHeight = layer.getTileHeight();

        // Define the set of solid tile IDs
        // move hashset to attribute of mapmanager if it is needed in multple methods
        //else here locally if only needed here then keep it here
        Set<Integer> solidTileIds = new HashSet<>();
        solidTileIds.add(203); // IDs for solid tiles
        solidTileIds.add(204);
        solidTileIds.add(205); 


        for (int y = 0; y < layer.getHeight(); y++) {
            for (int x = 0; x < layer.getWidth(); x++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                if (cell != null && solidTileIds.contains(cell.getTile().getId())) {
                    createStaticBodyForTile(x, y, tileWidth, tileHeight);
                }
            }
        }
    }

    private void createStaticBodyForTile(int x, int y, float tileWidth, float tileHeight) {

        Entity entity = engine.createEntity();

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.StaticBody;
        bodyDef.position.set(
            (x + 0.5f) * tileWidth, // Center the body in the tile
            (y + 0.5f) * tileHeight
        );

        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(tileWidth / 2, tileHeight / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.0f; 

        body.createFixture(fixtureDef);
        shape.dispose();
        body.setUserData(entity); 


        Box2DComponent box2DComponent = new Box2DComponent(body);
        entity.add(box2DComponent);
        GroundComponent groundComponent = new GroundComponent();
        entity.add(groundComponent);

        engine.addEntity(entity);
    }
}
