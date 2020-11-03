package com.bitlicon.purolator.services;

import android.content.ContentValues;
import android.content.Context;

import com.bitlicon.purolator.contentprovider.ManagerContentProvider;
import com.bitlicon.purolator.dao.BaseDAO;
import com.bitlicon.purolator.entities.Cliente;
import com.bitlicon.purolator.parser.ClienteParser;
import com.bitlicon.purolator.util.Constantes;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import static com.bitlicon.purolator.util.LogUtil.LOGE;
import static com.bitlicon.purolator.util.LogUtil.LOGI;

public class ClienteService {
    Boolean resultado;
    Context context;
    public ClienteService(Context context) {
        this.context = context;
    }
    public Boolean obtenerClientes(String linea1, String linea2) {
        resultado = Boolean.FALSE;
        JSONObject jsonObject = new JSONObject();
        StringEntity entity = null;
        SyncHttpClient clientHttp;

        try
        {
            clientHttp = new SyncHttpClient();
            clientHttp.setTimeout(Constantes.TIMEOUT);
            jsonObject.put("PurolatorID", linea1);
            jsonObject.put("FiltechID", linea2);
            entity = new StringEntity(jsonObject.toString());
            String url = Config.SERVICE_URL + Config.WS_CLIENTE + Config.METODO_SEARCHCLIENTEPORVENDEDOR;

            clientHttp.post(context, url, entity, Constantes.APPLICATION_JSON, new JsonHttpResponseHandler()
            {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray jsonArray)
                {
                    try
                    {
                        ClienteParser clienteParser = new ClienteParser(jsonArray);
                        ContentValues[] contentValues = clienteParser.obtenerValues();
                        BaseDAO baseDAO = new BaseDAO(context);
                        baseDAO.bulkInsert(ManagerContentProvider.CONTENT_URI_CLIENTE, contentValues);
                        resultado = Boolean.TRUE;
                    }
                    catch (Exception ex)
                    {
                        LOGE(getClass(), ex.getMessage(), ex);
                    }
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable)
                {
                    super.onFailure(statusCode, headers, responseString, throwable);
                }
            });
        }
        catch (Exception ex)
        {
            LOGE(getClass(), ex.getMessage(), ex);
        }
        return resultado;
    }

    int ClienteID = 0;
    public int enviarCliente(Context context, Cliente cliente) {

        ClienteID = 0;

        JSONObject jsonCliente = new JSONObject();
        StringEntity seCliente = null;
        SyncHttpClient clientHttp;

        try {
            clientHttp = new SyncHttpClient();

            clientHttp.setTimeout(70 * 1000);
            jsonCliente.put("Nombre", cliente.Nombre);
            jsonCliente.put("DireccionFiscal", getDireccion(cliente));
            jsonCliente.put("DireccionDespacho", cliente.Despacho);
            jsonCliente.put("RUC", cliente.RUC);
            jsonCliente.put("DNI", cliente.DNI);
            jsonCliente.put("Email", cliente.Email);
            jsonCliente.put("Telefono", cliente.Telefono);
            jsonCliente.put("GiroNegocio", cliente.Giro);
            jsonCliente.put("RepresentanteLegal", cliente.RepresentanteLegal);
            jsonCliente.put("Contacto", cliente.Encargado);
            jsonCliente.put("Distrito", getDistrito(cliente));
            jsonCliente.put("PurolatorID", cliente.PurolatorID);
            jsonCliente.put("FiltechID", cliente.FiltechID);
            seCliente = new StringEntity(jsonCliente.toString());

            if(cliente.ClienteID == null) {
                clientHttp.post(context, Config.SERVICE_URL + Config.WS_CLIENTE + Config.METODO_REGISTRAR_CLIENTE, seCliente, "application/json", new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            ClienteID = response.getInt("result");
                        } catch (Exception ex) {
                            LOGE(getClass(), ex.getMessage(), ex);
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                    }
                });
            }

        } catch (Exception ex) {
            LOGE(getClass(), ex.getMessage(), ex);
        }
        return ClienteID;

    }

    public String getDireccion(Cliente cliente) {
        int index = cliente.Direccion.indexOf("/");
        if (index > 0) {
            return cliente.Direccion.substring(0, index - 1);
        }
        return cliente.Direccion;
    }


    public String getDistrito(Cliente cliente) {
        int index = cliente.Direccion.indexOf("/");
        if (index > 0) {
            return cliente.Direccion.substring(index + 1);
        }
        return Constantes.EMPTY;
    }


}