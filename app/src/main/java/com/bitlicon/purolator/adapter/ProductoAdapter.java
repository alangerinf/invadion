package com.bitlicon.purolator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bitlicon.purolator.R;
import com.bitlicon.purolator.entities.DetalleFactura;
import com.bitlicon.purolator.util.Util;

import java.util.ArrayList;

public class ProductoAdapter extends ArrayAdapter<DetalleFactura> {

    public ProductoAdapter(Context context,
                           ArrayList<DetalleFactura> movimientos) {
        super(context, 0, movimientos);
        // TODO Auto-generated constructor stub
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {
        final ViewHolder viewHolder;
        final DetalleFactura detalleFactura = getItem(position);
        if (convertview == null) {
            convertview = LayoutInflater.from(getContext()).inflate(R.layout.item_producto, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.txtCodigo = (TextView) convertview.findViewById(R.id.txtCodigo);
            viewHolder.txtCantidad = (TextView) convertview.findViewById(R.id.txtCantidad);
            viewHolder.txtPrecioUnitario = (TextView) convertview.findViewById(R.id.txtPrecioUnitario);
            viewHolder.txtValor = (TextView) convertview.findViewById(R.id.txtValor);
            viewHolder.txtPrecioLista = (TextView) convertview.findViewById(R.id.txtPrecioLista);
            convertview.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertview.getTag();
        }
        viewHolder.txtCodigo.setText(detalleFactura.Descripcion);
        viewHolder.txtCantidad.setText(String.valueOf((int) detalleFactura.Cantidad));
        double valor = getMonto(detalleFactura);
        viewHolder.txtValor.setText(Util.formatoDineroSoles(valor));

        viewHolder.txtPrecioUnitario.setText(Util.formatoDineroSoles(valor / detalleFactura.Cantidad));
        viewHolder.txtPrecioLista.setText(Util.formatoDineroSoles(detalleFactura.Precio));


        return convertview;
    }

    private double getMonto(DetalleFactura detalleFactura) {
        return detalleFactura.PrecioDolares * detalleFactura.Cantidad * (1 - detalleFactura.DescuentoDistribuidor / 100) * (1 - detalleFactura.DescuentoPromocion / 100) * (1 - detalleFactura.DescuentoOrdenCompra / 100);
    }

    static class ViewHolder {
        TextView txtPrecioUnitario;
        TextView txtValor;
        TextView txtCantidad;
        TextView txtCodigo;
        TextView txtPrecioLista;
    }
}
