package com.example.dungeonquest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.widget.ImageView;

public class GameActivity extends AppCompatActivity {

    ConstraintLayout background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        background = findViewById(R.id.background);
        background.setBackgroundResource(R.drawable.forest_environment);
    }
}
