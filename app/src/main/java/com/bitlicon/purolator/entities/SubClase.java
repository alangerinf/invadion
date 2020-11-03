package com.bitlicon.purolator.entities;

import java.io.Serializable;

/**
 * Created by EduardoAndrés on 11/12/2015.
 */
public class SubClase implements Serializable {
    public String Codigo;
    public String Nombre;

    public String toString()
    {
        return Nombre;
    }
}
