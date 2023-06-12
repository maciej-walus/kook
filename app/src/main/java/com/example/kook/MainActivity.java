package com.example.kook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void reload_recipies(View v){
        ConstraintLayout activity_main = findViewById(R.id.mainConstraintLayout);
        LinearLayout categoryLinearLayout = findViewById(R.id.categoryLinearLayout);
        LayoutInflater inflater = LayoutInflater.from(this);
        View inflatedLayout = inflater.inflate(R.layout.widget, categoryLinearLayout, false);
        categoryLinearLayout.addView(inflatedLayout);
    }
}