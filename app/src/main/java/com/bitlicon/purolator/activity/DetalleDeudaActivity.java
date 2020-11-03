package com.bitlicon.purolator.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bitlicon.purolator.R;
import com.bitlicon.purolator.adapter.DeudaAdapter;
import com.bitlicon.purolator.dao.MovimientoDAO;
import com.bitlicon.purolator.entities.Cliente;
import com.bitlicon.purolator.entities.Movimiento;
import com.bitlicon.purolator.lib.ExpandibleListView;
import com.bitlicon.purolator.lib.SessionManager;
import com.bitlicon.purolator.util.Constantes;
import com.bitlicon.purolator.util.Util;

import java.util.ArrayList;

/**
 * Created by dianewalls on 22/05/2015.
 */
public class DetalleDeudaActivity extends ControlActivity {

    private ExpandibleListView mListViewDeudas;
    private TextView mTextViewNombreCliente;
    private MovimientoDAO movimientoDAO;
    private String tipoCadena = "";
    private String documentos;
    private String queryResult = "";

    private CheckBox check_DeudaTotal;
    private CheckBox check_DeudaCorriente;
    private CheckBox check_DeudaMorosa;
    private CheckBox check_DeudaVencida;
    private CheckBox check_DeudaNoVencida;


    private CheckBox checkLetras;
    private CheckBox checkFacturas;
    private CheckBox checkAbono;
    private CheckBox checkCargo;
    private CheckBox checkBoleta;
    private EditText txtVendedor;

    private DeudaAdapter deudaAdapter;
    private Cliente cliente;
    private ImageView content_email;
    private ImageView device_access;
    private SessionManager manager;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detalle_deuda_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                DetalleDeudaActivity.super.onBackPressed();
                break;
            case R.id.action_filtrar:
                mostrarDialogoContextual();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    String body = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detalle_deuda);

        Util.setIconMenu(R.drawable.ic_back, this);

        manager = new SessionManager(getApplicationContext());

        cliente = (Cliente) getIntent().getSerializableExtra("cliente");
        tipoCadena =  getIntent().getSerializableExtra("tipo").toString();
        documentos =  getIntent().getSerializableExtra("documentos").toString();
        mListViewDeudas = (ExpandibleListView) findViewById(R.id.lvDeudas);
        mTextViewNombreCliente = (TextView) findViewById(R.id.txtNombreCliente);
        content_email = (ImageView) findViewById(R.id.content_email);
        device_access = (ImageView) findViewById(R.id.device_access);
        TextView txtCodigoCliente = (TextView) findViewById(R.id.txtCodigoCliente);
        txtCodigoCliente.setText(cliente.ClienteID);
        movimientoDAO = new MovimientoDAO(getApplicationContext());

        mTextViewNombreCliente.setText(cliente.Nombre);


        queryResult = Constantes.EMPTY;
        StringBuilder query = new StringBuilder();

        String[] arregloDocumentos = documentos.split(",");
        int tamanioArregloDocumentos = arregloDocumentos.length;
        String documento = "";
        for(int i=0;i<tamanioArregloDocumentos;i++) {
            if(!arregloDocumentos[i].equals("")) {
                documento = arregloDocumentos[i];
                switch (documento) {
                    case "L":
                        query.append("'L',");
                        break;
                    case "F":
                        query.append("'F',");
                        break;
                    case "B":
                        query.append("'B',");
                        break;
                    case "D":
                        query.append("'D',");
                        break;
                    case "V":
                        query.append("'V',");
                        break;
                    case "C":
                        query.append("'C',");
                        break;
                    case "Q":
                        query.append("'Q',");
                        break;
                }
            }
        }
        int len = query.toString().length();
        if(len>0){
            queryResult = "(" + query.toString().substring(0, len - 1) + ")";
        }

        ArrayList<Movimiento> movimientos = movimientoDAO.listarMovimientosDeudaMultiple(cliente.ClienteID, tipoCadena, queryResult, "");

        double DeudaTotal = 0;
        Movimiento movimiento;
        int totalMovimientos = movimientos.size();
        for ( int i = 0;i<totalMovimientos;i++)
        {
            movimiento = movimientos.get(i);
            if(movimiento.TipoDocumento.equals("B") || movimiento.TipoDocumento.equals("D"))
            {
                DeudaTotal = DeudaTotal - movimiento.Saldo;
            }else
            {
                DeudaTotal = DeudaTotal + movimiento.Saldo;
            }
        }
        setTitle("Deuda: " + Util.formatoDineroSoles(DeudaTotal));
        noHayDeudas(movimientos);


        deudaAdapter = new DeudaAdapter(getApplicationContext(), movimientos);
        mListViewDeudas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return mostrarDialogoContextual();
            }
        });
        mListViewDeudas.setAdapter(deudaAdapter);
        mListViewDeudas.setExpanded(true);
        ((LinearLayout) mListViewDeudas.getParent()).setOnLongClickListener(getL());


        body = " <html>LEE FILTER DEL PERU S.A <br/> Fecha : "+ Util.getFechaHoyQueryEspanol()+" <br/><br/> <p>ESTADO DE CUENTA</p>  " ;
        body += "---------------------------------------------------------------<br/>";
        body += cliente.ClienteID + "   " + cliente.Nombre +"<br/>";
        body += "Vendedor :  " +  manager.getNombreUsuario() + "<br/>";
        body += "Linea Credito :  " +  Util.formatoDineroSoles(cliente.MontoLineaCredito) + "<br/>";
        body += "Balance :  " + Util.formatoDineroSoles(DeudaTotal) + "<br/>";
        body += "---------------------------------------------------------------<br/>";
        body+= "<b>Linea   Nro.Documento   Tipo   F.Vcto  Saldo</b> <br/>";
        for ( int i = 0;i<totalMovimientos;i++)
        {
            movimiento = movimientos.get(i);

            if (movimiento.VendedorID != null && movimiento.VendedorID.trim().equalsIgnoreCase(manager.getPurolator())) {
                body+="PUR  ";
            } else {
                body+="FIL  ";
            }

            if (movimiento.TipoDocumento != null && movimiento.TipoDocumento.trim().equalsIgnoreCase("L")) {
                body+=movimiento.Agencia + movimiento.Letban + " ";

            } else {
                body+=movimiento.NumDocumento + "  ";
            }

            body+=movimiento.TipoDocumento + "  ";

            body+=Util.formatoFecha(Util.getDateFromString(movimiento.FechaVencimiento))+ "  ";

            body+=Util.formatoDineroSoles(movimiento.Saldo) + "  ";

            body+= "<br/>";
        }
        body += "---------------------------------------------------------------<br/>";
        body+= "</body></html>";
        content_email.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{cliente.Email});
                email.putExtra(Intent.EXTRA_SUBJECT, "Detalle de Deuda");

                email.setType("text/html");

                email.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(body));
                startActivity(Intent.createChooser(email, "Enviar el correo :"));
            }
        });



        device_access.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String uri = "tel:" + cliente.Telefono;
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(uri));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }

    private void noHayDeudas(ArrayList<Movimiento> movimientos) {
        TextView textView = (TextView) findViewById(R.id.mensaje);

        if (movimientos != null && movimientos.size() == 0) {
            textView.setVisibility(View.VISIBLE);

            textView.setText("No registra deuda.");

        } else {
            if (movimientos.size() > 0) {
                textView.setVisibility(View.GONE);
            }
        }
    }

    private View.OnLongClickListener getL() {
        return new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return mostrarDialogoContextual();
            }
        };
    }



    public boolean mostrarDialogoContextual() {
        final Dialog dialog = new Dialog(DetalleDeudaActivity.this);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogo_context_deuda_detalle);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();

        layoutParams.copyFrom(window.getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        window.setAttributes(layoutParams);

        Button btnFiltrar = (Button) dialog.findViewById(R.id.btnFiltrar);



        checkLetras = (CheckBox) dialog.findViewById(R.id.check_letras);
        checkFacturas = (CheckBox) dialog.findViewById(R.id.check_facturas);
        checkAbono = (CheckBox) dialog.findViewById(R.id.check_abono);
        checkCargo = (CheckBox) dialog.findViewById(R.id.check_cargo);
        checkBoleta = (CheckBox) dialog.findViewById(R.id.check_boletas);


        check_DeudaTotal = (CheckBox) dialog.findViewById(R.id.check_deudaTotal);
        check_DeudaCorriente = (CheckBox) dialog.findViewById(R.id.check_deudaCorriente);
        check_DeudaMorosa = (CheckBox) dialog.findViewById(R.id.check_deudaMorosa);
        check_DeudaVencida = (CheckBox) dialog.findViewById(R.id.check_deudaVencida);
        check_DeudaNoVencida = (CheckBox) dialog.findViewById(R.id.check_deudaNoVencida);

        txtVendedor = (EditText) dialog.findViewById(R.id.txtVendedor);

        check_DeudaTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_DeudaTotal.isChecked()) {
                    check_DeudaCorriente.setChecked(true);
                    check_DeudaMorosa.setChecked(true);
                    check_DeudaVencida.setChecked(true);
                    check_DeudaNoVencida.setChecked(true);
                    check_DeudaCorriente.setEnabled(false);
                    check_DeudaMorosa.setEnabled(false);
                    check_DeudaNoVencida.setEnabled(false);
                    check_DeudaVencida.setEnabled(false);
                } else {
                    check_DeudaCorriente.setEnabled(true);
                    check_DeudaMorosa.setEnabled(true);
                    check_DeudaNoVencida.setEnabled(true);
                    check_DeudaVencida.setEnabled(true);
                }
            }
        });


        String[] arregloTipo = tipoCadena.split(",");
        int tamanioArregloTipo = arregloTipo.length;
        int deuda = 0;
        for(int i=0;i<tamanioArregloTipo;i++) {
            if(!arregloTipo[i].equals("")) {
                deuda = Integer.parseInt(arregloTipo[i]);
                switch (deuda) {
                    case Constantes.VENCIDAS:
                        check_DeudaVencida.setChecked(true);
                        break;
                    case Constantes.CORRIENTE:
                        check_DeudaCorriente.setChecked(true);
                        break;
                    case Constantes.LETRAS:
                        check_DeudaNoVencida.setChecked(true);
                        break;
                    case Constantes.MOROSA:
                        check_DeudaMorosa.setChecked(true);
                        break;
                    case Constantes.DEUDA:
                        check_DeudaTotal.setChecked(true);
                        check_DeudaCorriente.setChecked(true);
                        check_DeudaMorosa.setChecked(true);
                        check_DeudaVencida.setChecked(true);
                        check_DeudaNoVencida.setChecked(true);
                        check_DeudaCorriente.setEnabled(false);
                        check_DeudaMorosa.setEnabled(false);
                        check_DeudaNoVencida.setEnabled(false);
                        check_DeudaVencida.setEnabled(false);
                        break;
                }
            }
        }

        String[] arregloDocumentos = documentos.split(",");
        int tamanioArregloDocumentos = arregloDocumentos.length;
        String documento = "";
        for(int i=0;i<tamanioArregloDocumentos;i++) {
            if(!arregloDocumentos[i].equals("")) {
                documento = arregloDocumentos[i];
                switch (documento) {
                    case "L":
                        checkLetras.setChecked(true);
                        break;
                    case "F":
                        checkFacturas.setChecked(true);
                        break;
                    case "B":
                        checkAbono.setChecked(true);
                        break;
                    case "D":
                        checkAbono.setChecked(true);
                        break;
                    case "V":
                        checkBoleta.setChecked(true);
                        break;
                    case "C":
                        checkCargo.setChecked(true);
                        break;
                    case "Q":
                        checkCargo.setChecked(true);
                        break;
                }
            }
        }

        btnFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tipoCadena = "";
                documentos = "";

                queryResult = Constantes.EMPTY;
                StringBuilder query = new StringBuilder();
                if (checkLetras.isChecked()) {
                    query.append("'L',");
                    documentos += "L,";
                }
                if (checkFacturas.isChecked()) {
                    documentos += "F,";
                    query.append("'F',");

                }
                if (checkAbono.isChecked()) {
                    documentos += "B,D,";
                    query.append("'B','D',");

                }
                if (checkCargo.isChecked()) {
                    documentos += "C,Q,";
                    query.append("'C','Q',");
                }
                if (checkBoleta.isChecked()) {
                    documentos += "V,";
                    query.append("'V',");

                }
                int len = query.toString().length();
                if(len>0){
                    queryResult = "(" + query.toString().substring(0, len - 1) + ")";
                }

                if(check_DeudaTotal.isChecked()) {
                    tipoCadena += Constantes.DEUDA + ",";
                }else {

                    if (check_DeudaCorriente.isChecked()) {
                        tipoCadena += Constantes.CORRIENTE + ",";
                    }
                    if (check_DeudaMorosa.isChecked()) {
                        tipoCadena += Constantes.MOROSA + ",";
                    }
                    if (check_DeudaVencida.isChecked()) {
                        tipoCadena += Constantes.VENCIDAS + ",";
                    }
                    if (check_DeudaNoVencida.isChecked()) {
                        tipoCadena += Constantes.LETRAS + ",";
                    }
                }

                if(tipoCadena.length()>0)
                {
                    tipoCadena = tipoCadena.substring(0,tipoCadena.length() -1 );
                }

                ArrayList<Movimiento> movimientos = movimientoDAO.listarMovimientosDeudaMultiple(cliente.ClienteID, tipoCadena, queryResult, txtVendedor.getText().toString());
                deudaAdapter.clear();
                deudaAdapter.addAll(movimientos);
                deudaAdapter.notifyDataSetChanged();
                noHayDeudas(movimientos);
                dialog.dismiss();

                double DeudaTotal = 0;
                Movimiento movimiento;
                int totalMovimientos = movimientos.size();
                for ( int i = 0;i<totalMovimientos;i++)
                {
                    movimiento = movimientos.get(i);
                    if(movimiento.TipoDocumento.equals("B") || movimiento.TipoDocumento.equals("D"))
                    {
                        DeudaTotal = DeudaTotal - movimiento.Saldo;
                    }else
                    {
                        DeudaTotal = DeudaTotal + movimiento.Saldo;
                    }
                }
                if(!txtVendedor.getText().toString().equals(""))
                {
                    setTitle("Deuda: " + Util.formatoDineroSoles(DeudaTotal) + "/ " + txtVendedor.getText().toString());
                }else {
                    setTitle("Deuda: " + Util.formatoDineroSoles(DeudaTotal));
                }

                body = " <html>LEE FILTER DEL PERU S.A <br/> Fecha : "+ Util.getFechaHoyQueryEspanol()+" <br/><br/> <p>ESTADO DE CUENTA</p> " ;
                body += "---------------------------------------------------------------<br/>";
                body += cliente.ClienteID + "   " + cliente.Nombre +"<br/>";
                body += "Vendedor :  " +  manager.getNombreUsuario() + "<br/>";
                body += "Linea Credito :  " +  Util.formatoDineroSoles(cliente.MontoLineaCredito) + "<br/>";
                body += "Balance :  " + Util.formatoDineroSoles(DeudaTotal) + "<br/>";
                body += "---------------------------------------------------------------<br/>";
                body+= "<b>Linea   Nro.Documento   Tipo   F.Vcto  Saldo</b> <br/>";
                for ( int i = 0;i<totalMovimientos;i++)
                {
                    movimiento = movimientos.get(i);

                    if (movimiento.VendedorID != null && movimiento.VendedorID.trim().equalsIgnoreCase(manager.getPurolator())) {
                        body+="PUR  ";
                    } else {
                        body+="FIL  ";
                    }

                    if (movimiento.TipoDocumento != null && movimiento.TipoDocumento.trim().equalsIgnoreCase("L")) {
                        body+=movimiento.Agencia + movimiento.Letban + " ";

                    } else {
                        body+=movimiento.NumDocumento + "  ";
                    }

                    body+=movimiento.TipoDocumento + "  ";

                    body+=Util.formatoFecha(Util.getDateFromString(movimiento.FechaVencimiento))+ "  ";

                    body+=Util.formatoDineroSoles(movimiento.Saldo) + "  ";

                    body+= "<br/>";
                }
                body += "---------------------------------------------------------------<br/>";
                body+= "</body></html>";

            }
        });

        dialog.show();
        return true;
    }


}
