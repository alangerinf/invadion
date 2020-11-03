package com.bitlicon.purolator.fragment;


import android.app.Dialog;
import android.os.Bundle;
import androidx.core.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bitlicon.purolator.R;
import com.bitlicon.purolator.activity.BaseActivity;
import com.bitlicon.purolator.adapter.ResumenAdapter;
import com.bitlicon.purolator.dao.ClienteDAO;
import com.bitlicon.purolator.dao.ConfiguracionDAO;
import com.bitlicon.purolator.dao.MovimientoDAO;
import com.bitlicon.purolator.dao.VendedorDAO;
import com.bitlicon.purolator.entities.Cliente;
import com.bitlicon.purolator.entities.Configuracion;
import com.bitlicon.purolator.entities.Resumen;
import com.bitlicon.purolator.entities.Vendedor;
import com.bitlicon.purolator.lib.SessionManager;
import com.bitlicon.purolator.util.Constantes;
import com.bitlicon.purolator.util.LimitesFecha;
import com.bitlicon.purolator.util.Util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class PedidosFragment extends Fragment implements View.OnClickListener{

    private int opcion;
    private ListView listView;
    private View rootView;
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

        rootView = inflater.inflate(R.layout.fragment_pedidos, container, false);

        ConfiguracionDAO configDAO = new ConfiguracionDAO(getActivity());
        Configuracion config = configDAO.Obtener();
        switch (config.Resumen)
        {
            case 0:
                opcion = Constantes.RESUMEN_DIA;
                break;
            case 1:
                opcion = Constantes.RESUMEN_SEMANA;
                break;
            case 2:
                opcion = Constantes.RESUMEN_MES;
                break;
        }

        SessionManager sessionManager = new SessionManager(getActivity());
        VendedorDAO vendedorDAO = new VendedorDAO(getActivity());
        MovimientoDAO movimientoDAO = new MovimientoDAO(getActivity());

        TextView txtPurolatorMonto = (TextView) rootView.findViewById(R.id.resumen_purolator_monto);
        TextView txtPurolatorAvance = (TextView) rootView.findViewById(R.id.resumen_purolator_avance);
        TextView txtFiltechMonto = (TextView) rootView.findViewById(R.id.resumen_filtech_monto);
        TextView txtFiltechAvance = (TextView) rootView.findViewById(R.id.resumen_filtech_avance);
        TextView txtTotalMonto = (TextView) rootView.findViewById(R.id.resumen_total_monto);

        listView = (ListView) rootView.findViewById(R.id.resumen_lista_cliente);
        indexLayout = (LinearLayout) rootView.findViewById(R.id.side_index);

        campoOrden = ClienteDAO.NOMBRE;

        String usuario = sessionManager.getIdUsuario();

        if (usuario != null) {

            Vendedor vendedor = vendedorDAO.buscarVendedor(usuario);

            ConfiguracionDAO ConfDAO = new ConfiguracionDAO(getActivity());
            Configuracion Conf = ConfDAO.Obtener();

            LimitesFecha limitesFecha = new LimitesFecha().invoke(opcion,Conf.Fecha);
            Calendar calendarInicio = limitesFecha.getCalendarInicio();
            Calendar calendarFin = limitesFecha.getCalendarFin();

            final String fechaInicio = Util.formatoFechaQuery(calendarInicio.getTime());
            final String fechaFin = Util.formatoFechaQuery(calendarFin.getTime());

            //long cantidadPur = movimientoDAO.cantidadDePedidosFacturados(vendedor.Linea1, fechaInicio, fechaFin);
            //long cantidadFil = movimientoDAO.cantidadDePedidosFacturados(vendedor.Linea2, fechaInicio, fechaFin);
            Resumen resumen = movimientoDAO.obtenerResumen(vendedor.Linea1, vendedor.Linea2, fechaInicio, fechaFin);

            txtPurolatorMonto.setText(Util.formatoDineroSoles(resumen.getTotalPurolator()));
            txtFiltechMonto.setText(Util.formatoDineroSoles(resumen.getTotalFiltech()));
            txtTotalMonto.setText(Util.formatoDineroSoles(resumen.getTotal()));
            Log.i("calor dde cuota", "es : " + vendedor.CuotaPurolator);
            double avancePurolator = 0;
            double avanceFiltech = 0;
            if(vendedor.CuotaPurolator > 0 ) {
                avancePurolator  =100 *  resumen.getTotalPurolator() /  vendedor.CuotaPurolator ;
            }
            if(vendedor.CuotaPurolator > 0 ) {
                avanceFiltech = 100 *   resumen.getTotalFiltech() /  vendedor.CuotaFiltech;
            }
            txtFiltechAvance.setText(Util.formatoDinero(avanceFiltech) + "%");
            txtPurolatorAvance.setText(Util.formatoDinero(avancePurolator) + "%");


            ArrayList<Cliente> clientes = movimientoDAO.listarResumenCliente(vendedor.Linea1, vendedor.Linea2, fechaInicio, fechaFin);
            ResumenAdapter resumenAdapter = new ResumenAdapter(getActivity(), clientes);
            listView.setAdapter(resumenAdapter);

            getIndexList(clientes);
        }

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

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
        dialog.setContentView(R.layout.dialogo_context_sincronizar_movimiento);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();

        layoutParams.copyFrom(window.getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        window.setAttributes(layoutParams);
        dialog.show();

        LinearLayout lnSincronizar = (LinearLayout) dialog.findViewById(R.id.lnSincronizar);

        lnSincronizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                ((BaseActivity) getActivity()).descargarMovimientos();
            }
        });

        return true;
    }

    @Override
    public void onClick(View v) {
        TextView selectedIndex = (TextView) v;
        Integer index = mapIndex.get(selectedIndex.getText());
        if (index != null)
            listView.setSelection(index);
        else {
            listView.setSelection(0);
        }
    }


    protected Map<String, Integer> mapIndex;
    protected LinearLayout indexLayout;
    protected String campoOrden;

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
