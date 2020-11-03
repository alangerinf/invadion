package com.bitlicon.purolator.fragment;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.core.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bitlicon.purolator.R;
import com.bitlicon.purolator.activity.BaseActivity;
import com.bitlicon.purolator.activity.CobranzaClienteActivity;
import com.bitlicon.purolator.dao.ConfiguracionDAO;
import com.bitlicon.purolator.dao.MovimientoDAO;
import com.bitlicon.purolator.entities.Configuracion;
import com.bitlicon.purolator.util.Constantes;
import com.bitlicon.purolator.lib.SessionManager;
import com.bitlicon.purolator.util.Util;


public class CobranzaFragment extends Fragment {


    TextView nombreVendedor;
    private TextView txtDeudaTotal;
    private TextView txtDeudaMorosa;
    private TextView txtDeudaCorriente;
    private TextView txtDeudaLetras;
    private TextView txtDeudaVencidas;
    private LinearLayout tvDetalleDeuda;
    private LinearLayout tvDetalleCorriente;
    private LinearLayout tvDetalleMorosa;
    private LinearLayout tvDetalleLetras;
    private LinearLayout tvDetalleVencida;
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

        View rootView = inflater.inflate(R.layout.fragment_cobranza, container, false);

        nombreVendedor = (TextView) rootView.findViewById(R.id.nombreVendedor);
        txtDeudaTotal = (TextView) rootView.findViewById(R.id.txtDeudaTotal);
        txtDeudaMorosa = (TextView) rootView.findViewById(R.id.txtDeudaMorosa);
        txtDeudaCorriente = (TextView) rootView.findViewById(R.id.txtDeudaCorriente);
        txtDeudaVencidas = (TextView) rootView.findViewById(R.id.txtDeudaVencida);
        txtDeudaLetras = (TextView) rootView.findViewById(R.id.txtDeudaLetras);

        tvDetalleDeuda = (LinearLayout) rootView.findViewById(R.id.lldeuda);
        tvDetalleCorriente = (LinearLayout) rootView.findViewById(R.id.llcorriente);
        tvDetalleMorosa = (LinearLayout) rootView.findViewById(R.id.llmorosa);
        tvDetalleLetras = (LinearLayout) rootView.findViewById(R.id.llletras);
        tvDetalleVencida = (LinearLayout) rootView.findViewById(R.id.llvencida);

        SessionManager sessionManager = new SessionManager(getActivity());

        nombreVendedor.setText(sessionManager.getPurolator() + " - " + sessionManager.getFiltech());

        MovimientoDAO movimientoDAO = new MovimientoDAO(getActivity());
        ConfiguracionDAO configDAO = new ConfiguracionDAO(getActivity());
        Configuracion config = configDAO.Obtener();

        int dias = 0;
        dias = 8;
        /*switch (config.Resumen)
        {
            case 0:
                dias = 1;
                break;
            case 1:
                dias = 8;
                break;
            case 2:
                dias = 31;
        }*/

        double deudaTotal = movimientoDAO.deudas(Constantes.DEUDA, dias);
        double deudaMorosa = movimientoDAO.deudas(Constantes.MOROSA, dias);
        double deudaLetrasNoVencidas = movimientoDAO.deudas(Constantes.LETRAS, dias);
        double deudaCorriente = movimientoDAO.deudas(Constantes.CORRIENTE, dias);
        double deudaLetrasVencidas = movimientoDAO.deudas(Constantes.VENCIDAS, dias);

        txtDeudaCorriente.setText(Util.formatoDineroSoles(deudaCorriente));
        txtDeudaTotal.setText(Util.formatoDineroSoles(deudaTotal));
        txtDeudaMorosa.setText(Util.formatoDineroSoles(deudaMorosa));
        txtDeudaLetras.setText(Util.formatoDineroSoles(deudaLetrasNoVencidas));
        txtDeudaVencidas.setText(Util.formatoDineroSoles(deudaLetrasVencidas));


        tvDetalleDeuda.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getActivity(), CobranzaClienteActivity.class);
                intent.putExtra("tipo", Constantes.DEUDA);
                intent.putExtra("total", txtDeudaTotal.getText());
                startActivity(intent);
            }
        });

        //tvDetalleDeuda.setLongClickable(true);
        tvDetalleDeuda.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View view) {
                return mostrarDialogoContextual();
            }
        });

        tvDetalleCorriente.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getActivity(), CobranzaClienteActivity.class);
                intent.putExtra("tipo", Constantes.CORRIENTE);
                intent.putExtra("total", txtDeudaCorriente.getText());
                startActivity(intent);
            }
        });

        tvDetalleCorriente.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View view) {
                return mostrarDialogoContextual();
            }
        });

        tvDetalleMorosa.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getActivity(), CobranzaClienteActivity.class);
                intent.putExtra("tipo", Constantes.MOROSA);
                intent.putExtra("total", txtDeudaMorosa.getText());
                startActivity(intent);
            }
        });

        tvDetalleMorosa.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View view) {
                return mostrarDialogoContextual();
            }
        });

        tvDetalleLetras.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getActivity(), CobranzaClienteActivity.class);
                intent.putExtra("tipo", Constantes.LETRAS);
                intent.putExtra("total", txtDeudaLetras.getText());
                startActivity(intent);
            }
        });
        tvDetalleLetras.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View view) {
                return mostrarDialogoContextual();
            }
        });

        tvDetalleVencida.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getActivity(), CobranzaClienteActivity.class);
                intent.putExtra("tipo", Constantes.VENCIDAS);
                intent.putExtra("total", txtDeudaVencidas.getText());
                startActivity(intent);
            }
        });

        tvDetalleVencida.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View view) {
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


}
