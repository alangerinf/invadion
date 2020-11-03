package com.bitlicon.purolator.services;

import android.content.Context;
import android.util.Log;

import com.bitlicon.purolator.entities.Vendedor;
import com.bitlicon.purolator.util.Constantes;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import org.json.JSONObject;

/**
 * Created by EduardoAndres on 08/05/2015.
 */
public class VendedorService {

    int estado = 0;
    Vendedor vendedor = null;

    public Vendedor iniciarSesion(final Context context, String user, String password) {
        JSONObject jsonObject = new JSONObject();
        StringEntity entity = null;
        SyncHttpClient clientHttp;
        vendedor = new Vendedor();
        try {
            clientHttp = new SyncHttpClient();
            clientHttp.setTimeout(Constantes.TIMEOUT);
            jsonObject.put("User", user);
            jsonObject.put("Password", password);
            entity = new StringEntity(jsonObject.toString());

            clientHttp.post(context, Config.SERVICE_URL + Config.WS_VENDEDOR + Config.METODO_INICIARSESION, entity, "application/json", new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                    try {
                        vendedor.Usuario = json.getString("Usuario");
                        vendedor.Nombre = json.getString("Nombre");
                        vendedor.Linea1 = json.getString("Linea1");
                        vendedor.Linea2 = json.getString("Linea2");
                        vendedor.Password = json.getString("Password");
                        vendedor.CuotaPurolator = json.getDouble("ValorCuotaLinea1");
                        vendedor.CuotaFiltech = json.getDouble("ValorCuotaLinea2");
                        vendedor.Resultado = Constantes.EXITO;
                    } catch (Exception ex) {
                        vendedor.Resultado = Constantes.ERROR_ON_SUCCESS;
                        vendedor.Mensaje = ex.getMessage();
                        Log.e("IniciarSesion", ex.getMessage(), ex);
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                    Log.e("onFailure -> ",new Gson().toJson(throwable));
                    if (responseString.equalsIgnoreCase(Constantes.NULL)) {
                        vendedor.Resultado = Constantes.CREDENCIALES_INVALIDAS;
                        vendedor.Mensaje = "Credenciales inválidas";
                    } else if (responseString.contains("404")) {
                        vendedor.Resultado = Constantes.ERROR_404;
                        vendedor.Mensaje = "Servicio no disponible";
                    }
                }
            });
        } catch (Exception ex) {
            vendedor.Resultado = Constantes.ERROR_ON_REQUEST;
            vendedor.Mensaje = ex.getMessage();
            Log.e("IniciarSesion", vendedor.Mensaje, ex);
        }

        return vendedor;
    }
}
