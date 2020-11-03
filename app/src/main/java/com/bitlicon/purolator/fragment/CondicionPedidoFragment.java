package com.bitlicon.purolator.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bitlicon.purolator.R;
import com.bitlicon.purolator.activity.AgregarPedidoActivity;
import com.bitlicon.purolator.activity.PedidoActivity;
import com.bitlicon.purolator.activity.SeleccionDiaFacturaActivity;
import com.bitlicon.purolator.adapter.LetraPedidoAdapter;
import com.bitlicon.purolator.dao.ConfiguracionDAO;
import com.bitlicon.purolator.dao.DescuentoDAO;
import com.bitlicon.purolator.dao.DetallePedidoDAO;
import com.bitlicon.purolator.dao.LetraPedidoDAO;
import com.bitlicon.purolator.dao.PedidoDAO;
import com.bitlicon.purolator.dao.VendedorDAO;
import com.bitlicon.purolator.entities.Configuracion;
import com.bitlicon.purolator.entities.Descuento;
import com.bitlicon.purolator.entities.DetallePedido;
import com.bitlicon.purolator.entities.LetraPedido;
import com.bitlicon.purolator.entities.Pedido;
import com.bitlicon.purolator.entities.Vendedor;
import com.bitlicon.purolator.lib.SessionManager;
import com.bitlicon.purolator.util.Util;

import java.util.ArrayList;
import java.util.List;

public class CondicionPedidoFragment extends Fragment {

    private View rootView;
    private RadioButton radio_contado_adelantado;
    private RadioButton radio_contado_contra_entrega;
    private RadioButton radio_credito_letras;
    private RadioButton radio_credito_facturas;
    private RadioButton radio_otros;
    private RadioButton rbPurolator;
    private RadioButton rbFiltech;
    private CheckBox checkbox_descuento1;
    private CheckBox checkbox_descuento2;
    private CheckBox checkbox_descuento3;
    private CheckBox checkbox_descuento4;
    private CheckBox checkbox_descuento5;

    private EditText txtObservaciones;
    private Pedido pedido;
    private SessionManager sessionManager;
    private VendedorDAO vendedorDAO;
    private Vendedor vendedor;
    private DescuentoDAO descuentoDAO;
    private LetraPedidoDAO letraPedidoDAO;
    private PedidoDAO pedidoDAO;
    private List<Descuento> descuentos;
    private ConfiguracionDAO configuracionDAO;
    private Configuracion configuracion;
    private List<DetallePedido> detallePedidos;
    private DetallePedidoDAO detallePedidoDAO;

    public static CondicionPedidoFragment nuevaInstancia(Pedido pedido) {
        CondicionPedidoFragment h = new CondicionPedidoFragment();
        h.pedido = pedido;
        return h;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!pedido.Enviado) {
            detallePedidoDAO = new DetallePedidoDAO(getActivity().getApplicationContext());
            detallePedidos = detallePedidoDAO.listarDetallePedido(pedido.iPedido);
            int cantidadDetallePedidos = detallePedidos.size();
            if (cantidadDetallePedidos > 0) {
                rbFiltech.setEnabled(false);
                rbPurolator.setEnabled(false);
            } else {
                rbFiltech.setEnabled(true);
                rbPurolator.setEnabled(true);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vendedorDAO = new VendedorDAO(getActivity().getApplicationContext());
        descuentoDAO = new DescuentoDAO(getActivity().getApplicationContext());
        pedidoDAO = new PedidoDAO(getActivity().getApplicationContext());
        letraPedidoDAO = new LetraPedidoDAO(getActivity().getApplicationContext());
        configuracionDAO = new ConfiguracionDAO(getActivity().getApplicationContext());

        vendedor = vendedorDAO.buscarVendedor();
        configuracion = configuracionDAO.Obtener();
        descuentos = descuentoDAO.listarDescuentos();

        rootView = inflater.inflate(R.layout.fragment_condicion, container, false);

        sessionManager = new SessionManager(getActivity().getApplicationContext());

        rbPurolator = (RadioButton) rootView.findViewById(R.id.rbPurolator);
        rbFiltech = (RadioButton) rootView.findViewById(R.id.rbFiltech);

        radio_contado_adelantado = (RadioButton) rootView.findViewById(R.id.radio_contado_adelantado);
        radio_credito_letras = (RadioButton) rootView.findViewById(R.id.radio_credito_letras);
        radio_credito_facturas = (RadioButton) rootView.findViewById(R.id.radio_credito_facturas);
        radio_contado_contra_entrega = (RadioButton) rootView.findViewById(R.id.radio_contado_contra_entrega);
        radio_otros = (RadioButton) rootView.findViewById(R.id.radio_otros);

        checkbox_descuento1 = (CheckBox) rootView.findViewById(R.id.checkbox_descuento1);
        checkbox_descuento2 = (CheckBox) rootView.findViewById(R.id.checkbox_descuento2);
        checkbox_descuento3 = (CheckBox) rootView.findViewById(R.id.checkbox_descuento3);
        checkbox_descuento4 = (CheckBox) rootView.findViewById(R.id.checkbox_descuento4);
        checkbox_descuento5 = (CheckBox) rootView.findViewById(R.id.checkbox_descuento5);

        txtObservaciones = (EditText) rootView.findViewById(R.id.txtObservaciones);


        cargarDescuentos();
        cargarTerminoVenta();
        cargarVendedor();
        cargarObservacion();

        if (!pedido.Enviado) {
            actualizarTerminosVentaPedido();
            actualizarLineaVendedor();
            actualizarObservaciones();

            actualizarDescuento1();
            actualizarDescuento2();
            actualizarDescuento3();
            actualizarDescuento4();
            actualizarDescuento5();
        } else {
            if (pedido.TerminoVenta.equals("10")) {
                radio_credito_letras.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        List<LetraPedido> letraPedidos = letraPedidoDAO.listarLetraPedido(pedido.NumeroPedido);
                        mostrarLetras(letraPedidos);
                    }
                });
            } else if (pedido.TerminoVenta.equals("05")) {
                radio_credito_facturas.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<LetraPedido> letraPedidos = letraPedidoDAO.listarLetraPedido(pedido.NumeroPedido);
                        mostrarLetras(letraPedidos);
                    }
                });
            }
        }
        desactivarModificacion();

        return rootView;
    }

    public void mostrarLetras(List<LetraPedido> letraPedidos)
    {
        final Dialog dialogo_letras = new Dialog(getActivity());
        dialogo_letras.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogo_letras.setContentView(R.layout.dialogo_letras);

        final ListView lstLetras = (ListView) dialogo_letras.findViewById(R.id.lstLetras);
        TextView lblcerrar = (TextView)  dialogo_letras.findViewById(R.id.lblcerrar);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        Window window = dialogo_letras.getWindow();
        layoutParams.copyFrom(window.getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);

        LetraPedidoAdapter letraPedidoAdapter = new LetraPedidoAdapter(getActivity().getApplicationContext(),
                letraPedidos);
        lstLetras.setAdapter(letraPedidoAdapter);
        dialogo_letras.show();

        lblcerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogo_letras.hide();
            }
        });

    }
    public void actualizarTerminosVentaPedido() {


        radio_contado_adelantado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pedidoDAO.actualizarTerminoVenta("02", pedido.iPedido);
            }
        });

        radio_contado_contra_entrega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pedidoDAO.actualizarTerminoVenta("01", pedido.iPedido);
            }
        });

        radio_credito_letras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pedido = pedidoDAO.obtenerPedido(pedido.iPedido);

                if (pedido.TerminoVenta.equals("10")) {

                } else {
                    pedidoDAO.actualizarTerminoVenta("10", pedido.iPedido);
                    pedido.TerminoVenta = "10";
                    letraPedidoDAO.eliminarPorPedido(pedido.NumeroPedido);
                }

                Intent intent = new Intent(getActivity(), SeleccionDiaFacturaActivity.class);
                intent.putExtra("pedido", pedido);
                startActivity(intent);

            }
        });

        radio_credito_facturas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pedido = pedidoDAO.obtenerPedido(pedido.iPedido);
                if (pedido.TerminoVenta.equals("05")) {

                } else {
                    pedidoDAO.actualizarTerminoVenta("05", pedido.iPedido);
                    pedido.TerminoVenta = "05";
                    letraPedidoDAO.eliminarPorPedido(pedido.NumeroPedido);
                }
                Intent intent = new Intent(getActivity(), SeleccionDiaFacturaActivity.class);
                intent.putExtra("pedido", pedido);
                startActivity(intent);
            }
        });

        radio_otros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pedidoDAO.actualizarTerminoVenta("00", pedido.iPedido);
            }
        });
    }

    public void actualizarLineaVendedor() {
        rbPurolator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pedidoDAO.actualizarVendedor(vendedor.Linea1, pedido.iPedido);
            }
        });
        rbFiltech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pedidoDAO.actualizarVendedor(vendedor.Linea2, pedido.iPedido);
            }
        });
    }

    public void actualizarObservaciones() {
        txtObservaciones.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                pedidoDAO.actualizarObservacion(s.toString(), pedido.iPedido);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });

        txtObservaciones.setOnLongClickListener(new View.OnLongClickListener() {
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
    public void onActivityResult(int requestCode, int resultcode, Intent intent) {
        ArrayList<String> speech = null;
        if (intent != null) {
            speech = intent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
        }
        if (speech != null) {
            resultSpeech = speech.get(0);
            if (resultSpeech != null) {
                txtObservaciones.setText(resultSpeech);
            }
        }
    }

    public void actualizarDescuento1() {
        checkbox_descuento1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double TotalDescuentos = 0;
                pedido = pedidoDAO.obtenerPedido(pedido.iPedido);
                boolean agregarDescuento = agregarDescuento(pedido);
                boolean isChecked1 = checkbox_descuento1.isChecked();
                if (isChecked1) {
                    if (agregarDescuento) {
                        TotalDescuentos = obtenerTotalDescuento(checkbox_descuento1.isChecked(), checkbox_descuento2.isChecked(), checkbox_descuento3.isChecked(), checkbox_descuento4.isChecked(), checkbox_descuento5.isChecked());
                        double TotalImpuesto = 0;
                        double igv = configuracion.IGV;
                        pedidoDAO.actualizarTotalDescuento(TotalDescuentos, pedido.iPedido);
                        pedidoDAO.actualizarDescuento1(checkbox_descuento1.isChecked(), pedido.iPedido);
                        ((AgregarPedidoActivity) getActivity()).detallePedidoFragment.tvValorTotal.setText(Util.formatoDineroSoles(pedido.TotalImporte - TotalDescuentos));
                        double totalGeneralSinImpuestos = pedido.TotalImporte - TotalDescuentos;
                        TotalImpuesto = (double) totalGeneralSinImpuestos * igv / 100;
                        ((AgregarPedidoActivity) getActivity()).detallePedidoFragment.tvIgvTotal.setText(Util.formatoDineroSoles(TotalImpuesto));
                        ((AgregarPedidoActivity) getActivity()).detallePedidoFragment.tvTotal.setText(Util.formatoDineroSoles(TotalImpuesto + totalGeneralSinImpuestos));
                        pedidoDAO.actualizarTotalImpuestos(TotalImpuesto, pedido.iPedido);
                    } else {
                        checkbox_descuento1.setChecked(false);
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                                getActivity().getString(R.string.error_descuentos), duration);
                        toast.show();
                    }
                } else {
                    TotalDescuentos = obtenerTotalDescuento(checkbox_descuento1.isChecked(), checkbox_descuento2.isChecked(), checkbox_descuento3.isChecked(), checkbox_descuento4.isChecked(), checkbox_descuento5.isChecked());
                    double TotalImpuesto = 0;
                    double igv = configuracion.IGV;
                    pedidoDAO.actualizarTotalDescuento(TotalDescuentos, pedido.iPedido);
                    pedidoDAO.actualizarDescuento1(checkbox_descuento1.isChecked(), pedido.iPedido);
                    ((AgregarPedidoActivity) getActivity()).detallePedidoFragment.tvValorTotal.setText(Util.formatoDineroSoles(pedido.TotalImporte - TotalDescuentos));
                    double totalGeneralSinImpuestos = pedido.TotalImporte - TotalDescuentos;
                    TotalImpuesto = (double) totalGeneralSinImpuestos * igv / 100;
                    ((AgregarPedidoActivity) getActivity()).detallePedidoFragment.tvIgvTotal.setText(Util.formatoDineroSoles(TotalImpuesto));
                    ((AgregarPedidoActivity) getActivity()).detallePedidoFragment.tvTotal.setText(Util.formatoDineroSoles(TotalImpuesto + totalGeneralSinImpuestos));
                    pedidoDAO.actualizarTotalImpuestos(TotalImpuesto, pedido.iPedido);
                }
            }
        });
    }


    public void actualizarDescuento2() {
        checkbox_descuento2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double TotalDescuentos = 0;
                pedido = pedidoDAO.obtenerPedido(pedido.iPedido);
                boolean agregarDescuento = agregarDescuento(pedido);
                boolean isChecked2 = checkbox_descuento2.isChecked();
                if (isChecked2) {
                    if (agregarDescuento) {
                        TotalDescuentos = obtenerTotalDescuento(checkbox_descuento1.isChecked(), checkbox_descuento2.isChecked(), checkbox_descuento3.isChecked(), checkbox_descuento4.isChecked(), checkbox_descuento5.isChecked());
                        double TotalImpuesto = 0;
                        double igv = configuracion.IGV;
                        pedidoDAO.actualizarTotalDescuento(TotalDescuentos, pedido.iPedido);
                        pedidoDAO.actualizarDescuento2(checkbox_descuento2.isChecked(), pedido.iPedido);
                        ((AgregarPedidoActivity) getActivity()).detallePedidoFragment.tvValorTotal.setText(Util.formatoDineroSoles(pedido.TotalImporte - TotalDescuentos));
                        double totalGeneralSinImpuestos = pedido.TotalImporte - TotalDescuentos;
                        TotalImpuesto = (double) totalGeneralSinImpuestos * igv / 100;
                        ((AgregarPedidoActivity) getActivity()).detallePedidoFragment.tvIgvTotal.setText(Util.formatoDineroSoles(TotalImpuesto));
                        ((AgregarPedidoActivity) getActivity()).detallePedidoFragment.tvTotal.setText(Util.formatoDineroSoles(TotalImpuesto + totalGeneralSinImpuestos));
                        pedidoDAO.actualizarTotalImpuestos(TotalImpuesto, pedido.iPedido);
                    } else {
                        checkbox_descuento2.setChecked(false);
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                                getActivity().getString(R.string.error_descuentos), duration);
                        toast.show();
                    }
                } else {
                    TotalDescuentos = obtenerTotalDescuento(checkbox_descuento1.isChecked(), checkbox_descuento2.isChecked(), checkbox_descuento3.isChecked(), checkbox_descuento4.isChecked(), checkbox_descuento5.isChecked());
                    double TotalImpuesto = 0;
                    double igv = configuracion.IGV;
                    pedidoDAO.actualizarTotalDescuento(TotalDescuentos, pedido.iPedido);
                    pedidoDAO.actualizarDescuento2(checkbox_descuento2.isChecked(), pedido.iPedido);
                    ((AgregarPedidoActivity) getActivity()).detallePedidoFragment.tvValorTotal.setText(Util.formatoDineroSoles(pedido.TotalImporte - TotalDescuentos));
                    double totalGeneralSinImpuestos = pedido.TotalImporte - TotalDescuentos;
                    TotalImpuesto = (double) totalGeneralSinImpuestos * igv / 100;
                    ((AgregarPedidoActivity) getActivity()).detallePedidoFragment.tvIgvTotal.setText(Util.formatoDineroSoles(TotalImpuesto));
                    ((AgregarPedidoActivity) getActivity()).detallePedidoFragment.tvTotal.setText(Util.formatoDineroSoles(TotalImpuesto + totalGeneralSinImpuestos));
                    pedidoDAO.actualizarTotalImpuestos(TotalImpuesto, pedido.iPedido);
                }
            }
        });
    }

    public void actualizarDescuento3() {
        checkbox_descuento3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double TotalDescuentos = 0;
                pedido = pedidoDAO.obtenerPedido(pedido.iPedido);
                boolean agregarDescuento = agregarDescuento(pedido);
                boolean isChecked3 = checkbox_descuento3.isChecked();
                if (isChecked3) {
                    if (agregarDescuento) {
                        TotalDescuentos = obtenerTotalDescuento(checkbox_descuento1.isChecked(), checkbox_descuento2.isChecked(), checkbox_descuento3.isChecked(), checkbox_descuento4.isChecked(), checkbox_descuento5.isChecked());
                        double TotalImpuesto = 0;
                        double igv = configuracion.IGV;
                        pedidoDAO.actualizarTotalDescuento(TotalDescuentos, pedido.iPedido);
                        pedidoDAO.actualizarDescuento3(checkbox_descuento3.isChecked(), pedido.iPedido);
                        ((AgregarPedidoActivity) getActivity()).detallePedidoFragment.tvValorTotal.setText(Util.formatoDineroSoles(pedido.TotalImporte - TotalDescuentos));
                        double totalGeneralSinImpuestos = pedido.TotalImporte - TotalDescuentos;
                        TotalImpuesto = (double) totalGeneralSinImpuestos * igv / 100;
                        ((AgregarPedidoActivity) getActivity()).detallePedidoFragment.tvIgvTotal.setText(Util.formatoDineroSoles(TotalImpuesto));
                        ((AgregarPedidoActivity) getActivity()).detallePedidoFragment.tvTotal.setText(Util.formatoDineroSoles(TotalImpuesto + totalGeneralSinImpuestos));
                        pedidoDAO.actualizarTotalImpuestos(TotalImpuesto, pedido.iPedido);
                    } else {
                        checkbox_descuento3.setChecked(false);
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                                getActivity().getString(R.string.error_descuentos), duration);
                        toast.show();
                    }
                } else {
                    TotalDescuentos = obtenerTotalDescuento(checkbox_descuento1.isChecked(), checkbox_descuento2.isChecked(), checkbox_descuento3.isChecked(), checkbox_descuento4.isChecked(), checkbox_descuento5.isChecked());
                    double TotalImpuesto = 0;
                    double igv = configuracion.IGV;
                    pedidoDAO.actualizarTotalDescuento(TotalDescuentos, pedido.iPedido);
                    pedidoDAO.actualizarDescuento3(checkbox_descuento3.isChecked(), pedido.iPedido);
                    ((AgregarPedidoActivity) getActivity()).detallePedidoFragment.tvValorTotal.setText(Util.formatoDineroSoles(pedido.TotalImporte - TotalDescuentos));
                    double totalGeneralSinImpuestos = pedido.TotalImporte - TotalDescuentos;
                    TotalImpuesto = (double) totalGeneralSinImpuestos * igv / 100;
                    ((AgregarPedidoActivity) getActivity()).detallePedidoFragment.tvIgvTotal.setText(Util.formatoDineroSoles(TotalImpuesto));
                    ((AgregarPedidoActivity) getActivity()).detallePedidoFragment.tvTotal.setText(Util.formatoDineroSoles(TotalImpuesto + totalGeneralSinImpuestos));
                    pedidoDAO.actualizarTotalImpuestos(TotalImpuesto, pedido.iPedido);
                }
            }
        });
    }

    public void actualizarDescuento4() {
        checkbox_descuento4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double TotalDescuentos = 0;
                pedido = pedidoDAO.obtenerPedido(pedido.iPedido);

                boolean agregarDescuento = agregarDescuento(pedido);
                boolean isChecked4 = checkbox_descuento4.isChecked();
                if (isChecked4) {
                    if (agregarDescuento) {
                        TotalDescuentos = obtenerTotalDescuento(checkbox_descuento1.isChecked(), checkbox_descuento2.isChecked(), checkbox_descuento3.isChecked(), checkbox_descuento4.isChecked(), checkbox_descuento5.isChecked());
                        double TotalImpuesto = 0;
                        double igv = configuracion.IGV;
                        pedidoDAO.actualizarTotalDescuento(TotalDescuentos, pedido.iPedido);
                        pedidoDAO.actualizarDescuento4(checkbox_descuento4.isChecked(), pedido.iPedido);
                        ((AgregarPedidoActivity) getActivity()).detallePedidoFragment.tvValorTotal.setText(Util.formatoDineroSoles(pedido.TotalImporte - TotalDescuentos));
                        double totalGeneralSinImpuestos = pedido.TotalImporte - TotalDescuentos;
                        TotalImpuesto = (double) totalGeneralSinImpuestos * igv / 100;
                        ((AgregarPedidoActivity) getActivity()).detallePedidoFragment.tvIgvTotal.setText(Util.formatoDineroSoles(TotalImpuesto));
                        ((AgregarPedidoActivity) getActivity()).detallePedidoFragment.tvTotal.setText(Util.formatoDineroSoles(TotalImpuesto + totalGeneralSinImpuestos));
                        pedidoDAO.actualizarTotalImpuestos(TotalImpuesto, pedido.iPedido);
                    } else {
                        checkbox_descuento4.setChecked(false);
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                                getActivity().getString(R.string.error_descuentos), duration);
                        toast.show();
                    }
                } else {
                    TotalDescuentos = obtenerTotalDescuento(checkbox_descuento1.isChecked(), checkbox_descuento2.isChecked(), checkbox_descuento3.isChecked(), checkbox_descuento4.isChecked(), checkbox_descuento5.isChecked());
                    double TotalImpuesto = 0;
                    double igv = configuracion.IGV;
                    pedidoDAO.actualizarTotalDescuento(TotalDescuentos, pedido.iPedido);
                    pedidoDAO.actualizarDescuento4(checkbox_descuento4.isChecked(), pedido.iPedido);
                    ((AgregarPedidoActivity) getActivity()).detallePedidoFragment.tvValorTotal.setText(Util.formatoDineroSoles(pedido.TotalImporte - TotalDescuentos));
                    double totalGeneralSinImpuestos = pedido.TotalImporte - TotalDescuentos;
                    TotalImpuesto = (double) totalGeneralSinImpuestos * igv / 100;
                    ((AgregarPedidoActivity) getActivity()).detallePedidoFragment.tvIgvTotal.setText(Util.formatoDineroSoles(TotalImpuesto));
                    ((AgregarPedidoActivity) getActivity()).detallePedidoFragment.tvTotal.setText(Util.formatoDineroSoles(TotalImpuesto + totalGeneralSinImpuestos));
                    pedidoDAO.actualizarTotalImpuestos(TotalImpuesto, pedido.iPedido);
                }

            }
        });
    }

    public void actualizarDescuento5() {
        checkbox_descuento5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double TotalDescuentos = 0;
                pedido = pedidoDAO.obtenerPedido(pedido.iPedido);
                boolean agregarDescuento = agregarDescuento(pedido);

                boolean isChecked5 = checkbox_descuento5.isChecked();
                if (isChecked5) {
                    if (agregarDescuento) {
                        TotalDescuentos = obtenerTotalDescuento(checkbox_descuento1.isChecked(), checkbox_descuento2.isChecked(), checkbox_descuento3.isChecked(), checkbox_descuento4.isChecked(), checkbox_descuento5.isChecked());
                        double TotalImpuesto = 0;
                        double igv = configuracion.IGV;
                        pedidoDAO.actualizarTotalDescuento(TotalDescuentos, pedido.iPedido);
                        pedidoDAO.actualizarDescuento5(checkbox_descuento5.isChecked(), pedido.iPedido);
                        ((AgregarPedidoActivity) getActivity()).detallePedidoFragment.tvValorTotal.setText(Util.formatoDineroSoles(pedido.TotalImporte - TotalDescuentos));
                        double totalGeneralSinImpuestos = pedido.TotalImporte - TotalDescuentos;
                        TotalImpuesto = (double) totalGeneralSinImpuestos * igv / 100;
                        ((AgregarPedidoActivity) getActivity()).detallePedidoFragment.tvIgvTotal.setText(Util.formatoDineroSoles(TotalImpuesto));
                        ((AgregarPedidoActivity) getActivity()).detallePedidoFragment.tvTotal.setText(Util.formatoDineroSoles(TotalImpuesto + totalGeneralSinImpuestos));
                        pedidoDAO.actualizarTotalImpuestos(TotalImpuesto, pedido.iPedido);
                    } else {
                        checkbox_descuento5.setChecked(false);
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                                getActivity().getString(R.string.error_descuentos), duration);
                        toast.show();
                    }
                } else {
                    TotalDescuentos = obtenerTotalDescuento(checkbox_descuento1.isChecked(), checkbox_descuento2.isChecked(), checkbox_descuento3.isChecked(), checkbox_descuento4.isChecked(), checkbox_descuento5.isChecked());
                    double TotalImpuesto = 0;
                    double igv = configuracion.IGV;
                    pedidoDAO.actualizarTotalDescuento(TotalDescuentos, pedido.iPedido);
                    pedidoDAO.actualizarDescuento5(checkbox_descuento5.isChecked(), pedido.iPedido);
                    ((AgregarPedidoActivity) getActivity()).detallePedidoFragment.tvValorTotal.setText(Util.formatoDineroSoles(pedido.TotalImporte - TotalDescuentos));
                    double totalGeneralSinImpuestos = pedido.TotalImporte - TotalDescuentos;
                    TotalImpuesto = (double) totalGeneralSinImpuestos * igv / 100;
                    ((AgregarPedidoActivity) getActivity()).detallePedidoFragment.tvIgvTotal.setText(Util.formatoDineroSoles(TotalImpuesto));
                    ((AgregarPedidoActivity) getActivity()).detallePedidoFragment.tvTotal.setText(Util.formatoDineroSoles(TotalImpuesto + totalGeneralSinImpuestos));
                    pedidoDAO.actualizarTotalImpuestos(TotalImpuesto, pedido.iPedido);
                }
            }
        });
    }

    public void cargarDescuentos() {
        if (descuentos.size() > 0) {
            checkbox_descuento1.setText(descuentos.get(0).Descripcion);
            if (pedido.Descuento1) {
                checkbox_descuento1.setChecked(true);
            }
        }

        if (descuentos.size() > 1) {
            checkbox_descuento2.setText(descuentos.get(1).Descripcion);
            if (pedido.Descuento2) {
                checkbox_descuento2.setChecked(true);
            }
        }

        if (descuentos.size() > 2) {
            checkbox_descuento3.setText(descuentos.get(2).Descripcion);
            if (pedido.Descuento3) {
                checkbox_descuento3.setChecked(true);
            }
        }

        if (descuentos.size() > 3) {
            checkbox_descuento4.setText(descuentos.get(3).Descripcion);
            if (pedido.Descuento4) {
                checkbox_descuento4.setChecked(true);
            }
        }
        if (descuentos.size() > 4) {
            checkbox_descuento5.setText(descuentos.get(4).Descripcion);
            if (pedido.Descuento5) {
                checkbox_descuento5.setChecked(true);
            }
        }
    }

    public void cargarTerminoVenta() {
        if (pedido.TerminoVenta != null) {
            if (!pedido.TerminoVenta.equals("")) {
                switch (pedido.TerminoVenta) {
                    case "02":
                        radio_contado_adelantado.setChecked(true);
                        break;
                    case "01":
                        radio_contado_contra_entrega.setChecked(true);
                        break;
                    case "10":
                        radio_credito_letras.setChecked(true);
                        break;
                    case "05":
                        radio_credito_facturas.setChecked(true);
                        break;
                    case "00":
                        radio_otros.setChecked(true);
                        break;
                }
            }
        }
    }

    public void cargarVendedor() {
        if (pedido.VendedorID != null) {
            if (!pedido.VendedorID.equals("")) {
                if (pedido.VendedorID.equals(vendedor.Linea1)) {
                    rbPurolator.setChecked(true);
                } else {
                    rbFiltech.setChecked(true);
                }
            }
        }
    }

    public void cargarObservacion() {
        if (pedido.Observacion != null) {
            txtObservaciones.setText(pedido.Observacion);
        }
    }

    public void desactivarModificacion() {
        if (pedido.Enviado) {
            /*checkbox_descuento1.setEnabled(false);
            //checkbox_descuento2.setEnabled(false);
            //checkbox_descuento3.setEnabled(false);
            //checkbox_descuento4.setEnabled(false);
            //checkbox_descuento5.setEnabled(false);
            rbFiltech.setEnabled(false);
            rbPurolator.setEnabled(false);
            radio_contado_adelantado.setEnabled(false);
            radio_contado_contra_entrega.setEnabled(false);
            radio_credito_letras.setEnabled(false);
            radio_credito_facturas.setEnabled(false);
            radio_otros.setEnabled(false);
            txtObservaciones.setEnabled(false);
            if (pedido.TerminoVenta.equals("46")) {
                radio_credito_letras.setEnabled(true);
            }
            if (pedido.TerminoVenta.equals("45")) {
                radio_credito_facturas.setEnabled(true);
            }*/
        }
    }

    public double obtenerTotalDescuento(boolean desc1, boolean desc2, boolean desc3, boolean desc4, boolean desc5) {
        int desc1int = 0, desc2int = 0, desc3int = 0, desc4int = 0, desc5int = 0;
        boolean descuento1 = false;
        boolean descuento2 = false;
        boolean descuento3 = false;
        pedidoDAO.actualizarDescuento1Valor(0, pedido.iPedido);
        pedidoDAO.actualizarDescuento2Valor(0, pedido.iPedido);
        pedidoDAO.actualizarDescuento3Valor(0, pedido.iPedido);
        if (desc1) {
            String descuento = checkbox_descuento1.getText().toString().replace("%", "");
            try {
                desc1int = Integer.parseInt(descuento);
                if (!descuento1) {
                    descuento1 = pedidoDAO.actualizarDescuento1Valor((double) desc1int, pedido.iPedido);
                } else if (!descuento2) {
                    descuento2 = pedidoDAO.actualizarDescuento2Valor((double) desc1int, pedido.iPedido);
                } else if (!descuento3) {
                    descuento3 = pedidoDAO.actualizarDescuento3Valor((double) desc1int, pedido.iPedido);
                }
            } catch (NumberFormatException e) {
                desc1int = 0;
            }
        }
        if (desc2) {
            String descuento = checkbox_descuento2.getText().toString().replace("%", "");
            try {
                desc2int = Integer.parseInt(descuento);
                if (!descuento1) {
                    descuento1 = pedidoDAO.actualizarDescuento1Valor((double) desc2int, pedido.iPedido);
                } else if (!descuento2) {
                    descuento2 = pedidoDAO.actualizarDescuento2Valor((double) desc2int, pedido.iPedido);
                } else if (!descuento3) {
                    descuento3 = pedidoDAO.actualizarDescuento3Valor((double) desc2int, pedido.iPedido);
                }
            } catch (NumberFormatException e) {
                desc2int = 0;
            }
        }
        if (desc3) {
            String descuento = checkbox_descuento3.getText().toString().replace("%", "");
            try {
                desc3int = Integer.parseInt(descuento);
                if (!descuento1) {
                    descuento1 = pedidoDAO.actualizarDescuento1Valor((double) desc3int, pedido.iPedido);
                } else if (!descuento2) {
                    descuento2 = pedidoDAO.actualizarDescuento2Valor((double) desc3int, pedido.iPedido);
                } else if (!descuento3) {
                    descuento3 = pedidoDAO.actualizarDescuento3Valor((double) desc3int, pedido.iPedido);
                }
            } catch (NumberFormatException e) {
                desc3int = 0;
            }
        }
        if (desc4) {
            String descuento = checkbox_descuento4.getText().toString().replace("%", "");
            try {
                desc4int = Integer.parseInt(descuento);
                if (!descuento1) {
                    descuento1 = pedidoDAO.actualizarDescuento1Valor((double) desc4int, pedido.iPedido);
                } else if (!descuento2) {
                    descuento2 = pedidoDAO.actualizarDescuento2Valor((double) desc4int, pedido.iPedido);
                } else if (!descuento3) {
                    descuento3 = pedidoDAO.actualizarDescuento3Valor((double) desc4int, pedido.iPedido);
                }
            } catch (NumberFormatException e) {
                desc4int = 0;
            }
        }
        if (desc5) {
            String descuento = checkbox_descuento5.getText().toString().replace("%", "");
            try {
                desc5int = Integer.parseInt(descuento);
                if (!descuento1) {
                    descuento1 = pedidoDAO.actualizarDescuento1Valor((double) desc5int, pedido.iPedido);
                } else if (!descuento2) {
                    descuento2 = pedidoDAO.actualizarDescuento2Valor((double) desc5int, pedido.iPedido);
                } else if (!descuento3) {
                    descuento3 = pedidoDAO.actualizarDescuento3Valor((double) desc5int, pedido.iPedido);
                }
            } catch (NumberFormatException e) {
                desc5int = 0;
            }
        }
        double TotalFinal = (double) pedido.TotalImporte * ((double) (100 - desc1int) / 100) * ((double) (100 - desc2int) / 100) * ((double) (100 - desc3int) / 100) * ((double) (100 - desc4int) / 100) * ((double) (100 - desc5int) / 100);
        double TotalDescuentos = pedido.TotalImporte - TotalFinal;
        return TotalDescuentos;
    }

    public boolean agregarDescuento(Pedido pedido) {
        boolean agregarDescuento = true;
        int cantidadDescuentos = 0;
        if (pedido.Descuento1) {
            cantidadDescuentos++;
        }
        if (pedido.Descuento2) {
            cantidadDescuentos++;
        }
        if (pedido.Descuento3) {
            cantidadDescuentos++;
        }
        if (pedido.Descuento4) {
            cantidadDescuentos++;
        }
        if (pedido.Descuento5) {
            cantidadDescuentos++;
        }
        if (cantidadDescuentos >= 3) {
            agregarDescuento = false;
        }
        return agregarDescuento;
    }
}
