package com.bitlicon.purolator.parser;

import android.content.ContentValues;

import com.bitlicon.purolator.dao.DetallePedidoDAO;
import com.bitlicon.purolator.dao.LetraPedidoDAO;
import com.bitlicon.purolator.dao.PedidoDAO;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.bitlicon.purolator.util.LogUtil.LOGE;

/**
 * Created by dianewalls on 17/07/2015.
 */
public class LetraPedidoParser extends BaseParser {
    /**
     * nombre de la clase para poner en el log
     */
    private static final String LOG_NAME = "LetraPedidoParser";

    public LetraPedidoParser(JSONArray jsonArray) {
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
                JSONObject jsonLetraPedido = jsonArray.getJSONObject(i);
                ContentValues values = new ContentValues();

                values.put(LetraPedidoDAO.DIA, jsonLetraPedido.getInt(LetraPedidoDAO.DIA));
                values.put(LetraPedidoDAO.NUMERO_PEDIDO, jsonLetraPedido.getString(LetraPedidoDAO.NUMERO_PEDIDO));
                values.put(LetraPedidoDAO.NUMERO, jsonLetraPedido.getInt(LetraPedidoDAO.NUMERO));
                values.put(LetraPedidoDAO.PICKER, jsonLetraPedido.getBoolean(LetraPedidoDAO.PICKER));
                values.put(LetraPedidoDAO.FECHA, jsonLetraPedido.getString(LetraPedidoDAO.FECHA));
                contentValues[i] = values;
            }
        } catch (Exception e) {
            LOGE(LOG_NAME, e.getMessage(), e.getCause());
        }
        return contentValues;
    }

}
