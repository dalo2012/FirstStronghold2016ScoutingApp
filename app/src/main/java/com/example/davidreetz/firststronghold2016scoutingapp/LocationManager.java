package com.example.davidreetz.firststronghold2016scoutingapp;

import android.view.View;
import android.widget.RadioButton;

import java.util.ArrayList;

/**
 * Created by Daryl on 6/25/2015.
 */
public class LocationManager {
    private ArrayList<RadioButton> locations;
    private ArrayList<String> places;
    private View.OnClickListener listen, toCall;
    private View toPass;
    private String checkedButtonName = "";

    public LocationManager() {
        locations = new ArrayList<>();
        places = new ArrayList<>();
        listen = new View.OnClickListener() {
            public void onClick(View v) {
                for (int i = 0; i < locations.size(); i++) {
                    if (locations.get(i) != v) {
                        locations.get(i).setChecked(false);
                    } else {
                        checkedButtonName = (places.get(i));
                        Global.startLoc = (places.get(i));
                    }
                }
                if (toCall != null) {
                    toCall.onClick(v);
                }
            }
        };
    }

    public void add(RadioButton newLoc, String place) {
        locations.add(newLoc);
        places.add(place);
        newLoc.setOnClickListener(listen);
    }

    public void setOnClickListener(View.OnClickListener listen) {
        toCall = listen;
    }

    public void setOnClickListener(View.OnClickListener listen, View Pass) {
        toCall = listen;
        toPass = Pass;
    }

    public String getCheckedButtonName() {
        if (checkedButtonName.matches("")) {
            return "none";
        } else {
            return checkedButtonName;
        }

    }
}
