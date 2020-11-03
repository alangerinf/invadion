package com.bitlicon.purolator.util;

/**
 * Created by dianewalls on 25/05/2015.
 */
public final class Constantes {

    public static final String APPLICATION_JSON = "application/json";

    public static final int OK = 1;
    public static final int NO_OK = 0;


    public static final int EXITO = 1;
    public static final int ERROR_404 = 2;
    public static final int CREDENCIALES_INVALIDAS = 3;
    public static final int ERROR_ON_SUCCESS = 4;
    public static final int ERROR_ON_REQUEST = 5;

    public static final String NULL = "null";
    public static final int TIMEOUT = 200 * 1000;

    public static final String TAB = "tab";
    public static final String[] TIPOS = {"Deuda Total", "Deuda Corriente", "Deuda Morosa", "Letras No Vencidas", "Letras Vencidas"};

    public static final int RUTA = 0;
    public static final int GENERAL = 1;
    public static final int NUEVO = 2;

    public static final int DEUDA = 0;
    public static final int CORRIENTE = 1;
    public static final int MOROSA = 2;
    public static final int LETRAS = 3;
    public static final int VENCIDAS = 4;

    public static final int INDICE_RUTA = 1;

    public static final int LOGIN_PRIMERA_VEZ_DIA = 1;
    public static final int LOGIN_OTRA_VEZ_DIA = 2;
    public static final int NO_LOGIN = 0;

    public static final String VENDEDOR = "Vendedor";

    public static final int PUROLATOR = 0;
    public static final int FILTECH = 1;

    public static final String EMPTY = "";
    public static final int DIA_INVALIDO = -1;
    public static final int RESUMEN_DIA = 1;
    public static final int RESUMEN_SEMANA = 2;
    public static final int RESUMEN_MES = 3;


    private Constantes() {

    }
}