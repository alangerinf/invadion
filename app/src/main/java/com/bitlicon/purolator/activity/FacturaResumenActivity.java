package com.bitlicon.purolator.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.bitlicon.purolator.R;
import com.bitlicon.purolator.adapter.FacturaResumenAdapter;
import com.bitlicon.purolator.dao.ConfiguracionDAO;
import com.bitlicon.purolator.dao.MovimientoDAO;
import com.bitlicon.purolator.entities.Cliente;
import com.bitlicon.purolator.entities.Configuracion;
import com.bitlicon.purolator.entities.Movimiento;
import com.bitlicon.purolator.lib.SessionManager;
import com.bitlicon.purolator.util.Constantes;
import com.bitlicon.purolator.util.LimitesFecha;
import com.bitlicon.purolator.util.Util;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by dianewalls on 22/05/2015.
 */
public class FacturaResumenActivity extends ControlActivity {

    private ListView mListViewMovimientos;
    private TextView mTextViewNombreCliente;
    private MovimientoDAO movimientoDAO;
    private FacturaResumenAdapter facturaResumenAdapter;
    private Cliente cliente;
    private String linea;
    private SessionManager sessionManager;
    private int opcion;



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                FacturaResumenActivity.super.onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_resumen_factura);
        Util.setIconMenu(R.drawable.ic_back, this);

        cliente = (Cliente) getIntent().getSerializableExtra("cliente");
        linea = getIntent().getStringExtra("linea");

        mListViewMovimientos = (ListView) findViewById(R.id.lvMovimientos);
        mTextViewNombreCliente = (TextView) findViewById(R.id.txtNombreCliente);

        TextView txtCodigoCliente = (TextView) findViewById(R.id.txtCodigoCliente);
        txtCodigoCliente.setText(cliente.ClienteID);

        mTextViewNombreCliente.setText(cliente.Nombre);

        movimientoDAO = new MovimientoDAO(getApplicationContext());

        sessionManager = new SessionManager(getApplicationContext());

        opcion = Constantes.RESUMEN_MES;
        ConfiguracionDAO ConfDAO = new ConfiguracionDAO(getApplicationContext());
        Configuracion Conf = ConfDAO.Obtener();

        LimitesFecha limitesFecha = new LimitesFecha().LimitesFecha(opcion,Conf.Fecha);
        Calendar calendarInicio = limitesFecha.getCalendarInicio();
        Calendar calendarFin = limitesFecha.getCalendarFin();

        final String fechaInicio = Util.formatoFechaQuery(calendarInicio.getTime());
        final String fechaFin = Util.formatoFechaQuery(calendarFin.getTime());

        ArrayList<Movimiento> movimientos = movimientoDAO.listarMovimientosFactura(cliente.ClienteID, linea, fechaInicio, fechaFin);

        if (sessionManager.getFiltech().equals(linea)) {
            setTitle("Filtech " + Util.formatoDineroSoles(cliente.TotalFiltech));
        } else {
            setTitle("Purolator " + Util.formatoDineroSoles(cliente.TotalPurolator));

        }

        noHayFacturas(movimientos);


        facturaResumenAdapter = new FacturaResumenAdapter(getApplicationContext(), movimientos);

        mListViewMovimientos.setAdapter(facturaResumenAdapter);

    }

    private void noHayFacturas(ArrayList<Movimiento> movimientos) {
        TextView textView = (TextView) findViewById(R.id.mensaje);

        if (movimientos != null && movimientos.size() == 0) {
            textView.setVisibility(View.VISIBLE);
            textView.setText("No registra facturas");
        } else {
            if (movimientos.size() > 0) {
                textView.setVisibility(View.GONE);
            }
        }
    }


}
