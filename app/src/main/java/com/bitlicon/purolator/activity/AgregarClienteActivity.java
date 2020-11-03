package com.bitlicon.purolator.activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.bitlicon.purolator.R;
import com.bitlicon.purolator.dao.ClienteDAO;
import com.bitlicon.purolator.entities.Cliente;
import com.bitlicon.purolator.lib.SessionManager;
import com.bitlicon.purolator.util.Constantes;
import com.bitlicon.purolator.util.Util;

import java.util.Date;


public class AgregarClienteActivity extends ControlActivity {

    private EditText txtRazonSocial;
    private EditText txtDireccionFiscal;
    private EditText txtDireccionDespacho;
    private EditText txtRuc;
    private EditText txtCelular;
    private EditText txtCorreo;
    private EditText txtDNI;
    private EditText txtGiroNegocio;
    private EditText txtContacto;
    private EditText txtRepresentaLegal;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_cliente);

        Util.setIconMenu(R.drawable.ic_back, this);

        setTitle(R.string.clientes);

        sessionManager = new SessionManager(getApplicationContext());

        txtRazonSocial = (EditText) findViewById(R.id.txtRazonSocial);
        txtDireccionFiscal = (EditText) findViewById(R.id.txtDireccionFiscal);
        txtDireccionDespacho = (EditText) findViewById(R.id.txtDireccionDespacho);
        txtRuc = (EditText) findViewById(R.id.txtRuc);
        txtCelular = (EditText) findViewById(R.id.txtCelular);
        txtCorreo = (EditText) findViewById(R.id.txtCorreo);
        txtDNI = (EditText) findViewById(R.id.txtDNI);
        txtGiroNegocio = (EditText) findViewById(R.id.txtGiroNegocio);
        txtContacto = (EditText) findViewById(R.id.txtContacto);
        txtRepresentaLegal = (EditText) findViewById(R.id.txtRepresentaLegal);
        LinearLayout llCliente = (LinearLayout) findViewById(R.id.llCliente);
        ScrollView svCliente = (ScrollView) findViewById(R.id.svCliente);
        llCliente.setOnLongClickListener(getL());

        View.OnFocusChangeListener l = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                setTitle(((EditText) v).getHint());
            }
        };
        txtRepresentaLegal.setOnFocusChangeListener(l);
        txtCorreo.setOnFocusChangeListener(l);
        txtRuc.setOnFocusChangeListener(l);
        txtDNI.setOnFocusChangeListener(l);
        txtRazonSocial.setOnFocusChangeListener(l);
        txtDireccionDespacho.setOnFocusChangeListener(l);
        txtDireccionFiscal.setOnFocusChangeListener(l);
        txtCelular.setOnFocusChangeListener(l);
        txtContacto.setOnFocusChangeListener(l);
        txtGiroNegocio.setOnFocusChangeListener(l);

        ((LinearLayout) svCliente.getParent()).setOnLongClickListener(getL());


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
        final Dialog dialog = new Dialog(AgregarClienteActivity.this);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogo_context_cliente_nuevo);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        dialog.show();

        LinearLayout lnGrabar = (LinearLayout) dialog.findViewById(R.id.lnGrabar);
        LinearLayout lnCancelar = (LinearLayout) dialog.findViewById(R.id.lnCancelar);
        lnGrabar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.dismiss();
                grabarCliente();
            }
        });
        lnCancelar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.dismiss();

                AgregarClienteActivity.super.onBackPressed();


            }
        });


        return true;
    }


    private void grabarCliente() {
        Cliente cliente = new Cliente();
        cliente.Nombre = txtRazonSocial.getText().toString().trim();
        cliente.Direccion = txtDireccionFiscal.getText().toString().trim();
        cliente.RUC = txtRuc.getText().toString().trim();
        cliente.Telefono = txtCelular.getText().toString().trim();
        cliente.Encargado = txtContacto.getText().toString().trim();
        cliente.DNI = txtDNI.getText().toString().trim();
        cliente.Email = txtCorreo.getText().toString().trim();

        if (cliente.Nombre.length() == 0) {
            Toast.makeText(getApplicationContext(), "Ingresar nombre", Toast.LENGTH_LONG).show();
            return;
        }

        if (cliente.Direccion.length() == 0) {
            Toast.makeText(getApplicationContext(), R.string.idireccion, Toast.LENGTH_LONG).show();
            return;
        }

        if (cliente.RUC.length() == 0) {
            Toast.makeText(getApplicationContext(), "Ingresar RUC", Toast.LENGTH_LONG).show();
            return;
        }

        if (cliente.Telefono.trim().length() == 0) {
            Toast.makeText(getApplicationContext(), R.string.itelefono, Toast.LENGTH_LONG).show();
            return;
        }

        if (cliente.Encargado.trim().length() == 0) {
            Toast.makeText(getApplicationContext(), "Ingresar contacto", Toast.LENGTH_LONG).show();
            return;
        }
        if (cliente.RUC.length() != 11) {
            Toast.makeText(getApplicationContext(), R.string.vRUC, Toast.LENGTH_LONG).show();
            return;
        }

        if (cliente.DNI.length() != 8) {
            Toast.makeText(getApplicationContext(), R.string.vDNI, Toast.LENGTH_LONG).show();
            return;
        }


        if (cliente.Email.length() > 0 && !Util.isValidEmail(txtCorreo.getText())) {
            Toast.makeText(getApplicationContext(), R.string.vcorreo, Toast.LENGTH_LONG).show();
            return;
        }

        cliente.PurolatorID = sessionManager.getPurolator();
        cliente.FiltechID = sessionManager.getFiltech();
        cliente.Nuevo = true;
        cliente.Despacho = txtDireccionDespacho.getText().toString().trim();
        cliente.FechaCreacion = Util.formatoFechaQuery(new Date());
        cliente.Giro = txtGiroNegocio.getText().toString().trim();
        cliente.RepresentanteLegal = txtRepresentaLegal.getText().toString().trim();
        ClienteDAO clienteDAO = new ClienteDAO(getApplicationContext());
        long id = clienteDAO.registrarCliente(cliente);
        cliente.iCliente = id;

        Log.d(this.getClass().getName(), "ID: " + id);
        Intent intent = new Intent(getApplicationContext(), ClienteActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(Constantes.TAB, Constantes.NUEVO);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {


        AlertDialog.Builder builder = new AlertDialog.Builder(AgregarClienteActivity.this);
        builder.setTitle(R.string.confirmacion);
        builder.setMessage(R.string.guardar_cliente);

        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                grabarCliente();


            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                AgregarClienteActivity.super.onBackPressed();

            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.agregar_cliente_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_guardar:
                grabarCliente();
                break;
            case R.id.action_cancelar:
                AgregarClienteActivity.super.onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
