package com.bitlicon.purolator.parser;

import android.content.ContentValues;

import com.bitlicon.purolator.dao.ClaseDAO;
import com.bitlicon.purolator.dao.SubClaseDAO;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.bitlicon.purolator.util.LogUtil.LOGE;

/**
 * Created by EduardoAndrés on 11/12/2015.
 */
public class ClaseParser extends BaseParser {

    private static final String LOG_NAME = "ClaseParser";

    public ClaseParser(JSONArray jsonArray) {
        super(jsonArray);
    }

    public ContentValues[] obtenerValues() {
        int cantidad = jsonArray.length();
        ContentValues[] contentValues = new ContentValues[cantidad];
        try {
            JSONObject jsonClases;
            for (int i = 0; i < cantidad; i++) {
                jsonClases = jsonArray.getJSONObject(i);
                ContentValues values = new ContentValues();
                values.put(ClaseDAO.CODIGO, jsonClases.getString(ClaseDAO.CODIGO));
                values.put(ClaseDAO.NOMBRE, jsonClases.getString(ClaseDAO.NOMBRE));
                contentValues[i] = values;
            }
        } catch (Exception e) {
            LOGE(LOG_NAME, e.getMessage(), e.getCause());
        }
        return contentValues;
    }
}
