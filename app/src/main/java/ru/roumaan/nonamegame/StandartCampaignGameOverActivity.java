package ru.roumaan.nonamegame;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class StandartCampaignGameOverActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int score = getIntent().getIntExtra("score", 0);
        setContentView(new StandartCampaignGameOver(this, score));
    }
}
