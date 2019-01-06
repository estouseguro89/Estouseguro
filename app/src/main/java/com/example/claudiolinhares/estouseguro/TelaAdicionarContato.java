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
import com.example.claudiolinhares.estouseguro.database.Contact;
import com.example.claudiolinhares.estouseguro.database.User;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.claudiolinhares.estouseguro.database.AppDatabase.getAppDatabase;

public class TelaAdicionarContato extends AppCompatActivity implements View.OnClickListener{

    TextView text_adicionarcontato;
    Button button_salvar,menu_button;
    EditText telefoneinput,repetirtelefoneinput,nomeinput;
    TextInputLayout repetirtelefoneinputlayout,telefoneinputlayout,nomeinputlayout;
    AppDatabase db;
    String cpf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_adicionarcontato);

        //Pega a intent de outra activity
        Intent it = getIntent();

        //Recuperei a string da outra activity
        cpf = it.getStringExtra("CPF");


        text_adicionarcontato = (TextView) findViewById(R.id.text_adicionarcontato);
        button_salvar = (Button) findViewById(R.id.button_salvar);
        menu_button = (Button) findViewById(R.id.menu_button);

        repetirtelefoneinput = (EditText) findViewById(R.id.repetirtelefoneinput);
        telefoneinput = (EditText) findViewById(R.id.telefoneinput);
        nomeinput = (EditText) findViewById(R.id.nomeinput);

        repetirtelefoneinputlayout = (TextInputLayout) findViewById(R.id.repetirtelefoneinputlayout);
        telefoneinputlayout = (TextInputLayout) findViewById(R.id.telefoneinputlayout);
        nomeinputlayout = (TextInputLayout) findViewById(R.id.nomeinputlayout);


        //Altera as Fontes
        Typeface font = Typeface.createFromAsset(getAssets(),"impact.ttf");
        text_adicionarcontato.setTypeface(font);
        button_salvar.setTypeface(font);


        //Criando a mascara Telefone
        SimpleMaskFormatter smf2 = new SimpleMaskFormatter("(NN) NNNNNNNNN");
        MaskTextWatcher mtw2 = new MaskTextWatcher(repetirtelefoneinput,smf2);
        repetirtelefoneinput.addTextChangedListener(mtw2);
        MaskTextWatcher mtw = new MaskTextWatcher(telefoneinput,smf2);
        telefoneinput.addTextChangedListener(mtw);


        button_salvar.setOnClickListener(this);
        menu_button.setOnClickListener(this);

        db = getAppDatabase(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.menu_button: {
                Intent intent = new Intent(this, TelaMenu.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("CPF", cpf);
                startActivityForResult(intent, 0);
                overridePendingTransition(0,0); //0 for no animation
                break;
            }
            case  R.id.button_salvar: {

                repetirtelefoneinputlayout.setErrorEnabled(false);
                telefoneinputlayout.setErrorEnabled(false);
                nomeinputlayout.setErrorEnabled(false);

                String telefone = telefoneinput.getText().toString();
                String repetirtelefone = repetirtelefoneinput.getText().toString();
                String nome = nomeinput.getText().toString();

                //Verifica se Telefone e Repetir Telefone são iguais
                if(telefone.equals(repetirtelefone))
                {

                    User userLogin = db.userDao().findAlreadyExist(cpf);
                    int id = userLogin.getContacts().size()+1; //SUBSTITUIR ESSE CALCULO DE ID POR UM VERIFICADOR DOS IDS EXISTENTES E CRIAR UM NOVO
                    Contact contact = new Contact(id,nome,telefone);

                    if (userLogin != null) {
                        //PEGA OS CONTATOS EXISTENTES E INCREMENTA O NOVO
                        userLogin.setOneSendcontact(contact);
                        db.userDao().updateUsers(userLogin);

                        //ENVIAR PARA O CONTATO DO TELEFONE (SE EXISTIR) A SOLICITAÇÃO
                        List<User> listUser = db.userDao().getAll();
                        User userReceived = null;
                        for(User u : listUser)
                        {
                            if( u.getTelefone().equals(telefone))
                            {
                                userReceived = u;
                                break;
                            }
                        }
                        if(userReceived != null)
                        {
                            Contact contactLogged = new Contact(userLogin.getId(),userLogin.getName(),userLogin.getTelefone());
                            userReceived.setOneReceivedcontact(contactLogged);

                            db.userDao().updateUsers(userReceived);
                        }

                        final Intent intent = new Intent(this, TelaContatos.class);
                        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Cadastro de Contato")
                                .setContentText("Enviado com sucesso!")
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

                        final Intent intent = new Intent(this, TelaContatos.class);
                        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Cadastro de Contato")
                                .setContentText("Erro para referenciar!")
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
                }
                else
                {
                    repetirtelefoneinputlayout.setError("Os telefones não coincidem");
                    telefoneinputlayout.setError("Os telefones não coincidem");
                }
                break;

            }
        }

    }

}
