package com.bitlicon.purolator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bitlicon.purolator.R;
import com.bitlicon.purolator.entities.Cliente;
import com.bitlicon.purolator.util.Util;

import java.util.ArrayList;

public class CobranzaAdapter extends ArrayAdapter<Cliente> {

    private ViewHolder viewHolder;
    private LayoutInflater mInflater;

    public CobranzaAdapter(Context context,
                           ArrayList<Cliente> clientes) {
        super(context, 0, clientes);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(final int position, View convertview, ViewGroup parent) {
        Cliente cliente = getItem(position);
        if (convertview == null || convertview.getTag() == null) {
            convertview = mInflater.inflate(R.layout.item_cobranza, null);
            viewHolder = new ViewHolder();
            viewHolder.txtNombre = (TextView) convertview.findViewById(R.id.cobranza_cliente);
            viewHolder.txtPurolator = (TextView) convertview.findViewById(R.id.cobranza_purolator);
            viewHolder.txtFiltech = (TextView) convertview.findViewById(R.id.cobranza_filtech);
            viewHolder.txtTotal = (TextView) convertview.findViewById(R.id.cobranza_total);
            convertview.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertview.getTag();
        }
        viewHolder.txtNombre.setText(cliente.Nombre);
        viewHolder.txtPurolator.setText(Util.formatoDineroSoles(cliente.DeudaPurolator));
        viewHolder.txtFiltech.setText(Util.formatoDineroSoles(cliente.DeudaFiltech));
        viewHolder.txtTotal.setText(Util.formatoDineroSoles(cliente.DeudaTotal));
        return convertview;
    }

    static class ViewHolder {
        TextView txtNombre;
        TextView txtPurolator;
        TextView txtFiltech;
        TextView txtTotal;
    }
}
