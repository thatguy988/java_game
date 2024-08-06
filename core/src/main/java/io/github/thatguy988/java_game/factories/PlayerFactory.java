package io.github.thatguy988.java_game.factories;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import io.github.thatguy988.java_game.components.Box2DComponent;
import io.github.thatguy988.java_game.components.FacingDirectionComponent;
import io.github.thatguy988.java_game.components.PlayerComponent;
import io.github.thatguy988.java_game.components.PositionComponent;
import io.github.thatguy988.java_game.components.WeaponsComponent;
import io.github.thatguy988.java_game.components.WeaponsComponent.WeaponType;
import io.github.thatguy988.java_game.utils.PositionUtils;



public class PlayerFactory 
{
    private Engine engine;
    private World physicsWorld;

    private ComponentMapper<PlayerComponent> plcm = ComponentMapper.getFor(PlayerComponent.class);
    
    public PlayerFactory(Engine engine, World physicsWorld)
    {
        this.engine = engine;
        this.physicsWorld = physicsWorld;
    }

    public Entity createPlayer() {
        Entity player = engine.createEntity();

        PlayerComponent playerComponent = new PlayerComponent();
        player.add(playerComponent);


        // Define Box2D body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0, 0);
        Body body = physicsWorld.createBody(bodyDef);

        com.badlogic.gdx.math.Vector2 bodyPositions = PositionUtils.getPhysicsBodyPosition(playerComponent.getWidth(), playerComponent.getHeight());


        // Define a rectangle shape for the player
        PolygonShape rectangle = new PolygonShape();
        

        // Set as half-width and half-height
        rectangle.setAsBox(bodyPositions.x , bodyPositions.y);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = rectangle;
        fixtureDef.density = 1.0f; // Determines mass and inertia
        fixtureDef.friction = 0.3f; // Friction when sliding along surfaces
        fixtureDef.restitution = 0.0f; // Bounce factor (0 for no bounce)

        body.createFixture(fixtureDef);
        rectangle.dispose();

        // Add components
        player.add(new PositionComponent(0f, 0f));
        //player.add(new VelocityComponent());
        player.add(new FacingDirectionComponent());
        player.add(new WeaponsComponent(WeaponType.PISTOL, 0.25f, 800f, 1));
        player.add(new Box2DComponent(body));

        return player;
    }
    
}
