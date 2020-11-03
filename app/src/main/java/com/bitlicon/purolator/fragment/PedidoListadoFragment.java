package com.bitlicon.purolator.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.core.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bitlicon.purolator.R;
import com.bitlicon.purolator.activity.BaseActivity;
import com.bitlicon.purolator.activity.PedidoActivity;
import com.bitlicon.purolator.dao.ClienteDAO;
import com.bitlicon.purolator.lib.SessionManager;

public class PedidoListadoFragment extends Fragment {

    private BaseActivity base;
    private View rootView;
    private ListView lstClientesRuta;
    private ClienteDAO oClienteDAO;
    private SessionManager mSessionManager;

    public static PedidoListadoFragment nuevaInstancia(BaseActivity base) {
        PedidoListadoFragment h = new PedidoListadoFragment();
        base.setTitle(R.string.pedidos);
        base.setLayout(R.menu.pedido_fragment_actions);
        base.invalidateOptionsMenu();
        h.base = base;
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
        rootView = inflater.inflate(R.layout.fragment_pedidos_listado, container, false);

        mSessionManager = new SessionManager(getActivity().getApplicationContext());

        lstClientesRuta = (ListView) rootView.findViewById(R.id.lstClientesRuta);

        oClienteDAO = new ClienteDAO(getActivity().getApplicationContext());


        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), PedidoActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

}
