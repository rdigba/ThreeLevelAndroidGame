package com.example.game.viewLevel.game2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.game.BaseActivity;
import com.example.game.viewLevel.game3.Game3Activity;
import com.example.game.viewLevel.MainActivity;
import com.example.game.R;

public class Win extends BaseActivity {

    /**
     * Text displaying player stats
     */
    TextView lives, scores;

    /**
     * Code to execute when the Activity is created.
     *
     * @param savedInstanceState A Bundle containing possibly previous states of this Activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2_win);

        getWindow().getDecorView().setBackgroundResource(account.getBackground());


        lives = findViewById(R.id.livesText_Win);
        lives.setText(String.valueOf(account.getHitPoints()));

        scores = findViewById(R.id.scoreText_Win);
        scores.setText(String.valueOf(account.getCurrentScore()));
    }

    /** Called when the user taps the "To Game Three" button */
    public void nextGame(View view) {
        Intent intent = new Intent(this, Game3Activity.class);
        startActivity(intent);
    }

    /**
     * Called when the user taps the "To Main Menu" button. Takes User to main menu of the App.
     *
     * @param view The View of this Activity.
     */
    public void toMainMenu(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}