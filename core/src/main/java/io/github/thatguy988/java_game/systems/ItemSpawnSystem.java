package io.github.thatguy988.java_game.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.signals.Listener;
import com.badlogic.ashley.signals.Signal;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import io.github.thatguy988.java_game.components.AmmoBoxComponent;
import io.github.thatguy988.java_game.components.HealthBoxComponent;
import io.github.thatguy988.java_game.events.EntityRemovedEvent;
import io.github.thatguy988.java_game.events.EventManager;
import io.github.thatguy988.java_game.factories.AmmoBoxFactory;
import io.github.thatguy988.java_game.factories.HealthBoxFactory;
import io.github.thatguy988.java_game.utils.WeaponType;

public class ItemSpawnSystem extends EntitySystem implements Listener<EntityRemovedEvent>
{
    private Engine engine;

    private Array<Vector2> healthBoxSpawnPoints;
    private HealthBoxFactory healthBoxFactory;
    private float healthBoxLastSpawnTime; //last time enemy was spawned in
    private float healthBoxSpawnInterval = 5.0f; //time between spawning enemies
    private int maxNumberOfHealthBoxes = 5;
    private int currentNumberOfHealthBoxes = 0;
    private boolean allowSpawnOfHealthBox = true;

    private Array<Vector2> ammoBoxSpawnPoints;
    private AmmoBoxFactory ammoBoxFactory;
    private float ammoBoxLastSpawnTime;
    private float ammoBoxSpawnInterval = 2.0f;
    private int maxNumberOfAmmoBoxes = 5;
    private int currentNumberofAmmoBoxes = 0;
    private boolean allowSpawnOfAmmoBox = true;

    public ItemSpawnSystem(Engine engine, Array<Vector2> healthBoxSpawnPoints, HealthBoxFactory healthBoxFactory, Array<Vector2> ammoBoxSpawnPoints, AmmoBoxFactory ammoBoxFactory)
    {
        this.engine = engine;
        this.healthBoxSpawnPoints = healthBoxSpawnPoints;
        this.healthBoxFactory = healthBoxFactory;
        this.ammoBoxSpawnPoints = ammoBoxSpawnPoints;
        this.ammoBoxFactory = ammoBoxFactory;  

    }

    public void initialize()
    {
        EventManager.entityRemovedSignal.add(this);  // listener for removal
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if(allowSpawnOfHealthBox)
        {
            healthBoxLastSpawnTime += deltaTime;
        }

        if (healthBoxLastSpawnTime > healthBoxSpawnInterval) {
            spawnHealthBox();
            healthBoxLastSpawnTime = 0;  // reset spawn timer
        }

        if(allowSpawnOfAmmoBox)
        {
            ammoBoxLastSpawnTime += deltaTime;
        }

        if(ammoBoxLastSpawnTime > ammoBoxSpawnInterval)
        {
            spawnAmmoBox();
            ammoBoxLastSpawnTime = 0; 
        }

    }

    @Override
    public void receive(Signal<EntityRemovedEvent> signal, EntityRemovedEvent event) {
        // called when the event is triggered
        // if the removed entity is a health box or ammo box update the counters
        if (event.entity.getComponent(HealthBoxComponent.class) != null) {
            currentNumberOfHealthBoxes-=1;
            allowSpawnOfHealthBox = true;
        } else if (event.entity.getComponent(AmmoBoxComponent.class) != null) {
            currentNumberofAmmoBoxes-=1;
            System.out.println(currentNumberofAmmoBoxes);
            allowSpawnOfAmmoBox = true;

        }


    }

    private void spawnHealthBox() 
    {
        Vector2 spawnLocation = healthBoxSpawnPoints.random();
        Entity healthbox = healthBoxFactory.createHealthBox(spawnLocation.x, spawnLocation.y);
        engine.addEntity(healthbox);
        currentNumberOfHealthBoxes+=1;

        if(currentNumberOfHealthBoxes>=maxNumberOfHealthBoxes)
        {
            allowSpawnOfHealthBox = false;
        }
    }

    private void spawnAmmoBox()
    {
        Vector2 spawnLocation = ammoBoxSpawnPoints.random();
        WeaponType[] weaponTypes = {WeaponType.MACHINEGUN, WeaponType.MINIGUN, WeaponType.SHOTGUN};
        WeaponType randomWeaponType = weaponTypes[MathUtils.random(weaponTypes.length - 1)];
        System.out.println(randomWeaponType);
        Entity ammobox = ammoBoxFactory.createAmmoBox(spawnLocation.x, spawnLocation.y, randomWeaponType);
        engine.addEntity(ammobox);
        currentNumberofAmmoBoxes+=1;
        System.out.println(currentNumberofAmmoBoxes);

        if(currentNumberofAmmoBoxes>=maxNumberOfAmmoBoxes)
        {
            allowSpawnOfAmmoBox = false;
        }


    }

    public void setMaxNumberOfHealthBoxes(int newMax)
    {
        this.maxNumberOfHealthBoxes = newMax;
    }

    public void setHealthBoxTimeInterval(float newInterval)
    {
        this.healthBoxSpawnInterval = newInterval;
    }

    public void setMaxNumberOfAmmoBoxes(int newMax)
    {
        this.maxNumberOfAmmoBoxes = newMax;
    }

    public void setAmmoBoxTimeInterval(float newInterval)
    {
        this.ammoBoxSpawnInterval = newInterval;
    }

    public int getCurrentNumberOfAmmoBoxes()
    {
        return this.currentNumberofAmmoBoxes;
    }

    public int getCurrentNumberOfHealthBoxes()
    {
        return this.currentNumberOfHealthBoxes;
    }

    public boolean getAllowSpawnOfAmmoBoxes()
    {
        return this.allowSpawnOfAmmoBox;
    }

    public boolean getAllowSpawnofHealthBoxes()
    {
        return this.allowSpawnOfHealthBox;
    }

    public void setAllowSpawnOfAmmoBoxes(boolean value)
    {
        this.allowSpawnOfAmmoBox = value;
    }

    public void setAllowSpawnOfHealthBoxes(boolean value)
    {
        this.allowSpawnOfHealthBox = value;
    }
    
}
