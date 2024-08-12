package io.github.thatguy988.java_game.components;

import com.badlogic.ashley.core.Component;


public interface Shooter extends Component {
    float getWidth();
    float getHeight();
    float getTimeSinceLastShot();
    void setTimeSinceLastShot(float time);
    boolean isFiring();
    void setisFiring(boolean firing);
}
