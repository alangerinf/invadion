package com.bitlicon.purolator.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bitlicon.purolator.R;
import com.bitlicon.purolator.activity.BaseActivity;
import com.bitlicon.purolator.activity.FacturaResumenActivity;
import com.bitlicon.purolator.entities.Cliente;
import com.bitlicon.purolator.lib.SessionManager;
import com.bitlicon.purolator.util.Util;

import java.util.ArrayList;


public class ResumenAdapter extends ArrayAdapter<Cliente> {


    private ViewHolder viewHolder;
    private LayoutInflater mInflater;
    private SessionManager sessionManager;


    public ResumenAdapter(Context context,
                          ArrayList<Cliente> clientes) {
        super(context, 0, clientes);
        mInflater = LayoutInflater.from(context);
        sessionManager = new SessionManager(context);
    }

    @Override
    public View getView(final int position, View convertview, ViewGroup parent) {
        Cliente cliente = getItem(position);
        if (convertview == null || convertview.getTag() == null) {
            convertview = mInflater.inflate(R.layout.item_resumen, null);
            viewHolder = new ViewHolder();
            viewHolder.txtCliente = (TextView) convertview.findViewById(R.id.resumen_cliente);
            viewHolder.txtFiltech = (TextView) convertview.findViewById(R.id.resumen_filtech);
            viewHolder.txtPurolator = (TextView) convertview.findViewById(R.id.resumen_purolator);
            viewHolder.txtTotal = (TextView) convertview.findViewById(R.id.resumen_total);
            convertview.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertview.getTag();
        }
        viewHolder.txtCliente.setText(cliente.Nombre);
        viewHolder.txtFiltech.setText(Util.formatoDineroSoles(cliente.TotalFiltech));
        viewHolder.txtPurolator.setText(Util.formatoDineroSoles(cliente.TotalPurolator));
        viewHolder.txtTotal.setText(Util.formatoDineroSoles(cliente.Total));


        TextView.OnClickListener onClickListenerPur = new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cliente clienteEnviar = getItem(position);
                String linea = sessionManager.getPurolator();
                Intent intent = new Intent(getContext(), FacturaResumenActivity.class);
                intent.putExtra("cliente", clienteEnviar);
                intent.putExtra("linea", linea);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }

        };


        TextView.OnClickListener onClickListenerFil = new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cliente clienteEnviar = getItem(position);
                String linea = sessionManager.getFiltech();
                Intent intent = new Intent(getContext(), FacturaResumenActivity.class);
                intent.putExtra("cliente", clienteEnviar);
                intent.putExtra("linea", linea);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }

        };


        viewHolder.txtPurolator.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View view) {
                return mostrarDialogoContextual();
            }
        });

        viewHolder.txtPurolator.setOnClickListener(onClickListenerPur);
        viewHolder.txtFiltech.setOnClickListener(onClickListenerFil);

        viewHolder.txtFiltech.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View view) {
                return mostrarDialogoContextual();
            }
        });

        convertview.setLongClickable(true);
        return convertview;
    }

    static class ViewHolder {
        TextView txtCliente;
        TextView txtPurolator;
        TextView txtFiltech;
        TextView txtTotal;
    }

    public boolean mostrarDialogoContextual() {
        final Dialog dialog = new Dialog(getContext());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogo_context_sincronizar_movimiento);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();

        layoutParams.copyFrom(window.getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        window.setAttributes(layoutParams);
        dialog.show();

        LinearLayout lnSincronizar = (LinearLayout) dialog.findViewById(R.id.lnSincronizar);

        lnSincronizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                ((BaseActivity) getContext()).descargarMovimientos();
            }
        });

        return true;
    }
}
