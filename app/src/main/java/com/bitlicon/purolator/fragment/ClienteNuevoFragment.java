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

public class ClienteNuevoFragment extends BaseFragment implements View.OnClickListener {

    public static ClienteNuevoFragment nuevaInstancia(BaseActivity baseActivity) {
        ClienteNuevoFragment h = new ClienteNuevoFragment();
        h.baseActivity = baseActivity;
        h.tipo = Constantes.NUEVO;
        h.valorNuevo = Boolean.TRUE;
        h.valorDia = Constantes.DIA_INVALIDO;
        h.valorCampo = Constantes.EMPTY;
        h.campoOrden = ClienteDAO.NOMBRE;
        h.baseActivity.setClienteNuevoFragment(h);

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
        ArrayList<Cliente> clientes = clienteDAO.buscarClientesAvanzada(Constantes.EMPTY, ClienteDAO.NOMBRE, ClienteDAO.NOMBRE, valorDia,
                valorNuevo, false);
        clienteAdapter = new ClienteAdapter(getActivity().getApplicationContext(), clientes, true, false);
        listViewClientes.setAdapter(clienteAdapter);
        getIndexList(clientes);
    }

}
