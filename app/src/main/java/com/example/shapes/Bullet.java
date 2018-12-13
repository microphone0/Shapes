package com.example.shapes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.support.design.bottomappbar.BottomAppBar;
import android.view.Display;
import android.content.res.Resources;


public class Bullet {
    private float x = 100;
    private float y = 100;
    private float targetX = 1;
    private float targetY = 1;
    private float slope = 1;
    private float slopeX = 1;
    private float slopeY = 1;
    private float x0;
    private float y0;

    private boolean LEFT = false;
    private boolean BOTTOM = false;

    private Bitmap bitmap;
    private Canvas canvas;
    private Paint paint;

    private float horizontalVelocity;
    private float verticalVelocity;

    //Set speed
    float speed = 1000;


    private boolean isActive;

    //Constructor
    public Bullet(int screenX, int screenY){
        isActive = false;
        x0 = (screenX/2)+((screenX/5)/2);
        y0 = (screenY/2)+((screenY/10)/2);

        canvas = new Canvas();
        paint = new Paint();
        paint.setColor(Color.argb(255, 255, 255, 255));
    }

    //Getters and Setters to be used in ShapesView
    public Canvas getCanvas(){
        return canvas;
    }
    public Paint getPaint() {
        return paint;
    }
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
    public boolean getStatus(){
        return isActive;
    }
    public void setInactive(){
        isActive = false;
    }
    public float getImpactPointY(){
        return y;
    }
    public float getImpactPointX(){
        return x;
    }

    //Determine if a bullet is Active if not implements the method, if so then do nothing
    public boolean shoot(float startX, float startY, float touchX, float touchY, int screenX, int screenY) {
        if(!isActive){
            x = (startX+((screenX/5)/2));
            y = (startY+((screenX/10)/2));

            LEFT = false;
            BOTTOM = false;

            targetX = touchX;
            targetY = touchY;

            slopeX = (x/targetX);
            slopeY = (y/targetY);


            if(targetX < (screenX/2)) {
                LEFT = true;
            }

            if(targetY < (screenY/2)) {
                BOTTOM = true;
            }

            slope = slopeY/slopeX;

            isActive = true;
            return false;
        }

        //Bullet is already Active
        return false;
    }

//    private void changeSlope(){
//        slopeX = (x/targetX);
//        slopeY = (y/targetY);
//    }

   // private void moveBullet(long fps) {
//        if(LEFT){
//            if(slopeX - slopeY > 0)
//                x = (x - speed/fps) - (slopeX - slopeY) - 10;
//            else
//                x = (x - speed/fps);
//        }
//        else{
//            if(slopeX - slopeY > 0)
//                x = (x + speed/fps) + (slopeX - slopeY) - 10;
//            else
//                x = (x + speed/fps);
//        }
//
//        if(BOTTOM){
//            if(slopeY - slopeX > 0)
//                y = (y - speed/fps) - (slopeY - slopeX) - 10;
//            else
//                y = (y - speed/fps);
//        }
//        else{
//            if(slopeY - slopeX > 0)
//                y = (y + speed/fps) + (slopeY - slopeX) - 10;
//            else
//                y = (y + speed/fps);
//        }
//
//        changeSlope();
//        if(LEFT && BOTTOM) {
//            if((slopeY - slopeX) > 0) {
//                x = (x - speed / fps);
//                y = (y - speed / fps) - (slopeY - slopeX) - 10;
//            }
//            else {
//                x = (x - speed / fps) - (slopeY - slopeX) - 10;
//                y = (y - speed / fps);
//            }
//        }
//        else if(!LEFT && BOTTOM){
//            if((slopeY - slopeX) > 0) {
//                x = (x + speed / fps);
//                y = (y - speed / fps) - (slopeY - slopeX) - 10;
//            }
//            else {
//                x = (x + speed / fps) + (slopeY - slopeX) + 10;
//                y = (y - speed / fps);
//            }
//        }
//        else if(!LEFT && ! BOTTOM){
//            if((slopeY - slopeX) > 0) {
//                x = (x + speed / fps);
//                y = (y + speed / fps) + (slopeY - slopeX) + 10;
//            }
//            else {
//                x = (x + speed / fps) + (slopeY - slopeX) + 10;
//                y = (y + speed / fps);
//            }
//        }
//        else{ //LEFT && !BOTTOM
//            if((slopeY - slopeX) > 0) {
//                x = (x - speed / fps);
//                y = (y + speed / fps) + (slopeY - slopeX) + 10;
//            }
//            else {
//                x = (x - speed / fps) - (slopeY - slopeX) - 10;
//                y = (y + speed / fps);
//            }
//        }
//        changeSlope();


    //}

    public void update(long fps){
        //Move the bullet
        if(x > targetX && y > targetY){
            x = x - speed/fps;
            y = y - speed/fps;
        }
        else if(x < targetX && y > targetY){
            x = x + speed/fps;
            y = y - speed/fps;
        }
        else if(x > targetX && y < targetY){
            x = x - speed/fps;
            y = y + speed/fps;
        }
        else if(x < targetX && y < targetY){
            x = x + speed/fps;
            y = y + speed/fps;
        }

        if(x == targetX && y == targetY)
            isActive =false;

        //Update rect
        canvas.drawCircle(x,y,20,paint);
    }
}
