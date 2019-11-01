package com.example.game.Game2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.game.Account;
import com.example.game.BaseActivity;
import com.example.game.R;

public class Riddle2 extends BaseActivity {
    Account account;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2_riddle2);

        account = (Account) getIntent().getSerializableExtra("ac");

        if (account.getCustomization()[0] == 1) {
            getWindow().getDecorView().setBackgroundResource(R.color.background1);
        }
    }

    /** Called when the user taps the "McMurdo Dry Valleys, Antarctica" button */
    public void rightGuess2(View view) {
        account.incrementScore(100, context);
        Intent intent = new Intent(this, Riddle3.class);
        intent.putExtra("ac", account);
        startActivity(intent);
    }

    /** Called when the user taps any other button */
    public void wrongGuess2(View view) {
        account.decrementHitPoints(1, context);
        Intent intent = new Intent(this, Wrong2.class);
        intent.putExtra("ac", account);
        startActivity(intent);
    }
}
