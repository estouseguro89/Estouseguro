package com.example.claudiolinhares.estouseguro;

import android.arch.persistence.room.Room;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.support.design.widget.TextInputLayout;

import com.example.claudiolinhares.estouseguro.database.AppDatabase;
import com.example.claudiolinhares.estouseguro.database.User;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import static com.example.claudiolinhares.estouseguro.database.AppDatabase.*;

public class TelaLogin extends AppCompatActivity implements View.OnClickListener{

    EditText cpfinput,senhainput;
    TextInputLayout cpfinputlayout,senhainputlayout;
    TextView text_anjo;
    Button button_conectar,button_cadastrar;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_login);

        cpfinput = (EditText) findViewById(R.id.cpfinput);
        cpfinputlayout = (TextInputLayout) findViewById(R.id.cpfinputlayout);
        senhainput = (EditText) findViewById(R.id.senhainput);
        senhainputlayout = (TextInputLayout) findViewById(R.id.senhainputlayout);
        text_anjo = (TextView) findViewById(R.id.text_anjo);
        button_conectar = (Button) findViewById(R.id.button_conectar);
        button_cadastrar = (Button) findViewById(R.id.button_cadastrar);
        button_conectar.setOnClickListener(this);

        //Altera as Fontes
        Typeface font = Typeface.createFromAsset(getAssets(),"impact.ttf");
        text_anjo.setTypeface(font);
        button_conectar.setTypeface(font);
        button_cadastrar.setTypeface(font);

        //Criando a mascara CPF
        SimpleMaskFormatter smf = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher mtw = new MaskTextWatcher(cpfinput,smf);
        cpfinput.addTextChangedListener(mtw);
        //Criando o database
        db = getAppDatabase(this);

        User user_claudio = new User("091.289.116-52","456");
        //db.userDao().insertAll(user_claudio);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.button_conectar: {
                String usuario = cpfinput.getText().toString();
                String senha = senhainput.getText().toString();
                User userLogin = db.userDao().findLogin(usuario,senha);
                if(userLogin == null) {
                    cpfinputlayout.setError("CPF Inválido");
                    senhainputlayout.setError("Senha Inválida");
                }
                else
                {
                    System.out.println("Logado");
                }
                break;
            }
        }
    }
}
