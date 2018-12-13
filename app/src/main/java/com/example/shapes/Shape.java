package com.example.shapes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;

public class Shape {
   RectF rect;

   //The player will be represented by the Bitmap
    private Bitmap bitmap;

    //How long and high the player be
    private float length;
    private float height;

    //X is the far left of the rectangle which forms the player
    private float x;

    //Y is the top coordinate
    private float y;

    //This will hold the pixels per second speed the player can rotate
    private float shapeSpeed;


    //Which ways can the player rotate
    public final int STOPPED = 0;
    public final int LEFT = 1;
    public final int RIGHT = 2;

    //Is the player rotating and in which direction
    private int shapeRotate = STOPPED;

    //Constructor
    //When the object is created from this class we will pass
    //in the screen width and height
    public Shape(Context context, int screenX, int screenY){
        //Initialize a blank Rectf
        rect = new RectF();

        //Initialize the player based on screen resolution
        //Increase the denominator for a smaller ship
        //Decrease the denominator for a bigger ship
        length = screenX/5;
        height = screenY/10;

        //Start the player in the middle of the screen
        x = (screenX/2)-(length/2);
        y = (screenY/2)-(height/2);

        //Initialize the bitmap
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.circle);

        //Stretch the bitmap to a size appropriate for the screen resolution
        bitmap = Bitmap.createScaledBitmap(bitmap,
                (int) (length),
                (int)(height),
                false);

        //How fast is the player in pixels per seconds
        shapeSpeed = 350;
    }

    public RectF getRect(){
        return rect;
    }

    //This is a getter method to make the rectangle that
    //defines the player available in ShapesActivity class
    public Bitmap getBitmap(){
        return bitmap;
    }

    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }
    public float getLength() {
        return length;
    }

    //This method will be used to change/set if the ship is going left, right, or not moving
    public void setShapeRotate(int state){
        shapeRotate = state;
    }

    //This update method will be called from the update in ShapesView
    //It determines if the player needs to move and changes the coordinates
    //contained in x if necessary
    public void update(long fps){
        if(shapeRotate == LEFT){
            x = x - shapeSpeed / fps;
        }

        if(shapeRotate == RIGHT){
            x = x + shapeSpeed / fps;
        }

        //Update rect which is used to detect hits
        rect.top = y;
        rect.bottom = y + height;
        rect.left = x;
        rect.right = x + length;
    }
}
