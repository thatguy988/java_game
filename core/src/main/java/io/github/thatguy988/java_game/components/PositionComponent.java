package io.github.thatguy988.java_game.components;

import com.badlogic.ashley.core.Component;


public class PositionComponent implements Component {
    public float x = 0.0f;
    public float y = 0.0f;

    public PositionComponent(float xposition, float yposition)
    {
        this.x=xposition;
        this.y=yposition;
    }
    
}
