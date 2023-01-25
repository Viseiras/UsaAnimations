package com.example.usaanimations;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class VectorActivity extends AppCompatActivity {

    //Declaramos el vector Drawable
    AnimatedVectorDrawable avd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector);

        //Ponemos la imageView con el id bombillex que se encuentra en el xml
        ImageView iv = findViewById(R.id.bombillex);

        //Setteamos la animación de fondo como el vector animado
        iv.setBackgroundResource(R.drawable.bombillexanimation);

        avd=(AnimatedVectorDrawable) iv.getBackground();
        //Inicalizamos la animación cuando cambie de pestaña
        avd.start();

        TextView tx = findViewById(R.id.Welcome);
        //Hacemos visible el textview de bienvenida
        tx.setAlpha(1);
    }
}