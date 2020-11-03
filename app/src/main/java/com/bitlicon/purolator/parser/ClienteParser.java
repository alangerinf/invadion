package com.bitlicon.purolator.parser;


import android.content.ContentValues;

import com.bitlicon.purolator.dao.ClienteDAO;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.bitlicon.purolator.util.LogUtil.LOGE;

/**
 * Created by dianewalls on 17/07/2015.
 */
public class ClienteParser extends BaseParser {

    /**
     * nombre de la clase para poner en el log
     */
    private static final String LOG_NAME = "ClienteParser";

    public ClienteParser(JSONArray jsonArray) {
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
            JSONObject jsonCliente;
            for (int i = 0; i < cantidad; i++) {
                jsonCliente = jsonArray.getJSONObject(i);
                ContentValues values = new ContentValues();
                values.put(ClienteDAO.CLIENTE_ID, jsonCliente.getString(ClienteDAO.CLIENTE_ID).length() == 0 ? null : jsonCliente.getString("ClienteID"));
                values.put(ClienteDAO.NOMBRE, jsonCliente.getString(ClienteDAO.NOMBRE));
                values.put(ClienteDAO.DIRECCION, jsonCliente.getString(ClienteDAO.DIRECCION));
                values.put(ClienteDAO.DESPACHO, jsonCliente.getString(ClienteDAO.DESPACHO));
                values.put(ClienteDAO.GIRO, jsonCliente.getString(ClienteDAO.GIRO));
                values.put(ClienteDAO.REPRESENTANTE_LEGAL, jsonCliente.getString(ClienteDAO.REPRESENTANTE_LEGAL));
                values.put(ClienteDAO.RUTA, jsonCliente.getString(ClienteDAO.RUTA));
                values.put(ClienteDAO.TELEFONO, jsonCliente.getString(ClienteDAO.TELEFONO));
                values.put(ClienteDAO.DNI, jsonCliente.getString(ClienteDAO.DNI));
                values.put(ClienteDAO.ENCARGADO, jsonCliente.getString(ClienteDAO.ENCARGADO));
                values.put(ClienteDAO.RUC, jsonCliente.getString(ClienteDAO.RUC));
                values.put(ClienteDAO.PUROLATOR_ID, jsonCliente.getString(ClienteDAO.PUROLATOR_ID));
                values.put(ClienteDAO.FILTECH_ID, jsonCliente.getString(ClienteDAO.FILTECH_ID));
                values.put(ClienteDAO.MONTO_LINEA_CREDITO, jsonCliente.getDouble(ClienteDAO.MONTO_LINEA_CREDITO));
                values.put(ClienteDAO.EMAIL, jsonCliente.getString(ClienteDAO.EMAIL));
                values.put(ClienteDAO.FECHA_CREACION, jsonCliente.getString(ClienteDAO.FECHA_CREACION));
                values.put(ClienteDAO.NUEVO, jsonCliente.getBoolean(ClienteDAO.NUEVO));
                contentValues[i] = values;
            }
        } catch (Exception e) {
            LOGE(LOG_NAME, e.getMessage(), e.getCause());
        }
        return contentValues;
    }

}
