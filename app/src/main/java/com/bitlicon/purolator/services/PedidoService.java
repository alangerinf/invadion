package com.bitlicon.purolator.services;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.bitlicon.purolator.contentprovider.ManagerContentProvider;
import com.bitlicon.purolator.dao.BaseDAO;
import com.bitlicon.purolator.parser.DetallePedidoParser;
import com.bitlicon.purolator.parser.LetraPedidoParser;
import com.bitlicon.purolator.parser.MovimientoParser;
import com.bitlicon.purolator.parser.PedidoParser;
import com.bitlicon.purolator.util.Constantes;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by dianewalls on 22/05/2015.
 */
public class PedidoService {
    Boolean resultado;
    Context context;
    public PedidoService(Context context) {
        this.context = context;
    }

    public Boolean pedidoListar(String linea1, String linea2) {
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
            String url = Config.SERVICE_URL + Config.WS_PEDIDO + Config.METODO_PEDIDOPORVENDEDOR;
            clientHttp.post(context, url, entity, Constantes.APPLICATION_JSON, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray jsonArray) {
                    try {
                        PedidoParser pedidoParser = new PedidoParser(jsonArray);
                        ContentValues[] contentValues = pedidoParser.obtenerValues();
                        BaseDAO baseDAO = new BaseDAO(context);
                        baseDAO.bulkInsert(ManagerContentProvider.CONTENT_URI_PEDIDO, contentValues);
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

    public Boolean detallePedidoListar(String linea1, String linea2) {
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
            String url = Config.SERVICE_URL + Config.WS_PEDIDO + Config.METODO_DETALLEPEDIDOPORVENDEDOR;
            clientHttp.post(context, url, entity, Constantes.APPLICATION_JSON, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray jsonArray) {
                    try {
                        DetallePedidoParser detallePedidoParser = new DetallePedidoParser(jsonArray);
                        ContentValues[] contentValues = detallePedidoParser.obtenerValues();
                        BaseDAO baseDAO = new BaseDAO(context);
                        baseDAO.bulkInsert(ManagerContentProvider.CONTENT_URI_DETALLE_PEDIDO, contentValues);
                        resultado = Boolean.TRUE;
                    } catch (Exception ex) {
                        Log.i("detallePedidoListar", ex.getMessage());
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

    public Boolean letraPedidoListar(String linea1, String linea2) {
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
            String url = Config.SERVICE_URL + Config.WS_PEDIDO + Config.METODO_LETRAPEDIDOPORVENDEDOR;
            clientHttp.post(context, url, entity, Constantes.APPLICATION_JSON, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray jsonArray) {
                    try {
                        LetraPedidoParser letraPedidoParser = new LetraPedidoParser(jsonArray);
                        ContentValues[] contentValues = letraPedidoParser.obtenerValues();
                        BaseDAO baseDAO = new BaseDAO(context);
                        baseDAO.bulkInsert(ManagerContentProvider.CONTENT_URI_LETRA_PEDIDO, contentValues);
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
