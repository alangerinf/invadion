package com.bitlicon.purolator.parser;


import android.content.ContentValues;

import com.bitlicon.purolator.dao.ClienteDAO;
import com.bitlicon.purolator.dao.ProductoDAO;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.bitlicon.purolator.util.LogUtil.LOGE;

/**
 * Created by dianewalls on 17/07/2015.
 */
public class ProductoParser extends BaseParser {

    /**
     * nombre de la clase para poner en el log
     */
    private static final String LOG_NAME = "ProductoParser";

    public ProductoParser(JSONArray jsonArray) {
        super(jsonArray);
    }

    /**
     * Metodo que convierte las entidades parseadas en contentValues
     *
     * @return Retorna un arreglo de entidades en el ContentValues
     */
    public ContentValues[] obtenerValues() {
        int cantidad = jsonArray.length();
        ContentValues[] contentValues = new ContentValues[cantidad];
        try {
            JSONObject jsonProducto;
            for (int i = 0; i < cantidad; i++) {
                jsonProducto = jsonArray.getJSONObject(i);
                ContentValues values = new ContentValues();
                values.put(ProductoDAO.PRODUCTO_ID, jsonProducto.getString(ProductoDAO.PRODUCTO_ID).length() == 0 ? null : jsonProducto.getString(ProductoDAO.PRODUCTO_ID));
                values.put(ProductoDAO.NOMBRE, jsonProducto.getString(ProductoDAO.NOMBRE));
                values.put(ProductoDAO.DESCUENTO1, jsonProducto.getString(ProductoDAO.DESCUENTO1));
                values.put(ProductoDAO.DESCUENTO2, jsonProducto.getString(ProductoDAO.DESCUENTO2));
                values.put(ProductoDAO.DESCUENTO5, jsonProducto.getString(ProductoDAO.DESCUENTO5));
                values.put(ProductoDAO.PRECIOSOLES, jsonProducto.getDouble(ProductoDAO.PRECIOSOLES));
                values.put(ProductoDAO.PRECIODOLARES, jsonProducto.getDouble(ProductoDAO.PRECIODOLARES));
                values.put(ProductoDAO.PRECIOOFICINA, jsonProducto.getDouble(ProductoDAO.PRECIOOFICINA));
                values.put(ProductoDAO.PRECIOLIMA, jsonProducto.getDouble(ProductoDAO.PRECIOLIMA));
                values.put(ProductoDAO.PRECIONORTE, jsonProducto.getDouble(ProductoDAO.PRECIONORTE));
                values.put(ProductoDAO.TOP, jsonProducto.getInt(ProductoDAO.TOP));
                values.put(ProductoDAO.STOCK, jsonProducto.getString(ProductoDAO.STOCK));
                values.put(ProductoDAO.FECHAREGISTRO, jsonProducto.getString(ProductoDAO.FECHAREGISTRO));
                values.put(ProductoDAO.ESTADO1, jsonProducto.getString(ProductoDAO.ESTADO1));
                values.put(ProductoDAO.TIPO, jsonProducto.getString(ProductoDAO.TIPO));
                values.put(ProductoDAO.DIAMETROINTERIOR, jsonProducto.getString(ProductoDAO.DIAMETROINTERIOR));
                values.put(ProductoDAO.DIAMETROEXTERIOR, jsonProducto.getString(ProductoDAO.DIAMETROEXTERIOR));
                values.put(ProductoDAO.APLICACIONES, jsonProducto.getString(ProductoDAO.APLICACIONES));
                values.put(ProductoDAO.ALTURA, jsonProducto.getString(ProductoDAO.ALTURA));
                values.put(ProductoDAO.LONGITUD, jsonProducto.getString(ProductoDAO.LONGITUD));
                values.put(ProductoDAO.ANCHO, jsonProducto.getString(ProductoDAO.ANCHO));
                values.put(ProductoDAO.ROSCA, jsonProducto.getString(ProductoDAO.ROSCA));
                values.put(ProductoDAO.DIAMETROEXTERIORMAX, jsonProducto.getString(ProductoDAO.DIAMETROEXTERIORMAX));
                values.put(ProductoDAO.DIAMETROEXTERIORMIN, jsonProducto.getString(ProductoDAO.DIAMETROEXTERIORMIN));
                values.put(ProductoDAO.DIAMETROINTERIORMAX, jsonProducto.getString(ProductoDAO.DIAMETROINTERIORMAX));
                values.put(ProductoDAO.DIAMETROINTERIORMIN, jsonProducto.getString(ProductoDAO.DIAMETROINTERIORMIN));
                values.put(ProductoDAO.VALVULADERIVACION, jsonProducto.getString(ProductoDAO.VALVULADERIVACION));
                values.put(ProductoDAO.VALVULAANTIRETORNO, jsonProducto.getString(ProductoDAO.VALVULAANTIRETORNO));
                values.put(ProductoDAO.CODIGOPRIMARIO, jsonProducto.getString(ProductoDAO.CODIGOPRIMARIO));
                values.put(ProductoDAO.CATEGORIA, jsonProducto.getString(ProductoDAO.CATEGORIA));
                values.put(ProductoDAO.FORMA, jsonProducto.getString(ProductoDAO.FORMA));
                values.put(ProductoDAO.LINEA, jsonProducto.getString(ProductoDAO.LINEA));
                values.put(ProductoDAO.KIT1, jsonProducto.getString(ProductoDAO.KIT1));
                values.put(ProductoDAO.KIT2, jsonProducto.getString(ProductoDAO.KIT2));
                values.put(ProductoDAO.KIT3, jsonProducto.getString(ProductoDAO.KIT3));
                values.put(ProductoDAO.KIT4, jsonProducto.getString(ProductoDAO.KIT4));
                values.put(ProductoDAO.KIT5, jsonProducto.getString(ProductoDAO.KIT5));
                values.put(ProductoDAO.KIT6, jsonProducto.getString(ProductoDAO.KIT6));
                values.put(ProductoDAO.CLASEWEB, jsonProducto.getString(ProductoDAO.CLASE));
                values.put(ProductoDAO.SUBCLASEWEB, jsonProducto.getString(ProductoDAO.SUBCLASE));
                contentValues[i] = values;
            }
        } catch (Exception e) {
            LOGE(LOG_NAME, e.getMessage(), e.getCause());
        }
        return contentValues;
    }

}
