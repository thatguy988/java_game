package io.github.thatguy988.java_game.factories;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.IntSet;

import io.github.thatguy988.java_game.components.Box2DComponent;
import io.github.thatguy988.java_game.components.GroundComponent;

public class LevelFactory {

    private Engine engine;
    private World physicsWorld;

    public LevelFactory(Engine engine, World world)
    {
        this.engine = engine;
        this.physicsWorld = world;
    }

    public void createStaticBodies(TiledMap map) {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0); // first layer is for static tiles

        float tileWidth = layer.getTileWidth();
        float tileHeight = layer.getTileHeight();

        // Define the set of solid tile IDs
        // move hashset to attribute of mapmanager if it is needed in multple methods
        //else here locally if only needed here then keep it here
        IntSet solidTileIds = new IntSet();
       // Set<Integer> solidTileIds = new HashSet<>();
        solidTileIds.add(27); // IDs for solid tiles
        solidTileIds.add(28);
        solidTileIds.add(29); 


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

        Body body = physicsWorld.createBody(bodyDef);

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
