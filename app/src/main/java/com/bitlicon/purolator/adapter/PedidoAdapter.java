package com.bitlicon.purolator.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bitlicon.purolator.R;
import com.bitlicon.purolator.activity.AgregarPedidoActivity;
import com.bitlicon.purolator.dao.VendedorDAO;
import com.bitlicon.purolator.entities.Cliente;
import com.bitlicon.purolator.entities.Factura;
import com.bitlicon.purolator.entities.Pedido;
import com.bitlicon.purolator.entities.Vendedor;
import com.bitlicon.purolator.util.Constantes;
import com.bitlicon.purolator.util.Util;

import java.util.ArrayList;
import java.util.List;


public class PedidoAdapter extends ArrayAdapter<Pedido> {

    private ViewHolder viewHolder;
    private LayoutInflater mInflater;
    private Cliente cliente;
    private Vendedor vendedor;
    private VendedorDAO vendedorDAO;
    public PedidoAdapter(Context context,
                         List<Pedido> pedidos, Cliente cliente) {
        super(context, 0, pedidos);
        this.cliente = cliente;
        vendedorDAO = new VendedorDAO(getContext());
        mInflater = LayoutInflater.from(context);
        vendedor = vendedorDAO.buscarVendedor();
    }

    @Override
    public View getView(final int position, View convertview, ViewGroup parent) {
        final Pedido pedido = getItem(position);
        if (convertview == null || convertview.getTag() == null) {
            convertview = mInflater.inflate(R.layout.item_pedido, null);
            viewHolder = new ViewHolder();
            viewHolder.tvFechaPedido = (TextView) convertview.findViewById(R.id.tvFechaPedido);
            viewHolder.tvNroPedido = (TextView) convertview.findViewById(R.id.tvNroPedido);
            viewHolder.tvLinea = (TextView) convertview.findViewById(R.id.tvLinea);
            viewHolder.tvImportePedido = (TextView) convertview.findViewById(R.id.tvImportePedido);
            convertview.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertview.getTag();
        }

        viewHolder.tvNroPedido.setText(pedido.NumeroPedido);
        viewHolder.tvImportePedido.setText(Util.formatoDineroSoles(pedido.TotalImporte - pedido.TotalDescuento + pedido.TotalImpuestos));
        viewHolder.tvFechaPedido.setText(Util.formatoFecha(Util.getDateFromString(pedido.FechaCreacionPedido)));

        if (pedido.VendedorID.equals(vendedor.Linea2)) {
            viewHolder.tvLinea.setTextColor(getContext().getResources().getColor(R.color.rojo));
            viewHolder.tvLinea.setText("FIL");
        } else {
            viewHolder.tvLinea.setTextColor(getContext().getResources().getColor(R.color.celeste));
            viewHolder.tvLinea.setText("PUR");
        }
        convertview.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AgregarPedidoActivity.class);
                intent.putExtra("pedido", pedido);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }
        });
        return convertview;
    }

    static class ViewHolder {
        TextView tvFechaPedido;
        TextView tvNroPedido;
        TextView tvImportePedido;
        TextView tvLinea;
    }
}
