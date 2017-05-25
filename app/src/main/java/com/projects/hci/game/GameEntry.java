
/***************************************************************************

 HCI Final Project - Break out game

 by
 Piyush Kumar
 Pxk152030@utdallas.edu

 *///************************************************************************

package com.projects.hci.game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

public class GameEntry extends AppCompatActivity {

    float seekstatus=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_entry);



        final Button start = (Button) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent q = new Intent(GameEntry.this , Breakout.class);
                String value =Float.toString(seekstatus) ;
                q.putExtra("seekstatus",  value);

                startActivity(q);


            }
        });

        final Button high = (Button) findViewById(R.id.highscore);
        high.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent qq = new Intent(GameEntry.this, HighScore_Display.class);
                startActivity(qq);
            }
        });



    }
}
