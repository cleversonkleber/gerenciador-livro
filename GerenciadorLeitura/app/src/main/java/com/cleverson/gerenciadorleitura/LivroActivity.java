package com.cleverson.gerenciadorleitura;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.util.Date;

public class LivroActivity extends AppCompatActivity {
    public static final String KEY_TITULO = "KEY_TITULO";
    public static final String KEY_AUTOR = "KEY_AUTOR";
    public static final String KEY_NUM_PAGINAS = "KEY_NUM_PAGINAS";
    public static final String KEY_DATA_INICIO = "KEY_DATA_INICIO";
    public static final String KEY_DATA_FIM = "KEY_DATA_FIM";
    public static final String KEY_FAVORITO = "KEY_FAVORITO";
    public static final String KEY_STATUS = "KEY_STATUS";
    public static final String KEY_TIPO = "KEY_TIPO";
    public static final String KEY_ANOTACAO = "KEY_ANOTACAO";
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
        setTitle(R.string.cadastro_de_livros);

        editTextTitulo = findViewById(R.id.editTextTitulo);
        editTextAutor = findViewById(R.id.editTextAutor);
        editTextNPaginas = findViewById(R.id.editTextNPaginas);
        editTextDateInicio = findViewById(R.id.editTextDateInicio);
        editTextDateTermino = findViewById(R.id.editTextDateTermino);
        editTextAnotacao = findViewById(R.id.editTextAnotacao);

        checkBoxFavorito = findViewById(R.id.checkBoxFavorito);
        spinnerTipo = findViewById(R.id.spinnerTipo);
        radioGroup = findViewById(R.id.radioGroupStatus);

//
//        buttonLimpar = findViewById(R.id.buttonLimpar);
//        buttonSalvar = findViewById(R.id.buttonSalvar);

//        buttonLimpar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                limparCampos(v);
//            }
//        });
//
//        buttonSalvar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                salvarLivro(v);
//            }
//        });


    }


    public void limparCampos(){
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
    public void salvarLivro(){
        String autor = editTextAutor.getText().toString();
        String titulo = editTextTitulo.getText().toString();
        String numeroPaginas = editTextNPaginas.getText().toString();
        int radioButtonId = radioGroup.getCheckedRadioButtonId();
        String dataInicio = editTextDateInicio.getText().toString();
        String dataFim = editTextDateTermino.getText().toString();
        String anotacao = editTextAnotacao.getText().toString();
        int positionSpinner = spinnerTipo.getSelectedItemPosition();

        boolean livroFavorito = checkBoxFavorito.isChecked();
        Status status ;

        if (positionSpinner== AdapterView.INVALID_POSITION){
            Toast.makeText(this,
                    "O Tipo deve ser selecionado!",
                    Toast.LENGTH_LONG).show();
            return;
        }


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
            status = Status.LIDO;
        } else if (radioButtonId == R.id.radioButtonLendo) {
            status = Status.LENDO;
        } else if (radioButtonId == R.id.radioButtonQueroLer) {
            status = Status.QUEROLER;
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


        Intent intentResp = new Intent();
        intentResp.putExtra(KEY_TITULO, titulo);
        intentResp.putExtra(KEY_AUTOR, autor);
        intentResp.putExtra(KEY_NUM_PAGINAS, Integer.valueOf(numeroPaginas));
        intentResp.putExtra(KEY_DATA_INICIO, dataInicio);
        intentResp.putExtra(KEY_DATA_FIM, dataFim);
        intentResp.putExtra(KEY_FAVORITO, livroFavorito);
        intentResp.putExtra(KEY_STATUS, status.toString());
        intentResp.putExtra(KEY_TIPO, positionSpinner);
        intentResp.putExtra(KEY_ANOTACAO, anotacao);

        setResult(LivroActivity.RESULT_OK, intentResp);
        finish();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.livro_opcores, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int idMenuItem = item.getItemId();

        if (idMenuItem == R.id.menuItemSalvar){
            salvarLivro();
            return true;
        }else {
            if(idMenuItem == R.id.menuItemLimpar){
                limparCampos();
                return true;
            }else {
                return super.onOptionsItemSelected(item);
            }
        }

    }




}
