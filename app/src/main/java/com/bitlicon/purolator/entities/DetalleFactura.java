package com.bitlicon.purolator.entities;

/**
 * Created by Edinson on 24/05/2015.
 */
public class DetalleFactura {

    public int iDetalleFactura;
    public String ClienteID;
    public String NumeroPedido;
    public String NumeroFactura;
    public String NumeroSecuencia;
    public String ItemID;
    public double Cantidad;
    public double Precio;


    public String Descripcion;

    public double PrecioDolares;

    public double DescuentoDistribuidor;
    public double DescuentoPromocion;

    public double DescuentoOrdenCompra;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DetalleFactura{");
        sb.append("iDetalleFactura=").append(iDetalleFactura);
        sb.append(", NumeroPedido='").append(NumeroPedido).append('\'');
        sb.append(", NumeroFactura='").append(NumeroFactura).append('\'');
        sb.append(", NumeroSecuencia='").append(NumeroSecuencia).append('\'');
        sb.append(", ItemID='").append(ItemID).append('\'');
        sb.append(", Cantidad=").append(Cantidad);
        sb.append(", Precio=").append(Precio);
        sb.append(", Descripcion='").append(Descripcion).append('\'');
        sb.append(", PrecioDolares=").append(PrecioDolares);
        sb.append(", DescuentoDistribuidor=").append(DescuentoDistribuidor);
        sb.append(", DescuentoPromocion=").append(DescuentoPromocion);
        sb.append(", DescuentoOrdenCompra=").append(DescuentoOrdenCompra);
        sb.append('}');
        return sb.toString();
    }
}
