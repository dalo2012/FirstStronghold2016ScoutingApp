package com.example.davidreetz.firststronghold2016scoutingapp;
//Our most massive and complex activity yet.
//yet again importing stuff to be used
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
//Extending activity. I feel like we have never ever done this before, right?
public class Teleop extends Activity {
    //We have stuckobs set to nothing because they're not stuck on anything yet. We will set it to other stuff later.
    public String StuckObs = "";
    //Buttons. There are lots of them. It helps to have the layout open as well so you can follow along to what I'm talking about. Or
    //you can have the app open on a tablet.
    Button BFail1, BFail2, BFail3, BFail4, BFail5, BEndgame, BFinish, BStuck, BUnStuck, BUndo;
    //Counters for shots and techfouls
    Counter HiGoalTele, LoGoalTele, LoGoalMissTele, HiGoalMissTele, TechFoulsTele, BlockedShotsTele;
    //Checkboxes for events that we want to know about.
    CheckBox DeadTele, TippedTele, Intermittent, BallStuck, Captured, Breached, Defended;
    //Intent to go to comments. This was made at the competition because comments didn't work on the 7" tablets
    Intent ITComments;
    //ImageViews to put previously entered images in
    ImageView Empty1, Empty2, Empty3, Empty4, LoBar1Tele, HalfField;
    //Declaring context and not initializing. Have we ever done this before?
    Context context;
    //TextViews corresponding to obstacles. DummyTV doesn't correspond to anything important, and can't be changed. The thing is,
    // I needed to set the textView to something because the textViews correspond to the state of an obstacle and if you hadn't selected
    //an obstacle and therefore there was no text view for it to reference, android would crash because it can't change the state of
    //a nonexistant text view. I am bad at explaining.
    TextView TVObs1, TVObs2, TVObs3, TVObs4, TVObs5, DummyTV;
    //Crosses to be read at the end and incremented as time goes on.
    int CrossesLoBar = 0;
    int CrossesShovel = 0;
    int CrossesPortcullis = 0;
    int CrossesMoat = 0;
    int CrossesRamparts = 0;
    int CrossesSallyPort = 0;
    int CrossesDrawbridge = 0;
    int CrossesRoughTerrain = 0;
    int CrossesRockWall = 0;
    //Fails to be read at the end and incremented as time goes on.
    int failLowBar, failShovel, failPortcullis, failMoat, failRamparts, failSallyPort, failDrawbridge, failRockWall, failRoughTerrain;
    //Startcrossing and endCrossing are constantly changed and used to calculate totalCrossing by subtracting one from the other.
    //They are longs because it uses System.GetCurrentTimeMillis, and that's a big number because it's counting up from Jan 1, 0000. Yowza!
    long startCrossing = 0;
    long endCrossing = 0;
    //TotalCrossing storage.
    long TotalCrossingLoBar = 0;
    long TotalCrossingShovel = 0;
    long TotalCrossingPortcullis = 0;
    long TotalCrossingMoat = 0;
    long TotalCrossingRamparts = 0;
    long TotalCrossingSallyPort = 0;
    long TotalCrossingDrawbridge = 0;
    long TotalCrossingRoughTerrain = 0;
    long TotalCrossingRockWall = 0;
    //LastTime stores the last End-StartCrossing so you can undo the crossing by subtracting it from the total.
    long lastTime = 0;
    // Set to nothing because we don't want people clicking undo and screwing up data when no obstacle was selected.
    String lastObstacle = "";
    //More stuff so we can detect what button was last pressed for undo.
    Button lastButton;
    //More stuff for undo, so we know what textView to change to "undone".
    TextView lastTextView;
    //For my derp earlier about red and blue.
    String RedorBlue = Global.data.getROB();
//Now that we are done declaring, we can initialize. Oh boy! Finally the onCreate. This is a big class.
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Sets it to the teleop layout. This may or may not be the first time we have used setContentView.
        setContentView(R.layout.activity_teleop);
        //Initializing context. First time ever. sure.
        context = this;
        //Keeps the keyboard from showing up for comments until you click the editText.
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        //Initializing the stuff declared above. It's not like we've done this like 3 times before, not at all.
        BFail1 = (Button) findViewById(R.id.BObstacle1Tele);
        BFail2 = (Button) findViewById(R.id.BObstacle2Tele);
        BFail3 = (Button) findViewById(R.id.BObstacle3Tele);
        BFail4 = (Button) findViewById(R.id.BObstacle4Tele);
        BFail5 = (Button) findViewById(R.id.BObstacle5Tele);
        Breached = (CheckBox) findViewById(R.id.CBBreach);
        Captured = (CheckBox) findViewById(R.id.CBCapture);
        DummyTV = (TextView) findViewById(R.id.TVEndgame);
        TVObs1 = (TextView) findViewById(R.id.TVObs1);
        TVObs2 = (TextView) findViewById(R.id.TVObs2);
        TVObs3 = (TextView) findViewById(R.id.TVObs3);
        TVObs4 = (TextView) findViewById(R.id.TVObs4);
        TVObs5 = (TextView) findViewById(R.id.TVObs5);
        BUndo = (Button) findViewById(R.id.BUndo);
        Defended = (CheckBox) findViewById(R.id.Defended);
        BallStuck = (CheckBox) findViewById(R.id.CBBallStuck);
        HiGoalTele = (Counter) findViewById(R.id.HiGoalTele);
        LoGoalTele = (Counter) findViewById(R.id.LoGoalTele);
        HiGoalMissTele = (Counter) findViewById(R.id.HiGoalMissTele);
        LoGoalMissTele = (Counter) findViewById(R.id.LoGoalMissTele);
        TechFoulsTele = (Counter) findViewById(R.id.TechFoulsTele);
        DeadTele = (CheckBox) findViewById(R.id.CBDeadTele);
        TippedTele = (CheckBox) findViewById(R.id.CBTipped);
        BEndgame = (Button) findViewById(R.id.BEndGameAction);
        BFinish = (Button) findViewById(R.id.FinishButton);
        HalfField = (ImageView) findViewById(R.id.IMGhalfFieldTeleop);
        LoBar1Tele = (ImageView) findViewById(R.id.LoBar1Tele);
        Empty1 = (ImageView) findViewById(R.id.Empty1Tele);
        Empty2 = (ImageView) findViewById(R.id.Empty2Tele);
        Empty3 = (ImageView) findViewById(R.id.Empty3Tele);
        Empty4 = (ImageView) findViewById(R.id.Empty4Tele);
        BlockedShotsTele = (Counter) findViewById(R.id.BlockedShots);
        BStuck = (Button) findViewById(R.id.BStuckTele);
        BUnStuck = (Button) findViewById(R.id.UnStuck);
        Intermittent = (CheckBox) findViewById(R.id.CBIntemittent);
        //Setting lastTextView to DummyTV so it won't get recognized by the Switch/Case later on.
        lastTextView = DummyTV;
        //Sets the counter's count to the Techfouls entered in Auto.
        TechFoulsTele.setCount(Global.data.getTechFouls());
        //New intent for the comments class. I don't think we've ever done this before.
        ITComments = new Intent(this, Comments.class);
        //From if the robot got stuck in auto. Now before you do anything you have to click unstuck.
        if (Global.data.Stuckified) {
            //Red for stuck, green for not.
            BStuck.setBackgroundColor(Color.RED);
            //Just a reminder. People sometimes forget and panic that the app isn't doing anything because they forgot to click unstuck.
            Toast.makeText(getApplicationContext(), "The robot is stuck. Remember.",
                    Toast.LENGTH_LONG).show();
        } else {
            //Green for not stuck.
            BStuck.setBackgroundColor(Color.GREEN);
        }
        //This was hanging behind everything and we couldn't see it. That was a pain.
        BStuck.bringToFront();
        //Same here.
        BUnStuck.bringToFront();
        //Setting the images.
        switch (RedorBlue) {
            case "Red":
                Empty1.setImageResource(SetImageTele(Global.data.getB2Obs()));
                Empty2.setImageResource(SetImageTele(Global.data.getB3Obs()));
                Empty3.setImageResource(SetImageTele(Global.data.getB4Obs()));
                Empty4.setImageResource(SetImageTele(Global.data.getB5Obs()));
                HalfField.setImageResource(R.drawable.blue_half);
                break;
            case "Blue":
                Empty1.setImageResource(SetImageTele(Global.data.getR2Obs()));
                Empty2.setImageResource(SetImageTele(Global.data.getR3Obs()));
                Empty3.setImageResource(SetImageTele(Global.data.getR4Obs()));
                Empty4.setImageResource(SetImageTele(Global.data.getR5Obs()));
                HalfField.setImageResource(R.drawable.red_half);
                break;

        }

//An OCL. Again.
        final View.OnClickListener listen = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Switch/Case to determine what was clicked. Again.
                switch (v.getId()) {
                    case R.id.LoBar1Tele:
                        //ErrorChecking
                        if (!Global.data.Stuckified) {
                            if (lastTextView.getText().toString().matches("Crossing") && lastTextView != TVObs1) {
                                Toast.makeText(getApplicationContext(), "Please cross one obstacle before crossing another.",
                                        Toast.LENGTH_LONG).show();
                                return;
                            }
                            //Uses this method to increment crosses, and take the time, and all those other things that come with crossing.
                            crosses(v, TVObs1);
                            //setting lastObstacle for undo
                            lastObstacle = "Low Bar";
                            //and lastTV.
                            lastTextView = TVObs1;
                        }
                        break;
                    //Similar here to above.
                    case R.id.Empty1Tele:
                        //EC
                        if (!Global.data.Stuckified) {
                            if (lastTextView.getText().toString().equals("Crossing") && lastTextView != TVObs2) {
                                Toast.makeText(getApplicationContext(), "Please cross one obstacle before crossing another.",
                                        Toast.LENGTH_LONG).show();
                                return;
                            }
                            //More of the Crosses method.
                            crosses(v, TVObs2);
                            //Same as before, just checking for redOrBlue.
                            if (RedorBlue.equals("Red")) {
                                lastObstacle = Global.data.getB2Obs();
                            } else {
                                lastObstacle = Global.data.getR2Obs();
                            }
                            lastTextView = TVObs2;
                        }
                        break;
                    //Same here
                    case R.id.Empty2Tele:
                        if (!Global.data.Stuckified) {
                            if (lastTextView.getText().toString().equals("Crossing") && lastTextView != TVObs3) {
                                Toast.makeText(getApplicationContext(), "Please cross one obstacle before crossing another.",
                                        Toast.LENGTH_LONG).show();
                                return;
                            }
                            crosses(v, TVObs3);
                            if (RedorBlue.matches("Red")) {
                                lastObstacle = Global.data.getB3Obs();
                            } else {
                                lastObstacle = Global.data.getR3Obs();
                            }
                            lastTextView = TVObs3;
                        }
                        break;
                    //and here
                    case R.id.Empty3Tele:
                        if (!Global.data.Stuckified) {
                            if (lastTextView.getText().toString().equals("Crossing") && lastTextView != TVObs4) {
                                Toast.makeText(getApplicationContext(), "Please cross one obstacle before crossing another.",
                                        Toast.LENGTH_LONG).show();
                                return;
                            }
                            crosses(v, TVObs4);
                            if (RedorBlue.matches("Red")) {
                                lastObstacle = Global.data.getB4Obs();
                            } else {
                                lastObstacle = Global.data.getR4Obs();
                            }
                            lastTextView = TVObs4;
                        }
                        break;
                    //and here for that matter.
                    case R.id.Empty4Tele:
                        if (!Global.data.Stuckified) {
                            if (lastTextView.getText().toString().equals("Crossing") && lastTextView != TVObs5) {
                                Toast.makeText(getApplicationContext(), "Please cross one obstacle before crossing another.",
                                        Toast.LENGTH_LONG).show();
                                return;
                            }
                            crosses(v, TVObs5);
                            if (RedorBlue.matches("Red")) {
                                lastObstacle = Global.data.getB5Obs();
                            } else {
                                lastObstacle = Global.data.getR5Obs();
                            }
                            lastTextView = TVObs5;
                        }
                        break;
                    //Another one of my famous cycle thru the different texts to be read a the end. This is one of my favorite things to do.
                    case R.id.BEndGameAction:
                        System.out.println(Global.data.Stuckified);
                        if (!Global.data.Stuckified) {
                            switch (BEndgame.getText().toString()) {
                                case "No Action":
                                    BEndgame.setText("Challenged");
                                    break;
                                case "Challenged":
                                    BEndgame.setText("Scaled");
                                    break;
                                case "Scaled":
                                    BEndgame.setText("Rolled Off");
                                    break;
                                case "Rolled Off":
                                    BEndgame.setText("No Action");
                            }
                        }
                        break;
                    case R.id.BStuckTele:
                        //Making sure that they are on an obstacle.
                        if (!Global.data.Stuckified && !lastObstacle.matches("") && lastTextView.getText().toString().matches("Crossing")) {
                            //Telling Global that the robot is stuck, which in turn tells everything else to stop.
                            Global.data.Stuckified = true;
                            //Reminder.
                            Toast.makeText(getApplicationContext(), "The robot is now stuck.",
                                    Toast.LENGTH_LONG).show();
                            //red for stuck.
                            BStuck.setBackgroundColor(Color.RED);
                            //Don't know why I put this here.
                            BStuck.setText("Stuck");
                            //So you can undo a stuck.
                            lastButton = BStuck;

                        }
                        break;
                    //Slightly more complicated than stuck, because it stores the data now.
                    case R.id.UnStuck:
                        //in the event that the robot was stuck in auto and unstuck here, it doesn't put it in the stuck string of all the things it was stuck in.
                        if (Global.data.Stuckified) {
                            if (lastObstacle.matches("")) {
                                Global.data.Stuckified = false;
                                BStuck.setBackgroundColor(Color.GREEN);
                                //For if you clicked stuck in teleop.
                            } else {
                                //Stores the obstacle in a string.
                                StuckObs = StuckObs + lastObstacle + ".";
                                //Tells Global that the robot is no longer stuck
                                Global.data.Stuckified = false;
                                BStuck.setBackgroundColor(Color.GREEN);
                                //Confirmation that your tap did something.
                                Toast.makeText(getApplicationContext(), "The robot is no longer stuck.",
                                        Toast.LENGTH_LONG).show();
                                //A stuck counts as a fail, so we use the fail method as well.
                                fail(lastObstacle);
                                //Making sure we don't set DummyTV's text to 'failed' in case we got stuck in auto.
                                if (lastTextView != DummyTV) {
                                    lastTextView.setText("Failed");
                                }
                                //Resets start/end crossing so that it doesn't screw up the next time they cross.
                                startCrossing = 0;
                                endCrossing = 0;
                                //So that undo knows which thing to undo after this.
                                lastObstacle = "UnStuck";

                            }
                            //Also for undo.
                            lastButton = BUnStuck;
                        }
                        break;
                    case R.id.FinishButton:
                        //debugging
                        System.out.println("hello finish");
                        //Popup box
                        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        //reminder to check your data
                        builder.setMessage("Are you sure ALL the data is correct?")
                                .setPositiveButton("Yes, quit nagging", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //Setting all the stuff in Global
                                        Global.data.setTipped(TippedTele.isChecked());
                                        Global.data.setBallStuck(BallStuck.isChecked());
                                        Global.data.setBreach(Breached.isChecked());
                                        Global.data.setCapture(Captured.isChecked());
                                        Global.data.setIntermittet(Intermittent.isChecked());
                                        Global.data.setDieTele(DeadTele.isChecked());
                                        Global.data.setHigh(HiGoalTele.getCount());
                                        Global.data.setMissHigh(HiGoalMissTele.getCount());
                                        Global.data.setLow(LoGoalTele.getCount());
                                        Global.data.setDefended(Defended.isChecked());
                                        Global.data.setMissLow(LoGoalMissTele.getCount());
                                        Global.data.setTechFouls(TechFoulsTele.getCount());
                                        Global.data.setStuck(StuckObs);
                                        Global.data.setNumCrossesLoBar(CrossesLoBar);
                                        Global.data.setNumCrossesShovel(CrossesShovel);
                                        Global.data.setNumCrossesPortcullis(CrossesPortcullis);
                                        Global.data.setNumCrossesMoat(CrossesMoat);
                                        Global.data.setNumCrossesRamparts(CrossesRamparts);
                                        Global.data.setNumCrossesSallyPort(CrossesSallyPort);
                                        Global.data.setNumCrossesDrawbridge(CrossesDrawbridge);
                                        Global.data.setNumCrossesRoughTerrain(CrossesRoughTerrain);
                                        Global.data.setNumCrossesRockWall(CrossesRockWall);
                                        Global.data.setDefenseBlock(BlockedShotsTele.getCount());
                                        //Converting the total times and number of crosses to an average of seconds. Snazzy.
                                        if (TotalCrossingLoBar != 0) {
                                            Global.data.setTimeLoBar((CrossesLoBar > 0 ? TotalCrossingLoBar / ((double) CrossesLoBar) / 1000 : 0));
                                        }
                                        if (TotalCrossingShovel != 0) {
                                            Global.data.setTimeShovel((CrossesShovel > 0 ? TotalCrossingShovel / ((double) CrossesShovel) / 1000 : 0));
                                        }
                                        if (TotalCrossingPortcullis != 0) {
                                            Global.data.setTimePortcullis((CrossesPortcullis > 0 ? TotalCrossingPortcullis / ((double) CrossesPortcullis) / 1000 : 0));
                                        }
                                        if (TotalCrossingMoat != 0) {
                                            Global.data.setTimeMoat((CrossesMoat > 0 ? TotalCrossingMoat / ((double) CrossesMoat) / 1000 : 0));
                                        }
                                        if (TotalCrossingRamparts != 0) {
                                            Global.data.setTimeRamparts(CrossesRamparts > 0 ? TotalCrossingRamparts / ((double) CrossesRamparts) / 1000 : 0);
                                        }
                                        if (TotalCrossingSallyPort != 0) {
                                            Global.data.setTimeSallyPort((CrossesSallyPort > 0 ? TotalCrossingSallyPort / ((double) CrossesSallyPort) / 1000 : 0));
                                        }
                                        if (TotalCrossingDrawbridge != 0) {
                                            Global.data.setTimeDrawbridge(CrossesDrawbridge > 0 ? TotalCrossingDrawbridge / ((double) CrossesDrawbridge) / 1000 : 0);
                                        }
                                        if (TotalCrossingRoughTerrain != 0) {
                                            Global.data.setTimeRoughTerrain((CrossesRoughTerrain > 0 ? TotalCrossingRoughTerrain / ((double) CrossesRoughTerrain) / 1000 : 0));
                                        }
                                        if (TotalCrossingRockWall != 0) {
                                            Global.data.setTimeRockWall(CrossesRockWall > 0 ? TotalCrossingRockWall / ((double) CrossesRockWall) / 1000 : 0);
                                        }
                                        //More putting stuff in global.
                                        Global.data.setFailLoBar(failLowBar);
                                        Global.data.setFailShovel(failShovel);
                                        Global.data.setFailPortcullis(failPortcullis);
                                        Global.data.setFailMoat(failMoat);
                                        Global.data.setFailRamparts(failRamparts);
                                        Global.data.setFailSallyPort(failSallyPort);
                                        Global.data.setFailDrawbridge(failDrawbridge);
                                        Global.data.setFailRockWall(failRockWall);
                                        Global.data.setFailRoughTerrain(failRoughTerrain);
                                        //Reading the endgame cycler.
                                        switch (BEndgame.getText().toString()) {
                                            case "Challenged":
                                                Global.data.setChallenge(true);
                                                Global.data.setScale(false);
                                                Global.data.setRolledOff(false);
                                                break;
                                            case "Scaled":
                                                Global.data.setChallenge(false);
                                                Global.data.setScale(true);
                                                Global.data.setRolledOff(false);
                                                break;
                                            case "Rolled Off":
                                                Global.data.setChallenge(false);
                                                Global.data.setScale(false);
                                                Global.data.setRolledOff(true);
                                                break;
                                            default:
                                                Global.data.setChallenge(false);
                                                Global.data.setScale(false);
                                                Global.data.setRolledOff(false);
                                                break;
                                        }
                                        //starting yet another activity. Again.
                                        startActivity(ITComments);
                                    }
                                })
                                .setNegativeButton("Uh, no...", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //lets them double check. Exits the warning popup
                                        return;
                                    }
                                });
                        // Create the AlertDialog object and return it
                        builder.create();
                        builder.show();
                        break;
                    //Simple right? WRONG! look at the undo method.
                    case R.id.BUndo:
                        undo(lastObstacle);
                }
            }
        };
        //Yet another bad example. I'm full of them! For all the fail buttons
        final View.OnClickListener listen2 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Casting the view to a button. This tells android that the thing you thought was going to be a view will only be a button
                //and not any other type of view. That way I can use Button's methods.
                Button b = ((Button) v);
                switch (b.getId()) {
                    case R.id.BObstacle1Tele:
                        //Sets the text of the text view and calls the fail method.
                        if (TVObs1.getText().toString().matches("Crossing")) {
                            TVObs1.setText("Failed");
                            fail("Low Bar");

                        } else {
                            //ErrorChecking.
                            Toast.makeText(getApplicationContext(), "The robot wasn't crossing, so it couldn't back out.",
                                    Toast.LENGTH_LONG).show();
                        }
                        break;
                    case R.id.BObstacle2Tele:
                        //Same here, but with redOrBlue
                        if (TVObs2.getText().toString().matches("Crossing")) {
                            TVObs2.setText("Failed");
                            if (RedorBlue.matches("Red")) {
                                fail(Global.data.getB2Obs());
                            } else {
                                fail(Global.data.getR2Obs());
                            }
                            break;
                        } else {
                            Toast.makeText(getApplicationContext(), "The robot wasn't crossing, so it couldn't back out.",
                                    Toast.LENGTH_LONG).show();
                        }
                        break;
                    case R.id.BObstacle3Tele:
                        if (TVObs3.getText().toString().matches("Crossing")) {
                            TVObs3.setText("Failed");
                            if (RedorBlue.matches("Red")) {
                                fail(Global.data.getB3Obs());
                            } else {
                                fail(Global.data.getR3Obs());
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "The robot wasn't crossing, so it couldn't back out.",
                                    Toast.LENGTH_LONG).show();
                        }
                        break;
                    case R.id.BObstacle4Tele:
                        if (TVObs4.getText().toString().matches("Crossing")) {
                            TVObs4.setText("Failed");
                            if (RedorBlue.matches("Red")) {
                                fail(Global.data.getB4Obs());
                            } else {
                                fail(Global.data.getR4Obs());
                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "The robot wasn't crossing, so it couldn't back out.",
                                    Toast.LENGTH_LONG).show();
                        }
                        break;
                    case R.id.BObstacle5Tele:
                        if (TVObs5.getText().toString().matches("Crossing")) {
                            TVObs5.setText("Failed");
                            if (RedorBlue.matches("Red")) {
                                fail(Global.data.getB5Obs());
                            } else {
                                fail(Global.data.getR5Obs());
                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "The robot wasn't crossing, so it couldn't back out.",
                                    Toast.LENGTH_LONG).show();
                        }
                        break;
                    //up until here, this is where it deviates.

                }
            }
        };
        //Set all the OCLs!
        LoBar1Tele.setOnClickListener(listen);
        Empty1.setOnClickListener(listen);
        Empty2.setOnClickListener(listen);
        Empty3.setOnClickListener(listen);
        Empty4.setOnClickListener(listen);
        BEndgame.setOnClickListener(listen);
        BStuck.setOnClickListener(listen);
        BUnStuck.setOnClickListener(listen);
        BFinish.setOnClickListener(listen);
        BFail1.setOnClickListener(listen2);
        BFail2.setOnClickListener(listen2);
        BFail3.setOnClickListener(listen2);
        BFail4.setOnClickListener(listen2);
        BFail5.setOnClickListener(listen2);
        BUndo.setOnClickListener(listen);
    }
//Methods, START!!!!!
    //Yowza, Complicated! Let's break it down.
    public void crosses(View v, TextView t) {
        //Casting the View to an imageView. This is done because you give the method v from the OCL and that's a view, but it's only
        //an imageVeiw because those are the only methods in which this is called. I didn't want to cast it 5 different times.
        ImageView i = ((ImageView) v);
        //This triggers if they have already pressed the image once.
        if (t.getText().toString().matches("Crossing")) {
            //Gets the endCrossing time
            endCrossing = System.currentTimeMillis();
            //ROB madness
            switch (RedorBlue) {
                case "Red":
                    //Switch/Case to find which imageVeiw was clicked.
                    switch (i.getId()) {
                        //SaveObsData uses the data given to save the data in the variables declared before OnCreate.
                        case R.id.LoBar1Tele:
                            saveObsData("Low Bar", TVObs1);
                            break;
                        case R.id.Empty1Tele:
                            saveObsData(Global.data.getB2Obs(), TVObs2);
                            break;
                        case R.id.Empty2Tele:
                            saveObsData(Global.data.getB3Obs(), TVObs3);
                            break;
                        case R.id.Empty3Tele:
                            saveObsData(Global.data.getB4Obs(), TVObs4);
                            break;
                        case R.id.Empty4Tele:
                            saveObsData(Global.data.getB5Obs(), TVObs5);
                            break;

                    }
                    break;
                case "Blue":
                    switch (i.getId()) {
                        case R.id.LoBar1Tele:
                            saveObsData("Low Bar", TVObs1);
                            break;
                        case R.id.Empty1Tele:
                            saveObsData(Global.data.getR2Obs(), TVObs2);
                            break;
                        case R.id.Empty2Tele:
                            saveObsData(Global.data.getR3Obs(), TVObs3);
                            break;
                        case R.id.Empty3Tele:
                            saveObsData(Global.data.getR4Obs(), TVObs4);
                            break;
                        case R.id.Empty4Tele:
                            saveObsData(Global.data.getR5Obs(), TVObs5);
                            break;

                    }
                    break;

            }

//This activates if they haven't pressed the button yet. As you can see, all the magic happens when they press it a second time. This
            //is because it made undo easier, so I could just have it change the text and set the startCrossing to 0.
        } else {
            t.setText("Crossing");
            startCrossing = System.currentTimeMillis();

        }
    }
//Used to take image name from global and use it to set the empty images in tele.
    public int SetImageTele(String Defense) {
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
        //blaze it.
        return 420;
    }
//Used to save the data in the variables declared before oncreate whenever the images are clicked. Called in the crosses method.
    public void saveObsData(String Obs, TextView v) {
        //Gets the subtracted time.
        lastTime = endCrossing - startCrossing;
        switch (Obs) {
            //Finds out which variable to store it in. Then stores the time and increments the crosses.
            case "Low Bar":
                CrossesLoBar = setObsText(CrossesLoBar, TVObs1);
                TotalCrossingLoBar = TotalCrossingLoBar + endCrossing - startCrossing;
                break;
            case "Cheval de Frise":
                CrossesShovel = setObsText(CrossesShovel, v);
                TotalCrossingShovel = TotalCrossingShovel + endCrossing - startCrossing;
                break;
            case "Portcullis":
                CrossesPortcullis = setObsText(CrossesPortcullis, v);
                TotalCrossingPortcullis = TotalCrossingPortcullis + endCrossing - startCrossing;
                break;
            case "Moat":
                CrossesMoat = setObsText(CrossesMoat, v);
                TotalCrossingMoat = TotalCrossingMoat + endCrossing - startCrossing;
                break;
            case "Ramparts":
                CrossesRamparts = setObsText(CrossesRamparts, v);
                TotalCrossingRamparts = TotalCrossingRamparts + endCrossing - startCrossing;
                break;
            case "Sally Port":
                CrossesSallyPort = setObsText(CrossesSallyPort, v);
                TotalCrossingSallyPort = TotalCrossingSallyPort + endCrossing - startCrossing;
                break;
            case "Drawbridge":
                CrossesDrawbridge = setObsText(CrossesDrawbridge, v);
                TotalCrossingDrawbridge = TotalCrossingDrawbridge + endCrossing - startCrossing;
                break;
            case "Rough Terrain":
                CrossesRoughTerrain = setObsText(CrossesRoughTerrain, v);
                TotalCrossingRoughTerrain = TotalCrossingRoughTerrain + endCrossing - startCrossing;
                break;
            case "Rock Wall":
                CrossesRockWall = setObsText(CrossesRockWall, v);
                TotalCrossingRockWall = TotalCrossingRockWall + endCrossing - startCrossing;
                break;
        }
    }

    public int setObsText(int thisCross, TextView t) {
        //Cycling kind of, but doesn't go back to the beginning, just keeps incrementing after once, twice, x times. Returns the value that
        //The robot has crossed the obstacle and back where it was called, it sets this cross to the ammount of times that it was crossed.
        //for each individual obstacle.
        endCrossing = System.currentTimeMillis();
        switch (thisCross) {
            case 0:
                t.setText("Crossed\nOnce");
                return 1;
            case 1:
                t.setText("Crossed\nTwice");
                return 2;
            default:
                t.setText("Crossed\n" + (thisCross + 1) + " Times");
                return thisCross + 1;
        }
    }
//Fail is different for each different obstacle because the confirmation messages are different depending on what was failed on.
    public void fail(String s) {
        switch (s) {
            case "Low Bar":
                //incrementation
                failLowBar++;
                Toast.makeText(getApplicationContext(), "The robot has failed on the low bar.",
                        Toast.LENGTH_LONG).show();
                break;
            case "Cheval de Frise":
                //inc
                failShovel++;
                Toast.makeText(getApplicationContext(), "The robot has failed on the cheval de frise.",
                        Toast.LENGTH_LONG).show();
                break;
            case "Portcullis":
                failPortcullis++;
                Toast.makeText(getApplicationContext(), "The robot has failed on the portcullis.",
                        Toast.LENGTH_LONG).show();
                break;
            case "Moat":
                failMoat++;
                Toast.makeText(getApplicationContext(), "The robot has failed on the moat.",
                        Toast.LENGTH_LONG).show();
                break;
            case "Ramparts":
                failRamparts++;
                Toast.makeText(getApplicationContext(), "The robot has failed on the ramparts.",
                        Toast.LENGTH_LONG).show();
                break;
            case "Sally Port":
                failSallyPort++;
                Toast.makeText(getApplicationContext(), "The robot has failed on the sally port.",
                        Toast.LENGTH_LONG).show();
                break;
            case "Drawbridge":
                failDrawbridge++;
                Toast.makeText(getApplicationContext(), "The robot has failed on the drawbridge.",
                        Toast.LENGTH_LONG).show();
                break;
            case "Rock Wall":
                failRockWall++;
                Toast.makeText(getApplicationContext(), "The robot has failed on the Rock Wall.",
                        Toast.LENGTH_LONG).show();
                break;
            case "Rough Terrain":
                failRoughTerrain++;
                Toast.makeText(getApplicationContext(), "The robot has failed on the rough terrain.",
                        Toast.LENGTH_LONG).show();
                break;
            //end similarity.
        }
    }
//Craziest method I've ever done.
    public void undo(String LastObs) {
        switch (LastObs) {
            //keeps you from pressing undo twice. I couldn't figure out how to undo twice without causing me major headache and
            //tons of errors, so you can only undo once.
            case "Undo":
                Toast.makeText(getApplicationContext(), "You can't undo an undo. What's undone is undone.",
                        Toast.LENGTH_LONG).show();
                break;
            //If you haven't done anyting, you cant undo anything.
            case "":
                Toast.makeText(getApplicationContext(), "Please choose something to undo first.",
                        Toast.LENGTH_LONG).show();
                break;
            //After this, it's mostly similar for each obstacle.
            case "Low Bar":
                //This checks if there was a fail on the low bar
                if (lastTextView.getText().toString().matches("Failed")) {
                    //de-increments the fail on the low bar
                    failLowBar--;
                    //message of confirmation
                    Toast.makeText(getApplicationContext(), "Undone a fail on the low bar.",
                            Toast.LENGTH_LONG).show();
                    //If there wasn't a fail, if they were in the middle of crossing,
                } else if (lastTextView.getText().toString().matches("Crossing")) {
                    //sets the text and forgets about it, because no data change was made. The startCrossing will be overridden next time
                    //they click an obstacle.
                    //this is necessary because it prevents them clicking on the textView again and it counting as the second click, meaning that they undo was meaningless.
                    lastTextView.setText("Undone");
                    Toast.makeText(getApplicationContext(), "Undone. Thy egregious mistake hath been forgiven.",
                            Toast.LENGTH_LONG).show();
                    //if they have already crossed.
                } else {
                    //TotalCrossingLoBar = TotalCrossingLoBar - lastTime
                    //gets rid of the time change
                    TotalCrossingLoBar -= lastTime;
                    //Gets rid of the increment
                    CrossesLoBar--;
                    //Confirmation message
                    Toast.makeText(getApplicationContext(), "Undone. Thy egregious mistake hath been forgiven.",
                            Toast.LENGTH_LONG).show();
                    lastTextView.setText("Undone");
                }

                break;
            case "Cheval de Frise":
                if (lastTextView.getText().toString().matches("Failed")) {
                    failShovel--;
                    Toast.makeText(getApplicationContext(), "Undone a fail on the cheval de frise.",
                            Toast.LENGTH_LONG).show();
                } else if (lastTextView.getText().toString().matches("Crossing")) {
                    lastTextView.setText("Undone");
                    Toast.makeText(getApplicationContext(), "Undone. Thy egregious mistake hath been forgiven.",
                            Toast.LENGTH_LONG).show();
                } else {
                    TotalCrossingShovel -= lastTime;
                    CrossesShovel--;
                    Toast.makeText(getApplicationContext(), "Undone. Thy egregious mistake hath been forgiven.",
                            Toast.LENGTH_LONG).show();
                }
                lastTextView.setText("Undone");
                break;
            case "Portcullis":
                if (lastTextView.getText().toString().matches("Failed")) {
                    failPortcullis--;
                    Toast.makeText(getApplicationContext(), "Undone a fail on the portcullis.",
                            Toast.LENGTH_LONG).show();
                } else if (lastTextView.getText().toString().matches("Crossing")) {
                    lastTextView.setText("Undone");
                    Toast.makeText(getApplicationContext(), "Undone. Thy egregious mistake hath been forgiven.",
                            Toast.LENGTH_LONG).show();
                } else {
                    TotalCrossingPortcullis -= lastTime;
                    CrossesPortcullis--;
                    Toast.makeText(getApplicationContext(), "Undone. Thy egregious mistake hath been forgiven.",
                            Toast.LENGTH_LONG).show();
                }
                lastTextView.setText("Undone");
                break;
            case "Moat":
                if (lastTextView.getText().toString().matches("Failed")) {
                    failMoat--;
                    Toast.makeText(getApplicationContext(), "Undone a fail on the moat.",
                            Toast.LENGTH_LONG).show();
                } else if (lastTextView.getText().toString().matches("Crossing")) {
                    lastTextView.setText("Undone");
                    Toast.makeText(getApplicationContext(), "Undone. Thy egregious mistake hath been forgiven.",
                            Toast.LENGTH_LONG).show();
                } else {
                    TotalCrossingMoat -= lastTime;
                    CrossesMoat--;
                    Toast.makeText(getApplicationContext(), "Undone. Thy egregious mistake hath been forgiven.",
                            Toast.LENGTH_LONG).show();
                }
                lastTextView.setText("Undone");
                break;
            case "Ramparts":
                if (lastTextView.getText().toString().matches("Failed")) {
                    failRamparts--;
                    Toast.makeText(getApplicationContext(), "Undone a fail on the ramparts.",
                            Toast.LENGTH_LONG).show();
                } else if (lastTextView.getText().toString().matches("Crossing")) {
                    lastTextView.setText("Undone");
                    Toast.makeText(getApplicationContext(), "Undone. Thy egregious mistake hath been forgiven.",
                            Toast.LENGTH_LONG).show();
                } else {
                    TotalCrossingRamparts -= lastTime;
                    CrossesRamparts--;
                    Toast.makeText(getApplicationContext(), "Undone. Thy egregious mistake hath been forgiven.",
                            Toast.LENGTH_LONG).show();
                }
                lastTextView.setText("Undone");
                break;
            case "Sally Port":
                if (lastTextView.getText().toString().matches("Failed")) {
                    failSallyPort--;
                    Toast.makeText(getApplicationContext(), "Undone a fail on the sally port.",
                            Toast.LENGTH_LONG).show();
                } else if (lastTextView.getText().toString().matches("Crossing")) {
                    lastTextView.setText("Undone");
                    Toast.makeText(getApplicationContext(), "Undone. Thy egregious mistake hath been forgiven.",
                            Toast.LENGTH_LONG).show();
                } else {
                    TotalCrossingSallyPort -= lastTime;
                    CrossesSallyPort--;
                    Toast.makeText(getApplicationContext(), "Undone. Thy egregious mistake hath been forgiven.",
                            Toast.LENGTH_LONG).show();
                }
                lastTextView.setText("Undone");
                break;
            case "Drawbridge":
                if (lastTextView.getText().toString().matches("Failed")) {
                    failDrawbridge--;
                    Toast.makeText(getApplicationContext(), "Undone a fail on the drawbridge.",
                            Toast.LENGTH_LONG).show();
                } else if (lastTextView.getText().toString().matches("Crossing")) {
                    lastTextView.setText("Undone");
                    Toast.makeText(getApplicationContext(), "Undone. Thy egregious mistake hath been forgiven.",
                            Toast.LENGTH_LONG).show();
                } else {
                    TotalCrossingDrawbridge -= lastTime;
                    CrossesDrawbridge--;
                    Toast.makeText(getApplicationContext(), "Undone. Thy egregious mistake hath been forgiven.",
                            Toast.LENGTH_LONG).show();
                }
                lastTextView.setText("Undone");
                break;
            case "Rock Wall":
                if (lastTextView.getText().toString().matches("Failed")) {
                    failRockWall--;
                    Toast.makeText(getApplicationContext(), "Undone a fail on the Rock Wall.",
                            Toast.LENGTH_LONG).show();
                } else if (lastTextView.getText().toString().matches("Crossing")) {
                    lastTextView.setText("Undone");
                    Toast.makeText(getApplicationContext(), "Undone. Thy egregious mistake hath been forgiven.",
                            Toast.LENGTH_LONG).show();
                } else {
                    TotalCrossingRockWall -= lastTime;
                    CrossesRockWall--;
                    Toast.makeText(getApplicationContext(), "Undone. Thy egregious mistake hath been forgiven.",
                            Toast.LENGTH_LONG).show();
                }
                lastTextView.setText("Undone");
                break;
            case "Rough Terrain":
                if (lastTextView.getText().toString().matches("Failed")) {
                    failRoughTerrain--;
                    Toast.makeText(getApplicationContext(), "Undone a fail on the rough terrain.",
                            Toast.LENGTH_LONG).show();
                } else if (lastTextView.getText().toString().matches("Crossing")) {
                    lastTextView.setText("Undone");
                    Toast.makeText(getApplicationContext(), "Undone. Thy egregious mistake hath been forgiven.",
                            Toast.LENGTH_LONG).show();
                } else {
                    TotalCrossingRoughTerrain -= lastTime;
                    CrossesRoughTerrain--;
                    Toast.makeText(getApplicationContext(), "Undone. Thy egregious mistake hath been forgiven.",
                            Toast.LENGTH_LONG).show();
                }
                lastTextView.setText("Undone");
                break;
        }
        //Undoes a stuck without adding the obstacle to the stuck list
        if (lastButton == BStuck) {
            BStuck.setBackgroundColor(Color.GREEN);
            Global.data.Stuckified = false;
        }
        //makes it so you can't click undo again. Remember at the top?
        lastObstacle = "Undo";
    }
}