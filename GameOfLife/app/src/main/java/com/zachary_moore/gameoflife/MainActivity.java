package com.zachary_moore.gameoflife;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import processing.android.PFragment;

public class MainActivity extends FragmentActivity {

    // GameOfLife to hold onto
    private GameOfLife gameOfLife;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        // Create our GameOfLife and add it to our layout as a PFragment
        final LinearLayout gameContainer = this.findViewById(R.id.game_container);
        gameOfLife = new GameOfLife();
        PFragment pFragment = new PFragment(gameOfLife);
        pFragment.setView(gameContainer, this);

        // Grab our goButton and set it's onClick to start the game
        final Button goButton = this.findViewById(R.id.go_button);
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameOfLife.startConway();
            }
        });

        // Grab our resetButton and set it's onClick to reset the game
        final Button resetButton = this.findViewById(R.id.reset_button);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameOfLife.reset();
            }
        });

        // Grab our stepButton and set it's onClick to step the game
        final Button stepButton = this.findViewById(R.id.step_button);
        stepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameOfLife.stepOne();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (gameOfLife != null) {
            gameOfLife.onRequestPermissionsResult(
                    requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        if (gameOfLife != null) {
            gameOfLife.onNewIntent(intent);
        }
    }
}
