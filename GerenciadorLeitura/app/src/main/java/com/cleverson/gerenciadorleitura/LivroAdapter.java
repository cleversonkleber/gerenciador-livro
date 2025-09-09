package com.cleverson.gerenciadorleitura;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class LivroAdapter extends BaseAdapter {

    private Context context;
    private List<Livro> livroList;

    private String[] tipos;
    private String[] status;

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


    public LivroAdapter(Context context,List<Livro> livroList) {
        this.livroList = livroList;
        this.context = context;
        tipos = context.getResources().getStringArray(R.array.tipo);
        status= context.getResources().getStringArray(R.array.livros_status);
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
        LivroHolder livroHolder;
        if (convertView ==null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.linha_lista_livros, parent, false);
            livroHolder =  new LivroHolder();

            livroHolder.textViewValorTitulo = convertView.findViewById(R.id.textViewValorTitulo);
            livroHolder.textViewValorAutor = convertView.findViewById(R.id.textViewValorAutor);
            livroHolder.textViewValorPaginas = convertView.findViewById(R.id.textViewValorNPaginas);
            livroHolder.textViewValorDataInicio = convertView.findViewById(R.id.textViewValorDataInicio);
            livroHolder.textViewValorDataFim = convertView.findViewById(R.id.textViewValorDataFim);
            livroHolder.textViewValorStatus = convertView.findViewById(R.id.textViewValorStatus);
            livroHolder.textViewValorTipo = convertView.findViewById(R.id.textViewValorTipo);
            livroHolder.textViewValorFavorito = convertView.findViewById(R.id.textViewValorFavorito);
            livroHolder.textViewValorAnotacao = convertView.findViewById(R.id.textViewValorAnotaco);

            convertView.setTag(livroHolder);
        }else{
            livroHolder =(LivroHolder) convertView.getTag();
        }

        Livro livro = livroList.get(position);
        livroHolder.textViewValorTitulo.setText(livro.getTitulo());
        livroHolder.textViewValorAutor.setText(livro.getAutor());
        livroHolder.textViewValorPaginas.setText(String.valueOf(livro.getNumeroPaginas()));


        SimpleDateFormat formatadorData = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String dataInicioF = (livro.getDataInicio() != null) ? formatadorData.format(livro.getDataInicio()) : "-";
        String dataFimF = (livro.getDataFimLeitura() != null) ? formatadorData.format(livro.getDataFimLeitura()) : "-";

        livroHolder.textViewValorDataInicio.setText(dataInicioF);
        livroHolder.textViewValorDataFim.setText(dataFimF);


        livroHolder.textViewValorAnotacao.setText(livro.getAnotacao());
        if (livro.isFavorio()){
            livroHolder.textViewValorFavorito.setText(R.string.favorito);
        }else {
            livroHolder.textViewValorFavorito.setText(R.string.nao_favorito);
        }

        livroHolder.textViewValorStatus.setText(livro.getStatus());
        livroHolder.textViewValorTipo.setText(livro.getTipo());





        return convertView;
    }
}
