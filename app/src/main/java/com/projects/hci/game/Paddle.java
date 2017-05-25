/***************************************************************************

 HCI Final Project - Break out game

 by
 Piyush Kumar
 Pxk152030@utdallas.edu

 *///************************************************************************

package com.projects.hci.game;

import android.graphics.RectF;

public class Paddle {

    private RectF rect;  // initialize rectf which consists of four coordinates

    private float length;   // this is for the length and height of the paddle
    private float height;
    private float x;
    private float y;

    private float Speed_paddle; // this is for the speed of the paddle


    final int initial_position = 0;     // setting values for different side movements of the paddle
    final int left = 1;
    final int right = 2;


    private int paddle_Movement = initial_position;


    public Paddle(int screenX, int screenY)  // In this function we pass the screen dimensions as the parameters
    {

        length = 180;   // setting the length and height of the paddle
        height = 25;

        // Setting the position of the paddle
        x = screenX / 2;
        y = screenY - 360;

        rect = new RectF(x, y, x + length, y + height); // Setting the four coordinates into the rectf

        Speed_paddle = 350;
    }


    public RectF getRect()  // This is a get method to make the rectangle
    {
        return rect;
    }


    public void setmovement(int position) // This method will be used to know if the paddle is going left, right or stopped
    {
        paddle_Movement = position;
    }


    public void update(long fps)   // this method is to update the paddle movement
    {
      // here fps is the frames per second that is passed from the Breakout activity

        if(paddle_Movement == left)   // if the movement is left
        {
            x = x -Speed_paddle / fps;      // reducing the x position of the screen
        }

        if(paddle_Movement == right)   // if the movement is rigt
        {
            x = x + Speed_paddle / fps;    // increasing the x postion of the screen so that it moves right
        }

        rect.left = x;                  // again setting back the values by continous updation of the position and also the length of the paddle
        rect.right = x + length;
    }

}