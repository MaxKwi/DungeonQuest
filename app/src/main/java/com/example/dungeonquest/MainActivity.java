package com.example.dungeonquest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView newgame, cont, htp;

    ConstraintLayout title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newgame = (TextView)findViewById(R.id.newgame);
        cont = (TextView)findViewById(R.id.cont);
        htp = (TextView)findViewById(R.id.htp);

        title = findViewById(R.id.titlebackground);
        title.setBackgroundResource(R.drawable.titlecropped);

        newgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchGame();
            }
        });
        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchGame();
            }
        });
        htp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchInstructions();
            }
        });
    }

    private void launchGame() {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    private void launchInstructions() {
        Intent intent = new Intent(this, Instructions.class);
        startActivity(intent);
    }

}
