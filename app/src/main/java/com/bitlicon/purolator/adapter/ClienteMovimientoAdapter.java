package com.bitlicon.purolator.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bitlicon.purolator.R;

import com.bitlicon.purolator.activity.DetalleDeudaActivity;
import com.bitlicon.purolator.entities.Cliente;
import com.bitlicon.purolator.util.Constantes;
import com.bitlicon.purolator.util.Util;

import java.util.ArrayList;

public class ClienteMovimientoAdapter extends ArrayAdapter<Cliente> {

    private ViewHolder viewHolder;
    private LayoutInflater mInflater;
    private String tipo;
    private String documentos = Constantes.EMPTY;

    public ClienteMovimientoAdapter(Context context,
                                    ArrayList<Cliente> clientes, String tipo) {
        super(context, 0, clientes);
        mInflater = LayoutInflater.from(context);
        this.tipo = tipo;
    }

    public void setTipo(String tipo)
    {
        this.tipo = tipo;
    }
    public void setDocumentos(String documentos)
    {
        this.documentos = documentos;
    }

    @Override
    public void clear() {
        super.clear();
    }

    @Override
    public View getView(final int position, View convertview, ViewGroup parent) {
        final Cliente cliente = getItem(position);
        if (convertview == null || convertview.getTag() == null) {
            convertview = mInflater.inflate(R.layout.item_cliente_movimiento, null);
            viewHolder = new ViewHolder();
            viewHolder.txtNombre = (TextView) convertview.findViewById(R.id.txtNombre);
            viewHolder.txtCodigo = (TextView) convertview.findViewById(R.id.txtCodigo);
            viewHolder.txtMontoTotal = (TextView) convertview.findViewById(R.id.txtMontoTotal);
            convertview.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertview.getTag();
        }
        viewHolder.txtNombre.setText(cliente.Nombre);
        viewHolder.txtCodigo.setText(cliente.ClienteID);
        viewHolder.txtMontoTotal.setText(Util.formatoDineroSoles(cliente.Deuda));

        convertview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    Intent intent = new Intent(getContext(), DetalleDeudaActivity.class);
                    intent.putExtra("cliente", cliente);
                    intent.putExtra("tipo", tipo);
                    intent.putExtra("documentos", documentos);

                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(intent);
                }
            }
        });


        convertview.setLongClickable(true);

        return convertview;
    }

    static class ViewHolder {
        TextView txtNombre;
        TextView txtCodigo;
        TextView txtMontoTotal;

    }
}
