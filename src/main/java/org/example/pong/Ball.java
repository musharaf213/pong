package org.example.pong;

import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;
import java.util.Random;

public class Ball {

    private double posX;
    private double posY;
    private double radius;
    private Circle ball;
    private Point2D movement;
    private Random rand;
    private int countBorderLeft;
    private int countBorderRight;
    private boolean playerTurn;
    private boolean randPlayer;

    public Ball(double posX, double posY, double radius){
        this.posX = posX;
        this.posY = posY;
        this.radius = radius;
        this.rand = new Random();
        this.countBorderLeft = 0;
        this.countBorderRight = 0;
        createBall();
        randPlayer = rand.nextBoolean();
        this.playerTurn = randPlayer;

        this.movement = new Point2D(0,0);
    }

    public void createBall(){
        this.ball = new Circle(posX,posY,radius);
        ball.setStyle("-fx-fill: green; -fx-stroke: black; -fx-stroke-width: 2;");
        this.ball.setTranslateX(GamePanel.WIDTH/2);
        this.ball.setTranslateY(GamePanel.HEIGHT/2);
    }

    public Circle getBall() {
        return ball;
    }

    public void setBall(Circle ball) {
        this.ball = ball;
    }

    public void move(){

        ball.setTranslateX(ball.getTranslateX() + this.movement.getX());
        ball.setTranslateY(ball.getTranslateY() + this.movement.getY());

        // screen wrap
        if (ball.getTranslateX() -ball.getRadius() < 0){
            countBorderRight++;
            this.ball.setTranslateX(GamePanel.WIDTH/2);
            this.ball.setTranslateY(GamePanel.HEIGHT/2);
            this.movement = new Point2D(0, 0);
        }

        if(ball.getTranslateX() +ball.getRadius() > GamePanel.WIDTH){
            countBorderLeft++;
            this.ball.setTranslateX(GamePanel.WIDTH/2);
            this.ball.setTranslateY(GamePanel.HEIGHT/2);
            this.movement = new Point2D(0, 0);

        }
        if(ball.getTranslateY() -ball.getRadius()  < 0){
            ball.setTranslateY(radius); // prevent sticking
            this.movement = new Point2D(this.movement.getX(), this.movement.getY() * -1);
        }

        if (ball.getTranslateY() +ball.getRadius() > GamePanel.HEIGHT){
            ball.setTranslateY(GamePanel.HEIGHT - radius);
            this.movement = new Point2D(this.movement.getX(), this.movement.getY() * -1);
        }

    }

    public void accelerate(){
        int rotate;

        if (!playerTurn) {
            // Send ball to the right: 300–360 Grad or 0–60
            do {
                rotate = rand.nextInt(360);
            } while (!(rotate <= 60 || rotate >= 300));
            playerTurn = true;
        } else {
            // Send ball to the left: 120–240 Grad
            do {
                rotate = rand.nextInt(360);
            } while (!(rotate >= 120 && rotate <= 240));
            playerTurn = false;
        }

        this.ball.setRotate(rotate);
        double changeX = Math.cos(Math.toRadians(this.ball.getRotate()));
        double changeY = Math.sin(Math.toRadians(this.ball.getRotate()));

        changeX *= 5;
        changeY *= 5;

        this.movement = this.movement.add(changeX,changeY);
    }

    public Point2D getMovement() {
        return movement;
    }

    public void setMovement(Point2D movement) {
        this.movement = movement;
    }

    public int getCountBorderLeft() {
        return countBorderLeft;
    }

    public void setCountBorderLeft(int countBorderLeft) {
        this.countBorderLeft = countBorderLeft;
    }

    public int getCountBorderRight() {
        return countBorderRight;
    }

    public void setCountBorderRight(int countBorderRight) {
        this.countBorderRight = countBorderRight;
    }
}


