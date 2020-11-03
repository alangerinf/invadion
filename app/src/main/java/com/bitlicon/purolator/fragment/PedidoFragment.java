package com.bitlicon.purolator.fragment;

import android.app.Dialog;
import android.os.Bundle;
import androidx.core.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitlicon.purolator.R;
import com.bitlicon.purolator.adapter.ProductoAdapter;
import com.bitlicon.purolator.dao.DetalleFacturaDAO;
import com.bitlicon.purolator.entities.Cliente;
import com.bitlicon.purolator.entities.DetalleFactura;
import com.bitlicon.purolator.entities.Factura;
import com.bitlicon.purolator.util.Util;

import java.util.ArrayList;


public class PedidoFragment extends Fragment {

    private LinearLayout rootView;
    private TextView txtCodigo;
    private TextView txtNombre;
    private TextView txtValor;
    private TextView txtIGV;
    private TextView txtTotal;
    private ListView lvProductos;
    private DetalleFacturaDAO detalleFacturaDAO;
    private Cliente mCliente;
    private Factura mFactura;

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

        rootView = (LinearLayout) inflater.inflate(R.layout.fragment_pedido, container, false);
        mCliente = (Cliente) getArguments().getSerializable("cliente");
        mFactura = (Factura) getArguments().getSerializable("factura");
        txtCodigo = (TextView) rootView.findViewById(R.id.txtCodigoCliente);
        txtNombre = (TextView) rootView.findViewById(R.id.txtNombreCliente);
        txtValor = (TextView) rootView.findViewById(R.id.txtValor);
        txtIGV = (TextView) rootView.findViewById(R.id.txtIGV);
        txtTotal = (TextView) rootView.findViewById(R.id.txtTotal);
        detalleFacturaDAO = new DetalleFacturaDAO(getActivity());


        txtNombre.setText(mCliente.Nombre);
        txtCodigo.setText(mCliente.ClienteID);

        txtValor.setText(Util.formatoDineroSoles(mFactura.TotalOrden - (mFactura.DescuentoItem + mFactura.TotalDescuento)));
        txtIGV.setText(Util.formatoDineroSoles(mFactura.TotalImpuestos));
        txtTotal.setText(Util.formatoDineroSoles(mFactura.TotalImporte));

        lvProductos = (ListView) rootView.findViewById(R.id.lvProductos);

        ArrayList<DetalleFactura> productos = detalleFacturaDAO.listarDetalleFacturaxNroFactura(mFactura.NumeroFactura);

        ProductoAdapter deudaAdapter = new ProductoAdapter(getActivity().getApplicationContext(), productos);
        lvProductos.setAdapter(deudaAdapter);
        lvProductos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                return nuevoCliente();
            }
        });
        rootView.setOnLongClickListener(getL());

        return rootView;
    }

    private View.OnLongClickListener getL() {
        return new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return nuevoCliente();
            }
        };
    }

    public boolean nuevoCliente() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogo_context_cliente_detalle);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        dialog.show();

        LinearLayout lnNuevo = (LinearLayout) dialog.findViewById(R.id.lnNuevo);
        lnNuevo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.dismiss();
                Toast.makeText(getActivity(), "Pantalla en desarrollo", Toast.LENGTH_SHORT).show();

               /* Intent intent = new Intent(getActivity(), AgregarPedidoActivity.class);
                intent.putExtra("cliente", mCliente);
                intent.putExtra("factura", mFactura);
                startActivity(intent);*/
            }
        });


        return true;
    }
}
