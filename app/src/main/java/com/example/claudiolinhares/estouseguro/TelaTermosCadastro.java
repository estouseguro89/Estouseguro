package com.example.claudiolinhares.estouseguro;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.claudiolinhares.estouseguro.MainActivity;
import com.example.claudiolinhares.estouseguro.R;
import com.example.claudiolinhares.estouseguro.TelaMenu;
import com.example.claudiolinhares.estouseguro.database.AppDatabase;

import static com.example.claudiolinhares.estouseguro.database.AppDatabase.getAppDatabase;

public class TelaTermosCadastro extends AppCompatActivity implements View.OnClickListener{

    TextView text_config,text_anjo;
    Button fechar_menu;
    AppDatabase db;
    String cpf;
    private AlertDialog alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_termoscadastro);

        text_config = (TextView) findViewById(R.id.text_config);
        text_anjo = (TextView) findViewById(R.id.text_anjo);
        fechar_menu = (Button) findViewById(R.id.fechar_menu);

        //Altera as Fontes
        Typeface font = Typeface.createFromAsset(getAssets(),"impact.ttf");
        text_config.setTypeface(font);
        text_anjo.setTypeface(font);

        fechar_menu.setOnClickListener(this);

        db = getAppDatabase(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.fechar_menu: {
                finish();
                break;
            }
        }

    }

}
