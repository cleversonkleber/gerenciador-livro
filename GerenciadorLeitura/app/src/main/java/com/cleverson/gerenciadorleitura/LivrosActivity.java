package com.cleverson.gerenciadorleitura;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class LivrosActivity extends AppCompatActivity {
    private ListView listViewLivros;
    private List<Livro> livroList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livros);
        listViewLivros =findViewById(R.id.listViewLivros);
        popularListLivros();
    }

    private void popularListLivros() {
        String[] lista_tipos = getResources().getStringArray(R.array.tipo);
        String[] lista_status = getResources().getStringArray(R.array.status);
        livroList = new ArrayList<>();

        Livro livro;




    }
}