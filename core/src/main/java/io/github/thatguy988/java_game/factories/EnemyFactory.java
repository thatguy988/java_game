package io.github.thatguy988.java_game.factories;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import io.github.thatguy988.java_game.components.Box2DComponent;
import io.github.thatguy988.java_game.components.EnemyComponent;
import io.github.thatguy988.java_game.components.FacingDirectionComponent;
import io.github.thatguy988.java_game.components.HealthComponent;
import io.github.thatguy988.java_game.components.WeaponsComponent;
import io.github.thatguy988.java_game.utils.PositionUtils;
import io.github.thatguy988.java_game.utils.WeaponType;

public class EnemyFactory 
{
    private Engine engine;
    private World physicsWorld;
    
    public EnemyFactory(Engine engine, World physicsWorld)
    {
        this.engine = engine;
        this.physicsWorld = physicsWorld;
    }

    public Entity createEnemy(float spawnX, float spawnY) {
        Entity enemy = engine.createEntity();

        EnemyComponent enemyComponent = new EnemyComponent();
        enemy.add(enemyComponent);
        
        // Box2D body for enemy
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(spawnX, spawnY);
        Body body = physicsWorld.createBody(bodyDef);

        
        com.badlogic.gdx.math.Vector2 bodyPositions = PositionUtils.getPhysicsBodyPosition(enemyComponent.getWidth(), enemyComponent.getHeight());

        // shape of the enemy
        PolygonShape rectangle = new PolygonShape();
        rectangle.setAsBox(bodyPositions.x, bodyPositions.y);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = rectangle;
        fixtureDef.density = 1.0f; // mass and inertia
        fixtureDef.friction = 0.3f; // friction when sliding along surfaces
        fixtureDef.restitution = 0.0f; // no bounce

        body.createFixture(fixtureDef);
        rectangle.dispose();

        body.setFixedRotation(true);
        body.setLinearDamping(5.0f);
        body.setUserData(enemy);

        enemy.add(new FacingDirectionComponent());
        enemy.add(new WeaponsComponent(WeaponType.ENEMYGRUNT, 0.5f, 600f, 1));
        enemy.add(new Box2DComponent(body));
        enemy.add(new HealthComponent(50)); 

        return enemy;
    }
}
