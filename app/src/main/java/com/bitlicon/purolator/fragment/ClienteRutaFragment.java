package com.bitlicon.purolator.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitlicon.purolator.activity.BaseActivity;
import com.bitlicon.purolator.adapter.ClienteAdapter;
import com.bitlicon.purolator.dao.ClienteDAO;
import com.bitlicon.purolator.entities.Cliente;
import com.bitlicon.purolator.util.Constantes;
import com.bitlicon.purolator.util.Util;

import java.util.ArrayList;


public class ClienteRutaFragment extends BaseFragment implements View.OnClickListener {

    public static ClienteRutaFragment nuevaInstancia(BaseActivity baseActivity , boolean pedido) {
        ClienteRutaFragment h = new ClienteRutaFragment();
        h.baseActivity = baseActivity;
        h.tipo = Constantes.RUTA;
        h.valorNuevo = Boolean.FALSE;
        h.valorCampo = Constantes.EMPTY;
        h.campoOrden = ClienteDAO.NOMBRE;
        h.pedido = pedido;
        h.baseActivity.setClienteRutaFragment(h);

        return h;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = super.onCreateView(inflater, container, savedInstanceState);
        reset();
        return rootView;

    }

    public void reset() {
        valorDia = Util.resetDia();
        ArrayList<Cliente> clientes = clienteDAO.buscarClientesAvanzada(Constantes.EMPTY, ClienteDAO.NOMBRE, ClienteDAO.NOMBRE, valorDia,
                Boolean.FALSE, Util.esSemanaPar());
        clienteAdapter = new ClienteAdapter(getActivity().getApplicationContext(), clientes, false, pedido);
        listViewClientes.setAdapter(clienteAdapter);
        getIndexList(clientes);
    }


}
