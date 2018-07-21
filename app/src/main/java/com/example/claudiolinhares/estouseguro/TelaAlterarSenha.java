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
import android.widget.Toast;

import com.example.claudiolinhares.estouseguro.database.AppDatabase;
import com.example.claudiolinhares.estouseguro.database.User;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.example.claudiolinhares.estouseguro.database.AppDatabase.getAppDatabase;

public class TelaAlterarSenha extends AppCompatActivity implements View.OnClickListener{

    TextView text_alterarsenha;
    Button button_salvar;
    EditText senhaantigainput,novasenhainput,repetirnovasenhainput;
    TextInputLayout senhaantigainputlayout,novasenhainputlayout,repetirnovasenhainputlayout;
    AppDatabase db;
    String cpf;
    private AlertDialog alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_alterarsenha);

        //Pega a intent de outra activity
        Intent it = getIntent();

        //Recuperei a string da outra activity
        cpf = it.getStringExtra("CPF");


        text_alterarsenha = (TextView) findViewById(R.id.text_alterarsenha);
        button_salvar = (Button) findViewById(R.id.button_salvar);

        senhaantigainput = (EditText) findViewById(R.id.senhaantigainput);
        novasenhainput = (EditText) findViewById(R.id.novasenhainput);
        repetirnovasenhainput = (EditText) findViewById(R.id.repetirnovasenhainput);

        senhaantigainputlayout = (TextInputLayout) findViewById(R.id.senhaantigainputlayout);
        novasenhainputlayout = (TextInputLayout) findViewById(R.id.novasenhainputlayout);
        repetirnovasenhainputlayout = (TextInputLayout) findViewById(R.id.repetirnovasenhainputlayout);


        //Altera as Fontes
        Typeface font = Typeface.createFromAsset(getAssets(),"impact.ttf");
        text_alterarsenha.setTypeface(font);
        button_salvar.setTypeface(font);

        button_salvar.setOnClickListener(this);

        db = getAppDatabase(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.button_salvar: {

                senhaantigainputlayout.setErrorEnabled(false);
                novasenhainputlayout.setErrorEnabled(false);
                repetirnovasenhainputlayout.setErrorEnabled(false);

                String senhaantiga = senhaantigainput.getText().toString();
                String novasenha = novasenhainput.getText().toString();
                String repetirnovasenha = repetirnovasenhainput.getText().toString();

                User userTrocaSenha = db.userDao().findAlreadyExist(cpf);
                if (userTrocaSenha != null) {
                    if(userTrocaSenha.getPassword().equals(TelaCadastro.md5(senhaantiga)))
                    {
                        //Senha Antiga esta correta
                        if(novasenha.equals(repetirnovasenha))
                        {
                            //Novas senhas coincidem
                            userTrocaSenha.setPassword(TelaCadastro.md5(novasenha));
                            db.userDao().updateUsers(userTrocaSenha);

                            //Cria o gerador do AlertDialog
                            AlertDialog.Builder builder = new AlertDialog.Builder(this);
                            //define o titulo
                            builder.setTitle("Senha");
                            //define a mensagem
                            builder.setMessage("Senha alterada com sucesso!");
                            //define um botão como positivo
                            builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface arg0, int arg1) {
                                    finish();
                                }
                            });
                            //cria o AlertDialog
                            alerta = builder.create();
                            //Exibe
                            alerta.show();

                        }
                        else
                        {
                            novasenhainputlayout.setError("As novas senhas não coincidem");
                            repetirnovasenhainputlayout.setError("As novas senhas não coincidem");
                        }
                    }
                    else
                    {
                        senhaantigainputlayout.setError("Senha antiga não confere");
                    }
                }
                break;

            }
        }

    }

}
