package com.jbalicki.snake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.jbalicki.snake.Enums.Direction;
import com.jbalicki.snake.Enums.GameState;
import com.jbalicki.snake.Views.Snakeview;
import com.jbalicki.snake.silnik.GameEngine;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener{

    private GameEngine gameEngine;
    private Snakeview snakeView;
    private final Handler handler = new Handler();
    private final long updateDelay = 500;

    private float prevX,prevY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameEngine = new GameEngine();
        gameEngine.initGame();

        snakeView = (Snakeview)findViewById(R.id.SnakeView);
        snakeView.setOnTouchListener(this);

        startUpdateHandler();
    }

    private void startUpdateHandler(){
        handler.postDelayed(()->{
            gameEngine.Update();
            if(gameEngine.getCurrentGameState() == GameState.Running) {
                startUpdateHandler();
            }
            if(gameEngine.getCurrentGameState() == GameState.Lost) {
                OnGameLost();
            }
            snakeView.setSnakeViewMap(gameEngine.getMap());
            snakeView.invalidate();
        }, updateDelay);
    }
    private void OnGameLost(){
        Intent myIntent = new Intent(getBaseContext(), Scoreboard.class);
        startActivity(myIntent);;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                prevX = event.getX();
                prevY = event.getY();

                break;
            case MotionEvent.ACTION_UP:

                float newX = event.getX();
                float newY = event.getY();

//Określenie, gdzie się przesunęliśmy

                if (Math.abs(newX - prevX) > Math.abs(newY - prevY)){
//kierunek lewo - prawo
                if (newX > prevX){
//prawo
                    gameEngine.UpdateDirection(Direction.East);
                }else{
//lewo
                    gameEngine.UpdateDirection(Direction.West);
                }
            }else{
//kierunek góra - dół
                if (newY > prevY){
//góra
                    gameEngine.UpdateDirection(Direction.South);
                }
                else{
//dół
                    gameEngine.UpdateDirection(Direction.North);
                }
            }
        }
        return true;

    }
}