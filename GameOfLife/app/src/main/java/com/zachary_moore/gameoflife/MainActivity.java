package com.zachary_moore.gameoflife;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import processing.android.PFragment;
import processing.core.PApplet;

/**
 * Created by zsmoore on 10/12/17.
 */

public class MainActivity extends FragmentActivity {

    private GameOfLife gameOfLife;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        final LinearLayout mainLayout = this.findViewById(R.id.main_layout);
        gameOfLife = new GameOfLife(10, 10);
        PFragment pFragment = new PFragment(gameOfLife);
        pFragment.setView(mainLayout, this);

        final Button goButton = this.findViewById(R.id.go_button);
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameOfLife.startConway();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
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
