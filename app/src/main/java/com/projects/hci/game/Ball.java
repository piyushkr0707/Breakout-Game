/***************************************************************************

 HCI Final Project - Breakout game

 by
 Piyush Kumar
 Pxk152030@utdallas.edu

 *///************************************************************************


package com.projects.hci.game;
import android.graphics.*;

import java.util.Random;

public class Ball
{
    private RectF rec;   // Here we are assuming the ball to be a rectangle in shape which consists of four coordinates so that it is easy for during the intersection with the paddle
    float x_speed;  // this for setting the movement speed of the ball
    float y_spe;
    float ballWidth = 10;   // this setting the dimensions of the ball
    float ballHeight = 10;
   // create a variable float speed =
    public Ball(int screenX, int screenY , float speed){

        // Setting the initial speed
        x_speed = 450+(1*speed) ;    // 240
        y_spe =  -650-(1*speed)  ;    // 450
      //  y_spe = - y_spe;
        rec = new RectF();
    }

    public RectF getRect()
    {
        return rec;
    }

    public void update(long fps)  // this function is to update ball movement whenever if changes the postion
    {
        rec.left = rec.left + (x_speed / fps);     // here we are updating all the four coordinates for the rectF
        rec.top = rec.top + (y_spe / fps);
        rec.right = rec.left + ballWidth;
        rec.bottom = rec.top - ballHeight;
    }

    public void reverseY()    // this function is for reverse movement of x axis
    {
        y_spe = -y_spe;
    }

    public void reverseX()  // Similarly this function is for reverse movement of y axis
    {
        x_speed = - x_speed;
    }
    public void UpdateX(float x  )  // Similarly this function is for reverse movement of y axis
    {
        x_speed = x*(-240);
    }


    public void setrandom_X_speed(){  // this method is for generating the random number for the next setting the random speed
        Random speed = new Random();
        int new_speed = speed.nextInt(2);

        if(new_speed == 0)  // if by anychance the random number generated is zero then do the reverse movement of x
        {
            reverseX();
        }
    }

    public void clearObstacleY(float y)
    {
        rec.bottom = y;
        rec.top = y - ballHeight;
    }

    public void clearObstacleX(float x){
        rec.left = x;
        rec.right = x + ballWidth;
    }

    public void reset(int x, int y)
    {
        rec.left = x / 2;
        rec.top = y - 20;
        rec.right = x / 2 + ballWidth;
        rec.bottom = y - 20 - ballHeight;
    }

}