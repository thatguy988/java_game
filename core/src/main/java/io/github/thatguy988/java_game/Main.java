package io.github.thatguy988.java_game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import io.github.thatguy988.java_game.screens.GameScreen;
import io.github.thatguy988.java_game.screens.MainMenuScreen;
import io.github.thatguy988.java_game.screens.PauseMenuScreen;

public class Main extends Game {
    public SpriteBatch spriteBatch;
    public ShapeRenderer shapeRenderer;
    private GameScreen gameScreen;
    private MainMenuScreen mainMenuScreen;
    private PauseMenuScreen pauseMenuScreen;

    @Override
    public void create() {
        this.spriteBatch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();
        setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render(); 
    }

    @Override
    public void dispose() {
        super.dispose();
        spriteBatch.dispose();
        shapeRenderer.dispose();
    }

    public Screen getMainMenuScreen() 
    {
        if (this.mainMenuScreen == null) 
        {
            this.mainMenuScreen = new MainMenuScreen(this);
        }
        return this.mainMenuScreen;
    }

    public Screen getGameScreen()
    {
        if(this.gameScreen == null)
        {
            this.gameScreen = new GameScreen(this);
        }
        return this.gameScreen;
    }

    public Screen getPauseMenuScreen() 
    {
        if (this.pauseMenuScreen == null) 
        {
            this.pauseMenuScreen = new PauseMenuScreen(this);
        }
        return this.pauseMenuScreen;
    }

}







