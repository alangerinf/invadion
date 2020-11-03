package com.bitlicon.purolator.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bitlicon.purolator.R;
import com.bitlicon.purolator.activity.FichaTecnicaActivity;
import com.bitlicon.purolator.entities.Equivalencia;
import com.bitlicon.purolator.entities.Producto;
import com.bitlicon.purolator.util.Util;

import java.util.ArrayList;

/**
 * Created by EduardoAndres on 17/12/2015.
 */
public class EquivalenciaAdapter  extends ArrayAdapter<Equivalencia> {
    public EquivalenciaAdapter(Context context,  ArrayList<Equivalencia> equivalencias) {
        super(context, 0, equivalencias);
        // TODO Auto-generated constructor stub
    }

    @Override
    public View getView(final int position, View convertview, ViewGroup parent) {
        final ViewHolder viewHolder;
        final Equivalencia equivalencia = getItem(position);
        if (convertview == null)
        {
            convertview = LayoutInflater.from(getContext()).inflate(R.layout.item_equivalencia, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.txtCodigoOriginal = (TextView) convertview.findViewById(R.id.txtCodigoOriginal);
            viewHolder.txtMarca = (TextView) convertview.findViewById(R.id.txtMarca);
            viewHolder.txtCodPurolator = (TextView) convertview.findViewById(R.id.txtCodPurolator);

            convertview.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertview.getTag();
        }

        if(equivalencia.CodigoOriginal!=null)
        {
            viewHolder.txtCodigoOriginal.setText(equivalencia.CodigoOriginal);
        }

        viewHolder.txtMarca.setText(equivalencia.MarcaOriginal);
        viewHolder.txtCodPurolator.setText(equivalencia.CodigoPurolator);

        convertview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FichaTecnicaActivity.class);
                intent.putExtra("producto", ((Equivalencia)getItem(position)).producto);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }
        });

        return convertview;
    }

    static class ViewHolder {
        TextView txtCodigoOriginal;
        TextView txtMarca;
        TextView txtCodPurolator;
    }
}
