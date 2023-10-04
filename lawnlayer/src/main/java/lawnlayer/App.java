package lawnlayer;

import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;

public class App extends PApplet
{
    public String path = "config.json";
	public String[] ObjectList = {"worm", "beetle", "grass", "concrete_tile", "ball"};
    public ArrayList<PImage> images;
    private ArrayList<Location> location;
    private FileReader fileReader = new FileReader(this.path);
    private GameEngine gameEngine;

    public void settings()
    {
        this.size(1260, 720);
    }

    public void setup()
    {
        images = new ArrayList<>();
        location = new ArrayList<>();
        frameRate(60);
        for (int i = 0; i < ObjectList.length; i++){
            // System.out.println(ObjectList[i]+".png");
            PImage temp = loadImage(this.getClass().getResource(ObjectList[i]+".png").getPath());
            images.add(temp);
        }
        gameEngine = fileReader.readJsonFile(this, location);
        gameEngine.setLevel(0);
        gameEngine.SetupImage(images);
    }
    /**
     * Draw all elements in the game by current frame. 
    */
    public void draw()
    {
        fill(color(110, 50, 34));
        this.rect(-1,-1, 1261, 721);

        gameEngine.DrawMap();
    }

    @Override
    public void keyReleased(){
        GameEngine.keyPress = false;
    }

    @Override
    public void keyPressed(){
        GameEngine.keyPress = true;
        this.gameEngine.ballMove(keyCode);
    }

    public static void main(String[] args) {
        PApplet.main("lawnlayer.App");
    }
}
