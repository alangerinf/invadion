package com.bitlicon.purolator.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bitlicon.purolator.R;
import com.bitlicon.purolator.adapter.FacturaAdapter;
import com.bitlicon.purolator.adapter.PedidoAdapter;
import com.bitlicon.purolator.dao.FacturaDAO;
import com.bitlicon.purolator.dao.MovimientoDAO;
import com.bitlicon.purolator.dao.PedidoDAO;
import com.bitlicon.purolator.entities.Cliente;
import com.bitlicon.purolator.entities.Factura;
import com.bitlicon.purolator.entities.Pedido;
import com.bitlicon.purolator.lib.ExpandibleListView;
import com.bitlicon.purolator.lib.SessionManager;
import com.bitlicon.purolator.util.Constantes;
import com.bitlicon.purolator.util.Util;

import java.util.ArrayList;
import java.util.List;


public class DetalleClienteActivity extends ControlActivity {

    ProgressDialog prgDialog;
    private Cliente cliente;
    private TextView txtCodigoCliente;
    private TextView txtNombreCliente;
    private TextView txtDeudaTotal;
    private TextView txtDeudaMorosa;
    private TextView txtDeudaCorriente;
    private TextView txtDeudaLetras;
    private TextView txtDeudaVencidas;
    private TextView txtLineaDisponible;
    private LinearLayout tvDetalleDeuda;
    private LinearLayout tvDetalleCorriente;
    private LinearLayout tvDetalleMorosa;
    private LinearLayout tvDetalleLetras;
    private LinearLayout tvDetalleVencida;
    private ExpandibleListView lvFacturas;
    private ExpandibleListView lvPedidos;
    private SessionManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_cliente);
        Util.setIconMenu(R.drawable.ic_back, this);

        setTitle(R.string.clientes);
        manager = new SessionManager(getApplicationContext());
        txtNombreCliente = (TextView) findViewById(R.id.txtNombreCliente);
        txtDeudaTotal = (TextView) findViewById(R.id.txtDeudaTotal);
        txtDeudaMorosa = (TextView) findViewById(R.id.txtDeudaMorosa);
        txtDeudaCorriente = (TextView) findViewById(R.id.txtDeudaCorriente);
        txtDeudaVencidas = (TextView) findViewById(R.id.txtDeudaVencida);
        txtDeudaLetras = (TextView) findViewById(R.id.txtDeudaLetras);
        txtLineaDisponible = (TextView) findViewById(R.id.txtLineaDisponible);

        tvDetalleDeuda = (LinearLayout) findViewById(R.id.lldeuda);
        tvDetalleCorriente = (LinearLayout) findViewById(R.id.llcorriente);
        tvDetalleMorosa = (LinearLayout) findViewById(R.id.llmorosa);
        tvDetalleLetras = (LinearLayout) findViewById(R.id.llletras);
        tvDetalleVencida = (LinearLayout) findViewById(R.id.llvencida);

        lvFacturas = (ExpandibleListView) findViewById(R.id.lvFacturas);
        lvPedidos = (ExpandibleListView) findViewById(R.id.lvPedidos);


        txtCodigoCliente = (TextView) findViewById(R.id.txtCodigoCliente);

        cliente = (Cliente) getIntent().getSerializableExtra("cliente");

        txtCodigoCliente.setText(cliente.ClienteID);

        if (cliente.ClienteID != null) {
            FacturaDAO facturaDAO = new FacturaDAO(getApplicationContext());
            PedidoDAO pedidoDAO = new PedidoDAO(getApplicationContext());

            ArrayList<Factura> facturas = facturaDAO.listarTresUltimasFacturaxCliente(cliente.ClienteID, manager.getPurolator(), Constantes.PUROLATOR);
            facturas.addAll(facturaDAO.listarTresUltimasFacturaxCliente(cliente.ClienteID, manager.getFiltech(), Constantes.FILTECH));

            List<Pedido> pedidos = pedidoDAO.listarPedidosPorVendedorYEnviados(cliente.ClienteID, manager.getPurolator());
            pedidos.addAll(pedidoDAO.listarPedidosPorVendedorYEnviados(cliente.ClienteID, manager.getFiltech()));
            FacturaAdapter facturaAdapter = new FacturaAdapter(getApplicationContext(), facturas, cliente);
            PedidoAdapter pedidoAdapter = new PedidoAdapter(getApplicationContext(), pedidos, cliente);

            lvPedidos.setAdapter(pedidoAdapter);
            lvPedidos.setExpanded(true);

            lvFacturas.setAdapter(facturaAdapter);
            lvFacturas.setExpanded(true);


        }

        txtNombreCliente.setText(cliente.Nombre);
        MovimientoDAO movimientoDAO = new MovimientoDAO(getApplicationContext());
        double deudaTotal = movimientoDAO.deudaPorClienteID(cliente.ClienteID, Constantes.DEUDA);
        double deudaMorosa = movimientoDAO.deudaPorClienteID(cliente.ClienteID, Constantes.MOROSA);
        double deudaLetrasNoVencidas = movimientoDAO.deudaPorClienteID(cliente.ClienteID, Constantes.LETRAS);
        double deudaCorriente = movimientoDAO.deudaPorClienteID(cliente.ClienteID, Constantes.CORRIENTE);
        double deudaLetrasVencidas = movimientoDAO.deudaPorClienteID(cliente.ClienteID, Constantes.VENCIDAS);

        txtDeudaCorriente.setText(Util.formatoDineroSoles(deudaCorriente));
        txtDeudaTotal.setText(Util.formatoDineroSoles(deudaTotal));
        txtDeudaMorosa.setText(Util.formatoDineroSoles(deudaMorosa));
        txtDeudaLetras.setText(Util.formatoDineroSoles(deudaLetrasNoVencidas));
        txtDeudaVencidas.setText(Util.formatoDineroSoles(deudaLetrasVencidas));
        txtLineaDisponible.setText(Util.formatoDineroSoles(cliente.MontoLineaCredito - deudaTotal));


        prgDialog = new ProgressDialog(this);
        prgDialog.setMessage("Please wait...");
        prgDialog.setCancelable(false);


        tvDetalleDeuda.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getApplicationContext(), DetalleDeudaActivity.class);
                intent.putExtra("cliente", cliente);
                intent.putExtra("tipo", Constantes.DEUDA);
                intent.putExtra("documentos", Constantes.EMPTY);
                startActivity(intent);
            }
        });


        tvDetalleCorriente.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getApplicationContext(), DetalleDeudaActivity.class);
                intent.putExtra("cliente", cliente);
                intent.putExtra("tipo", Constantes.CORRIENTE);
                intent.putExtra("documentos", Constantes.EMPTY);
                startActivity(intent);
            }
        });


        tvDetalleMorosa.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getApplicationContext(), DetalleDeudaActivity.class);
                intent.putExtra("cliente", cliente);
                intent.putExtra("tipo", Constantes.MOROSA);
                intent.putExtra("documentos", Constantes.EMPTY);
                startActivity(intent);
            }
        });


        tvDetalleLetras.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getApplicationContext(), DetalleDeudaActivity.class);
                intent.putExtra("cliente", cliente);
                intent.putExtra("tipo", Constantes.LETRAS);
                intent.putExtra("documentos", Constantes.EMPTY);
                startActivity(intent);
            }
        });


        tvDetalleVencida.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getApplicationContext(), DetalleDeudaActivity.class);
                intent.putExtra("cliente", cliente);
                intent.putExtra("tipo", Constantes.VENCIDAS);
                intent.putExtra("documentos", Constantes.EMPTY);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.resumen_cliente_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                DetalleClienteActivity.super.onBackPressed();
                break;
            case R.id.action_detalle:
                Intent intent = new Intent(getApplicationContext(), EditarClienteActivity.class);
                intent.putExtra("cliente", cliente);
                intent.putExtra("editable", false);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
