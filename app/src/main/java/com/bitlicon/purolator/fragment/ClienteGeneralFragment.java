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

import java.util.ArrayList;

public class ClienteGeneralFragment extends BaseFragment implements View.OnClickListener {

    public static ClienteGeneralFragment nuevaInstancia(BaseActivity baseActivity, boolean pedido) {
        ClienteGeneralFragment h = new ClienteGeneralFragment();
        h.baseActivity = baseActivity;
        h.baseActivity.setClienteGeneralFragment(h);
        h.valorDia = Constantes.DIA_INVALIDO;
        h.tipo = Constantes.GENERAL;
        h.valorNuevo = Boolean.FALSE;
        h.valorCampo = Constantes.EMPTY;
        h.campoOrden = ClienteDAO.NOMBRE;
        h.pedido = pedido;
        return h;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = super.onCreateView(inflater, container, savedInstanceState);

        campoOrden = ClienteDAO.NOMBRE;
        reset();

        return rootView;

    }

    public void reset() {
        ArrayList<Cliente> clientes = clienteDAO.buscarClientesAvanzada(Constantes.EMPTY, ClienteDAO.NOMBRE, ClienteDAO.NOMBRE,
                valorDia, valorNuevo, false);

        clienteAdapter = new ClienteAdapter(getActivity().getApplicationContext(), clientes, false, pedido);
        listViewClientes.setAdapter(clienteAdapter);
        getIndexList(clientes);
    }


}
