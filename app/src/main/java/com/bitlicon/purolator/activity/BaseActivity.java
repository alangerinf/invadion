package com.bitlicon.purolator.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentTransaction;

import com.bitlicon.purolator.R;
import com.bitlicon.purolator.adapter.ClienteAdapter;
import com.bitlicon.purolator.adapter.EquivalenciaAdapter;
import com.bitlicon.purolator.dao.ClaseDAO;
import com.bitlicon.purolator.dao.ClienteDAO;
import com.bitlicon.purolator.dao.EquivalenciaDAO;
import com.bitlicon.purolator.dao.MovimientoDAO;
import com.bitlicon.purolator.dao.ProductoDAO;
import com.bitlicon.purolator.dao.SincronizacionServicioDAO;
import com.bitlicon.purolator.dao.SubClaseDAO;
import com.bitlicon.purolator.dao.VendedorDAO;
import com.bitlicon.purolator.dialog.DateDialog;
import com.bitlicon.purolator.entities.Clase;
import com.bitlicon.purolator.entities.Cliente;
import com.bitlicon.purolator.entities.Equivalencia;
import com.bitlicon.purolator.entities.Producto;
import com.bitlicon.purolator.entities.SincronizacionServicio;
import com.bitlicon.purolator.entities.SubClase;
import com.bitlicon.purolator.entities.Vendedor;
import com.bitlicon.purolator.fragment.BaseFragment;
import com.bitlicon.purolator.fragment.ClienteFragment;
import com.bitlicon.purolator.fragment.ClienteGeneralFragment;
import com.bitlicon.purolator.fragment.ClienteNuevoFragment;
import com.bitlicon.purolator.fragment.ClienteRutaFragment;
import com.bitlicon.purolator.fragment.MenuListFragment;
import com.bitlicon.purolator.fragment.StockFragment;
import com.bitlicon.purolator.lib.SessionManager;
import com.bitlicon.purolator.services.ClienteService;
import com.bitlicon.purolator.services.EquivalenciaService;
import com.bitlicon.purolator.services.MovimientoService;
import com.bitlicon.purolator.services.VendedorService;
import com.bitlicon.purolator.util.AppStatus;
import com.bitlicon.purolator.util.Constantes;
import com.bitlicon.purolator.util.Util;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;


public class BaseActivity extends ControlSlidingFragmentActivity {


    public static final String RESULTADO = "resultado";
    public static final String CLIENTE = "cliente";
    protected MenuListFragment mFrag;
    private int mTitleRes;
    private int mLayout;
    private SessionManager manager;
    private int cantidad;
    private ProgressDialog progressDialog;
    private ClienteDAO clienteDAO;
    private SincronizacionServicioDAO sincronizacionServicioDAO;
    private ClienteRutaFragment clienteRutaFragment;
    private ClienteNuevoFragment clienteNuevoFragment;
    private StockFragment stockFragment;
    public ProgressDialog progressDialogM;
    MovimientoService movimientoService;

    public boolean Nuevos,Ofertas,Kits,Packs;
    public String ordenamientoSctock = ProductoDAO.NOMBRE;
    public String nombreStock = "";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            int resultado = msg.getData().getInt(RESULTADO);
            Cliente cliente = (Cliente) msg.getData().getSerializable(CLIENTE);

            if (resultado > 0) {
                cliente.ClienteID = String.valueOf(resultado);
                cliente.Nuevo = true;
                clienteDAO.actualizarCliente(cliente);
            }

            cantidad--;
            progressDialog.setMessage(getMensaje());
            if (cantidad == 0) {
                progressDialog.dismiss();
            }
        }

    };

    private Handler handlerMovimientos = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            progressDialog.dismiss();

            Boolean Resultado = (Boolean) msg.getData().getSerializable(SINCRONIZADO);
            if(Resultado)
            {
                setTitle("Resumen" + " " + sincronizacionServicio.Mensaje);;
            }
        }

    };

    private ClienteGeneralFragment clienteGeneralFragment;

    public BaseActivity(int titleRes, int layout) {
        mTitleRes = titleRes;
        mLayout = layout;
    }

    public BaseActivity(int titleRes) {
        mTitleRes = titleRes;
    }

    public SessionManager getManager() {
        return manager;
    }

    public ClienteGeneralFragment getClienteGeneralFragment() {
        return clienteGeneralFragment;
    }

    public void setClienteGeneralFragment(ClienteGeneralFragment clienteGeneralFragment) {
        this.clienteGeneralFragment = clienteGeneralFragment;
    }

    public ClienteNuevoFragment getClienteNuevoFragment() {
        return clienteNuevoFragment;
    }

    public void setClienteNuevoFragment(ClienteNuevoFragment clienteNuevoFragment) {
        this.clienteNuevoFragment = clienteNuevoFragment;
    }

    public StockFragment getStockFragment() {
        return stockFragment;
    }

    public void setStockFragment(StockFragment stockFragment) {
        this.stockFragment = stockFragment;
    }

    public ClienteRutaFragment getClienteRutaFragment() {
        return clienteRutaFragment;
    }

    public void setClienteRutaFragment(ClienteRutaFragment clienteRutaFragment) {
        this.clienteRutaFragment = clienteRutaFragment;
    }

    public void setLayout(int mLayout) {
        this.mLayout = mLayout;
    }


    SessionManager sessionManager;
    VendedorDAO vendedorDAO;
    Vendedor vendedor;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBehindContentView(R.layout.menu_frame);
        manager = new SessionManager(getApplicationContext());
        progressDialog = new ProgressDialog(BaseActivity.this);
        clienteDAO = new ClienteDAO(getApplicationContext());
        movimientoService = new MovimientoService(getApplicationContext());
        movimientoDAO = new MovimientoDAO(getApplicationContext());
        vendedorDAO = new VendedorDAO(getApplicationContext());
        sincronizacionServicioDAO = new SincronizacionServicioDAO(getApplicationContext());
        sessionManager  = new SessionManager(BaseActivity.this);
        String usuario = sessionManager.getIdUsuario();
        if (usuario != null) {
            VendedorDAO vendedorDAO = new VendedorDAO(getApplicationContext());
            vendedor = vendedorDAO.buscarVendedor(usuario);
        }
        Util.setIconMenu(R.drawable.ic_sandwich, this);
        if (savedInstanceState == null) {
            FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
            mFrag = new MenuListFragment();
            t.replace(R.id.menu_frame, mFrag);
            t.commit();
        } else {
            mFrag = (MenuListFragment) this.getSupportFragmentManager().findFragmentById(R.id.menu_frame);
        }

        SlidingMenu sm = getSlidingMenu();
        sm.setShadowWidthRes(R.dimen.shadow_width);
        sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        sm.setFadeDegree(0.35f);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(mLayout, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search_ruta);
        setActionMenuItemRuta(menuItem);

        MenuItem menuItemGeneral = menu.findItem(R.id.action_search_general);
        setActionMenuItemGeneral(menuItemGeneral);

        MenuItem menuItemStock = menu.findItem(R.id.action_search_producto);
        setActionMenuItemStock(menuItemStock);

        return super.onCreateOptionsMenu(menu);
    }

    public void setActionMenuItemRuta(MenuItem menuItem) {
        if (menuItem != null) {
            SearchView search = (SearchView) menuItem.getActionView();
            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    buscarClientesRuta(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });

        }
    }

    public void setActionMenuItemGeneral(MenuItem menuItem) {
        if (menuItem != null) {
            SearchView search = (SearchView) menuItem.getActionView();
            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    buscarClientesGeneral(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }
    }

    public void setActionMenuItemStock (MenuItem menuItem)
    {
        if (menuItem != null)
        {
            final SearchView search = (SearchView) menuItem.getActionView();
            search.setOnQueryTextListener(new SearchView.OnQueryTextListener()
            {
                @Override
                public boolean onQueryTextSubmit(String query)
                {
                    buscarProducto(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });

            search.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose()
                {
                    buscarProducto("");
                    return false;
                }
            });
        }
    }

    public void buscarClientesRuta(String query) {
        if (clienteRutaFragment != null) {
            ArrayList<Cliente> clientes = clienteDAO.buscarClientesAvanzada(query,
                    clienteRutaFragment.getCampoOrden(),
                    clienteRutaFragment.getCampoOrden(),
                    clienteRutaFragment.getValorDia(),
                    clienteRutaFragment.isValorNuevo(),
                    Util.esSemanaPar());
            clienteRutaFragment.getClienteAdapter().clear();
            clienteRutaFragment.getClienteAdapter().addAll(clientes);
            clienteRutaFragment.getClienteAdapter().notifyDataSetChanged();

            clienteRutaFragment.getIndexList(clientes);
        }
    }

    public void buscarClientesGeneral(String query) {
        if (clienteGeneralFragment != null) {
            ArrayList<Cliente> clientes = clienteDAO.buscarClientesAvanzada(query,
                    clienteGeneralFragment.getCampoOrden(),
                    clienteGeneralFragment.getCampoOrden(),
                    clienteGeneralFragment.getValorDia(),
                    clienteGeneralFragment.isValorNuevo(),
                    false);
            clienteGeneralFragment.getClienteAdapter().clear();
            clienteGeneralFragment.getClienteAdapter().addAll(clientes);
            clienteGeneralFragment.getClienteAdapter().notifyDataSetChanged();
            clienteGeneralFragment.getIndexList(clientes);
        }
    }

    public void buscarProducto(String Nombre)
    {
        if (stockFragment != null)
        {
            nombreStock = Nombre;
            ProductoDAO productoDAO = new ProductoDAO(getApplicationContext());
            ArrayList<Producto> productos = productoDAO.buscarNombre(nombreStock,
                    ordenamientoSctock,Nuevos,Ofertas,Kits,Packs);
            stockFragment.getStockAdapter().clear();
            stockFragment.getStockAdapter().addAll(productos);
            stockFragment.getStockAdapter().notifyDataSetChanged();
            stockFragment.getIndexList(productos, ordenamientoSctock);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                BaseActivity.this.toggle();
                break;
            case R.id.action_nuevo:
                nuevoCliente();
                break;
            case R.id.action_eliminar:
                eliminar();
                break;
            case R.id.action_seleccionar:
                seleccionar();
                break;
            case R.id.action_ordenar_codigo_general:
                ordenarPorCodigo(clienteGeneralFragment);
                break;
            case R.id.action_ordenar_codigo_ruta:
                ordenarPorCodigo(clienteRutaFragment);
                break;
            case R.id.action_ordenar_codigo_nuevo:
                ordenarPorCodigo(clienteNuevoFragment);
                break;
            case R.id.action_ordenar_nombre_general:
                ordenarPorNombre(clienteGeneralFragment);
                break;
            case R.id.action_ordenar_nombre_ruta:
                ordenarPorNombre(clienteRutaFragment);
                break;
            case R.id.action_ordenar_nombre_nuevo:
                ordenarPorNombre(clienteNuevoFragment);
                break;
            case R.id.action_sicronizar:
                sincronizar();
                break;
            case R.id.action_busqueda_general:
                buscar(clienteGeneralFragment);
                break;
            case R.id.action_busqueda_ruta:
                buscar(clienteRutaFragment);
                break;
            case R.id.action_busqueda_nuevo:
                buscar(clienteNuevoFragment);
                break;
            case R.id.action_ruta:
                ruta();
                break;
            case R.id.action_ruta_general:
                ClienteFragment clienteFragment = (ClienteFragment) this.getSupportFragmentManager().findFragmentById(R.id.content_main);
                clienteFragment.viewPagerClientes.setCurrentItem(0);
                ruta();
                break;
            case R.id.sincronizar_movimientos:
                boolean isOnline = existeConexionInternet();
                if (isOnline) {
                    descargarMovimientos();
                }else
                {
                    Toast.makeText(this, getString(R.string.fail_sincro_login_offline), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.filtrar_stock:
                filtrar_stock(stockFragment);
                break;
            case R.id.ordenar_nombre_stock:
                ordenar_stock(stockFragment, "Nombre");
                break;
            case R.id.ordenar_clase_stock:
                ordenar_stock(stockFragment, "Clase");
                break;
            case R.id.ordenar_subclase_stock:
                ordenar_stock(stockFragment, "SubClase");
                break;
            case R.id.busqueda_avanzada_stock:
                busqueda_avanzada_stock(stockFragment);
                break;
            case R.id.busqueda_equivalencia_stock:
                busqueda_equivalencias_stock(stockFragment);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean existeConexionInternet() {
        return AppStatus.getInstance(this).isOnline();
    }
    private MovimientoDAO movimientoDAO;
    public static final String MOVIMIENTO = "Movimiento";
    public static final String SINCRONIZADO = "Sincronizado";
    private SincronizacionServicio sincronizacionServicio;
    public void descargarMovimientos() {

        progressDialog.setTitle("Sincronizando");
        progressDialog.setMessage("Actualizando Movimientos..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        new Thread() {
            public void run() {
                movimientoDAO.eliminarMovimientos();
                Boolean resultado = movimientoService.movimientoListar(vendedor.Linea1, vendedor.Linea2);
                if (resultado) {
                    sincronizacionServicioDAO.eliminar(MOVIMIENTO);
                    sincronizacionServicio = new SincronizacionServicio();
                    sincronizacionServicio.Estado = Constantes.OK;
                    sincronizacionServicio.Entidad = MOVIMIENTO;
                    sincronizacionServicio.FechaSincronizacion = Util.getFechaHoyQuery();
                    sincronizacionServicio.Mensaje = Util.getFechaHoyQueryHora();
                    sincronizacionServicio.Servicio = "MovimientoService";
                    sincronizacionServicio.Vendedor = vendedor.Usuario;
                    sincronizacionServicioDAO.registrar(sincronizacionServicio);
                    Message message = handlerMovimientos.obtainMessage();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(SINCRONIZADO, resultado);
                    message.setData(bundle);
                    handlerMovimientos.sendMessage(message);
                }
            }
        }.start();

    }

    public void nuevoCliente() {
        Intent intent = new Intent(getApplicationContext(), AgregarClienteActivity.class);
        startActivity(intent);
    }

    public void ordenarPorNombre(BaseFragment baseFragment) {

        ArrayList<Cliente> clientes = clienteDAO.buscarClientesAvanzada(Constantes.EMPTY, ClienteDAO.NOMBRE,
                ClienteDAO.NOMBRE, baseFragment.getValorDia(), baseFragment.isValorNuevo(), Util.esSemanaPar());
        baseFragment.setCampoOrden(ClienteDAO.NOMBRE);
        baseFragment.getClienteAdapter().clear();
        baseFragment.getClienteAdapter().addAll(clientes);
        baseFragment.getClienteAdapter().notifyDataSetChanged();
        baseFragment.getIndexList(clientes);
    }

    public void ordenarPorCodigo(BaseFragment baseFragment) {

        ArrayList<Cliente> clientes = clienteDAO.buscarClientesAvanzada(Constantes.EMPTY, ClienteDAO.NOMBRE,
                ClienteDAO.CLIENTE_ID, baseFragment.getValorDia(), baseFragment.isValorNuevo(), Util.esSemanaPar());
        baseFragment.setCampoOrden(ClienteDAO.CLIENTE_ID);
        baseFragment.getClienteAdapter().clear();
        baseFragment.getClienteAdapter().addAll(clientes);
        baseFragment.getClienteAdapter().notifyDataSetChanged();
        baseFragment.getIndexList(clientes);

    }

    public void sincronizar() {
        ArrayList<Cliente> clientes = clienteDAO.buscarClientesNoSincronizados();
        int n = clientes.size();
        cantidad = n;

        String mensaje = getMensaje();
        progressDialog.setCancelable(false);
        if (mensaje.equals(Constantes.EMPTY)) {
            Toast.makeText(getApplicationContext(), "Todos los clientes nuevos han sido sincronizados", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.setTitle("Sincronizando");
            progressDialog.setMessage(mensaje);
            progressDialog.show();
        }
        for (int i = 0; i < n; i++) {
            final Cliente cliente = clientes.get(i);
            new Thread() {
                public void run() {
                    ClienteService clienteService = new ClienteService(getApplicationContext());
                    int result = clienteService.enviarCliente(getApplicationContext(), cliente);
                    manageHandler(result, cliente);

                }
            }.start();

        }
    }
    private String getMensaje() {
        if (cantidad == 0) {
            return Constantes.EMPTY;
        } else if (cantidad == 1) {
            return "Queda 1 registro";
        } else {

            return "Quedan " + cantidad + " registros";
        }
    }

    private void manageHandler(int result, Cliente cliente) {
        Message message = handler.obtainMessage();
        Bundle bundle = new Bundle();
        bundle.putInt(RESULTADO, result);
        bundle.putSerializable(CLIENTE, cliente);
        message.setData(bundle);
        handler.sendMessage(message);
    }

    public void seleccionar() {
        ClienteAdapter adapter = getClienteNuevoFragment().getClienteAdapter();
        adapter.seleccionarTodos();
        adapter.notifyDataSetChanged();
    }

    public void eliminar() {

        int cant = getClienteNuevoFragment().getClienteAdapter().getCantidad();
        if (cant <= 0) {
            Toast.makeText(getApplicationContext(), "No existen clientes seleccionados", Toast.LENGTH_LONG).show();
            return;
        }
        Log.d(this.getClass().getName(), "Cantidad: " + cant);
        AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);
        builder.setTitle(R.string.confirmacion);
        if (cant == 1) {
            builder.setMessage(R.string.eliminar_uno);
        } else {
            String mensaje = getString(R.string.eliminar_masivo).replace("X", String.valueOf(cant));
            builder.setMessage(mensaje);
        }

        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                getClienteNuevoFragment().getClienteAdapter().eliminar();
                getClienteNuevoFragment().reset();


            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();


            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }

    public void buscar(final BaseFragment baseFragment) {


        final Dialog dialog_busqueda = new Dialog(BaseActivity.this);
        dialog_busqueda.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog_busqueda.setContentView(R.layout.dialogo_busqueda_avanzada);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        Window window = dialog_busqueda.getWindow();
        layoutParams.copyFrom(window.getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);
        dialog_busqueda.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        LinearLayout lnCancelar = (LinearLayout) dialog_busqueda.findViewById(R.id.lnCancelar);
        lnCancelar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog_busqueda.dismiss();
            }
        });

        RadioGroup radioGroup = (RadioGroup) dialog_busqueda.findViewById(R.id.lstOpciones);
        final RadioButton item1 = (RadioButton) radioGroup.getChildAt(0);
        final RadioButton item2 = (RadioButton) radioGroup.getChildAt(1);
        final RadioButton item3 = (RadioButton) radioGroup.getChildAt(2);
        final RadioButton item4 = (RadioButton) radioGroup.getChildAt(3);
        final RadioButton item5 = (RadioButton) radioGroup.getChildAt(4);

        if (baseFragment.getCampoOrden().equals(ClienteDAO.NOMBRE)) {
            item1.setChecked(true);
        } else {
            item4.setChecked(true);
        }

        final EditText txtCampoBusquedaAvanzada = (EditText) dialog_busqueda.findViewById(R.id.txtCampoBusquedaAvanzada);
        txtCampoBusquedaAvanzada.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String valor = txtCampoBusquedaAvanzada.getText().toString();
                    if (item1.isChecked()) {
                        ArrayList<Cliente> clientes = clienteDAO.buscarClientesAvanzada(valor, ClienteDAO.NOMBRE, baseFragment.getCampoOrden(),
                                baseFragment.getValorDia(), baseFragment.isValorNuevo(),
                                Util.esSemanaPar());
                        baseFragment.getClienteAdapter().clear();
                        baseFragment.getClienteAdapter().addAll(clientes);
                        baseFragment.getClienteAdapter().notifyDataSetChanged();
                        baseFragment.getIndexList(clientes);
                        dialog_busqueda.dismiss();
                        return true;
                    }
                    if (item2.isChecked()) {
                        ArrayList<Cliente> clientes = clienteDAO.buscarClientesAvanzada(valor, ClienteDAO.TELEFONO, baseFragment.getCampoOrden(),
                                baseFragment.getValorDia(), baseFragment.isValorNuevo(),
                                Util.esSemanaPar());
                        baseFragment.getClienteAdapter().clear();
                        baseFragment.getClienteAdapter().addAll(clientes);
                        baseFragment.getClienteAdapter().notifyDataSetChanged();
                        baseFragment.getIndexList(clientes);
                        dialog_busqueda.dismiss();
                        return true;
                    }
                    if (item3.isChecked()) {
                        ArrayList<Cliente> clientes = clienteDAO.buscarClientesAvanzada(valor, ClienteDAO.RUC, baseFragment.getCampoOrden(),
                                baseFragment.getValorDia(), baseFragment.isValorNuevo(),
                                Util.esSemanaPar());
                        baseFragment.getClienteAdapter().clear();
                        baseFragment.getClienteAdapter().addAll(clientes);
                        baseFragment.getClienteAdapter().notifyDataSetChanged();
                        baseFragment.getIndexList(clientes);
                        dialog_busqueda.dismiss();
                        return true;
                    }
                    if (item4.isChecked()) {
                        ArrayList<Cliente> clientes = clienteDAO.buscarClientesAvanzada(valor, ClienteDAO.CLIENTE_ID, baseFragment.getCampoOrden(),
                                baseFragment.getValorDia(), baseFragment.isValorNuevo(),
                                Util.esSemanaPar());
                        baseFragment.getClienteAdapter().clear();
                        baseFragment.getClienteAdapter().addAll(clientes);
                        baseFragment.getClienteAdapter().notifyDataSetChanged();
                        baseFragment.getIndexList(clientes);

                        dialog_busqueda.dismiss();
                        return true;
                    }

                    if (item5.isChecked()) {
                        ArrayList<Cliente> clientes = clienteDAO.buscarClientesAvanzada(valor, ClienteDAO.DNI, baseFragment.getCampoOrden(),
                                baseFragment.getValorDia(), baseFragment.isValorNuevo(),
                                Util.esSemanaPar());
                        baseFragment.getClienteAdapter().clear();
                        baseFragment.getClienteAdapter().addAll(clientes);
                        baseFragment.getClienteAdapter().notifyDataSetChanged();
                        baseFragment.getIndexList(clientes);
                        dialog_busqueda.dismiss();
                        return true;
                    }
                    dialog_busqueda.dismiss();
                }
                return false;
            }
        });


        dialog_busqueda.show();
    }

    public void ruta() {
        mostrarDateDialog();
    }

    private void mostrarDateDialog() {
        int id = clienteRutaFragment.getFragmentManager().getFragments().indexOf(clienteRutaFragment);
        DateDialog dialog = new DateDialog(id);
        android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        dialog.show(ft, "DatePicker");
    }

    public void filtrar_stock(final StockFragment fragment)
    {
        final Dialog dialog_filtrar = new Dialog(BaseActivity.this);
        dialog_filtrar.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog_filtrar.setContentView(R.layout.dialogo_filtro_stock);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        Window window = dialog_filtrar.getWindow();
        layoutParams.copyFrom(window.getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);

        dialog_filtrar.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        TextView lblCancelar = (TextView) dialog_filtrar.findViewById(R.id.lblCancelar);
        TextView lblAceptar = (TextView) dialog_filtrar.findViewById(R.id.lblAceptar);

        final CheckBox chkNuevos = (CheckBox) dialog_filtrar.findViewById(R.id.chkNuevos);
        if(Nuevos)
        {
            chkNuevos.setChecked(true);
        }
        final CheckBox chkOfertas = (CheckBox) dialog_filtrar.findViewById(R.id.chkOfertas);
        if(Ofertas)
        {
            chkOfertas.setChecked(true);
        }
        final CheckBox chkKits = (CheckBox) dialog_filtrar.findViewById(R.id.chkKits);
        if(Kits)
        {
            chkKits.setChecked(true);
        }
        final CheckBox chkPacks = (CheckBox) dialog_filtrar.findViewById(R.id.chkPacks);
        if(Packs)
        {
            chkPacks.setChecked(true);
        }

        lblCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_filtrar.dismiss();
            }
        });

        lblAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProductoDAO productoDAO = new ProductoDAO(getApplicationContext());

                Nuevos = Ofertas = Kits = Packs = false;

                if(chkNuevos.isChecked())
                {
                    Nuevos = true;
                }

                if(chkOfertas.isChecked())
                {
                    Ofertas = true;
                }

                if(chkKits.isChecked())
                {
                    Kits = true;
                }

                if(chkPacks.isChecked())
                {
                    Packs = true;
                }

                ArrayList<Producto> productos = productoDAO.filtrarProductos(Nuevos,Ofertas,Kits,Packs, ordenamientoSctock,nombreStock);

                fragment.getStockAdapter().clear();
                fragment.getStockAdapter().addAll(productos);
                fragment.getStockAdapter().notifyDataSetChanged();
                fragment.getIndexList(productos, ordenamientoSctock);
                dialog_filtrar.dismiss();
            }
        });

        dialog_filtrar.show();
    }

    public void busqueda_avanzada_stock(final StockFragment fragment)
    {
        final Dialog dialog_busqueda_avanzada = new Dialog(BaseActivity.this);
        dialog_busqueda_avanzada.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog_busqueda_avanzada.setContentView(R.layout.dialogo_busqueda_avanzada_stock);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        Window window = dialog_busqueda_avanzada.getWindow();
        layoutParams.copyFrom(window.getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);

        dialog_busqueda_avanzada.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        final EditText txtDiamtro1 = (EditText)dialog_busqueda_avanzada.findViewById(R.id.txtDiamtro1);
        final EditText txtDiametro2 = (EditText)dialog_busqueda_avanzada.findViewById(R.id.txtDiametro2);
        final EditText txtAltura = (EditText)dialog_busqueda_avanzada.findViewById(R.id.txtAltura);
        final EditText txtRosca = (EditText)dialog_busqueda_avanzada.findViewById(R.id.txtRosca);
        TextView lblCancelar = (TextView) dialog_busqueda_avanzada.findViewById(R.id.lblCancelar);
        TextView lblAceptar = (TextView) dialog_busqueda_avanzada.findViewById(R.id.lblAceptar);
        final Spinner spClase = (Spinner) dialog_busqueda_avanzada.findViewById(R.id.spClase);
        final Spinner spSubClase = (Spinner) dialog_busqueda_avanzada.findViewById(R.id.spSubClase);

        ClaseDAO claseDAO = new ClaseDAO(getApplicationContext());
        ArrayList<Clase> Clases = claseDAO.listarClases();
        ArrayAdapter<Clase> AdapterClases = new ArrayAdapter<Clase>(this, android.R.layout.simple_spinner_item, Clases);
        spClase.setAdapter(AdapterClases);

        SubClaseDAO subClaseDAO = new SubClaseDAO(getApplicationContext());
        ArrayList<SubClase> subClases = subClaseDAO.listarSubClases();
        ArrayAdapter<SubClase> AdapterSubClases = new ArrayAdapter<SubClase>(this, android.R.layout.simple_spinner_item, subClases);
        spSubClase.setAdapter(AdapterSubClases);

        lblCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_busqueda_avanzada.dismiss();
            }
        });


        lblAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductoDAO productoDAO = new ProductoDAO(getApplicationContext());

                if(txtAltura.getText().toString().length()>0)
                {
                    String Code = txtAltura.getText().toString();
                    Code = Code.replace("/", "");
                    Code = Code.replace("-","");
                    Code = Code.replace("(","");
                    Code = Code.replace(")","");
                    Code = Code.replace("*","");
                    txtAltura.setText(Code);
                }

                if(txtRosca.getText().toString().length()>0)
                {
                    String Code = txtRosca.getText().toString();
                    Code = Code.replace("/", "");
                    Code = Code.replace("-","");
                    Code = Code.replace("(","");
                    Code = Code.replace(")","");
                    Code = Code.replace("*","");
                    txtRosca.setText(Code);
                }

                if(txtDiamtro1.getText().toString().length()>0)
                {
                    String Code = txtDiamtro1.getText().toString();
                    Code = Code.replace("/", "");
                    Code = Code.replace("-","");
                    Code = Code.replace("(","");
                    Code = Code.replace(")","");
                    Code = Code.replace("*","");
                    txtDiamtro1.setText(Code);
                }

                if(txtDiametro2.getText().toString().length()>0)
                {
                    String Code = txtDiametro2.getText().toString();
                    Code = Code.replace("/", "");
                    Code = Code.replace("-","");
                    Code = Code.replace("(","");
                    Code = Code.replace(")","");
                    Code = Code.replace("*","");
                    txtDiametro2.setText(Code);
                }

                ArrayList<Producto> productos = productoDAO.buscarProductos(
                        ((Clase)spClase.getSelectedItem()).Codigo,
                        ((SubClase)spSubClase.getSelectedItem()).Codigo,
                        String.valueOf(txtAltura.getText()),
                        String.valueOf(txtRosca.getText()),
                        String.valueOf(txtDiamtro1.getText()),
                        String.valueOf(txtDiametro2.getText()),
                        ordenamientoSctock);

                fragment.getStockAdapter().clear();
                fragment.getStockAdapter().addAll(productos);
                fragment.getStockAdapter().notifyDataSetChanged();
                fragment.getIndexList(productos, ordenamientoSctock);
                dialog_busqueda_avanzada.dismiss();
            }
        });

        dialog_busqueda_avanzada.show();
    }

    public void busqueda_equivalencias_stock(final StockFragment fragment)
    {
        final Dialog dialog_busqueda_equivalencias = new Dialog(BaseActivity.this);
        dialog_busqueda_equivalencias.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog_busqueda_equivalencias.setContentView(R.layout.dialogo_busqueda_equivalencias_stock);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        Window window = dialog_busqueda_equivalencias.getWindow();
        layoutParams.copyFrom(window.getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);

        dialog_busqueda_equivalencias.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        final EditText txtCodigo = (EditText) dialog_busqueda_equivalencias.findViewById(R.id.txtCodigo);

        final ListView lstEquivalencias = (ListView) dialog_busqueda_equivalencias.findViewById(R.id.lstEquivalencias);
        TextView lblCancelar = (TextView) dialog_busqueda_equivalencias.findViewById(R.id.lblCancelar);
        TextView lblAceptar = (TextView) dialog_busqueda_equivalencias.findViewById(R.id.lblAceptar);


        lblCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_busqueda_equivalencias.dismiss();
            }
        });

        lblAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isOnline = existeConexionInternet();
                if (isOnline)
                {
                    if(txtCodigo.getText().toString().length()>0)
                    {
                        String Code = txtCodigo.getText().toString();
                        Code = Code.replace("/", "");
                        Code = Code.replace("-","");
                        Code = Code.replace("(","");
                        Code = Code.replace(")","");
                        Code = Code.replace("*","");
                        txtCodigo.setText(Code);
                    }

                    progressDialogM = new ProgressDialog(BaseActivity.this);
                    progressDialogM.setTitle("Obteniendo Equivalencias");
                    progressDialogM.setMessage("Espere un momento");
                    progressDialogM.setCancelable(false);
                    progressDialogM.show();


                    new Thread() {
                        public void run() {
                            EquivalenciaDAO equivalenciaDAO = new EquivalenciaDAO(getApplicationContext());
                            equivalenciaDAO.eliminarEquivalencias();

                            EquivalenciaService equivalenciaService = new EquivalenciaService(getApplicationContext());
                            equivalenciaService.obtenerEquivalenciasPorNombre(txtCodigo.getText().toString());

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    EquivalenciaDAO equivalenciaDAO = new EquivalenciaDAO(getApplicationContext());

                                    ArrayList<Equivalencia> equivalencias = equivalenciaDAO.buscar(txtCodigo.getText().toString());
                                    EquivalenciaAdapter equivalenciaAdapter = new EquivalenciaAdapter(getApplicationContext(), equivalencias);

                                    lstEquivalencias.setAdapter(equivalenciaAdapter);
                                    equivalenciaAdapter.notifyDataSetChanged();
                                }
                            });

                            Message message = handler.obtainMessage();
                            handlerEquivalencias.sendMessage(message);
                        }
                    }.start();
                }
            }
        });

        dialog_busqueda_equivalencias.show();
    }

    public void ordenar_stock(final StockFragment fragment, String Ordenamiento)
    {
        ProductoDAO productoDAO = new ProductoDAO(getApplicationContext());

        ArrayList<Producto> productos = productoDAO.filtrarProductos(Nuevos,Ofertas,Kits,Packs, Ordenamiento,nombreStock);

        fragment.getStockAdapter().clear();
        fragment.getStockAdapter().addAll(productos);
        fragment.getStockAdapter().notifyDataSetChanged();
        fragment.getIndexList(productos, Ordenamiento);
    }

    private Handler handlerEquivalencias = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            progressDialogM.dismiss();
        }

    };
}