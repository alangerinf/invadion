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
import com.bitlicon.purolator.entities.Producto;
import com.bitlicon.purolator.util.Util;

import java.util.ArrayList;


public class StockPedidoAdapter extends ArrayAdapter<Producto> {



    public StockPedidoAdapter(Context context, ArrayList<Producto> productos) {
        super(context, 0, productos);
        // TODO Auto-generated constructor stub
    }

    @Override
    public View getView(final int position, View convertview, ViewGroup parent) {
        final ViewHolder viewHolder;
        final Producto producto = getItem(position);
        viewHolder = new ViewHolder();

        if(producto.ProductoID!=null && producto.ProductoID.length()>0 && producto.ProductoID.substring(2,4).equals("06"))
        {
            convertview = LayoutInflater.from(getContext()).inflate(R.layout.item_stock_kit, parent, false);

            viewHolder.txtCodigo = (TextView) convertview.findViewById(R.id.txtCodigo);
            viewHolder.txtClase = (TextView) convertview.findViewById(R.id.txtClase);
            viewHolder.txtSubClase = (TextView) convertview.findViewById(R.id.txtSubClase);
            viewHolder.txtPrecio = (TextView) convertview.findViewById(R.id.txtPrecio);
            viewHolder.txtStock = (TextView) convertview.findViewById(R.id.txtStock);
            viewHolder.txtKit1 = (TextView) convertview.findViewById(R.id.txtKit1);
            viewHolder.txtKit2 = (TextView) convertview.findViewById(R.id.txtKit2);
            viewHolder.txtKit3 = (TextView) convertview.findViewById(R.id.txtKit3);
            viewHolder.txtKit4 = (TextView) convertview.findViewById(R.id.txtKit4);
            viewHolder.txtKit5 = (TextView) convertview.findViewById(R.id.txtKit5);
            viewHolder.txtKit6 = (TextView) convertview.findViewById(R.id.txtKit6);

            if(producto.Kit1.trim().length()>0)
            {
                viewHolder.txtKit1.setText(producto.Kit1);
            }
            else
            {
                viewHolder.txtKit1.setVisibility(View.GONE);
            }
            if(producto.Kit2.trim().length()>0)
            {
                viewHolder.txtKit2.setText(producto.Kit2);
            }
            else
            {
                viewHolder.txtKit2.setVisibility(View.GONE);
            }
            if(producto.Kit3.trim().length()>0)
            {
                viewHolder.txtKit3.setText(producto.Kit3);
            }else
            {
                viewHolder.txtKit3.setVisibility(View.GONE);
            }
            if(producto.Kit4.trim().length()>0)
            {
                viewHolder.txtKit4.setText(producto.Kit4);
            }
            else
            {
                viewHolder.txtKit4.setVisibility(View.GONE);
            }
            if(producto.Kit5.trim().length()>0)
            {
                viewHolder.txtKit5.setText(producto.Kit5);
            }
            else
            {
                viewHolder.txtKit5.setVisibility(View.GONE);
            }
            if(producto.Kit6.trim().length()>0)
            {
                viewHolder.txtKit6.setText(producto.Kit6);
            }
            else
            {
                viewHolder.txtKit6.setVisibility(View.GONE);
            }

        }
        else
        {
            convertview = LayoutInflater.from(getContext()).inflate(R.layout.item_stock, parent, false);

            viewHolder.txtCodigo = (TextView) convertview.findViewById(R.id.txtCodigo);
            viewHolder.txtClase = (TextView) convertview.findViewById(R.id.txtClase);
            viewHolder.txtSubClase = (TextView) convertview.findViewById(R.id.txtSubClase);
            viewHolder.txtPrecio = (TextView) convertview.findViewById(R.id.txtPrecio);
            viewHolder.txtStock = (TextView) convertview.findViewById(R.id.txtStock);


        }

        viewHolder.txtCodigo.setText(producto.Nombre);
        String clase = "";
        if(producto.Categoria.trim().length()>0)
        {
         clase = "(" + producto.Categoria + ") ";
        }

        clase += producto.Clase;

        viewHolder.txtClase.setText(clase);
        viewHolder.txtSubClase.setText(producto.SubClase);
        viewHolder.txtPrecio.setText("PL: " + Util.formatoDineroSoles(producto.PrecioDolares));
        viewHolder.txtStock.setText("Cant: " + String.format("%d", (long) producto.Stock));

        convertview.setTag(viewHolder);



        return convertview;
    }

    static class ViewHolder
    {
        TextView txtCodigo;
        TextView txtPrecio;
        TextView txtClase;
        TextView txtSubClase;
        TextView txtStock;
        TextView txtKit1;
        TextView txtKit2;
        TextView txtKit3;
        TextView txtKit4;
        TextView txtKit5;
        TextView txtKit6;
    }
}
