package com.example.davidreetz.firststronghold2016scoutingapp;
//Import. Again.
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
//Yet another class declaration
public class Comments extends AppCompatActivity {
    //Declaring stuff
    Button Finish;
    EditText Comments;
    TextView Instructions;
    Context context;
    Intent gameSetup;

    @Override
    //onCreate method
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setting layout to comments layout
        setContentView(R.layout.activity_comments);
        //initialize context
        context = this;
        //Initialize other stuffs
        Comments = (EditText) findViewById(R.id.Comments);
        Finish = (Button) findViewById(R.id.BFinish);
        gameSetup = new Intent(this, GameSetup.class);
        //OCL start
        final View.OnClickListener listen = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.BFinish:
                        //This is where we make/append the file:
                        //popup
                        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Are you sure ALL the data is correct?")
                                .setPositiveButton("Yes, quit nagging", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //Taking the commas and newLines out of comments. it screws up the CSV (comma seperated values) file
                                        // if we don't do this
                                        String revisedComments = Comments.getText().toString();
                                        revisedComments = revisedComments.replace(",", "");
                                        revisedComments = revisedComments.replace("\n", "");
                                        Global.data.setComments(revisedComments);
                                        //Increments matchesSinceCollect. We name the file Group X Matches Y-Z depending on what they select on signin, and
                                        //This helps us detect the matches for Y-Z.
                                        Global.data.matchesSinceCollect++;
                                        //sets the firstMatch variable, which is used for Y in the file naming part of the program
                                        if (Global.data.matchesSinceCollect == 1) {
                                            Global.data.firstMatch = Global.data.getMatch();
                                        }
                                        //Debug
                                        System.out.println("hello FileWriter");
                                        //StringBuilder makes it easy to append, used for the file name.
                                        StringBuilder sb = new StringBuilder();
                                        //finds just the team number from the team entry in teamNames.CSV
                                        Scanner teamScanner = new Scanner(Global.stats[Global.ioTeam]);
                                        teamScanner.useDelimiter(",");
                                        try {
                                            Global.data.setTeam(teamScanner.next());
                                        } catch (java.util.InputMismatchException e) {
                                            System.out.println("hello Not a number");
                                            sb.append("0000,");
                                        }
                                        teamScanner.close();
                                        //Puts stuff into the stringBuilder.
                                        //Advanced for loop: goes until there are no more values in the array.
                                        //The commas seperate different values.
                                        for (String i : Global.stats) {
                                            //We don't want the end result to say null, so it says nothing.
                                            if (i == null) {
                                                sb.append(",");
                                                //True is one, false is zero. This is for stuart's calculations.
                                            } else if (i.matches("true")) {
                                                sb.append("1,");
                                            } else if (i.matches("false")) {
                                                sb.append("0,");
                                                //This is everything else.
                                            } else {
                                                sb.append(i + ",");
                                            }
                                        }
                                        //Tries to make a file
                                        try {
                                            //Creating a name
                                            File newFile = new File(Environment.getExternalStorageDirectory() + "/" + Global.data.getRobot() + " Matches " + Global.data.firstMatch + " - " + Global.data.getMatch() + ".csv");
                                            //Renaming the oldFile that was already there
                                            Global.data.OldFile.renameTo(newFile);
                                            //Actually converting the oldFile to the new file
                                            Global.data.OldFile = newFile;
                                            //Creating 
                                            FileWriter fw = new FileWriter(Environment.getExternalStorageDirectory() + "/" + Global.data.getRobot() + " Matches " + Global.data.firstMatch + " - " + Global.data.getMatch() + ".csv", true);
                                            System.out.println("hello" + sb.toString());
                                            fw.append(sb.toString() + "\n");
                                            fw.flush();
                                            fw.close();
                                        } catch (IOException e) {
                                            System.out.println("hello failed");
                                        }
                                        Global.stats = new String[74];
                                        Global.data.MatchNum++;
                                        Global.data.setName(Global.User);
                                        Global.data.setRobot(Global.Robot);
                                        startActivity(gameSetup);
                                    }
                                })
                                .setNegativeButton("Uhh... let me double check", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        return;
                                    }
                                });
                        builder.create();
                        builder.show();
                        break;

                }
            }
        };
        Finish.setOnClickListener(listen);
    }
}
