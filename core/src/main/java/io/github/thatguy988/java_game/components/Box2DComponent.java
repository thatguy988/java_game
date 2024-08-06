package io.github.thatguy988.java_game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;


public class Box2DComponent implements Component 
{
    public Body body;
    public Vector2 playerVelocity = new Vector2(0, 0); // Holds the body velocity from player movement speed and recoil


    public Box2DComponent(Body body)
    {
        this.body = body;
    }

    
}
