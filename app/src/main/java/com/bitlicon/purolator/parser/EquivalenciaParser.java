package com.bitlicon.purolator.parser;

import android.content.ContentValues;

import com.bitlicon.purolator.dao.EquivalenciaDAO;
import com.bitlicon.purolator.dao.ProductoDAO;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.bitlicon.purolator.util.LogUtil.LOGE;

/**
 * Created by EduardoAndrés on 16/12/2015.
 */
public class EquivalenciaParser extends BaseParser
{
    private static final String LOG_NAME = "EquivalenciaParser";

    public EquivalenciaParser(JSONArray jsonArray)
    {
        super(jsonArray);
    }

    public ContentValues[] obtenerValues()
    {
        int cantidad = jsonArray.length();
        ContentValues[] contentValues = new ContentValues[cantidad];
        try {
            JSONObject jsonEquivalencia;
            for (int i = 0; i < cantidad; i++) {
                jsonEquivalencia = jsonArray.getJSONObject(i);
                ContentValues values = new ContentValues();
                values.put(EquivalenciaDAO.CODIGOORIGINAL, jsonEquivalencia.getString(EquivalenciaDAO.CODIGOORIGINAL));
                values.put(EquivalenciaDAO.MARCAORIGINAL, jsonEquivalencia.getString(EquivalenciaDAO.MARCAORIGINAL));
                values.put(EquivalenciaDAO.CODIGOPUROLATOR, jsonEquivalencia.getString(EquivalenciaDAO.CODIGOPUROLATOR));
                values.put(EquivalenciaDAO.PROCEDENCIA, jsonEquivalencia.getString(EquivalenciaDAO.PROCEDENCIA));
                contentValues[i] = values;
            }
        } catch (Exception e) {
            LOGE(LOG_NAME, e.getMessage(), e.getCause());
        }
        return contentValues;
    }
}
