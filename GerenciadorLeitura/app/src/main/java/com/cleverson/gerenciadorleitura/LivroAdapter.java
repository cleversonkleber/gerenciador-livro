package com.cleverson.gerenciadorleitura;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public class LivroAdapter extends BaseAdapter {

    private Context context;
    private List<Livro> livroList;

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
        

        return null;
    }
}
