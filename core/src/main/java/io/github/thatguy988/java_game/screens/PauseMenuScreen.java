package io.github.thatguy988.java_game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.thatguy988.java_game.Main;

public class PauseMenuScreen extends ScreenAdapter
{
    private Main game;
    private SpriteBatch batch;
    private BitmapFont font;

    public PauseMenuScreen(Main game) {
        this.game = game;
        this.batch = game.spriteBatch; 
        this.font = new BitmapFont();//custom font specific for pause menu todo
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        font.draw(batch, "Paused", Gdx.graphics.getWidth() / 2 - 30, Gdx.graphics.getHeight() / 2 + 50);
        font.draw(batch, "Press Enter to Resume", Gdx.graphics.getWidth() / 2 - 70, Gdx.graphics.getHeight() / 2);
        font.draw(batch, "Press Q to Quit", Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2 - 50);
        batch.end();

        if (Gdx.input.isKeyJustPressed(Keys.ENTER)) 
        {
            this.game.setScreen(this.game.getGameScreen()); // Resume the game by returning to the existing GameScreen

        }

        if (Gdx.input.isKeyJustPressed(Keys.Q)) 
        {
            this.game.setScreen(this.game.getMainMenuScreen()); // go to main menu
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
