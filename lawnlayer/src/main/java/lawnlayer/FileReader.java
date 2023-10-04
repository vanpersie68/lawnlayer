package lawnlayer;

import processing.data.*;
import java.io.*;
import java.util.*;
import static processing.core.PApplet.*;

public class FileReader {

    String path;
    GameEngine ge;

    public FileReader(String path){
        this.path = path;
    }

    public GameEngine readJsonFile(App mianObj, ArrayList<Location> maps)
    {
        JSONArray a = loadJSONObject(new File(path)).getJSONArray("levels");
        int b = loadJSONObject(new File(path)).getInt("lives");
        for (int c = 0; c < a.size(); c++){
            ArrayList<Enemy> d = new ArrayList<>();
            JSONArray f = a.getJSONObject(c).getJSONArray("enemies");
            for (int i = 0; i < f.size(); i++){
                d.add(new Enemy(f.getJSONObject(i).getInt("type")));
            }
            Location gameMap = new Location(a.getJSONObject(c).getString("outlay"), d, a.getJSONObject(c).getDouble("goal"));
            if(gameMap.getChars() != null){
                maps.add(gameMap);
            }else{
                System.out.println("path is invalid");
            }
        }
        ge = new GameEngine(mianObj, maps, b);
        return ge;
    }
}
