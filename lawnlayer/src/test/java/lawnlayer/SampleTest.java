package lawnlayer;


import org.checkerframework.checker.units.qual.A;
import processing.core.PApplet;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class SampleTest {

    @Test
    public void basicTest()
    {
        App app = new App();
        app.noLoop();
        PApplet.runSketch(new String[] {"App"}, app);

        app.setup();
        app.delay(1000);
        app.draw();
        app.keyReleased();
        app.keyPressed();
    }

    @Test
    public void EnemyTest()
    {
        Enemy enemy = new Enemy(1);
        enemy.geteX();
        enemy.geteY();
        enemy.getType();
    }

    @Test
    public void BallTest()
    {
        Ball ball = new Ball();
    }

    @Test
    public void GameEngineTest()
    {
        PApplet pApplet = new PApplet();
        ArrayList<Location> loc = new ArrayList<>();
        GameEngine gameEngine = new GameEngine(pApplet,loc,0);
    }
}
