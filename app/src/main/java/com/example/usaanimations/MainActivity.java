package com.example.usaanimations;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;


import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    AnimationDrawable animacionbanyera;
    AnimationDrawable animacionbaile;
    AnimationDrawable animacionFondo;
    ObjectAnimator animatorColor;

    //Declaramos las constantes de colores
    private static final int RED = 0xffFF8080;
    private static final int BLUE = 0xff8080FF;
    private static final int YELLOW = 0xffFFFF00;
    private static final int BLACK = 0xff000000;
    private static final int WHITE = 0xffFFFFFF;
    private static final int PURPLE = 0xff800080;
    private static final int GREEN = 0xff00FF00;
    float x1,x2,y1,y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setBackgroundResource(R.drawable.background_degradado_principal);

        ImageView imageView= new ImageView(this);
        imageView.setBackgroundResource(R.drawable.animacion_banyera);

        animacionbanyera=(AnimationDrawable) imageView.getBackground();
        animacionbanyera.start();
        int ancho = 1300;
        int alto = 1000;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ancho, alto);
        imageView.setLayoutParams(params);
        ll.addView(imageView);


        TextView tx = new TextView(this);
        tx.setText(R.string.temperatura);
        tx.setPadding(60,20,60,20);
        tx.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        ll.addView(tx);

        ProgressBar pb = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        Drawable d = new ProgressDrawable();
        pb.setProgressDrawable(d);

        pb.setPadding(20, 20, 20, 0);

        ll.addView(pb);


        SeekBar.OnSeekBarChangeListener l = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                animacionFondo.stop();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int newProgress = pb.getMax() * progress / seekBar.getMax();
                Log.d(TAG, "onProgressChanged " + newProgress);
                pb.setProgress(newProgress);
                if(progress<5)
                {
                    ll.setBackgroundResource(R.drawable.animacion_degradado_frio);
                }
                else if(progress>5)
                {
                    ll.setBackgroundResource(R.drawable.animacion_degradado_caliente);
                }
                animacionFondo=(AnimationDrawable) ll.getBackground();
                animacionFondo.start();
                if(progress==0)
                {
                    imageView.setBackgroundResource(R.drawable.animacion_fria);
                    animacionbanyera=(AnimationDrawable) imageView.getBackground();
                    animacionbanyera.start();
                }
                if(progress<5 && progress>0) {

                    int colorFrom = (int)getColor(R.color.fondof);
                    int colorTo = (int)getColor(R.color.fondoi);
                    ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
                    colorAnimation.setDuration(2000); // milliseconds
                    colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                        @Override
                        public void onAnimationUpdate(ValueAnimator animator) {
                            tx.setBackgroundColor((int) animator.getAnimatedValue());
                        }

                    });
                    colorAnimation.start();
                }
                if(progress==5)
                {
                    tx.setBackgroundResource(R.drawable.background_degradado);
                }
                if(progress>5 && progress<10) {

                    int colorFrom = (int)getColor(R.color.fondoi);
                    int colorTo = (int)getColor(R.color.fondof);
                    ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
                    colorAnimation.setDuration(2000); // milliseconds
                    colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                        @Override
                        public void onAnimationUpdate(ValueAnimator animator) {
                            tx.setBackgroundColor((int) animator.getAnimatedValue());
                        }

                    });
                    colorAnimation.start();
                }
                if(progress==10)
                {
                    imageView.setBackgroundResource(R.drawable.animacion_caliente);
                    animacionbanyera=(AnimationDrawable) imageView.getBackground();
                    animacionbanyera.start();
                }
                if(progress>1 && progress<10)
                {
                    imageView.setBackgroundResource(R.drawable.animacion_banyera);
                    animacionbanyera=(AnimationDrawable) imageView.getBackground();
                    animacionbanyera.start();
                }
            }
        };

        int[] maxs = {4, 10, 60, 110};

            SeekBar sb = new SeekBar(this);
            sb.setMax(maxs[1]);
            sb.setOnSeekBarChangeListener(l);
            sb.setPadding(60, 20, 60, 0);
            ll.addView(sb);

        ImageView im = new ImageView(this);
        im.setBackgroundResource(R.drawable.animacion_baile);
        im.setPadding(20,50,20,20);
        animacionbaile=(AnimationDrawable) im.getBackground();





        Button btnBaile = new Button(this);
        Button btnSeAcaboLaFiesta = new Button(this);
        btnSeAcaboLaFiesta.setVisibility(View.INVISIBLE);

        btnBaile.setText(R.string.btnBaile);
        btnBaile.setTextSize(20);
        btnBaile.setPadding(20,50,20,20);
        btnBaile.setOnClickListener(v-> {
                animacionbaile.start();
                animatorColor = ObjectAnimator.ofInt(btnBaile, "backgroundColor", RED, BLUE, YELLOW, GREEN, BLACK, PURPLE,WHITE);
                animatorColor.setDuration(1000);
                animatorColor.setEvaluator(new ArgbEvaluator());
                //animatorColor.setRepeatCount(ValueAnimator.INFINITE);
                animatorColor.setRepeatCount(-1);
                animatorColor.start();
                btnSeAcaboLaFiesta.setVisibility(View.VISIBLE);
        });

        btnSeAcaboLaFiesta.setText(R.string.btnParaBaile);
        btnSeAcaboLaFiesta.setTextSize(20);
        btnSeAcaboLaFiesta.setPadding(20,50,20,20);
        btnSeAcaboLaFiesta.setOnClickListener(v-> {
            animacionbaile.stop();
            animatorColor.setDuration(0);
            animatorColor.setRepeatCount(1);
            animatorColor.start();
            btnSeAcaboLaFiesta.setVisibility(View.INVISIBLE);
        });
        ll.addView(btnBaile);
        ll.addView(btnSeAcaboLaFiesta);
        ll.addView(im);

        setContentView(ll);
    }


    public boolean onTouchEvent(MotionEvent touchevent){
        //Hacemos una TouchScreen para aplicar movimientos
        switch (touchevent.getAction()){
            //Declaramos posición inicial
            case MotionEvent.ACTION_DOWN:
                x1 = touchevent.getX();
                y1 = touchevent.getY();
                break;
            //Declaramos posición final
            case MotionEvent.ACTION_UP:
                x2 = touchevent.getX();
                y2= touchevent.getY();
                //Movimento a la derecha
                if(x1>x2)
                {
                    Intent i = new Intent(MainActivity.this, VectorActivity.class);
                    startActivity(i);
                }
                //Movimiento a la izquierda
                if(x1<x2)
                {
                }
                break;
        }
        return false;
    }

}

class ProgressDrawable extends Drawable {
    private static final int NUM_RECTS = 10;
    Paint mPaint = new Paint();

    @Override
    protected boolean onLevelChange(int level) {
        invalidateSelf();
        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        int level = getLevel();
        Rect b = getBounds();
        float width = b.width();
        for (int i = 0; i < NUM_RECTS; i++) {
            float left = width * i / NUM_RECTS;
            float right = left + 0.9f * width / NUM_RECTS;

            mPaint.setColor((i + 1) * 10000 / NUM_RECTS <= level? 0xffFF8080:0xff8080FF);
            canvas.drawRect(left, b.top, right, b.bottom, mPaint);
        }
    }

    @Override
    public void setAlpha(int alpha) {
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
