package com.cleverson.gerenciadorleitura;


import android.content.Intent;
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
                    mode.finish();
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
                view.setBackgroundColor(Color.LTGRAY);
                listViewLivros.setEnabled(false);
                actionMode = startSupportActionMode(actionCallback);

                return true;
            }
        });
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
                            Livro livro = new Livro(0,title,autor,numerPaginas,dataInicioFormat,
                                    datafimFormat,Tipo.values()[tipo],favorito,Status.valueOf(status),anotacao);

                            livroList.add(livro);
                            Collections.sort(livroList, Livro.ordenaCrescente);
                            livroAdapter.notifyDataSetChanged();

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
            }else {
                return super.onOptionsItemSelected(item);
            }
        }

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.livros_item_selecao, menu);

    }



    private void excluirLivro() {
        livroList.remove(posicaoSelecionada);
        livroAdapter.notifyDataSetChanged();
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
                            livro.setFavorio(favorito);
                            livro.setStatus(Status.valueOf(statusText));
                            livro.setTipo(tipos[tipo]);
                            livro.setAnotacao(anotacao);

                            Collections.sort(livroList, Livro.ordenaCrescente);

                            livroAdapter.notifyDataSetChanged();
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




}

















