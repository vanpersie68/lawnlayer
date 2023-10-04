package lawnlayer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Location {

    String Fp;
    int idx = 0;
    double goal;
    int space = 0;
    char[][] LocChar;
    ArrayList<Enemy> enemies;

    public char[][] getChars() {
        return this.LocChar;
    }
    public int getSpace(){
        return this.space;}

    public double getGoal(){
        return this.goal;
    }
    public ArrayList<Enemy> getEnemies(){
        return this.enemies;
    }

    public Location(String Fp, ArrayList<Enemy> e, double goal) {
        this.Fp = Fp;
        enemies = e;
        this.goal = goal;
        LocChar = readFile(Fp);
        countSpace();
    }

    public void countSpace(){
        for (int X1 = 0; X1 <LocChar.length; X1++){
            for (int X2 = 0; X2<LocChar[X1].length; X2++){
                if (LocChar[X1][X2] == ' '){
                    space++;}
            }
        }
    }

    private char[][] readFile(String f) {
        char[][] chs = new char[32][63];
        try{
            Scanner scan = new Scanner(new File(f));
            while (scan.hasNextLine()){
                chs[idx] = scan.nextLine().toCharArray();
                idx++;
            }
            return chs;
        }catch (FileNotFoundException e){
            return null;
        }
    }

}