package com.example.shapes;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Highscore extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        //Make a "Home button" that returns user to home page
        FloatingActionButton home = findViewById(R.id.HomeButton);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Highscore.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


}
