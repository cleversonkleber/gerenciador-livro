package com.cleverson.gerenciadorleitura.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import com.cleverson.gerenciadorleitura.R;

public class UtilsAlert {
    private UtilsAlert(){}

    public static void mostrarAviso(Context context, int idMensagem ){
        mostrarAviso(context,context.getString(idMensagem), null);

    }

    public static void mostrarAviso(Context context,
                                    int idMensagem,
                                    DialogInterface.OnClickListener onClickListener){
        mostrarAviso(context,context.getString(idMensagem), onClickListener);
    }

    public static void mostrarAviso(Context context,
                                    String mensagem,
                                    DialogInterface.OnClickListener onClickListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(R.string.aviso);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setMessage(mensagem);
        builder.setNeutralButton(R.string.ok, onClickListener);

        AlertDialog alert =  builder.create();
        alert.show();
    }
}
