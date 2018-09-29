package com.example.claudiolinhares.estouseguro;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.claudiolinhares.estouseguro.database.AppDatabase;
import com.example.claudiolinhares.estouseguro.database.Contact;
import com.example.claudiolinhares.estouseguro.database.User;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static com.example.claudiolinhares.estouseguro.database.AppDatabase.getAppDatabase;

public class TelaCadastro extends AppCompatActivity implements View.OnClickListener{

    TextView text_anjo,text_cadastro;
    Button button_cadastrar;
    EditText cpfinput,nomeinput,sobrenomeinput,telefoneinput,emailinput,senhainput;
    TextInputLayout cpfinputlayout,nomeinputlayout,sobrenomeinputlayout,telefoneinputlayout,emailinputlayout,senhainputlayout;
    AppDatabase db;
    private AlertDialog alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cadastro);


        cpfinput = (EditText) findViewById(R.id.cpfinput);
        nomeinput = (EditText) findViewById(R.id.nomeinput);
        sobrenomeinput = (EditText) findViewById(R.id.sobrenomeinput);
        telefoneinput = (EditText) findViewById(R.id.telefoneinput);
        emailinput = (EditText) findViewById(R.id.emailinput);
        senhainput = (EditText) findViewById(R.id.senhainput);

        cpfinputlayout = (TextInputLayout) findViewById(R.id.cpfinputlayout);
        nomeinputlayout = (TextInputLayout) findViewById(R.id.nomeinputlayout);
        sobrenomeinputlayout = (TextInputLayout) findViewById(R.id.sobrenomeinputlayout);
        telefoneinputlayout = (TextInputLayout) findViewById(R.id.telefoneinputlayout);
        emailinputlayout = (TextInputLayout) findViewById(R.id.emailinputlayout);
        senhainputlayout = (TextInputLayout) findViewById(R.id.senhainputlayout);


        text_anjo = (TextView) findViewById(R.id.text_anjo);
        text_cadastro = (TextView) findViewById(R.id.text_cadastro);
        button_cadastrar = (Button) findViewById(R.id.button_cadastrar);


        //Altera as Fontes
        Typeface font = Typeface.createFromAsset(getAssets(),"impact.ttf");
        text_anjo.setTypeface(font);
        text_cadastro.setTypeface(font);
        button_cadastrar.setTypeface(font);
        button_cadastrar.setOnClickListener(this);

        //Criando a mascara CPF
        SimpleMaskFormatter smf = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher mtw = new MaskTextWatcher(cpfinput,smf);
        cpfinput.addTextChangedListener(mtw);

        //Criando a mascara Telefone
        SimpleMaskFormatter smf2 = new SimpleMaskFormatter("(NN) NNNNNNNNN");
        MaskTextWatcher mtw2 = new MaskTextWatcher(telefoneinput,smf2);
        telefoneinput.addTextChangedListener(mtw2);

        db = getAppDatabase(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.button_cadastrar: {
                String cpf = cpfinput.getText().toString();
                String nome = nomeinput.getText().toString();
                String password = senhainput.getText().toString();
                String lastName = sobrenomeinput.getText().toString();
                String email = emailinput.getText().toString();
                String telefone = telefoneinput.getText().toString();

                boolean erro = false;

                if (cpf.equals("")) {
                    cpfinputlayout.setError("Digite um CPF válido");
                    erro = true;
                }
                else
                {
                    cpfinputlayout.setErrorEnabled(false);
                }
                if (nome.equals("")) {
                    nomeinputlayout.setError("Digite um Nome válido");
                    erro = true;
                }
                else
                {
                    nomeinputlayout.setErrorEnabled(false);
                }
                if (password.equals("")) {
                    senhainputlayout.setError("Digite uma Senha válida");
                    erro = true;
                }
                else
                {
                    senhainputlayout.setErrorEnabled(false);
                }
                if (lastName.equals("")) {
                    sobrenomeinputlayout.setError("Digite um Sobrenome válido");
                    erro = true;
                }
                else
                {
                    sobrenomeinputlayout.setErrorEnabled(false);
                }
                if (telefone.equals("")) {
                    telefoneinputlayout.setError("Digite um Telefone válido");
                    erro = true;
                }
                else
                {
                    telefoneinputlayout.setErrorEnabled(false);
                }
                if (!erro) {
                    User userLogin = db.userDao().findAlreadyExist(cpf);
                    if (userLogin == null) {
                        List<Contact> contatos = new ArrayList();
                        User new_user = new User(cpf, md5(password), nome, lastName, email, telefone, contatos);
                        db.userDao().insertAll(new_user);


                        //Cria o gerador do AlertDialog
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        //define o titulo
                        builder.setTitle("Cadastro");
                        //define a mensagem
                        builder.setMessage("Cadastro realizado com sucesso!");
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
                        cpfinputlayout.setError("CPF já foi cadastrado.");
                    }
                }

                break;
            }
        }

    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
