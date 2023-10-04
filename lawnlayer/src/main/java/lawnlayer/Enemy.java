package lawnlayer;

public class Enemy
{

    private int type, eX, eY, direct = (int)(Math.random()*4);


    public Enemy(int type) {
        this.type = type;
    }

    public int getType(){
        return this.type;
    }
    public int geteX(){
        return this.eX;
    }
    public int geteY(){
        return this.eY;
    }
    public void seteX(int eX){
        this.eX = eX;
    }
    public void seteY(int eY){
        this.eY = eY;
    }

    public void Move(GameEngine ge, char[][] c){
        if (direct == 0){
            if (c[eX -1][eY -1] == ' '){
                eX -=1;
                eY -=1;
            }else{
                if (c[eX][eY -1] == 'T'){c[eX][eY -1] = 'R';}
                if (c[eX -1][eY] == 'T'){c[eX -1][eY] = 'R';}
                if (c[eX][eY -1] != ' '){
                    direct = 1;}
                if (c[eX -1][eY] != ' '){
                    direct = 2;
                }
                if (direct == 0 ) {
                    direct = 3;
                }
                if (type == 1){
                    if (c[eX][eY -1] == 'G'){
                        c[eX][eY -1]=' ';ge.grassDown();
                    }
                    if (c[eX -1][eY] == 'G'){
                        c[eX -1][eY]=' ';ge.grassDown();
                    }
                }
            }
        }else if (direct == 3) {
            if (c[eX +1][eY +1] == ' '){
                eX +=1;
                eY +=1;
            }else {
                if (c[eX][eY +1] == 'T') {c[eX][eY +1] = 'R';}
                if (c[eX +1][eY] == 'T') {c[eX +1][eY] = 'R';}
                if (c[eX][eY +1] != ' ') {
                    direct = 2;}
                if (c[eX +1][eY] != ' ') {
                    direct = 1;}
                if (direct == 3 ) {
                    direct = 0;}
                if (type == 1) {
                    if (c[eX][eY +1] == 'G') {c[eX][eY +1] = ' ';ge.grassDown();}
                    if (c[eX +1][eY] == 'G') {c[eX +1][eY] = ' ';ge.grassDown();}
                }
            }
        } else if (direct == 1) {
            if (c[eX -1][eY +1] == ' '){
                eX -=1;
                eY +=1;
            }else {
                if (c[eX -1][eY] == 'T') {c[eX -1][eY] = 'R';}
                if (c[eX][eY +1] == 'T') {c[eX][eY +1] = 'R';}
                if (c[eX][eY +1] != ' ') {
                    direct = 0;}
                if (c[eX -1][eY] != ' ') {
                    direct = 3;}
                if (direct == 1 ) {
                    direct = 2;}
                if (type == 1) {
                    if (c[eX][eY +1] == 'G') {c[eX][eY +1] = ' ';ge.grassDown();}
                    if (c[eX -1][eY] == 'G') {c[eX -1][eY] = ' ';ge.grassDown();}
                }
            }
        }else if (direct == 2) {
            if (c[eX +1][eY -1] == ' '){
                eX++;
                eY--;
            }else {
                if (c[eX][eY -1] == 'T') {c[eX][eY -1] = 'R';}
                if (c[eX +1][eY] == 'T') {c[eX +1][eY] = 'R';}
                if (c[eX][eY -1] != ' ') {
                    direct = 3;}
                if (c[eX +1][eY] != ' ') {
                    direct = 0;}
                if (direct == 2 ) {
                    direct = 1;}
                if (type == 1) {
                    if (c[eX][eY -1] == 'G') {c[eX][eY -1] = ' ';ge.grassDown();}
                    if (c[eX +1][eY] == 'G') {c[eX +1][eY] = ' ';ge.grassDown();}
                }
            }
        }
    }

}
