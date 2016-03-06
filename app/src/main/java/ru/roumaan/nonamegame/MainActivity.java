package ru.roumaan.nonamegame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StandartCampaignPlayer egypt = new StandartCampaignPlayer(this, 50000);
        setContentView(egypt);
    }
}
