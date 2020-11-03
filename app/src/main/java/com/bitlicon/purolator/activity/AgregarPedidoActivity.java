package com.bitlicon.purolator.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bitlicon.purolator.R;
import com.bitlicon.purolator.adapter.StockPedidoAdapter;
import com.bitlicon.purolator.dao.ConfiguracionDAO;
import com.bitlicon.purolator.dao.DescuentoDAO;
import com.bitlicon.purolator.dao.DetallePedidoDAO;
import com.bitlicon.purolator.dao.LetraPedidoDAO;
import com.bitlicon.purolator.dao.PedidoDAO;
import com.bitlicon.purolator.dao.ProductoDAO;
import com.bitlicon.purolator.dao.VendedorDAO;
import com.bitlicon.purolator.entities.Configuracion;
import com.bitlicon.purolator.entities.Descuento;
import com.bitlicon.purolator.entities.DetallePedido;
import com.bitlicon.purolator.entities.LetraPedido;
import com.bitlicon.purolator.entities.Pedido;
import com.bitlicon.purolator.entities.Producto;
import com.bitlicon.purolator.entities.Vendedor;
import com.bitlicon.purolator.fragment.CondicionPedidoFragment;
import com.bitlicon.purolator.fragment.DetallePedidoFragment;
import com.bitlicon.purolator.services.Config;
import com.bitlicon.purolator.util.AppStatus;
import com.bitlicon.purolator.util.Constantes;
import com.bitlicon.purolator.util.Util;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;


import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class AgregarPedidoActivity extends ControlFragmentActivity {

    private static final int NUM_PAGES = 2;
    private ViewPager viewPagerPedido;
    private ResumenSlidePagerAdapter mResumenSlidePagerAdapter;
    private TextView txtCondicion;
    private VendedorDAO vendedorDAO;
    private Vendedor vendedor = null;
    private Pedido pedido;
    private String nombreStock;
    private String ordenamientoSctock = "";
    public boolean Nuevos = false,Ofertas = false,Kits = false,Packs = false;
    protected StockPedidoAdapter stockPedidoAdapter;
    protected ArrayList<Producto> productos;
    ProductoDAO productoDAO;
    DetallePedidoDAO detallePedidoDAO;
    LetraPedidoDAO letraPedidoDAO;
    PedidoDAO pedidoDAO;
    DescuentoDAO descuentoDAO;
    ProgressDialog progressDialog;
    public DetallePedidoFragment detallePedidoFragment;
    public CondicionPedidoFragment condicionPedidoFragment;
    ConfiguracionDAO configuracionDAO;
    Configuracion configuracion;
    private List<Descuento> descuentos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        pedido = (Pedido) getIntent().getSerializableExtra("pedido");
        setTitle("N°: " + pedido.NumeroPedido);
        setContentView(R.layout.activity_agregar_pedido);

        Util.setIconMenu(R.drawable.ic_back, this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        viewPagerPedido = (ViewPager) findViewById(R.id.viewPagerPedido);
        mResumenSlidePagerAdapter = new ResumenSlidePagerAdapter(getSupportFragmentManager());
        viewPagerPedido.setAdapter(mResumenSlidePagerAdapter);
        viewPagerPedido.setOnPageChangeListener(mResumenSlidePagerAdapter);

        vendedorDAO = new VendedorDAO(getApplicationContext());
        configuracionDAO = new ConfiguracionDAO(getApplicationContext());
        descuentoDAO = new DescuentoDAO(getApplicationContext());

        vendedor = vendedorDAO.buscarVendedor();
        configuracion = configuracionDAO.Obtener();
        descuentos = descuentoDAO.listarDescuentos();

        LinearLayout lnCondicionFranja = (LinearLayout) findViewById(R.id.lnCondicionFranja);
        lnCondicionFranja.setVisibility(View.VISIBLE);
        txtCondicion = (TextView) findViewById(R.id.txtCondicion);
        txtCondicion.setTextColor(getResources().getColor(R.color.celeste));


        LinearLayout lnCondicion = (LinearLayout) findViewById(R.id.lnCondicion);
        lnCondicion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                viewPagerPedido.setCurrentItem(0);
            }
        });

        LinearLayout lnPedido = (LinearLayout) findViewById(R.id.lnPedido);
        lnPedido.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                viewPagerPedido.setCurrentItem(1);
            }
        });
    }

    public void limpiarseleccionado() {
        LinearLayout lnPedidoFranja = (LinearLayout) findViewById(R.id.lnPedidoFranja);
        lnPedidoFranja.setVisibility(View.INVISIBLE);

        LinearLayout lnCondicionFranja = (LinearLayout) findViewById(R.id.lnCondicionFranja);
        lnCondicionFranja.setVisibility(View.INVISIBLE);

        TextView txtPedido = (TextView) findViewById(R.id.txtPedido);
        txtPedido.setTextColor(getResources().getColor(R.color.plomomenu));
        TextView txtCondicion = (TextView) findViewById(R.id.txtCondicion);
        txtCondicion.setTextColor(getResources().getColor(R.color.plomomenu));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(!pedido.Enviado) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.agregar_pedido_activity_actions, menu);
            return super.onCreateOptionsMenu(menu);
        }else
        {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.agregar_pedido_enviado_activity_actions, menu);
            return super.onCreateOptionsMenu(menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;
            case R.id.grabar_finalizado:
                boolean enviar_pedido = true;
                pedidoDAO = new PedidoDAO(getApplicationContext());
                detallePedidoDAO = new DetallePedidoDAO(getApplicationContext());
                letraPedidoDAO = new LetraPedidoDAO(getApplicationContext());

                final Pedido pedidoSerializar = pedidoDAO.obtenerPedido(pedido.iPedido);
                List<DetallePedido> detallePedidoList = detallePedidoDAO.listarDetallePedido(pedido.iPedido);
                List<LetraPedido> letraPedidoList = letraPedidoDAO.listarLetraPedido(pedidoSerializar.NumeroPedido);

                if(enviar_pedido) {
                    if (pedidoSerializar.TerminoVenta != null) {
                        if (pedidoSerializar.TerminoVenta.equals("")) {
                            enviar_pedido = false;
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.termino_venta_requerido), duration);
                            toast.show();
                        }
                    } else {
                        enviar_pedido = false;
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.termino_venta_requerido), duration);
                        toast.show();
                    }
                }
                if(enviar_pedido) {
                    if (detallePedidoList != null) {
                        if (detallePedidoList.size() == 0) {
                            enviar_pedido = false;
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.pedido_requerido), duration);
                            toast.show();
                        }
                    } else {
                        enviar_pedido = false;
                    }
                }

                if(enviar_pedido) {
                    if(pedidoSerializar.TerminoVenta.equals("05") || pedidoSerializar.TerminoVenta.equals("10"))
                    {
                        if (letraPedidoList != null) {
                            if (letraPedidoList.size() == 0) {
                                enviar_pedido = false;
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.letra_requerido), duration);
                                toast.show();
                            }
                        } else {
                            enviar_pedido = false;
                        }
                    }
                }

                boolean isOnline = existeConexionInternet();
                if(isOnline) {
                    if (enviar_pedido) {
                        progressDialog.setMessage(getApplicationContext().getString(R.string.enviando_pedido));
                        progressDialog.show();

                        AsyncHttpClient asyncHttpClient;
                        asyncHttpClient = new AsyncHttpClient();
                        asyncHttpClient.setTimeout(Constantes.TIMEOUT);

                        pedidoSerializar.DetallePedidos = detallePedidoList;
                        pedidoSerializar.LetraPedidos = letraPedidoList;
                        Gson gson = new Gson();
                        String jsonPedido = gson.toJson(pedidoSerializar);
                        StringEntity entity = null;

                        String url = Config.SERVICE_URL + Config.WS_PEDIDO + Config.METODO_REGISTRARPEDIDO;
                        try {
                            entity = new StringEntity(jsonPedido, "UTF-8");
                            //entity.setContentEncoding("UTF-8");
                            asyncHttpClient.post(getApplicationContext(), url, entity, Constantes.APPLICATION_JSON, new JsonHttpResponseHandler() {

                                @Override
                                public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
                                    try {
                                        String Item1 = jsonObject.getString("Item1");
                                        String Item2 = jsonObject.getString("Item2");
                                        if (Item1.equals("OK")) {
                                            pedidoDAO.actualizarPedidoEnviado(Item2, pedidoSerializar.iPedido);
                                            detallePedidoDAO.actualizarDetallePedidoEnviado(Item2, pedidoSerializar.iPedido);
                                            letraPedidoDAO.actualizarLetraPedidoEnviado(Item2, pedidoSerializar.iPedido);
                                            progressDialog.hide();
                                            onBackPressed();
                                        } else {
                                            int duration = Toast.LENGTH_SHORT;
                                            Toast toast = Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.error_envio_pedido), duration);
                                            toast.show();
                                            progressDialog.hide();
                                        }
                                    } catch (Exception ex) {

                                    }
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                    int duration = Toast.LENGTH_SHORT;
                                    Toast toast = Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.error_envio_pedido), duration);
                                    toast.show();
                                    progressDialog.hide();
                                }
                            });
                        } catch (Exception ex) {

                        }
                    }
                }else {
                    Toast.makeText(this, getString(R.string.internet_pedido_offline), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.anular_pedido:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Confirmación");
                builder.setMessage("¿Está seguro de anular el pedido?");
                builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        pedidoDAO = new PedidoDAO(getApplicationContext());
                        pedidoDAO.eliminarPedido(pedido.iPedido);
                        detallePedidoDAO = new DetallePedidoDAO(getApplicationContext());
                        detallePedidoDAO.eliminarDetallePedidoPorPedido(pedido.iPedido);
                        letraPedidoDAO = new LetraPedidoDAO(getApplicationContext());
                        letraPedidoDAO.eliminarLetraPedidoPorPedido(pedido.iPedido);
                        onBackPressed();
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
                break;
            case R.id.nuevo_producto:
                agregarNuevoProducto();
                break;
            case R.id.nuevo_pedido:
                Pedido pedidoNuevo = new Pedido();
                pedidoDAO = new PedidoDAO(getApplicationContext());
                descuentoDAO = new DescuentoDAO(getApplicationContext());
                detallePedidoDAO = new DetallePedidoDAO(getApplicationContext());
                letraPedidoDAO = new LetraPedidoDAO(getApplicationContext());
                productoDAO = new ProductoDAO(getApplicationContext());
                descuentos = descuentoDAO.listarDescuentos();
                Pedido pedidoACopiar = pedidoDAO.obtenerPedido(pedido.iPedido);
                List<DetallePedido> detallePedidoAcopiarList = detallePedidoDAO.listarDetallePedidoPorNumero(pedidoACopiar.NumeroPedido);
                List<LetraPedido> letraPedidoAcopiarList = letraPedidoDAO.listarLetraPedido(pedidoACopiar.NumeroPedido);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
                String fechaCreacionPedido = sdf.format(new Date());
                pedidoNuevo.FechaCreacionPedido = fechaCreacionPedido;
                pedidoNuevo.ClienteID = pedidoACopiar.ClienteID;
                pedidoNuevo.VendedorID = pedidoACopiar.VendedorID;
                pedidoNuevo.TerminoVenta = pedidoACopiar.TerminoVenta;
                pedidoNuevo.Observacion = pedidoACopiar.Observacion;
                pedidoNuevo.Descuento1 = pedidoACopiar.Descuento1;
                pedidoNuevo.Descuento2 = pedidoACopiar.Descuento2;
                pedidoNuevo.Descuento3 = pedidoACopiar.Descuento3;
                pedidoNuevo.Descuento4 = pedidoACopiar.Descuento4;
                pedidoNuevo.Descuento5 = pedidoACopiar.Descuento5;
                pedidoNuevo.TotalDescuento = 0;
                pedidoNuevo.TotalImpuestos = 0;
                pedidoNuevo.TotalImporte = 0;
                pedidoNuevo.iPedido = pedidoDAO.registrarPedido(pedidoNuevo);
                pedidoNuevo.NumeroPedido = String.valueOf(pedidoNuevo.iPedido);
                pedidoDAO.actualizarNumeroPedido(String.valueOf(pedidoNuevo.iPedido), pedidoNuevo.iPedido);
                int cantidad_detalle_pedido = detallePedidoAcopiarList.size();
                int cantidad_letra_pedido = letraPedidoAcopiarList.size();
                DetallePedido detallePedido = null;
                DetallePedido DetallePedidoACopiar = null;
                LetraPedido letraPedido = null;
                LetraPedido letraPedidoACopiar = null;
                Producto productoEncontrado = null;
                for(int i=0;i<cantidad_detalle_pedido;i++)
                {
                    detallePedido = new DetallePedido();
                    DetallePedidoACopiar = detallePedidoAcopiarList.get(i);
                    productoEncontrado = productoDAO.obtener(DetallePedidoACopiar.ProductoID);
                    if(productoEncontrado!=null) {
                        detallePedido.Cantidad = DetallePedidoACopiar.Cantidad;
                        detallePedido.ProductoID = DetallePedidoACopiar.ProductoID;
                        if(productoEncontrado.PrecioDolares!=0) {
                            detallePedido.Precio = productoEncontrado.PrecioDolares;
                            detallePedido.iPedido = pedidoNuevo.iPedido;
                            detallePedido.NumeroPedido = pedidoNuevo.NumeroPedido;
                            detallePedido.Descuento1 = (100 * 100 - (100 - productoEncontrado.Descuento1) * (100 - productoEncontrado.Descuento2)) / 100 * 1.00;
                            detallePedido.Descuento11 = productoEncontrado.Descuento1;
                            detallePedido.Descuento12 = productoEncontrado.Descuento2;
                            detallePedido.Descuento2 = productoEncontrado.Descuento5;
                            detallePedidoDAO.registrarDetallePedido(detallePedido);
                        }
                    }
                }
                List<DetallePedido> detallePedidosNuevos = detallePedidoDAO.listarDetallePedido(pedidoNuevo.iPedido);
                DetallePedido detallePedidoNuevo;
                double subtotal = 0;
                int cantidadDetallePedido = detallePedidosNuevos.size();
                double TotalGeneral = 0;
                for(int i=0; i<cantidadDetallePedido; i++)
                {
                    detallePedidoNuevo = detallePedidosNuevos.get(i);
                    subtotal = detallePedidoNuevo.Cantidad * ( (detallePedidoNuevo.Precio) * ( (100 - detallePedidoNuevo.Descuento1) / 100 * 1.00) * ((100-detallePedidoNuevo.Descuento2) / 100 * 1.00));
                    TotalGeneral =  TotalGeneral + subtotal;
                }
                pedidoDAO.actualizarTotalImporte(TotalGeneral, pedidoNuevo.iPedido);
                pedidoDAO.actualizarTotalImporte(TotalGeneral, pedidoNuevo.iPedido);
                pedidoNuevo = pedidoDAO.obtenerPedido(pedidoNuevo.iPedido);
                double TotalDescuentos = 0;
                double TotalImpuesto = 0;
                double igv = configuracion.IGV;
                TotalDescuentos = obtenerTotalDescuento(pedidoNuevo.Descuento1, pedidoNuevo.Descuento2, pedidoNuevo.Descuento3, pedidoNuevo.Descuento4, pedidoNuevo.Descuento5,pedidoNuevo);
                pedidoDAO.actualizarTotalDescuento(TotalDescuentos, pedidoNuevo.iPedido);

                double totalGeneralSinImpuestos = TotalGeneral - TotalDescuentos;
                TotalImpuesto = (double) totalGeneralSinImpuestos * igv / 100;
                pedidoDAO.actualizarTotalImpuestos(TotalImpuesto, pedidoNuevo.iPedido);

                for(int i=0;i<cantidad_letra_pedido;i++)
                {
                    letraPedido = new LetraPedido();
                    letraPedidoACopiar = letraPedidoAcopiarList.get(i);
                    letraPedido.Dia = letraPedidoACopiar.Dia;
                    letraPedido.NumeroPedido = pedidoNuevo.NumeroPedido;
                    letraPedido.iPedido = pedidoNuevo.iPedido;
                    letraPedido.Numero = letraPedidoACopiar.Numero;
                    letraPedido.Picker = letraPedidoACopiar.Picker;
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DATE, letraPedido.Dia);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    letraPedido.Fecha = dateFormat.format(cal.getTime());
                    letraPedidoDAO.registrarLetraPedido(letraPedido);
                }
                Intent intent = new Intent(getApplicationContext(), AgregarPedidoActivity.class);
                intent.putExtra("pedido", pedidoNuevo);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean existeConexionInternet() {
        return AppStatus.getInstance(this).isOnline();
    }

    public class ResumenSlidePagerAdapter extends FragmentStatePagerAdapter implements ViewPager.OnPageChangeListener {
        public ResumenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                condicionPedidoFragment = CondicionPedidoFragment.nuevaInstancia(pedido);
                return condicionPedidoFragment;
            } else if (position == 1) {
                detallePedidoFragment = DetallePedidoFragment.nuevaInstancia(pedido);
                return detallePedidoFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageSelected(int position) {
            LinearLayout lnfranja = null;
            TextView txtTexto = null;
            limpiarseleccionado();
            switch (position) {
                case 0:
                    lnfranja = (LinearLayout) findViewById(R.id.lnCondicionFranja);
                    txtTexto = (TextView) findViewById(R.id.txtCondicion);
                    break;
                case 1:
                    lnfranja = (LinearLayout) findViewById(R.id.lnPedidoFranja);
                    txtTexto = (TextView) findViewById(R.id.txtPedido);
                    break;
            }
            lnfranja.setVisibility(View.VISIBLE);
            txtTexto.setTextColor(getResources().getColor(R.color.celeste));
        }
    }
    EditText txtCodigo;
    public void agregarNuevoProducto()
    {
        final Dialog dialog_busqueda_producto = new Dialog(AgregarPedidoActivity.this);
        dialog_busqueda_producto.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog_busqueda_producto.setContentView(R.layout.dialogo_busqueda_productos);

        final TextView lblBuscar = (TextView) dialog_busqueda_producto.findViewById(R.id.lblBuscar);
        txtCodigo = (EditText) dialog_busqueda_producto.findViewById(R.id.txtCodigo);


        final TextView lblTop = (TextView) dialog_busqueda_producto.findViewById(R.id.lblTop);
        final TextView lblPromocion = (TextView) dialog_busqueda_producto.findViewById(R.id.lblPromocion);
        final TextView lblNuevo = (TextView) dialog_busqueda_producto.findViewById(R.id.lblNuevo);
        final TextView lblKits = (TextView) dialog_busqueda_producto.findViewById(R.id.lblKits);
        final TextView lblPacks = (TextView) dialog_busqueda_producto.findViewById(R.id.lblPacks);

        if(configuracion!=null)
        {
            lblTop.setText("Top " + configuracion.TopProductos);
        }

        final TextView lblAF = (TextView) dialog_busqueda_producto.findViewById(R.id.lblAF);
        final TextView lblAFP = (TextView) dialog_busqueda_producto.findViewById(R.id.lblAFP);
        final TextView lblFCO = (TextView) dialog_busqueda_producto.findViewById(R.id.lblFCO);
        final TextView lblPER = (TextView) dialog_busqueda_producto.findViewById(R.id.lblPER);

        final TextView lblFCA = (TextView) dialog_busqueda_producto.findViewById(R.id.lblFCA);
        final TextView lblEP = (TextView) dialog_busqueda_producto.findViewById(R.id.lblEP);
        final TextView lblPC = (TextView) dialog_busqueda_producto.findViewById(R.id.lblPC);
        final TextView lblPP = (TextView) dialog_busqueda_producto.findViewById(R.id.lblPP);

        final TextView lblPFC = (TextView) dialog_busqueda_producto.findViewById(R.id.lblPFC);
        final TextView lblFF = (TextView) dialog_busqueda_producto.findViewById(R.id.lblFF);
        final TextView lblFL = (TextView) dialog_busqueda_producto.findViewById(R.id.lblFL);
        final TextView lblFA = (TextView) dialog_busqueda_producto.findViewById(R.id.lblFA);

        final ListView lstProductos = (ListView) dialog_busqueda_producto.findViewById(R.id.lstProductos);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        Window window = dialog_busqueda_producto.getWindow();
        layoutParams.copyFrom(window.getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);
        productos = new ArrayList<>();
        stockPedidoAdapter = new StockPedidoAdapter(getApplicationContext(), productos);
        lstProductos.setAdapter(stockPedidoAdapter);

        dialog_busqueda_producto.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        dialog_busqueda_producto.show();

        lblAF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtCodigo.setText("AF");
            }
        });
        lblAFP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtCodigo.setText("AFP");
            }
        });
        lblFCO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtCodigo.setText("FCO");
            }
        });
        lblPER.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtCodigo.setText("PER");
            }
        });

        lblFCA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtCodigo.setText("FCA");
            }
        });
        lblEP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtCodigo.setText("EP");
            }
        });
        lblPC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtCodigo.setText("PC");
            }
        });
        lblPP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtCodigo.setText("PP");
            }
        });

        lblPFC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtCodigo.setText("PFC");
            }
        });
        lblFF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtCodigo.setText("FF");
            }
        });
        lblFL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtCodigo.setText("FL");
            }
        });
        lblFA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtCodigo.setText("FA");
            }
        });

        lblBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txtCodigo.getText().toString().length() > 0) {
                    String Code = txtCodigo.getText().toString();
                    Code = Code.replace("/", "");
                    Code = Code.replace("-", "");
                    Code = Code.replace("(", "");
                    Code = Code.replace(")", "");
                    Code = Code.replace("*", "");
                    txtCodigo.setText(Code);
                }

                nombreStock = txtCodigo.getText().toString();
                productoDAO = new ProductoDAO(getApplicationContext());

                pedidoDAO = new PedidoDAO(getApplicationContext());
                Pedido pedidoAux = pedidoDAO.obtenerPedido(pedido.iPedido);


                if(pedidoAux.VendedorID.equals(vendedor.Linea1)){
                    productos = productoDAO.buscarNombrePurolator(nombreStock,
                            ordenamientoSctock, Nuevos, Ofertas, Kits, Packs);
                }else {

                    productos = productoDAO.buscarNombreFiltech(nombreStock,
                            ordenamientoSctock, Nuevos, Ofertas, Kits, Packs);
                }


                stockPedidoAdapter.clear();
                stockPedidoAdapter.addAll(productos);
                stockPedidoAdapter.notifyDataSetChanged();

            }
        });
        lblPromocion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtCodigo.setText("");
                if (txtCodigo.getText().toString().length() > 0) {
                    String Code = txtCodigo.getText().toString();
                    Code = Code.replace("/", "");
                    Code = Code.replace("-", "");
                    Code = Code.replace("(", "");
                    Code = Code.replace(")", "");
                    Code = Code.replace("*", "");
                    txtCodigo.setText(Code);
                }

                pedidoDAO = new PedidoDAO(getApplicationContext());

                nombreStock = txtCodigo.getText().toString();
                productoDAO = new ProductoDAO(getApplicationContext());

                Pedido pedidoAux = pedidoDAO.obtenerPedido(pedido.iPedido);

                if(pedidoAux.VendedorID.equals(vendedor.Linea1)){
                    productos = productoDAO.buscarNombrePurolator(nombreStock,
                            ordenamientoSctock, false, true, false, false);
                }else {
                    productos = productoDAO.buscarNombreFiltech(nombreStock,
                            ordenamientoSctock, false, true, false, false);

                }

                stockPedidoAdapter.clear();
                stockPedidoAdapter.addAll(productos);
                stockPedidoAdapter.notifyDataSetChanged();
            }
        });

        lblNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtCodigo.setText("");
                if (txtCodigo.getText().toString().length() > 0) {
                    String Code = txtCodigo.getText().toString();
                    Code = Code.replace("/", "");
                    Code = Code.replace("-", "");
                    Code = Code.replace("(", "");
                    Code = Code.replace(")", "");
                    Code = Code.replace("*", "");
                    txtCodigo.setText(Code);
                }
                pedidoDAO = new PedidoDAO(getApplicationContext());
                nombreStock = txtCodigo.getText().toString();
                productoDAO = new ProductoDAO(getApplicationContext());

                Pedido pedidoAux = pedidoDAO.obtenerPedido(pedido.iPedido);
                if(pedidoAux.VendedorID.equals(vendedor.Linea1)){
                    productos = productoDAO.buscarNombrePurolator(nombreStock,
                            ordenamientoSctock, true, false, false, false);
                }else {
                    productos = productoDAO.buscarNombreFiltech(nombreStock,
                            ordenamientoSctock, true, false, false, false);

                }

                stockPedidoAdapter.clear();
                stockPedidoAdapter.addAll(productos);
                stockPedidoAdapter.notifyDataSetChanged();
            }
        });

        lblKits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtCodigo.setText("");
                if (txtCodigo.getText().toString().length() > 0) {
                    String Code = txtCodigo.getText().toString();
                    Code = Code.replace("/", "");
                    Code = Code.replace("-", "");
                    Code = Code.replace("(", "");
                    Code = Code.replace(")", "");
                    Code = Code.replace("*", "");
                    txtCodigo.setText(Code);
                }

                pedidoDAO = new PedidoDAO(getApplicationContext());
                nombreStock = txtCodigo.getText().toString();
                productoDAO = new ProductoDAO(getApplicationContext());

                Pedido pedidoAux = pedidoDAO.obtenerPedido(pedido.iPedido);
                if(pedidoAux.VendedorID.equals(vendedor.Linea1)){
                    productos = productoDAO.buscarNombrePurolator(nombreStock,
                            ordenamientoSctock, false, false, true, false);
                }else {
                    productos = productoDAO.buscarNombreFiltech(nombreStock,
                            ordenamientoSctock, false, false, true, false);

                }

                stockPedidoAdapter.clear();
                stockPedidoAdapter.addAll(productos);
                stockPedidoAdapter.notifyDataSetChanged();
            }
        });


        lblPacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtCodigo.setText("");
                if (txtCodigo.getText().toString().length() > 0) {
                    String Code = txtCodigo.getText().toString();
                    Code = Code.replace("/", "");
                    Code = Code.replace("-", "");
                    Code = Code.replace("(", "");
                    Code = Code.replace(")", "");
                    Code = Code.replace("*", "");
                    txtCodigo.setText(Code);
                }
                pedidoDAO = new PedidoDAO(getApplicationContext());
                nombreStock = txtCodigo.getText().toString();
                productoDAO = new ProductoDAO(getApplicationContext());

                Pedido pedidoAux = pedidoDAO.obtenerPedido(pedido.iPedido);
                if(pedidoAux.VendedorID.equals(vendedor.Linea1)){
                    productos = productoDAO.buscarNombrePurolator(nombreStock,
                            ordenamientoSctock, false, false, false, true);
                }else {
                    productos = productoDAO.buscarNombreFiltech(nombreStock,
                            ordenamientoSctock, false, false, false, true);

                }

                stockPedidoAdapter.clear();
                stockPedidoAdapter.addAll(productos);
                stockPedidoAdapter.notifyDataSetChanged();
            }
        });

        lblTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtCodigo.setText("");

                productoDAO = new ProductoDAO(getApplicationContext());
                pedidoDAO = new PedidoDAO(getApplicationContext());
                Pedido pedidoAux = pedidoDAO.obtenerPedido(pedido.iPedido);
                if(pedidoAux.VendedorID.equals(vendedor.Linea1)){
                    productos = productoDAO.listarTopProductosPuro(configuracion.TopProductos);
                }else {
                    productos = productoDAO.listarTopProductosFi(configuracion.TopProductos);

                }

                stockPedidoAdapter.clear();
                stockPedidoAdapter.addAll(productos);
                stockPedidoAdapter.notifyDataSetChanged();
            }
        });

        lstProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Producto producto = (Producto) parent.getItemAtPosition(position);

                final Dialog dialog_producto_cantidad = new Dialog(AgregarPedidoActivity.this);
                dialog_producto_cantidad.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                dialog_producto_cantidad.setContentView(R.layout.dialogo_producto_cantidad);

                final TextView lblCancelar = (TextView) dialog_producto_cantidad.findViewById(R.id.lblCancelar);
                final TextView lblAgregar = (TextView) dialog_producto_cantidad.findViewById(R.id.lblAgregar);
                final EditText txtCantidad = (EditText) dialog_producto_cantidad.findViewById(R.id.txtCantidad);
                final RadioButton radio_precio_lista = (RadioButton) dialog_producto_cantidad.findViewById(R.id.radio_precio_lista);
                final RadioButton radio_precio_oficina = (RadioButton) dialog_producto_cantidad.findViewById(R.id.radio_precio_oficina);
                final RadioButton radio_precio_lima = (RadioButton) dialog_producto_cantidad.findViewById(R.id.radio_precio_lima);
                final RadioButton radio_precio_norte = (RadioButton) dialog_producto_cantidad.findViewById(R.id.radio_precio_norte);
                final CheckBox cbDescuento1 = (CheckBox) dialog_producto_cantidad.findViewById(R.id.cbDescuento1);
                final CheckBox cbDescuento2 = (CheckBox) dialog_producto_cantidad.findViewById(R.id.cbDescuento2);
                final CheckBox cbDescuento3 = (CheckBox) dialog_producto_cantidad.findViewById(R.id.cbDescuento3);

                cbDescuento1.setText("Descuento ("+producto.Descuento1 + "%)");
                cbDescuento2.setText("Descuento ("+producto.Descuento2 + "%)");
                cbDescuento3.setText("Descuento ("+producto.Descuento5 + "%)");

                lblCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_producto_cantidad.hide();
                    }
                });

                lblAgregar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String cantidadString = txtCantidad.getText().toString().trim();

                        if(cantidadString.length() == 0)
                        {
                            Toast.makeText(getApplicationContext(), R.string.icantidad, Toast.LENGTH_LONG).show();

                        }else {

                            int cantidad = Integer.parseInt(txtCantidad.getText().toString());
                            String ProductoID = producto.ProductoID;
                            double Precio = 0;
                            if (radio_precio_lista.isChecked()) {
                                Precio = producto.PrecioDolares; // Precio Lista
                            } else if (radio_precio_oficina.isChecked()) {
                                Precio = producto.PrecioOficina;
                            } else if (radio_precio_lima.isChecked()) {
                                Precio = producto.PrecioLima;
                            } else if (radio_precio_norte.isChecked()) {
                                Precio = producto.PrecioNorte;
                            }
                            if(Precio<=0)
                            {
                                Toast.makeText(getApplicationContext(), R.string.precio_mayor_cero, Toast.LENGTH_LONG).show();
                            }else {
                                detallePedidoDAO = new DetallePedidoDAO(getApplicationContext());
                                DetallePedido detallePedido = new DetallePedido();
                                detallePedido.Cantidad = cantidad;
                                detallePedido.ProductoID = ProductoID;
                                detallePedido.Precio = Precio;
                                detallePedido.iPedido = pedido.iPedido;
                                if(!cbDescuento1.isChecked()){
                                    producto.Descuento1 = 0;
                                }
                                if(!cbDescuento2.isChecked()){
                                    producto.Descuento2 = 0;
                                }
                                if(!cbDescuento3.isChecked()){
                                    producto.Descuento5 = 0;
                                }
                                detallePedido.Descuento1 = (100 * 100 - (100 - producto.Descuento1) * (100 - producto.Descuento2)) / 100 * 1.00;
                                detallePedido.Descuento2 = producto.Descuento5;
                                detallePedido.Descuento11 = producto.Descuento1;
                                detallePedido.Descuento12 = producto.Descuento2;
                                boolean encontrado = detallePedidoDAO.obtenerDetallePedido(detallePedido);
                                boolean registro = false;
                                if (encontrado) {
                                    registro = detallePedidoDAO.actualizarDetallePedido(detallePedido);
                                } else {
                                    registro = detallePedidoDAO.registrarDetallePedido(detallePedido);
                                }
                                if (registro) {
                                    dialog_producto_cantidad.hide();
                                    dialog_busqueda_producto.hide();
                                    if (detallePedidoFragment != null) {
                                        detallePedidoFragment.onResume();
                                    }
                                    if(condicionPedidoFragment != null) {
                                        condicionPedidoFragment.onResume();
                                    }
                                } else {

                                }
                            }
                        }
                    }
                });

                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                Window window = dialog_producto_cantidad.getWindow();
                layoutParams.copyFrom(window.getAttributes());
                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                window.setAttributes(layoutParams);

                dialog_producto_cantidad.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                dialog_producto_cantidad.show();

            }
        });

        txtCodigo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String DIALOG_TEXT = "Hable por favor";
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, DIALOG_TEXT);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, REQUEST_CODE);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-PE");
                startActivityForResult(intent, 1);
                return false;
            }
        });
    }
    int REQUEST_CODE = 1;
    String resultSpeech = "";
    @Override
    protected void onActivityResult(int requestCode, int resultcode, Intent intent) {
        super.onActivityResult(requestCode, resultcode, intent);
        ArrayList<String> speech;
        if (resultcode == RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                speech = intent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                resultSpeech = speech.get(0);
                txtCodigo.setText(resultSpeech);
                //you can set resultSpeech to your EditText or TextView
            }
        }
    }


    public double obtenerTotalDescuento(boolean desc1, boolean desc2, boolean desc3,boolean desc4,boolean desc5, Pedido pedidoNuevo)
    {
        int desc1int = 0,desc2int = 0,desc3int = 0,desc4int = 0, desc5int = 0;
        if(desc1)
        {
            String descuento = descuentos.get(0).Descripcion.replace("%", "");
            try {
                desc1int = Integer.parseInt(descuento);
            }catch (NumberFormatException e) {
                desc1int = 0;
            }
        }
        if(desc2)
        {
            String descuento = descuentos.get(1).Descripcion.replace("%", "");
            try {
                desc2int = Integer.parseInt(descuento);
            }catch (NumberFormatException e) {
                desc2int = 0;
            }
        }
        if(desc3)
        {
            String descuento = descuentos.get(2).Descripcion.replace("%", "");
            try {
                desc3int = Integer.parseInt(descuento);
            }catch (NumberFormatException e) {
                desc3int = 0;
            }
        }
        if(desc4)
        {
            String descuento = descuentos.get(3).Descripcion.replace("%", "");
            try {
                desc4int = Integer.parseInt(descuento);
            }catch (NumberFormatException e) {
                desc4int = 0;
            }
        }
        if(desc5)
        {
            String descuento = descuentos.get(4).Descripcion.replace("%", "");
            try {
                desc5int = Integer.parseInt(descuento);
            }catch (NumberFormatException e) {
                desc5int = 0;
            }
        }
        double TotalFinal = (double) pedidoNuevo.TotalImporte * ((double)(100-desc1int) / 100) * ((double)(100 -desc2int)/100) * ((double)(100 -desc3int)/100) * ((double)(100 -desc4int) /100) * ((double)(100 -desc5int)/100);
        double TotalDescuentos = pedidoNuevo.TotalImporte - TotalFinal;
        return TotalDescuentos;
    }

}
