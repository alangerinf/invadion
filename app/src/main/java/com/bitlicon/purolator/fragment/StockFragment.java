package com.bitlicon.purolator.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import androidx.fragment.app.Fragment;

import com.bitlicon.purolator.R;
import com.bitlicon.purolator.activity.BaseActivity;
import com.bitlicon.purolator.adapter.StockAdapter;
import com.bitlicon.purolator.dao.ClienteDAO;
import com.bitlicon.purolator.dao.ProductoDAO;
import com.bitlicon.purolator.entities.Cliente;
import com.bitlicon.purolator.entities.Producto;
import com.bitlicon.purolator.util.Constantes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class StockFragment extends Fragment implements View.OnClickListener {

    private BaseActivity base;
    private View rootView;
    private ListView lstProductos;
    protected StockAdapter stockAdapter;
    private ProductoDAO productoDAO;
    protected ArrayList<Producto> productos;
    protected Map<String, Integer> mapIndex;
    protected LinearLayout indexLayout;

    public static StockFragment nuevaInstancia(BaseActivity base) {
        StockFragment h = new StockFragment();
        base.setTitle(R.string.stock);
        base.setLayout(R.menu.stock_fragment_actions);
        base.invalidateOptionsMenu();
        h.base = base;
        return h;
    }

    public StockAdapter getStockAdapter()
    {
        return stockAdapter;
    }

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
        rootView = inflater.inflate(R.layout.fragment_stock, container, false);
        base.setStockFragment(this);
        lstProductos = (ListView) rootView.findViewById(R.id.lstProductos);
        indexLayout = (LinearLayout) rootView.findViewById(R.id.side_index);
        productoDAO = new ProductoDAO(getActivity());
        productos = productoDAO.listarProductos();
        stockAdapter = new StockAdapter(getActivity(), productos);

        lstProductos.setAdapter(stockAdapter);

        getIndexList(productos, "Nombre");

        return rootView;
    }

    public void getIndexList(ArrayList<Producto> productos, String Ordenamiento) {
        mapIndex = new LinkedHashMap<String, Integer>();
        indexLayout.removeAllViews();

        for (int i = 0; i < productos.size(); i++) {
            Producto producto = productos.get(i);
            String index = null;

            switch (Ordenamiento)
            {
                case "Nombre":
                    if (producto.Nombre != null)
                        index = producto.Nombre.substring(0, 1);
                    break;
                case "Clase":
                    if (producto.Clase != null)
                        index = producto.Clase.substring(0,1);
                    break;
                case "SubClase":
                    if (producto.SubClase != null)
                        index = producto.SubClase.substring(0,1);
                    break;
                default:
                    if (producto.Nombre != null)
                        index = producto.Nombre.substring(0, 1);
                    break;
            }


            if (mapIndex.get(index) == null)
                mapIndex.put(index, i);
        }
        displayIndex();
    }

    private void displayIndex() {

        TextView textView;
        List<String> indexList = new ArrayList<String>(mapIndex.keySet());
        for (String index : indexList) {
            textView = (TextView) this.getActivity().getLayoutInflater().inflate(
                    R.layout.side_index_item, null);
            textView.setText(index);
            textView.setOnClickListener(this);
            indexLayout.addView(textView);
        }
        indexLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v)
    {
        TextView selectedIndex = (TextView) v;
        Integer index = mapIndex.get(selectedIndex.getText());

        if (index != null)
        {
            lstProductos.setSelection(index);
        }
        else
        {
            lstProductos.setSelection(0);
        }
    }
}
