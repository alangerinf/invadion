package com.bitlicon.purolator.parser;


import android.content.ContentValues;

import com.bitlicon.purolator.dao.DescuentoDAO;
import com.bitlicon.purolator.dao.ProductoDAO;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.bitlicon.purolator.util.LogUtil.LOGE;

/**
 * Created by dianewalls on 17/07/2015.
 */
public class DescuentoParser extends BaseParser {

    /**
     * nombre de la clase para poner en el log
     */
    private static final String LOG_NAME = "DescuentoParser";

    public DescuentoParser(JSONArray jsonArray) {
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
            JSONObject jsonDescuento;
            for (int i = 0; i < cantidad; i++) {
                jsonDescuento = jsonArray.getJSONObject(i);
                ContentValues values = new ContentValues();
                values.put(DescuentoDAO.DESCUENTO_ID, jsonDescuento.getString(DescuentoDAO.DESCUENTO_ID).length() == 0 ? null : jsonDescuento.getString(DescuentoDAO.DESCUENTO_ID));
                values.put(DescuentoDAO.DESCRIPCION, jsonDescuento.getString(DescuentoDAO.DESCRIPCION));

                contentValues[i] = values;
            }
        } catch (Exception e) {
            LOGE(LOG_NAME, e.getMessage(), e.getCause());
        }
        return contentValues;
    }

}
