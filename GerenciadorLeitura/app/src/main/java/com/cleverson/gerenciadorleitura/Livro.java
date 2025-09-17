package com.cleverson.gerenciadorleitura;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;


public class Livro {

    public static Comparator<Livro> ordenaCrescente =  new Comparator<Livro>() {
        @Override
        public int compare(Livro livro1, Livro livro2) {
           return livro1.getTitulo().compareTo(livro2.getTitulo());
        }
    };

    public static Comparator<Livro> ordenaDeCrescente =  new Comparator<Livro>() {
        @Override
        public int compare(Livro livro1, Livro livro2) {
            return livro2.getTitulo().compareTo(livro1.getTitulo());
        }
    };
    private int id;
    private String titulo;
    private String autor;
    private int numeroPaginas;
    private Date dataInicio;
    private Date dataFimLeitura;
    private Tipo tipo;
    private boolean favorito;
    private Status status;
    private  String anotacao;

    public Livro() {
    }

    public Livro(int id, String titulo, String autor, int numeroPaginas,
                 Date dataInicio, Date dataFimLeitura, Tipo tipo, boolean favorito,
                 Status status, String anotacao) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.numeroPaginas = numeroPaginas;
        this.dataInicio = dataInicio;
        this.dataFimLeitura = dataFimLeitura;
        this.tipo = tipo;
        this.favorito = favorito;
        this.status = status;
        this.anotacao = anotacao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getNumeroPaginas() {
        return numeroPaginas;
    }

    public void setNumeroPaginas(int numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFimLeitura() {
        return dataFimLeitura;
    }

    public void setDataFimLeitura(Date dataFimLeitura) {
        this.dataFimLeitura = dataFimLeitura;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public boolean isFavorito() {
        return favorito;
    }

    public void setFavorio(boolean favorito) {
        this.favorito = favorito;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getAnotacao() {
        return anotacao;
    }

    public void setAnotacao(String anotacao) {
        this.anotacao = anotacao;
    }

    @Override
    public String toString() {
        SimpleDateFormat formatadorData = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String dataInicioF = (dataInicio != null) ? formatadorData.format(dataInicio) : "-";
        String dataFimF = (dataFimLeitura != null) ? formatadorData.format(dataFimLeitura) : "-";

        return  titulo + '\n' +
                autor + '\n' +
                numeroPaginas+ '\n' +
                dataInicioF + '\n' +
                dataFimF + '\n' +
                tipo + + '\n' +
                favorito + '\n' +
                status + '\n' +
                anotacao;

    }
}
