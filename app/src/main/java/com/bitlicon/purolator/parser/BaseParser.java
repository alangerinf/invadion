package com.bitlicon.purolator.parser;

/**
 * Created by dianewalls on 17/07/2015.
 */

import org.json.JSONArray;

public class BaseParser {

    protected JSONArray jsonArray;

    /**
     * Constructor de la clase
     */
    public BaseParser() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructor de la clase
     *
     * @param jsonArray JSON que trae el servicio
     */
    public BaseParser(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

}

