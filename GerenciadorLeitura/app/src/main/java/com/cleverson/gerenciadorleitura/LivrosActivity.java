package com.cleverson.gerenciadorleitura;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livros);

        listViewLivros =findViewById(R.id.listViewLivros);
        this.popularListLivros();

    }




    private void popularListLivros() {
        String[] livros_titulo = getResources().getStringArray(R.array.livros_titulo);
        String[] livros_autor = getResources().getStringArray(R.array.livros_autor);
        String[] livros_data_inicio = getResources().getStringArray(R.array.livros_data_inicio);
        String[] livros_data_fim = getResources().getStringArray(R.array.livros_data_fim);
        int[] livros_paginas = getResources().getIntArray(R.array.livros_paginas);
        int[] livros_tipo = getResources().getIntArray(R.array.livros_tipo);
        int[] livros_status = getResources().getIntArray(R.array.livros_status);
        int[] livros_favorito = getResources().getIntArray(R.array.livros_favorito);
        String[] livros_anotacao = getResources().getStringArray(R.array.livros_anotacao);
        livroList = new ArrayList<>();

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Livro livro;
        Status[] status = Status.values();
        Tipo[] tipos = Tipo.values();

        for (int cont =0;cont < livros_autor.length; cont++){
            Date dataInicio =null ;
            Date datafim=null;
            boolean favorito = (livros_favorito[cont] == 1);
            try {
                 dataInicio = formatter.parse(livros_data_inicio[cont]);
                 datafim = formatter.parse(livros_data_fim[cont]);

            }catch (ParseException e){
                e.printStackTrace();
            }
            livro = new Livro (
                    cont,
                    livros_titulo[cont],
                    livros_autor[cont],
                    livros_paginas[cont],
                    dataInicio,
                    datafim,
                    tipos[livros_tipo[cont]],
                    favorito,
                    status[livros_status[cont]],
                    livros_anotacao[cont]
            );
            livroList.add(livro);
        }

        ArrayAdapter<Livro> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                livroList
        );

        listViewLivros.setAdapter(adapter);


    }
}


















