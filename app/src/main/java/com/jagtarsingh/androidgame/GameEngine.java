package com.jagtarsingh.androidgame;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.constraint.solver.widgets.Rectangle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

public class GameEngine extends SurfaceView implements Runnable {

    // -----------------------------------
    // ## ANDROID DEBUG VARIABLES
    // -----------------------------------

    // Android debug variables
    final static String TAG="PONG-GAME";

    // -----------------------------------
    // ## SCREEN & DRAWING SETUP VARIABLES
    // -----------------------------------

    // screen size
    int screenHeight;
    int screenWidth;

    // game state
    boolean gameIsRunning;

    // threading
    Thread gameThread;


    // drawing variables
    SurfaceHolder holder;
    Canvas canvas;
    Paint paintbrush;



    // -----------------------------------
    // ## GAME SPECIFIC VARIABLES
    // -----------------------------------

    // ----------------------------
    // ## SPRITES
    // ----------------------------
        Bitmap player;
        Bitmap enemy;
        Rect playerHitBox;
        Rect enemyHitBox;
        Point playerPos;
        Point enemyPos;
    // ----------------------------
    // ## GAME STATS - number of lives, score, etc
    // ----------------------------


    public GameEngine(Context context, int w, int h) {
        super(context);


        this.holder = this.getHolder();
        this.paintbrush = new Paint();

        this.screenWidth = w;
        this.screenHeight = h;


        this.printScreenInfo();

        // @TODO: Add your sprites to this section
        // added player image as bitmap
        this.player = BitmapFactory.decodeResource(context.getResources(), R.drawable.player_ship);
        //initial player position
        playerPos = new Point();
        playerPos.x = 100;
        playerPos.y = 120;

        // added enemy image as bitmap
        this.enemy = BitmapFactory.decodeResource(context.getResources(), R.drawable.alien_ship1);
        //initial enemy position
        enemyPos = new Point();
        enemyPos.x = this.screenWidth - 500;
        enemyPos.y = 120;

        //initial player hitbox position
        playerHitBox = new Rect(
                playerPos.x,
                playerPos.y,
                playerPos.x + player.getWidth(),
                playerPos.y + player.getHeight());

        //initial enemy hitbox position
        enemyHitBox = new Rect(
                enemyPos.x,
                enemyPos.y,
                enemyPos.x + enemy.getWidth(),
                enemyPos.y + enemy.getHeight());


        // @TODO: Any other game setup stuff goes here


    }

    // ------------------------------
    // HELPER FUNCTIONS
    // ------------------------------

    // This funciton prints the screen height & width to the screen.
    private void printScreenInfo() {

        Log.d(TAG, "Screen (w, h) = " + this.screenWidth + "," + this.screenHeight);
    }


    // ------------------------------
    // GAME STATE FUNCTIONS (run, stop, start)
    // ------------------------------
    @Override
    public void run() {
        while (gameIsRunning == true) {
            this.updatePositions();
            this.redrawSprites();
            this.setFPS();
        }
    }


    public void pauseGame() {
        gameIsRunning = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            // Error
        }
    }

    public void startGame() {
        gameIsRunning = true;
        gameThread = new Thread(this);
        gameThread.start();
    }


    // ------------------------------
    // GAME ENGINE FUNCTIONS
    // - update, draw, setFPS
    // ------------------------------

    // 1. Tell Android the (x,y) positions of your sprites
    public void updatePositions() {
        // @TODO: Update the position of the sprites

        // @TODO: Collision detection code

    }

    // 2. Tell Android to DRAW the sprites at their positions
    public void redrawSprites() {
        if (this.holder.getSurface().isValid()) {
            this.canvas = this.holder.lockCanvas();

            //----------------
            // Put all your drawing code in this section

            // configure the drawing tools
            this.canvas.drawColor(Color.argb(255,255,255,255));
            paintbrush.setColor(Color.WHITE);


            //@TODO: Draw the sprites (rectangle, circle, etc)
                //drawing the player ship
                canvas.drawBitmap(player,100,120,paintbrush);

                //changing the paintbrush color so hitbox can be visible
                paintbrush.setColor(Color.BLUE);
                paintbrush.setStyle(Paint.Style.STROKE);
                paintbrush.setStrokeWidth(5);

                //drawing the player hitbox
                canvas.drawRect(this.playerHitBox.left,
                        this.playerHitBox.top,
                        this.playerHitBox.right,
                        this.playerHitBox.bottom,paintbrush);

                //drawing the enemy ship
                canvas.drawBitmap(enemy,enemyPos.x,enemyPos.y, paintbrush);

            //drawing the enemy hitbox
            canvas.drawRect(this.enemyHitBox.left,
                    this.enemyHitBox.top,
                    this.enemyHitBox.right,
                    this.enemyHitBox.bottom,paintbrush);




            //@TODO: Draw game statistics (lives, score, etc)
            paintbrush.setTextSize(60);
            canvas.drawText("Score: 25", 20, 100, paintbrush);

            //----------------
            this.holder.unlockCanvasAndPost(canvas);
        }
    }

    // Sets the frame rate of the game
    public void setFPS() {
        try {
            gameThread.sleep(50);
        }
        catch (Exception e) {

        }
    }

    // ------------------------------
    // USER INPUT FUNCTIONS
    // ------------------------------

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int userAction = event.getActionMasked();
        //@TODO: What should happen when person touches the screen?
        if (userAction == MotionEvent.ACTION_DOWN) {
            // user pushed down on screen
        }
        else if (userAction == MotionEvent.ACTION_UP) {
            // user lifted their finger
        }
        return true;
    }
}