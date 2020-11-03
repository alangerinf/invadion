package com.bitlicon.purolator.parser;

import android.content.ContentValues;

import com.bitlicon.purolator.dao.DetalleFacturaDAO;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.bitlicon.purolator.util.LogUtil.LOGE;

/**
 * Created by dianewalls on 17/07/2015.
 */
public class DetalleFacturaParser extends BaseParser {

    /**
     * nombre de la clase para poner en el log
     */
    private static final String LOG_NAME = "DetalleFacturaParser";

    public DetalleFacturaParser(JSONArray jsonArray) {
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
                values.put(DetalleFacturaDAO.ITEM_ID, jsonFactura.getString(DetalleFacturaDAO.ITEM_ID));
                values.put(DetalleFacturaDAO.PRECIO, jsonFactura.getDouble(DetalleFacturaDAO.PRECIO));
                values.put(DetalleFacturaDAO.CANTIDAD, jsonFactura.getDouble(DetalleFacturaDAO.CANTIDAD));
                values.put(DetalleFacturaDAO.NUMERO_SECUENCIA, jsonFactura.getString(DetalleFacturaDAO.NUMERO_SECUENCIA));
                values.put(DetalleFacturaDAO.NUMERO_FACTURA, jsonFactura.getString(DetalleFacturaDAO.NUMERO_FACTURA));
                values.put(DetalleFacturaDAO.NUMERO_PEDIDO, jsonFactura.getString(DetalleFacturaDAO.NUMERO_PEDIDO));
                values.put(DetalleFacturaDAO.DESCUENTO_ORDEN_COMPRA, jsonFactura.getDouble(DetalleFacturaDAO.DESCUENTO_ORDEN_COMPRA));
                values.put(DetalleFacturaDAO.DESCUENTO_PROMOCION, jsonFactura.getDouble(DetalleFacturaDAO.DESCUENTO_PROMOCION));
                values.put(DetalleFacturaDAO.DESCUENTO_DISTRIBUIDOR, jsonFactura.getDouble(DetalleFacturaDAO.DESCUENTO_DISTRIBUIDOR));
                values.put(DetalleFacturaDAO.DESCRIPCION, jsonFactura.getString(DetalleFacturaDAO.DESCRIPCION));
                values.put(DetalleFacturaDAO.PRECIO_DOLARES, jsonFactura.getDouble(DetalleFacturaDAO.PRECIO_DOLARES));
                contentValues[i] = values;
            }
        } catch (Exception e) {
            LOGE(LOG_NAME, e.getMessage(), e.getCause());
        }
        return contentValues;
    }

}
