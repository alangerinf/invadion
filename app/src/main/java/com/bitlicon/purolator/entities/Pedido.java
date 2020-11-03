package com.bitlicon.purolator.entities;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Edinson on 21/05/2015.
 */
public class Pedido implements Serializable {

    public int iPedido;
    public String NumeroPedido;
    public String VendedorID;
    public String ClienteID;
    public double TotalImporte;
    public double TotalImpuestos;
    public String FechaCreacionPedido;
    public double TotalDescuento;
    public boolean Enviado;
    public boolean Descuento3;
    public boolean Descuento2;
    public boolean Descuento1;
    public boolean Descuento4;
    public boolean Descuento5;

    public String TerminoVenta;
    public String Observacion;
    public String FechaEnvioPedido;

    public double Descuento1Valor;
    public double Descuento2Valor;
    public double Descuento3Valor;

    public List<DetallePedido> DetallePedidos;
    public List<LetraPedido> LetraPedidos;
}
