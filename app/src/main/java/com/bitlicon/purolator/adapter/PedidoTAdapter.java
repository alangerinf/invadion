package com.bitlicon.purolator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bitlicon.purolator.R;
import com.bitlicon.purolator.dao.DetallePedidoDAO;
import com.bitlicon.purolator.dao.VendedorDAO;
import com.bitlicon.purolator.entities.Cliente;
import com.bitlicon.purolator.entities.DetallePedido;
import com.bitlicon.purolator.entities.Factura;
import com.bitlicon.purolator.entities.Pedido;
import com.bitlicon.purolator.entities.Vendedor;
import com.bitlicon.purolator.util.Constantes;
import com.bitlicon.purolator.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lokerfy on 22/04/2016.
 */
public class PedidoTAdapter extends ArrayAdapter<Pedido> {

    private ViewHolder viewHolder;
    private LayoutInflater mInflater;
    private VendedorDAO vendedorDAO;
    private DetallePedidoDAO detallePedidoDAO;
    private Vendedor vendedor;
    public PedidoTAdapter(Context context, List<Pedido> pedidos) {
        super(context, 0, pedidos);
        mInflater = LayoutInflater.from(context);
        vendedorDAO = new VendedorDAO(getContext());
        detallePedidoDAO = new DetallePedidoDAO(getContext());
        vendedor = vendedorDAO.buscarVendedor();
    }

    @Override
    public View getView(final int position, View convertview, ViewGroup parent) {
        final Pedido pedido = getItem(position);
        if (convertview == null || convertview.getTag() == null) {
            convertview = mInflater.inflate(R.layout.item_pedido_t, null);
            viewHolder = new ViewHolder();
            viewHolder.tvNroPedido = (TextView) convertview.findViewById(R.id.tvNroPedido);
            viewHolder.tvLinea = (TextView) convertview.findViewById(R.id.tvLinea);
            viewHolder.tvImportePedido = (TextView) convertview.findViewById(R.id.tvImportePedido);

            convertview.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertview.getTag();
        }

        viewHolder.tvNroPedido.setText(pedido.NumeroPedido);
        viewHolder.tvImportePedido.setText(Util.formatoDineroSoles(pedido.TotalImporte - pedido.TotalDescuento));
        if (pedido.VendedorID.equals(vendedor.Linea2)) {
            viewHolder.tvLinea.setTextColor(getContext().getResources().getColor(R.color.rojo));
            viewHolder.tvLinea.setText("FIL");
        } else {
            viewHolder.tvLinea.setTextColor(getContext().getResources().getColor(R.color.celeste));
            viewHolder.tvLinea.setText("PUR");
        }

        return convertview;
    }

    static class ViewHolder {
        TextView tvNroPedido;
        TextView tvImportePedido;
        TextView tvLinea;
    }
}
