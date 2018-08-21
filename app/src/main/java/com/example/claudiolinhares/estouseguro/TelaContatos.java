package com.example.claudiolinhares.estouseguro;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.claudiolinhares.estouseguro.database.AppDatabase;
import com.example.claudiolinhares.estouseguro.database.User;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.example.claudiolinhares.estouseguro.R.id.contato_enivado;
import static com.example.claudiolinhares.estouseguro.database.AppDatabase.getAppDatabase;

public class TelaContatos extends AppCompatActivity implements View.OnClickListener{

    TextView text_contatos;
    Button ic_reject_contact_send,menu_button;
    RelativeLayout contato_enivado;
    ImageView line_convites_send1;
    AppDatabase db;
    String cpf;
    private AlertDialog alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_contatos);

        //Pega a intent de outra activity
        Intent it = getIntent();

        //Recuperei a string da outra activity
        cpf = it.getStringExtra("CPF");

        text_contatos = (TextView) findViewById(R.id.text_contatos);

        line_convites_send1 = (ImageView) findViewById(R.id.line_convites_send1);

        contato_enivado = (RelativeLayout) findViewById(R.id.contato_enivado);

        ic_reject_contact_send = (Button) findViewById(R.id.ic_reject_contact_send);
        menu_button = (Button) findViewById(R.id.menu_button);

        //Altera as Fontes
        Typeface font = Typeface.createFromAsset(getAssets(),"impact.ttf");
        text_contatos.setTypeface(font);

        ic_reject_contact_send.setOnClickListener(this);
        menu_button.setOnClickListener(this);

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
            case  R.id.ic_reject_contact_send: {

                findViewById(R.id.contato_enivado).setVisibility(View.GONE);
                findViewById(R.id.line_convites_send1).setVisibility(View.GONE);


                //syntax error in View
                //View namebar = View.findViewById(R.id.contato_enivado);
                //((ViewGroup) namebar.getParent()).removeView(namebar);

                break;
            }
        }

    }

}
