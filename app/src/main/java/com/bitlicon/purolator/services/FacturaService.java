package com.bitlicon.purolator.services;

import android.content.ContentValues;
import android.content.Context;

import com.bitlicon.purolator.contentprovider.ManagerContentProvider;
import com.bitlicon.purolator.dao.BaseDAO;
import com.bitlicon.purolator.parser.FacturaParser;
import com.bitlicon.purolator.util.Constantes;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import static com.bitlicon.purolator.util.LogUtil.LOGE;
import static com.bitlicon.purolator.util.LogUtil.LOGI;


/**
 * Created by EduardoAndres on 08/05/2015.
 */
public class FacturaService {

    Boolean resultado;
    Context context;

    public FacturaService(Context context) {
        this.context = context;
    }

    public Boolean facturaListar(String linea1, String linea2) {
        resultado = Boolean.FALSE;

        JSONObject jsonObject = new JSONObject();
        StringEntity entity = null;
        SyncHttpClient clientHttp;

        try {

            LOGI(getClass(), "facturaListar");

            clientHttp = new SyncHttpClient();
            clientHttp.setTimeout(Constantes.TIMEOUT);
            jsonObject.put("PurolatorID", linea1);
            jsonObject.put("FiltechID", linea2);
            entity = new StringEntity(jsonObject.toString());

            String url = Config.SERVICE_URL + Config.WS_FACTURA + Config.METODO_FACTURAPORVENDEDOR;
            LOGI(getClass(), "URL -> " + url);


            clientHttp.post(context, url, entity, Constantes.APPLICATION_JSON, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray jsonArray) {
                    try {
                        FacturaParser facturaParser = new FacturaParser(jsonArray);
                        ContentValues[] contentValues = facturaParser.obtenerValues();
                        BaseDAO baseDAO = new BaseDAO(context);
                        baseDAO.bulkInsert(ManagerContentProvider.CONTENT_URI_FACTURA, contentValues);
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
