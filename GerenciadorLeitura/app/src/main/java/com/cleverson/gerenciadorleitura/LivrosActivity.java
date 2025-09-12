package com.cleverson.gerenciadorleitura;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LivrosActivity extends AppCompatActivity {
    private ListView listViewLivros;
    private List<Livro> livroList;

    private LivroAdapter livroAdapter;


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
        this.popularListLivros();

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
//        String[] livros_titulo = getResources().getStringArray(R.array.livros_titulo);
//        String[] livros_autor = getResources().getStringArray(R.array.livros_autor);
//        String[] livros_data_inicio = getResources().getStringArray(R.array.livros_data_inicio);
//        String[] livros_data_fim = getResources().getStringArray(R.array.livros_data_fim);
//        int[] livros_paginas = getResources().getIntArray(R.array.livros_paginas);
//        int[] livros_tipo = getResources().getIntArray(R.array.livros_tipo);
//        int[] livros_status = getResources().getIntArray(R.array.livros_status);
//        int[] livros_favorito = getResources().getIntArray(R.array.livros_favorito);
//        String[] livros_anotacao = getResources().getStringArray(R.array.livros_anotacao);
        livroList = new ArrayList<>();

//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
//        Livro livro;
//        Status[] status = Status.values();
//        Tipo[] tipos = Tipo.values();
//
//        for (int cont =0;cont < livros_autor.length; cont++){
//            Date dataInicio =null ;
//            Date datafim=null;
//            boolean favorito = (livros_favorito[cont] == 1);
//            try {
//                 dataInicio = formatter.parse(livros_data_inicio[cont]);
//                 datafim = formatter.parse(livros_data_fim[cont]);
//
//            }catch (ParseException e){
//                e.printStackTrace();
//            }
//            livro = new Livro (
//                    cont,
//                    livros_titulo[cont],
//                    livros_autor[cont],
//                    livros_paginas[cont],
//                    dataInicio,
//                    datafim,
//                    tipos[livros_tipo[cont]],
//                    favorito,
//                    status[livros_status[cont]],
//                    livros_anotacao[cont]
//            );
//            livroList.add(livro);
//        }

        livroAdapter = new LivroAdapter(this,livroList);

        listViewLivros.setAdapter(livroAdapter);


    }

    public void abrirSobre(View view){
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
                        }
                    }
                }
            });

    public void adicionarLivro(View view){
        Intent intentAbertura=  new Intent(this, LivroActivity.class);
        resultLauncher.launch(intentAbertura);
    }
}

















