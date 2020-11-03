package com.bitlicon.purolator.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.core.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.bitlicon.purolator.R;

import com.bitlicon.purolator.activity.AgregarPedidoActivity;
import com.bitlicon.purolator.adapter.PedidoTAdapter;
import com.bitlicon.purolator.dao.PedidoDAO;
import com.bitlicon.purolator.entities.Cliente;
import com.bitlicon.purolator.entities.Pedido;

import java.util.List;


public class PedidosPorClienteFragment extends Fragment {

    private View rootView;
    private Cliente cliente;
    private TextView txtNombreCliente,txtCodigoCliente;
    private List<Pedido> pedidos;
    private ListView lvPedidosPorCliente;
    View headerListView;
    PedidoTAdapter pedidoTAdapter;
    private PedidoDAO pedidoDAO;

    public static PedidosPorClienteFragment nuevaInstancia(Cliente cliente, List<Pedido> pedidos) {
        PedidosPorClienteFragment h = new PedidosPorClienteFragment();
        h.cliente = cliente;
        h.pedidos = pedidos;
        return h;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        pedidoDAO = new PedidoDAO(getActivity().getApplicationContext());
        pedidos = pedidoDAO.listarPedidos(cliente.ClienteID);
        pedidoTAdapter = new PedidoTAdapter(getActivity().getApplicationContext(),pedidos);
        lvPedidosPorCliente.setAdapter(pedidoTAdapter);
    }

    public void actualizarPedidos(List<Pedido> pedidos)
    {
        this.pedidos = pedidos;
        pedidoTAdapter = new PedidoTAdapter(getActivity().getApplicationContext(),this.pedidos);
        lvPedidosPorCliente.setAdapter(pedidoTAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_pedidos_t, container, false);
        lvPedidosPorCliente = (ListView) rootView.findViewById(R.id.lvPedidosPorCliente);
        pedidoTAdapter = new PedidoTAdapter(getActivity().getApplicationContext(),pedidos);
        lvPedidosPorCliente.setAdapter(pedidoTAdapter);
        headerListView = ((LayoutInflater)getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE)).inflate(R.layout.header_pedidos_t, null, false);
        lvPedidosPorCliente.addHeaderView(headerListView);
        txtNombreCliente = (TextView) headerListView.findViewById(R.id.txtNombreCliente);
        txtCodigoCliente = (TextView) headerListView.findViewById(R.id.txtCodigoCliente);
        txtNombreCliente.setText(cliente.Nombre);
        txtCodigoCliente.setText(cliente.ClienteID);

        lvPedidosPorCliente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0) {
                    Pedido pedido = (Pedido) parent.getItemAtPosition(position);
                    if (pedido != null) {
                        Intent intent = new Intent(getActivity(), AgregarPedidoActivity.class);
                        intent.putExtra("cliente", cliente);
                        intent.putExtra("pedido", pedido);
                        startActivity(intent);
                    }
                }
            }
        });

        return rootView;
    }
}
