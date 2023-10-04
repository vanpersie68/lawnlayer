package lawnlayer;

import processing.core.*;
import java.util.*;

public class GameEngine {

    public static boolean keyPress;
    private final PApplet drawerObj;
    private int order;
    private int level, num_live, num_Grass = 0, x = -1, y = -1;
    private long enemySd, PlayerSpeed, aSpeed;
    private long pTime, RedTime, freezeTime;
    private boolean path_start, path_stop, ifFreeze = false;
    private ArrayList<PImage> images;
    private ArrayList<Integer> pc = new ArrayList<>();
    private ArrayList<Integer> pr = new ArrayList<>();
    private Location mapLocation;
    private char[][] pt;
    private ArrayList<Enemy> enemy;
    private final ArrayList<Location> locations;
    private final Ball ball = new Ball();

    public GameEngine(PApplet drawer, ArrayList<Location> loc, int n){
        drawerObj = drawer;
        locations = loc;
        num_live = n;
        enemySd = pTime = PlayerSpeed = freezeTime = System.currentTimeMillis();
        aSpeed = RedTime = System.currentTimeMillis();
        mapLocation = locations.get(level);
        pt = mapLocation.getChars();
        enemy = mapLocation.getEnemies();
        for (Enemy enemy : enemy) {
            int row = (int) (Math.random()*32), col = (int) (Math.random()*63);
            while (pt[row][col] == 'X') {
                col = (int) (Math.random()*63);
                row = (int) (Math.random()*32);
            }
            enemy.seteX(row);
            enemy.seteY(col);
        }
    }

    public void SetupImage(ArrayList<PImage> images){
        this.images = images;
    }

    public void setLevel(int lv){
        level = lv;
    }

    public void setInfo(){
        drawerObj.fill(230, 230, 230);
        drawerObj.textSize(49);
        drawerObj.text((num_Grass * 100 / mapLocation.getSpace()) + "%/" + (int)(100 * mapLocation.getGoal()) + "%", 690, 44);
        drawerObj.text("Lives: " + num_live, 190, 44);
        drawerObj.textSize(30);
        drawerObj.text("Level " + (level+1), 1008, 60);
        for (int x1 = 0; x1 < pt.length; x1++){
            for (int x2 = 0; x2 < 63; x2++){
                if (pt[x1][x2] == 'X'){
                    drawerObj.image(images.get(3), x2* 20, x1*20 + 80);
                } else if (pt[x1][x2] == 'T') {
                    drawerObj.fill(drawerObj.color(30, 250, 20));
                    drawerObj.rect(x2* 20, 80 +x1* 20, 20, 20);
                } else if (pt[x1][x2] == 'G') {
                    drawerObj.image(images.get(2), x2* 20, x1* 20 + 80);
                } else if (pt[x1][x2] == 'R') {
                    drawerObj.fill(drawerObj.color(235, 10, 10));
                    drawerObj.rect(x2* 20, 80 +x1* 20, 20, 20);
                }
            }
        }
    }

    public void ToRed(){
        if (System.currentTimeMillis() - RedTime > 40){
            for (int idx = 0; idx < pc.size(); idx++){
                if ('R' == pt[pc.get(idx)][pr.get(idx)]){
                    if (idx - 1 >= 0 && 'T' == pt[pc.get(idx-1)][pr.get(idx-1)]){
                        pt[pc.get(idx-1)][pr.get(idx-1)] = 'R';
                    }
                    if (idx + 1 < pc.size() && 'T' == pt[pc.get(idx+1)][pr.get(idx+1)]){
                        pt[pc.get(idx+1)][pr.get(idx+1)] = 'R';
                        idx++;
                    }
                }
            }
            RedTime = System.currentTimeMillis();
        }
    }

    public void crash(){
        if (pt[ball.getX1()][ball.getX2()] == 'R'){
            ball.Reset();
            num_live--;
            for (int i = 0; i< pc.size(); i++){
                pt[pc.get(i)][pr.get(i)] = ' ';
            }
            CleanList();
        }
    }

    public void resetEnemy(){
        for (Enemy enemy : enemy) {
            if (ball.getX1() == enemy.geteX() && ball.getX2() == enemy.geteY()) {
                for (int t = 0; t < pc.size(); t++) {
                    pt[pc.get(t)][pr.get(t)] = ' ';
                }
                ball.Reset();
                CleanList();
                num_live--;
                break;
            }
        }
    }

    public void DrawMap() {
        if(num_live > 0) {
            if (level < locations.size()){
                setInfo();
                ToRed();
                if (System.currentTimeMillis() - PlayerSpeed > 9800){
                    x = (int)(Math.random()*32);
                    y = (int)(Math.random()*63);
                    PlayerSpeed = System.currentTimeMillis();
                }
                crash();
                resetEnemy();
                drawerObj.image(images.get(4), ball.getX2()* 20, ball.getX1()* 20 + 80);
                if (pt[ball.getX1()][ball.getX2()] == ' ') {
                    pt[ball.getX1()][ball.getX2()] = 'T';
                    pr.add(ball.getX2());
                    pc.add(ball.getX1());
                }
                if(!keyPress){
                    AutoMove();
                }
                if (path_start && path_stop) {
                    ChangeGreen();
                    path_start = path_stop = false;
                }
                if (ball.getX2()== y && ball.getX1()== x){
                    y = x = -1;
                    ifFreeze = true;
                    PlayerSpeed = freezeTime = System.currentTimeMillis();
                }
                if(System.currentTimeMillis() - freezeTime > 4500){
                    ifFreeze = false;
                }
                EmenyMove();
                ToSecondLevel();
            }else{
                drawerObj.textSize(60);
                drawerObj.fill(240,240,240);
                drawerObj.text("YOU WIN", 540, 340);
            }
        }else {
            drawerObj.textSize(60);
            drawerObj.fill(240,240,240);
            drawerObj.text("GAME OVER", 540, 340);
        }
    }

    public boolean ifEnemy(int x1, int x2, boolean[][] checkList){
        if (x1 < 0 || x1 > 31 || x2 < 0 || x2 > 63){return false;}
        for (Enemy e : enemy){if (e.geteX() == x1 && e.geteY() == x2){return false;}}
        if (pt[x1][x2] == 'G' || pt[x1][x2] == 'T' || pt[x1][x2] == 'X' || pt[x1][x2] == 'R' || checkList[x1][x2]){return true;}
        checkList[x1][x2] = true;
        boolean t = ifEnemy(x1+1, x2, checkList);
        boolean b = ifEnemy(x1-1, x2, checkList);
        boolean l = ifEnemy(x1, x2+1, checkList);
        boolean rig = ifEnemy(x1, x2-1, checkList);
        return t && b && l && rig;
    }

    public void resetFreeze(boolean ifFreeze){
        this.ifFreeze = ifFreeze;
    }

    public void ToSecondLevel() {
        if ((int)(mapLocation.getGoal() * 100) <= (num_Grass * 100 / mapLocation.getSpace())) {
            level += 1;
            if (level < locations.size()) {
                ball.Reset();
                resetFreeze(false);
                mapLocation = locations.get(level);
                pt = mapLocation.getChars();
                num_Grass = 0;
                enemySd = pTime = aSpeed = System.currentTimeMillis();
                enemy = mapLocation.getEnemies();
                for (Enemy enemy : enemy) {
                    int Y = (int) (Math.random() * 63), X = (int)                (Math.random() * 32);
                    while (pt[X][Y] != ' ') {
                        X = (int) (Math.random() * 32); Y = (int) (Math.random() * 63);
                    }
                    enemy.seteX(X);
                    enemy.seteY(Y);

                }
            }
        }
    }
    public void ballMove(int cd){
        ball.Move(cd, pTime, pt, pr, pc, this, order);
    }
    public void updatePtime(){
        pTime = System.currentTimeMillis();
    }
    public void setPath_stop(boolean s){
        this.path_stop = s;
    }
    public void setPath_start(boolean s){
        this.path_start = s;
    }

    public void AutoMove(){
        int pY = ball.getX1(), pX = ball.getX2();
        if (System.currentTimeMillis() - aSpeed > 55) {
            if (order == 38 && pt[pY][pX] == 'T') {
                ball.Move(38, pTime, pt, pr, pc, this, order);
            } else if (order == 40 && 'T' == pt[pY][pX]) {
                ball.Move(40, pTime, pt, pr, pc, this, order);
            } else if (order == 37 && pt[pY][pX] == 'T') {
                ball.Move(37, pTime, pt, pr, pc, this, order);
            } else if (order == 39 && 'T' == pt[pY][pX]) {
                ball.Move(39, pTime, pt, pr, pc, this, order);
            }
            aSpeed = System.currentTimeMillis();
        }
    }
    public void setOrder(int order){
        this.order = order;
    }

    public void liveDown(){
        --num_live;
    }

    void CleanList(){
        pr = new ArrayList<>();
        pc = new ArrayList<>();
    }
    public void grassDown(){
        --num_Grass;
    }
    private void toGreen(int r, int c){
        if (r < 0 || r > 31 || c < 0 || c > 63){
            return;
        }
        if (pt[r][c] == 'G' || pt[r][c] == 'T'){
            return;
        }
        if(pt[r][c] == 'X' || pt[r][c] == 'R'){
            return;
        }
        num_Grass++;
        pt[r][c] = 'G';
        toGreen(r+1, c);
        toGreen(r, c-1);
        toGreen(r, c+1);
        toGreen(r-1, c);
    }
    public void EmenyMove(){
        if (System.currentTimeMillis() - enemySd > 100){
            if(!ifFreeze){
                for (Enemy enemy : enemy){
                    enemy.Move(this, pt);}
            }
            enemySd = System.currentTimeMillis();
        }
        for (Enemy enemy : enemy){
            drawerObj.image(images.get(enemy.getType()), enemy.geteY()*20, enemy.geteX()*20+80);
        }
    }

    public void ChangeGreen(){
        for (int x1 = 0; x1 < 32; x1++){
            for (int x2 = 0; x2 < 63; x2++){
                boolean[][] check = new boolean[32][63];
                if (pt[x1][x2] == ' ' && ifEnemy(x1, x2, check)){
                    toGreen(x1, x2);
                }
            }
        }
        for (int i = 0; i <  pc.size(); i++){
            pt[pc.get(i)][pr.get(i)] = 'G';
            num_Grass++;
        }
        CleanList();
    }

}
