package com.example.usaanimations;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    AnimationDrawable animacionbanyera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = findViewById(R.id.banyera);

        imageView.setBackgroundResource(R.drawable.animacion_banyera);

        animacionbanyera=(AnimationDrawable) imageView.getBackground();

        animacionbanyera.start();
    }
}