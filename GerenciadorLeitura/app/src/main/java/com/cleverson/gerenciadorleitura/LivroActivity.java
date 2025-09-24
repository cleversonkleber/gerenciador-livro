package com.cleverson.gerenciadorleitura;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cleverson.gerenciadorleitura.persistencia.LivroDatabase;
import com.cleverson.gerenciadorleitura.utils.UtilsAlert;

import java.text.ParseException;
import java.util.Date;

public class LivroActivity extends AppCompatActivity {

    public static final String KEY_ID ="ID";
    public static final String KEY_MODO = "MODO";
    public static final int MODO_NOVO = 0;
    public static final int MODO_EDITAR =1;
    public static final String KEY_SUGERIR_TIPO="SUGERIR_TIPO";
    public static final String KEY_ULTIMO_TIPO="ULTIMO_TIPO";


    private EditText editTextTitulo,
            editTextAutor,
            editTextNPaginas,
            editTextDateInicio,
            editTextDateTermino,
            editTextAnotacao;
    private Button buttonLimpar, buttonSalvar;
    private CheckBox checkBoxFavorito;
    private RadioGroup radioGroup;
    private Spinner spinnerTipo;
    private RadioButton radioLendo, radioQueroLer, radioLido;
    private int modo;
    private Livro livroOriginal;
    private boolean sugerirTipo=false;
    private int ultimoTipo =0;


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

        radioLendo = findViewById(R.id.radioButtonLendo);
        radioLido = findViewById(R.id.radioButtonLido);
        radioQueroLer = findViewById(R.id.radioButtonQueroLer);

        String dataFimValidade = null;
        String dataInicioValidade = null;

        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");



        lerPreferncias();

        Intent intentAbertur =  getIntent();
        Bundle bundle = intentAbertur.getExtras();
        if(bundle != null){
            modo = bundle.getInt(KEY_MODO);
            if(modo == MODO_NOVO){
                setTitle(R.string.cadastro_de_livros);
                if(sugerirTipo){
                    spinnerTipo.setSelection(ultimoTipo);
                }
            }else {

                setTitle(getString(R.string.editar_livro));

                Long id = bundle.getLong(KEY_ID);

                LivroDatabase livroDatabase = LivroDatabase.getInstance(this);

                livroOriginal= livroDatabase.getLivroDao().findById(id);
                dataFimValidade = dateFormat.format(livroOriginal.getDataFimLeitura());
                dataInicioValidade = dateFormat.format(livroOriginal.getDataInicio());


                editTextTitulo.setText(livroOriginal.getTitulo());
                editTextAutor.setText(livroOriginal.getAutor());
                editTextNPaginas.setText(String.valueOf(livroOriginal.getNumeroPaginas()));
                editTextDateInicio.setText(dataInicioValidade);
                editTextDateTermino.setText(dataFimValidade);
                editTextAnotacao.setText(livroOriginal.getAnotacao());

                checkBoxFavorito.setChecked(livroOriginal.isFavorito());
                spinnerTipo.setSelection(livroOriginal.getTipo().ordinal());

                Status status = livroOriginal.getStatus();
                if(status == Status.LIDO){
                    radioLido.setChecked(true);
                }else
                    if (status ==Status.LENDO){
                        radioLendo.setChecked(true);
                    }else
                        if (status==Status.QUEROLER){
                            radioQueroLer.setChecked(true);
                        }

               editTextTitulo.requestFocus();
               editTextTitulo.setSelection(editTextTitulo.getText().length());

            }
        }

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

        UtilsAlert.mostrarAviso(this,
                R.string.os_o_valor_dos_campos_foram_apagados
        );


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

        Date dataFimValidade = null;
        Date dataInicioValidade = null;


        boolean livroFavorito = checkBoxFavorito.isChecked();
        Status status ;

        if (positionSpinner== AdapterView.INVALID_POSITION){
            UtilsAlert.mostrarAviso(this,
                    R.string.o_tipo_deve_ser_selecionado
            );
            return;
        }


        if(autor.isEmpty() || titulo.isEmpty() || numeroPaginas.isEmpty()){
            UtilsAlert.mostrarAviso(this,
                    R.string.obrigatorio_incluir_autor_e_titulo_do_livro_e_a_quantidade_de_paginas
            );
                return;

        }

        try {
             int  paginas = Integer.parseInt(numeroPaginas);
             if(paginas < 10) {
                 UtilsAlert.mostrarAviso(this,
                         R.string.o_livro_deve_conter_no_m_nimo_10_paginas
                 );
                return;

            }
        }catch (NumberFormatException e){
            UtilsAlert.mostrarAviso(this,
                    R.string.por_favor_digite_um_n_mero_v_lido_de_paginas
            );


            return;
        }

        try {

            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dataFimValidade = dateFormat.parse(dataFim);
            dataInicioValidade = dateFormat.parse(dataInicio);

            if(dataFimValidade.before(dataInicioValidade)){
                UtilsAlert.mostrarAviso(this,
                        R.string.a_data_de_t_rmino_n_o_pode_ser_menor_que_a_data_inicio
                );

                return;
            }


        } catch (ParseException e) {
            UtilsAlert.mostrarAviso(this,
                    R.string.erro_ao_validar_a_data_use_o_formato_dd_mm_yyyy
            );

            return;
        }


        if (radioButtonId == R.id.radioButtonLido) {
            status = Status.LIDO;
        } else if (radioButtonId == R.id.radioButtonLendo) {
            status = Status.LENDO;
        } else if (radioButtonId == R.id.radioButtonQueroLer) {
            status = Status.QUEROLER;
        } else {
            UtilsAlert.mostrarAviso(this,
                    R.string.o_status_obrigat_rio
            );

            return;
        }



        if(dataInicio.isEmpty() || dataFim.isEmpty()){
            UtilsAlert.mostrarAviso(this,
                    R.string.necess_rio_informar_a_data_de_inicio_e_fim
            );


            return;
        }
;
        if(dataInicio.length() != 10
                    || dataInicio.charAt(2) != '/'
                    || dataInicio.charAt(5) != '/'

        ){
            UtilsAlert.mostrarAviso(this,
                    R.string.formato_de_data_de_in_cio_inv_lido_favor_usar_o_padr_o_dd_mm_yyyy
            );


            return;
        }

        if(dataFim.length() != 10
                || dataFim.charAt(2) !='/'
                || dataFim.charAt(5) !='/'
        ){
            UtilsAlert.mostrarAviso(this,
                    R.string.formato_de_data_de_t_rmino_inv_lido_favor_usar_o_padr_o_dd_mm_yyyy
            );
            return;
        }


        String tipo = (String) spinnerTipo.getSelectedItem();
        if(tipo==null){
            UtilsAlert.mostrarAviso(this,
                    R.string.o_tipo_obrigat_rio
            );

            return;
        }
        Tipo[] tipos=Tipo.values();
        salvarUltimoTipo(positionSpinner);
        Livro livro = new Livro(
                titulo,
                autor,
                Integer.valueOf(numeroPaginas),
                dataInicioValidade,
                dataFimValidade,
                tipos[positionSpinner],
                livroFavorito,
                status,
                anotacao
        );

        if (livro.equals(livroOriginal)){
            setResult(LivroActivity.RESULT_CANCELED);
            finish();
            return;
        }

        Intent intentResp = new Intent();

        LivroDatabase livroDatabase = LivroDatabase.getInstance(this);
        if(modo==MODO_NOVO){
            long novoId= livroDatabase.getLivroDao().insert(livro);

            if(novoId <=0){
                UtilsAlert.mostrarAviso(this, getString(R.string.erro_ao_tentar_inserir), null);
                return;
            }
            livro.setId(novoId);

        }else{
            livro.setId(livroOriginal.getId());
            int quantidadeAlterada  = livroDatabase.getLivroDao().update(livro);
            if(quantidadeAlterada != 1){
                UtilsAlert.mostrarAviso(this, getString(R.string.erro_ao_tentar_alterar_o_resgistro), null);
                return;
            }
        }

        intentResp.putExtra(KEY_ID, livro.getId());
        setResult(LivroActivity.RESULT_OK, intentResp);
        finish();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.livro_operacoes, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.menuSugerirTipo);
        item.setChecked(sugerirTipo);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int idMenuItem = item.getItemId();

        if (idMenuItem == R.id.menuItemSalvar){
            salvarLivro();
            return true;
        }else
            if(idMenuItem == R.id.menuItemLimpar){
                limparCampos();
                return true;
            }else
                if (idMenuItem == R.id.menuSugerirTipo){
                    boolean valor = !item.isChecked();
                    salvarSugerirTipo(valor);
                    item.setChecked(valor);
                    if(sugerirTipo){
                        spinnerTipo.setSelection(ultimoTipo);
                    }
                    return true;
                }else {
                    return super.onOptionsItemSelected(item);
                }


    }

    private void lerPreferncias(){
        SharedPreferences preferences = getSharedPreferences(LivrosActivity.ARQUIVO_PREFERENCIAS, Context.MODE_PRIVATE);
        sugerirTipo =  preferences.getBoolean(KEY_SUGERIR_TIPO, sugerirTipo);
        ultimoTipo = preferences.getInt(KEY_ULTIMO_TIPO, ultimoTipo);


    }

    private void salvarSugerirTipo(boolean novoValor){
        SharedPreferences preferences = getSharedPreferences(LivrosActivity.ARQUIVO_PREFERENCIAS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(KEY_SUGERIR_TIPO, novoValor);

        editor.commit();
        sugerirTipo = novoValor;
    }


    private void salvarUltimoTipo(int novoValor){
        SharedPreferences preferences = getSharedPreferences(LivrosActivity.ARQUIVO_PREFERENCIAS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(KEY_ULTIMO_TIPO, novoValor);

        editor.commit();
        ultimoTipo = novoValor;
    }






}
