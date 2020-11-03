package com.bitlicon.purolator.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.bitlicon.purolator.R;
import com.bitlicon.purolator.activity.DetalleClienteActivity;
import com.bitlicon.purolator.activity.EditarClienteActivity;
import com.bitlicon.purolator.activity.PedidoActivity;
import com.bitlicon.purolator.dao.ClienteDAO;
import com.bitlicon.purolator.entities.Cliente;
import com.bitlicon.purolator.fragment.BaseFragment;

import java.util.ArrayList;

public class ClienteAdapter extends ArrayAdapter<Cliente> {

    private boolean nuevo;
    private ViewHolder viewHolder;
    private LayoutInflater mInflater;
    private boolean pedido;
    public ClienteAdapter(Context context,
                          ArrayList<Cliente> clientes, boolean nuevo, boolean pedido) {
        super(context, 0, clientes);
        mInflater = LayoutInflater.from(context);
        this.nuevo = nuevo;
        this.pedido = pedido;
    }

    public int getCantidad() {
        int cant = 0;
        for (int i = 0; i < this.getCount(); i++) {
            Cliente cliente = this.getItem(i);
            if (cliente.Eliminar) {
                cant++;
            }
        }
        return cant;
    }

    @Override
    public void clear() {
        super.clear();
    }

    @Override
    public View getView(final int position, View convertview, ViewGroup parent) {
        Cliente cliente = getItem(position);
        if (convertview == null || convertview.getTag() == null) {
            convertview = mInflater.inflate(R.layout.item_cliente, null);
            viewHolder = new ViewHolder();
            viewHolder.txtNombre = (TextView) convertview.findViewById(R.id.txtNombre);
            viewHolder.txtCodigo = (TextView) convertview.findViewById(R.id.txtCodigo);
            viewHolder.txtTelefono = (TextView) convertview.findViewById(R.id.txtTelefono);
            viewHolder.cbxEliminar = (CheckBox) convertview.findViewById(R.id.check_eliminar);
            convertview.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertview.getTag();
        }
        viewHolder.txtNombre.setText(cliente.Nombre);
        viewHolder.txtCodigo.setText(cliente.ClienteID);
        viewHolder.txtTelefono.setText(cliente.Telefono);
        viewHolder.cbxEliminar.setChecked(cliente.Eliminar);


        if (nuevo) {
            viewHolder.cbxEliminar.setVisibility(View.VISIBLE);

        } else {
            viewHolder.cbxEliminar.setVisibility(View.GONE);
        }

        viewHolder.txtTelefono.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                String uri = "tel:" + ((TextView) v).getText().toString().trim();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(uri));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }
        });

        viewHolder.cbxEliminar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getItem(position).Eliminar = isChecked;

            }
        });

        convertview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nuevo) {
                    Intent intent = new Intent(getContext(), EditarClienteActivity.class);
                    intent.putExtra(BaseFragment.CLIENTE, getItem(position));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(intent);

                } else {
                    if(pedido)
                    {
                        Intent intent = new Intent(getContext(), PedidoActivity.class);
                        intent.putExtra(BaseFragment.CLIENTE, getItem(position));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getContext().startActivity(intent);

                    }else {
                        Intent intent = new Intent(getContext(), DetalleClienteActivity.class);
                        intent.putExtra(BaseFragment.CLIENTE, getItem(position));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getContext().startActivity(intent);
                    }
                }
            }
        });


        convertview.setLongClickable(true);

        return convertview;
    }

    public void eliminar() {
        for (int i = 0; i < this.getCount(); i++) {
            Cliente cliente = this.getItem(i);
            if (cliente.Eliminar) {
                ClienteDAO clienteDAO = new ClienteDAO(getContext());
                clienteDAO.eliminarCliente(cliente.iCliente);
            }
        }
    }

    public void seleccionarTodos() {
        for (int i = 0; i < this.getCount(); i++) {
            Cliente cliente = this.getItem(i);
            cliente.Eliminar = true;
        }
    }

    static class ViewHolder {
        TextView txtNombre;
        TextView txtCodigo;
        TextView txtTelefono;
        CheckBox cbxEliminar;
    }
}
