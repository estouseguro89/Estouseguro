package com.example.claudiolinhares.estouseguro;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.InputType;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.claudiolinhares.estouseguro.database.AppDatabase.getAppDatabase;

public class TelaCadastro extends AppCompatActivity implements View.OnClickListener{

    TextView text_anjo,text_cadastro,button_termos;
    Button button_cadastrar;
    CheckBox checkbox_termos;
    //EditText cpfinput,sobrenomeinput,telefoneinput,emailinput,senhainput;
    //TextInputLayout cpfinputlayout,sobrenomeinputlayout,telefoneinputlayout,emailinputlayout,senhainputlayout;
    TextInputEditText nomeinputlayout,cpfinputlayout,sobrenomeinputlayout,telefoneinputlayout,emailinputlayout,senhainputlayout,senhainputlayoutrepetir;
    AppDatabase db;
    private AlertDialog alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cadastro);


        /*cpfinput = (EditText) findViewById(R.id.cpfinput);
        sobrenomeinput = (EditText) findViewById(R.id.sobrenomeinput);
        telefoneinput = (EditText) findViewById(R.id.telefoneinput);
        emailinput = (EditText) findViewById(R.id.emailinput);
        senhainput = (EditText) findViewById(R.id.senhainput);
        */

        cpfinputlayout = (TextInputEditText) findViewById(R.id.cpfinputlayout);
        nomeinputlayout = (TextInputEditText) findViewById(R.id.nomeinputlayout);
        sobrenomeinputlayout = (TextInputEditText) findViewById(R.id.sobrenomeinputlayout);
        telefoneinputlayout = (TextInputEditText) findViewById(R.id.telefoneinputlayout);
        emailinputlayout = (TextInputEditText) findViewById(R.id.emailinputlayout);
        senhainputlayout = (TextInputEditText) findViewById(R.id.senhainputlayout);
        senhainputlayoutrepetir = (TextInputEditText) findViewById(R.id.senhainputlayoutrepetir);

        checkbox_termos = (CheckBox) findViewById(R.id.checkbox_termos);

        text_anjo = (TextView) findViewById(R.id.text_anjo);
        text_cadastro = (TextView) findViewById(R.id.text_cadastro);
        button_termos = (TextView) findViewById(R.id.button_termos);
        button_cadastrar = (Button) findViewById(R.id.button_cadastrar);


        //Altera as Fontes
        Typeface font = Typeface.createFromAsset(getAssets(),"impact.ttf");
        text_anjo.setTypeface(font);
        text_cadastro.setTypeface(font);
        button_cadastrar.setTypeface(font);
        button_cadastrar.setOnClickListener(this);

        //Criando a mascara CPF
        SimpleMaskFormatter smf = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher mtw = new MaskTextWatcher(cpfinputlayout,smf);
        cpfinputlayout.addTextChangedListener(mtw);

        //Criando a mascara Telefone
        SimpleMaskFormatter smf2 = new SimpleMaskFormatter("(NN) NNNNNNNNN");
        MaskTextWatcher mtw2 = new MaskTextWatcher(telefoneinputlayout,smf2);
        telefoneinputlayout.addTextChangedListener(mtw2);

        String checkBoxText = "Eu li e concordo com os <a href='' >Termos de Uso</a>";

        button_termos.setText(Html.fromHtml(checkBoxText));
        button_termos.setMovementMethod(LinkMovementMethod.getInstance());
        button_termos.setOnClickListener(this);

        db = getAppDatabase(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_termos:{
                Intent intent = new Intent(this, TelaTermosCadastro.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivityForResult(intent, 0);
                overridePendingTransition(0,0); //0 for no animation
                break;
            }
            case  R.id.button_cadastrar: {
                String cpf = cpfinputlayout.getText().toString();
                String nome = nomeinputlayout.getText().toString();
                String password = senhainputlayout.getText().toString();
                String password2 = senhainputlayoutrepetir.getText().toString();
                String lastName = sobrenomeinputlayout.getText().toString();
                String email = emailinputlayout.getText().toString();
                String telefone = telefoneinputlayout.getText().toString();

                boolean erro = false;

                if (cpf.equals("")) {
                    cpfinputlayout.setError("Digite um CPF válido");
                    erro = true;
                }

                if (nome.equals("")) {
                    nomeinputlayout.setError("Digite um Nome válido");
                    erro = true;
                }

                if (password.equals("")) {
                    senhainputlayout.setError("Digite uma Senha válida");
                    erro = true;
                }

                if (password.equals("")) {
                    senhainputlayoutrepetir.setError("Digite uma Senha válida");
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
                if(!checkbox_termos.isChecked()){
                    checkbox_termos.setError("É preciso aceitar os Termos de Uso");
                    erro = true;
                }
                else{
                    checkbox_termos.setError(null);
                }

                if (!erro)
                {
                    if(!password.equals(password2))
                    {
                        senhainputlayout.setError("As senhas digitadas não conferem.");
                        senhainputlayoutrepetir.setError("As senhas digitadas não conferem.");
                    }
                    else{
                        User userLogin = db.userDao().findAlreadyExist(cpf);
                        if (userLogin == null) {
                            List<Contact> contatos = new ArrayList();
                            User new_user = new User(cpf, md5(password), nome, lastName, email, telefone, contatos);
                            db.userDao().insertAll(new_user);


//                            //Cria o gerador do AlertDialog
//                            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                            //define o titulo
//                            builder.setTitle("Cadastro");
//                            //define a mensagem
//                            builder.setMessage("Cadastro realizado com sucesso!");
//                            //define um botão como positivo
//                            builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface arg0, int arg1) {
//                                    finish();
//                                }
//                            });
//                            //cria o AlertDialog
//                            alerta = builder.create();
//                            //Exibe
//                            alerta.show();

                            new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Cadastro")
                                    .setContentText("Cadastrado com sucesso!")
                                    .setConfirmText("OK")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog.dismissWithAnimation();
                                            finish();
                                        }
                                    })
                                    .show();
                        }
                        else
                        {
                            cpfinputlayout.setError("CPF já foi cadastrado.");
                        }
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
