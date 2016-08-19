package com.example.davidreetz.firststronghold2016scoutingapp;
//Import ALL THE STUFFS

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
//Extending activity class. We will do this A LOT.
public class Autonomous extends Activity {
    //Checkboxes. Below is commented out because I didn't need it.
    CheckBox HiGoalAuto, LoGoalAuto, MissHiAuto, MissLoAuto;
    //CheckBox DeadAuto;
    //RadioButtons for StartLocations
    RadioButton SpyBot, StartLoc1, StartLoc2, StartLoc3, StartLoc4, StartLoc5, NoShow;
    //Textviews for displaying the state of an obstacle
    TextView Obs1Auto, Obs2Auto, Obs3Auto, Obs4Auto, Obs5Auto;
    //Next Button
    Button ToTeleop;
    //Intent for nextButton
    Intent ITToTeleop;
    //Empty ImageViews to put what the user entered in the last activity so as to reduce confusion
    ImageView Empty1, Empty2, Empty3, Empty4, Field, LoBar;
    //Counts tech fouls. Duh.
    Counter TechFoulsAuto;
    //Used later. Remember my derp when I made this all about Red or Blue? Oops.
    String RedorBlue = Global.data.getROB();
    /*LocationManager is used to check and uncheck RadioButtons when one is checked. Android has something like
     *this, but it only works in a straight down line. This is custom and you can see the code (commented of course)
     *in LocationManager.java. Daryl wrote this one, so its better than the rest of the code.
     */
    //Final means that its value can't be changed.
    final LocationManager start = new LocationManager();
    //TechFoulsTotal is used to store the variable stored in the counter for tech fouls
    int TechFoulsTotal;
    //Error Chekkin'
    int ImpossiBoxes = 0;
    int ImpossiDefense;
    //Used later. Declared but not initialized.
    Context context;

    @Override
    //OnCreate Method. As you would see in every activity.
    protected void onCreate(Bundle savedInstanceState) {
        //Uses savedInstanceState to restore activity to previous state. We don't have this set up.
        super.onCreate(savedInstanceState);
        //Ties it to the XML
        setContentView(R.layout.activity_autonomous);
        //Turns off Wifi
        WifiManager wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(true);
        wifiManager.setWifiEnabled(false);
        //Initializing Context
        context = this;
        //Initializing the Views declared above. Yowza!
        HiGoalAuto = (CheckBox) findViewById(R.id.CBShotHiGoalAuto);
        LoGoalAuto = (CheckBox) findViewById(R.id.CBShotLowGoalAuto);
        MissHiAuto = (CheckBox) findViewById(R.id.CBMissHiAuto);
        MissLoAuto = (CheckBox) findViewById(R.id.CBMissLoAuto);
        SpyBot = (RadioButton) findViewById(R.id.RBSpyBot);
        StartLoc1 = (RadioButton) findViewById(R.id.RBPositionLeft);
        StartLoc2 = (RadioButton) findViewById(R.id.RBPositionMidLeft);
        StartLoc3 = (RadioButton) findViewById(R.id.RBPositionMid);
        StartLoc4 = (RadioButton) findViewById(R.id.RBPositionMidRight);
        StartLoc5 = (RadioButton) findViewById(R.id.RBPositionRight);
        //This was troubleshooting because on the old HP tablets, the RBs wouldn't show up, so we thought they were behind other stuff. I forgot to take it out.
        StartLoc1.bringToFront();
        StartLoc2.bringToFront();
        StartLoc3.bringToFront();
        StartLoc4.bringToFront();
        StartLoc5.bringToFront();
        //Initializing more stuff declared above.
        NoShow = (RadioButton) findViewById(R.id.RBNoShow);
        Obs1Auto = (TextView) findViewById(R.id.Obstacle1Auto);
        Obs2Auto = (TextView) findViewById(R.id.Obstacle2Auto);
        Obs3Auto = (TextView) findViewById(R.id.Obstacle3Auto);
        Obs4Auto = (TextView) findViewById(R.id.Obstacle4Auto);
        Obs5Auto = (TextView) findViewById(R.id.BObstacle5Auto);
        ToTeleop = (Button) findViewById(R.id.BToTeleop);
        TechFoulsAuto = (Counter) findViewById(R.id.TechFoulsAuto);
        Empty1 = (ImageView) findViewById(R.id.IMGEmpty1Auto);
        Empty2 = (ImageView) findViewById(R.id.IMGEmpty2Auto);
        Empty3 = (ImageView) findViewById(R.id.IMGEmpty3Auto);
        Empty4 = (ImageView) findViewById(R.id.IMGEmpty4Auto);
        LoBar = (ImageView) findViewById(R.id.IMGLoBarAuto);
        Field = (ImageView) findViewById(R.id.imgHalfFieldAuto);
        //DeadAuto = (CheckBox)findViewById(R.id.CBDeadAuto);
        //These actually were behind other stuff. This is actually necessary.
        Obs1Auto.bringToFront();
        Obs2Auto.bringToFront();
        Obs3Auto.bringToFront();
        Obs4Auto.bringToFront();
        Obs5Auto.bringToFront();
        //To understand this, you have to look at the LocationManager class. start is the LM object.
        start.add(SpyBot, "0");
        start.add(StartLoc1, "1");
        start.add(StartLoc2, "2");
        start.add(StartLoc3, "3");
        start.add(StartLoc4, "4");
        start.add(StartLoc5, "5");
        start.add(NoShow, "");
        //Initializing the intent
        ITToTeleop = new Intent(this, Teleop.class);
        //Settting the OCL. You actually can do this above the OCL.
        LoBar.setOnClickListener(listen1);
        Empty1.setOnClickListener(listen1);
        Empty2.setOnClickListener(listen1);
        Empty3.setOnClickListener(listen1);
        Empty4.setOnClickListener(listen1);
        ToTeleop.setOnClickListener(listen2);
        //Setting the images. to what the user input before. ROB(redOrBlue) remenants galore. See the Global class. That's where my main mistake was.
        switch (RedorBlue) {
            case "Red":
                Empty1.setImageResource(SetImageAuto(Global.data.getB2Obs()));
                Empty2.setImageResource(SetImageAuto(Global.data.getB3Obs()));
                Empty3.setImageResource(SetImageAuto(Global.data.getB4Obs()));
                Empty4.setImageResource(SetImageAuto(Global.data.getB5Obs()));
                Field.setImageResource(R.drawable.blue_half);
                //Debug
                System.out.println("hello");
                break;
            case "Blue":
                Empty1.setImageResource(SetImageAuto(Global.data.getR2Obs()));
                Empty2.setImageResource(SetImageAuto(Global.data.getR3Obs()));
                Empty3.setImageResource(SetImageAuto(Global.data.getR4Obs()));
                Empty4.setImageResource(SetImageAuto(Global.data.getR5Obs()));
                Field.setImageResource(R.drawable.red_half);
                //Debug
                System.out.println("Goodbye");
                break;
        }
    }
//For setting the image. Gets the string as an argument and returns the image that corresponds to it.
    public int SetImageAuto(String Defense) {
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
        //So android doesn't yell at me.
        return 999;
    }
//2 OCLs in this Class, one for the defenses so I didn't have to do a seperate Switch/Case for each one. They all do the same thing pretty much.
    //There are better ways to do this. Don't follow my example here. This is the normal one, listen1 is the defenses.
    final View.OnClickListener listen2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //ErrorChecking.
            ImpossiBoxes = 0;
            ImpossiDefense = 0;
            if (LoGoalAuto.isChecked()) {
                ImpossiBoxes++;
            }
            if (HiGoalAuto.isChecked()) {
                ImpossiBoxes++;
            }
            if (MissLoAuto.isChecked()) {
                ImpossiBoxes++;
            }
            if (MissHiAuto.isChecked()) {
                ImpossiBoxes++;
            }
            if (!Obs1Auto.getText().toString().matches("No Interaction")) {
                ImpossiDefense++;
            }
            if (!Obs2Auto.getText().toString().matches("No Interaction")) {
                ImpossiDefense++;
            }
            if (!Obs3Auto.getText().toString().matches("No Interaction")) {
                ImpossiDefense++;
            }
            if (!Obs4Auto.getText().toString().matches("No Interaction")) {
                ImpossiDefense++;
            }
            if (!Obs5Auto.getText().toString().matches("No Interaction")) {
                ImpossiDefense++;
            }
            if (ImpossiBoxes > 1) {
                Toast.makeText(getApplicationContext(), "Robots can't do more than one thing with the same ball.",
                        Toast.LENGTH_LONG).show();
                return;
            }
            System.out.println("Hello 02");
            if (start.getCheckedButtonName().matches("none") && !NoShow.isChecked()) {
                Toast.makeText(getApplicationContext(), "Even a rookie team has to start somewhere...",
                        Toast.LENGTH_LONG).show();
                return;
            }
            System.out.println("hello 01");
            if (ImpossiDefense > 1) {
                Toast.makeText(getApplicationContext(), "Robots can't cross more than one defense during auto.",
                        Toast.LENGTH_LONG).show();
                return;
            }
            //ErrorChecking end. Wow, that was a lot of work at 2:30 in the morning.
            //All the hellos are for searching in the logcat. I can search hello and see how far it got before crashing.
            System.out.println("hello 1");
            //Sets all the data in Global to what the user entered here.
            Global.data.setAutoLow(LoGoalAuto.isChecked());
            Global.data.setAutoHigh(HiGoalAuto.isChecked());
            System.out.println("Hello 2");
            //See LocationManager
            Global.data.setStartPos(start.getCheckedButtonName());
            //If something is is checked (Checkbox, RadioButton)
            Global.data.setAutoMissLow(MissLoAuto.isChecked());
            Global.data.setAutoMissHigh(MissHiAuto.isChecked());
            //Global.data.setDieAuto(DeadAuto.isChecked());
            System.out.println("Hello 3");
            ObstacleInteraction();
            System.out.println("Hello 4");
            //GetCount is in the counter class. See that for more details.
            TechFoulsTotal = TechFoulsAuto.getCount();
            Global.data.setTechFouls(TechFoulsAuto.getCount());
            //Global.data.DeadAuto = Global.data.getDieAuto();
            System.out.println("hello 5");
            //For if the robot didn't show. This was bad code. I should have gone and edited how I put NoShow into start (the LM)
            //but instead I put the number in blank and entered it here. Bad example. This was because I originally had the startPositions in number format
            //rather than words.
            if (NoShow.isChecked()) {
                Global.data.setStartPos("No Show");
            }
            //Using that intent tho...
            startActivity(ITToTeleop);
        }
    };
    //Another OCL
    final View.OnClickListener listen1 = new View.OnClickListener() {
        @Override
        //SwitchCase to determine what Obstacle
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.IMGLoBarAuto:
                    setObsText(Obs1Auto);
                    break;
                case R.id.IMGEmpty1Auto:
                    setObsText(Obs2Auto);
                    break;
                case R.id.IMGEmpty2Auto:
                    setObsText(Obs3Auto);
                    break;
                case R.id.IMGEmpty3Auto:
                    setObsText(Obs4Auto);
                    break;
                case R.id.IMGEmpty4Auto:
                    setObsText(Obs5Auto);
            }
        }
    };
//Method that uses other methods to determine the text of a textView, but while referencing an empty imageView.
// There are more efficient ways to do this.
    public void ObstacleInteraction() {
        switch (RedorBlue) {
            //See ObstacleRead. This determines which obstalce was clicked.
            case "Red":
                ObstacleRead(Obs1Auto, "Low Bar");
                ObstacleRead(Obs2Auto, Global.data.getB2Obs());
                ObstacleRead(Obs3Auto, Global.data.getB3Obs());
                ObstacleRead(Obs4Auto, Global.data.getB4Obs());
                ObstacleRead(Obs5Auto, Global.data.getB5Obs());
                break;
            case "Blue":
                ObstacleRead(Obs1Auto, "Low Bar");
                ObstacleRead(Obs2Auto, Global.data.getR2Obs());
                ObstacleRead(Obs3Auto, Global.data.getR3Obs());
                ObstacleRead(Obs4Auto, Global.data.getR4Obs());
                ObstacleRead(Obs5Auto, Global.data.getR5Obs());
        }
    }
//Checks the text of each textView.
    public void ObstacleRead(TextView textView, String name) {
        switch (textView.getText().toString()) {
            case "Reached":
                Global.data.setAutoReach(1);
                Global.data.setAutoCross(0);
                break;
            case "Crossed":
                Global.data.setAutoCross(1);
                Global.data.setAutoReach(0);
                break;
            case "Stuck":
                //we want to know what obstacles they got stuck on in auto, we don't much care about reaching and crossing. other than
                //that they did it.
                Global.data.setAutoStuck(name);

                break;
        }
    }
//My signature cycle thru text options, so it can be read at the end. Its really satisfying for some reason.
    public void setObsText(TextView tv) {
        switch (tv.getText().toString()) {
            case "No Interaction":
                tv.setText("Reached");
                break;
            case "Reached":
                tv.setText("Crossed");
                break;
            case "Crossed":
                tv.setText("Stuck");
                //So it shows up as stuck on the next activity.
                Global.data.Stuckified = true;
                break;
            case "Stuck":
                tv.setText("No Interaction");
                //So it doesn't show up as stuck on the next activity.
                Global.data.Stuckified = false;
        }
    }
}

