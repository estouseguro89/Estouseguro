package com.example.claudiolinhares.estouseguro;

import android.content.ClipData;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.claudiolinhares.estouseguro.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView texto_fonte, arraste_esq, arraste_dir, contatos_text,cadeado, text_menu,ok,min, dispositivo, dispositivo2;
    Button ver_relatorio,menu_button, contatos_button;
    ImageView image_view,image_view_direito, estouseguro_background, estouseguro2_background,button1, button2,b;
    LinearLayout linear_estouseguro;
    View view;

    boolean panic;

    private static final String LOGCAT = null;

    float dX, dY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        panic = false;

        texto_fonte = (TextView) findViewById(R.id.texto_fonte);
        Typeface font = Typeface.createFromAsset(getAssets(),"impact.ttf");
        texto_fonte.setTypeface(font);

        ok = (TextView) findViewById(R.id.ok);
        min = (TextView) findViewById(R.id.min);
        ok.setTypeface(font);
        min.setTypeface(font);
        min.setVisibility(View.INVISIBLE);

        dispositivo = (TextView) findViewById(R.id.dispositivo);
        dispositivo2 = (TextView) findViewById(R.id.dispositivo2);

        b = (ImageView) findViewById(R.id.button);

        linear_estouseguro = (LinearLayout) findViewById(R.id.linear_estouseguro);

        texto_fonte = (TextView) findViewById(R.id.texto_fonte);
        cadeado = (TextView) findViewById(R.id.cadeado);


        button1 = (ImageView) findViewById(R.id.button1);
        button2 = (ImageView) findViewById(R.id.button2);


        MyButtonsListener buttonsListener = new MyButtonsListener();
        button1.setOnDragListener(buttonsListener);
        button2.setOnDragListener(buttonsListener);

        ver_relatorio = (Button) findViewById(R.id.ver_relatorio);
        ver_relatorio.setTypeface(font);

        arraste_esq = (TextView) findViewById(R.id.arraste_esq);
        arraste_esq.setTypeface(font);

        arraste_dir = (TextView) findViewById(R.id.arraste_dir);
        arraste_dir.setTypeface(font);

        menu_button = (Button) findViewById(R.id.menu_button);
        menu_button.setTypeface(font);

        text_menu = (TextView) findViewById(R.id.text_menu);
        text_menu.setTypeface(font);

        contatos_button = (Button) findViewById(R.id.contatos_button);

        contatos_text = (TextView) findViewById(R.id.contatos_text);
        contatos_text.setTypeface(font);

        view = (View) findViewById(R.id.view);


        estouseguro_background = (ImageView) findViewById(R.id.estouseguro_background);

        estouseguro2_background = (ImageView) findViewById(R.id.estouseguro2_background);


        findViewById(R.id.button).setOnTouchListener(new MyTouchListener());

        desenhaSetas("Esquerdo");

        //Desenhando pentagono

        int x = 800;

        Bitmap pallet = Bitmap.createBitmap(x, 450, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(pallet);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor("#035666"));

        /*
         x = 200; y = 200;
        halfWidth = x / 2;

        path = new Path();
        path.moveTo(x, y );
        path.lineTo(x , y - y);
        path.lineTo(x + x, y - y);
        path.lineTo(x + x, y );
        path.lineTo(x + x - halfWidth, y + halfWidth);
        path.close();
        */

        int y = 200;
        int z = 100;
        int halfWidth = x / 2;

        /*path = new Path();
        path.moveTo(0, 0 );
        path.lineTo(x , 0);
        path.lineTo(x - halfWidth, y - z);
        path.close();
        */

        int deslocamento = 340;

        Path path = new Path();
        path.moveTo(0, deslocamento );
        path.lineTo(0  , - deslocamento);
        path.lineTo(x , 0);
        path.lineTo(x , deslocamento);
        path.lineTo(x - halfWidth, y - z + deslocamento);
        path.close();

        canvas.drawPath(path, paint);

        //Ajustar para o tamanho da tela

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(pallet,width,600,true);

        estouseguro_background.setImageBitmap(scaledBitmap);


        x = 800;

        pallet = Bitmap.createBitmap(x, 450, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(pallet);

        y = 200;
        z = 100;
        halfWidth = x / 2;

        deslocamento = 350;

        path = new Path();
        path.moveTo(0, deslocamento );
        path.lineTo(0  , - deslocamento);
        path.lineTo(x , 0);
        path.lineTo(x , deslocamento);
        path.lineTo(x - halfWidth, y - z + deslocamento);
        path.close();

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor("#034251"));


        canvas.drawPath(path, paint);

        scaledBitmap = Bitmap.createScaledBitmap(pallet,width,650,true);

        estouseguro2_background.setImageBitmap(scaledBitmap);

        linear_estouseguro.setVerticalGravity(Gravity.CENTER_HORIZONTAL);

        Integer in = (int) scaledBitmap.getHeight()/8;

        linear_estouseguro.setPadding(0,in,0,0);

        //Fim pentagono

        //Implementando action dos botões
        ver_relatorio.setOnClickListener(this);
        menu_button.setOnClickListener(this);
        contatos_button.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.ver_relatorio: {
                System.out.println("ver relatorio");
                break;
            }
            case  R.id.menu_button: {
                System.out.println("menu button");
                break;
            }
            case  R.id.contatos_button: {
                System.out.println("contatos button");
                break;
            }
            //.... etc
        }
    }

    private static final String TAG = "Drag Operation";

    private final class MyButtonsListener implements View.OnDragListener {
        //VERDE e VERMELHO

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int dragAction = event.getAction();

            switch (dragAction) {

                case DragEvent.ACTION_DRAG_STARTED:
                    if(v.getId() == button1.getId())
                        v.setBackgroundResource(R.drawable.round_button1);
                    if(v.getId() == button2.getId())
                        v.setBackgroundResource(R.drawable.round_button2);
                    return true;
                case DragEvent.ACTION_DRAG_ENTERED:
                    if(v.getId() == button1.getId())
                        v.setBackgroundResource(R.drawable.round_button1_hover);
                    if(v.getId() == button2.getId())
                        v.setBackgroundResource(R.drawable.round_button2_hover);
                    return true;
                case DragEvent.ACTION_DRAG_LOCATION:
                    //Log.i(TAG, "Location: "+event.getX()+":"+event.getY());
                    return true;
                case DragEvent.ACTION_DRAG_EXITED:
                    if(v.getId() == button1.getId())
                        v.setBackgroundResource(R.drawable.round_button1);
                    if(v.getId() == button2.getId())
                        v.setBackgroundResource(R.drawable.round_button2);
                case DragEvent.ACTION_DRAG_ENDED:
                    return true;
                case DragEvent.ACTION_DROP:

                    if (v.getId() == button1.getId()) {
                        v.setBackgroundResource(R.drawable.round_button1);

                        //System.out.println("Dropped Successfully + id:" + v.getId());
                        if (panic) {
                            paginaPanico(false);
                            return false;
                        }
                    } else if (v.getId() == button2.getId()) {
                        v.setBackgroundResource(R.drawable.round_button2);

                        //System.out.println("Dropped Successfully + id:" + v.getId());
                        if (!panic) {
                            paginaPanico(true);
                            return false;
                        }
                    }

            }
            return false;
        }
    }


    public void desenhaSetas(String lado)
    {

        image_view = (ImageView) findViewById(R.id.image_view);
        image_view_direito = (ImageView) findViewById(R.id.image_view_direito);

        if(lado.equals("Esquerdo")) {
            arraste_esq.setVisibility(View.VISIBLE);
            arraste_dir.setVisibility(View.INVISIBLE);
        }
        else{
            arraste_esq.setVisibility(View.INVISIBLE);
            arraste_dir.setVisibility(View.VISIBLE);
        }

        int strokePaint = 15;

        //Desenha as setas
        Bitmap pallet = Bitmap.createBitmap(170, 80, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(pallet);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokePaint);

        if(lado.equals("Esquerdo")) {
            paint.setColor(Color.parseColor("#97CB00"));
        }
        else{
            paint.setColor(Color.parseColor("#FA3100"));
        }


        int halfWidth = 30;

        Path path = new Path();

        int x = 150, y = 10;
        if(lado.equals("Direito")) {
            x = 0;
        }

        path.moveTo(x + 10, y); // Top
        if(lado.equals("Esquerdo")) {
            path.lineTo(x - halfWidth, y + halfWidth); // Bottom left
        }
        else{
            path.lineTo(x + halfWidth+20, y + halfWidth); // Bottom left
        }
        path.lineTo(x + 10, y + 60); // Bottom right

        canvas.drawPath(path, paint);

        path = new Path();

        if(lado.equals("Esquerdo")) {
            x = x - 55;
        }
        else{
            x = x + 55;
        }


        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokePaint);

        paint.setColor(Color.parseColor("#EE9C1E"));

        path.moveTo(x + 10, y); // Top
        if(lado.equals("Esquerdo")) {
            path.lineTo(x - halfWidth, y + halfWidth); // Bottom left
        }
        else{
            path.lineTo(x + halfWidth+20, y + halfWidth); // Bottom left
        }
        path.lineTo(x + 10, y + 60); // Bottom right

        canvas.drawPath(path, paint);


        if(lado.equals("Esquerdo")) {
            x = x - 55;
        }
        else{
            x = x + 55;
        }

        path = new Path();

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokePaint);

        if(lado.equals("Esquerdo")) {
            paint.setColor(Color.parseColor("#FA3100"));
        }
        else{
            paint.setColor(Color.parseColor("#97CB00"));
        }


        path.moveTo(x + 10, y); // Top
        if(lado.equals("Esquerdo")) {
            path.lineTo(x - halfWidth, y + halfWidth); // Bottom left
        }
        else{
            path.lineTo(x + halfWidth+20, y + halfWidth); // Bottom left
        }
        path.lineTo(x + 10, y + 60); // Bottom right

        canvas.drawPath(path, paint);

        int srcWidth = pallet.getWidth();
        int srcHeight = pallet.getHeight();
        int dstWidth = (int)(srcWidth*0.7f);
        int dstHeight = (int)(srcHeight*0.7f);
        Bitmap dstBitmap = Bitmap.createScaledBitmap(pallet, dstWidth, dstHeight, true);

        if(lado.equals("Esquerdo")) {
            image_view.setImageBitmap(dstBitmap);
            image_view_direito.setImageResource(0);


        }
        else{
            image_view_direito.setImageBitmap(dstBitmap);
            image_view.setImageResource(0);
        }


        //Fim desenhando setas

    }

    private final class MyTouchListener implements View.OnTouchListener {

        public boolean onTouch(View view, MotionEvent event) {

            if (event.getAction() == MotionEvent.ACTION_DOWN) {

                ClipData data = ClipData.newPlainText("", "");

                //ContextCompat.getDrawable(getApplicationContext(), R.drawable.round_button);

                //ImageDragShadowBuilder shadowBuilder = new ImageDragShadowBuilder(view,ContextCompat.getDrawable(getApplicationContext(), R.drawable.round_button));
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                //b.setVisibility(View.INVISIBLE);
                return true;
            }
            else {
                return false;
            }
        }


    }


    private final class MyNewTouchListener implements View.OnTouchListener {


        int prevX,prevY;

        public boolean onTouch(View v, MotionEvent event) {


            final ConstraintLayout.LayoutParams par=(ConstraintLayout.LayoutParams)v.getLayoutParams();
            switch(event.getAction())
            {
                case MotionEvent.ACTION_MOVE:
                {
                    par.topMargin+=(int)event.getRawY()-prevY;
                    prevY=(int)event.getRawY();

                    prevX=(int)event.getRawX();
                    v.setLayoutParams(par);
                    return true;
                }
                case MotionEvent.ACTION_UP:
                {
                    par.topMargin+=(int)event.getRawY()-prevY;

                    v.setLayoutParams(par);
                    return true;
                }
                case MotionEvent.ACTION_DOWN:
                {
                    prevX=(int)event.getRawX();
                    prevY=(int)event.getRawY();
                    par.bottomMargin=-2*v.getHeight();

                    v.setLayoutParams(par);
                    return true;
                }
            }
            return false;
        }


    }




    /*private void refreshView(){
        view.setVisibility(View.GONE);
        view.setVisibility(View.VISIBLE);
    }*/



    private final void paginaPanico(boolean change_state){

        if(change_state)
        {
            //MODO PANICO
            panic = true;
            b.setBackgroundResource(R.drawable.panic_round_button);
            texto_fonte.setText("ESTOU EM RISCO");
            cadeado.setBackgroundResource(R.drawable.cadeado_vermelho);

            ok.setText("15");
            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) ok.getLayoutParams();
            mlp.setMargins(0, 0, 0, 50);
            ViewGroup.MarginLayoutParams mlp2 = (ViewGroup.MarginLayoutParams) min.getLayoutParams();
            mlp2.setMargins(0, 70, 0, 0);
            min.setVisibility(View.VISIBLE);

            dispositivo.setBackgroundResource(R.drawable.ic_gps);
            dispositivo2.setText("Seu GPS está sendo monitorado");
            dispositivo2.setTextColor(Color.parseColor("#FFFFFF"));

            desenhaSetas("Direito");
        }
        else
        {
            //MODO NORMAL
            panic = false;
            b.setBackgroundResource(R.drawable.round_button);
            texto_fonte.setText("ESTOU SEGURO");
            cadeado.setBackgroundResource(R.drawable.cadeado_verde);

            ok.setText("OK");
            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) ok.getLayoutParams();
            mlp.setMargins(0, 0, 0, 0);
            ViewGroup.MarginLayoutParams mlp2 = (ViewGroup.MarginLayoutParams) min.getLayoutParams();
            mlp2.setMargins(0, 0, 0, 0);
            min.setVisibility(View.INVISIBLE);

            dispositivo.setBackgroundResource(R.drawable.ic_dispositivo);
            dispositivo2.setText("Dispositivo bluetooth conectado");
            dispositivo2.setTextColor(Color.parseColor("#9DFC00"));

            desenhaSetas("Esquerdo");
        }

    }


}
