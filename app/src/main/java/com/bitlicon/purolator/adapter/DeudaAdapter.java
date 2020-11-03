package com.bitlicon.purolator.adapter;

import android.content.Context;
import android.graphics.Color;
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


public class DeudaAdapter extends ArrayAdapter<Movimiento> {

    private SessionManager manager;

    public DeudaAdapter(Context context,
                        ArrayList<Movimiento> movimientos) {
        super(context, 0, movimientos);
        manager = new SessionManager(getContext());
        // TODO Auto-generated constructor stub
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {
        final ViewHolder viewHolder;
        final Movimiento movimiento = getItem(position);
        if (convertview == null) {
            convertview = LayoutInflater.from(getContext()).inflate(R.layout.item_deuda, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.txtLinea = (TextView) convertview.findViewById(R.id.txtLinea);
            viewHolder.txtTipo = (TextView) convertview.findViewById(R.id.txtTipo);
            viewHolder.txtSaldo = (TextView) convertview.findViewById(R.id.txtSaldo);
            viewHolder.txtFecha = (TextView) convertview.findViewById(R.id.txtFecha);
            viewHolder.txtNumero = (TextView) convertview.findViewById(R.id.txtNumero);
            if(movimiento.Pagada.equals("B"))
            {
                viewHolder.txtLinea.setTextColor(getContext().getResources().getColor(R.color.verdemil));
                viewHolder.txtTipo.setTextColor(getContext().getResources().getColor(R.color.verdemil));
                viewHolder.txtSaldo.setTextColor(getContext().getResources().getColor(R.color.verdemil));
                viewHolder.txtFecha.setTextColor(getContext().getResources().getColor(R.color.verdemil));
                viewHolder.txtNumero.setTextColor(getContext().getResources().getColor(R.color.verdemil));
            }else if(movimiento.Pagada.equals("*"))
            {
                viewHolder.txtLinea.setTextColor(Color.RED);
                viewHolder.txtTipo.setTextColor(Color.RED);
                viewHolder.txtSaldo.setTextColor(Color.RED);
                viewHolder.txtFecha.setTextColor(Color.RED);
                viewHolder.txtNumero.setTextColor(Color.RED);
            }
            convertview.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertview.getTag();
        }

        if (movimiento.VendedorID != null && movimiento.VendedorID.trim().equalsIgnoreCase(manager.getPurolator())) {
            viewHolder.txtLinea.setText("PUR");
            //viewHolder.txtLinea.setTextColor(getContext().getResources().getColor(R.color.celeste));
        } else {
            viewHolder.txtLinea.setText("FIL");
            //viewHolder.txtLinea.setTextColor(getContext().getResources().getColor(R.color.rojo));
        }
        viewHolder.txtTipo.setText(movimiento.TipoDocumento);
        viewHolder.txtSaldo.setText(Util.formatoDineroSoles(movimiento.Saldo));
        viewHolder.txtFecha.setText(Util.formatoFecha(Util.getDateFromString(movimiento.FechaVencimiento)));
        if (movimiento.TipoDocumento != null && movimiento.TipoDocumento.trim().equalsIgnoreCase("L")) {
            viewHolder.txtNumero.setText(movimiento.Agencia + movimiento.Letban);

        } else {
            viewHolder.txtNumero.setText(movimiento.NumDocumento);
        }

        return convertview;
    }

    static class ViewHolder {
        TextView txtLinea;
        TextView txtTipo;
        TextView txtSaldo;
        TextView txtFecha;
        TextView txtNumero;
    }
}
