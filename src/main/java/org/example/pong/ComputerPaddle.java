package org.example.pong;

import javafx.scene.shape.Rectangle;

import java.util.Random;

public class ComputerPaddle extends Paddle{
    private Random rand;

    public ComputerPaddle(int width, int height, int xPos, Ball ball) {
        super(width, height, xPos, ball);
        this.rand = new Random();
        changeColor();
    }

    public void changeColor(){
        getPaddle().setStyle("-fx-fill: red; -fx-stroke: black; -fx-stroke-width: 2;");
    }

    public void move() {
        double yBallPos = getBallInstance().getBall().getTranslateY();
        double yBallMovement = getBallInstance().getMovement().getY();

        // Calculate the difference between paddle's position and ball's position
        double yPaddlePos = getPaddle().getTranslateY();
        double difference = yBallPos - yPaddlePos;

        // How much the paddle should move towards the ball (adjust speed here)
        double movementSpeed = 3; // Higher values make the paddle follow the ball faster

        // Adding some randomness for unpredictability
        double randomFactor = rand.nextInt(3) - 1; // Random value between -1 and 1

        // If the ball is moving downward (positive y movement), move the paddle down
        if (yBallMovement > 0) {
            if (yPaddlePos < yBallPos && yPaddlePos < GamePanel.HEIGHT - getPaddle().getHeight()) {
                getPaddle().setTranslateY(yPaddlePos + movementSpeed + randomFactor);
            }
        }
        // If the ball is moving upward (negative y movement), move the paddle up
        if (yBallMovement < 0) {
            if (yPaddlePos > yBallPos && yPaddlePos > 0) {
                getPaddle().setTranslateY(yPaddlePos - movementSpeed - randomFactor);
            }
        }
    }

}
