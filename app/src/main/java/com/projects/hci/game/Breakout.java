/***************************************************************************

                     HCI Final Project - Breakout game

                           by
                     Piyush Kumar
                     Pxk152030@utdallas.edu

*///************************************************************************
package com.projects.hci.game;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.Random;

public class Breakout extends Activity implements SensorEventListener {


    Sensor accelerometer ;  ///////  declaring variables for sensor and sensor manager
    SensorManager sm;
    float sensorX ; // for storing the value of acceerometer .
    float ballspeednew;
    // gameView will be the view of the game
    // It will also hold the logic of the game
    // and respond to screen touches as well
    BreakoutView breakout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize gameView and set it as the view

        // ********  for Accelerometer
        sm              = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelerometer   = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sm.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);

        //*************************************************

        breakout = new BreakoutView(this);
        setContentView(breakout);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

      sensorX =  event.values[0];

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    // Here is our implementation of BreakoutView
    // It is an inner class.
    // Note how the final closing curly brace }
    // is inside the BreakoutGame class

    // Notice we implement runnable so we have
    // A thread and can override the run method.
    class BreakoutView extends SurfaceView implements Runnable {

        // This is our thread
        Thread gameThread = null;

        // This is new. We need a SurfaceHolder
        // When we use Paint and Canvas in a thread
        // We will see it in action in the draw method soon.
        SurfaceHolder ourHolder;

        // A boolean which we will set and unset
        // when the game is running- or not.
        volatile boolean playing;  // this a boolean value which will is used to know whether the game is running or not and we used volatile because we are using multithreading
        // Game is paused at the start
        boolean paused = true;
        Canvas canvas;   // initializing the canvas and paint
        Paint paint;
        long fps;  // this is a variable for the frames per inch and we took it long because the time obtained from the system time is fa more than int thus we take long
        long frametime; // This is used to calculate the fps
        int screenX;     // These variables are for getting the size of the screen
        int screenY;
        Paddle paddle;   // Initializing the paddle
        Ball ball;    // This is for the ball
        int k;  // This is for the score
        Brick[] bricks = new Brick[200];  // initalizing the brick array
        int brickcount=0;
        Random rn=new Random();
        int[] brickcolorarray=new int[200];
        int numBricks = 0;
        int score = 0;

        // Lives
        int lives = 3;

        // When the we initialize (call new()) on gameView
        // This special constructor method runs
        public BreakoutView(Context context) {
            // The next line of code asks the
            // SurfaceView class to set up our object.
            // How kind.
            super(context);

            // Initialize ourHolder and paint objects
            ourHolder = getHolder();
            paint = new Paint();

            // Get a Display object to access screen details
            Display display = getWindowManager().getDefaultDisplay();
            // Load the resolution into a Point object
            Point size = new Point();
            display.getSize(size);

            screenX = size.x;
            screenY = size.y;

            Bundle g = getIntent().getExtras();
            //ballspeednew=(Float.parseFloat(g.getString("seekstatus")));
            ballspeednew = 1;
            // Create a ball
            ball = new Ball(screenX, screenY - 360, ballspeednew);
//             System.out.println("ballspeednew:"+ballspeednew);
            paddle = new Paddle(screenX, screenY);




            brickcolorarray[0]=2;
            for(int m=1;m<200;m++){

                k = rn.nextInt(5) + 1;
                while(k==brickcolorarray[m-1]){
                    k=rn.nextInt(5)+1;
                }
                brickcolorarray[m] = k;

            }
            create_bricks();

        }

        public void create_bricks()  // This method is for creating the wall of bricks
        {

            // Put the ball back to the start
            ball.reset(screenX, screenY - 360);
            numBricks = 0;
            int columnnum=10;   // As given that top row should contain nine bricks we have taken the initial to be 10
            for(int row = 0; row < 3; row ++ ){
                columnnum--;   // Required that each row should be one less than the above row
                int brickWidth = screenX /columnnum;   // Division of the x axis based on the number of columns for the number of bricks to be present
                int brickHeight = screenY/20;   // Similarly calculating what the height of each brick should be
                for(int column = 0; column < columnnum; column ++ )
                {
                    bricks[numBricks] = new Brick(row, column,brickWidth,brickHeight); // Putting these values to the four coordinates of the rectf
                    numBricks ++;  // here each element is a brick which will be useful for painting it later
                }
            }


            // if game over reset scores and lives
            if(lives == 0) {
                score = 0;
                lives = 3;
            }

        }

        @Override
        public void run()  // this method is for running of the game
        {
            while (playing) {
                long startFrameTime = System.currentTimeMillis(); // getting the current time in seconds

                if(!paused) // we are updating the frame if the game is not paused
                {
                    update();
                }

                // Draw the frame
                draw();

                frametime = System.currentTimeMillis() - startFrameTime;
                if (frametime >= 1) {
                    fps = 1000 / frametime;  // calculating the frames per second
                }

            }

        }

        // Everything that needs to be updated goes in here
        // Movement, collision detection etc.
        public void update() {
            ball.UpdateX(sensorX);  //***********************        ********************************************

            // Move the paddle if required
            paddle.update(fps);

            ball.update(fps);

            // Check for ball colliding with a brick
            for(int i = 0; i < numBricks; i++){

                if (bricks[i].getVisibility())
                {

                    if(RectF.intersects(bricks[i].getRect(), ball.getRect())) {

                        if(brickcolorarray[i]==1){
                            bricks[i].setInvisible();
                            brickcount++;
                        }
                        brickcolorarray[i]=brickcolorarray[i]-1;
                        ball.reverseY();
                        score = score + 10;


                    }
                }
            }


            if(RectF.intersects(paddle.getRect(),ball.getRect())) // this is to check whether the ball collides with the paddle checked
            {
                ball.setrandom_X_speed();
                ball.reverseY();
                ball.clearObstacleY(paddle.getRect().top - 2);

            }


            if(ball.getRect().bottom > screenY -360)  // this is to check if the ball touches the bottom of the screen
            {
                // here if the ball touches the screen then the user has lost the game and thus it goes into the next screen
                canvas.drawText("YOU HAVE LOST!", 10, screenY / 2, paint);
                Intent q = new Intent(getBaseContext(), EnterScore.class);
                q.putExtra("Transfer", "" + score);
                startActivity(q);

            }

            // below are the conditions for the ball bouncing
            if(ball.getRect().top < 0)  // This is to bounce the ball back when it hits the top of screen
            {
                ball.reverseY();
                ball.clearObstacleY(12);


            }


            if(ball.getRect().left < 0)  // This is if the ball hits left wall
            {
                ball.reverseX();
                ball.clearObstacleX(2);


            }


            if(ball.getRect().right > screenX - 10)  // This is if the ball hits right wall
            {
                ball.reverseX();
                ball.clearObstacleX(screenX - 22);


            }

            // Pause if cleared screen
            if(brickcount==numBricks){
                paused = true;
                create_bricks();
            }

        }

        // Draw the newly updated scene
        public void draw() {
            // brickcolorarray[0]=1;
            // Make sure our drawing surface is valid or we crash
            if (ourHolder.getSurface().isValid()) {
                // Lock the canvas ready to draw
                canvas = ourHolder.lockCanvas();

                // Draw the background color
                canvas.drawColor(Color.DKGRAY);
                // canvas.drawColor(Color.argb(255,  26, 128, 182));

                // Choose the brush color for drawing
                paint.setColor(Color.argb(255,  255, 255, 255));

                // Draw the paddle
                canvas.drawRoundRect(paddle.getRect(), 20, 50, paint);

                // Draw the ball
                canvas.drawCircle(ball.getRect().centerX(), ball.getRect().centerY(), screenX / 50, paint);

                // Change the brush color for drawing
                paint.setColor(Color.argb(255,  249, 129, 0));

                // Draw the bricks if visible

                for(int i = 0; i < numBricks; i++)   // this loop is for putting different colors to bricks
                {
                    if(brickcolorarray[i]==1)
                    {
                        paint.setColor(Color.argb(255,  255, 255, 255));   // white
                    }
                    else if(brickcolorarray[i]==2)
                    {
                        paint.setColor(Color.argb(255,  160,32,240));    // purple
                    }
                    else if(brickcolorarray[i]==3)
                    {
                        paint.setColor(Color.argb(255,  139,69,19));   // Saddle Brown
                    }
                    else if(brickcolorarray[i]==4)
                    {
                        paint.setColor(Color.argb(255, 50,160,50));   //  green
                    }
                    else
                    {
                        paint.setColor(Color.argb(255,  0, 0, 0));   // black
                    }
                    if(bricks[i].getVisibility())  // getting the values of the visibility of each brick
                    {
                        canvas.drawRect(bricks[i].getRect(), paint);  // painting the brick
                    }
                }

                // Choose the brush color for drawing
                paint.setColor(Color.YELLOW);

                // Draw the score
                paint.setTextSize(70);
                canvas.drawText("Score: " + score, 15,1750, paint);


                if(brickcount==numBricks)  // This is if the player has finished all the bricks
                {
                    paint.setTextSize(80);
                    canvas.drawText("YOU HAVE WON!", 10, screenY / 2, paint);
                    Intent q = new Intent(getBaseContext(), EnterScore.class);
                    q.putExtra("Transfer",""+score);
                    startActivity(q);

                }




                // Draw everything to the screen
                ourHolder.unlockCanvasAndPost(canvas);
            }

        }

        // If SimpleGameEngine Activity is paused/stopped
        // shutdown our thread.
        public void pause() {
            playing = false;
            try {
                gameThread.join();
            } catch (InterruptedException e) {
                Log.e("Error:", "joining thread");
            }

        }

        // If SimpleGameEngine Activity is started theb
        // start our thread.
        public void resume() {
            playing = true;
            gameThread = new Thread(this);
            gameThread.start();
        }

        // The SurfaceView class implements onTouchListener
        // So we can override this method and detect screen touches.
        @Override
        public boolean onTouchEvent(MotionEvent motionEvent) {

            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

                // Player has touched the screen
                case MotionEvent.ACTION_DOWN:

                    paused = false;

                    if(motionEvent.getX() > screenX / 2){
                        paddle.setmovement(paddle.right);
                    }
                    else{
                        paddle.setmovement(paddle.left);
                    }

                    break;

                // Player has removed finger from screen
                case MotionEvent.ACTION_UP:

                    paddle.setmovement(paddle.initial_position);
                    break;
            }
            return true;
        }

    }
    // This is the end of our BreakoutView inner class

    // This method executes when the player starts the game
    @Override
    protected void onResume() {
        super.onResume();

        // Tell the gameView resume method to execute
        breakout.resume();
    }

    // This method executes when the player quits the game
    @Override
    protected void onPause() {
        super.onPause();

        // Tell the gameView pause method to execute
        breakout.pause();
    }

}
// This is the end of the BreakoutGame class
