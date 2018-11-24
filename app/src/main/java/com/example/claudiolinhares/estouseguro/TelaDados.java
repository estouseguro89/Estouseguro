package com.example.claudiolinhares.estouseguro;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.claudiolinhares.estouseguro.database.AppDatabase;
import com.example.claudiolinhares.estouseguro.database.Contact;
import com.example.claudiolinhares.estouseguro.database.User;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.claudiolinhares.estouseguro.database.AppDatabase.getAppDatabase;

public class TelaDados extends AppCompatActivity implements View.OnClickListener{

    TextView text_dados;
    Button button_editar,menu_button;
    //EditText cpfinput,sobrenomeinput,telefoneinput,emailinput,senhainput;
    //TextInputLayout cpfinputlayout,sobrenomeinputlayout,telefoneinputlayout,emailinputlayout,senhainputlayout;
    TextInputEditText nomeinputlayout,cpfinputlayout,sobrenomeinputlayout,telefoneinputlayout,emailinputlayout;
    AppDatabase db;
    String cpf;
    private AlertDialog alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_dados);


        //Pega a intent de outra activity
        Intent it = getIntent();

        //Recuperei a string da outra activity
        cpf = it.getStringExtra("CPF");


        cpfinputlayout = (TextInputEditText) findViewById(R.id.cpfinputlayout);
        nomeinputlayout = (TextInputEditText) findViewById(R.id.nomeinputlayout);
        sobrenomeinputlayout = (TextInputEditText) findViewById(R.id.sobrenomeinputlayout);
        telefoneinputlayout = (TextInputEditText) findViewById(R.id.telefoneinputlayout);
        emailinputlayout = (TextInputEditText) findViewById(R.id.emailinputlayout);


        text_dados = (TextView) findViewById(R.id.text_dados);
        button_editar = (Button) findViewById(R.id.button_editar);
        menu_button = (Button) findViewById(R.id.menu_button);


        //Altera as Fontes
        Typeface font = Typeface.createFromAsset(getAssets(),"impact.ttf");
        text_dados.setTypeface(font);
        button_editar.setTypeface(font);
        button_editar.setOnClickListener(this);
        menu_button.setOnClickListener(this);

        //Criando a mascara CPF
        SimpleMaskFormatter smf = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher mtw = new MaskTextWatcher(cpfinputlayout,smf);
        cpfinputlayout.addTextChangedListener(mtw);

        //Criando a mascara Telefone
        SimpleMaskFormatter smf2 = new SimpleMaskFormatter("(NN) NNNNNNNNN");
        MaskTextWatcher mtw2 = new MaskTextWatcher(telefoneinputlayout,smf2);
        telefoneinputlayout.addTextChangedListener(mtw2);

        db = getAppDatabase(this);


        //Carrega as configurações previamente preenchidas
        User user = db.userDao().findAlreadyExist(cpf);
        nomeinputlayout.setText(user.getName());
        sobrenomeinputlayout.setText(user.getLastname());
        cpfinputlayout.setText(user.getCpf());
        telefoneinputlayout.setText(user.getTelefone());
        emailinputlayout.setText(user.getEmail());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.menu_button: {
                Intent it = new Intent(this, TelaMenu.class);
                it.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                it.putExtra("CPF", cpf);
                startActivityForResult(it, 0);
                overridePendingTransition(0,0); //0 for no animation
                //startActivity(it);
                break;
            }
            case  R.id.button_editar: {
                String cpf_old = cpfinputlayout.getText().toString();
                String nome = nomeinputlayout.getText().toString();
                String lastName = sobrenomeinputlayout.getText().toString();
                String email = emailinputlayout.getText().toString();
                String telefone = telefoneinputlayout.getText().toString();

                boolean erro = false;

                if (cpf_old.equals("")) {
                    cpfinputlayout.setError("Digite um CPF válido");
                    erro = true;
                }

                if (nome.equals("")) {
                    nomeinputlayout.setError("Digite um Nome válido");
                    erro = true;
                }

                if (lastName.equals("")) {
                    sobrenomeinputlayout.setError("Digite um Sobrenome válido");
                    erro = true;
                }

                if (telefone.equals("")) {
                    telefoneinputlayout.setError("Digite um Telefone válido");
                    erro = true;
                }

                if (!erro)
                {

                    User userLogin = db.userDao().findAlreadyExist(cpf);
                    if (userLogin != null) {
                        userLogin.setName(nome);
                        userLogin.setLastname(lastName);
                        userLogin.setCpf(cpf_old);
                        userLogin.setEmail(email);
                        userLogin.setTelefone(telefone);
                        db.userDao().updateUsers(userLogin);


//                        //Cria o gerador do AlertDialog
//                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                        //define o titulo
//                        builder.setTitle("Dados Pessoais");
//                        //define a mensagem
//                        builder.setMessage("Dados atualizados com sucesso!");
//                        //define um botão como positivo
//                        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface arg0, int arg1) {
//                                finish();
//                            }
//                        });
//                        //cria o AlertDialog
//                        alerta = builder.create();
//                        //Exibe
//                        alerta.show();

                        final Intent intent = new Intent(this, MainActivity.class);
                        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Dados Pessoais")
                                .setContentText("Atualizados com sucesso!")
                                .setConfirmText("OK")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                        intent.putExtra("CPF", cpf);
                                        startActivityForResult(intent, 0);
                                        overridePendingTransition(0,0); //0 for no animation
                                    }
                                })
                                .show();

                    }
                    else
                    {
                        cpfinputlayout.setError("Erro: Usuário não encontrado.");
                    }
                }


                break;
            }
        }

    }

}
