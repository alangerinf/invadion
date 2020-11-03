package com.bitlicon.purolator.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.fragment.app.Fragment;

import com.bitlicon.purolator.R;
import com.bitlicon.purolator.entities.Cliente;
import com.bitlicon.purolator.entities.Factura;

public class CondicionFragment extends Fragment {

    private View rootView;
    private RadioButton radio_contado_adelantado;
    private RadioButton radio_contado_contra_entrega;
    private RadioButton radio_credito_letras;
    private RadioButton radio_credito_facturas;
    private RadioButton radio_otros;
    private CheckBox checkbox_contado;
    private CheckBox checkbox_descuento1;
    private CheckBox checkbox_descuento2;
    private CheckBox checkbox_descuento3;
    private CheckBox checkbox_otros;
    private EditText txtObservaciones;

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

        rootView = inflater.inflate(R.layout.fragment_condicion, container, false);
        Cliente cliente = (Cliente) getArguments().getSerializable("cliente");
        Factura factura = (Factura) getArguments().getSerializable("factura");
        radio_contado_adelantado = (RadioButton) rootView.findViewById(R.id.radio_contado_adelantado);
        radio_contado_adelantado.setEnabled(false);

        radio_contado_contra_entrega = (RadioButton) rootView.findViewById(R.id.radio_contado_contra_entrega);
        radio_contado_contra_entrega.setEnabled(false);

        radio_credito_letras = (RadioButton) rootView.findViewById(R.id.radio_credito_letras);
        radio_credito_letras.setEnabled(false);

        radio_credito_facturas = (RadioButton) rootView.findViewById(R.id.radio_credito_facturas);
        radio_credito_facturas.setEnabled(false);

        radio_otros = (RadioButton) rootView.findViewById(R.id.radio_otros);
        radio_otros.setEnabled(false);

        checkbox_contado = (CheckBox) rootView.findViewById(R.id.checkbox_descuento4);
        checkbox_contado.setEnabled(false);

        checkbox_descuento1 = (CheckBox) rootView.findViewById(R.id.checkbox_descuento1);
        checkbox_descuento1.setEnabled(false);

        checkbox_descuento2 = (CheckBox) rootView.findViewById(R.id.checkbox_descuento2);
        checkbox_descuento2.setEnabled(false);

        checkbox_descuento3 = (CheckBox) rootView.findViewById(R.id.checkbox_descuento3);
        checkbox_descuento3.setEnabled(false);

        checkbox_otros = (CheckBox) rootView.findViewById(R.id.checkbox_descuento5);
        checkbox_otros.setEnabled(false);

        txtObservaciones = (EditText) rootView.findViewById(R.id.txtObservaciones);
        txtObservaciones.setEnabled(false);




        StringBuilder sb = new StringBuilder();
        String termino = factura.TerminoVenta;
        sb.append("Condición: ");
        try {
            Integer valor = Integer.valueOf(termino);
            switch (valor) {
                case 1:
                    radio_contado_contra_entrega.setChecked(true);
                    sb.append(radio_contado_contra_entrega.getText());
                    break;
                case 2:
                    radio_contado_adelantado.setChecked(true);
                    sb.append(radio_contado_adelantado.getText());
                    break;
                case 3:
                    radio_credito_facturas.setChecked(true);
                    sb.append(radio_credito_facturas.getText());
                    break;
                case 4:
                    radio_credito_facturas.setChecked(true);
                    sb.append(radio_credito_facturas.getText());
                    break;
                case 5:
                    radio_credito_facturas.setChecked(true);
                    sb.append(radio_credito_facturas.getText());
                    break;
                default:
                    radio_credito_letras.setChecked(true);
                    sb.append(radio_credito_letras.getText());
                    break;
            }
        } catch (Exception e) {
            sb.append(radio_otros.getText());
            radio_otros.setChecked(true);
        }

        if (factura.Descuento1 > 0) {
            checkbox_descuento1.setChecked(true);
            sb.append("\n");
            sb.append("Descuento 01: ");
            checkbox_descuento1.setText(factura.Descuento1 + " %");

            sb.append(factura.Descuento1);
            sb.append("%");
        }
        if (factura.Descuento2 > 0) {

            checkbox_descuento2.setChecked(true);
            sb.append("\n");
            sb.append("Descuento 02: ");
            checkbox_descuento2.setText(factura.Descuento2 + " %");
            sb.append(factura.Descuento2);
            sb.append("%");

        }
        if (factura.Descuento3 > 0) {
            checkbox_descuento3.setChecked(true);
            sb.append("\n");
            sb.append("Descuento 03: ");
            checkbox_descuento3.setText(factura.Descuento3 + " %");

            sb.append(factura.Descuento3);
            sb.append("%");

        }
        if (factura.Descuento1 <= 0 && factura.Descuento2 <= 0 && factura.Descuento3 <= 0) {
            if (radio_contado_adelantado.isChecked() && radio_contado_contra_entrega.isChecked()) {
                checkbox_contado.setChecked(true);
                sb.append("\n");
                sb.append("Descuento: Ninguno");
            }
        }
        Log.d("CondicionFragment", sb.toString());
        txtObservaciones.setText(sb.toString());
        return rootView;
    }
}
