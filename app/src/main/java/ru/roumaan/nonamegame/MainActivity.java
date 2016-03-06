package ru.roumaan.nonamegame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StandardCampaignPlayer egypt = new StandardCampaignPlayer(this, 50000);
        setContentView(egypt);
    }
}
