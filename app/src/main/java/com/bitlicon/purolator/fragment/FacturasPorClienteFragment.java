package com.bitlicon.purolator.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import com.bitlicon.purolator.R;
import com.bitlicon.purolator.adapter.FacturaAdapter;
import com.bitlicon.purolator.dao.FacturaDAO;
import com.bitlicon.purolator.entities.Cliente;
import com.bitlicon.purolator.entities.Factura;
import com.bitlicon.purolator.lib.SessionManager;
import com.bitlicon.purolator.util.Constantes;
import java.util.ArrayList;

public class FacturasPorClienteFragment extends Fragment {

    private View rootView;
    private TextView txtNombreCliente,txtCodigoCliente;
    private Cliente cliente;
    private SessionManager manager;
    private ListView lvFacturas;
    public static FacturasPorClienteFragment nuevaInstancia(Cliente cliente) {
        FacturasPorClienteFragment h = new FacturasPorClienteFragment();
        h.cliente = cliente;
        return h;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_facturas, container, false);
        /*txtNombreCliente = (TextView) rootView.findViewById(R.id.txtNombreCliente);
        txtCodigoCliente = (TextView) rootView.findViewById(R.id.txtCodigoCliente);
        txtNombreCliente.setText(cliente.Nombre);
        txtCodigoCliente.setText(cliente.ClienteID);
        */
        lvFacturas = (ListView) rootView.findViewById(R.id.lvFacturas);

        manager = new SessionManager(getActivity().getApplicationContext());

        FacturaDAO facturaDAO = new FacturaDAO(getActivity().getApplicationContext());

        ArrayList<Factura> facturas = facturaDAO.listarFacturaxCliente(cliente.ClienteID, manager.getPurolator(), Constantes.PUROLATOR);
        facturas.addAll(facturaDAO.listarFacturaxCliente(cliente.ClienteID, manager.getFiltech(), Constantes.FILTECH));

        FacturaAdapter facturaAdapter = new FacturaAdapter(getActivity().getApplicationContext(), facturas, cliente);
        lvFacturas.setAdapter(facturaAdapter);
        return rootView;
    }
}
