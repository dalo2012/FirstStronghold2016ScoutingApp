package com.example.davidreetz.firststronghold2016scoutingapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
//this has all the stuff Activity has, and more that we add below.
public class ScoutSignIn extends Activity {
    //Declaring Objects. I have the initials of each data type before the var name because it helps me keep track of what's what.
    //ET=EditText
    AutoCompleteTextView ETScoutName;
    //dropdown menu
    Spinner SRobot;
    Button BToFieldLayout;
    //for spinner (will discuss later)
    String[] Robots;
    //how we get to the next activity (screen)
    Intent ITGameSetup;

    //this means that we won't be using the barebones version of onCreate that the activity class gives us.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //??? we should look this up, I don't know what its for, but it is put before every super class.
        super.onCreate(savedInstanceState);
        //show the layout activity_scout_sign_in
        setContentView(R.layout.activity_scout_sign_in);
        //initializing intent
        ITGameSetup = new Intent(this, GameSetup.class);
        //SharedPreferences declaration and initialization. We'll talk about this in class. Remind me.
        SharedPreferences preferences = getSharedPreferences("myPreferences", MODE_PRIVATE);
        //SP Editor declaration and init.
        SharedPreferences.Editor editor = preferences.edit();
        //anti crash stuff, used to make sure if we are starting the app for our first scouting app use or consecutively.
        if (Global.data == null) {
            editor.putInt("MatchNum", 1);
            editor.commit();
            Global.data = new Global();
        }
        //Everything resets if left alone for 10 mins. If not, it automatically puts in the data you previously entered and skips to the next activity.
        if (System.currentTimeMillis() - (preferences.getLong("SaveTime", 0)) < 600000 && !preferences.getBoolean("NotYou", true)) {
            Global.data.setName(preferences.getString("ScoutName", ""));
            Global.data.setRobot(preferences.getString("Robot", ""));
            Global.data.setMatch(preferences.getInt("MatchNum", 1));
            editor.putBoolean("NotYou", false);
            editor.commit();
            startActivity(ITGameSetup);
        }
        //initializing all the stuff declared before our current method.
        ETScoutName = (AutoCompleteTextView) findViewById(R.id.ETScoutName);
        SRobot = (Spinner) findViewById(R.id.SRobot);
        BToFieldLayout = (Button) findViewById(R.id.BToFieldLayout);
        Robots = new String[]{"Select One", "Group 1", "Group 2", "Group 3", "Group 4", "Group 5", "Group 6"};
        //spinners have to have an adapter to read a string array, so you can see what's contained in the list. That's what this is for.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Robots);
        //setting the adapter to the spinner
        SRobot.setAdapter(adapter);
        //easter egg. Pretty much does nothing except be funny.
        Toast.makeText(getApplicationContext(), "You're using APPWITHANATTITUDE(c)2016\nDavid Reetz and nonexistent friends LLC",
                Toast.LENGTH_LONG).show();
        //Event handling. OCL basically waits for you to click something, and then it does something. Simple yet complicated.
        View.OnClickListener listen = new View.OnClickListener() {
            @Override
            //this method is activated whenever someone clicks something that the onclicklistener was set to. (later in code). It takes the
            //view that was clicked as its argument.
            public void onClick(View v) {
                //Switch/Case statements to determine what was clicked.
                //Gets the view that was taken as an argument. v= the view.
                switch (v.getId()) {
                    case R.id.BToFieldLayout:
                        //Error Checking. From now on, I'll just say EC.
                        if (ETScoutName.getText().toString().matches("")) {
                            Toast.makeText(getApplicationContext(), "Please enter a name, Mr./Ms. <error>.",
                                    Toast.LENGTH_LONG).show();
                            return;
                        }
                        //EC
                        if (SRobot.getSelectedItem().toString().matches("Select One")) {
                            Toast.makeText(getApplicationContext(), "Please choose which robot you are scouting.",
                                    Toast.LENGTH_LONG).show();
                            return;
                        }
                        //Setting the data in Global to whatever the user inputted.
                        Global.data.setRobot(SRobot.getSelectedItem().toString());
                        Global.Robot = SRobot.getSelectedItem().toString();
                        Global.User = ETScoutName.getText().toString();
                        Global.data.setName(ETScoutName.getText().toString());
                        //Starts the next activity.
                        startActivity(ITGameSetup);
                }
            }
        };
        //Sets the OCL to the button. The only custom click action. This is what I referenced before.
        BToFieldLayout.setOnClickListener(listen);
    }
}