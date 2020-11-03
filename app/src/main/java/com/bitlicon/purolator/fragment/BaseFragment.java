package com.bitlicon.purolator.fragment;


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
import androidx.viewpager.widget.ViewPager;

import com.bitlicon.purolator.R;
import com.bitlicon.purolator.activity.BaseActivity;
import com.bitlicon.purolator.adapter.ClienteAdapter;
import com.bitlicon.purolator.dao.ClienteDAO;
import com.bitlicon.purolator.entities.Cliente;
import com.bitlicon.purolator.util.Constantes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class BaseFragment extends Fragment implements View.OnClickListener {

    public static final String CLIENTE = "cliente";
    protected Map<String, Integer> mapIndex;
    protected View rootView;
    protected ClienteDAO clienteDAO;
    protected ClienteAdapter clienteAdapter;
    protected LinearLayout indexLayout;
    protected BaseActivity baseActivity;
    protected String campoOrden;
    protected ListView listViewClientes;
    protected ClienteFragment clienteFragment;
    protected int valorDia;
    protected boolean valorNuevo;
    protected String valorCampo;
    protected int tipo;
    protected boolean pedido;
    //protected String linea;

    public ListView getListViewClientes() {
        return listViewClientes;
    }
/*

    public String getLinea() {
        return linea;
    }
*/

    public String getCampoOrden() {
        return campoOrden;
    }

    public void setCampoOrden(String campoOrden) {
        this.campoOrden = campoOrden;
    }

    public int getValorDia() {
        return valorDia;
    }

    public void setValorDia(int valorDia) {
        this.valorDia = valorDia;
    }

    public String getValorCampo() {
        return valorCampo;
    }

    public boolean isValorNuevo() {
        return valorNuevo;
    }

    public LinearLayout getIndexLayout() {
        return indexLayout;
    }

    public ClienteAdapter getClienteAdapter() {
        return clienteAdapter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clienteDAO = new ClienteDAO(getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_clientes_ruta, container, false);
        ClienteFragment.ClienteSlidePagerAdapter pagerAdapter = (ClienteFragment.ClienteSlidePagerAdapter) (((ViewPager) container).getAdapter());
        clienteFragment = pagerAdapter.getFragment();
        listViewClientes = (ListView) rootView.findViewById(R.id.lstClientesRuta);
        indexLayout = (LinearLayout) rootView.findViewById(R.id.side_index);


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
        return rootView;
    }

    public boolean mostrarDialogoContextual() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogo_context_cliente);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();

        layoutParams.copyFrom(window.getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        window.setAttributes(layoutParams);
        dialog.show();

        LinearLayout lnNuevoCliente = (LinearLayout) dialog.findViewById(R.id.lnNuevoCliente);

        lnNuevoCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                baseActivity.nuevoCliente();
            }
        });

        LinearLayout lnBusquedaAvanzada = (LinearLayout) dialog.findViewById(R.id.lnBusquedaAvanzada);
        lnBusquedaAvanzada.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                baseActivity.buscar(BaseFragment.this);
            }
        });

        LinearLayout lnEscogerRuta = (LinearLayout) dialog.findViewById(R.id.lnEscogerRuta);
        if (tipo == Constantes.RUTA || tipo == Constantes.GENERAL) {
            lnEscogerRuta.setVisibility(View.VISIBLE);
        }else
        {
            lnEscogerRuta.setVisibility(View.GONE);
        }
        lnEscogerRuta.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (tipo == Constantes.RUTA) {

                } else
                {
                    ClienteFragment clienteFragment = (ClienteFragment) baseActivity.getSupportFragmentManager().findFragmentById(R.id.content_main);
                    clienteFragment.viewPagerClientes.setCurrentItem(0);
                }
                dialog.dismiss();
                baseActivity.ruta();
            }
        });

        LinearLayout lnOrdenarPorCodigo = (LinearLayout) dialog.findViewById(R.id.lnOrdenarCodigo);
        lnOrdenarPorCodigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                baseActivity.ordenarPorCodigo(BaseFragment.this);
            }
        });


        LinearLayout lnOrdenarPorNombre = (LinearLayout) dialog.findViewById(R.id.lnOrdenarNombre);
        lnOrdenarPorNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                baseActivity.ordenarPorNombre(BaseFragment.this);
            }
        });

        LinearLayout lnSincronizar = (LinearLayout) dialog.findViewById(R.id.lnSincronizar);

        if (tipo != Constantes.NUEVO) {
            lnSincronizar.setVisibility(View.GONE);
            lnNuevoCliente.setVisibility(View.GONE);
        }
        lnSincronizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                baseActivity.sincronizar();
            }
        });
        return true;
    }


    @Override
    public void onResume() {
        super.onResume();

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

        if (tipo == Constantes.NUEVO && campoOrden.equals(ClienteDAO.CLIENTE_ID)) {
            return;
        }
        for (int i = 0; i < clientes.size(); i++) {
            Cliente cliente = clientes.get(i);
            String index = null;
            if(cliente!=null) {
                if (campoOrden.equals(ClienteDAO.NOMBRE)) {
                    if (cliente.Nombre != null)
                        index = cliente.Nombre.substring(0, 1);
                } else {
                    if (cliente.ClienteID != null)
                        index = cliente.ClienteID.substring(0, 1);
                }
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
