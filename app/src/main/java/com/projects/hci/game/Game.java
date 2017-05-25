/***************************************************************************

 HCI Final Project - Break out game

 by
 Piyush Kumar
 Pxk152030@utdallas.edu

 *///************************************************************************


package com.projects.hci.game;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.*;
import android.graphics.Canvas;
import android.view.Display;


public class Game extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        int screenX;
        int screenY;
        Paddle paddle;
        Canvas canvas;
        Paint paint;
        // Get a Display object to access screen details
        Display display = getWindowManager().getDefaultDisplay();
        // Load the resolution into a Point object
        Point size = new Point();
        display.getSize(size);

        screenX = size.x;
        screenY = size.y;
        paddle = new Paddle(screenX, screenY);



    }
}

