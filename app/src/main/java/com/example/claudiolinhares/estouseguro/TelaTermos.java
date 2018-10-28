package com.example.claudiolinhares.estouseguro;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.claudiolinhares.estouseguro.database.AppDatabase;
import com.example.claudiolinhares.estouseguro.database.User;

import static com.example.claudiolinhares.estouseguro.database.AppDatabase.getAppDatabase;

public class TelaTermos extends AppCompatActivity implements View.OnClickListener{

    TextView text_config;
    Button menu_button,fechar_menu;
    AppDatabase db;
    String cpf;
    private AlertDialog alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_termos);

        //Pega a intent de outra activity
        Intent it = getIntent();

        //Recuperei a string da outra activity
        cpf = it.getStringExtra("CPF");


        text_config = (TextView) findViewById(R.id.text_config);
        menu_button = (Button) findViewById(R.id.menu_button);
        fechar_menu = (Button) findViewById(R.id.fechar_menu);

        //Altera as Fontes
        Typeface font = Typeface.createFromAsset(getAssets(),"impact.ttf");
        text_config.setTypeface(font);

        menu_button.setOnClickListener(this);
        fechar_menu.setOnClickListener(this);

        db = getAppDatabase(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.fechar_menu: {
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("CPF", cpf);
                startActivityForResult(intent, 0);
                overridePendingTransition(0,0); //0 for no animation
                //startActivity(new Intent(this, TelaMenu.class));
                break;
            }
            case  R.id.menu_button: {
                Intent intent = new Intent(this, TelaMenu.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("CPF", cpf);
                startActivityForResult(intent, 0);
                overridePendingTransition(0,0); //0 for no animation
                //startActivity(new Intent(this, TelaMenu.class));
                break;
            }
        }

    }

}
