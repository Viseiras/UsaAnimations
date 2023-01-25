package com.example.usaanimations;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class VectorActivity extends AppCompatActivity {

    AnimatedVectorDrawable avd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector);

        ImageView iv = findViewById(R.id.bombillex);

        iv.setBackgroundResource(R.drawable.bombillexanimation);

        avd=(AnimatedVectorDrawable) iv.getBackground();
        avd.start();

        TextView tx = findViewById(R.id.Welcome);
        tx.setAlpha(1);
    }
}