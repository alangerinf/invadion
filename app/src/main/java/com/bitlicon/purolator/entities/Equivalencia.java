package com.bitlicon.purolator.entities;

import java.io.Serializable;

/**
 * Created by EduardoAndrés on 16/12/2015.
 */

public class Equivalencia implements Serializable
{
    public String CodigoOriginal;
    public String MarcaOriginal;
    public String CodigoPurolator;
    public String Procedencia;
    public Producto producto;

    public Equivalencia()
    {
        this.producto = new Producto();
    }
}
