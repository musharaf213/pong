package org.example.pong;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Paddle {
    private Rectangle paddle;
    private Ball ball;

    public Paddle(int width, int height, int xPos, Ball ball){
        this.ball = ball;
        createPaddle(width, height);
        initPos(xPos,height);
    }

    public void createPaddle(int width, int height){
        this.paddle = new Rectangle(width,height);
        paddle.setStyle("-fx-fill: blue; -fx-stroke: black; -fx-stroke-width: 2;");

    }
    public void initPos(int xPos, int height){
        paddle.setTranslateX(xPos);
        paddle.setTranslateY(GamePanel.HEIGHT/2 -height/2);

    }

    public Rectangle getPaddle() {
        return paddle;
    }

    public void setPaddle(Rectangle paddle) {
        this.paddle = paddle;
    }

    public void moveUp() {
        paddle.setTranslateY(Math.max(0, paddle.getTranslateY() - 5));
    }

    public void moveDown() {
        paddle.setTranslateY(Math.min(GamePanel.HEIGHT - paddle.getHeight(), paddle.getTranslateY() + 5));
    }

    public Ball getBallInstance() {
        return ball;
    }

    public void setBallInstance(Ball ball) {
        this.ball = ball;
    }

    public boolean collide(Ball ball){
        Shape collisionArea = Shape.intersect(paddle, ball.getBall());
        return collisionArea.getBoundsInLocal().getWidth() != -1;
    }

    public void collideAndReflect() {
        Rectangle paddle = this.paddle;
        Circle ballShape = ball.getBall();
        Point2D movement = ball.getMovement();

        // Calculate next ball position
        double ballNextX = ballShape.getTranslateX() + movement.getX();
        double ballNextY = ballShape.getTranslateY() + movement.getY();
        double ballRadius = ballShape.getRadius();

        // Paddle boundaries
        double paddleLeft = paddle.getTranslateX();
        double paddleRight = paddle.getTranslateX() + paddle.getWidth();
        double paddleTop = paddle.getTranslateY();
        double paddleBottom = paddle.getTranslateY() + paddle.getHeight();

        Bounds paddleBounds = paddle.getBoundsInParent();
        Bounds ballBounds = ballShape.getBoundsInParent();

        double ballCenterY = ballBounds.getMinY() + ballBounds.getHeight() / 2;

        // Check collision
        if (ballNextX + ballRadius > paddleLeft && ballNextX - ballRadius < paddleRight &&
                ballNextY + ballRadius > paddleTop && ballNextY - ballRadius < paddleBottom) {

            // Horizontal reflection
            movement = new Point2D( -movement.getX(), movement.getY());

            // add spin based on vertical hit position
            double paddleCenterY = paddleBounds.getMinY() + paddle.getHeight() / 2;
            double offset = (ballCenterY - paddleCenterY) / (paddle.getHeight() / 2); // -1..1

            double speed = movement.magnitude(); //  speed constant
            double newDx = movement.getX();
            double newDy = offset * speed;

            // Normalize to original speed
            movement = new Point2D(newDx, newDy).normalize().multiply(speed);

            // avoid sticking
            if (ballNextX < paddleLeft)
                ballShape.setTranslateX(paddleLeft - ballRadius);
            else
                ballShape.setTranslateX(paddleRight + ballRadius);
        }
        ball.setMovement(movement);
    }

}
