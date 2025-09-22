package com.cleverson.gerenciadorleitura;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;

import com.cleverson.gerenciadorleitura.utils.UtilsAlert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LivrosActivity extends AppCompatActivity {
    private ListView listViewLivros;
    private List<Livro> livroList;

    private LivroAdapter livroAdapter;

    private int posicaoSelecionada = -1;

    private ActionMode actionMode;

    private View viewSelecionada;
    private Drawable backgroudDrawable;

    public static final String ARQUIVO_PREFERENCIAS="com.cleverson.gerenciadorleitura.PREFERENCIAS";

    public static final String KEY_ORDENACAO_ASC="KEY_ORDENACAO_ASC";

    public static final boolean PADRAO_INICIAL_ORDENACAO_ASCENDENTE=true;
    private boolean ordenaAsc = PADRAO_INICIAL_ORDENACAO_ASCENDENTE;

    private MenuItem menuItemAcao;

    private ActionMode.Callback actionCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.livros_item_selecao, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
             int idMenuItem = item.getItemId();
            if(idMenuItem == R.id.menuItemEditar){
                editarLivro();
                return true;
            }else {
                if(idMenuItem == R.id.menuItemExcluir){
                    excluirLivro();
                    return true;
                }else {

                    return false;
                }
            }

        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            if(viewSelecionada != null){
                viewSelecionada.setBackground(backgroudDrawable);

            }
            actionMode = null;
            viewSelecionada=null;
            backgroudDrawable=null;
            listViewLivros.setEnabled(true);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livros);

        setTitle(R.string.controle_leitura_livros);

        listViewLivros =findViewById(R.id.listViewLivros);
        listViewLivros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Livro livro =(Livro) listViewLivros.getItemAtPosition(position);
                String status = statusLeitura(livro);

                Toast.makeText(
                        getApplicationContext(),
                        getString(R.string.livro_de_titulo)+
                                livro.getTitulo()+
                                getString(R.string.status_leitura)+
                                status
                        ,Toast.LENGTH_LONG
                ).show();
            }
        });

        listViewLivros.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {


            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(actionMode != null) {
                    return false;
                }
                posicaoSelecionada = position;
                viewSelecionada = view;
                backgroudDrawable = view.getBackground();
                view.setBackgroundColor(getColor(R.color.corSelecionado));
                listViewLivros.setEnabled(false);
                actionMode = startSupportActionMode(actionCallback);

                return true;
            }
        });
        lerPreferencias();
        this.popularListLivros();
        registerForContextMenu(listViewLivros);
    }

    private String statusLeitura(Livro livro){
        String status=null;
        switch (livro.getStatus()){
            case LIDO:
                status = getString(R.string.lido);
                break;
            case LENDO:
                status = getString(R.string.lendo);
                break;
            case VOULER:
                status = getString(R.string.vou_ler);
                break;
            case QUEROLER:
                status = getString(R.string.quero_ler);
                break;
        }
        return status;
    }

    private void popularListLivros() {
        livroList = new ArrayList<>();
        livroAdapter = new LivroAdapter(this,livroList);

        listViewLivros.setAdapter(livroAdapter);
        livroAdapter.notifyDataSetChanged();
    }

    public void abrirSobre(){
        Intent intentAbertura = new Intent(this, SobreActivity.class);
        startActivity(intentAbertura);
    }
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    ActivityResultLauncher<Intent>resultLauncher=registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == LivrosActivity.RESULT_OK){
                        Intent intent= result.getData();
                        Bundle bundle =  intent.getExtras();

                        if (bundle != null){
                            String title = bundle.getString(LivroActivity.KEY_TITULO);
                            String autor = bundle.getString(LivroActivity.KEY_AUTOR);
                            int numerPaginas = bundle.getInt(LivroActivity.KEY_NUM_PAGINAS);
                            String dataInicio = bundle.getString(LivroActivity.KEY_DATA_INICIO);
                            String dataFim = bundle.getString(LivroActivity.KEY_DATA_FIM);
                            boolean favorito = bundle.getBoolean(LivroActivity.KEY_FAVORITO);
                            int tipo = bundle.getInt(LivroActivity.KEY_TIPO);
                            String status = bundle.getString(LivroActivity.KEY_STATUS);
                            String anotacao = bundle.getString(LivroActivity.KEY_ANOTACAO);
                            Date dataInicioFormat =null ;
                            Date datafimFormat=null;

                            try {
                                dataInicioFormat = formatter.parse(dataInicio);
                                datafimFormat = formatter.parse(dataFim);
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }
                            Livro livro = new Livro(title,autor,numerPaginas,dataInicioFormat,
                                    datafimFormat,Tipo.values()[tipo],favorito,Status.valueOf(status),anotacao);

                            livroList.add(livro);

                        }
                    }
                    posicaoSelecionada =-1;

                }
            });

    public void adicionarLivro(){
        Intent intentAbertura=  new Intent(this, LivroActivity.class);
        intentAbertura.putExtra(LivroActivity.KEY_MODO,  LivroActivity.MODO_NOVO);

        resultLauncher.launch(intentAbertura);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.livros_opcoes, menu);
        menuItemAcao = menu.findItem(R.id.menuItemOrdenacao);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int idMenuItem = item.getItemId();

        if (idMenuItem == R.id.menuItemAdicionar){
            adicionarLivro();
            return true;
        }else {
            if(idMenuItem == R.id.menuItemSobre){
                abrirSobre();
                return true;
            }else
            if(idMenuItem==R.id.menuItemOrdenacao){

                salvarPreferenciaOrdenacaoAscendente(!ordenaAsc);
                atualizarIconOrdenacao();
                ordenarLista();
                return true;
            }else{
                if (idMenuItem == R.id.menuItemRestaurar){
                    confirmarRestaurarPadroes();

                    return true;
                }else {
                    return super.onOptionsItemSelected(item);
                }
            }
        }

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.livros_item_selecao, menu);

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        atualizarIconOrdenacao();
        return true;
    }

    private void excluirLivro() {
        Livro livro = livroList.get(posicaoSelecionada);
        String mensagem = getString(R.string.tem_certeza_que_quer_excluir)+ livro.getTitulo() + "\"";
        DialogInterface.OnClickListener listenerSim = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                livroList.remove(posicaoSelecionada);
                livroAdapter.notifyDataSetChanged();
                actionMode.finish();

            }
        };

        UtilsAlert.confirmarAcao(this, mensagem, listenerSim, null);

    }

    ActivityResultLauncher<Intent>launcherEditarLivro=registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == LivrosActivity.RESULT_OK){
                        Intent intent= result.getData();
                        Bundle bundle =  intent.getExtras();

                        if (bundle != null){
                            Tipo[] tipos = Tipo.values();
                            String title = bundle.getString(LivroActivity.KEY_TITULO);
                            String autor = bundle.getString(LivroActivity.KEY_AUTOR);
                            int numerPaginas = bundle.getInt(LivroActivity.KEY_NUM_PAGINAS);
                            String dataInicio = bundle.getString(LivroActivity.KEY_DATA_INICIO);
                            String dataFim = bundle.getString(LivroActivity.KEY_DATA_FIM);
                            boolean favorito = bundle.getBoolean(LivroActivity.KEY_FAVORITO);
                            int tipo = bundle.getInt(LivroActivity.KEY_TIPO);
                            String statusText = bundle.getString(LivroActivity.KEY_STATUS);
                            String anotacao = bundle.getString(LivroActivity.KEY_ANOTACAO);
                            Date dataInicioFormat =null ;
                            Date datafimFormat=null;

                            try {
                                dataInicioFormat = formatter.parse(dataInicio);
                                datafimFormat = formatter.parse(dataFim);
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }
                            Livro livro = livroList.get(posicaoSelecionada);
                            livro.setTitulo(title);
                            livro.setAutor(autor);
                            livro.setNumeroPaginas(numerPaginas);
                            livro.setDataInicio(dataInicioFormat);
                            livro.setDataFimLeitura(datafimFormat);
                            livro.setFavorito(favorito);
                            livro.setStatus(Status.valueOf(statusText));
                            livro.setTipo(tipos[tipo]);
                            livro.setAnotacao(anotacao);


                        }
                    }
                    posicaoSelecionada = -1;
                    if (actionMode != null){
                        actionMode.finish();
                    }
                }
            });

    private void editarLivro() {

        Livro livro =  livroList.get(posicaoSelecionada);
        Intent intentAbertura = new Intent(this, LivroActivity.class);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String dataInicioFormat =null ;
        String datafimFormat=null;

        dataInicioFormat = formatter.format(livro.getDataInicio());
        datafimFormat = formatter.format(livro.getDataFimLeitura());


        intentAbertura.putExtra(LivroActivity.KEY_MODO,  LivroActivity.MODO_EDITAR);
        intentAbertura.putExtra(LivroActivity.KEY_TITULO,livro.getTitulo());
        intentAbertura.putExtra(LivroActivity.KEY_AUTOR,livro.getAutor());
        intentAbertura.putExtra(LivroActivity.KEY_NUM_PAGINAS,livro.getNumeroPaginas());
        intentAbertura.putExtra(LivroActivity.KEY_DATA_INICIO,dataInicioFormat);
        intentAbertura.putExtra(LivroActivity.KEY_DATA_FIM,datafimFormat);
        intentAbertura.putExtra(LivroActivity.KEY_FAVORITO,livro.isFavorito());
        intentAbertura.putExtra(LivroActivity.KEY_TIPO,livro.getTipo().ordinal());
        intentAbertura.putExtra(LivroActivity.KEY_STATUS,livro.getStatus().toString());
        intentAbertura.putExtra(LivroActivity.KEY_ANOTACAO,livro.getAnotacao());
        launcherEditarLivro.launch(intentAbertura);

    }

    private void ordenarLista(){
        if(ordenaAsc){
            Collections.sort(livroList, Livro.ordenaCrescente);
        }else {
            Collections.sort(livroList, Livro.ordenaDeCrescente);
        }
        livroAdapter.notifyDataSetChanged();
    }

    private void lerPreferencias(){
        SharedPreferences preferences = getSharedPreferences(ARQUIVO_PREFERENCIAS, Context.MODE_PRIVATE);
        ordenaAsc = preferences.getBoolean(KEY_ORDENACAO_ASC, ordenaAsc);

    }

    private void salvarPreferenciaOrdenacaoAscendente(boolean novoValor){
        SharedPreferences preferences = getSharedPreferences(ARQUIVO_PREFERENCIAS, Context.MODE_PRIVATE);
        SharedPreferences.Editor edito = preferences.edit();
        edito.commit();

        ordenaAsc = novoValor;
    }

    private  void atualizarIconOrdenacao(){
        if (ordenaAsc){
            menuItemAcao.setIcon(R.drawable.ic_action_asc_order);
        }else {
           menuItemAcao.setIcon(R.drawable.ic_action_desc_order);
        }
    }

    private void confirmarRestaurarPadroes() {

        DialogInterface.OnClickListener listenerSim = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                restaurarPadres();
                atualizarIconOrdenacao();
                ordenarLista();

                Toast.makeText(LivrosActivity.this,
                        R.string.restaurar_configuracoes_padrao_instalacao,
                        Toast.LENGTH_LONG
                ).show();

            }
        };
        UtilsAlert.confirmarAcao(this,
                R.string.certeza_que_quer_restaurar_para_as_configura_es_de_instala_o,
                listenerSim,
                null
        );


    }
    private void restaurarPadres(){
        SharedPreferences preferences = getSharedPreferences(ARQUIVO_PREFERENCIAS, Context.MODE_PRIVATE);
        SharedPreferences.Editor edito = preferences.edit();
        edito.clear();
        edito.commit();
        ordenaAsc = PADRAO_INICIAL_ORDENACAO_ASCENDENTE;
    }


}

















