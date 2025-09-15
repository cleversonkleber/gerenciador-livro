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

        if (livro.isFavorito()){
            livroHolder.textViewValorFavorito.setText("Sim");
        }else {
            livroHolder.textViewValorFavorito.setText("Não");
        }

        String tipo=null;
        switch (livro.getTipo()){
            case ACAO:
                tipo = context.getString(R.string.acao);
                break;
            case AVENTURA:
                tipo = context.getString(R.string.aventura);

                break;
            case DRAMA:
                tipo = context.getString(R.string.drama);
                break;
            case POESIA:
                tipo = context.getString(R.string.poesia);
                break;
            case TERRRO:
                tipo = context.getString(R.string.terror);
                break;
            case CRISTAO:
                tipo = context.getString(R.string.cristao);
                break;
            case ROMANCE:
                tipo = context.getString(R.string.romance);
                break;
            case POLITICA:
                tipo = context.getString(R.string.politica);
                break;
            case TECNOLOGIA:
                tipo = context.getString(R.string.tecnologia);
                break;
            case FICCAO_CIENTIFICA:
                tipo = context.getString(R.string.ficcaoo_cientifica);
                break;
        }


        String status=null;
        switch (livro.getStatus()){
            case LIDO:
                status = context.getString(R.string.lido);
                break;
            case LENDO:
                status = context.getString(R.string.lendo);
                break;
            case VOULER:
                status =context.getString(R.string.vou_ler);
                break;
            case QUEROLER:
                status = context.getString(R.string.quero_ler);
                break;
        }

        livroHolder.textViewValorTipo.setText(tipo);
        livroHolder.textViewValorStatus.setText(status);
        return convertView;
    }

}
