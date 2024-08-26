package io.github.thatguy988.java_game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;


public interface Shooter extends Component {
    float getWidth();
    float getHeight();
    float getTimeSinceLastShot();
    void setTimeSinceLastShot(float time);
    boolean isFiring();
    void setisFiring(boolean firing);
    Vector2 getKnockBackForce();
    void setKnockBackForce(Vector2 force);
    void clearKnockBackForce();

}
