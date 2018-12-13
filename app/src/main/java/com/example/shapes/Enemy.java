package com.example.shapes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;

import java.util.Random;

public class Enemy {
    RectF rect;

    Random generator = new Random();

    // The player ship will be represented by a Bitmap
    private Bitmap bitmap1;

    // How long and high our invader will be
    private float length;
    private float height;

    // X is the far left of the rectangle which forms our invader
    private float x;

    // Y is the top coordinate
    private float y;

    public final int RIGHT = 2;

    boolean isVisible;

    public Enemy(Context context, int screenX, int screenY) {

        // Initialize a blank RectF
        rect = new RectF();

        length = screenX / 10;
        height = screenY / 20;

        isVisible = true;

        int z = (int) ((screenX / 2) - ((screenX / 5) / 2)) - 20;
        int a = (int) ((screenY / 2) - ((screenY / 10) / 2)) - 20;
        boolean intx = generator.nextBoolean();
        boolean inty = generator.nextBoolean();
        if (intx) {
            x = generator.nextInt(z);
        } else {
            x = (generator.nextInt(z)) + z;
        }

        if (inty) {
            y = generator.nextInt(a);
        } else {
            y = generator.nextInt(a) + a;
        }

        // Initialize the bitmap
        bitmap1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.water);

        // stretch the first bitmap to a size appropriate for the screen resolution
        bitmap1 = Bitmap.createScaledBitmap(bitmap1,
                (int) (length),
                (int) (height),
                false);
    }

    public void setInvisible() {
        isVisible = false;
    }

    public boolean getVisibility() {
        return isVisible;
    }

    public float getHeight() {
        return height;
    }
    public RectF getRect() {
        return rect;
    }

    public Bitmap getBitmap() {
        return bitmap1;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getLength() {
        return length;
    }

    public void update(long fps) {

    }
}