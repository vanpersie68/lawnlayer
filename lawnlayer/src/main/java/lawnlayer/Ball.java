package lawnlayer;

import java.util.ArrayList;

public class Ball
{
    int x1 = 0, x2 = 0;

    public void Move(int in, long num, char[][] c, ArrayList<Integer> rs, ArrayList<Integer> cs, GameEngine g, int d){

        if (System.currentTimeMillis() - num > 10){
            if (in == 40){
                if (x1 +1<32 && c[x1 +1][x2] != 'T'){
                    if (c[x1][x2] == 'X' || c[x1][x2] == 'G'){if(c[x1 +1][x2] == ' '){g.setPath_start(true);}}
                    if (c[x1 +1][x2] == 'X' || c[x1 +1][x2] == 'G'){if (c[x1][x2] == 'T'){g.setPath_stop(true);}}
                    if (c[x1][x2] == 'X' || c[x1][x2] == 'G' || c[x1][x2] == 'T' && d != 38){ x1++; g.setOrder(40);}
                }else {
                    if (c[x1 -1][x2] == 'X' || c[x1 - 1][x2] == 'G') {if (c[x1][x2] == 'T'){g.setPath_stop(true);}}
                    if (c[x1][x2] == 'T' && d == 38) {
                        x1--;} else if (x1 + 1 < 32  && c[x1 + 1][x2] == 'T') {
                        g.resetFreeze(false);
                        g.liveDown();
                        for (int i=0; i<rs.size(); i++){ c[cs.get(i)][rs.get(i)]=' ';}
                        g.CleanList();}
                }
            }else if (in == 39) {
                if (x2 +1 < 63 && c[x1][x2 +1] != 'T'){
                    if ((c[x1][x2] == 'X' || c[x1][x2] == 'G') && c[x1][x2 +1] == ' '){g.setPath_start(true);}
                    if (c[x1][x2] == 'T' && (c[x1][x2 +1] == 'X' || c[x1][x2 +1] == 'G')){g.setPath_stop(true);}
                    if (c[x1][x2] == 'X' || c[x1][x2] == 'G' || c[x1][x2] == 'T' && d != 37){
                        x2++;g.setOrder(39);}
                }else {
                    if (c[x1][x2] == 'T' && (c[x1][x2 -1] == 'X' || c[x1][x2 -1] == 'G')) {g.setPath_stop(true);}
                    if (c[x1][x2] == 'T' && d == 37) {
                        x2--;} else if (x2 +1 < 63 && c[x1][x2 +1] == 'T') {
                        g.resetFreeze(false);
                        g.liveDown();
                        for (int i=0; i<rs.size(); i++){ c[cs.get(i)][rs.get(i)]=' ';}
                        g.CleanList();}
                }
            }else if (in == 38){
                if (x1 -1 >= 0 && c[x1 -1][x2] != 'T'){
                    if ((c[x1][x2] == 'X' || c[x1][x2] == 'G') && c[x1 -1][x2] == ' '){g.setPath_start(true);}
                    if (c[x1][x2] == 'T' && (c[x1 -1][x2] == 'X' || c[x1 -1][x2] == 'G')){g.setPath_stop(true);}
                    if (c[x1][x2] == 'X' || c[x1][x2] == 'G' || c[x1][x2] == 'T' && d != 40){
                        x1--;g.setOrder(38);}
                }else{
                    if (c[x1][x2] == 'T' && (c[x1 +1][x2] == 'X' || c[x1 +1][x2] == 'G')){g.setPath_stop(true);}
                    if (c[x1][x2] == 'T' && d == 40){
                        x1++;}else if(x1 -1>=0 && c[x1 -1][x2] == 'T'){
                        g.resetFreeze(false);
                        g.liveDown();
                        for (int i=0; i<rs.size(); i++){ c[cs.get(i)][rs.get(i)]=' ';}
                        g.CleanList();}
                }
            }else if (in == 37){
                if (x2 -1 >= 0 && c[x1][x2 -1] != 'T'){
                    if ((c[x1][x2] == 'X' || c[x1][x2] == 'G') && c[x1][x2 -1] == ' '){g.setPath_start(true);}
                    if (c[x1][x2] == 'T' && (c[x1][x2 -1] == 'X' || c[x1][x2 -1] == 'G')){g.setPath_stop(true);}
                    if (c[x1][x2] == 'X' || c[x1][x2] == 'G' || c[x1][x2] == 'T' && d != 39){
                        x2--; g.setOrder(37);}
                }else {
                    if (c[x1][x2] == 'T' && (c[x1][x2 +1] == 'X' || c[x1][x2 +1] == 'G')) {g.setPath_stop(true);}
                    if (c[x1][x2] == 'T' && d == 39) {
                        x2++;} else if (x2 -1 >= 0 && c[x1][x2 -1] == 'T') {
                        g.resetFreeze(false);
                        g.liveDown();
                        for (int i=0; i<rs.size(); i++){ c[cs.get(i)][rs.get(i)]=' ';}
                        g.CleanList();}
                }
            }
            g.updatePtime();
        }
    }

    public void Reset(){
        x1 = x2 = 0;
    }
    public int getX1(){
        return x1;
    }
    public int getX2(){
        return x2;
    }

}
