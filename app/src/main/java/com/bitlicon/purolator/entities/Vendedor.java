package com.bitlicon.purolator.entities;

import java.io.Serializable;

/**
 * Created by Edinson on 07/05/2015.
 */
public class Vendedor implements Serializable {

    public String Usuario;
    public String Nombre;
    public String Linea1;
    public String Linea2;
    public String Password;
    public String FechaSincro;
    public int Recordar;
    public double CuotaPurolator;
    public double CuotaFiltech;
    public int Resultado;
    public String Mensaje;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Vendedor{");
        sb.append("Usuario='").append(Usuario).append('\'');
        sb.append(", Nombre='").append(Nombre).append('\'');
        sb.append(", Linea1='").append(Linea1).append('\'');
        sb.append(", Linea2='").append(Linea2).append('\'');
        sb.append(", Password='").append(Password).append('\'');
        sb.append(", FechaSincro='").append(FechaSincro).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
