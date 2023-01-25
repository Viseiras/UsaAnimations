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

    //Declaramos todos los animators
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
    //declaramos todos los valores de pestañas (para hacer el deslizar)
    float x1,x2,y1,y2;


    //Iniciamos la página principal de la app
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setteamos el fondo de la pantalla principal por defecto e inciamos el linear layout que iremos usando
        LinearLayout ll = new LinearLayout(this);
        //Setteamos el linear layout a vertical para que la información se añada hacía abajo
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setBackgroundResource(R.drawable.background_degradado_principal);

        //Setteamos la animación de la banyera por defecto
        ImageView imageView= new ImageView(this);
        imageView.setBackgroundResource(R.drawable.animacion_banyera);

        //Iniciamos la animación para que nada más abrirlo esté funcionando
        animacionbanyera=(AnimationDrawable) imageView.getBackground();
        animacionbanyera.start();
        //Declaramos el alto y ancho de nuestro linear layout para que ocupe un tamaño adecuado (Si es un móvil más pequeño se vería diferente)
        int ancho = 1300;
        int alto = 1000;
        //Setteamos los parametros del linear layout
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ancho, alto);
        imageView.setLayoutParams(params);
        //Añadimos la animación de la bañera en el linear layout
        ll.addView(imageView);

        //Seteamos el textView de la Temperatura
        TextView tx = new TextView(this);
        tx.setText(R.string.temperatura);
        tx.setPadding(60,20,60,20);
        tx.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        //Añadimos el textview al linearlayout
        ll.addView(tx);

        //Creamos la progressbar (de una clase custom que tenemos abajo creada), con el atributo progressBarStyleHorizontal para que avance de izquierda a derecha (problema, solo empieza desde 0)
        ProgressBar pb = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        //Inicializamos el progressDrawable que nos permite añadir una animación de progreso a la barra
        Drawable d = new ProgressDrawable();
        //Le añadimos la animación del progreso a la propia barra
        pb.setProgressDrawable(d);

        pb.setPadding(20, 20, 20, 0);

        //Añadimos la barra al linear layout
        ll.addView(pb);

        //Este método nos permite notificar cuando se cambia la barra y en que posiciones
        SeekBar.OnSeekBarChangeListener l = new SeekBar.OnSeekBarChangeListener() {
            //Cuando paras de mover la barra y la sueltas la animación del fondo se detiene dejando el fondo quieto
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                animacionFondo.stop();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            //Cuando cambiamos el proceso reacciona cambiando el color de la barra las posiciones que se haya movido
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //Aquí caluclamos el progreso total menos el actual, por ejemplo 0+4 nos da que se ha movido 4 casillas a la derecha
                int newProgress = pb.getMax() * progress / seekBar.getMax();
                //Lo loggeamos para tener esa información para posibles debbugs
                Log.d(TAG, "onProgressChanged " + newProgress);
                pb.setProgress(newProgress);
                //Cuando la barra se encuentre a menos de 5 y moviendose se realizará la animación predominante a azul
                if(progress<5)
                {
                    ll.setBackgroundResource(R.drawable.animacion_degradado_frio);
                }
                //Cuando la barra se encuentre a más de 5 y moviendose se realizará la animación predominante a rojo
                else if(progress>5)
                {
                    ll.setBackgroundResource(R.drawable.animacion_degradado_caliente);
                }
                //Setteamos la animación de fondo a la uqe haya salido antes
                animacionFondo=(AnimationDrawable) ll.getBackground();
                //Iniciamos la animación del fondo
                animacionFondo.start();
                //Si el progreso es 0 cambia la animación principal por la animación fría
                if(progress==0)
                {
                    imageView.setBackgroundResource(R.drawable.animacion_fria);
                    animacionbanyera=(AnimationDrawable) imageView.getBackground();
                    animacionbanyera.start();
                }
                //Si está a menos de 5 debería cambiar el color del botón en transición a azul
                if(progress<5 && progress>0) {

                    int colorFrom = (int)getColor(R.color.fondof);
                    int colorTo = (int)getColor(R.color.fondoi);
                    ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
                    colorAnimation.setDuration(2000); // millisegundos
                    colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                        //Declaramos la animación de degradado del textbox de la temperatura
                        @Override
                        public void onAnimationUpdate(ValueAnimator animator) {
                            tx.setBackgroundColor((int) animator.getAnimatedValue());
                        }

                    });
                    colorAnimation.start();
                }
                //Si es 5 justo debería dejar por defecto el fondo normal (Pero no funciona muy bien)
                if(progress==5)
                {
                    tx.setBackgroundResource(R.drawable.background_degradado);
                }
                //Si el progreso se encuentra a más de 5 la textbox debería cambiar en un degradado a rojo
                if(progress>5 && progress<10) {

                    int colorFrom = (int)getColor(R.color.fondoi);
                    int colorTo = (int)getColor(R.color.fondof);
                    ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
                    colorAnimation.setDuration(2000); // millisegundos
                    colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                        //Declaramos la animación de degradado del textbox
                        @Override
                        public void onAnimationUpdate(ValueAnimator animator) {
                            tx.setBackgroundColor((int) animator.getAnimatedValue());
                        }

                    });
                    //Iniciamos la animación
                    colorAnimation.start();
                }
                //Si la barra se encuentra en 10 hará la animación de quemarse en lugar de la animación de ducharse
                if(progress==10)
                {
                    imageView.setBackgroundResource(R.drawable.animacion_caliente);
                    animacionbanyera=(AnimationDrawable) imageView.getBackground();
                    animacionbanyera.start();
                }
                //Si la barra se encuentra en 10 hará la animación de tener frío en lugar de la animación de ducharse
                if(progress>1 && progress<10)
                {
                    imageView.setBackgroundResource(R.drawable.animacion_banyera);
                    animacionbanyera=(AnimationDrawable) imageView.getBackground();
                    animacionbanyera.start();
                }
            }
        };

        //Setteamos los tipos de progresos que podría haber (Solo he usado del 0 al 10 pero se puede usar el que prefieras)
        int[] maxs = {4, 10, 60, 110};

            //Creamos el SeekBar y le añadimos el máximo el listener del cambio y cosas visuales como el padding
            SeekBar sb = new SeekBar(this);
            sb.setMax(maxs[1]);
            sb.setOnSeekBarChangeListener(l);
            sb.setPadding(60, 20, 60, 0);
            //Añadimos la seekbar al Linear Layout
            ll.addView(sb);

        //Creamos la animación del AmongUs Bailarín
        ImageView im = new ImageView(this);
        im.setBackgroundResource(R.drawable.animacion_baile);
        im.setPadding(20,50,20,20);
        animacionbaile=(AnimationDrawable) im.getBackground();

        //Declaramos los dos botones que irán influyendo en el baile
        Button btnBaile = new Button(this);
        Button btnSeAcaboLaFiesta = new Button(this);
        //Setteamos el botón para acabar el baile en invisible
        btnSeAcaboLaFiesta.setVisibility(View.INVISIBLE);

        //Setteamos el texto del botón
        btnBaile.setText(R.string.btnBaile);
        btnBaile.setTextSize(20);
        btnBaile.setPadding(20,50,20,20);
        //Hacemos un onClickListener que nos inicie la animación
        btnBaile.setOnClickListener(v-> {
                animacionbaile.start();
                //Aquí en el color animator hacemos una sucesión de colores redonda para que acabe en el color que inicia
                animatorColor = ObjectAnimator.ofInt(btnBaile, "backgroundColor", RED, BLUE, YELLOW, GREEN, BLACK, PURPLE,WHITE);
                animatorColor.setDuration(1000);
                animatorColor.setEvaluator(new ArgbEvaluator());
                //animatorColor.setRepeatCount(ValueAnimator.INFINITE); es lo mismo que el repeat -1
                animatorColor.setRepeatCount(-1);
                //Iniciamos la animación
                animatorColor.start();
                //Hacemos el botón de parar la vista visible y por lo tanto accesible a ser pulsado
                btnSeAcaboLaFiesta.setVisibility(View.VISIBLE);
        });

        //Setteamos el botón para parar la fiesta y sus propiedades
        btnSeAcaboLaFiesta.setText(R.string.btnParaBaile);
        btnSeAcaboLaFiesta.setTextSize(20);
        btnSeAcaboLaFiesta.setPadding(20,50,20,20);
        //Hacemos un onClickListener para que pare la animación
        btnSeAcaboLaFiesta.setOnClickListener(v-> {
            //Para la animación para que deje de ejecutarse el otro comando
            animacionbaile.stop();
            //como la duración anterior es infinita aquí la ponemos a 0 para que pare (sino no se detiene aunque se haya parado con stop)
            animatorColor.setDuration(0);
            //Hacemos una pasada más por la misma razón de que es infinita y sino no afecta
            animatorColor.setRepeatCount(1);
            animatorColor.start();
            //Volvemos a hacer el botón de parar invisible y por lo tanto inaccesible (Que sea invisible permite que no se pueda pulsar)
            btnSeAcaboLaFiesta.setVisibility(View.INVISIBLE);
        });
        //Añadimos al linear Layout los dos botones y la imageView de la animación
        ll.addView(btnBaile);
        ll.addView(btnSeAcaboLaFiesta);
        ll.addView(im);

        //Añadimos el contenido del linear layout a la vista
        setContentView(ll);
    }

    //Hacemos un onTouchEvent que nos deja hacer scroll a la pantalla de izquierda o derecha
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
                    //Nos abre otro activity si scrolleamos a la derecha
                    Intent i = new Intent(MainActivity.this, VectorActivity.class);
                    startActivity(i);
                }
                //Movimiento a la izquierda
                if(x1<x2)
                {
                    //No hace nada
                }
                break;
        }
        return false;
    }

}

//Creamos la clase custom progressdrawable
class ProgressDrawable extends Drawable {
    //Seteamos en cuantos segmentos se separará la barra de progreso
    private static final int NUM_RECTS = 10;
    //Hacemos un paint para que nos deje pintar las celdas cuando se mueva
    Paint mPaint = new Paint();

    //Cuando se cambie el nivel eliminaremos le estado actual
    @Override
    protected boolean onLevelChange(int level) {
        invalidateSelf();
        return true;
    }

    //Cuando no haya vista creada (es decir el primer movimiento o esté eliminado) pintaremos de nuevo la barra con el progreso actual, aquí denominado level
    @Override
    public void draw(Canvas canvas) {
        //Sacamos el level para pintar los cuadros respectivos
        int level = getLevel();
        //Miramos lo que ocupa cada rectangulo para saber cuanto pintar
        Rect b = getBounds();
        //Sacamos el ancho del rectangulo para pintarlo
        float width = b.width();
        //Separamos en cuadrados/rectangulos iguales en el tamaño que se haya designado
        for (int i = 0; i < NUM_RECTS; i++) {
            float left = width * i / NUM_RECTS;
            float right = left + 0.9f * width / NUM_RECTS;

            //Pintamos la parte de la barra que toque de cada color según el level que se encuentre la seekbar
            mPaint.setColor((i + 1) * 10000 / NUM_RECTS <= level? 0xffFF8080:0xff8080FF); //izquierda es el azul y derecha es el rojo
            //Hacemos pintado vectorial para que nos dibuje un rectangulo del color especificado según el level
            canvas.drawRect(left, b.top, right, b.bottom, mPaint);
        }
    }

    //Estos métodos son de utilidad por si hacen falta (este hace transparente)
    @Override
    public void setAlpha(int alpha) {
    }

    //Este pone un filtro para daltonicos
    @Override
    public void setColorFilter(ColorFilter cf) {
    }

    //Aquí sacamos la opacidad actual por si queremos modificar la opacidad
    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
