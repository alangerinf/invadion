package com.bitlicon.purolator.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;

import com.bitlicon.purolator.R;
import com.bitlicon.purolator.activity.BaseActivity;
import com.bitlicon.purolator.dao.ClienteDAO;
import com.bitlicon.purolator.entities.Cliente;
import com.bitlicon.purolator.fragment.BaseFragment;
import com.bitlicon.purolator.lib.SessionManager;
import com.bitlicon.purolator.util.Constantes;
import com.bitlicon.purolator.util.Util;

import java.util.ArrayList;
import java.util.Calendar;

public class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private int id;
    public DateDialog(int id) {
        this.id = id;

    }
    private TextView txtRutaTitulo;
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
        int dia = Util.getNumberDateOfTheWeek(c.getTime());
        int week = c.get(Calendar.WEEK_OF_MONTH);
        if(week == 6) week = 1;
        boolean result = week % 2 == 0;
        Log.d(this.getClass().getName(), "Día de la semana: " + dia);

        BaseFragment fragment = (BaseFragment) ((BaseActivity) getActivity()).getSupportFragmentManager().getFragments().get(id);
        ClienteDAO clienteDAO = new ClienteDAO(getActivity());
        txtRutaTitulo = (TextView) getActivity().findViewById(R.id.txtRutaTitulo);
        ArrayList<Cliente> clientes = clienteDAO.buscarClientesAvanzada(Constantes.EMPTY, ClienteDAO.NOMBRE, ClienteDAO.NOMBRE, dia,
                false, result);
        fragment.getClienteAdapter().clear();
        fragment.getClienteAdapter().addAll(clientes);
        fragment.getClienteAdapter().notifyDataSetChanged();
        fragment.getIndexList(clientes);
        txtRutaTitulo.setText("RUTA " + Util.formatoFechaMes(c.getTime()));
        fragment.setValorDia(dia);

    }
}