package hu.ait.android.minesweeper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by teagu_000 on 5/10/2017.
 */


public class MineSweeperView extends View {

    int Flag = 0;
    boolean gameRunning=true;

    Paint paintBg;
    Paint paintLine;

    public MineSweeperView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        //black fill
        paintBg = new Paint();
        paintBg.setColor(Color.BLACK);
        paintBg.setStyle(Paint.Style.FILL);

        //for lines AND text
        paintLine = new Paint();
        paintLine.setColor(Color.WHITE);
        paintLine.setStrokeWidth(5);
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setTextSize(125);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(0, 0, getWidth(), getHeight(), paintBg);

        drawGameArea(canvas);

        drawIcons(canvas);

    }

    private void drawGameArea(Canvas canvas) {
        // border
        canvas.drawRect(0, 0, getWidth(), getHeight(), paintLine);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Drawable tile = ContextCompat.getDrawable(getContext(),R.drawable.tile);
                tile.setBounds(i*getWidth()/5,j*getHeight()/5,(i+1)*getWidth()/5,(j+1)*getWidth()/5);
                tile.draw(canvas);
            }
        }

    }


    //this draws the icons in each field
    private void drawIcons(Canvas canvas) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if(MineSweeperModel.getInstance().getVisiblility(i,j)==MineSweeperModel.VISIBLE){
                    Drawable pic;
                    int icon = MineSweeperModel.getInstance().getFieldContent(i,j);

                    switch(icon) {
                        case MineSweeperModel.MINE: pic = ContextCompat.getDrawable(getContext(),R.drawable.bomb_normal);
                            break;
                        case MineSweeperModel.EXPLODEDMINE: pic = ContextCompat.getDrawable(getContext(),R.drawable.bomb_exploded);
                            break;
                        case MineSweeperModel.FLAG: pic = ContextCompat.getDrawable(getContext(),R.drawable.flag);
                            break;
                        case MineSweeperModel.EMPTY: pic = ContextCompat.getDrawable(getContext(),R.drawable.tile_depressed);
                            break;
                        case 1: pic = ContextCompat.getDrawable(getContext(),R.drawable.number_1);
                            break;
                        case 2: pic = ContextCompat.getDrawable(getContext(),R.drawable.number_2);
                            break;
                        case 3: pic = ContextCompat.getDrawable(getContext(),R.drawable.number_3);
                            break;
                        case 4: pic = ContextCompat.getDrawable(getContext(),R.drawable.number_4);
                            break;
                        case 5: pic = ContextCompat.getDrawable(getContext(),R.drawable.number_5);
                            break;
                        case 6: pic = ContextCompat.getDrawable(getContext(),R.drawable.number_6);
                            break;
                        case 7: pic = ContextCompat.getDrawable(getContext(),R.drawable.number_7);
                            break;
                        case 8: pic = ContextCompat.getDrawable(getContext(),R.drawable.number_8);
                            break;
                        default: pic = ContextCompat.getDrawable(getContext(),R.drawable.tile);
                            break;
                    }

                    //all tiles except for the depressed one look better if they're not the full size
                    pic.setBounds((i*getWidth()/5)+12,(j*getHeight()/5)+12,((i+1)*getWidth()/5)-12,((j+1)*getWidth()/5)-12);

                    if(icon==MineSweeperModel.EMPTY){
                        pic.setBounds(i*getWidth()/5,j*getHeight()/5,(i+1)*getWidth()/5,(j+1)*getWidth()/5);
                    }

                    pic.draw(canvas);
                }
            }
        }

    }




    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            if(gameRunning) {
                int tX = ((int) event.getX() / (getWidth() / 5));
                int tY = ((int) event.getY() / (getHeight() / 5));

                if (MineSweeperModel.getInstance().getVisiblility(tX, tY) ==
                        MineSweeperModel.INVISIBLE) {
                    MineSweeperModel.getInstance().setVisibility(tX, tY,
                            MineSweeperModel.VISIBLE);

                    if (Flag == 0) {
                        normalConditions(tX, tY);
                    }

                    if (Flag == 1) {
                        flagConditions(tX, tY);
                    }

                    invalidate();
                }
            }
        }

        return super.onTouchEvent(event);
    }

    //this executes if we're in 'Try a Field' mode
    public void normalConditions(int tX,int tY){
        if(MineSweeperModel.getInstance().getFieldContent(tX,tY)
                ==MineSweeperModel.MINE){
            MineSweeperModel.getInstance().setFieldContent(tX,tY,MineSweeperModel.EXPLODEDMINE);
            setMinesVisible();
            gameRunning=false;
            ((MainActivity) getContext()).printLose();
        }
    }

    //this executes if we're in 'Flag' mode
    public void flagConditions(int tX, int tY){
        if(MineSweeperModel.getInstance().getFieldContent(tX,tY)
                !=MineSweeperModel.MINE){
            setMinesVisible();
            gameRunning=false;
            ((MainActivity) getContext()).printLose();

        }else{
            MineSweeperModel.getInstance().setFieldContent(tX,tY,MineSweeperModel.FLAG);
            if(MineSweeperModel.getInstance().checkWin()){
                setAllVisible();
                ((MainActivity) getContext()).printWin();
            }
        }

    }

    public void setMinesVisible(){
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if(MineSweeperModel.getInstance().getFieldContent(i,j)==MineSweeperModel.MINE) {
                    MineSweeperModel.getInstance().setVisibility(i, j, MineSweeperModel.VISIBLE);
                }
            }
        }
    }

    public void setAllVisible(){
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                MineSweeperModel.getInstance().setVisibility(i, j, MineSweeperModel.VISIBLE);
            }
        }
    }


    public void clearBoard() {
        MineSweeperModel.getInstance().resetGame();
        Flag=0;
        gameRunning=true;
        invalidate();
    }


    public void changeFlag(){
        if(Flag==0){
            Flag=1;
        }else if(Flag==1){
            Flag=0;
        }
    }

}
