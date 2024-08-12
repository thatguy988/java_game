package io.github.thatguy988.java_game.components;

import com.badlogic.ashley.core.Component;


public class FacingDirectionComponent implements Component
{
    public enum Direction
    {
        LEFT,RIGHT,DOWN,UP,UPRIGHT,UPLEFT,DOWNRIGHT,DOWNLEFT
    }

    public Direction direction = Direction.RIGHT;
    
}
