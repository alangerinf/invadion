package com.bitlicon.purolator.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bitlicon.purolator.R;
import com.bitlicon.purolator.activity.CobranzaClienteActivity;
import com.bitlicon.purolator.adapter.ClienteMovimientoAdapter;
import com.bitlicon.purolator.dao.ClienteDAO;
import com.bitlicon.purolator.entities.Cliente;
import com.bitlicon.purolator.util.Constantes;
import com.bitlicon.purolator.util.Util;
import com.bitlicon.purolator.dao.MovimientoDAO;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class ClienteMovimientoRutaFragment extends Fragment implements View.OnClickListener{

    protected View rootView;
    protected ListView listViewClientes;
    protected LinearLayout indexLayout;
    protected Map<String, Integer> mapIndex;
    protected String campoOrden;
    protected int valorDia;
    protected ClienteDAO clienteDAO;
    protected MovimientoDAO movimientoDAO;
    protected ClienteMovimientoAdapter clienteMovimientoAdapter;
    protected CobranzaClienteActivity baseActivity;
    protected int tipo;
    public static ClienteMovimientoRutaFragment nuevaInstancia(Activity baseActivity, int tipo) {
        ClienteMovimientoRutaFragment h = new ClienteMovimientoRutaFragment();
        h.baseActivity = (CobranzaClienteActivity) baseActivity;
        h.tipo = tipo;
        return h;
    }

    public ClienteMovimientoAdapter obtenerClienteMovimientoAdapter()
    {
        return clienteMovimientoAdapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        clienteDAO = new ClienteDAO(getActivity().getApplicationContext());
        movimientoDAO = new MovimientoDAO(getActivity().getApplicationContext());

        rootView = inflater.inflate(R.layout.fragment_clientes_ruta, container, false);

        campoOrden = ClienteDAO.NOMBRE;

        listViewClientes = (ListView) rootView.findViewById(R.id.lstClientesRuta);
        indexLayout = (LinearLayout) rootView.findViewById(R.id.side_index);

        valorDia = Util.resetDia();
        ArrayList<Cliente> clientes = clienteDAO.buscarClientesAvanzadaxDeuda(Constantes.EMPTY, ClienteDAO.NOMBRE, ClienteDAO.NOMBRE, valorDia,
                Boolean.FALSE, Util.esSemanaPar(), tipo);


        clienteMovimientoAdapter = new ClienteMovimientoAdapter(getActivity().getApplicationContext(), clientes, String.valueOf(tipo));
        listViewClientes.setAdapter(clienteMovimientoAdapter);

        listViewClientes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                return mostrarDialogoContextual();
            }


        });

        rootView.setLongClickable(true);
        rootView.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View view) {
                return mostrarDialogoContextual();
            }
        });

        

        getIndexList(clientes);

        if(clientes.size() > 0)
        {
            int cantidadClientes = clientes.size();
            baseActivity.setTitle("Deuda : " + Util.formatoDineroSoles(clientes.get(cantidadClientes-1).DeudaTotal));
        }else
        {
            baseActivity.setTitle("Deuda : 0.00");
        }
        return rootView;

    }

    public boolean mostrarDialogoContextual() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogo_context_deuda_ruta);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();

        layoutParams.copyFrom(window.getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        window.setAttributes(layoutParams);
        dialog.show();

        LinearLayout lnFiltrarDeuda = (LinearLayout) dialog.findViewById(R.id.lnFiltrarDeuda);

        lnFiltrarDeuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                baseActivity.mostrarDialogoFiltrosDeuda();
            }
        });

        LinearLayout lnBusquedaAvanzada = (LinearLayout) dialog.findViewById(R.id.lnBusquedaAvanzada);
        lnBusquedaAvanzada.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                baseActivity.mostrarDialogoBusquedaAvanzadaRuta();
            }
        });

        LinearLayout lnEscogerRuta = (LinearLayout) dialog.findViewById(R.id.lnEscogerRuta);
        if (tipo != Constantes.RUTA) {
            lnEscogerRuta.setVisibility(View.GONE);
        }
        lnEscogerRuta.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                baseActivity.ruta();
            }
        });


        return true;
    }

    @Override
    public void onClick(View v) {
        TextView selectedIndex = (TextView) v;
        Integer index = mapIndex.get(selectedIndex.getText());
        if (index != null)
            listViewClientes.setSelection(index);
        else {
            listViewClientes.setSelection(0);
        }
    }
    public void getIndexList(ArrayList<Cliente> clientes) {
        mapIndex = new LinkedHashMap<String, Integer>();
        indexLayout.removeAllViews();

        for (int i = 0; i < clientes.size(); i++) {
            Cliente cliente = clientes.get(i);
            String index = null;

            if (campoOrden.equals(ClienteDAO.NOMBRE)) {
                if (cliente.Nombre != null)
                    index = cliente.Nombre.substring(0, 1);
            } else {
                if (cliente.ClienteID != null)
                    index = cliente.ClienteID.substring(0, 1);
            }

            if (mapIndex.get(index) == null)
                mapIndex.put(index, i);
        }
        displayIndex();
    }

    private void displayIndex() {

        TextView textView;
        List<String> indexList = new ArrayList<String>(mapIndex.keySet());
        for (String index : indexList) {
            textView = (TextView) this.getActivity().getLayoutInflater().inflate(
                    R.layout.side_index_item, null);
            textView.setText(index);
            textView.setOnClickListener(this);
            indexLayout.addView(textView);
        }
        indexLayout.setVisibility(View.VISIBLE);
    }


}
