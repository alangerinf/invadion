package com.bitlicon.purolator.parser;

import android.content.ContentValues;

import com.bitlicon.purolator.dao.MovimientoDAO;
import com.bitlicon.purolator.dao.PedidoDAO;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.bitlicon.purolator.util.LogUtil.LOGE;

/**
 * Created by dianewalls on 17/07/2015.
 */
public class PedidoParser extends BaseParser {
    /**
     * nombre de la clase para poner en el log
     */
    private static final String LOG_NAME = "PedidoParser";

    public PedidoParser(JSONArray jsonArray) {
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
                JSONObject jsonPedido= jsonArray.getJSONObject(i);
                ContentValues values = new ContentValues();
                values.put(PedidoDAO.NUMERO_PEDIDO, jsonPedido.getString(PedidoDAO.NUMERO_PEDIDO));
                values.put(PedidoDAO.VENDEDOR_ID, jsonPedido.getString(PedidoDAO.VENDEDOR_ID));
                values.put(PedidoDAO.CLIENTE_ID, jsonPedido.getString(PedidoDAO.CLIENTE_ID));
                values.put(PedidoDAO.TOTAL_IMPORTE, jsonPedido.getDouble(PedidoDAO.TOTAL_IMPORTE));
                values.put(PedidoDAO.TOTAL_DESCUENTO, jsonPedido.getDouble(PedidoDAO.TOTAL_DESCUENTO));
                values.put(PedidoDAO.TOTAL_IMPUESTOS, jsonPedido.getDouble(PedidoDAO.TOTAL_IMPUESTOS));
                values.put(PedidoDAO.TERMINO_VENTA, jsonPedido.getString(PedidoDAO.TERMINO_VENTA));
                values.put(PedidoDAO.FECHA_CREACION_PEDIDO, jsonPedido.getString(PedidoDAO.FECHA_CREACION_PEDIDO));
                values.put(PedidoDAO.OBSERVACION, jsonPedido.getString(PedidoDAO.OBSERVACION));
                values.put(PedidoDAO.DESCUENTO_1, jsonPedido.getBoolean(PedidoDAO.DESCUENTO_1));
                values.put(PedidoDAO.DESCUENTO_2, jsonPedido.getBoolean(PedidoDAO.DESCUENTO_2));
                values.put(PedidoDAO.DESCUENTO_3, jsonPedido.getBoolean(PedidoDAO.DESCUENTO_3));
                values.put(PedidoDAO.DESCUENTO_4, jsonPedido.getBoolean(PedidoDAO.DESCUENTO_4));
                values.put(PedidoDAO.DESCUENTO_5, jsonPedido.getBoolean(PedidoDAO.DESCUENTO_5));
                values.put(PedidoDAO.DESCUENTO_1_VALOR, jsonPedido.getDouble(PedidoDAO.DESCUENTO_1_VALOR));
                values.put(PedidoDAO.DESCUENTO_2_VALOR, jsonPedido.getDouble(PedidoDAO.DESCUENTO_2_VALOR));
                values.put(PedidoDAO.DESCUENTO_3_VALOR, jsonPedido.getDouble(PedidoDAO.DESCUENTO_3_VALOR));
                values.put(PedidoDAO.ENVIADO, true);
                contentValues[i] = values;
            }
        } catch (Exception e) {
            LOGE(LOG_NAME, e.getMessage(), e.getCause());
        }
        return contentValues;
    }

}
