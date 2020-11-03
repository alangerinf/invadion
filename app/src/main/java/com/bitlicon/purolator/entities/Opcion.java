package com.bitlicon.purolator.entities;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Opcion implements Serializable {

    private int idOpcion;
    private String nombreOpcion;

    public String getNombreOpcion() {
        return nombreOpcion;
    }

    public void setNombreOpcion(String nombreOpcion) {
        this.nombreOpcion = nombreOpcion;
    }

    public int getIdOpcion() {
        return idOpcion;
    }

    public void setIdOpcion(int idOpcion) {
        this.idOpcion = idOpcion;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Opcion{");
        sb.append("idOpcion=").append(idOpcion);
        sb.append(", nombreOpcion='").append(nombreOpcion).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
