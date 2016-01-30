package ru.roumaan.nonamegame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GamePlayer egypt = new GamePlayer(this,new StandartBackground(this), new StandartButtons(this), new StandartBoard(this), 50000);
        setContentView(egypt);
    }
}
