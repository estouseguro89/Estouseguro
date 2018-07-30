package com.example.claudiolinhares.estouseguro;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.claudiolinhares.estouseguro.database.AppDatabase;
import com.example.claudiolinhares.estouseguro.database.User;

import static com.example.claudiolinhares.estouseguro.database.AppDatabase.getAppDatabase;

public class TelaMenu extends AppCompatActivity implements View.OnClickListener{

    Button fechar_menu,bt_dadospessoais,bt_alterarsenha,bt_config,bt_contatosamigos,bt_mapaalertas,bt_relatorio,bt_termos;
    String cpf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_menu);

        //Pega a intent de outra activity
        Intent it = getIntent();

        //Recuperei a string da outra activity
        cpf = it.getStringExtra("CPF");

        fechar_menu = (Button) findViewById(R.id.fechar_menu);
        bt_dadospessoais = (Button) findViewById(R.id.bt_dadospessoais);
        bt_alterarsenha = (Button) findViewById(R.id.bt_alterarsenha);
        bt_config = (Button) findViewById(R.id.bt_config);
        bt_contatosamigos = (Button) findViewById(R.id.bt_contatosamigos);
        bt_mapaalertas = (Button) findViewById(R.id.bt_mapaalertas);
        bt_relatorio = (Button) findViewById(R.id.bt_relatorio);
        bt_termos = (Button) findViewById(R.id.bt_termos);


        //Altera as Fontes
        Typeface font = Typeface.createFromAsset(getAssets(),"impact.ttf");
        bt_dadospessoais.setTypeface(font);
        bt_alterarsenha.setTypeface(font);
        bt_config.setTypeface(font);
        bt_contatosamigos.setTypeface(font);
        bt_mapaalertas.setTypeface(font);
        bt_relatorio.setTypeface(font);
        bt_termos.setTypeface(font);


        fechar_menu.setOnClickListener(this);
        bt_alterarsenha.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fechar_menu: {
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivityForResult(intent, 0);
                overridePendingTransition(0,0); //0 for no animation
                break;
            }
            case  R.id.bt_alterarsenha: {
                //startActivity(new Intent(this, TelaAlterarSenha.class));
                Intent it = new Intent(this, TelaAlterarSenha.class);
                it.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                it.putExtra("CPF", cpf);
                startActivityForResult(it, 0);
                overridePendingTransition(0,0); //0 for no animation
                break;
            }
        }

    }

}
