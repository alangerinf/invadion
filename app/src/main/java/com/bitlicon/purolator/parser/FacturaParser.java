package com.bitlicon.purolator.parser;

import android.content.ContentValues;

import com.bitlicon.purolator.dao.FacturaDAO;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.bitlicon.purolator.util.LogUtil.LOGE;

/**
 * Created by dianewalls on 17/07/2015.
 */
public class FacturaParser extends BaseParser {
    /**
     * nombre de la clase para poner en el log
     */
    private static final String LOG_NAME = "FacturaParser";

    public FacturaParser(JSONArray jsonArray) {
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
                JSONObject jsonFactura = jsonArray.getJSONObject(i);
                ContentValues values = new ContentValues();
                values.put(FacturaDAO.CLIENTE_ID, jsonFactura.getString(FacturaDAO.CLIENTE_ID));
                values.put(FacturaDAO.TOTAL_IMPORTE, jsonFactura.getDouble(FacturaDAO.TOTAL_IMPORTE));
                values.put(FacturaDAO.TOTAL_IMPUESTOS, jsonFactura.getDouble(FacturaDAO.TOTAL_IMPUESTOS));
                values.put(FacturaDAO.VENDEDOR_ID, jsonFactura.getString(FacturaDAO.VENDEDOR_ID));
                values.put(FacturaDAO.NUMERO_FACTURA, jsonFactura.getString(FacturaDAO.NUMERO_FACTURA));
                values.put(FacturaDAO.FECHA_FACTURA, jsonFactura.getString(FacturaDAO.FECHA_FACTURA).substring(0, 10));
                values.put(FacturaDAO.DESCUENTO_1, jsonFactura.getDouble(FacturaDAO.DESCUENTO_1));
                values.put(FacturaDAO.DESCUENTO_2, jsonFactura.getDouble(FacturaDAO.DESCUENTO_2));
                values.put(FacturaDAO.DESCUENTO_3, jsonFactura.getDouble(FacturaDAO.DESCUENTO_3));
                values.put(FacturaDAO.DESCUENTO_ITEM, jsonFactura.getDouble(FacturaDAO.DESCUENTO_ITEM));
                values.put(FacturaDAO.TERMINO_VENTA, jsonFactura.getString(FacturaDAO.TERMINO_VENTA));
                values.put(FacturaDAO.TOTAL_ORDEN, jsonFactura.getDouble(FacturaDAO.TOTAL_ORDEN));
                values.put(FacturaDAO.TOTAL_DESCUENTO, jsonFactura.getDouble(FacturaDAO.TOTAL_DESCUENTO));
                contentValues[i] = values;
            }
        } catch (Exception e) {
            LOGE(LOG_NAME, e.getMessage(), e.getCause());
        }
        return contentValues;
    }

}
