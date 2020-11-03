package com.bitlicon.purolator.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bitlicon.purolator.R;
import com.bitlicon.purolator.activity.DetalleFacturaActivity;
import com.bitlicon.purolator.entities.Cliente;
import com.bitlicon.purolator.entities.Factura;
import com.bitlicon.purolator.util.Constantes;
import com.bitlicon.purolator.util.Util;

import java.util.ArrayList;

public class FacturaAdapter extends ArrayAdapter<Factura> {

    private ViewHolder viewHolder;
    private LayoutInflater mInflater;
    private Cliente cliente;

    public FacturaAdapter(Context context,
                          ArrayList<Factura> facturas, Cliente cliente) {
        super(context, 0, facturas);
        this.cliente = cliente;
        mInflater = LayoutInflater.from(context);

    }

    @Override
    public View getView(final int position, View convertview, ViewGroup parent) {
        final Factura factura = getItem(position);
        if (convertview == null || convertview.getTag() == null) {
            convertview = mInflater.inflate(R.layout.item_factura, null);
            viewHolder = new ViewHolder();
            viewHolder.tvFechaFactura = (TextView) convertview.findViewById(R.id.tvFechaFactura);
            viewHolder.tvNroFactura = (TextView) convertview.findViewById(R.id.tvNroFactura);
            viewHolder.tvLinea = (TextView) convertview.findViewById(R.id.tvLinea);
            viewHolder.tvImporteFactura = (TextView) convertview.findViewById(R.id.tvImporteFactura);
            convertview.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertview.getTag();
        }

        viewHolder.tvNroFactura.setText(factura.NumeroFactura);
        viewHolder.tvImporteFactura.setText(Util.formatoDineroSoles(factura.TotalImporte));
        viewHolder.tvFechaFactura.setText(Util.formatoFecha(Util.getDateFromString(factura.FechaFactura)));

        if (factura.Linea == Constantes.FILTECH) {
            viewHolder.tvLinea.setTextColor(getContext().getResources().getColor(R.color.rojo));
            viewHolder.tvLinea.setText("FIL");

        } else {
            viewHolder.tvLinea.setTextColor(getContext().getResources().getColor(R.color.celeste));
            viewHolder.tvLinea.setText("PUR");
        }
        convertview.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetalleFacturaActivity.class);
                intent.putExtra("cliente", cliente);
                intent.putExtra("factura", factura);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }
        });
        return convertview;
    }

    static class ViewHolder {
        TextView tvFechaFactura;
        TextView tvNroFactura;
        TextView tvImporteFactura;
        TextView tvLinea;
    }
}
