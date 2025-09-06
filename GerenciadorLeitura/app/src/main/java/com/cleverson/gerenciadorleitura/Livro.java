package com.cleverson.gerenciadorleitura;

import java.time.LocalDate;


public class Livro {
    private Long id;
    private String titulo;
    private String autor;
    private int numeroPaginas;
    private LocalDate dataInicio;
    private LocalDate dataFimLeitura;
    private Tipo tipo;
    private boolean favorio;
    private Status status;
    private  String anotacao;

    public Livro() {
    }

    public Livro(Long id, String titulo, String autor, int numeroPaginas,
                 LocalDate dataInicio, LocalDate dataFimLeitura, Tipo tipo, boolean favorio,
                 Status status, String anotacao) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.numeroPaginas = numeroPaginas;
        this.dataInicio = dataInicio;
        this.dataFimLeitura = dataFimLeitura;
        this.tipo = tipo;
        this.favorio = favorio;
        this.status = status;
        this.anotacao = anotacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFimLeitura() {
        return dataFimLeitura;
    }

    public void setDataFimLeitura(LocalDate dataFimLeitura) {
        this.dataFimLeitura = dataFimLeitura;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public boolean isFavorio() {
        return favorio;
    }

    public void setFavorio(boolean favorio) {
        this.favorio = favorio;
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
        return "Livro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", numeroPaginas=" + numeroPaginas +
                ", dataInicio=" + dataInicio +
                ", dataFimLeitura=" + dataFimLeitura +
                ", tipo='" + tipo + '\'' +
                ", favorio=" + favorio +
                ", status='" + status + '\'' +
                ", anotacao='" + anotacao + '\'' +
                '}';
    }
}
