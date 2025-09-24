package org.example.pong;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GamePanel extends Application {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 600;

    private Ball ball;
    private Paddle player;
    private ComputerPaddle computer;
    private UserInput keyInput;

    public GamePanel(){
        this.ball = new Ball(0,0, 15);
        this.player = new Paddle(15,150,50,ball);
        this.computer = new ComputerPaddle(15,150,GamePanel.WIDTH-65,ball);
        this.keyInput = new UserInput(player, ball);
    }

    public static void main(String[] args) {
        launch(GamePanel.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = new Pane();
        Text pointsLeft = new Text(10,30, "Player-Score: 0");
        pointsLeft.setStyle("-fx-fill: white;-fx-background-color: black;-fx-font-size: 20px;-fx-font-family: Consolas;-fx-font-weight: bold");
        pointsLeft.setVisible(false);

        Text pointsRight = new Text(GamePanel.WIDTH-200,30,"Computer-Score: 0");

        pointsRight.setStyle("-fx-fill: white;-fx-background-color: black;-fx-font-size: 20px;-fx-font-family: Consolas;-fx-font-weight: bold");
        pointsRight.setVisible(false);

        pane.setPrefSize(WIDTH,HEIGHT);
        pane.setStyle("-fx-fill: black;-fx-background-color: black;");

        Button startButton = new Button("Start Game");
        startButton.setLayoutX(GamePanel.WIDTH/2 -90);
        startButton.setLayoutY(GamePanel.HEIGHT/2);
        startButton.getStyleClass().add("pongButton");

        String title = "PONG GAME";

        // Colors for each letter of PONG GAME
        Color[] colors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE, Color.MAGENTA};

        HBox hbox = new HBox(5); // 5px spacing between letters

        hbox.setStyle("-fx-background-color: black; -fx-alignment: center;"); // black background

        for (int i = 0; i < title.length(); i++) {
            char c = title.charAt(i);

            Text letter = new Text(String.valueOf(c));
            letter.setFont(Font.font("Consolas", 60));
            letter.setFill(colors[i % colors.length]);

            hbox.getChildren().add(letter);
        }

        hbox.setLayoutX(GamePanel.WIDTH/2 -168);
        hbox.setLayoutY(GamePanel.HEIGHT/3 -100);

        Label gameDesc = new Label("First to reach 12 points wins");
        gameDesc.setLayoutX(GamePanel.WIDTH/2 -220);
        gameDesc.setLayoutY(GamePanel.HEIGHT/2 -100);
        gameDesc.getStyleClass().add("gameDescription");

        Label gameOver = new Label("Game Over");
        gameOver.setLayoutX(GamePanel.WIDTH/2 -120);
        gameOver.setLayoutY(GamePanel.HEIGHT/5);
        gameOver.setVisible(false);
        gameOver.getStyleClass().add("gameOverLabel");

        Button retry = new Button("Retry");
        retry.setLayoutX(GamePanel.WIDTH/2 -53);
        retry.setLayoutY(GamePanel.HEIGHT/2);
        retry.setVisible(false);
        retry.getStyleClass().add("pongButton");

        Label winMessage = new Label("");
        winMessage.setLayoutX(GamePanel.WIDTH/2 -100);
        winMessage.setLayoutY(GamePanel.HEIGHT/3 );
        winMessage.getStyleClass().add("gameDescription");
        winMessage.setVisible(false);

        Circle newBall = ball.getBall();
        newBall.setVisible(false);

        Rectangle newPaddle = player.getPaddle();
        Rectangle newComputer = computer.getPaddle();
        newPaddle.setVisible(false);
        newComputer.setVisible(false);

        Line line = new Line(GamePanel.WIDTH/2,0,GamePanel.WIDTH/2, GamePanel.HEIGHT );
        line.setStroke(Color.GREEN);
        line.setStrokeWidth(10);
        line.setVisible(false);
        line.getStrokeDashArray().addAll(35.0, 22.0);

        Circle circle = new Circle(GamePanel.WIDTH/2,GamePanel.HEIGHT/2,ball.getBall().getRadius());
        circle.setFill(Color.GREEN);         // white fill
        circle.setStroke(Color.WHITE);       // green border
        circle.setStrokeWidth(3);
        circle.setVisible(false);

        pane.getChildren().addAll(hbox,line,circle,newPaddle,newComputer,newBall,startButton,gameDesc,pointsLeft,pointsRight,gameOver,retry,winMessage);

        Scene scene = new Scene(pane);

        scene.setOnKeyPressed(keyEvent -> {
            keyInput.add(keyEvent.getCode(), Boolean.TRUE);
        });

        scene.setOnKeyReleased(keyEvent ->{
            keyInput.add(keyEvent.getCode(),Boolean.FALSE);
        });

        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                keyInput.checkKeyInput();
                ball.move();
                computer.move();
                pointsRight.setText("Computer-Score: " + ball.getCountBorderRight());
                pointsLeft.setText("Player-Score: " + ball.getCountBorderLeft());

                if(player.collide(ball)){
                    player.collideAndReflect();
                }
                if(computer.collide(ball)){
                    computer.collideAndReflect();
                }

                if(ball.getMovement().getX() == 0 && ball.getMovement().getY() == 0){
                    player.getPaddle().setTranslateX(50);
                    player.getPaddle().setTranslateY(GamePanel.HEIGHT/2 - player.getPaddle().getHeight()/2);

                    computer.getPaddle().setTranslateX(GamePanel.WIDTH-65);
                    computer.getPaddle().setTranslateY(GamePanel.HEIGHT/2 - computer.getPaddle().getHeight()/2);
                    keyInput.startBall();
                }

                if(ball.getCountBorderLeft() == 12 || ball.getCountBorderRight() == 12 ){
                    gameOver.setVisible(true);
                    retry.setVisible(true);
                    if(ball.getCountBorderLeft() == 12){
                        winMessage.setText("Player wins");
                    }else{
                        winMessage.setText("Computer wins");
                    }
                    winMessage.setVisible(true);
                    stop();
                }

            }
        };

        startButton.setOnAction(actionEvent -> {
            startButton.setVisible(false);
            gameDesc.setVisible(false);
            hbox.setVisible(false);
            line.setVisible(true);
            circle.setVisible(true);
            newPaddle.setVisible(true);
            newComputer.setVisible(true);
            newBall.setVisible(true);
            pointsLeft.setVisible(true);
            pointsRight.setVisible(true);
            gameLoop.start();

        });

        retry.setOnAction(actionEvent -> {
            gameOver.setVisible(false);
            retry.setVisible(false);
            winMessage.setVisible(false);
            ball.setCountBorderLeft(0);
            ball.setCountBorderRight(0);
            gameLoop.start();

        });

        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        Image image = new Image(getClass().getResource("/images/pong.png").toExternalForm());

        stage.setTitle("Pong-Game");
        stage.getIcons().add(image);
        stage.setResizable(false);
        stage.setFullScreen(false);
        stage.setScene(scene);
        stage.show();
    }
}
