package com.example.dungeonquest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GameOver extends AppCompatActivity {

    TextView mainmenu;

    ConstraintLayout gameoverbackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        mainmenu = (TextView)findViewById(R.id.mainmenu);

        gameoverbackground = findViewById(R.id.gameoverbackground);
        gameoverbackground.setBackgroundResource(R.drawable.black_background);

        mainmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMenu();
            }
        });
    }

    private void goToMenu() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
