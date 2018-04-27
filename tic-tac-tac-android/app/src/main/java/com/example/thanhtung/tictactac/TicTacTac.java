package com.example.thanhtung.tictactac;

import android.graphics.Point;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;
import android.widget.ToggleButton;

public class TicTacTac extends AppCompatActivity {

    private ImageButton[][] imageButtonArray;
    private int N = 6;
    private boolean currentX = true;

    private Game game;
    private boolean clickable;
    private boolean playerFirst = true;

    private Button xButton,oButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_tac);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            playerFirst = extras.getBoolean("playerFirst");
            N = extras.getInt("N");
        }



        initImageButtonArray();
        initUI();
        initGame();
    }

    public void initImageButtonArray(){

        imageButtonArray = new ImageButton[N][N];

        for (int i = 0; i < N; i++){
            for (int j = 0; j < N; j++){
                imageButtonArray[i][j] = new ImageButton(this);
                ImageButtonOnClickListener l = new ImageButtonOnClickListener(i,j);
                imageButtonArray[i][j].setOnClickListener(l);
            }
        }
    }

    private class ImageButtonOnClickListener implements View.OnClickListener{
        private int localX;
        private int localY;

        public ImageButtonOnClickListener(int localX, int localY){
            this.localX = localX;
            this.localY = localY;
        }

        @Override
        public void onClick(View v) {
            if (!clickable) return;
            System.out.println(localX + " - " + localY);
            int result;
            if (currentX) result = game.move(localX, localY,1);
            else result = game.move(localX,localY,-1);

            // Return value: 1: Human win
            //              -1: Human lose
            //               0: Continue
            //              -2: Invalid move
            switch (result){
                case -2: Toast.makeText(getApplicationContext(), "Invalid move!", Toast.LENGTH_LONG).show(); break;
                case  1: Toast.makeText(getApplicationContext(), "You win!", Toast.LENGTH_LONG).show();
                    imageButtonArray[localX][localY].setImageResource(currentX?R.drawable.tictactacx:R.drawable.tictactaco);
                    clickable = false;
                    break;
                case -1: Toast.makeText(getApplicationContext(), "You lose!", Toast.LENGTH_LONG).show();
                    imageButtonArray[localX][localY].setImageResource(currentX?R.drawable.tictactacx:R.drawable.tictactaco);
                    clickable = false;
                    break;
                default:
                    imageButtonArray[localX][localY].setImageResource(currentX?R.drawable.tictactacx:R.drawable.tictactaco);
                    imageButtonArray[game.nextMoveX][game.nextMoveY].setImageResource(game.nextMoveType==1?R.drawable.tictactacx:R.drawable.tictactaco);
            }



        }
    }

    public void initUI(){
        TableLayout tl = (TableLayout) findViewById(R.id.tablelayout);
        tl.setStretchAllColumns(true);

        //Get screen size
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        params.setMargins(-5, -5, -5, -5);

        for (int i = 0; i < N; i++) {
            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);

            for (int j = 0; j < N; j++) {
                imageButtonArray[i][j].setMinimumHeight(size.x/N + 10);
                imageButtonArray[i][j].setPadding(0,0,0,0);
                imageButtonArray[i][j].setScaleType(ImageView.ScaleType.CENTER);
                //addBtn.setMinimumWidth(size.x/N);
                imageButtonArray[i][j].setLayoutParams(params);
                row.addView(imageButtonArray[i][j]);
            }
            tl.addView(row, i);
        }

        xButton = (Button) findViewById(R.id.xButton);
        oButton = (Button) findViewById(R.id.oButton);
        xButton.setTextSize(30);
        oButton.setTextSize(10);
        xButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                currentX = true;
                xButton.setTextSize(30);
                oButton.setTextSize(10);
            }
        });
        oButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                currentX = false;
                oButton.setTextSize(30);
                xButton.setTextSize(10);
            }
        });

    }

    public void initGame(){
        this.game = new Game(N);
        this.clickable = true;

        if (!playerFirst){
            game.moveFirst();
            imageButtonArray[game.nextMoveX][game.nextMoveY].setImageResource(game.nextMoveType==1?R.drawable.tictactacx:R.drawable.tictactaco);
        }
    }

}
