package com.bitlicon.purolator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bitlicon.purolator.R;
import com.bitlicon.purolator.entities.LetraPedido;

import java.util.List;


public class LetraPedidoAdapter extends ArrayAdapter<LetraPedido> {

    public LetraPedidoAdapter(Context context, List<LetraPedido> letraPedidos) {
        super(context, 0, letraPedidos);
        // TODO Auto-generated constructor stub
    }

    @Override
    public View getView(final int position, View convertview, ViewGroup parent) {
        ViewHolder viewHolder;
        final LetraPedido letraPedido = getItem(position);
        viewHolder = new ViewHolder();

        if(convertview == null || convertview.getTag() == null)
        {
            convertview = LayoutInflater.from(getContext()).inflate(R.layout.item_letrapedido, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvNumero = (TextView) convertview.findViewById(R.id.tvNumero);
            viewHolder.tvFecha = (TextView) convertview.findViewById(R.id.tvFecha);
            viewHolder.tvDia = (TextView) convertview.findViewById(R.id.tvDia);
            convertview.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertview.getTag();
        }

        viewHolder.tvNumero.setText(String.valueOf(letraPedido.Numero));
        viewHolder.tvFecha.setText(letraPedido.Fecha);
        viewHolder.tvDia.setText(String.valueOf(letraPedido.Dia));
        convertview.setTag(viewHolder);

        return convertview;
    }

    static class ViewHolder
    {
        TextView tvNumero;
        TextView tvDia;
        TextView tvFecha;
    }
}
