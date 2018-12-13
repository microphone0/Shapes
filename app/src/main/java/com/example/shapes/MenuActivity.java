package com.example.shapes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //Set onClickListeners
        Button play = findViewById(R.id.playButton);
        Button highscore = findViewById(R.id.highscoreButton);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, ShapesActivity.class);
                startActivity(intent);
                finish();
            }
        });

        highscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, Highscore.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
