package com.bitlicon.purolator.dao;

import android.content.Context;
import com.bitlicon.purolator.entities.Cliente;
import com.bitlicon.purolator.fragment.LoginFragment;
import com.bitlicon.purolator.services.ClaseService;
import com.bitlicon.purolator.services.ClienteService;
import com.bitlicon.purolator.services.DescuentoService;
import com.bitlicon.purolator.services.DetalleFacturaService;
import com.bitlicon.purolator.services.EquivalenciaService;
import com.bitlicon.purolator.services.FacturaService;
import com.bitlicon.purolator.services.MovimientoService;
import com.bitlicon.purolator.services.PedidoService;
import com.bitlicon.purolator.services.ProductoService;
import com.bitlicon.purolator.services.SubClaseService;
import com.bitlicon.purolator.services.VendedorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ServiceUnitTest {


    private String linea1 = "", linea2 = "", nombre = "";

    @Mock
    Context ctx;

    @Mock
    ClaseService claseService;

    @Mock
    ClienteService clientService;

    @Mock
    DescuentoService descuentoService;

    @Mock
    DetalleFacturaService detalleFacturaService;

    @Mock
    EquivalenciaService equivalenciaService;

    @Mock
    FacturaService facturaService;

    @Mock
    MovimientoService movimientoService;

    @Mock
    PedidoService pedidoService;

    @Mock
    ProductoService productoService;

    @Mock
    SubClaseService subClaseService;

    @Mock
    VendedorService vendedorService;
    @Mock
    LoginFragment loginFragment;

    @Mock
    Cliente cliente;

    @Test
    public void testClaseService(){
        claseService.obtenerClases();
    }



    @Test
    public void testClientService() {
        clientService.enviarCliente(ctx,cliente);
        clientService.obtenerClientes( linea1, linea2);
        clientService.getDistrito(cliente);
        clientService.getDireccion(cliente);
    }

    @Test
    public void testDescuentoService() {
        descuentoService.obtenerDescuentos();
    }

    @Test
    public void testDetalleFacturaService() {
        detalleFacturaService.detalleFacturaListar(linea1, linea2);
    }

    @Test
    public void testEquivalenciaService(){
        equivalenciaService.obtenerEquivalencias();
        equivalenciaService.obtenerEquivalenciasPorNombre(nombre);
    }

    @Test
    public void testFacturaService(){
        facturaService.facturaListar(linea1, linea2);
    }

    @Test
    public void testMovimientoService(){
        movimientoService.movimientoListar(linea1, linea2);
    }

    @Test
    public void testPedidoService(){
        pedidoService.detallePedidoListar(linea1, linea2);
        pedidoService.letraPedidoListar(linea1, linea2);
        pedidoService.pedidoListar(linea1, linea2);
    }

    @Test
    public void testProductoService(){
        productoService.obtenerProductos();
    }

    @Test
    public void testSubClaseService(){
        subClaseService.obtenerSubClases();
    }

    @Test
    public void sincronizar(){
        loginFragment.validarUsuario("250350", "075025");
        vendedorService.iniciarSesion(ctx, "250350", "075025");
        clientService.enviarCliente(ctx,cliente);
    }

    @Test
    public void verifyDescuentoService(){
        descuentoService.obtenerDescuentos();
    }

}
