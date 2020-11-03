package com.bitlicon.purolator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bitlicon.purolator.R;
import com.bitlicon.purolator.entities.Movimiento;
import com.bitlicon.purolator.lib.SessionManager;
import com.bitlicon.purolator.util.Util;

import java.util.ArrayList;


public class DeudaResumenAdapter extends ArrayAdapter<Movimiento> {

    private SessionManager manager;

    public DeudaResumenAdapter(Context context,
                               ArrayList<Movimiento> movimientos) {
        super(context, 0, movimientos);
        manager = new SessionManager(getContext());

    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {
        final ViewHolder viewHolder;
        final Movimiento movimiento = getItem(position);
        if (convertview == null) {
            convertview = LayoutInflater.from(getContext()).inflate(R.layout.item_deuda_resumen, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.txtTipo = (TextView) convertview.findViewById(R.id.txtTipo);
            viewHolder.txtSaldo = (TextView) convertview.findViewById(R.id.txtSaldo);
            viewHolder.txtFecha = (TextView) convertview.findViewById(R.id.txtFecha);
            viewHolder.txtNumero = (TextView) convertview.findViewById(R.id.txtNumero);
            convertview.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertview.getTag();
        }

        viewHolder.txtTipo.setText(movimiento.TipoDocumento);
        viewHolder.txtSaldo.setText(Util.formatoDineroSoles(movimiento.Saldo));
        viewHolder.txtFecha.setText(Util.formatoFecha(Util.getDateFromString(movimiento.FechaVencimiento)));
        if (movimiento.TipoDocumento != null && movimiento.TipoDocumento.trim().equalsIgnoreCase("L"))
        {
            viewHolder.txtNumero.setText(movimiento.Agencia + movimiento.Letban);
        }
        else
        {
            viewHolder.txtNumero.setText(movimiento.NumDocumento);
        }

        return convertview;
    }

    static class ViewHolder {
        TextView txtTipo;
        TextView txtSaldo;
        TextView txtFecha;
        TextView txtNumero;
    }
}