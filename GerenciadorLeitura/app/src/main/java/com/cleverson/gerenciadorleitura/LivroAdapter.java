package com.cleverson.gerenciadorleitura;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class LivroAdapter extends BaseAdapter {

    private Context context;
    private List<Livro> livroList;

    private static class LivroHolder{
        public TextView textViewValorTitulo;
        private TextView textViewValorAutor;
        private TextView textViewValorPaginas;
        private TextView textViewValorDataInicio;
        private TextView textViewValorDataFim;
        private TextView textViewValorTipo;
        private TextView textViewValorStatus;
        private TextView textViewValorFavorito;
        private TextView textViewValorAnotacao;
    }
    public LivroAdapter(List<Livro> livroList, Context context) {
        this.livroList = livroList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return livroList.size();
    }

    @Override
    public Object getItem(int position) {
        return livroList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LivroHolder livroHolder = null;
        if (convertView ==null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.linha_lista_livros, parent, false);
            livroHolder.textViewValorTitulo = convertView.findViewById(R.id.textViewValorTitulo);
            livroHolder.textViewValorAutor = convertView.findViewById(R.id.textViewValorAutor);
            livroHolder.textViewValorPaginas = convertView.findViewById(R.id.textViewValorNPaginas);
            livroHolder.textViewValorDataInicio = convertView.findViewById(R.id.editTextDateInicio);
            livroHolder.textViewValorDataFim = convertView.findViewById(R.id.textViewValorDataFim);
            livroHolder.textViewValorStatus = convertView.findViewById(R.id.textViewValorStatus);
            livroHolder.textViewValorTipo = convertView.findViewById(R.id.textViewValorTipo);
            livroHolder.textViewValorFavorito = convertView.findViewById(R.id.textViewValorFavorito);
            livroHolder.textViewValorAnotacao = convertView.findViewById(R.id.textViewValorAnotaco);

            
        }

        return null;
    }
}
