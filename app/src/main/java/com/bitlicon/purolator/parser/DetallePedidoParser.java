package com.bitlicon.purolator.parser;

import android.content.ContentValues;

import com.bitlicon.purolator.dao.DetallePedidoDAO;
import com.bitlicon.purolator.dao.PedidoDAO;
import com.bitlicon.purolator.entities.DetallePedido;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.bitlicon.purolator.util.LogUtil.LOGE;

/**
 * Created by dianewalls on 17/07/2015.
 */
public class DetallePedidoParser extends BaseParser {
    /**
     * nombre de la clase para poner en el log
     */
    private static final String LOG_NAME = "DetallePedidoParser";

    public DetallePedidoParser(JSONArray jsonArray) {
        super(jsonArray);
    }

    /**
     * Metodo que convierte las entidades parseadas en contentValues
     *
     * @return Retorna un arreglo de entidades en el ContentValues
     */
    public ContentValues[] obtenerValues() {
        int cantidad = jsonArray.length();
        ContentValues[] contentValues = new ContentValues[cantidad];
        try {
            for (int i = 0; i < cantidad; i++) {
                JSONObject jsonDetallePedido = jsonArray.getJSONObject(i);
                ContentValues values = new ContentValues();

                values.put(DetallePedidoDAO.PRODUCTO_ID, jsonDetallePedido.getString(DetallePedidoDAO.PRODUCTO_ID));
                values.put(DetallePedidoDAO.NUMERO_PEDIDO, jsonDetallePedido.getString(PedidoDAO.NUMERO_PEDIDO));
                values.put(DetallePedidoDAO.CANTIDAD, jsonDetallePedido.getInt(DetallePedidoDAO.CANTIDAD));
                values.put(DetallePedidoDAO.PRECIO, jsonDetallePedido.getDouble(DetallePedidoDAO.PRECIO));
                values.put(DetallePedidoDAO.DESCUENTO1, jsonDetallePedido.getDouble(DetallePedidoDAO.DESCUENTO1));
                values.put(DetallePedidoDAO.DESCUENTO2, jsonDetallePedido.getDouble(DetallePedidoDAO.DESCUENTO2));
                values.put(DetallePedidoDAO.DESCUENTO11, jsonDetallePedido.getDouble(DetallePedidoDAO.DESCUENTO11));
                values.put(DetallePedidoDAO.DESCUENTO12, jsonDetallePedido.getDouble(DetallePedidoDAO.DESCUENTO12));
                contentValues[i] = values;
            }
        } catch (Exception e) {
            LOGE(LOG_NAME, e.getMessage(), e.getCause());
        }
        return contentValues;
    }

}
