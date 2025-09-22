package com.cleverson.gerenciadorleitura.persistencia;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.cleverson.gerenciadorleitura.Livro;

import java.util.List;

@Dao
public interface LivroDao {
    @Insert
    long insert(Livro livro);

    @Delete
    int delete(Livro livro);

    @Update
    int update(Livro livro);

    @Query("select * from livro where id=:id")
    Livro findById(long id);

    @Query("select * from livro order by titulo asc")
    List<Livro> getAllLivrosAsc();

    @Query("select * from livro order by titulo desc")
    List<Livro> getAllLivrosDesc();




}
