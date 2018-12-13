package com.example.shapes;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;

import java.io.IOException;

public class ShapesView extends SurfaceView implements Runnable {
    Context context;

    //Our thread
    private Thread gameThread = null;

    //Our SurfaceHolder to lock the surface before we draw our graphics
    private SurfaceHolder ourHolder;

    //A boolean which will set or unset
    //when the game is running- or not
    private volatile boolean playing;

    //A boolean to see if the game is running or not
    private boolean paused = true;

    private int index = 0;

    //A Canvas and Paint object
    private Canvas canvas;
    private Paint paint;

    //A variable to track the fps rate
    private long fps;

    //Used to calculate fps
    private long timeThisFrame;

    //The size of the screen
    private int screenX;
    private int screenY;

    //The player
    private Shape playerShape;

    //The player's bullet
    private Bullet[] playerBullet = new Bullet[200];
    private int numBullets;

    //The enemies bullets
    //private Bullet[] enemyBullet = new Bullet[200];
    private int nextEnemyBullet;
    private int maxEnemyBullet = 10;

    //Up to 20 Enemies
    private Enemy[] enemies = new Enemy[100];
    private int numenemy = 0;


    //The score
    int score = 0;

    //Lives
    private int lives = 3;


    public ShapesView(Context context, int x, int y) {
        super(context);

        //Make a copy
        this.context = context;

        ourHolder = getHolder();
        paint = new Paint();

        screenX = x;
        screenY = y;




//        try{
//            //Create objects of the 2 required classes
//            AssetManager assetManager = context.getAssets();
//            AssetFileDescriptor descriptor;
//
//            descriptor = assetManager.openFd("");
//        }catch(IOException e){
//            Log.e("error", "failed to load sound files");
//        }
        prepareLevel();
    }

    private void prepareLevel(){
        //Here we will initialize all the game objects

        //Make a new player
        playerShape = new Shape(context, screenX, screenY);

        //Prepare the player's bullet
        index = 0;
        for(int i = 0; i < playerBullet.length; i++){
            playerBullet[i] = new Bullet(screenX, screenY);
            numBullets++;
        }

        //Initialize the enemiesBullets array
//        for(int i = 0; i < enemyBullet.length; i++){
//            enemyBullet[i] = new Bullet(screenX, screenY);
//        }

        // Build an army of invaders
        numenemy = 0;
        for(int i = 0; i < enemies.length; i++ ){
            enemies[i] = new Enemy(context, screenX, screenY);
            numenemy++;
        }

    }

    @Override
    public void run() {
        while (playing) {
            //Capture the current time in milliseconds in startFrameTime
            long startFrameTime = System.currentTimeMillis();

            //Update the frame
            if (!paused) {
                update();
            }

            //Draw the frame
            draw();

            //Calculate the fps this frame
            //The result is used to time animations
            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                fps = 1000 / timeThisFrame;
            }

            //Something is done here near the end of the project
        }
    }

    public void update() {
        //Did an enemy bump into the side of the screen
        boolean bumped = false;

        //Did the player die
        boolean lost = false;

        //Rotate the player
        playerShape.update(fps);

        // Update all the invaders if visible
        for (int i = 0; i < numenemy; i++) {

            if (enemies[i].getVisibility()) {
                // Move the next invader
                enemies[i].update(fps);

//                // Does he want to take a shot?
//                if(enemies[i].takeAim(playerShape.getX(),
//                        playerShape.getLength())){
//
//                    // If so try and spawn a bullet
//                    if(enemies[nextBullet].shoot(enemies[i].getX()
//                                    + invaders[i].getLength() / 2,
//                            enemies[i].getY(), enemyBullet.DOWN)) {
//
//                        // Shot fired
//                        // Prepare for the next shot
//                        nextBullet++;
//
//                        // Loop back to the first one if we have reached the last
//                        if (nextBullet == maxInvaderBullets) {
//                            // This stops the firing of another bullet until one completes its journey
//                            // Because if bullet 0 is still active shoot returns false.
//                            nextBullet = 0;
//                        }
//                    }
//                }

//                // If that move caused them to bump the screen change bumped to true
//                if (invaders[i].getX() > screenX - invaders[i].getLength()
//                        || invaders[i].getX() < 0){
//
//                    bumped = true;
//
//                }
            }

        }

        //Update all the enemies bullets if active
//        for(int i = 0; i < enemyBullet.length; i++){
//            if(enemyBullet[i].getStatus()){
//                enemyBullet[i].update(fps);
//            }
//        }

//        // Did an invader bump into the edge of the screen
//        if(bumped){
//
//            // Move all the invaders down and change direction
//            for(int i = 0; i < numenemy; i++){
//                enemies[i].dropDownAndReverse();
//                // Have the invaders landed
//                if(enemies[i].getY() > screenY - screenY / 10){
//                    lost = true;
//                }
//            }
//
//            // Increase the menace level
//            // By making the sounds more frequent
//            menaceInterval = menaceInterval - 80;
//        }

        if (lost) {
            prepareLevel();
        }

        //Update the players bullet
        for (int i = 0; i < playerBullet.length; i++) {
            if (playerBullet[i].getStatus()) {
                playerBullet[i].update(fps);
            }
        }

        // Has the player's bullet hit the top of the screen
        for (int i = 0; i < playerBullet.length; i++) {
            if (playerBullet[i].getImpactPointY() < 0 || playerBullet[i].getImpactPointX() > screenX) {
                playerBullet[i].setInactive();
            }
            if (playerBullet[i].getImpactPointY() < 0 || playerBullet[i].getImpactPointY() > screenY) {
                playerBullet[i].setInactive();
            }
        }

        //Has an enemy bullet hit the edge of the screen

        // Has the player's bullet hit an invader
        for(int j = 0; j < playerBullet.length; j++){
           if (playerBullet[j].getStatus()) {
               for (int i = 0; i < numenemy; i++) {
                   if (enemies[i].getVisibility()) {
                       if ((playerBullet[j].getImpactPointX() > enemies[i].getX() && playerBullet[j].getImpactPointX() < (enemies[i].getX() + enemies[i].getLength()))
                               && (playerBullet[j].getImpactPointY() > enemies[i].getY() && playerBullet[j].getImpactPointY() < (enemies[i].getY() + enemies[i].getHeight()))) {
                           enemies[i].setInvisible();
                           playerBullet[j].setInactive();
                           score = score + 10;

                           // Has the player won
                           if (score == numenemy * 10) {
                               paused = true;
                               score = 0;
                               lives = 3;
                               prepareLevel();
                           }
                       }
                   }
               }
           }
        }

        //Has an enemy bullet hit the player

    }

    public void draw() {
        //Make sure our drawing surface is valid or we crash
        if (ourHolder.getSurface().isValid()) {
            //Lock the canvas ready to draw
            canvas = ourHolder.lockCanvas();

            //Draw the background color
            canvas.drawColor(Color.argb(255, 0, 0, 0));

            //Choose the brush color for the drawing
            paint.setColor(Color.argb(255, 255, 255, 255));

            //Draw the player
            canvas.drawBitmap(playerShape.getBitmap(), playerShape.getX(), playerShape.getY(), paint);

            // Draw the invaders
            for(int i = 0; i < numenemy; i++){
                if(enemies[i].getVisibility()) {
                    canvas.drawBitmap(enemies[i].getBitmap(), enemies[i].getX(), enemies[i].getY(), paint);
                }
            }

            //Draw the player's bullets if active
            for(int i = 0; i < playerBullet.length; i++){
                if(playerBullet[i].getStatus()){
                    canvas.drawCircle(playerBullet[i].getX(),playerBullet[i].getY(),20,paint);
                }
            }

            //Draw the enemies's bullet if active
//            for(int i = 0; i < enemyBullet.length; i++){
//                if(enemyBullet[i].getStatus()){
//                    canvas.drawCircle(enemyBullet[i].getX(),enemyBullet[i].getY(),20,paint);
//                }
//            }

            //Draw the score and remaining lives
            //Change the brush color
            paint.setColor(Color.argb(255, 255, 255, 255));
            paint.setTextSize(40);
            canvas.drawText("Score: " + score + "   Lives:" + lives, 10, 50, paint);

            //Draw everything to the screen
            ourHolder.unlockCanvasAndPost(canvas);
        }
    }

    //If ShapesActivity is paused/stopped
    //shutdown thread
    public void pause(){
        playing = false;
        try{
            gameThread.join();
        }catch(InterruptedException e){
            Log.e("Error: ", "joining thread");
        }
    }

    //If ShapesActivity is started then
    //start our thread
    public void resume(){
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }


    //The SurfaceView class implements onTouchListener
    //So we can override this method and detect screen touches.
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            //Player has touched the screen
            case MotionEvent.ACTION_DOWN:

                paused = false;

                //Shoot the bullet
                if (playerBullet[index].shoot(playerShape.getX(),
                        playerShape.getY(),
                        motionEvent.getX(),
                        motionEvent.getY(),
                        screenX,
                        screenY)) {  }

                index++;
                if(index == numBullets) {
                    index = 0;
                }
                break;

            //Player has removed finger from screen
            case MotionEvent.ACTION_UP:

//                if(motionEvent.getY() > screenY - screenY/10){
//                    playerShape.setShapeRotate(playerShape.STOPPED);
//                }
                break;
        }
        return true;


    }
}


