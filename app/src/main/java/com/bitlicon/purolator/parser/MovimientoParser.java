package com.bitlicon.purolator.parser;

import android.content.ContentValues;

import com.bitlicon.purolator.dao.MovimientoDAO;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.bitlicon.purolator.util.LogUtil.LOGE;

/**
 * Created by dianewalls on 17/07/2015.
 */
public class MovimientoParser extends BaseParser {
    /**
     * nombre de la clase para poner en el log
     */
    private static final String LOG_NAME = "MovimientoParser";

    public MovimientoParser(JSONArray jsonArray) {
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
                JSONObject jsonMovimiento = jsonArray.getJSONObject(i);
                ContentValues values = new ContentValues();

                values.put(MovimientoDAO.SALDO, jsonMovimiento.getDouble(MovimientoDAO.SALDO));
                values.put(MovimientoDAO.NUM_DOCUMENTO, jsonMovimiento.getString(MovimientoDAO.MOVIMIENTO_ID));
                values.put(MovimientoDAO.TIPO_DOCUMENTO, jsonMovimiento.getString(MovimientoDAO.TIPO_DOCUMENTO));
                values.put(MovimientoDAO.FECHA_VENCIMIENTO, jsonMovimiento.getString(MovimientoDAO.FECHA_VENCIMIENTO).substring(0, 10));
                values.put(MovimientoDAO.CLIENTE_ID, jsonMovimiento.getString(MovimientoDAO.CLIENTE_ID));
                values.put(MovimientoDAO.VENDEDOR_ID, jsonMovimiento.getString(MovimientoDAO.VENDEDOR_ID));
                values.put(MovimientoDAO.MOVIMIENTO_ID, jsonMovimiento.getString(MovimientoDAO.MOVIMIENTO_ID));
                values.put(MovimientoDAO.MONTO, jsonMovimiento.getDouble(MovimientoDAO.MONTO));
                values.put(MovimientoDAO.PAGADA, jsonMovimiento.getString(MovimientoDAO.PAGADA));
                values.put(MovimientoDAO.AGENCIA, jsonMovimiento.getString(MovimientoDAO.AGENCIA));
                values.put(MovimientoDAO.LETBAN, jsonMovimiento.getString(MovimientoDAO.LETBAN));
                values.put(MovimientoDAO.FECHA_DOCUMENTO, jsonMovimiento.getString(MovimientoDAO.FECHA_DOCUMENTO).substring(0, 10));

                contentValues[i] = values;
            }
        } catch (Exception e) {
            LOGE(LOG_NAME, e.getMessage(), e.getCause());
        }
        return contentValues;
    }

}
