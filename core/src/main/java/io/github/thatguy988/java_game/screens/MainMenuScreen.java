package io.github.thatguy988.java_game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.thatguy988.java_game.Main;

public class MainMenuScreen extends ScreenAdapter {
    private Main game;
    private SpriteBatch batch;
    private BitmapFont font;

    public MainMenuScreen(Main game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.font = new BitmapFont(); // use custom font for main menu
    }

    @Override
    public void show() 
    {
        this.batch = new SpriteBatch();
        this.font = new BitmapFont(); 
    } // Called when this screen becomes the current screen for a Game


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        font.draw(batch, "Main Menu", Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2 + 50);
        font.draw(batch, "Press Enter to Start", Gdx.graphics.getWidth() / 2 - 70, Gdx.graphics.getHeight() / 2);
        font.draw(batch, "Press Q to Quit", Gdx.graphics.getWidth() / 2 - 60, Gdx.graphics.getHeight() / 2 - 50);
        batch.end();

        if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
            this.game.setScreen(this.game.getGameScreen()); 
        }

        if (Gdx.input.isKeyJustPressed(Keys.Q))  
        {
            Gdx.app.exit();
        }
    }

    @Override
    public void hide() 
    {
        dispose();
    } // Called when this screen is no longer the current screen for a Game


    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
