package org.example.pong;

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

        // Calculating the difference between paddle's position and ball's position
        double yPaddlePos = getPaddle().getTranslateY();
        double difference = yBallPos - yPaddlePos;

        // How much the paddle should move towards the ball/ speed adjustment
        double movementSpeed = 3;

        // randomness
        double randomFactor = rand.nextInt(3) - 1; // between -1 and 1

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
