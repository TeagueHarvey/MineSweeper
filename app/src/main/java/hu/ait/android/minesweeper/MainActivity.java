package hu.ait.android.minesweeper;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/*
NOTE - Additional features
-Really damn nice UI
-When you win, all tiles are revealed. When you lose, only mines are revealed
(emulating actual game behaviour)
-When you lose, you can no longer click on the board and must hit 'Reset Game'
-Added variable level of mines. I was going to do a variable board size too,
but I figured that it would be hard to tap with your finger.

...9 mines in a 5x5 space is hard enough anyway, jesus christ. I've yet to win it.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MineSweeperView board = (MineSweeperView) findViewById(R.id.MineSweeperView);


        Button btnToggle = (Button) findViewById(R.id.btnToggle);
        btnToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                board.changeFlag();
            }

        });



        Button btnReset = (Button) findViewById(R.id.btnReset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                board.clearBoard();
            }

        });



        Button btn3Mines = (Button) findViewById(R.id.btn3Mines);
        btn3Mines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MineSweeperModel.getInstance().setMines(3);
                board.clearBoard();
            }

        });

        Button btn5Mines = (Button) findViewById(R.id.btn5Mines);
        btn5Mines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MineSweeperModel.getInstance().setMines(5);
                board.clearBoard();
            }

        });

        Button btn7Mines = (Button) findViewById(R.id.btn7Mines);
        btn7Mines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MineSweeperModel.getInstance().setMines(7);
                board.clearBoard();
            }

        });

        Button btn9Mines = (Button) findViewById(R.id.btn9Mines);
        btn9Mines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MineSweeperModel.getInstance().setMines(9);
                board.clearBoard();
            }

        });
    }


    public void printLose(){
        final LinearLayout layoutContent = findViewById(R.id.layoutContent);
        Snackbar.make(layoutContent, "You lost buddy :(", Snackbar.LENGTH_LONG).show();
    }

    public void printWin(){
        final LinearLayout layoutContent = findViewById(R.id.layoutContent);
        Snackbar.make(layoutContent, "You won!! :)", Snackbar.LENGTH_LONG).show();
    }

}
