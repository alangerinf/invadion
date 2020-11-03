package com.bitlicon.purolator.fragment;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;


import androidx.fragment.app.Fragment;

import com.bitlicon.purolator.R;
import com.bitlicon.purolator.activity.AgregarPedidoActivity;
import com.bitlicon.purolator.adapter.DetallePedidoAdapter;
import com.bitlicon.purolator.dao.ConfiguracionDAO;
import com.bitlicon.purolator.dao.DescuentoDAO;
import com.bitlicon.purolator.dao.DetallePedidoDAO;
import com.bitlicon.purolator.dao.PedidoDAO;
import com.bitlicon.purolator.dao.VendedorDAO;
import com.bitlicon.purolator.entities.Configuracion;
import com.bitlicon.purolator.entities.Descuento;
import com.bitlicon.purolator.entities.DetallePedido;
import com.bitlicon.purolator.entities.Pedido;
import com.bitlicon.purolator.entities.Vendedor;
import com.bitlicon.purolator.lib.SessionManager;
import com.bitlicon.purolator.util.Util;

import java.util.List;

public class DetallePedidoFragment extends Fragment {

    private View rootView;
    private Pedido pedido;
    private SessionManager sessionManager;
    private VendedorDAO vendedorDAO;
    private Vendedor vendedor;
    private Configuracion configuracion;
    private DescuentoDAO descuentoDAO;
    private PedidoDAO pedidoDAO;
    private ConfiguracionDAO configuracionDAO;
    private DetallePedidoDAO detallePedidoDAO;
    private List<Descuento> descuentos;
    private ListView lvDetallePedidos;
    private List<DetallePedido> detallePedidos;
    private DetallePedidoAdapter detallePedidoAdapter;
    View headerListView;
    public TextView tvValorTotal , tvIgvTotal, tvTotal,tvValorTotalUnitario , tvIgvTotalUnitario, tvTotalUnitario;
    double TotalGeneral = 0;

    public static DetallePedidoFragment nuevaInstancia(Pedido pedido) {
        DetallePedidoFragment h = new DetallePedidoFragment();
        h.pedido = pedido;
        return h;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        vendedorDAO = new VendedorDAO(getActivity().getApplicationContext());
        descuentoDAO = new DescuentoDAO(getActivity().getApplicationContext());
        pedidoDAO = new PedidoDAO(getActivity().getApplicationContext());
        detallePedidoDAO = new DetallePedidoDAO(getActivity().getApplicationContext());
        configuracionDAO = new ConfiguracionDAO(getActivity().getApplicationContext());

        vendedor = vendedorDAO.buscarVendedor();
        descuentos = descuentoDAO.listarDescuentos();
        configuracion = configuracionDAO.Obtener();

        if(pedido.Enviado)
        {
            detallePedidos = detallePedidoDAO.listarDetallePedidoPorNumero(pedido.NumeroPedido);
        }else {
            detallePedidos = detallePedidoDAO.listarDetallePedido(pedido.iPedido);
        }
        detallePedidoAdapter = new DetallePedidoAdapter(getActivity(), detallePedidos, pedido , this, ((AgregarPedidoActivity)getActivity()).condicionPedidoFragment);
        lvDetallePedidos.setAdapter(detallePedidoAdapter);
        if(!pedido.Enviado) {
            TotalGeneral = 0;
            DetallePedido detallePedido;
            double subtotal = 0;
            int cantidadDetallePedido = detallePedidos.size();
            for(int i=0; i<cantidadDetallePedido; i++)
            {
                detallePedido = detallePedidos.get(i);
                subtotal = detallePedido.Cantidad * ( (detallePedido.Precio) * ( (100 - detallePedido.Descuento1) / 100 * 1.00) * ((100-detallePedido.Descuento2) / 100 * 1.00));
                TotalGeneral =  TotalGeneral + subtotal;
            }
            pedidoDAO.actualizarTotalImporte(TotalGeneral, pedido.iPedido);
            pedido = pedidoDAO.obtenerPedido(pedido.iPedido);
            double TotalDescuentos = 0;
            double TotalImpuesto = 0;
            double igv = configuracion.IGV;
            TotalDescuentos = obtenerTotalDescuento(pedido.Descuento1, pedido.Descuento2, pedido.Descuento3, pedido.Descuento4, pedido.Descuento5);
            pedidoDAO.actualizarTotalDescuento(TotalDescuentos, pedido.iPedido);
            tvValorTotal.setText(Util.formatoDineroSoles(TotalGeneral - TotalDescuentos));
            double totalGeneralSinImpuestos = TotalGeneral - TotalDescuentos;
            TotalImpuesto = (double) totalGeneralSinImpuestos * igv / 100;
            tvIgvTotal.setText(Util.formatoDineroSoles(TotalImpuesto));
            tvTotal.setText(Util.formatoDineroSoles(TotalImpuesto + totalGeneralSinImpuestos));
            pedidoDAO.actualizarTotalImpuestos(TotalImpuesto, pedido.iPedido);

        }else {
            tvValorTotal.setText(Util.formatoDineroSoles(pedido.TotalImporte - pedido.TotalDescuento));
            tvIgvTotal.setText(Util.formatoDineroSoles(pedido.TotalImpuestos));
            tvTotal.setText(Util.formatoDineroSoles(pedido.TotalImporte - pedido.TotalDescuento + pedido.TotalImpuestos));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_detalle_pedido, container, false);
        headerListView = ((LayoutInflater)getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_detalle_pedido_header, null, false);
        lvDetallePedidos = (ListView) rootView.findViewById(R.id.lvDetallePedidos);
        lvDetallePedidos.addHeaderView(headerListView);

        tvValorTotal = (TextView) headerListView.findViewById(R.id.tvValorTotal);
        tvIgvTotal = (TextView) headerListView.findViewById(R.id.tvIgvTotal);
        tvTotal = (TextView) headerListView.findViewById(R.id.tvTotal);


        tvValorTotalUnitario = (TextView) headerListView.findViewById(R.id.tvValorTotalUnitario);
        tvIgvTotalUnitario = (TextView) headerListView.findViewById(R.id.tvIgvTotalUnitario);
        tvTotalUnitario = (TextView) headerListView.findViewById(R.id.tvTotalUnitario);

        sessionManager = new SessionManager(getActivity().getApplicationContext());

        return rootView;
    }

    public double obtenerTotalDescuento(boolean desc1, boolean desc2, boolean desc3,boolean desc4,boolean desc5)
    {
        int desc1int = 0,desc2int = 0,desc3int = 0,desc4int = 0, desc5int = 0;
        if(desc1)
        {
            String descuento = descuentos.get(0).Descripcion.replace("%", "");
            try {
                desc1int = Integer.parseInt(descuento);
            }catch (NumberFormatException e) {
                desc1int = 0;
            }
        }
        if(desc2)
        {
            String descuento = descuentos.get(1).Descripcion.replace("%", "");
            try {
                desc2int = Integer.parseInt(descuento);
            }catch (NumberFormatException e) {
                desc2int = 0;
            }
        }
        if(desc3)
        {
            String descuento = descuentos.get(2).Descripcion.replace("%", "");
            try {
                desc3int = Integer.parseInt(descuento);
            }catch (NumberFormatException e) {
                desc3int = 0;
            }
        }
        if(desc4)
        {
            String descuento = descuentos.get(3).Descripcion.replace("%", "");
            try {
                desc4int = Integer.parseInt(descuento);
            }catch (NumberFormatException e) {
                desc4int = 0;
            }
        }
        if(desc5)
        {
            String descuento = descuentos.get(4).Descripcion.replace("%", "");
            try {
                desc5int = Integer.parseInt(descuento);
            }catch (NumberFormatException e) {
                desc5int = 0;
            }
        }
        double TotalFinal = (double) pedido.TotalImporte * ((double)(100-desc1int) / 100) * ((double)(100 -desc2int)/100) * ((double)(100 -desc3int)/100) * ((double)(100 -desc4int) /100) * ((double)(100 -desc5int)/100);
        double TotalDescuentos = pedido.TotalImporte - TotalFinal;
        return TotalDescuentos;
    }
}
