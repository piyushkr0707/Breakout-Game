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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class HighScore_Display extends AppCompatActivity {   // this activity is for displaying the top scores
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score__display);
        String[] listitems;
        String entirecontent="";
        String newcontent="";
        try {
            System.out.println("Hai");
            FileInputStream fi= openFileInput("Scor.txt");
            int content;
            while ((content = fi.read()) != -1) {
                entirecontent=entirecontent+Character.toString((char)content);  // storing the content into a whole string
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        listitems=entirecontent.split("\n");     // splitting the string based on \n
        int scores[] = new int[100];
        for(int xz=0; xz<listitems.length; xz++){
            String[] dataline = listitems[xz].split("\t");      // splitting the string based on \t
    //        names[xz]=dataline[0];
            scores[xz]=Integer.parseInt(dataline[1]);   // here we are storing the scores into an array
   //         System.out.println(names[xz]);
           System.out.println(scores[xz]);
        }

        int temp;

        for(int i = 0; i < scores.length; i++)       // this is the bubble sorting technique for sorting the scores in descending order
        {
            for(int j = 1; j < (scores.length-i); j++)
            {
                if(scores[j-1] < scores[j])
                {
                    temp = scores[j-1];
                    scores[j-1]=scores[j];
                    scores[j]=temp;
                    String temp1;
                    temp1 = listitems[j-1];    // here based on the sorted index we sort the entire string
                    listitems[j-1] = listitems[j];
                    listitems[j] = temp1;
                }
            }
        }
        int count=1;
        String[] sortedlist=new String[11];
        String[] topten;
        for(int we=0;we<listitems.length;we++)    // here we are copying the top ten values into a new string
        {
            if (count < 11) {
                sortedlist[we] = listitems[we];
                System.out.println(sortedlist[we]);
                count++;
            }
        }
        try {     // storing top 10 into the new file each time

            FileOutputStream fout = openFileOutput("HighScores.txt",MODE_WORLD_READABLE);
            System.out.println("hai u have entered the file");
            for(int we=0;we<10;we++)
            {
                System.out.println("yooooooo"+sortedlist[we]);
                if (sortedlist[we]==null)
                    break;
                else{
                fout.write((sortedlist[we] + "\n").getBytes());
            }}
            } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {            // this is for reading the stored  top ten content
            FileInputStream fi= openFileInput("HighScores.txt");
            int content;
            while ((content = fi.read()) != -1) {
                newcontent=newcontent+Character.toString((char)content);  // storing the content into a whole string
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        topten=newcontent.split("\n");    //splitting the content based on \n and putting it into a new string

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list, topten);  // writing an array adapter to store the list into the adapter and set the list into the adapter
        final ListView list = (ListView) findViewById(R.id.listView);
        list.setAdapter(adapter);


        System.out.println(entirecontent);
        final Button back = (Button) findViewById(R.id.back);  // this is the back button to return to the main screen of the game
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


              //  Intent a = new Intent(getBaseContext(), Breakout.class);
                Intent a = new Intent(getBaseContext(), GameEntry.class);
                startActivity(a);   // starting the next activity

            }
        });
    }
        }
