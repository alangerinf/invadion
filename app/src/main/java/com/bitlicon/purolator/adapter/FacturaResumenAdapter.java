package com.bitlicon.purolator.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bitlicon.purolator.R;
import com.bitlicon.purolator.activity.DetalleFacturaActivity;
import com.bitlicon.purolator.dao.ClienteDAO;
import com.bitlicon.purolator.dao.FacturaDAO;
import com.bitlicon.purolator.entities.Cliente;
import com.bitlicon.purolator.entities.Factura;
import com.bitlicon.purolator.entities.Movimiento;
import com.bitlicon.purolator.lib.SessionManager;
import com.bitlicon.purolator.util.Util;

import java.util.ArrayList;


public class FacturaResumenAdapter extends ArrayAdapter<Movimiento> {

    private SessionManager manager;
    private ClienteDAO clienteDAO;
    private FacturaDAO facturaDAO;

    public FacturaResumenAdapter(Context context,
                                 ArrayList<Movimiento> movimientos) {
        super(context, 0, movimientos);
        manager = new SessionManager(getContext());
        clienteDAO = new ClienteDAO(getContext());
        facturaDAO = new FacturaDAO(getContext());
        // TODO Auto-generated constructor stub
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {
        final ViewHolder viewHolder;
        final Movimiento movimiento = getItem(position);
        if (convertview == null) {
            convertview = LayoutInflater.from(getContext()).inflate(R.layout.item_resumen_factura, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.txtTipo = (TextView) convertview.findViewById(R.id.txtTipo);
            viewHolder.txtValor = (TextView) convertview.findViewById(R.id.txtValor);
            viewHolder.txtFecha = (TextView) convertview.findViewById(R.id.txtFecha);
            viewHolder.txtNumero = (TextView) convertview.findViewById(R.id.txtNumero);
            convertview.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertview.getTag();
        }


        viewHolder.txtTipo.setText(movimiento.TipoDocumento);
        viewHolder.txtValor.setText(Util.formatoDineroSoles(movimiento.Monto));
        viewHolder.txtFecha.setText(Util.formatoFecha(Util.getDateFromString(movimiento.FechaDocumento)));
        viewHolder.txtNumero.setText(movimiento.NumDocumento);

        viewHolder.txtNumero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Factura factura = facturaDAO.buscarFactura(movimiento.NumDocumento);
                if (factura == null) {
                    Toast.makeText(getContext(), "Este documento no tiene detalle", Toast.LENGTH_LONG).show();
                    return;
                }
                Cliente cliente = clienteDAO.buscarCliente(movimiento.ClienteID);

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
        TextView txtTipo;
        TextView txtValor;
        TextView txtFecha;
        TextView txtNumero;
    }
}
