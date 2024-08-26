package io.github.thatguy988.java_game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.World;

import io.github.thatguy988.java_game.components.AmmoBoxComponent;
import io.github.thatguy988.java_game.components.Box2DComponent;
import io.github.thatguy988.java_game.components.BulletComponent;
import io.github.thatguy988.java_game.components.HealthBoxComponent;
import io.github.thatguy988.java_game.events.EntityRemovedEvent;
import io.github.thatguy988.java_game.events.EventManager;

public class RemovalSystem extends IteratingSystem {
    private World world;
    private Engine engine;
    private ComponentMapper<Box2DComponent> bm = ComponentMapper.getFor(Box2DComponent.class);
    private ComponentMapper<BulletComponent> bulletm = ComponentMapper.getFor(BulletComponent.class);
    private ComponentMapper<HealthBoxComponent> hbm = ComponentMapper.getFor(HealthBoxComponent.class);
    private ComponentMapper<AmmoBoxComponent> abm = ComponentMapper.getFor(AmmoBoxComponent.class);

    public RemovalSystem(World world, Engine engine) {
        super(Family.all(Box2DComponent.class).get());
        this.world = world;
        this.engine = engine;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Box2DComponent box2D = bm.get(entity);
        BulletComponent bullet = bulletm.get(entity);
        HealthBoxComponent healthBox = hbm.get(entity);
        AmmoBoxComponent ammoBox = abm.get(entity);

        if (bullet != null && !bullet.getActiveState()) 
        {
            world.destroyBody(box2D.body);
            engine.removeEntity(entity);
            return;
        }

        if (healthBox != null && !healthBox.getActiveState()) 
        {
            world.destroyBody(box2D.body);
            EventManager.entityRemovedSignal.dispatch(new EntityRemovedEvent(entity)); // remove event for item spawn system
            engine.removeEntity(entity);
            return;
        }

        if(ammoBox != null && !ammoBox.getActiveState())
        {
            world.destroyBody(box2D.body);
            EventManager.entityRemovedSignal.dispatch(new EntityRemovedEvent(entity)); // remove event for item spawn system
            engine.removeEntity(entity);
            return;
        }

        
    }
}
