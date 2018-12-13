package com.example.shapes;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

public class ShapesActivity extends Activity {
    //This will be the view of the game and hold the logic of the game
    //and respond to screen touches
    ShapesView shapesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Make a Display object to access screen details
        Display display = getWindowManager().getDefaultDisplay();
        //Load the resolution into a Point object
        Point size = new Point();
        display.getSize(size);

        //Initialize gameView and set it as the view
        shapesView = new ShapesView(this, size.x, size.y);
        setContentView(shapesView);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Tell the gameView resume method to execute
        shapesView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        shapesView.pause();
    }
}
