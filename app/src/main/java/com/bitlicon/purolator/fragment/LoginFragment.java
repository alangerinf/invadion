package com.bitlicon.purolator.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.bitlicon.purolator.R;
import com.bitlicon.purolator.activity.PedidoActivity;
import com.bitlicon.purolator.activity.ResumenActivity;
import com.bitlicon.purolator.dao.ClaseDAO;
import com.bitlicon.purolator.dao.ClienteDAO;
import com.bitlicon.purolator.dao.ConfiguracionDAO;
import com.bitlicon.purolator.dao.DescuentoDAO;
import com.bitlicon.purolator.dao.DetalleFacturaDAO;
import com.bitlicon.purolator.dao.DetallePedidoDAO;
import com.bitlicon.purolator.dao.FacturaDAO;
import com.bitlicon.purolator.dao.LetraPedidoDAO;
import com.bitlicon.purolator.dao.MovimientoDAO;
import com.bitlicon.purolator.dao.PedidoDAO;
import com.bitlicon.purolator.dao.ProductoDAO;
import com.bitlicon.purolator.dao.SincronizacionServicioDAO;
import com.bitlicon.purolator.dao.SubClaseDAO;
import com.bitlicon.purolator.dao.VendedorDAO;
import com.bitlicon.purolator.entities.Configuracion;
import com.bitlicon.purolator.entities.SincronizacionServicio;
import com.bitlicon.purolator.entities.Vendedor;
import com.bitlicon.purolator.lib.SessionManager;
import com.bitlicon.purolator.services.ClaseService;
import com.bitlicon.purolator.services.ClienteService;
import com.bitlicon.purolator.services.DescuentoService;
import com.bitlicon.purolator.services.DetalleFacturaService;
import com.bitlicon.purolator.services.FacturaService;
import com.bitlicon.purolator.services.MovimientoService;
import com.bitlicon.purolator.services.PedidoService;
import com.bitlicon.purolator.services.ProductoService;
import com.bitlicon.purolator.services.SubClaseService;
import com.bitlicon.purolator.services.VendedorService;
import com.bitlicon.purolator.util.AppStatus;
import com.bitlicon.purolator.util.Constantes;
import com.bitlicon.purolator.util.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LoginFragment extends Fragment {


    public static final String POS_TABLA = "Tabla";
    public static final String RES_TABLA = "Resultado";
    public static final String VENDEDOR = "vendedor";
    public static final int POS_CLIENTE = 0;
    public static final int POS_MOVIMIENTO = 1;
    public static final int POS_FACTURA = 2;
    public static final int POS_DETALLE_FACTURA = 3;
    public static final int POS_PRODUCTO = 4;
    public static final int POS_CLASE = 5;
    public static final int POS_SUBCLASE = 6;
    public static final int POS_DESCUENTO = 7;
    public static final int POS_PEDIDO = 8;
    public static final int POS_DETALLE_PEDIDO = 9;
    public static final int POS_LETRA_PEDIDO = 10;
    public static final String CLIENTE = "Cliente";
    public static final String MOVIMIENTO = "Movimiento";
    public static final String FACTURA = "Factura";
    public static final String DETALLE_FACTURA = "DetalleFactura";
    public static final String PRODUCTO = "Producto";
    public static final String CLASE = "Clase";
    public static final String SUBCLASE = "SubClase";
    public static final String DESCUENTO = "Descuento";
    public static final String PEDIDO = "Pedido";
    public static final String DETALLE_PEDIDO = "DetallePedido";
    public static final String LETRA_PEDIDO = "LetraPedido";
    private OnFragmentInteractionListener mListener;
    private Vendedor vendedor;
    private EditText txtUsuario;
    private EditText txtPassword;
    private Button btnIngresar;
    private CheckBox cbxRecordar;
    private ProgressDialog progressDialog;
    private VendedorDAO vendedorDAO;
    private ClienteDAO clienteDAO;
    private ProductoDAO productoDAO;
    private MovimientoDAO movimientoDAO;
    private FacturaDAO facturaDAO;
    private LetraPedidoDAO letraPedidoDAO;
    private DetalleFacturaDAO detalleFacturaDAO;
    private ClaseDAO claseDAO;
    private SubClaseDAO subClaseDAO;
    private DescuentoDAO descuentoDAO;
    private PedidoDAO pedidoDAO;
    private DetallePedidoDAO detallePedidoDAO;
    private String[] mensajes = {"Clientes", "Movimientos", "Facturas", "Detalles de la factura", "Productos", "Clases", "SubClases" , "Descuento" , "Pedido", "DetallePedido", "LetraPedido" };
    private int[] tabla = {0, 0, 0, 0, 0, 0, 0 , 0 , 0 , 0, 0 };
    private boolean[] res_tabla = {false, false, false, false, false, false, false, false, false, false, false};

    private Handler handlerSincro = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int pos = msg.getData().getInt(POS_TABLA);
            Boolean Resultado = msg.getData().getBoolean(RES_TABLA);
            res_tabla[pos] = Resultado;
            tabla[pos] = 1;
            if(!Resultado)
            {
                switch (pos)
                {
                    case 0:
                        Toast.makeText(getActivity(), "Error en descargar Clientes", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(getActivity(), "Error en descargar Movimiento", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(getActivity(), "Error en descargar Facturas", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(getActivity(), "Error en descargar Detalles de la factura", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(getActivity(), "Error en descargar Productos", Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
                        Toast.makeText(getActivity(), "Error en descargar Clases", Toast.LENGTH_SHORT).show();
                        break;
                    case 6:
                        Toast.makeText(getActivity(), "Error en descargar SubClases", Toast.LENGTH_SHORT).show();
                        break;
                    case 7:
                        Toast.makeText(getActivity(), "Error en descargar Descuentos", Toast.LENGTH_SHORT).show();
                        break;
                    case 8:
                        Toast.makeText(getActivity(), "Error en descargar Pedidos", Toast.LENGTH_SHORT).show();
                        break;
                    case 9:
                        Toast.makeText(getActivity(), "Error en descargar Detalle de Pedidos", Toast.LENGTH_SHORT).show();
                        break;
                    case 10:
                        Toast.makeText(getActivity(), "Error en descargar Letra de Pedidos", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
            progressDialog.setMessage(getMensajeTotal());
        }

    };

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            progressDialog.dismiss();

            Vendedor vendedor = (Vendedor) msg.getData().getSerializable(VENDEDOR);
            int resultado = vendedor.Resultado;
            switch (resultado) {
                case Constantes.EXITO:
                    sincronizar(vendedor);
                    break;
                case Constantes.CREDENCIALES_INVALIDAS:
                    credencialesInvalidas();
                    break;
                case Constantes.ERROR_404:
                    servicioNoDisponible();
                    break;
                case Constantes.ERROR_ON_REQUEST:
                    errorServicio();
                    break;
                case Constantes.ERROR_ON_SUCCESS:
                    errorServicio();
                    break;
            }
        }

    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        btnIngresar = (Button) view.findViewById(R.id.btnIngresar);
        btnIngresar.setFocusable(true);
        btnIngresar.setFocusableInTouchMode(true);

        txtUsuario = (EditText) view.findViewById(R.id.txtUsuario);
        txtPassword = (EditText) view.findViewById(R.id.txtPassword);
        cbxRecordar = (CheckBox) view.findViewById(R.id.cbxRecordar);

        progressDialog = new ProgressDialog(getActivity());
        vendedorDAO = new VendedorDAO(getActivity());
        clienteDAO = new ClienteDAO(getActivity());
        movimientoDAO = new MovimientoDAO(getActivity());
        detalleFacturaDAO = new DetalleFacturaDAO(getActivity());
        facturaDAO = new FacturaDAO(getActivity());
        productoDAO = new ProductoDAO(getActivity());
        claseDAO = new ClaseDAO(getActivity());
        subClaseDAO = new SubClaseDAO(getActivity());
        descuentoDAO = new DescuentoDAO(getActivity());
        pedidoDAO = new PedidoDAO(getActivity());
        detallePedidoDAO = new DetallePedidoDAO(getActivity());
        letraPedidoDAO = new LetraPedidoDAO(getActivity());

        Vendedor vendedor = vendedorDAO.buscarVendedor();

        if (vendedor != null && vendedor.Recordar == 1) {
            txtUsuario.setText(vendedor.Usuario);
            cbxRecordar.setChecked(true);
            btnIngresar.requestFocus();
        }

        txtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (txtPassword.getText().length() >= 6) {

                    InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(txtPassword.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    btnIngresar.requestFocus();
                }
            }
        });


        txtPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && txtPassword.getText().length() >= 6) {
                    txtPassword.getText().clear();
                }
            }
        });


        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String usuario = txtUsuario.getText().toString();
                final String password = txtPassword.getText().toString();
                validarUsuario(usuario, password);
            }
        });

        return view;

    }

    private boolean existeConexionInternet() {
        return AppStatus.getInstance(getActivity()).isOnline();
    }


    private void credencialesInvalidas() {
        String mensaje = getString(R.string.credenciales_invalidas);
        Toast.makeText(getActivity(), mensaje, Toast.LENGTH_LONG).show();
    }

    private void servicioNoDisponible() {
        String mensaje = getString(R.string.servicio_no_disponible);
        Toast.makeText(getActivity(), mensaje, Toast.LENGTH_LONG).show();
    }

    private void errorServicio() {
        String mensaje = getString(R.string.error_servicio);
        Toast.makeText(getActivity(), mensaje, Toast.LENGTH_LONG).show();
    }

    private void validarUsuario(final String usuario, final String password) {

        for (int i = 0; i < tabla.length; i++)
        {
            tabla[i] = 0;
        }

        if (usuario.trim().length() == 0 || usuario.trim().length() == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginFragment.this.getActivity());
            builder.setMessage(R.string.campos_requeridos_login);
            builder.setTitle(R.string.error_titulo);
            AlertDialog dialog = builder.create();
            dialog.show();
            return;
        }

        Calendar calendar = Calendar.getInstance();
        final String fechaSincro = Util.formatoFechaQuery(calendar.getTime());

        boolean isOnline = existeConexionInternet();
        vendedor = null;

        if (isOnline) {
            progressDialog.setTitle("Autenticando");
            progressDialog.setMessage("Espere un momento");
            progressDialog.setCancelable(false);
            progressDialog.show();
            new Thread() {
                public void run() {
                    VendedorService vendedorService = new VendedorService();
                    vendedor = vendedorService.iniciarSesion(getActivity(), usuario, password);
                    Message message = handler.obtainMessage();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(VENDEDOR, vendedor);
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
            }.start();
        } else {
            long cantidad = vendedorDAO.existeVendedor();
            if (cantidad == 0) {
                Toast.makeText(getActivity(), getString(R.string.login_primera_vez_offline), Toast.LENGTH_SHORT).show();
                return;
            }
            vendedor = vendedorDAO.buscarVendedor(usuario, password, fechaSincro);
            if (vendedor != null) {
                if (validarSincronizacion()) {
                    mostrarLoginLinea();
                } else {
                    Toast.makeText(getActivity(), getString(R.string.fail_sincro_login_offline), Toast.LENGTH_SHORT).show();
                }
                return;
            } else {
                credencialesInvalidas();
            }
        }

    }

    private void sincronizar(Vendedor vendedor) {

        if (!validarSincronizacion()) {

            progressDialog.setTitle("Descargando");
            progressDialog.setMessage(getMensajeTotal());
            progressDialog.setCancelable(false);
            progressDialog.show();

            if (res_tabla[POS_CLIENTE] == false) {
                progressDialog.setTitle("Descargando");
                progressDialog.setMessage(getMensajeTotal());
                progressDialog.setCancelable(false);
                progressDialog.show();
                clienteDAO.eliminarClientes();
                descargarClientes(vendedor.Linea1, vendedor.Linea2);
            } else if(res_tabla[POS_MOVIMIENTO] == false)
            {
                tabla[0] = 1;
                progressDialog.setTitle("Descargando");
                progressDialog.setMessage(getMensajeTotal());
                progressDialog.setCancelable(false);
                progressDialog.show();
                movimientoDAO.eliminarMovimientos();
                descargarMovimientos(vendedor.Linea1, vendedor.Linea2);
            }else if(res_tabla[POS_FACTURA] == false)
            {
                tabla[0] = 1;
                tabla[1] = 1;
                progressDialog.setTitle("Descargando");
                progressDialog.setMessage(getMensajeTotal());
                progressDialog.setCancelable(false);
                progressDialog.show();
                facturaDAO.eliminarFacturas();
                descargarFacturas(vendedor.Linea1, vendedor.Linea2);
            }else if(res_tabla[POS_DETALLE_FACTURA] == false)
            {
                tabla[0] = 1;
                tabla[1] = 1;
                tabla[2] = 1;
                progressDialog.setTitle("Descargando");
                progressDialog.setMessage(getMensajeTotal());
                progressDialog.setCancelable(false);
                progressDialog.show();
                detalleFacturaDAO.eliminarDetalleFactura();
                descargarDetalleFacturas(vendedor.Linea1, vendedor.Linea2);
            }else if(res_tabla[POS_PRODUCTO] == false)
            {
                tabla[0] = 1;
                tabla[1] = 1;
                tabla[2] = 1;
                tabla[3] = 1;
                progressDialog.setTitle("Descargando");
                progressDialog.setMessage(getMensajeTotal());
                progressDialog.setCancelable(false);
                progressDialog.show();
                productoDAO.eliminarProductos();
                descargarProductos();
            } else if(res_tabla[POS_CLASE] == false)
            {
                tabla[0] = 1;
                tabla[1] = 1;
                tabla[2] = 1;
                tabla[3] = 1;
                tabla[4] = 1;
                progressDialog.setTitle("Descargando");
                progressDialog.setMessage(getMensajeTotal());
                progressDialog.setCancelable(false);
                progressDialog.show();
                claseDAO.eliminarClases();
                descargarClases();
            }
            else if(res_tabla[POS_SUBCLASE] == false)
            {
                tabla[0] = 1;
                tabla[1] = 1;
                tabla[2] = 1;
                tabla[3] = 1;
                tabla[4] = 1;
                tabla[5] = 1;
                progressDialog.setTitle("Descargando");
                progressDialog.setMessage(getMensajeTotal());
                progressDialog.setCancelable(false);
                progressDialog.show();
                subClaseDAO.eliminarSubClase();
                descargarSubClases();
            }else if(res_tabla[POS_DESCUENTO] == false)
            {
                tabla[0] = 1;
                tabla[1] = 1;
                tabla[2] = 1;
                tabla[3] = 1;
                tabla[4] = 1;
                tabla[5] = 1;
                tabla[6] = 1;
                progressDialog.setTitle("Descargando");
                progressDialog.setMessage(getMensajeTotal());
                progressDialog.setCancelable(false);
                progressDialog.show();
                descuentoDAO.eliminar();
                descargarDescuento();
            }else if(res_tabla[POS_PEDIDO] == false)
            {
                tabla[0] = 1;
                tabla[1] = 1;
                tabla[2] = 1;
                tabla[3] = 1;
                tabla[4] = 1;
                tabla[5] = 1;
                tabla[6] = 1;
                tabla[7] = 1;
                progressDialog.setTitle("Descargando");
                progressDialog.setMessage(getMensajeTotal());
                progressDialog.setCancelable(false);
                progressDialog.show();
                pedidoDAO.eliminarPedidos();
                descargarPedidos(vendedor.Linea1, vendedor.Linea2);
            }else if(res_tabla[POS_DETALLE_PEDIDO] == false)
            {
                tabla[0] = 1;
                tabla[1] = 1;
                tabla[2] = 1;
                tabla[3] = 1;
                tabla[4] = 1;
                tabla[5] = 1;
                tabla[6] = 1;
                tabla[7] = 1;
                tabla[8] = 1;
                progressDialog.setTitle("Descargando");
                progressDialog.setMessage(getMensajeTotal());
                progressDialog.setCancelable(false);
                progressDialog.show();
                detallePedidoDAO.eliminarDetallePedidoPorPedidoEnviado();
                descargarDetallePedidos(vendedor.Linea1, vendedor.Linea2);
            }else if(res_tabla[POS_PEDIDO] == false)
            {
                tabla[0] = 1;
                tabla[1] = 1;
                tabla[2] = 1;
                tabla[3] = 1;
                tabla[4] = 1;
                tabla[5] = 1;
                tabla[6] = 1;
                tabla[7] = 1;
                tabla[8] = 1;
                tabla[9] = 1;
                progressDialog.setTitle("Descargando");
                progressDialog.setMessage(getMensajeTotal());
                progressDialog.setCancelable(false);
                progressDialog.show();
                letraPedidoDAO.eliminarLetraPedidoPorPedidoEnviado();
                descargarLetraPedidos(vendedor.Linea1, vendedor.Linea2);
            }

        } else {
            mostrarLoginLinea();
        }

    }

    private Boolean validarSincronizacion() {
        int cont = 0;
        SincronizacionServicioDAO sincronizacionServicioDAO = new SincronizacionServicioDAO(getActivity());
        SincronizacionServicio servicioCliente = sincronizacionServicioDAO.buscarServicio(CLIENTE);
        SincronizacionServicio servicioMovimiento = sincronizacionServicioDAO.buscarServicio(MOVIMIENTO);
        SincronizacionServicio servicioFactura = sincronizacionServicioDAO.buscarServicio(FACTURA);
        SincronizacionServicio servicioDetalleFactura = sincronizacionServicioDAO.buscarServicio(DETALLE_FACTURA);
        SincronizacionServicio servicioProducto = sincronizacionServicioDAO.buscarServicio(PRODUCTO);
        SincronizacionServicio servicioClase = sincronizacionServicioDAO.buscarServicio(CLASE);
        SincronizacionServicio servicioSubClase = sincronizacionServicioDAO.buscarServicio(SUBCLASE);
        SincronizacionServicio servicioDescuento = sincronizacionServicioDAO.buscarServicio(DESCUENTO);
        SincronizacionServicio servicioPedido = sincronizacionServicioDAO.buscarServicio(PEDIDO);
        SincronizacionServicio servicioDetallePedido = sincronizacionServicioDAO.buscarServicio(DETALLE_PEDIDO);
        SincronizacionServicio servicioLetraPedido = sincronizacionServicioDAO.buscarServicio(LETRA_PEDIDO);
        if (validarServicio(servicioCliente)) {
            res_tabla[POS_CLIENTE] = true;
            cont++;
        }
        if (validarServicio(servicioMovimiento)) {
            res_tabla[POS_MOVIMIENTO] = true;
            cont++;
        }
        if (validarServicio(servicioFactura)) {
            res_tabla[POS_FACTURA] = true;
            cont++;
        }
        if (validarServicio(servicioDetalleFactura)) {
            res_tabla[POS_DETALLE_FACTURA] = true;
            cont++;
        }
        if (validarServicio(servicioProducto)) {
            res_tabla[POS_PRODUCTO] = true;
            cont++;
        }
        if (validarServicio(servicioClase)) {
            res_tabla[POS_CLASE] = true;
            cont++;
        }
        if (validarServicio(servicioSubClase)) {
            res_tabla[POS_SUBCLASE] = true;
            cont++;
        }
        if (validarServicio(servicioDescuento)) {
            res_tabla[POS_DESCUENTO] = true;
            cont++;
        }
        if (validarServicio(servicioPedido)) {
            res_tabla[POS_PEDIDO] = true;
            cont++;
        }
        if (validarServicio(servicioDetallePedido)) {
            res_tabla[POS_DETALLE_PEDIDO] = true;
            cont++;
        }
        if (validarServicio(servicioLetraPedido)) {
            res_tabla[POS_LETRA_PEDIDO] = true;
            cont++;
        }
        return cont == 11;
    }

    private void manageHandler(int pos, boolean resultado)
    {
        Message message = handlerSincro.obtainMessage();
        Bundle bundle = new Bundle();
        bundle.putInt(POS_TABLA, pos);
        bundle.putBoolean(RES_TABLA,resultado);
        message.setData(bundle);
        handlerSincro.sendMessage(message);
    }

    private boolean validarServicio(SincronizacionServicio servicioCliente) {
        return servicioCliente != null && servicioCliente.Vendedor.equals(vendedor.Usuario) && servicioCliente.Estado == Constantes.OK && servicioCliente.FechaSincronizacion.equals(Util.getFechaHoyQuery());
    }

    private void descargarClientes(final String linea1, final String linea2) {
        new Thread() {
            public void run()
            {
                ClienteService clienteService = new ClienteService(getActivity());
                Boolean resultado = clienteService.obtenerClientes(linea1, linea2);

                if (resultado)
                {
                    SincronizacionServicioDAO sincronizacionServicioDAO = new SincronizacionServicioDAO(getActivity());
                    SincronizacionServicio sincronizacionServicio = new SincronizacionServicio();
                    sincronizacionServicio.Estado = Constantes.OK;
                    sincronizacionServicio.Entidad = CLIENTE;
                    sincronizacionServicio.FechaSincronizacion = Util.getFechaHoyQuery();
                    sincronizacionServicio.Mensaje = Util.getFechaHoyQueryHora();
                    sincronizacionServicio.Servicio = "ClienteService";
                    sincronizacionServicio.Vendedor = vendedor.Usuario;
                    sincronizacionServicioDAO.registrar(sincronizacionServicio);
                }

                manageHandler(POS_CLIENTE, resultado);

                if (res_tabla[POS_MOVIMIENTO] == false)
                {
                    movimientoDAO.eliminarMovimientos();
                    descargarMovimientos(vendedor.Linea1, vendedor.Linea2);
                }
            }
        }.start();

    }

    private void descargarMovimientos(final String linea1, final String linea2) {

        new Thread() {
            public void run()
            {
                MovimientoService service = new MovimientoService(getActivity());
                Boolean resultado = service.movimientoListar(linea1, linea2);

                if (resultado)
                {
                    SincronizacionServicioDAO sincronizacionServicioDAO = new SincronizacionServicioDAO(getActivity());
                    SincronizacionServicio sincronizacionServicio = new SincronizacionServicio();
                    sincronizacionServicio.Estado = Constantes.OK;
                    sincronizacionServicio.Entidad = MOVIMIENTO;
                    sincronizacionServicio.FechaSincronizacion = Util.getFechaHoyQuery();
                    sincronizacionServicio.Mensaje = Util.getFechaHoyQueryHora();
                    sincronizacionServicio.Servicio = "MovimientoService";
                    sincronizacionServicio.Vendedor = vendedor.Usuario;
                    sincronizacionServicioDAO.registrar(sincronizacionServicio);
                }

                manageHandler(POS_MOVIMIENTO, resultado);

               if (res_tabla[POS_FACTURA] == false)
                {
                    facturaDAO.eliminarFacturas();
                    descargarFacturas(vendedor.Linea1, vendedor.Linea2);
                }
            }
        }.start();



    }



    private void descargarFacturas(final String linea1, final String linea2) {
        new Thread() {
            public void run() {
                FacturaService service = new FacturaService(getActivity());
                Boolean resultado = service.facturaListar(linea1, linea2);

                if (resultado)
                {
                    SincronizacionServicioDAO sincronizacionServicioDAO = new SincronizacionServicioDAO(getActivity());
                    SincronizacionServicio sincronizacionServicio = new SincronizacionServicio();
                    sincronizacionServicio.Estado = Constantes.OK;
                    sincronizacionServicio.Entidad = FACTURA;
                    sincronizacionServicio.FechaSincronizacion = Util.getFechaHoyQuery();
                    sincronizacionServicio.Mensaje = Util.getFechaHoyQueryHora();
                    sincronizacionServicio.Servicio = "FacturaService";
                    sincronizacionServicio.Vendedor = vendedor.Usuario;

                    sincronizacionServicioDAO.registrar(sincronizacionServicio);
                }


                manageHandler(POS_FACTURA, resultado);

                if (res_tabla[POS_DETALLE_FACTURA] == false)
                {
                    detalleFacturaDAO.eliminarDetalleFactura();
                    descargarDetalleFacturas(vendedor.Linea1, vendedor.Linea2);
                }


            }
        }.start();

    }

    private void descargarDetalleFacturas(final String linea1, final String linea2) {
        new Thread() {
            public void run() {
                DetalleFacturaService service = new DetalleFacturaService(getActivity());
                Boolean resultado = service.detalleFacturaListar(linea1, linea2);

                if (resultado)
                {
                    SincronizacionServicioDAO sincronizacionServicioDAO = new SincronizacionServicioDAO(getActivity());
                    SincronizacionServicio sincronizacionServicio = new SincronizacionServicio();
                    sincronizacionServicio.Estado = Constantes.OK;
                    sincronizacionServicio.Entidad = DETALLE_FACTURA;
                    sincronizacionServicio.FechaSincronizacion = Util.getFechaHoyQuery();
                    sincronizacionServicio.Mensaje = Util.getFechaHoyQueryHora();
                    sincronizacionServicio.Servicio = "DetalleFacturaService";
                    sincronizacionServicio.Vendedor = vendedor.Usuario;
                    sincronizacionServicioDAO.registrar(sincronizacionServicio);
                }


                manageHandler(POS_DETALLE_FACTURA,resultado);

                if(res_tabla[POS_PRODUCTO] == false)
                {
                    productoDAO.eliminarProductos();
                    descargarProductos();
                }

            }
        }.start();
    }

    private void descargarProductos() {
        new Thread() {
            public void run() {
                ProductoService service = new ProductoService(getActivity());
                Boolean resultado = service.obtenerProductos();
                if (resultado)
                {
                    SincronizacionServicioDAO sincronizacionServicioDAO = new SincronizacionServicioDAO(getActivity());
                    SincronizacionServicio sincronizacionServicio = new SincronizacionServicio();
                    sincronizacionServicio.Estado = Constantes.OK;
                    sincronizacionServicio.Entidad = PRODUCTO;
                    sincronizacionServicio.FechaSincronizacion = Util.getFechaHoyQuery();
                    sincronizacionServicio.Mensaje = Util.getFechaHoyQueryHora();
                    sincronizacionServicio.Servicio = "ProductoService";
                    sincronizacionServicio.Vendedor = vendedor.Usuario;
                    sincronizacionServicioDAO.registrar(sincronizacionServicio);
                }

                manageHandler(POS_PRODUCTO,resultado);

                if(res_tabla[POS_CLASE] == false)
                {
                    claseDAO.eliminarClases();
                    descargarClases();
                }

            }
        }.start();
    }

    private void descargarClases() {
        new Thread() {
            public void run() {
                ClaseService claseService = new ClaseService(getActivity());
                Boolean resultado = claseService.obtenerClases();
                if (resultado)
                {
                    SincronizacionServicioDAO sincronizacionServicioDAO = new SincronizacionServicioDAO(getActivity());
                    SincronizacionServicio sincronizacionServicio = new SincronizacionServicio();
                    sincronizacionServicio.Estado = Constantes.OK;
                    sincronizacionServicio.Entidad = CLASE;
                    sincronizacionServicio.FechaSincronizacion = Util.getFechaHoyQuery();
                    sincronizacionServicio.Mensaje = Util.getFechaHoyQueryHora();
                    sincronizacionServicio.Servicio = "ClaseService";
                    sincronizacionServicio.Vendedor = vendedor.Usuario;
                    sincronizacionServicioDAO.registrar(sincronizacionServicio);
                }

                manageHandler(POS_CLASE,resultado);

                if(res_tabla[POS_SUBCLASE] == false)
                {
                    subClaseDAO.eliminarSubClase();
                    descargarSubClases();
                }

            }
        }.start();
    }

    private void descargarSubClases() {
        new Thread() {
            public void run() {
                SubClaseService subClaseService = new SubClaseService(getActivity());
                Boolean resultado = subClaseService.obtenerSubClases();
                if (resultado)
                {
                    SincronizacionServicioDAO sincronizacionServicioDAO = new SincronizacionServicioDAO(getActivity());
                    SincronizacionServicio sincronizacionServicio = new SincronizacionServicio();
                    sincronizacionServicio.Estado = Constantes.OK;
                    sincronizacionServicio.Entidad = SUBCLASE;
                    sincronizacionServicio.FechaSincronizacion = Util.getFechaHoyQuery();
                    sincronizacionServicio.Mensaje = Util.getFechaHoyQueryHora();
                    sincronizacionServicio.Servicio = "SubClaseService";
                    sincronizacionServicio.Vendedor = vendedor.Usuario;
                    sincronizacionServicioDAO.registrar(sincronizacionServicio);
                }

                manageHandler(POS_SUBCLASE,resultado);

                if(res_tabla[POS_DESCUENTO] == false)
                {
                    descuentoDAO.eliminar();
                    descargarDescuento();
                }

            }
        }.start();
    }

    private void descargarDescuento()
    {
        new Thread() {
            public void run() {
                DescuentoService descuentoService = new DescuentoService(getActivity());
                Boolean resultado = descuentoService.obtenerDescuentos();
                if (resultado)
                {
                    SincronizacionServicioDAO sincronizacionServicioDAO = new SincronizacionServicioDAO(getActivity());
                    SincronizacionServicio sincronizacionServicio = new SincronizacionServicio();
                    sincronizacionServicio.Estado = Constantes.OK;
                    sincronizacionServicio.Entidad = DESCUENTO;
                    sincronizacionServicio.FechaSincronizacion = Util.getFechaHoyQuery();
                    sincronizacionServicio.Mensaje = Util.getFechaHoyQueryHora();
                    sincronizacionServicio.Servicio = "DescuentoService";
                    sincronizacionServicio.Vendedor = vendedor.Usuario;
                    sincronizacionServicioDAO.registrar(sincronizacionServicio);
                }
                manageHandler(POS_DESCUENTO,resultado);

                if(res_tabla[POS_PEDIDO] == false)
                {
                    pedidoDAO.eliminarPedidos();
                    descargarPedidos(vendedor.Linea1, vendedor.Linea2);
                }

            }
        }.start();
    }

    private void descargarPedidos(final String linea1, final String linea2)
    {
        new Thread() {
            public void run() {
                PedidoService pedidoService = new PedidoService(getActivity());
                Boolean resultado = pedidoService.pedidoListar(linea1, linea2);
                if (resultado)
                {
                    SincronizacionServicioDAO sincronizacionServicioDAO = new SincronizacionServicioDAO(getActivity());
                    SincronizacionServicio sincronizacionServicio = new SincronizacionServicio();
                    sincronizacionServicio.Estado = Constantes.OK;
                    sincronizacionServicio.Entidad = PEDIDO;
                    sincronizacionServicio.FechaSincronizacion = Util.getFechaHoyQuery();
                    sincronizacionServicio.Mensaje = Util.getFechaHoyQueryHora();
                    sincronizacionServicio.Servicio = "PedidoService";
                    sincronizacionServicio.Vendedor = vendedor.Usuario;
                    sincronizacionServicioDAO.registrar(sincronizacionServicio);
                }
                manageHandler(POS_PEDIDO,resultado);
                if(res_tabla[POS_DETALLE_PEDIDO] == false)
                {
                    detallePedidoDAO.eliminarDetallePedidoPorPedidoEnviado();
                    descargarDetallePedidos(vendedor.Linea1, vendedor.Linea2);
                }
            }
        }.start();
    }

    private void descargarDetallePedidos(final String linea1, final String linea2)
    {
        new Thread() {
            public void run() {
                PedidoService pedidoService = new PedidoService(getActivity());
                Boolean resultado = pedidoService.detallePedidoListar(linea1,linea2);
                if (resultado)
                {
                    SincronizacionServicioDAO sincronizacionServicioDAO = new SincronizacionServicioDAO(getActivity());
                    SincronizacionServicio sincronizacionServicio = new SincronizacionServicio();
                    sincronizacionServicio.Estado = Constantes.OK;
                    sincronizacionServicio.Entidad = DETALLE_PEDIDO;
                    sincronizacionServicio.FechaSincronizacion = Util.getFechaHoyQuery();
                    sincronizacionServicio.Mensaje = Util.getFechaHoyQueryHora();
                    sincronizacionServicio.Servicio = "DetallePedidoService";
                    sincronizacionServicio.Vendedor = vendedor.Usuario;
                    sincronizacionServicioDAO.registrar(sincronizacionServicio);
                }
                manageHandler(POS_DETALLE_PEDIDO,resultado);

                if(res_tabla[POS_DETALLE_PEDIDO] == false)
                {
                    letraPedidoDAO.eliminarLetraPedidoPorPedidoEnviado();
                    descargarLetraPedidos(vendedor.Linea1, vendedor.Linea2);
                }
            }
        }.start();
    }

    private void descargarLetraPedidos(final String linea1, final String linea2)
    {
        new Thread() {
            public void run() {
                PedidoService pedidoService = new PedidoService(getActivity());
                Boolean resultado = pedidoService.letraPedidoListar(linea1,linea2);
                if (resultado)
                {
                    SincronizacionServicioDAO sincronizacionServicioDAO = new SincronizacionServicioDAO(getActivity());
                    SincronizacionServicio sincronizacionServicio = new SincronizacionServicio();
                    sincronizacionServicio.Estado = Constantes.OK;
                    sincronizacionServicio.Entidad = LETRA_PEDIDO;
                    sincronizacionServicio.FechaSincronizacion = Util.getFechaHoyQuery();
                    sincronizacionServicio.Mensaje = Util.getFechaHoyQueryHora();
                    sincronizacionServicio.Servicio = "LetraPedidoService";
                    sincronizacionServicio.Vendedor = vendedor.Usuario;
                    sincronizacionServicioDAO.registrar(sincronizacionServicio);
                }
                manageHandler(POS_LETRA_PEDIDO,resultado);
            }
        }.start();
    }

    private String getMensajeTotal()
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < tabla.length; i++)
        {
            if (tabla[i] == 0)
            {
                sb.append(mensajes[i]);
                sb.append("\n");
            }
        }

        if (sb.toString().isEmpty())
        {
            progressDialog.dismiss();
            if (validarSincronizacion())
            {
                mostrarLoginLinea();
            }
            else
            {
                Toast.makeText(getActivity(), getString(R.string.fail_sincro_login_online), Toast.LENGTH_SHORT).show();
            }
        }
        return sb.toString();
    }

    private void mostrarLoginLinea()
    {
        vendedorDAO.eliminarVendedor();
        vendedor.FechaSincro = Util.getFechaHoyQuery();
        vendedor.Recordar = cbxRecordar.isChecked() ? 1 : 0;
        vendedorDAO.registrarVendedor(vendedor);
        SessionManager sessionManager = new SessionManager(getActivity().getApplicationContext());

        sessionManager.createLoginSession(vendedor.Usuario, vendedor.Nombre, vendedor.Linea1.trim(), vendedor.Linea2.trim());

        ConfiguracionDAO ConfDAO = new ConfiguracionDAO(getActivity());
        Configuracion Conf = ConfDAO.Obtener();

        Calendar c = Calendar.getInstance();
        String myFormat = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        ConfDAO.ActualizarFecha(sdf.format(c.getTime()));

        Intent intent = null;

        switch (Conf.PantallaInicio)
        {
            case 0:
                //Clientes
                intent = new Intent(getActivity().getApplicationContext(), ResumenActivity.class);
                intent.putExtra("Fragment", 1);
                startActivity(intent);
                getActivity().finish();
                break;
            case 1:
                //Pedidos
                intent = new Intent(getActivity().getApplicationContext(), PedidoActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
            case 2:
                //Stock
                intent = new Intent(getActivity().getApplicationContext(), ResumenActivity.class);
                intent.putExtra("Fragment", 2);
                startActivity(intent);
                getActivity().finish();
                break;
            case 3:
                //Resumen
                intent = new Intent(getActivity().getApplicationContext(), ResumenActivity.class);
                intent.putExtra("Fragment", 3);
                startActivity(intent);
                getActivity().finish();
                break;
            default:
                intent = new Intent(getActivity().getApplicationContext(), ResumenActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
        }
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null)
        {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try
        {
            mListener = (OnFragmentInteractionListener) activity;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
