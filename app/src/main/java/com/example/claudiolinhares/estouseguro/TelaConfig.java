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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.claudiolinhares.estouseguro.database.AppDatabase;
import com.example.claudiolinhares.estouseguro.database.User;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.claudiolinhares.estouseguro.database.AppDatabase.getAppDatabase;

public class TelaConfig extends AppCompatActivity implements View.OnClickListener{

    TextView text_config;
    Button button_salvar,menu_button;
    AppDatabase db;
    String cpf;
    CheckBox configsms, configlocation, configemail;
    private AlertDialog alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_config);

        //Pega a intent de outra activity
        Intent it = getIntent();

        //Recuperei a string da outra activity
        cpf = it.getStringExtra("CPF");


        text_config = (TextView) findViewById(R.id.text_config);
        button_salvar = (Button) findViewById(R.id.button_salvar);
        menu_button = (Button) findViewById(R.id.menu_button);

        configsms = (CheckBox) findViewById(R.id.configsms);
        configlocation = (CheckBox) findViewById(R.id.configlocation);
        configemail = (CheckBox) findViewById(R.id.configemail);


        //Altera as Fontes
        Typeface font = Typeface.createFromAsset(getAssets(),"impact.ttf");
        text_config.setTypeface(font);
        button_salvar.setTypeface(font);

        button_salvar.setOnClickListener(this);
        menu_button.setOnClickListener(this);

        db = getAppDatabase(this);

        //Carrega as configurações previamente selecionadas
        User user2 = db.userDao().findAlreadyExist(cpf);
        if (user2.isConfigsms()) {
            configsms.setChecked(true);
        }
        if (user2.isConfiglocation()) {
            configlocation.setChecked(true);
        }
        if (user2.isConfigemail()) {
            configemail.setChecked(true);
        }
    }

    Intent intentfecha;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.menu_button: {
                Intent intent = new Intent(this, TelaMenu.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("CPF", cpf);
                startActivityForResult(intent, 0);
                overridePendingTransition(0,0); //0 for no animation
                //startActivity(new Intent(this, TelaMenu.class));
                break;
            }
            case  R.id.button_salvar: {

                User user = db.userDao().findAlreadyExist(cpf);
                if (configsms.isChecked()) {
                    user.setConfigsms(true);
                }
                else{
                    user.setConfigsms(false);
                }

                if (configlocation.isChecked()) {
                    user.setConfiglocation(true);
                }
                else{
                    user.setConfiglocation(false);
                }

                if (configemail.isChecked()) {
                    user.setConfigemail(true);
                }
                else{
                    user.setConfigemail(false);
                }

                db.userDao().updateUsers(user);

//                //Cria o gerador do AlertDialog
//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                //define o titulo
//                builder.setTitle("Configurações");
//                //define a mensagem
//                builder.setMessage("Configurações salvas com sucesso!");
//                intentfecha = new Intent(this, MainActivity.class);
//                intentfecha.putExtra("CPF", cpf);
//                intentfecha.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                //define um botão como positivo
//                builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface arg0, int arg1) {
//
//                        startActivityForResult(intentfecha, 0);
//                        overridePendingTransition(0,0); //0 for no animation
//                    }
//                });
//                //cria o AlertDialog
//                alerta = builder.create();
//                //Exibe
//                alerta.show();

                final Intent intent = new Intent(this, MainActivity.class);
                new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Configurações")
                        .setContentText("Salvas com sucesso!")
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

                break;

            }
        }

    }

}
