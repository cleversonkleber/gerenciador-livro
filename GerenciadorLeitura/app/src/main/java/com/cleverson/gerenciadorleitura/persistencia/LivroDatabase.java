package com.cleverson.gerenciadorleitura.persistencia;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.cleverson.gerenciadorleitura.Livro;
import com.cleverson.gerenciadorleitura.utils.ConversorDatas;

@Database(entities = {Livro.class}, version = 1,exportSchema = false)
@TypeConverters({ConversorDatas.class})
public abstract class LivroDatabase extends RoomDatabase {

    public abstract LivroDao getLivroDao();

    private static LivroDatabase INSTANCE;

    public static LivroDatabase getInstance(final Context context){
        if (INSTANCE != null){
            synchronized (LivroDatabase.class){
                if (INSTANCE==null){
                    INSTANCE = Room.databaseBuilder(
                            context,
                            LivroDatabase.class,
                            "livro.db"
                    ).allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }

}
