package io.github.thatguy988.java_game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class CameraComponent implements Component {
    public OrthographicCamera camera;

    public CameraComponent(float viewportWidth, float viewportHeight) {
        this.camera = new OrthographicCamera(viewportWidth, viewportHeight);
        this.camera.setToOrtho(false, viewportWidth, viewportHeight);
    }
}
