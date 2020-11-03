package com.bitlicon.purolator.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bitlicon.purolator.R;
import com.bitlicon.purolator.adapter.DeudaAdapter;
import com.bitlicon.purolator.adapter.DeudaResumenAdapter;
import com.bitlicon.purolator.dao.MovimientoDAO;
import com.bitlicon.purolator.entities.Cliente;
import com.bitlicon.purolator.entities.Movimiento;
import com.bitlicon.purolator.lib.SessionManager;
import com.bitlicon.purolator.util.Constantes;
import com.bitlicon.purolator.util.Util;

import java.util.ArrayList;

/**
 * Created by EduardoAndres on 12/08/2015.
 */

public class DeudaResumenActivity extends ControlActivity{

    private ListView mListViewDeudas;
    private TextView mTextViewNombreCliente;
    private MovimientoDAO movimientoDAO;
    private int tipo;
    private int candidato;
    private double deuda;
    private DeudaResumenAdapter deudaAdapter;
    private Cliente cliente;

    private CheckBox chkDeudaTotal;
    private CheckBox chkDeutaCorriente;
    private CheckBox chkDeudaMorosa;
    private CheckBox chkLetraNoVencida;
    private CheckBox chkLetraVencida;

    private CheckBox checkLetras;
    private CheckBox checkBoletas;
    private CheckBox checkFacturas;
    private CheckBox checkAbono;
    private CheckBox checkCargo;

    private RadioButton rbtnHoy;
    private RadioButton rbtnRangoFecha;
    private RadioButton rbtnVendedor;

    private SessionManager manager;

    private ImageButton imgCorreo;
    private ImageButton imgLlamada;
    private Menu menu;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.resumen_deuda_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                DeudaResumenActivity.super.onBackPressed();
                break;
            case R.id.action_filtrar:
                mostrarDialogoContextual();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_deuda_resumen);
        Util.setIconMenu(R.drawable.ic_back, this);


        manager = new SessionManager(getApplicationContext());
        cliente = (Cliente) getIntent().getSerializableExtra("cliente");
        tipo = (int) getIntent().getSerializableExtra("tipo");
        //deuda = (double) getIntent().getSerializableExtra("deuda");
        mListViewDeudas = (ListView) findViewById(R.id.lvDeudas);
        mTextViewNombreCliente = (TextView) findViewById(R.id.txtNombreCliente);
        imgLlamada = (ImageButton) findViewById(R.id.imgLlamada);
        imgCorreo = (ImageButton) findViewById(R.id.imgCorreo);

        //setTitle("Deuda : " + deuda);
        this.setTitle("Deuda : ");

        TextView txtCodigoCliente = (TextView) findViewById(R.id.txtCodigoCliente);
        txtCodigoCliente.setText(cliente.ClienteID);
        movimientoDAO = new MovimientoDAO(getApplicationContext());

        mTextViewNombreCliente.setText(cliente.Nombre);

        imgLlamada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "tel:" + cliente.Telefono;
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(uri));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                DeudaResumenActivity.this.startActivity(intent);
            }
        });

        imgCorreo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{cliente.Email});
                email.putExtra(Intent.EXTRA_SUBJECT, "");
                email.putExtra(Intent.EXTRA_TEXT, "");
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Enviar Correo"));
            }
        });

        ArrayList<Movimiento> movimientos = movimientoDAO.listarMovimientos(cliente.ClienteID, tipo, Constantes.EMPTY);
        noHayDeudas(movimientos);


        deudaAdapter = new DeudaResumenAdapter(getApplicationContext(), movimientos);
        mListViewDeudas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return mostrarDialogoContextual();
            }
        });
        mListViewDeudas.setAdapter(deudaAdapter);
        ((LinearLayout) mListViewDeudas.getParent()).setOnLongClickListener(getL());
    }
    private void noHayDeudas(ArrayList<Movimiento> movimientos) {
        TextView textView = (TextView) findViewById(R.id.mensaje);

        if (movimientos != null && movimientos.size() == 0) {
            textView.setVisibility(View.VISIBLE);

            textView.setText("No registra " + this.getTitle());

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
        final Dialog dialog = new Dialog(DeudaResumenActivity.this);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogo_context_deuda_resumen);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();

        layoutParams.copyFrom(window.getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        window.setAttributes(layoutParams);

        Button btnFiltrar = (Button) dialog.findViewById(R.id.btnFiltrar);

        chkDeudaTotal = (CheckBox) dialog.findViewById(R.id.opt_total);
        chkDeutaCorriente = (CheckBox) dialog.findViewById(R.id.opt_corriente);
        chkDeudaMorosa = (CheckBox) dialog.findViewById(R.id.opt_morosa);
        chkLetraNoVencida = (CheckBox) dialog.findViewById(R.id.opt_no_vencida);
        chkLetraVencida = (CheckBox) dialog.findViewById(R.id.opt_vencida);

        rbtnHoy = (RadioButton) dialog.findViewById(R.id.opt_Hoy);
        rbtnRangoFecha = (RadioButton) dialog.findViewById(R.id.opt_Rango);
        rbtnVendedor = (RadioButton) dialog.findViewById(R.id.opt_Vendedor);

        checkLetras = (CheckBox) dialog.findViewById(R.id.check_letras);
        checkBoletas = (CheckBox) dialog.findViewById(R.id.check_boletas);
        checkFacturas = (CheckBox) dialog.findViewById(R.id.check_facturas);
        checkAbono = (CheckBox) dialog.findViewById(R.id.check_abono);
        checkCargo = (CheckBox) dialog.findViewById(R.id.check_cargo);

        candidato = -1;

        chkDeudaTotal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                BloqueoTipoDocumento();
            }
        });

        chkDeutaCorriente.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                BloqueoTipoDocumento();

            }
        });

        chkDeudaMorosa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                BloqueoTipoDocumento();
            }
        });

        chkLetraNoVencida.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                BloqueoTipoDocumento();
            }
        });

        chkLetraVencida.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                BloqueoTipoDocumento();
            }
        });

        checkLetras.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                BloqueoTipoDeuda();

            }
        });

        checkBoletas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                BloqueoTipoDeuda();
            }
        });


        checkFacturas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                BloqueoTipoDeuda();
            }
        });

        checkAbono.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                BloqueoTipoDeuda();
            }
        });

        checkCargo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                BloqueoTipoDeuda();
            }
        });

        btnFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setTitle(Constantes.TIPOS[candidato]);
                StringBuilder sb = new StringBuilder();
                StringBuilder query = new StringBuilder();

                if (checkLetras.isChecked())
                {
                    sb.append("L/");
                    query.append("'L',");
                }
                if(checkBoletas.isChecked())
                {
                    sb.append("B/");
                    query.append("'B',");
                }
                if (checkFacturas.isChecked())
                {
                    sb.append("F/");
                    query.append("'F',");
                }
                if (checkAbono.isChecked())
                {
                    sb.append("NA/");
                    query.append("'D',");
                }
                if (checkCargo.isChecked())
                {
                    sb.append("NC/");
                    query.append("'V','C','Q',");
                }

                int length = sb.toString().length();
                String queryResult = Constantes.EMPTY;
                if (length > 0) {
                    setTitle(getTitle() + " - " + sb.toString().substring(0, length - 1));
                    int len = query.toString().length();
                    queryResult = "(" + query.toString().substring(0, len - 1) + ")";
                }

                ArrayList<Movimiento> movimientos = movimientoDAO.listarMovimientos(cliente.ClienteID, candidato, queryResult);
                deudaAdapter.clear();
                deudaAdapter.addAll(movimientos);
                deudaAdapter.notifyDataSetChanged();
                noHayDeudas(movimientos);
                dialog.dismiss();

            }
        });

        dialog.show();
        return true;
    }

    private void BloqueoTipoDocumento()
    {
        if(chkDeudaTotal.isChecked() || chkDeutaCorriente.isChecked() || chkDeudaMorosa.isChecked()
                ||  chkLetraNoVencida.isChecked() || chkLetraVencida.isChecked())
        {
            checkLetras.setChecked(false);
            checkBoletas.setChecked(false);
            checkFacturas.setChecked(false);
            checkAbono.setChecked(false);
            checkCargo.setChecked(false);

            checkBoletas.setEnabled(false);
            checkLetras.setEnabled(false);
            checkFacturas.setEnabled(false);
            checkAbono.setEnabled(false);
            checkCargo.setEnabled(false);
        }
        else
        {
            checkBoletas.setEnabled(true);
            checkLetras.setEnabled(true);
            checkFacturas.setEnabled(true);
            checkAbono.setEnabled(true);
            checkCargo.setEnabled(true);
        }

    }

    private void BloqueoTipoDeuda()
    {
        if(checkBoletas.isChecked() || checkLetras.isChecked() || checkFacturas.isChecked()
                || checkAbono.isChecked() || checkCargo.isChecked())
        {
            chkDeudaTotal.setChecked(false);
            chkDeutaCorriente.setChecked(false);
            chkDeudaMorosa.setChecked(false);
            chkLetraNoVencida.setChecked(false);
            chkLetraVencida.setChecked(false);

            chkDeudaTotal.setEnabled(false);
            chkDeutaCorriente.setEnabled(false);
            chkDeudaMorosa.setEnabled(false);
            chkLetraNoVencida.setEnabled(false);
            chkLetraVencida.setEnabled(false);
        }
        else
        {
            chkDeudaTotal.setEnabled(true);
            chkDeutaCorriente.setEnabled(true);
            chkDeudaMorosa.setEnabled(true);
            chkLetraNoVencida.setEnabled(true);
            chkLetraVencida.setEnabled(true);
        }
    }

}