package com.bitlicon.purolator.services;

import android.content.ContentValues;
import android.content.Context;
import com.bitlicon.purolator.contentprovider.ManagerContentProvider;
import com.bitlicon.purolator.dao.BaseDAO;
import com.bitlicon.purolator.parser.MovimientoParser;
import com.bitlicon.purolator.util.Constantes;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONObject;

public class MovimientoService {
    Boolean resultado;
    Context context;
    public MovimientoService(Context context) {
        this.context = context;
    }
    public Boolean movimientoListar(String linea1, String linea2) {
        resultado = Boolean.FALSE;
        JSONObject jsonObject = new JSONObject();
        StringEntity entity = null;
        SyncHttpClient clientHttp;
        try {
            clientHttp = new SyncHttpClient();
            clientHttp.setTimeout(Constantes.TIMEOUT);
            jsonObject.put("PurolatorID", linea1);
            jsonObject.put("FiltechID", linea2);
            entity = new StringEntity(jsonObject.toString());
            String url = Config.SERVICE_URL + Config.WS_MOVIMIENTO + Config.METODO_MOVIMIENTOPORVENDEDOR;
            clientHttp.post(context, url, entity, Constantes.APPLICATION_JSON, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray jsonArray) {
                    try {
                        MovimientoParser movimientoParser = new MovimientoParser(jsonArray);
                        ContentValues[] contentValues = movimientoParser.obtenerValues();
                        BaseDAO baseDAO = new BaseDAO(context);
                        baseDAO.bulkInsert(ManagerContentProvider.CONTENT_URI_MOVIMIENTO, contentValues);
                        resultado = Boolean.TRUE;
                    } catch (Exception ex) {

                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                }
            });
        } catch (Exception ex) {

        }
        return resultado;
    }


}
