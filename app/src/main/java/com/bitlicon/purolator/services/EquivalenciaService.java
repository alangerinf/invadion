package com.bitlicon.purolator.services;

import android.content.ContentValues;
import android.content.Context;

import com.bitlicon.purolator.contentprovider.ManagerContentProvider;
import com.bitlicon.purolator.dao.BaseDAO;
import com.bitlicon.purolator.entities.Equivalencia;
import com.bitlicon.purolator.parser.EquivalenciaParser;
import com.bitlicon.purolator.parser.ProductoParser;
import com.bitlicon.purolator.util.Constantes;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.bitlicon.purolator.util.LogUtil.LOGE;
import static com.bitlicon.purolator.util.LogUtil.LOGI;

/**
 * Created by EduardoAndrés on 16/12/2015.
 */
public class EquivalenciaService {
    Boolean resultado;
    Context context;
    public EquivalenciaService(Context context) {
        this.context = context;
    }

    public Boolean obtenerEquivalencias() {
        resultado = Boolean.FALSE;
        JSONObject jsonObject = new JSONObject();
        StringEntity entity = null;
        SyncHttpClient clientHttp;
        try {
            LOGI(getClass(), "obtenerEquivalencias");
            clientHttp = new SyncHttpClient();
            clientHttp.setTimeout(Constantes.TIMEOUT);
            entity = new StringEntity(jsonObject.toString());
            String url = Config.SERVICE_URL + Config.WS_EQUIVALENCIA + Config.METODO_EQUIVALENCIAS;
            LOGI(getClass(), "URL -> " + url);
            clientHttp.post(context, url, entity, Constantes.APPLICATION_JSON, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray jsonArray) {
                    try {
                        EquivalenciaParser equivalenciaParser = new EquivalenciaParser(jsonArray);
                        ContentValues[] contentValues = equivalenciaParser.obtenerValues();
                        BaseDAO baseDAO = new BaseDAO(context);
                        baseDAO.bulkInsert(ManagerContentProvider.CONTENT_URI_EQUIVALENCIA, contentValues);
                        resultado = Boolean.TRUE;
                    } catch (Exception ex) {
                        LOGE(getClass(), ex.getMessage(), ex);
                    }
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                }
            });
        } catch (Exception ex) {
            LOGE(getClass(), ex.getMessage(), ex);
        }
        return resultado;
    }

    public Boolean obtenerEquivalenciasPorNombre(String Nombre)
    {
        resultado = Boolean.FALSE;
        JSONObject jsonObject = new JSONObject();
        StringEntity entity = null;
        SyncHttpClient clientHttp;
        try
        {
            LOGI(getClass(), "obtenerEquivalenciasPorNombre");
            clientHttp = new SyncHttpClient();
            clientHttp.setTimeout(Constantes.TIMEOUT);

            jsonObject.put("Nombre", Nombre);

            entity = new StringEntity(jsonObject.toString());

            String url = Config.SERVICE_URL + Config.WS_EQUIVALENCIA + Config.METODO_OBTENEREQUIVALENCIAS;
            LOGI(getClass(), "URL -> " + url);
            clientHttp.post(context, url, entity, Constantes.APPLICATION_JSON, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray jsonArray)
                {
                    try
                    {
                        EquivalenciaParser equivalenciaParser = new EquivalenciaParser(jsonArray);
                        ContentValues[] contentValues = equivalenciaParser.obtenerValues();
                        BaseDAO baseDAO = new BaseDAO(context);
                        baseDAO.bulkInsert(ManagerContentProvider.CONTENT_URI_EQUIVALENCIA, contentValues);
                        resultado = Boolean.TRUE;
                    }
                    catch (Exception ex)
                    {
                        LOGE(getClass(), ex.getMessage(), ex);
                    }
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                }
            });
        }
        catch(Exception ex)
        {
            LOGE(getClass(), ex.getMessage(), ex);
        }

        return resultado;
    }
}