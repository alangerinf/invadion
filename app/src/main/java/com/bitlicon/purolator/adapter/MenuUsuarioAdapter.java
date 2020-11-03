package com.bitlicon.purolator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bitlicon.purolator.R;
import com.bitlicon.purolator.entities.Opcion;

import java.util.ArrayList;

public class MenuUsuarioAdapter extends ArrayAdapter<Opcion> {


    public MenuUsuarioAdapter(Context context, ArrayList<Opcion> opciones) {
        super(context, 0, opciones);
        // TODO Auto-generated constructor stub
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {
        Opcion opcion = getItem(position);

        if (convertview == null) {
            convertview = LayoutInflater.from(getContext()).inflate(R.layout.item_menu_usuario, parent, false);
        }

        TextView txtMenuOpcion = (TextView) convertview.findViewById(R.id.txtMenuOpcion);
        txtMenuOpcion.setText(opcion.getNombreOpcion());

        return convertview;
    }
}
