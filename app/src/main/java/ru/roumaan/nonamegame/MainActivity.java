package ru.roumaan.nonamegame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EgyptCampaignPlayer egypt = new EgyptCampaignPlayer(this, 50000, 30000, 12000, 100, 1.2, 10);
        setContentView(egypt);
    }
}
