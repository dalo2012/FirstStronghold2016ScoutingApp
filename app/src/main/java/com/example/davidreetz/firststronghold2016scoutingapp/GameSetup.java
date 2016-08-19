package com.example.davidreetz.firststronghold2016scoutingapp;
//import all the random crap
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
//another extension of the activity class
public class GameSetup extends Activity {
    //declaration of another sharedPreferences object
    SharedPreferences preferences;
    //reason it's set to -1 is that that doesn't match any of the switch/case statements later in the code, so it goes to the default.
    int obstacle = -1;
    //more intents, one to go forward, one to go back.
    Intent ITAuto, ITBack;
    //spinner for picking the team list
    Spinner sTeams;
    //input matchnumber
    EditText MatchNum;
    //empty imageviews and template inmageviews to 'copy' over to the empties.
    ImageView shovel, portcullis, moat, ramparts, sallyPort, drawbridge, roughTerrain, RockWall,
            empty1, empty2, empty3, empty4, Field;
    //buttons. Bsent means the button that you press when you have sent the data to stuart.
    Button BToAuto, BBack, BSent;
    //whether the robot is Blue or red
    RadioButton RBBlue, RBRed;
    //empty string array that we put data into using a file downloaded onto the tablet. Will explain later in code.
    String teams[];
    //these tell you what your name is and the team you are on.
    TextView Name, Robot;
    //this is declaring a filereader to read the CSV (comma seperated values) file that we store the teams in.
    FileReader fr;
    //another arrayadapter to use for the teams spinner
    ArrayAdapter adapter;
    //context basically means "where in the code am I?" we don't give this a value yet because we want to have this in the OnCreate
    Context context;
    //Used to set the editText text to what the user input, which went into SharedPreferences. Earlier SP put it into Global. See the recurrence?
    String MN = "" + Global.data.MatchNum;
//Same as before, just setContentView sets it to the corresponding Layout.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_setup);
//This is where we initialize context. This means the current class. We could set another context by typing RandomClass.class.
        context = this;
        //this removes the keyboard at the beginning until they click on the matchNum EditText
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        //Increments  the matchesSinceCollect variable which is used in naming the file. This will become important later.
        Global.data.matchesSinceCollect++;
        //Initializing the intent for the NotYou button to activate.
        ITBack = new Intent(this, ScoutSignIn.class);
        //Initializing everything declared above.
        shovel = (ImageView) findViewById(R.id.IMGcheval_de_frise);
        portcullis = (ImageView) findViewById(R.id.IMGporticullis);
        moat = (ImageView) findViewById(R.id.IMGmoat);
        ramparts = (ImageView) findViewById(R.id.IMGramparts);
        sallyPort = (ImageView) findViewById(R.id.IMGsally_port);
        drawbridge = (ImageView) findViewById(R.id.IMGdrawpbridge);
        roughTerrain = (ImageView) findViewById(R.id.IMGrough_terrain);
        RockWall = (ImageView) findViewById(R.id.IMGstone_wall);
        empty1 = (ImageView) findViewById(R.id.IMGdefense1);
        RBBlue = (RadioButton) findViewById(R.id.RBBlue);
        RBRed = (RadioButton) findViewById(R.id.RBRed);
        empty2 = (ImageView) findViewById(R.id.IMGdefense2);
        empty3 = (ImageView) findViewById(R.id.IMGdefense3);
        empty4 = (ImageView) findViewById(R.id.IMGdefense4);
        Field = (ImageView) findViewById(R.id.IMGWholeFieldSetup);
        BSent = (Button) findViewById(R.id.BSent);
        BBack = (Button) findViewById(R.id.BNotMe);
        BToAuto = (Button) findViewById(R.id.BToAutonomous);
        sTeams = (Spinner) findViewById(R.id.STeam);
        MatchNum = (EditText) findViewById(R.id.ETMatchNum);
        Name = (TextView) findViewById(R.id.TVNameSetup);
        Robot = (TextView) findViewById(R.id.TVRobotSetup);
        Name.setText(Global.data.getName());
        Robot.setText(Global.data.getROB());
        //Setting Matchnum to the text taken from Global.data above.
        MatchNum.setText(MN);
        //Example Team #s to prevent error. Try and find out which one doesn't fit.
        teams = new String[]{"Team #", "148", "254", "1114", "1678", "1987", "2826"};
        //Try/Catch to catch errors if there isn't a file so android doesn't crash and scream at you.
        //Keep in mind that the try only happens if there is no error. If there is an error, it goes onto the catch.
        try {
            fr = new FileReader(Environment.getExternalStorageDirectory() + "/team_names.csv");
            //System.out.println is used for debugging. If the code passed thru here, and the tablet is hooked up, you'll know.
            System.out.println("hello " + Environment.getExternalStorageDirectory() + "/team_names.csv");
            //Scanner basically reads thru strings and finds stuff
            Scanner in = new Scanner(fr);
            //this is where we delete new lines. \n represents a new line because android studio doesn't care about whitespace (space/enter)
            in.useDelimiter("\n");
            //Makes a new arraylist of strings that is blank
            ArrayList<String> teamlist = new ArrayList<String>();
            //adds the entry that pops up without someone clicking on the spinner.
            teamlist.add("Team #");
            //This basically says after each newline in the CSV, make a new entry. But only do this while there is still stuff left in the arraylist
            while (in.hasNext()) {
                String team = in.next().trim();
                teamlist.add(team);
            }
            //finishes the scanner.
            in.close();

            teams = teamlist.toArray(teams);
            //sets the old example array to the new array that was just created
            //this is what happens if there is no file called 'team_names.csv'
        } catch (FileNotFoundException e) {
            //basically tells you that you don't have the file.
            Toast t = Toast.makeText(this,
                    "File not found!", Toast.LENGTH_LONG);
            t.show();
        }
        //initializes the adapter.
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, teams);
        //sets the type of look the adapter uses when creating items for the spinner.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //sets the adapter. well, duh.
        sTeams.setAdapter(adapter);
        //initializes the intent so you can go forward
        ITAuto = new Intent(this, Autonomous.class);
        //New OCL
        View.OnClickListener listen = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //See method below. This basically gets rid of all the tints on the original template obstacles.
                undoTints();
                //switch/case to determine what was clicked.
                switch (v.getId()) {
                    case R.id.IMGcheval_de_frise:
                        //sets obstacle to a number that will be found in a switch/case statement later. This corresponds to the CDF
                        obstacle = 0;
                        //Tints the obstacle. undoTints undoes this, if that wasn't clear.
                        shovel.setColorFilter(Color.argb(204, 255, 255, 128));
                        //exits switch/case
                        break;
                    //same here
                    case R.id.IMGporticullis:
                        obstacle = 1;
                        portcullis.setColorFilter(Color.argb(204, 255, 255, 128));
                        break;
                    //and here
                    case R.id.IMGmoat:
                        obstacle = 2;
                        moat.setColorFilter(Color.argb(204, 255, 255, 128));
                        break;
                    //and until I say again.
                    case R.id.IMGramparts:
                        obstacle = 3;
                        ramparts.setColorFilter(Color.argb(204, 255, 255, 128));
                        break;
                    case R.id.IMGsally_port:
                        obstacle = 4;
                        sallyPort.setColorFilter(Color.argb(204, 255, 255, 128));
                        break;
                    case R.id.IMGdrawpbridge:
                        obstacle = 5;
                        drawbridge.setColorFilter(Color.argb(204, 255, 255, 128));
                        break;
                    case R.id.IMGrough_terrain:
                        obstacle = 6;
                        roughTerrain.setColorFilter(Color.argb(204, 255, 255, 128));
                        break;
                    case R.id.IMGstone_wall:
                        obstacle = 7;
                        RockWall.setColorFilter(Color.argb(204, 255, 255, 128));
                        break;
                    //Now it's different.
                    case R.id.IMGdefense1:
                        /*Checks whether red or blue radiobuttons are checked. This is here because I originally
                         *wrote the application with the intent of having you put in all the obstacles on the field,
                         * not just the side you were on. I realized this was impractical, but it kind of set up most
                         * the structure of my code. You can see R(anynumber)obs basically means Red obstacle in (that number's) postition.
                         * Next time I'll think it thru better. Sorry!
                         */
                        //nested if statement
                        if (RBBlue.isChecked() || RBRed.isChecked()) {
                            if (Global.data.getROB().matches("Red")) {
                                //obstacleToString basically takes the obstacle image in the position, converts it to a name, and then spits out the name.
                                Global.data.setB2Obs(obstacleToString());
                            } else {
                                Global.data.setR2Obs(obstacleToString());
                            }
                        } else {
                            //Won't let you click the empty box without clicking the radioButton for red or blue.
                            Toast.makeText(getApplicationContext(), "Please Select Red or Blue",
                                    Toast.LENGTH_LONG).show();
                            return;
                        }
                        //sets the blank image to the image that was tapped before using the setImage method below.
                        empty1.setImageResource(setImage());
                        //just so you can't tap on the empties twice without tapping a seperate obstacle template
                        obstacle = -1;
                        break;
                    //same here
                    case R.id.IMGdefense2:
                        if (RBBlue.isChecked() || RBRed.isChecked()) {
                            if (Global.data.getROB().matches("Red")) {
                                Global.data.setB3Obs(obstacleToString());
                            } else {
                                Global.data.setR3Obs(obstacleToString());
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Please Select Red or Blue",
                                    Toast.LENGTH_LONG).show();
                            return;
                        }
                        empty2.setImageResource(setImage());
                        obstacle = -1;
                        break;
                    //and here
                    case R.id.IMGdefense3:
                        if (RBBlue.isChecked() || RBRed.isChecked()) {
                            if (Global.data.getROB().matches("Red")) {
                                Global.data.setB4Obs(obstacleToString());
                            } else {
                                Global.data.setR4Obs(obstacleToString());
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Please Select Red or Blue",
                                    Toast.LENGTH_LONG).show();
                            return;
                        }
                        empty3.setImageResource(setImage());
                        obstacle = -1;
                        break;
                    //and here.
                    case R.id.IMGdefense4:
                        if (RBBlue.isChecked() || RBRed.isChecked()) {
                            if (Global.data.getROB().matches("Red")) {
                                Global.data.setB5Obs(obstacleToString());
                            } else {
                                Global.data.setR5Obs(obstacleToString());
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Please Select Red or Blue",
                                    Toast.LENGTH_LONG).show();
                            return;
                        }
                        empty4.setImageResource(setImage());
                        obstacle = -1;
                        break;
                    //RadioButton Blue. If you play on the blue side, you are looking at the red half.
                    case R.id.RBBlue:
                        //setROB basically is global storing red or blue.
                        Global.data.setROB("Blue");
                        //sets the field to the half you are looking at so as to reduce confusion.
                        Field.setImageResource(R.drawable.red_half);
                        //puts in the data that the user already entered, so as to not waste precious time. Only if applicable, because it's cleared after every full cycle.
                        if (Global.data.getR2Obs() != null) {
                            empty1.setImageResource(SetImageSetup(Global.data.getR2Obs()));
                            empty2.setImageResource(SetImageSetup(Global.data.getR3Obs()));
                            empty3.setImageResource(SetImageSetup(Global.data.getR4Obs()));
                            empty4.setImageResource(SetImageSetup(Global.data.getR5Obs()));
                        }
                        break;
                    //same, but for red.
                    case R.id.RBRed:
                        Global.data.setROB("Red");
                        Field.setImageResource(R.drawable.blue_half);
                        if (Global.data.getB2Obs() != null) {
                            empty1.setImageResource(SetImageSetup(Global.data.getB2Obs()));
                            empty2.setImageResource(SetImageSetup(Global.data.getB3Obs()));
                            empty3.setImageResource(SetImageSetup(Global.data.getB4Obs()));
                            empty4.setImageResource(SetImageSetup(Global.data.getB5Obs()));
                        }
                        break;
                    //Next button.
                    case R.id.BToAutonomous:
                        //error checking. Please pick a team.
                        if (sTeams.getSelectedItem().toString().matches("Team #")) {
                            Toast.makeText(getApplicationContext(), "Team Team # doesn't exist.",
                                    Toast.LENGTH_LONG).show();
                            return;
                        }
                        //error checking. Please pick a team.
                        if (Global.data.getROB() == null) {
                            Toast.makeText(getApplicationContext(), "Please Select Red or Blue",
                                    Toast.LENGTH_LONG).show();
                            return;
                        }
                        //This is a popup with an OK and Cancel button.
                        //we use our context here.
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        //just to make sure that people placed defenses correctly and didn't accidentally make a mistake.
                        builder.setMessage("Did you octuple check that the defenses are placed correctly?")
                                //positiveButtton = yes.
                                .setPositiveButton("Yes, quit nagging", new DialogInterface.OnClickListener() {
                                    //for when the button is clicked
                                    public void onClick(DialogInterface dialog, int id) {
                                        //sends the data entered to Global.
                                        if (MatchNum.getText().toString().matches("")) {
                                            Global.data.setMatch(Global.data.MatchNum);
                                        } else {
                                            Global.data.setMatch(Integer.parseInt(MatchNum.getText().toString()));
                                            Global.data.MatchNum = Integer.parseInt(MatchNum.getText().toString());
                                        }
                                        //For naming. This comes in the comments activity.
                                        if (Global.data.matchesSinceCollect == 1) {
                                            Global.data.firstMatch = Global.data.getMatch();
                                        }
                                        Global.data.setTeam(sTeams.getSelectedItem().toString());
                                        //Enters all the sharedpreferences data into the sharedprefrences object. Saves even when you leave the app.
                                        preferences = getSharedPreferences("myPreferences", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.putBoolean("NotYou", false);
                                        editor.putLong("SaveTime", System.currentTimeMillis());
                                        editor.putString("ScoutName", Global.data.getName());
                                        editor.putInt("MatchNum", Global.data.getMatch());
                                        editor.putString("Robot", Global.data.getROB());
                                        editor.apply();
                                        //errorChecking
                                        if (Global.data.getROB() == null) {
                                            Toast.makeText(getApplicationContext(), "Please Select Red or Blue",
                                                    Toast.LENGTH_LONG).show();
                                            return;
                                        }
                                        //Finally goes to next
                                        if (empty1.getDrawable() != null && empty2.getDrawable() != null && empty3.getDrawable() != null && empty4.getDrawable() != null) {
                                            startActivity(ITAuto);
                                            return;
                                            //but only if all the images are filled in.
                                        } else {
                                            Toast.makeText(getApplicationContext(), "the outer works can't have an empty spot.",
                                                    Toast.LENGTH_LONG).show();
                                            return;
                                        }
                                    }
                                })
                                //just in case the user changes their mind.
                                .setNegativeButton("Uh, no...", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        return;
                                    }
                                });
                        // Create the AlertDialog object and return it
                        builder.create().show();
                        break;
                    //Goes back
                    case R.id.BNotMe:
                        preferences = getSharedPreferences("myPreferences", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        //this is because we don't want stuff being automatically inputted in the scoutSignIn activity if the user clicked not you. Backspacing is a pain.
                        editor.putBoolean("NotYou", true);
                        editor.commit();
                        //Goes back.
                        startActivity(ITBack);
                        break;
                    //This is so we make a new file. We don't want duplicate data. Remember, this app appends onto the same file until you tell it not to.
                    case R.id.BSent:
                        //another error checking popup
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                        builder1.setMessage("Are you sure you sent the data to Stuart?")
                                .setPositiveButton("Yes, quit nagging", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //makes a new file. You'll see in the file naming process. Files in android only refer to a location, not a physical file until you actually make the file.
                                        Global.data.OldFile = new File("");
                                        //more resetting. You'll see later in Comments.
                                        Global.data.matchesSinceCollect = 0;
                                        //Lets you know that you were successful in creating a new file.
                                        Toast.makeText(getApplicationContext(), "Thanks for sending the data!",
                                                Toast.LENGTH_LONG).show();
                                        return;
                                    }
                                })
                                .setNegativeButton("Uh, no...", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //just a reminder.
                                        Toast.makeText(getApplicationContext(), "So you didn't send the data? Do it regularly!",
                                                Toast.LENGTH_LONG).show();
                                        return;
                                    }
                                });
                        //you gotta show the alertDialog.
                        builder1.show();
                }
            }


        };
        //Setting all these things so that it's recognized when they are clicked. You gotta remember to do this. I forget literally every time!
        shovel.setOnClickListener(listen);
        portcullis.setOnClickListener(listen);
        moat.setOnClickListener(listen);
        ramparts.setOnClickListener(listen);
        sallyPort.setOnClickListener(listen);
        drawbridge.setOnClickListener(listen);
        roughTerrain.setOnClickListener(listen);
        RockWall.setOnClickListener(listen);
        empty1.setOnClickListener(listen);
        empty2.setOnClickListener(listen);
        empty3.setOnClickListener(listen);
        empty4.setOnClickListener(listen);
        BToAuto.setOnClickListener(listen);
        BBack.setOnClickListener(listen);
        BSent.setOnClickListener(listen);
        RBBlue.setOnClickListener(listen);
        RBRed.setOnClickListener(listen);
    }
    //end of the OnCreate Class. From here on, these are activated whenever something happens.
//For setting the images from blank to the template that was just clicked on.
    public int setImage() {
        switch (obstacle) {
            /*the numbers correspond to a template. Up in the OCL, you'll see that it uses setImageResource
              * which takes a drawable (image included in the app) and we give it this method, which incidentally
              * returns a drawable.
             */
            //Breaks are commented out because they're unneccessary, and I was told to comment out instead of delete, which you should always do.
            case 0:
                return R.drawable.cheval_de_frise;
            //break;
            case 1:
                return R.drawable.porticullis;
            //break;
            case 2:
                return R.drawable.moat;
            //break;
            case 3:
                return R.drawable.ramparts;
            //break;
            case 4:
                return R.drawable.sally_port;
            //break;
            case 5:
                return R.drawable.drawbridge;
            //break;
            case 6:
                return R.drawable.rough_terrain;
            //break;
            case 7:
                return R.drawable.stone_wall;
            //break;

        }
        /*Android needs assurance that this function will always return an int. (For obscure reasons, drawables are ints. Don't worry about it.
         *This is why we have zero here, just because android doesn't know that our switch/case will always return something. This is just
         *to prevent errors.
         */
        return 0;
    }
//Give it an in that correspond to an image, and it will spit out the words that correspond to that image/int.
    public String obstacleToString() {
        switch (obstacle) {
            case 0:
                return "Cheval de Frise";
            case 1:
                return "Portcullis";
            case 2:
                return "Moat";
            case 3:
                return "Ramparts";
            case 4:
                return "Sally Port";
            case 5:
                return "Drawbridge";
            case 6:
                return "Rough Terrain";
            case 7:
                return "Rock Wall";

        }
        //Like before, android needs assurance.
        return "";
    }
//Undoes the tints. clearColorFilter makes the tint revert to default, which is nothing.
    public void undoTints() {
        shovel.clearColorFilter();
        portcullis.clearColorFilter();
        moat.clearColorFilter();
        ramparts.clearColorFilter();
        sallyPort.clearColorFilter();
        drawbridge.clearColorFilter();
        roughTerrain.clearColorFilter();
        RockWall.clearColorFilter();
    }
//Sets the image if the user already gave the data. The string is given when you call the method, and taken from Global.data.
    public int SetImageSetup(String Defense) {
        switch (Defense) {
            case "Cheval de Frise":
                return R.drawable.cheval_de_frise;
            case "Portcullis":
                return R.drawable.porticullis;
            case "Moat":
                return R.drawable.moat;
            case "Ramparts":
                return R.drawable.ramparts;
            case "Sally Port":
                return R.drawable.sally_port;
            case "Drawbridge":
                return R.drawable.drawbridge;
            case "Rough Terrain":
                return R.drawable.rough_terrain;
            case "Rock Wall":
                return R.drawable.stone_wall;
        }
        //Needs assurance. I was bored. I probably wrote this part at 2 in the morning when i was supposed to be sleeping. Sorry mom. Sorry guys.
        return 420;
    }
}
//Tada!