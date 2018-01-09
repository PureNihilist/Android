package com.example.pc.kolko_i_krzyzyk;

import android.media.MediaPlayer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView textView;
    ConstraintLayout view;
    int [] boardStatus = new int[9]; // 0 - not used 1 - circle 2 - cross
    boolean firstPlayerTurn = true;
    boolean computerPlayer = false;
    MediaPlayer wrongSoundMP = null;
    MediaPlayer gameWinSoundMP = null;
    MediaPlayer tieWinSoundMp = null;
    ImageView boardImage = null;
    private void init() {
        for(int i = 0 ; i < 9 ; i++) {
          boardStatus[i] = 0;
        }
    }

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntArray("BoardState",boardStatus);
        outState.putBoolean("computerOn",computerPlayer);
        outState.putString("textView",textView.getText().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textView = findViewById(R.id.textView);
        boardImage = findViewById(R.id.boardImage);
        wrongSoundMP = MediaPlayer.create(this,R.raw.wrong);
        gameWinSoundMP = MediaPlayer.create(this,R.raw.applause);
        tieWinSoundMp = MediaPlayer.create(this,R.raw.tie);
        view = findViewById(R.id.constraintLayout);
        if (savedInstanceState != null) {
            computerPlayer = savedInstanceState.getBoolean("computerOn");
            textView.setText(savedInstanceState.getString("textView"));
            boardStatus = savedInstanceState.getIntArray("BoardState");
            for(int i = 0 ; i < 9 ; i++) {
                if(boardStatus[i] == 1) {
                    String id_str = "circleImage" + Integer.toString(i + 1);
                    int imageId = getResources().getIdentifier(id_str, "id", getPackageName());
                    ImageView imageView = findViewById(imageId);
                    imageView.setVisibility(View.VISIBLE);
                } else if(boardStatus[i] == 2) {
                    String id_str = "crossImage" + Integer.toString(i + 1);
                    int imageId = getResources().getIdentifier(id_str, "id", getPackageName());
                    ImageView imageView = findViewById(imageId);
                    imageView.setVisibility(View.VISIBLE);
                }
            }
        }

        boardImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    double relativeX = event.getX() / boardImage.getWidth();
                    double relativeY = event.getY() / boardImage.getHeight();
                    //textView.setText(relativeX + " " + relativeY);
                    int positionNum = checkImagePosition(relativeX,relativeY);
                    if(positionNum == -1)
                        return true;
                    playerTurn(positionNum);
                } else if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.performClick();
                }
                return true;
            }
        });
    }
    public int checkImagePosition(double relX, double relY) {
        if( relY < 0.4)
            return -1;
        int x;
        int y;
        if( relY < 0.58)
            y = 0;
        else if( relY > 0.79)
            y = 2;
        else
            y = 1;

        if( relX < 0.39)
            x = 0;
        else if( relX > 0.67)
            x = 2;
        else
            x = 1;
        return y*3 + x;
    }

    public boolean checkGameEnd() {
        int circleCount;
        int crossCount;
        for(int j = 0 ; j < 9 ; j+=3) {
            circleCount = 0;
            crossCount = 0;
            for (int i = 0; i < 3; i++) {
                if(boardStatus[j+i] == 1) {
                    circleCount++;
                } else if(boardStatus[j+i] == 2) {
                    crossCount++;
                }
            }
            if(circleCount == 3) {
                winStatus(true);
                return true;
            } else if(crossCount == 3){
                winStatus(false);
                return true;
            }
        }
        for(int i = 0 ; i < 3 ; i++) {
            circleCount = 0;
            crossCount = 0;
            for (int j = 0; j < 9; j+=3) {
                if (boardStatus[j+i] == 1) {
                    circleCount++;
                } else if (boardStatus[j+i] == 2) {
                    crossCount++;
                }
            }
            if(circleCount == 3) {
                winStatus(true);
                return true;
            } else if(crossCount == 3){
                winStatus(false);
                return true;
            }
        }
        if(boardStatus[2] == 1 && boardStatus[4] == 1 && boardStatus[6] == 1) {
            winStatus(true);
            return true;
        } else if (boardStatus[2] == 2 && boardStatus[4] == 2 && boardStatus[6] == 2) {
            winStatus(false);
            return true;
        } else if(boardStatus[0] == 1 && boardStatus[4] == 1 && boardStatus[8] == 1){
            winStatus(true);
            return true;
        } else if(boardStatus[0] == 2 && boardStatus[4] == 2 && boardStatus[8] == 2) {
            return true;
        } else if(drawStatus()) {
            return true;
        }
        return false;
    }

    public void winStatus(boolean circleWon) {
        if(circleWon){
            gameWinSoundMP.start();
            Toast.makeText(getApplicationContext(), R.string.game_won_circle, Toast.LENGTH_LONG).show();
            textView.setText(R.string.game_won_circle);
        } else {
            gameWinSoundMP.start();
            Toast.makeText(getApplicationContext(), R.string.game_won_cross, Toast.LENGTH_LONG).show();
            textView.setText(R.string.game_won_cross);
        }
    }

    public boolean drawStatus(){
        for(int i = 0 ; i < 9 ; i++) {
            if(boardStatus[i] == 0)
                return false;
        }
        Toast.makeText(getApplicationContext(), R.string.game_draw, Toast.LENGTH_LONG).show();
        textView.setText(R.string.game_draw);
        tieWinSoundMp.start();
        return true;
    }

    public void playerTurn(int num) {
        if(firstPlayerTurn) {
            if (boardStatus[num] == 0) {
                drawCircle(num);
                firstPlayerTurn = false; //end of turn
                if (checkGameEnd())
                    boardImage.setEnabled(false);
                if (computerPlayer)
                    computerTurn();
            } else {
                wrongSoundMP.start();
            }
        } else  {
            if (boardStatus[num] == 0) {
                drawCross(num);
                firstPlayerTurn = true;
                if (checkGameEnd())
                    boardImage.setEnabled(false);
                if (computerPlayer)
                    computerTurn();
            } else {
                wrongSoundMP.start();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main,menu);
        return true;
    }
    public void drawCircle(int position) {
        String id_str = "circleImage" + Integer.toString(position+1);
        int imageId = getResources().getIdentifier(id_str, "id", getPackageName());
        findViewById(imageId).setVisibility(View.VISIBLE);
        boardStatus[position] = 1;
        if(checkGameEnd())
            boardImage.setEnabled(false);
    }

    public void drawCross(int position) {
        String id_str = "crossImage" + Integer.toString(position+1);
        int imageId = getResources().getIdentifier(id_str, "id", getPackageName());
        findViewById(imageId).setVisibility(View.VISIBLE);
        boardStatus[position] = 2;
        if (checkGameEnd())
            boardImage.setEnabled(false);
    }
    public int scanHorizontal(int key){
        int count = 0;
        int toFill = -1;

        for (int i = 0; i < 9; i++) {
            if(i == 3 || i == 6) {
                count = 0;
                toFill = -1;
                System.out.println(i + " count" + count);
            }
            System.out.println(i + " " + boardStatus[i]);
            if(boardStatus[i] == key){
                count++;
                System.out.println(i + " count" + count);
            }

          if(boardStatus[i] == 0) {
              System.out.println(i);
              toFill = i;
          }
          if((i%3== 2) && count == 2 && toFill != -1) {
                  System.out.println(toFill);
                  return toFill;

          }
        }
        if(count == 2){
            System.out.println(toFill);
            return toFill;
        }
        return -1;
    }

    public int scanVertical(int key){
        int count = 0;
        int toFill = -1;

        for(int j = 0; j < 3 ;j++) {
            count = 0;
            toFill = -1;
            for (int i = j; i <= 8; i = i + 3) {
                System.out.println("i:"+i);
                if (boardStatus[i] == key) {
                    count++;
                }
                if (boardStatus[i] == 0) {
                    toFill = i;
                }
                System.out.println("toFill" + toFill + " count" +count );
                if(count == 2 && toFill != -1) {
                    return toFill;
                }
            }
        }
        if (count == 2) {
            System.out.println(toFill);
            return toFill;
        }
        return -1;
    }

    public int scanCrossOne(int key) {
        int count = 0;
        int toFill = -1;
        if(boardStatus[2] == key){
            count++;
        }  else if (boardStatus[2] == 0 ){
            toFill = 2;
        }
        if(boardStatus[4] == key){
            count++;
        }  else if (boardStatus[4] == 0 ){
            toFill = 4;
        }
        if(boardStatus[6] == key){
            count++;
        }  else if (boardStatus[6] == 0 ){
            toFill = 6;
        }

        if(count == 2){
            return toFill;
        }
        return -1;
    }

    public int scanCrossTwo(int key) {
        int count = 0;
        int toFill = -1;
        if(boardStatus[0] == key){
            count++;
        } else if (boardStatus[2] == 0 ){
            toFill = 2;
        }
        if(boardStatus[4] == key){
            count++;
        } else if (boardStatus[4] == 0 ){
            toFill = 4;
        }
        if(boardStatus[8] == key){
            count++;
        } else if (boardStatus[8] == 0 ){
            toFill = 8;
        }
        if(count == 2){
            return toFill;
        }
        return -1;
    }

    public void computerTurn(){
        if(firstPlayerTurn) {
            /*take center*/
            if (boardStatus[4] == 0) {
                drawCircle(4);
                firstPlayerTurn = false;
                return;
            }
            int toFill;
            if ((toFill = scanHorizontal(2)) != -1) {
                drawCircle(toFill);
                firstPlayerTurn = false; //nastepny gracz nr 2
                if (checkGameEnd())
                    boardImage.setEnabled(false);
                return;
            }
            if ((toFill = scanVertical(2)) != -1) {
                drawCircle(toFill);
                firstPlayerTurn = false; //nastepny gracz nr 2
                if (checkGameEnd())
                    boardImage.setEnabled(false);
                return;
            }
            if ((toFill = scanCrossOne(2)) != -1) {
                drawCircle(toFill);
                firstPlayerTurn = false;
                if (checkGameEnd())
                    boardImage.setEnabled(false);
                return;
            }
            if ((toFill = scanCrossTwo(2)) != -1) {
                drawCircle(toFill);
                firstPlayerTurn = false; //nastepny gracz nr 2
                if (checkGameEnd())
                    boardImage.setEnabled(false);
                return;
            }
            if ((toFill = scanHorizontal(1)) != -1) {
                drawCircle(toFill);
                firstPlayerTurn = false; //nastepny gracz nr 2
                if (checkGameEnd())
                    boardImage.setEnabled(false);
                return;
            }
            if ((toFill = scanVertical(1)) != -1) {
                drawCircle(toFill);
                firstPlayerTurn = false; //nastepny gracz nr 2
                if (checkGameEnd())
                    boardImage.setEnabled(false);
                return;
            }
            if ((toFill = scanCrossOne(1)) != -1) {
                drawCircle(toFill);
                firstPlayerTurn = false; //nastepny gracz nr 2
                if (checkGameEnd())
                    boardImage.setEnabled(false);
                return;
            }
            if ((toFill = scanCrossTwo(1)) != -1) {
                drawCircle(toFill);
                firstPlayerTurn = false; //nastepny gracz nr 2
                if (checkGameEnd())
                    boardImage.setEnabled(false);
                return;
            }
            if( boardStatus[0] == 0 ) {
                drawCircle(0);
                firstPlayerTurn = false; //nastepny gracz nr 2
                if (checkGameEnd())
                    boardImage.setEnabled(false);
                return;
            }
            else if ( boardStatus[2] == 0 ) {
                drawCircle(2);
                firstPlayerTurn = false; //nastepny gracz nr 2
                if (checkGameEnd())
                    boardImage.setEnabled(false);
                return;
            }
            else if ( boardStatus[6] == 0 ) {
                drawCircle(6);
                firstPlayerTurn = false; //nastepny gracz nr 2
                if (checkGameEnd())
                    boardImage.setEnabled(false);
                return;
            }
            else if ( boardStatus[8] == 0 ) {
                drawCircle(8);
                if (checkGameEnd())
                    boardImage.setEnabled(false);
                return;
            }

            for (int i = 0; i < 9; i++) {
                if (boardStatus[i] == 0) {
                    drawCircle(i); firstPlayerTurn = false; //nastepny gracz nr 2
                    if (checkGameEnd())
                        boardImage.setEnabled(false);
                    return;
                }
            }
        } else {
            /*take center if available*/
            System.out.println("cross round");
            if (boardStatus[4] == 0) {
                drawCross(4);
                firstPlayerTurn = true;
                return;
            }
            System.out.println("scan horizontal");
            int toFill;
            if ((toFill = scanHorizontal(1)) != -1) {
                drawCross(toFill);
                firstPlayerTurn = true; //nastepny gracz nr 1
                if (checkGameEnd())
                    boardImage.setEnabled(false);
                return;
            }
            System.out.println("scan vertical");
            if ((toFill = scanVertical(1)) != -1) {
                drawCross(toFill);
                firstPlayerTurn = true; //nastepny gracz nr 1
                if (checkGameEnd())
                    boardImage.setEnabled(false);
                return;
            }
            System.out.println("scan cross one");
            if ((toFill = scanCrossOne(1)) != -1) {
                drawCross(toFill);
                firstPlayerTurn = true;
                if (checkGameEnd())
                    boardImage.setEnabled(false);
                return;
            }
            System.out.println("scan cross two");
            if ((toFill = scanCrossTwo(1)) != -1) {
                drawCircle(toFill);
                firstPlayerTurn = true; //nastepny gracz nr 2
                if (checkGameEnd())
                    boardImage.setEnabled(false);
                return;
            }
            System.out.println("kupsko");
            if ((toFill = scanHorizontal(2)) != -1) {
                drawCross(toFill);
                firstPlayerTurn = true; //nastepny gracz nr 1
                if (checkGameEnd())
                    boardImage.setEnabled(false);
                return;
            }
            if ((toFill = scanVertical(2)) != -1) {
                drawCross(toFill);
                firstPlayerTurn = true; //nastepny gracz nr 1
                if (checkGameEnd())
                    boardImage.setEnabled(false);
                return;
            }
            if ((toFill = scanCrossOne(2)) != -1) {
                drawCross(toFill);
                firstPlayerTurn = true; //nastepny gracz nr 1
                if (checkGameEnd())
                    boardImage.setEnabled(false);
                return;
            }
            if ((toFill = scanCrossTwo(2)) != -1) {
                drawCross(toFill);
                firstPlayerTurn = true; //nastepny gracz nr 1
                if (checkGameEnd())
                    boardImage.setEnabled(false);
                return;
            }
            if(boardStatus[0] == 0 ) {
                drawCross(0);
                firstPlayerTurn = true; //nastepny gracz nr 1
                if (checkGameEnd())
                    boardImage.setEnabled(false);
                return;
            }
            else if (boardStatus[2] == 0 ) {
                drawCross(2);
                firstPlayerTurn = true; //nastepny gracz nr 1
                if (checkGameEnd())
                    boardImage.setEnabled(false);
                return;
            }
            else if(boardStatus[6] == 0 ) {
                drawCross(6);
                firstPlayerTurn = true; //nastepny gracz nr 1
                if (checkGameEnd())
                    boardImage.setEnabled(false);
                return;
            }
            else if ( boardStatus[8] == 0 ) {
                drawCross(8);
                firstPlayerTurn = true; //nastepny gracz nr 1
                if (checkGameEnd())
                    boardImage.setEnabled(false);
                return;
            }

            for(int i = 0 ; i < 9 ; i++) {
                if(boardStatus[i] == 0) {
                    drawCross(i);
                    firstPlayerTurn = true; //nastepny gracz nr 1
                    if (checkGameEnd())
                        boardImage.setEnabled(false);
                    return;
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int res_id = item.getItemId();
        if(res_id == R.id.action_computer_player && !item.isChecked()) {
            Toast.makeText(getApplicationContext(), R.string.computer_on, Toast.LENGTH_LONG).show();
            item.setChecked(true);
            computerPlayer = true;
            computerTurn();
        } else if ( res_id == R.id.action_computer_player && item.isChecked()) {
            Toast.makeText(getApplicationContext(), R.string.computer_off, Toast.LENGTH_LONG).show();
            item.setChecked(false);
            computerPlayer = false;
        } else if ( res_id == R.id.action_new_game ) {
            Toast.makeText(getApplicationContext(), R.string.new_game, Toast.LENGTH_LONG).show();
            newGame();
        }
        return true;
    }

    public void newGame() {
        firstPlayerTurn = false;
        for(int i = 0 ; i < 9 ; i++){
            boardStatus[i] = 0;
        }
        finish();
        startActivity(getIntent());
    }
}