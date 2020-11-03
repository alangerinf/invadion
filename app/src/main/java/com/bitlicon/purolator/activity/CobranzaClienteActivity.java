package com.bitlicon.purolator.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.bitlicon.purolator.R;
import com.bitlicon.purolator.dao.ClienteDAO;
import com.bitlicon.purolator.entities.Cliente;
import com.bitlicon.purolator.fragment.ClienteMovimientoGeneralFragment;
import com.bitlicon.purolator.fragment.ClienteMovimientoRutaFragment;
import com.bitlicon.purolator.util.Constantes;
import com.bitlicon.purolator.util.Util;
import java.util.ArrayList;
import java.util.Calendar;

public class CobranzaClienteActivity extends ControlFragmentActivity {

    private int tipo;
    private String total;
    LinearLayout lnRutaFranja;
    LinearLayout lnGeneralFranja,lnGeneralCliente,lnRutaCliente;
    public ViewPager viewPagerClientes;
    private ClienteSlidePagerAdapter mClienteSlidePagerAdapter;
    private TextView txtRutaTitulo, txtGeneralTitulo;
    private ClienteMovimientoRutaFragment clienteMovimientoRutaFragment;
    private ClienteMovimientoGeneralFragment clienteMovimientoGeneralFragment;
    private static final int NUM_TABS = 2;
    Activity base;
    private CheckBox check_DeudaTotal;
    private CheckBox check_DeudaCorriente;
    private CheckBox check_DeudaMorosa;
    private CheckBox check_DeudaVencida;
    private CheckBox check_DeudaNoVencida;
    private RadioButton item1,item2,item3,item4,item5;
    private CheckBox checkLetras;
    private CheckBox checkFacturas;
    private CheckBox checkAbono;
    private CheckBox checkCargo;
    private CheckBox checkBoletas;
    private String deudaSeleccionadas = "";
    ClienteDAO clienteDAO;
    int diaSeleccionado = 0;
    boolean isParSeleccionado = false;
    String valorSeleccionado = Constantes.EMPTY;
    String campoSeleccionado = ClienteDAO.NOMBRE;
    private int[] menu = {R.menu.cobranza_cliente_ruta_actions, R.menu.cobranza_cliente_general_actions};
    private boolean activoRuta = true;
    private boolean activoGeneral = false;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(mLayout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                CobranzaClienteActivity.super.onBackPressed();
                break;
            case R.id.action_ruta:
                ruta();
                break;
            case R.id.action_filtrar_deuda:
                mostrarDialogoFiltrosDeuda();
                break;
            case R.id.action_busqueda_ruta:
                mostrarDialogoBusquedaAvanzadaRuta();
                break;
            case R.id.action_busqueda_general:
                mostrarDialogoBusquedaAvanzada();
                break;
            case R.id.action_escoger_ruta_general:
                viewPagerClientes.setCurrentItem(0);
                ruta();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setLayout(int mLayout) {
        this.mLayout = mLayout;
    }
    private int mLayout;
    private String queryResult = "";
    private String documentos = "";
    private boolean checkAbonoBoolean = false;
    ArrayList<Cliente> clientesDialogo;

    public boolean mostrarDialogoFiltrosDeuda() {

        checkAbonoBoolean = false;
        final Dialog dialog = new Dialog(CobranzaClienteActivity.this);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogo_context_deuda);

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
        checkBoletas = (CheckBox) dialog.findViewById(R.id.check_boletas);

        check_DeudaTotal = (CheckBox) dialog.findViewById(R.id.check_deudaTotal);
        check_DeudaCorriente = (CheckBox) dialog.findViewById(R.id.check_deudaCorriente);
        check_DeudaMorosa = (CheckBox) dialog.findViewById(R.id.check_deudaMorosa);
        check_DeudaVencida = (CheckBox) dialog.findViewById(R.id.check_deudaVencida);
        check_DeudaNoVencida = (CheckBox) dialog.findViewById(R.id.check_deudaNoVencida);

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


        String[] arregloTipo = deudaSeleccionadas.split(",");
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
                    case "L": // Letras
                        checkLetras.setChecked(true);
                        break;
                    case "F": // Facturas
                       checkFacturas.setChecked(true);
                        break;
                    case "B": // Abono.
                        checkAbonoBoolean = true;
                        checkAbono.setChecked(true);
                        break;
                    case "D": // Notas de Credito.
                        checkAbonoBoolean = true;
                        checkAbono.setChecked(true);
                        break;
                    case "V": // Boletas
                        checkBoletas.setChecked(true);
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

                deudaSeleccionadas = "";
                documentos = "";
                queryResult = Constantes.EMPTY;
                checkAbonoBoolean = false;
                StringBuilder query = new StringBuilder();
                if (checkLetras.isChecked()) {
                    documentos += "L,";
                    query.append("'L',");
                }
                if (checkFacturas.isChecked()) {
                    query.append("'F',");
                    documentos += "F,";
                }
                if (checkAbono.isChecked()) {
                    //query.append("'B','D',");
                    checkAbonoBoolean = true;
                    documentos += "B,D,";
                }
                if (checkCargo.isChecked()) {
                    documentos += "C,Q,";
                    query.append("'C','Q',");
                }
                if (checkBoletas.isChecked())
                {
                    documentos += "V,";
                    query.append("'V',");
                }

                int len = query.toString().length();
                if(len > 0) {
                    queryResult = "(" + query.toString().substring(0, len - 1) + ")";
                    documentos = documentos.substring(0,documentos.length() -1);
                }

                if(check_DeudaTotal.isChecked()) {
                    deudaSeleccionadas += Constantes.DEUDA + ",";
                }else {

                    if (check_DeudaCorriente.isChecked()) {
                        deudaSeleccionadas += Constantes.CORRIENTE + ",";
                    }
                    if (check_DeudaMorosa.isChecked()) {
                        deudaSeleccionadas += Constantes.MOROSA + ",";
                    }
                    if (check_DeudaVencida.isChecked()) {
                        deudaSeleccionadas += Constantes.VENCIDAS + ",";
                    }
                    if (check_DeudaNoVencida.isChecked()) {
                        deudaSeleccionadas += Constantes.LETRAS + ",";
                    }
                }

                if(deudaSeleccionadas.length()>0)
                {
                    deudaSeleccionadas = deudaSeleccionadas.substring(0,deudaSeleccionadas.length() -1 );
                }

                clienteDAO = new ClienteDAO(base);

                ArrayList<Cliente> clientes = clienteDAO.buscarClientesAvanzadaxDeudaMultiple(valorSeleccionado, campoSeleccionado, ClienteDAO.NOMBRE,
                        Constantes.DIA_INVALIDO, false, false, deudaSeleccionadas,queryResult,checkAbono.isChecked());

                clienteMovimientoGeneralFragment.obtenerClienteMovimientoAdapter().clear();
                clienteMovimientoGeneralFragment.obtenerClienteMovimientoAdapter().addAll(clientes);
                clienteMovimientoGeneralFragment.obtenerClienteMovimientoAdapter().notifyDataSetChanged();
                clienteMovimientoGeneralFragment.getIndexList(clientes);
                clienteMovimientoGeneralFragment.obtenerClienteMovimientoAdapter().setTipo(deudaSeleccionadas);
                clienteMovimientoGeneralFragment.obtenerClienteMovimientoAdapter().setDocumentos(documentos);


                ArrayList<Cliente> clientesRuta = clienteDAO.buscarClientesAvanzadaxDeudaMultiple(valorSeleccionado, campoSeleccionado, ClienteDAO.NOMBRE,
                        diaSeleccionado, false, isParSeleccionado, deudaSeleccionadas,queryResult,checkAbono.isChecked());

                clienteMovimientoRutaFragment.obtenerClienteMovimientoAdapter().clear();
                clienteMovimientoRutaFragment.obtenerClienteMovimientoAdapter().addAll(clientesRuta);
                clienteMovimientoRutaFragment.obtenerClienteMovimientoAdapter().notifyDataSetChanged();
                clienteMovimientoRutaFragment.getIndexList(clientesRuta);
                clienteMovimientoRutaFragment.obtenerClienteMovimientoAdapter().setTipo(deudaSeleccionadas);
                clienteMovimientoRutaFragment.obtenerClienteMovimientoAdapter().setDocumentos(documentos);

                if(activoGeneral) {
                    if (clientes.size() > 0) {
                        int cantidadClientes = clientes.size();
                        base.setTitle("Deuda : " + Util.formatoDineroSoles(clientes.get(cantidadClientes - 1).DeudaTotal));
                    } else {
                        base.setTitle("Deuda : 0.00");
                    }
                }

                if(activoRuta) {
                    if (clientesRuta.size() > 0) {
                        int cantidadClientes = clientesRuta.size();
                        base.setTitle("Deuda : " + Util.formatoDineroSoles(clientesRuta.get(cantidadClientes - 1).DeudaTotal));
                    } else {
                        base.setTitle("Deuda : 0.00");
                    }
                }

                dialog.dismiss();

            }
        });

        dialog.show();
        return true;
    }

    public boolean mostrarDialogoBusquedaAvanzada() {

        final Dialog dialog_busqueda = new Dialog(base);
        dialog_busqueda.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog_busqueda.setContentView(R.layout.dialogo_busqueda_avanzada);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        Window window = dialog_busqueda.getWindow();
        layoutParams.copyFrom(window.getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);

        clienteDAO = new ClienteDAO(base);
        dialog_busqueda.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        LinearLayout lnCancelar = (LinearLayout) dialog_busqueda.findViewById(R.id.lnCancelar);
        lnCancelar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog_busqueda.dismiss();
            }
        });

        RadioGroup radioGroup = (RadioGroup) dialog_busqueda.findViewById(R.id.lstOpciones);
        item1 = (RadioButton) radioGroup.getChildAt(0);
        item2 = (RadioButton) radioGroup.getChildAt(1);
        item3 = (RadioButton) radioGroup.getChildAt(2);
        item4 = (RadioButton) radioGroup.getChildAt(3);
        item5 = (RadioButton) radioGroup.getChildAt(4);

        final EditText txtCampoBusquedaAvanzada = (EditText) dialog_busqueda.findViewById(R.id.txtCampoBusquedaAvanzada);
        txtCampoBusquedaAvanzada.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String valor = txtCampoBusquedaAvanzada.getText().toString();
                    valorSeleccionado = valor;
                    if (item1.isChecked()) {
                        clientesDialogo = clienteDAO.buscarClientesAvanzadaxDeudaMultiple(valor, ClienteDAO.NOMBRE, ClienteDAO.NOMBRE,
                                Constantes.DIA_INVALIDO, false, false, deudaSeleccionadas, queryResult,checkAbono.isChecked());
                        campoSeleccionado = ClienteDAO.NOMBRE;
                    }
                    if (item2.isChecked()) {
                        clientesDialogo = clienteDAO.buscarClientesAvanzadaxDeudaMultiple(valor, ClienteDAO.TELEFONO, ClienteDAO.NOMBRE,
                                Constantes.DIA_INVALIDO, false, false, deudaSeleccionadas, queryResult,checkAbono.isChecked());
                        campoSeleccionado = ClienteDAO.TELEFONO;
                    }
                    if (item3.isChecked()) {
                        clientesDialogo = clienteDAO.buscarClientesAvanzadaxDeudaMultiple(valor, ClienteDAO.RUC, ClienteDAO.NOMBRE,
                                Constantes.DIA_INVALIDO, false, false, deudaSeleccionadas, queryResult,checkAbonoBoolean);
                        campoSeleccionado = ClienteDAO.RUC;
                    }
                    if (item4.isChecked()) {
                        clientesDialogo = clienteDAO.buscarClientesAvanzadaxDeudaMultiple(valor, ClienteDAO.CLIENTE_ID, ClienteDAO.NOMBRE,
                                Constantes.DIA_INVALIDO, false, false, deudaSeleccionadas, queryResult,checkAbonoBoolean);
                        campoSeleccionado = ClienteDAO.CLIENTE_ID;
                    }

                    if (item5.isChecked()) {
                        clientesDialogo = clienteDAO.buscarClientesAvanzadaxDeudaMultiple(valor, ClienteDAO.DNI, ClienteDAO.NOMBRE,
                                Constantes.DIA_INVALIDO, false, false, deudaSeleccionadas, queryResult,checkAbonoBoolean);
                        campoSeleccionado = ClienteDAO.DNI;
                    }

                    clienteMovimientoGeneralFragment.obtenerClienteMovimientoAdapter().clear();
                    clienteMovimientoGeneralFragment.obtenerClienteMovimientoAdapter().addAll(clientesDialogo);
                    clienteMovimientoGeneralFragment.obtenerClienteMovimientoAdapter().notifyDataSetChanged();
                    clienteMovimientoGeneralFragment.getIndexList(clientesDialogo);

                    if(clientesDialogo.size() > 0)
                    {
                        int cantidadClientes = clientesDialogo.size();
                        base.setTitle("Deuda : " + Util.formatoDineroSoles(clientesDialogo.get(cantidadClientes-1).DeudaTotal));
                    }else
                    {
                        base.setTitle("Deuda : 0.00");
                    }

                    dialog_busqueda.dismiss();
                }
                return false;
            }
        });
        dialog_busqueda.show();
        return true;
    }

    public boolean mostrarDialogoBusquedaAvanzadaRuta() {

        final Dialog dialog_busqueda = new Dialog(base);
        dialog_busqueda.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog_busqueda.setContentView(R.layout.dialogo_busqueda_avanzada);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        Window window = dialog_busqueda.getWindow();
        layoutParams.copyFrom(window.getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);

        clienteDAO = new ClienteDAO(base);
        dialog_busqueda.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        LinearLayout lnCancelar = (LinearLayout) dialog_busqueda.findViewById(R.id.lnCancelar);
        lnCancelar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog_busqueda.dismiss();
            }
        });

        RadioGroup radioGroup = (RadioGroup) dialog_busqueda.findViewById(R.id.lstOpciones);
        item1 = (RadioButton) radioGroup.getChildAt(0);
        item2 = (RadioButton) radioGroup.getChildAt(1);
        item3 = (RadioButton) radioGroup.getChildAt(2);
        item4 = (RadioButton) radioGroup.getChildAt(3);
        item5 = (RadioButton) radioGroup.getChildAt(4);

        final EditText txtCampoBusquedaAvanzada = (EditText) dialog_busqueda.findViewById(R.id.txtCampoBusquedaAvanzada);
        txtCampoBusquedaAvanzada.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String valor = txtCampoBusquedaAvanzada.getText().toString();
                    valorSeleccionado = valor;
                    if (item1.isChecked()) {
                        clientesDialogo = clienteDAO.buscarClientesAvanzadaxDeudaMultiple(valor, ClienteDAO.NOMBRE, ClienteDAO.NOMBRE,
                                diaSeleccionado, false, isParSeleccionado, deudaSeleccionadas, queryResult,checkAbonoBoolean);
                        campoSeleccionado = ClienteDAO.NOMBRE;
                    }
                    if (item2.isChecked()) {
                        clientesDialogo = clienteDAO.buscarClientesAvanzadaxDeudaMultiple(valor, ClienteDAO.TELEFONO, ClienteDAO.NOMBRE,
                                diaSeleccionado, false, isParSeleccionado, deudaSeleccionadas, queryResult,checkAbonoBoolean);
                        campoSeleccionado = ClienteDAO.TELEFONO;
                    }
                    if (item3.isChecked()) {
                        clientesDialogo = clienteDAO.buscarClientesAvanzadaxDeudaMultiple(valor, ClienteDAO.RUC, ClienteDAO.NOMBRE,
                                diaSeleccionado, false, isParSeleccionado, deudaSeleccionadas, queryResult,checkAbonoBoolean);
                        campoSeleccionado = ClienteDAO.RUC;
                    }
                    if (item4.isChecked()) {
                        clientesDialogo = clienteDAO.buscarClientesAvanzadaxDeudaMultiple(valor, ClienteDAO.CLIENTE_ID, ClienteDAO.NOMBRE,
                                diaSeleccionado, false, isParSeleccionado, deudaSeleccionadas, queryResult,checkAbonoBoolean);
                        campoSeleccionado = ClienteDAO.CLIENTE_ID;
                    }

                    if (item5.isChecked()) {
                        clientesDialogo = clienteDAO.buscarClientesAvanzadaxDeudaMultiple(valor, ClienteDAO.DNI, ClienteDAO.NOMBRE,
                                diaSeleccionado, false, isParSeleccionado, deudaSeleccionadas, queryResult,checkAbonoBoolean);
                        campoSeleccionado = ClienteDAO.DNI;
                    }

                    clienteMovimientoRutaFragment.obtenerClienteMovimientoAdapter().clear();
                    clienteMovimientoRutaFragment.obtenerClienteMovimientoAdapter().addAll(clientesDialogo);
                    clienteMovimientoRutaFragment.obtenerClienteMovimientoAdapter().notifyDataSetChanged();
                    clienteMovimientoRutaFragment.getIndexList(clientesDialogo);

                    if(clientesDialogo.size() > 0)
                    {
                        int cantidadClientes = clientesDialogo.size();
                        base.setTitle("Deuda : " + Util.formatoDineroSoles(clientesDialogo.get(cantidadClientes-1).DeudaTotal));
                    }else
                    {
                        base.setTitle("Deuda : 0.00");
                    }

                    dialog_busqueda.dismiss();
                }
                return false;
            }
        });
        dialog_busqueda.show();
        return true;
    }

    public void ruta()
    {
        DateDialog dialog = new DateDialog();
        android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        dialog.show(ft, "DatePicker");
    }

    public class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        public Dialog onCreateDialog(Bundle savedInstaceState) {

            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialogDatePicker = new DatePickerDialog(getActivity(), this, year, month, day);
            dialogDatePicker.getDatePicker().setCalendarViewShown(true);
            dialogDatePicker.getDatePicker().setSpinnersShown(false);
            return dialogDatePicker;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {

            final Calendar c = Calendar.getInstance();
            c.set(year, month, day);
            diaSeleccionado = Util.getNumberDateOfTheWeek(c.getTime());
            int week = c.get(Calendar.WEEK_OF_MONTH);
            isParSeleccionado = week % 2 == 0;
            clienteDAO = new ClienteDAO(getActivity());


            ArrayList<Cliente> clientes = clienteDAO.buscarClientesAvanzadaxDeudaMultiple(valorSeleccionado,campoSeleccionado, ClienteDAO.NOMBRE,
                    diaSeleccionado, false, isParSeleccionado, deudaSeleccionadas,queryResult,checkAbonoBoolean);


            clienteMovimientoRutaFragment.obtenerClienteMovimientoAdapter().clear();
            clienteMovimientoRutaFragment.obtenerClienteMovimientoAdapter().addAll(clientes);
            clienteMovimientoRutaFragment.obtenerClienteMovimientoAdapter().notifyDataSetChanged();
            clienteMovimientoRutaFragment.getIndexList(clientes);

            if(clientes.size() > 0)
            {
                int cantidadClientes = clientes.size();
                base.setTitle("Deuda : " + Util.formatoDineroSoles(clientes.get(cantidadClientes-1).DeudaTotal));
            }else
            {
                base.setTitle("Deuda : 0.00");
            }
            month = month+1;
            String mesAMostrar = String.valueOf(month);
            String diaAMostrar =  String.valueOf(day);
            if(month<10)
            {
                mesAMostrar  = "0" + month;
            }
            if(day<10)
            {
                diaAMostrar  = "0" + day;
            }
            txtRutaTitulo.setText("RUTA : " + diaAMostrar + "/" + mesAMostrar);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cobranza_cliente);

        base = this;
        Util.setIconMenu(R.drawable.ic_back, this);

        tipo = (int) getIntent().getSerializableExtra("tipo");
        total = (String) getIntent().getSerializableExtra("total");

        diaSeleccionado = Util.resetDia();
        isParSeleccionado = Util.esSemanaPar();
        checkAbonoBoolean= false;
        String tituloDeuda = "";

        switch (tipo)
        {
            case Constantes.DEUDA: tituloDeuda = "Deuda: " + total; break;
            case Constantes.CORRIENTE: tituloDeuda = "Deuda: " + total; break;
            case Constantes.MOROSA: tituloDeuda = "Deuda: " + total; break;
            case Constantes.VENCIDAS: tituloDeuda = "Deuda: " + total; break;
            case Constantes.LETRAS: tituloDeuda = "Deuda: " + total; break;

        }
        deudaSeleccionadas = String.valueOf(tipo);

        setTitle("Deuda");
        invalidateOptionsMenu();
        setLayout(menu[0]);

        viewPagerClientes = (ViewPager) findViewById(R.id.viewPagerClientes);

        mClienteSlidePagerAdapter = new ClienteSlidePagerAdapter(getSupportFragmentManager());
        viewPagerClientes.setAdapter(mClienteSlidePagerAdapter);
        viewPagerClientes.setOnPageChangeListener(mClienteSlidePagerAdapter);

        lnRutaFranja = (LinearLayout) findViewById(R.id.lnRutaFranja);
        lnRutaFranja.setVisibility(View.VISIBLE);
        txtRutaTitulo = (TextView) findViewById(R.id.txtRutaTitulo);
        txtRutaTitulo.setTextColor(getResources().getColor(R.color.celeste));

        Calendar calendar = Calendar.getInstance();
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int mes = calendar.get(Calendar.MONTH);
        mes = mes+1;
        String mesAMostrar = String.valueOf(mes);
        String diaAMostrar =  String.valueOf(dayOfMonth);
        if(mes<10)
        {
            mesAMostrar  = "0" + mes;
        }
        if(dayOfMonth<10)
        {
            diaAMostrar  = "0" + dayOfMonth;
        }
        txtRutaTitulo.setText("RUTA : " + diaAMostrar + "/" + mesAMostrar);
        lnRutaCliente = (LinearLayout) findViewById(R.id.lnRutaCliente);
        lnRutaCliente.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                viewPagerClientes.setCurrentItem(0);
                resetRutaCliente();
            }
        });

        lnGeneralCliente = (LinearLayout) findViewById(R.id.lnGeneralCliente);
        lnGeneralCliente.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                viewPagerClientes.setCurrentItem(1);
            }
        });

    }

    public class ClienteSlidePagerAdapter extends FragmentStatePagerAdapter implements ViewPager.OnPageChangeListener {
        public ClienteSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            if (position == Constantes.RUTA) {
                clienteMovimientoRutaFragment = ClienteMovimientoRutaFragment.nuevaInstancia(base,tipo);
                return clienteMovimientoRutaFragment;
            } else if (position == Constantes.GENERAL) {
                clienteMovimientoGeneralFragment = ClienteMovimientoGeneralFragment.nuevaInstancia(base, tipo);
                return clienteMovimientoGeneralFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_TABS;
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }


        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageSelected(int position) {

            base.invalidateOptionsMenu();
            LinearLayout lnfranja = null;
            TextView txtTexto = null;
            limpiarseleccionado();

            switch (position) {
                case Constantes.RUTA:
                    lnfranja = (LinearLayout) base.findViewById(R.id.lnRutaFranja);
                    txtTexto = (TextView) base.findViewById(R.id.txtRutaTitulo);
                    setLayout(menu[0]);

                    resetRutaCliente();

                    break;
                case Constantes.GENERAL:
                    lnfranja = (LinearLayout) base.findViewById(R.id.lnGeneralFranja);
                    txtTexto = (TextView) base.findViewById(R.id.txtGeneralTitulo);
                    setLayout(menu[1]);
                    resetGeneralCliente();

                    break;
            }
            lnfranja.setVisibility(View.VISIBLE);
            txtTexto.setTextColor(getResources().getColor(R.color.celeste));
        }
    }

    public void limpiarseleccionado() {
        lnRutaFranja = (LinearLayout) base.findViewById(R.id.lnRutaFranja);
        lnRutaFranja.setVisibility(View.INVISIBLE);

        lnGeneralFranja = (LinearLayout) base.findViewById(R.id.lnGeneralFranja);
        lnGeneralFranja.setVisibility(View.INVISIBLE);

        txtRutaTitulo = (TextView) base.findViewById(R.id.txtRutaTitulo);
        txtRutaTitulo.setTextColor(getResources().getColor(R.color.plomomenu));
        txtGeneralTitulo = (TextView) base.findViewById(R.id.txtGeneralTitulo);
        txtGeneralTitulo.setTextColor(getResources().getColor(R.color.plomomenu));

    }

    public void resetRutaCliente()
    {
        ArrayList<Cliente> clientes;

        clienteDAO = new ClienteDAO(base);

        diaSeleccionado = Util.resetDia();
        isParSeleccionado = Util.esSemanaPar();
        campoSeleccionado = ClienteDAO.NOMBRE;
        valorSeleccionado = Constantes.EMPTY;

        clientes = clienteDAO.buscarClientesAvanzadaxDeudaMultiple(Constantes.EMPTY, ClienteDAO.NOMBRE, ClienteDAO.NOMBRE,
                Util.resetDia(), false, Util.esSemanaPar(), deudaSeleccionadas,queryResult,checkAbonoBoolean);

        clienteMovimientoRutaFragment.obtenerClienteMovimientoAdapter().clear();
        clienteMovimientoRutaFragment.obtenerClienteMovimientoAdapter().addAll(clientes);
        clienteMovimientoRutaFragment.obtenerClienteMovimientoAdapter().notifyDataSetChanged();
        clienteMovimientoRutaFragment.getIndexList(clientes);
        Calendar calendar = Calendar.getInstance();
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int mes = calendar.get(Calendar.MONTH);
        mes = mes+1;
        String mesAMostrar = String.valueOf(mes);
        String diaAMostrar =  String.valueOf(dayOfMonth);
        if(mes<10)
        {
            mesAMostrar  = "0" + mes;
        }
        if(dayOfMonth<10)
        {
            diaAMostrar  = "0" + dayOfMonth;
        }
        if(clientes.size() > 0)
        {
            int cantidadClientes = clientes.size();
            base.setTitle("Deuda : " + Util.formatoDineroSoles(clientes.get(cantidadClientes-1).DeudaTotal));
        }else
        {
            base.setTitle("Deuda : 0.00");
        }
        activoRuta = true;
        activoGeneral = false;
        txtRutaTitulo.setText("RUTA : " + diaAMostrar + "/" + mesAMostrar);
    }

    public void resetGeneralCliente()
    {
        ArrayList<Cliente> clientes;

        clienteDAO = new ClienteDAO(base);

        campoSeleccionado = ClienteDAO.NOMBRE;
        valorSeleccionado = Constantes.EMPTY;

        clientes = clienteDAO.buscarClientesAvanzadaxDeudaMultiple(Constantes.EMPTY, ClienteDAO.NOMBRE, ClienteDAO.NOMBRE,
                Constantes.DIA_INVALIDO, false, false, deudaSeleccionadas,queryResult,checkAbonoBoolean);

        clienteMovimientoGeneralFragment.obtenerClienteMovimientoAdapter().clear();
        clienteMovimientoGeneralFragment.obtenerClienteMovimientoAdapter().addAll(clientes);
        clienteMovimientoGeneralFragment.obtenerClienteMovimientoAdapter().notifyDataSetChanged();
        clienteMovimientoGeneralFragment.getIndexList(clientes);

        if(clientes.size() > 0)
        {
            int cantidadClientes = clientes.size();
            base.setTitle("Deuda : " + Util.formatoDineroSoles(clientes.get(cantidadClientes-1).DeudaTotal));
        }else
        {
            base.setTitle("Deuda : 0.00");
        }
        activoGeneral = true;
        activoRuta = false;
    }


}
