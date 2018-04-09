package hu.ait.android.minesweeper;

import java.util.Random;

/**
 * Created by teagu_000 on 6/10/2017.
 */

public class MineSweeperModel {

    private static MineSweeperModel MineSweeperModel = null;

    private MineSweeperModel(){
    }

    public static MineSweeperModel getInstance(){
        if(MineSweeperModel==null){
            MineSweeperModel = new MineSweeperModel();
            MineSweeperModel.setMines();
            MineSweeperModel.setNearby();
        }
        return MineSweeperModel;
    }

    public static final int EMPTY = 0;
    public static final int MINE = -1;
    public static final int EXPLODEDMINE=-2;
    public static final int FLAG = -3;

    private int numMines=3;
    private int numMinesLeft=3;

    //this model contains numbers and mines
    private int[][] model = {
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY}
    };


    //method to calculate nearby mines in field
    private void setNearby(){
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if(model[i][j]!=MINE){

                    if(i-1>-1&&j-1>-1) {
                        if (model[i - 1][j - 1] == MINE) {
                            model[i][j] += 1;
                        }
                    }

                    if(j-1>-1) {
                        if (model[i][j - 1] == MINE) {
                            model[i][j] += 1;
                        }
                    }

                    if(i+1<5&&j-1>-1) {
                        if (model[i + 1][j - 1] == MINE) {
                            model[i][j] += 1;
                        }
                    }

                    if(i-1>-1) {
                        if (model[i - 1][j] == MINE) {
                            model[i][j] += 1;
                        }
                    }

                    if(i+1<5) {
                        if (model[i + 1][j] == MINE) {
                            model[i][j] += 1;
                        }
                    }

                    if(i-1>-1&&j+1<5) {
                        if (model[i - 1][j + 1] == MINE) {
                            model[i][j] += 1;
                        }
                    }

                    if(j+1<5) {
                        if (model[i][j + 1] == MINE) {
                            model[i][j] += 1;
                        }
                    }

                    if(i+1<5&&j+1<5) {
                        if (model[i + 1][j + 1] == MINE) {
                            model[i][j] += 1;
                        }
                    }

                }
            }
        }
    }

    //method to rando generate mine locations
    private void setMines(){
        for (int i = 0; i < numMines; i++){
            int x = new Random(System.currentTimeMillis()).nextInt(4);
            int y = new Random(System.currentTimeMillis()).nextInt(4);

            //ensures that it sets unique mines
            while(model[x][y]==MINE){
                x = new Random(System.currentTimeMillis()).nextInt(4);
                y = new Random(System.currentTimeMillis()).nextInt(4);
            }

            model[x][y]=MINE;
        }
    }



    public int getFieldContent(int x, int y) {
        return model[x][y];
    }

    public void setFieldContent (int x, int y, int content){
        model[x][y] = content;
    }




    public static final int INVISIBLE = 3;
    public static final int VISIBLE = 4;

    //this model says if the field is visible or not
    private int[][] modelClicked = {
            {INVISIBLE,INVISIBLE,INVISIBLE,INVISIBLE,INVISIBLE},
            {INVISIBLE,INVISIBLE,INVISIBLE,INVISIBLE,INVISIBLE},
            {INVISIBLE,INVISIBLE,INVISIBLE,INVISIBLE,INVISIBLE},
            {INVISIBLE,INVISIBLE,INVISIBLE,INVISIBLE,INVISIBLE},
            {INVISIBLE,INVISIBLE,INVISIBLE,INVISIBLE,INVISIBLE}
    };

    public int getVisiblility(int x, int y) {
        return modelClicked[x][y];
    }

    public void setVisibility (int x, int y, int content){
        modelClicked[x][y] = content;
    }


    public void resetGame(){
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                model[i][j]=EMPTY;
                modelClicked[i][j]=INVISIBLE;
            }
        }
        MineSweeperModel.setMines();
        MineSweeperModel.setNearby();
        numMinesLeft=numMines;
    }

    public boolean checkWin(){
        numMinesLeft--;
        if(numMinesLeft==0){
            return true;
        }
        return false;
    }


    public void setMines (int x){
        numMines=x;
    }

}
