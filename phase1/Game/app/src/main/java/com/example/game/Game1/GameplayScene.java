package com.example.game.Game1;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.ArrayList;

/**
 *  GameplayScene. Handles drawing & updating the objects, controls, and game over
 */
public class GameplayScene implements Scene {

    /**
     * Instance Variables
     */
    private RectPlayer player; // player object
    private Point playerPoint; // player coordinates
    private ObstacleManager obstacleManager; // obstacle spawner
    private boolean movingPlayer = false; // check whether player is moving
    private boolean gameOver = false; // check whether the game is over
    private int score; // Score for the game
    private int lives; // Lives for the game
    private OrientationData orientationData; // orientation data
    private long frameTime; // time frame

    /**
     * Constructor for GameplayScene. Instansiates player, playerPoint, obstacles, and lives.
     */
    GameplayScene() {
        player = new RectPlayer(new Rect(100, 100, 200, 200), Color.rgb(255, 0, 0));
        playerPoint = new Point(Constants.SCREEN_WIDTH/2, 3 * Constants.SCREEN_HEIGHT/4);
        player.update(playerPoint);
        obstacleManager = new ObstacleManager(1000, 75, Color.BLACK);
        lives = 3;
        orientationData = new OrientationData();
        frameTime = System.currentTimeMillis();
    }

    @Override
    public void terminate() {
        SceneManager.ACTIVE_SCENE = 0;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        player.draw(canvas); // draw player
        obstacleManager.draw(canvas); // draw obstacles
        Paint paint = new Paint();
        paint.setTextSize(100);
        paint.setColor(Color.MAGENTA);
        // Draw score
        canvas.drawText("" + score, 50, 50 + paint.descent() - paint.ascent(), paint);
        // Draw lives
        paint.setColor(Color.GREEN);
        canvas.drawText("Lives: " + lives, Constants.SCREEN_WIDTH/2, 50 + paint.descent() - paint.ascent(), paint);
    }

    @Override
    public void update() {
        if (!gameOver) {
            if (frameTime < Constants.INIT_TIME) {
                frameTime = Constants.INIT_TIME;
            }
            int elapsedTime = (int) (System.currentTimeMillis() - frameTime);
            frameTime = System.currentTimeMillis();
            if (orientationData.getOrientation() != null && orientationData.getStartOrientation() != null) {
                float pitch = orientationData.getOrientation()[1] - orientationData.getStartOrientation()[1];
                float roll = orientationData.getOrientation()[2] - orientationData.getStartOrientation()[2];
                float xSpeed = 2 * roll * Constants.SCREEN_WIDTH/1000f;
                float ySpeed = pitch * Constants.SCREEN_HEIGHT/1000f;
                if (Math.abs(xSpeed * elapsedTime) > 5) {
                    playerPoint.x += xSpeed * elapsedTime;
                }
                if (Math.abs(ySpeed * elapsedTime) > 5) {
                    playerPoint.y += ySpeed * elapsedTime;
                }
            }
            // Keep player within boundaries
            if (playerPoint.x < 0) {
                playerPoint.x = 0;
            }
            else if (playerPoint.x > Constants.SCREEN_WIDTH) {
                playerPoint.x = Constants.SCREEN_WIDTH;
            }
            if (playerPoint.y < 0) {
                playerPoint.y = 0;
            }
            // If player falls off screen lose a life
            else if (playerPoint.y > Constants.SCREEN_HEIGHT) {
                gameOver = true;
                lives --;
                // If player has no lives go to GameOverActivity
                if (lives == 0) {
                    ((BallJumperActivity) Constants.CURRENT_CONTEXT).gameOver(score);
                }
                else {
                    reset();
                    orientationData.newGame();
                }
            }

            player.update(playerPoint);
            ArrayList<Obstacle> obstacles = obstacleManager.getObstacles();
            // If obstacle goes off screen remove it, then add to our score
            if (obstacles.get(obstacles.size() - 1).getRectangle().bottom <= 0) {
                obstacles.remove(obstacles.size() - 1);
                score++;
            }
            obstacleManager.update();
            // When player gets hit subtract lives
            if (obstacleManager.playerCollide(player)) {
                gameOver = true;
                lives --;
                // If player has no lives go to GameOverActivity
                if (lives == 0) {
                    ((BallJumperActivity) Constants.CURRENT_CONTEXT).gameOver(score);
                }
                else {
                    reset();
                    orientationData.newGame();
                }
            }
        }
    }

    @Override
    public void receiveTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!gameOver && player.getRectangle().contains((int) event.getX(), (int) event.getY())) {
                    movingPlayer = true;
                }
            case MotionEvent.ACTION_MOVE:
                if (!gameOver && movingPlayer) {
                    playerPoint.set((int) event.getX(), (int) event.getY());
                }
                break;
            case MotionEvent.ACTION_UP:
                movingPlayer = false;
                break;
        }
    }

    /**
     * Reset whenever player dies
     */
    private void reset() {
        playerPoint = new Point(Constants.SCREEN_WIDTH/2, 3 * Constants.SCREEN_HEIGHT/4);
        player.update(playerPoint);
        obstacleManager = new ObstacleManager(1000, 75, Color.BLACK);
        movingPlayer = false;
        gameOver = false;
    }
}