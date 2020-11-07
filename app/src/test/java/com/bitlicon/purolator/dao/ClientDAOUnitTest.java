package com.bitlicon.purolator.dao;

import com.bitlicon.purolator.entities.Cliente;
import com.bitlicon.purolator.util.Util;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import java.util.Date;

@RunWith(MockitoJUnitRunner.class)
public class ClientDAOUnitTest {

    @Mock
    ClienteDAO clienteDAO;

    @Mock
    Cliente cliente;
    long id;

    @Test
    public void registrarCliente() {
        cliente.Nombre = "Razon social test";
        cliente.Direccion = "Direccion test";
        cliente.RUC = "11111111111";
        cliente.Telefono = "9999999999";
        cliente.Encargado = "Nombre encargado Test";
        cliente.DNI = "78459999";
        cliente.Email = "test@test.com";
        cliente.PurolatorID = "999999999";
        cliente.FiltechID = "84684488";
        cliente.Nuevo = true;
        cliente.Despacho = "Direccion de despacho test";
        cliente.FechaCreacion = Util.formatoFechaQuery(new Date());
        cliente.Giro = "Giro test";
        cliente.RepresentanteLegal = "nombre del ";
        Assert.assertNotEquals(0,cliente.Nombre.length());
        Assert.assertNotEquals(0,cliente.Direccion.length());
        Assert.assertNotEquals(0,cliente.RUC.length());
        Assert.assertNotEquals(0,cliente.Telefono.trim().length());
        Assert.assertNotEquals(0,cliente.Encargado.trim().length());
        Assert.assertEquals(11,cliente.RUC.length());
        Assert.assertEquals(8,cliente.DNI.trim().length());
        Assert.assertTrue(cliente.Email.length() > 0);
        id = clienteDAO.registrarCliente(cliente);
        Assert.assertNotEquals(-1, id);
        clienteDAO.buscarClientesNoSincronizados();
        clienteDAO.actualizarCliente(cliente);
        clienteDAO.eliminarCliente(id);
    }
}
