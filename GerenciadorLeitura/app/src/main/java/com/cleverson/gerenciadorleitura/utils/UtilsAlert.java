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

        AlertDialog alertDialog =  builder.create();
        alertDialog.show();
    }

    public static void confirmarAcao(Context context,
                                     int idMensagem,
                                     DialogInterface.OnClickListener listenerSim,
                                     DialogInterface.OnClickListener listenerNao
    ){
        confirmarAcao(context, context.getString(idMensagem), listenerSim, listenerNao);
    }

    public static void confirmarAcao(Context context,
                                    String mensagem,
                                    DialogInterface.OnClickListener listenerSim,
                                    DialogInterface.OnClickListener listenerNao
    ){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(R.string.confirmation);
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setMessage(mensagem);

        builder.setNegativeButton(R.string.sim, listenerSim);
        builder.setPositiveButton(R.string.nao, listenerNao);

        AlertDialog alertDialog =  builder.create();
        alertDialog.show();
    }


}
