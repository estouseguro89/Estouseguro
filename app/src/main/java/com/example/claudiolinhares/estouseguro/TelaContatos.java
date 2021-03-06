package com.example.claudiolinhares.estouseguro;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.claudiolinhares.estouseguro.database.AppDatabase;
import com.example.claudiolinhares.estouseguro.database.Contact;
import com.example.claudiolinhares.estouseguro.database.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.claudiolinhares.estouseguro.database.AppDatabase.getAppDatabase;

public class TelaContatos extends AppCompatActivity implements View.OnClickListener{

    TextView text_contatos;
    Button ic_reject_contact_send,menu_button,contatos;
    LinearLayout layout_raiz1;
    RelativeLayout contato_enivado;
    ImageView line_convites_send1;
    AppDatabase db;
    String cpf;
    private AlertDialog alerta;
    int buttonID;
    List<Contact> contatosEnviados;
    List<Contact> contatosRecebidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_contatos);

        //Pega a intent de outra activity
        Intent it = getIntent();

        //Recuperei a string da outra activity
        cpf = it.getStringExtra("CPF");

        text_contatos = (TextView) findViewById(R.id.text_contatos);

        layout_raiz1 = (LinearLayout) findViewById(R.id.layout_raiz1);
        /*
        line_convites_send1 = (ImageView) findViewById(R.id.line_convites_send1);

        contato_enivado = (RelativeLayout) findViewById(R.id.contato_enivado);

        ic_reject_contact_send = (Button) findViewById(R.id.ic_reject_contact_send);
        */
        menu_button = (Button) findViewById(R.id.menu_button);
        contatos = (Button) findViewById(R.id.contatos);

        //Altera as Fontes
        Typeface font = Typeface.createFromAsset(getAssets(),"impact.ttf");
        text_contatos.setTypeface(font);

        //ic_reject_contact_send.setOnClickListener(this);
        menu_button.setOnClickListener(this);
        contatos.setOnClickListener(this);

        buttonID = generateViewId();

        db = getAppDatabase(this);
        User userLogin = db.userDao().findAlreadyExist(cpf);
        if (userLogin != null) {
            //PEGA OS CONTATOS RECEBIDOS E ADICIONA DINAMICAMENTE
            contatosRecebidos =  userLogin.getReceivedcontact();
            if(contatosRecebidos.size() == 0)
            {
                TextView tv = new TextView(this);
                tv.setText("Nenhum convite recebido.");
                tv.setTextColor(Color.parseColor("#C1CBCA"));
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);

                layout_raiz1.addView(tv);

                ImageView line_convites = new ImageView(this);
                int dimensionInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());
                RelativeLayout.LayoutParams line_params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, dimensionInDp);
                line_params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                line_convites.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.line_dark));
                line_convites.setLayoutParams(line_params);

                layout_raiz1.addView(line_convites);
            }
            else
            {
                for(Contact contatoRecebido : contatosRecebidos)
                {
                    //Verifica os contatos enviados
                    RelativeLayout relativeLayout = new RelativeLayout(this);
                    RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.MATCH_PARENT,
                            RelativeLayout.LayoutParams.MATCH_PARENT);
                    relativeLayout.setLayoutParams(rlp);

                    Button button = new Button(this);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
                    params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    button.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.ic_reject_contact));
                    button.setLayoutParams(params);
                    button.setId(contatoRecebido.getId());

                    int dimensionInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
                    button.getLayoutParams().height = dimensionInDp;
                    dimensionInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35, getResources().getDisplayMetrics());
                    button.getLayoutParams().width = dimensionInDp;
                    button.requestLayout();

                    relativeLayout.addView(button);

                    LinearLayout linear = new LinearLayout(this);
                    RelativeLayout.LayoutParams llp = new RelativeLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    llp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                    //llp.gravity= Gravity.CENTER_VERTICAL;
                    linear.setGravity(Gravity.CENTER);
                    linear.setOrientation(LinearLayout.VERTICAL);
                    linear.setLayoutParams(llp);


                    TextView tv = new TextView(this);
                    tv.setText(contatoRecebido.getName());
                    tv.setTextColor(Color.parseColor("#C1CBCA"));
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);

                    // Defining the layout parameters of the TextView
                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
                    lp.addRule(RelativeLayout.CENTER_IN_PARENT);

                    // Setting the parameters on the TextView
                    tv.setLayoutParams(lp);

                    linear.addView(tv);

                    TextView tvTelefone = new TextView(this);
                    tvTelefone.setText(contatoRecebido.getTelefone());
                    tvTelefone.setTextColor(Color.parseColor("#C1CBCA"));
                    tvTelefone.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);

                    // Defining the layout parameters of the TextView
                    RelativeLayout.LayoutParams lpTelefone = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
                    lpTelefone.addRule(RelativeLayout.CENTER_IN_PARENT);

                    // Setting the parameters on the TextView
                    tvTelefone.setLayoutParams(lpTelefone);

                    linear.addView(tvTelefone);


                    Button button2 = new Button(this);
                    RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
                    params2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    button2.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.ic_accept_contact));
                    button2.setLayoutParams(params2);
                    button2.setId(contatoRecebido.getId());

                    dimensionInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
                    button2.getLayoutParams().height = dimensionInDp;
                    dimensionInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35, getResources().getDisplayMetrics());
                    button2.getLayoutParams().width = dimensionInDp;
                    button2.requestLayout();

                    button2.setOnClickListener(this);


                    // Adding the TextView to the RelativeLayout as a child
                    relativeLayout.addView(linear);
                    relativeLayout.addView(button2);

                    layout_raiz1.addView(relativeLayout);

                    ImageView line_convites = new ImageView(this);
                    dimensionInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());
                    RelativeLayout.LayoutParams line_params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, dimensionInDp);
                    line_params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    line_convites.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.line_dark));
                    line_convites.setLayoutParams(line_params);

                    layout_raiz1.addView(line_convites);


                }
            }

            //ADICIONA DINAMICAMENTE O TEXTO CONVITES ENVIADOS E A BARRA PRETA

            TextView tv_convitesenviados = new TextView(this);
            tv_convitesenviados.setText("Convites Enviados");
            tv_convitesenviados.setTextColor(Color.parseColor("#01C7CA"));
            tv_convitesenviados.setTextSize(TypedValue.COMPLEX_UNIT_SP,17);

            layout_raiz1.addView(tv_convitesenviados);


            //PEGA OS CONTATOS ENVIADOS E ADICIONA DINAMICAMENTE
            contatosEnviados =  userLogin.getSendcontact();
            if(contatosEnviados.size() == 0)
            {
                TextView tv = new TextView(this);
                tv.setText("Nenhum convite enviado.");
                tv.setTextColor(Color.parseColor("#C1CBCA"));
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);

                layout_raiz1.addView(tv);

                ImageView line_convites = new ImageView(this);
                int dimensionInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());
                RelativeLayout.LayoutParams line_params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, dimensionInDp);
                line_params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                line_convites.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.line_dark));
                line_convites.setLayoutParams(line_params);

                layout_raiz1.addView(line_convites);
            }
            else
            {
                for(Contact contatoEnviado : contatosEnviados)
                {
                    //Verifica os contatos enviados
                    RelativeLayout relativeLayout = new RelativeLayout(this);
                    RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.MATCH_PARENT,
                            RelativeLayout.LayoutParams.MATCH_PARENT);
                    relativeLayout.setLayoutParams(rlp);

                    LinearLayout linear = new LinearLayout(this);
                    LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);

                    linear.setOrientation(LinearLayout.VERTICAL);
                    linear.setLayoutParams(llp);


                    TextView tv = new TextView(this);
                    tv.setText(contatoEnviado.getName());
                    tv.setTextColor(Color.parseColor("#C1CBCA"));
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);

                    // Defining the layout parameters of the TextView
                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
                    lp.addRule(RelativeLayout.CENTER_IN_PARENT);

                    // Setting the parameters on the TextView
                    tv.setLayoutParams(lp);

                    linear.addView(tv);

                    TextView tvTelefone = new TextView(this);
                    tvTelefone.setText(contatoEnviado.getTelefone());
                    tvTelefone.setTextColor(Color.parseColor("#C1CBCA"));
                    tvTelefone.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);

                    // Defining the layout parameters of the TextView
                    RelativeLayout.LayoutParams lpTelefone = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
                    lpTelefone.addRule(RelativeLayout.CENTER_IN_PARENT);

                    // Setting the parameters on the TextView
                    tvTelefone.setLayoutParams(lpTelefone);

                    linear.addView(tvTelefone);


                    Button button = new Button(this);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
                    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    button.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.ic_reject_contact));
                    button.setLayoutParams(params);
                    button.setId(contatoEnviado.getId());

                    int dimensionInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
                    button.getLayoutParams().height = dimensionInDp;
                    dimensionInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35, getResources().getDisplayMetrics());
                    button.getLayoutParams().width = dimensionInDp;
                    button.requestLayout();

                    button.setOnClickListener(this);


                    // Adding the TextView to the RelativeLayout as a child
                    relativeLayout.addView(linear);
                    relativeLayout.addView(button);

                    layout_raiz1.addView(relativeLayout);

                    ImageView line_convites = new ImageView(this);
                    dimensionInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());
                    RelativeLayout.LayoutParams line_params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, dimensionInDp);
                    line_params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    line_convites.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.line_dark));
                    line_convites.setLayoutParams(line_params);

                    layout_raiz1.addView(line_convites);


                }
            }

        }
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.menu_button)
        {
            Intent intent = new Intent(this, TelaMenu.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            intent.putExtra("CPF", cpf);
            startActivityForResult(intent, 0);
            overridePendingTransition(0,0); //0 for no animation
        }
        else if(v.getId() == buttonID)
        {
            //findViewById(R.id.contato_enivado).setVisibility(View.GONE);
            //findViewById(R.id.line_convites_send1).setVisibility(View.GONE);

        }
        else if(v.getId() == R.id.contatos)
        {
            Intent intent = new Intent(this, TelaAdicionarContato.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            intent.putExtra("CPF", cpf);
            startActivityForResult(intent, 0);
            overridePendingTransition(0,0); //0 for no animation
        }
        else{
            for(final Contact contatoEnviado : contatosEnviados)
            {
                if(contatoEnviado.getId() == v.getId())
                {
//                    //Cria o gerador do AlertDialog
//                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                    //define o titulo
//                    builder.setTitle("Contato");
//                    //define a mensagem
//                    builder.setMessage("Deseja excluir o contato selecionado?");
//                    final Intent intent = new Intent(this, TelaContatos.class);
//                    //define um botão como positivo
//                    builder.setNeutralButton("Yes", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface arg0, int arg1) {
//
//                            List<Contact> updatedContacts = new ArrayList<>();
//                            updatedContacts.addAll(contatosEnviados);
//                            updatedContacts.remove(contatoEnviado);
//
//                            User userLogin = db.userDao().findAlreadyExist(cpf);
//                            if (userLogin != null) {
//                                //ATUALIZA OS CONTATOS
//                                userLogin.setAllContacts(updatedContacts);
//                            }
//
//                            db.userDao().updateUsers(userLogin);
//
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                            intent.putExtra("CPF", cpf);
//                            startActivityForResult(intent, 0);
//                            overridePendingTransition(0,0); //0 for no animation
//                        }
//                    });
//                    //cria o AlertDialog
//                    alerta = builder.create();
//                    //Exibe
//                    alerta.show();

                    final Intent intent = new Intent(this, TelaContatos.class);
                    new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Contato")
                            .setContentText("Cancelar o envio?")
                            .setCancelText("Não")
                            .setConfirmText("Sim")
                            .showCancelButton(true)
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.cancel();
                                }
                            })
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                public void onClick(SweetAlertDialog sDialog) {

                                    List<Contact> updatedContacts = new ArrayList<>();
                                    updatedContacts.addAll(contatosEnviados);
                                    updatedContacts.remove(contatoEnviado);

                                    User userLogin = db.userDao().findAlreadyExist(cpf);
                                    if (userLogin != null) {
                                        //ATUALIZA OS CONTATOS ENVIADOS
                                        userLogin.setSendcontact(updatedContacts);
                                    }

                                    db.userDao().updateUsers(userLogin);

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

    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    public static int generateViewId() {
        for (;;) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }

}
