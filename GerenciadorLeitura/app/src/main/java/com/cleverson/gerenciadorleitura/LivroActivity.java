package com.cleverson.gerenciadorleitura;

import android.annotation.SuppressLint;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.util.Date;

public class LivroActivity extends AppCompatActivity {
    private EditText editTextTitulo,editTextAutor,editTextNPaginas,editTextDateInicio,editTextDateTermino,editTextAnotacao;
    private Button buttonLimpar, buttonSalvar;
    private CheckBox checkBoxFavorito;
    private RadioGroup radioGroup;
    private Spinner spinnerTipo;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livro);
        editTextTitulo = findViewById(R.id.editTextTitulo);
        editTextAutor = findViewById(R.id.editTextAutor);
        editTextNPaginas = findViewById(R.id.editTextNPaginas);
        editTextDateInicio = findViewById(R.id.editTextDateInicio);
        editTextDateTermino = findViewById(R.id.editTextDateTermino);
        editTextAnotacao = findViewById(R.id.editTextAnotacao);

        checkBoxFavorito = findViewById(R.id.checkBoxFavorito);
        spinnerTipo = findViewById(R.id.spinnerTipo);
        radioGroup = findViewById(R.id.radioGroupStatus);


        buttonLimpar = findViewById(R.id.buttonLimpar);
        buttonSalvar = findViewById(R.id.buttonSalvar);

        buttonLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limparCampos(v);
            }
        });

        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarLivro(v);
            }
        });


    }


    public void limparCampos(View view){
        editTextTitulo.setText("");
        editTextAutor.setText("");
        editTextNPaginas.setText("");
        editTextDateInicio.setText("");
        editTextDateTermino.setText("");
        editTextAnotacao.setText("");
        checkBoxFavorito.setChecked(false);
        editTextTitulo.requestFocus();
        radioGroup.clearCheck();

        Toast.makeText(this,
                "Os o valor dos campos foram apagados!",
                Toast.LENGTH_LONG).show();

    }

    @SuppressLint("NonConstantResourceId")
    public void salvarLivro(View view){
        String autor = editTextAutor.getText().toString();
        String titulo = editTextTitulo.getText().toString();
        String numeroPaginas = editTextNPaginas.getText().toString();
        int radioButtonId = radioGroup.getCheckedRadioButtonId();
        String dataInicio = editTextDateInicio.getText().toString();
        String dataFim = editTextDateTermino.getText().toString();
        boolean livroFavorito = checkBoxFavorito.isChecked();
        String status;


        if(autor.isEmpty() || titulo.isEmpty() || numeroPaginas.isEmpty()){
            System.out.println("Teste");
            Toast.makeText(this,
                    "É obrigatorio incluir: Autor e Titulo do Livro e a quantidade de paginas!",
                    Toast.LENGTH_LONG).show();
                return;

        }

        try {
             int  paginas = Integer.parseInt(numeroPaginas);
             if(paginas < 10) {
                Toast.makeText(this,
                        "O livro deve conter no mínimo 10 paginas!",
                        Toast.LENGTH_LONG).show();
                return;

            }
        }catch (NumberFormatException e){
            Toast.makeText(this,
                    "Por favor digite um número válido de paginas!",
                    Toast.LENGTH_LONG).show();
            return;
        }

        try {

            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date dataFimValidade = dateFormat.parse(dataFim);
            Date dataInicioValidade = dateFormat.parse(dataInicio);
            Date hoje = new Date();
            if (dataFimValidade.before(hoje)) {
                Toast.makeText(this,
                        "A data de término não pode ser no passado.",
                        Toast.LENGTH_LONG
                ).show();
                return;
            }
            if(dataFimValidade.before(dataInicioValidade)){
                Toast.makeText(this,
                        "A data de término não pode ser menor que a data inicio.",
                        Toast.LENGTH_LONG
                ).show();
                return;
            }

        } catch (ParseException e) {
            Toast.makeText(this,
                    "Erro ao validar a data. Use o formato DD/MM/YYYY.",
                    Toast.LENGTH_LONG
            ).show();
            return;
        }


        if (radioButtonId == R.id.radioButtonLido) {
            status = getString(R.string.lido);
        } else if (radioButtonId == R.id.radioButtonLendo) {
            status = getString(R.string.lendo);
        } else if (radioButtonId == R.id.radioButtonQueroLer) {
            status = getString(R.string.queroLer);
        } else {
            Toast.makeText(this,
                    "O status é obrigatório!",
                    Toast.LENGTH_LONG
            ).show();
            return;
        }



        if(dataInicio.isEmpty() || dataFim.isEmpty()){
            Toast.makeText(this,
                    "È necessário informar a data de inicio e fim!",
                    Toast.LENGTH_LONG
            ).show();
            return;
        }
;
        if(dataInicio.length() != 10
                    || dataInicio.charAt(2) != '/'
                    || dataInicio.charAt(5) != '/'

        ){
            Toast.makeText(this,
                    "Formato de data de início inválido. Favor usar o padrão DD/MM/YYYY.",
                    Toast.LENGTH_LONG
            ).show();
            return;
        }

        if(dataFim.length() != 10
                || dataFim.charAt(2) !='/'
                || dataFim.charAt(5) !='/'
        ){
            Toast.makeText(this,
                    "Formato de data de término inválido. Favor usar o padrão DD/MM/YYYY.",
                    Toast.LENGTH_LONG
            ).show();
            return;
        }


        String tipo = (String) spinnerTipo.getSelectedItem();
        if(tipo==null){
            Toast.makeText(this,
                    "O tipo é obrigatório!",
                    Toast.LENGTH_LONG
                    ).show();
            return;
        }



        Toast.makeText(this,
                getString(R.string.titulo)+" : "+ titulo+"\n"+
                getString(R.string.autor)+" : "+autor+"\n"+
                getString(R.string.numeroPaginas)+" : "+numeroPaginas+"\n"+
                getString(R.string.status)+" : "+status+"\n"+
                "Tipo :"+tipo+"\n"+
                (livroFavorito ? " Livro favoritado!":"")+"\n"

                , Toast.LENGTH_LONG).show();



    }




}
