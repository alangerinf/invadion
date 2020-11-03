package com.bitlicon.purolator.parser;

import android.content.ContentValues;

import com.bitlicon.purolator.dao.SubClaseDAO;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.bitlicon.purolator.util.LogUtil.LOGE;

/**
 * Created by EduardoAndrés on 11/12/2015.
 */
public class SubClaseParser extends BaseParser {

    private static final String LOG_NAME = "SubClaseParser";

    public SubClaseParser(JSONArray jsonArray) {
        super(jsonArray);
    }

    public ContentValues[] obtenerValues() {
        int cantidad = jsonArray.length();
        ContentValues[] contentValues = new ContentValues[cantidad];
        try {
            JSONObject jsonSubClases;
            for (int i = 0; i < cantidad; i++) {
                jsonSubClases = jsonArray.getJSONObject(i);
                ContentValues values = new ContentValues();
                values.put(SubClaseDAO.CODIGO, jsonSubClases.getString(SubClaseDAO.CODIGO));
                values.put(SubClaseDAO.NOMBRE, jsonSubClases.getString(SubClaseDAO.NOMBRE));
                contentValues[i] = values;
            }
        } catch (Exception e) {
            LOGE(LOG_NAME, e.getMessage(), e.getCause());
        }
        return contentValues;
    }

}
