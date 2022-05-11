package com.example.ahmad.hakimi;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class SuggestionFirstPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion_first_page);
        BottomNavigationViewEx bottomNavigationView = (BottomNavigationViewEx) findViewById(R.id.bnve) ;
        // BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1D024D")));

    }

    public void addAgent(View view) {
     startActivity(new Intent(SuggestionFirstPage.this,Suggestion.class));

    }

    public void addADvice(View view) {
        startActivity(new Intent(SuggestionFirstPage.this,improve_program.class));
    }
}
