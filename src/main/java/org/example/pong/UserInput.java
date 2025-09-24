package org.example.pong;

import javafx.scene.input.KeyCode;

import java.util.HashMap;
import java.util.Map;

public class UserInput {
    private Map<KeyCode,Boolean> pressedKeys;
    private Paddle paddle;
    private Ball ball;

    public UserInput(Paddle paddle, Ball ball){
        this.pressedKeys = new HashMap<>();
        this.paddle = paddle;
        this.ball = ball;
    }

    public void add(KeyCode keyCode, Boolean pressed){
        this.pressedKeys.put(keyCode,pressed);
    }

    public void get(KeyCode keyCode){
        this.pressedKeys.get(keyCode);
    }

    public void checkKeyInput(){
        if(pressedKeys.getOrDefault(KeyCode.W, false)){
            paddle.moveUp();
        }

        if(pressedKeys.getOrDefault(KeyCode.S, false)){
            paddle.moveDown();
        }

    }

    public void startBall(){
        if(pressedKeys.getOrDefault(KeyCode.ENTER,false)){
            this.ball.accelerate();
        }
    }
}
