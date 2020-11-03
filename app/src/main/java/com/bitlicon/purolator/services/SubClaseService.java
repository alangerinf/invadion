package com.bitlicon.purolator.services;

import android.content.ContentValues;
import android.content.Context;

import com.bitlicon.purolator.contentprovider.ManagerContentProvider;
import com.bitlicon.purolator.dao.BaseDAO;
import com.bitlicon.purolator.parser.ClaseParser;
import com.bitlicon.purolator.parser.SubClaseParser;
import com.bitlicon.purolator.util.Constantes;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import static com.bitlicon.purolator.util.LogUtil.LOGE;
import static com.bitlicon.purolator.util.LogUtil.LOGI;

public class SubClaseService {
    Boolean resultado;
    Context context;
    public SubClaseService(Context context) {
        this.context = context;
    }
    public Boolean obtenerSubClases() {
        resultado = Boolean.FALSE;
        JSONObject jsonObject = new JSONObject();
        StringEntity entity = null;
        SyncHttpClient clientHttp;
        try {
            LOGI(getClass(), "obtenerSubClases");
            clientHttp = new SyncHttpClient();
            clientHttp.setTimeout(Constantes.TIMEOUT);
            entity = new StringEntity(jsonObject.toString());
            String url = Config.SERVICE_URL + Config.WS_SUBCLASE + Config.METODO_SUBCLASES;
            LOGI(getClass(), "URL -> " + url);
            clientHttp.post(context, url, entity, Constantes.APPLICATION_JSON, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray jsonArray) {
                    try {
                        SubClaseParser subClaseParser = new SubClaseParser(jsonArray);
                        ContentValues[] contentValues = subClaseParser.obtenerValues();
                        BaseDAO baseDAO = new BaseDAO(context);
                        baseDAO.bulkInsert(ManagerContentProvider.CONTENT_URI_SUBCLASE, contentValues);
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
}