package ru.roumaan.nonamegame;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class EgyptCampaignActivity extends AppCompatActivity {

    int time;
    int silverGrade;
    int bronzeGrade;
    int speed;
    double speedMultiplier;
    int symbols;

    public EgyptCampaignActivity(int time, int silverGrade, int bronzeGrade, int speed, double speedMultiplier, int symbols) {
        super();

        this.

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new EgyptCampaignPlayer(this, 25000, 20000, 5000, 1.1));
    }

}