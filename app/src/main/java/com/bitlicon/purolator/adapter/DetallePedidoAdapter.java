package com.bitlicon.purolator.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bitlicon.purolator.R;
import com.bitlicon.purolator.activity.AgregarPedidoActivity;
import com.bitlicon.purolator.dao.ConfiguracionDAO;
import com.bitlicon.purolator.dao.DescuentoDAO;
import com.bitlicon.purolator.dao.DetallePedidoDAO;
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
import com.bitlicon.purolator.util.Util;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Lokerfy on 22/04/2016.
 */
public class DetallePedidoAdapter extends ArrayAdapter<DetallePedido> {

    private ViewHolder viewHolder;
    private LayoutInflater mInflater;
    private DetallePedidoDAO detallePedidoDAO;
    private PedidoDAO pedidoDAO;
    private Pedido pedido;
    private List<Descuento> descuentos;
    private DescuentoDAO descuentoDAO;
    private ProductoDAO productoDAO;
    DetallePedidoFragment detallePedidoFragment;
    CondicionPedidoFragment condicionPedidoFragment;
    private ConfiguracionDAO configuracionDAO;
    private Configuracion configuracion;

    public DetallePedidoAdapter(Context context, List<DetallePedido> detallePedidos, Pedido pedido, DetallePedidoFragment detallePedidoFragment, CondicionPedidoFragment condicionPedidoFragment) {
        super(context, 0, detallePedidos);
        mInflater = LayoutInflater.from(context);
        this.pedido = pedido;
        descuentoDAO = new DescuentoDAO(context);
        productoDAO = new ProductoDAO(context);
        configuracionDAO = new ConfiguracionDAO(context);
        descuentos = descuentoDAO.listarDescuentos();
        configuracion = configuracionDAO.Obtener();
        this.detallePedidoFragment = detallePedidoFragment;
        this.condicionPedidoFragment = condicionPedidoFragment;
    }

    @Override
    public View getView(final int position, View convertview, final ViewGroup parent) {
        final DetallePedido detallePedido = getItem(position);
        if (convertview == null || convertview.getTag() == null) {
            convertview = mInflater.inflate(R.layout.item_detalle_pedido, null);
            viewHolder = new ViewHolder();
            viewHolder.tvCodigo = (TextView) convertview.findViewById(R.id.tvCodigo);
            viewHolder.etPrecio = (EditText) convertview.findViewById(R.id.etPrecio);
            viewHolder.etCantidad = (EditText) convertview.findViewById(R.id.etCantidad);
            viewHolder.tvValor = (TextView) convertview.findViewById(R.id.tvValor);
            viewHolder.ivEliminar = (ImageView) convertview.findViewById(R.id.ivEliminar);
            if (!pedido.Enviado) {

            } else {
                viewHolder.ivEliminar.setVisibility(View.GONE);
            }
            convertview.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertview.getTag();
        }

        viewHolder.tvCodigo.setText(detallePedido.Producto);

        double subtotal = detallePedido.Cantidad * ((detallePedido.Precio) * ((100 - detallePedido.Descuento1) / 100 * 1.00) * ((100 - detallePedido.Descuento2) / 100 * 1.00));

        viewHolder.tvValor.setText(Util.formatoDineroSoles(subtotal));

        viewHolder.ivEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detallePedidoDAO = new DetallePedidoDAO(getContext());
                boolean eliminar = detallePedidoDAO.eliminarDetallePedido(detallePedido);
                if (eliminar) {
                    remove(detallePedido);

                    TextView tvValorTotalUnitario = (TextView) parent.findViewById(R.id.tvValorTotalUnitario);
                    TextView tvIgvTotalUnitario = (TextView) parent.findViewById(R.id.tvIgvTotalUnitario);
                    TextView tvTotalUnitario = (TextView) parent.findViewById(R.id.tvTotalUnitario);

                    tvValorTotalUnitario.setText("0.00");
                    tvIgvTotalUnitario.setText("0.00");
                    tvTotalUnitario.setText("0.00");

                    notifyDataSetChanged();
                    if (detallePedidoFragment != null) {
                        detallePedidoFragment.onResume();
                    }
                    if (condicionPedidoFragment != null) {
                        condicionPedidoFragment.onResume();
                    }
                }
            }
        });

        viewHolder.etPrecio.setText(Util.formatoDineroSoles(detallePedido.Precio));
        viewHolder.etCantidad.setText(String.valueOf(detallePedido.Cantidad));


        viewHolder.tvCodigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double totalValor = detallePedido.Cantidad * ((detallePedido.Precio) * ((100 - detallePedido.Descuento1) / 100 * 1.00) * ((100 - detallePedido.Descuento2) / 100 * 1.00));

                TextView tvValorTotalUnitario = (TextView) parent.findViewById(R.id.tvValorTotalUnitario);
                TextView tvIgvTotalUnitario = (TextView) parent.findViewById(R.id.tvIgvTotalUnitario);
                TextView tvTotalUnitario = (TextView) parent.findViewById(R.id.tvTotalUnitario);

                pedidoDAO = new PedidoDAO(getContext());

                pedido = pedidoDAO.obtenerPedido(pedido.iPedido);
                double TotalDescuentos = 0;
                double TotalImpuesto = 0;
                double igv = configuracion.IGV;
                TotalDescuentos = obtenerTotalDescuento(pedido.Descuento1, pedido.Descuento2, pedido.Descuento3, pedido.Descuento4, pedido.Descuento5, totalValor);
                tvValorTotalUnitario.setText(Util.formatoDineroSoles(totalValor - TotalDescuentos));

                double totalGeneralSinImpuestos = totalValor - TotalDescuentos;
                TotalImpuesto = (double) totalGeneralSinImpuestos * igv / 100;
                tvIgvTotalUnitario.setText(Util.formatoDineroSoles(TotalImpuesto));
                tvTotalUnitario.setText(Util.formatoDineroSoles(TotalImpuesto + totalGeneralSinImpuestos));

                if(pedido.Enviado) {
                    mostrarDetallePedido(detallePedido);
                }
            }
        });

        if (!pedido.Enviado) {
            viewHolder.etCantidad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog_producto_cantidad = new Dialog(getContext());

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

                    Producto producto = productoDAO.obtener(detallePedido.ProductoID);

                    cbDescuento1.setText("Descuento (" + producto.Descuento1 + "%)");
                    cbDescuento2.setText("Descuento (" + producto.Descuento2 + "%)");
                    cbDescuento3.setText("Descuento (" + producto.Descuento5 + "%)");

                    txtCantidad.setText(String.valueOf(detallePedido.Cantidad));
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
                            if (cantidadString.length() == 0) {
                                Toast.makeText(getContext(), R.string.icantidad, Toast.LENGTH_LONG).show();
                            } else {
                                int cantidad = Integer.parseInt(txtCantidad.getText().toString());
                                String ProductoID = detallePedido.ProductoID;
                                Producto producto = productoDAO.obtener(detallePedido.ProductoID);
                                double Precio = 0;
                                if (radio_precio_lista.isChecked()) {
                                    Precio = producto.PrecioDolares;
                                } else if (radio_precio_oficina.isChecked()) {
                                    Precio = producto.PrecioOficina;
                                } else if (radio_precio_lima.isChecked()) {
                                    Precio = producto.PrecioLima;
                                } else if (radio_precio_norte.isChecked()) {
                                    Precio = producto.PrecioNorte;
                                }
                                if (Precio <= 0) {
                                    Toast.makeText(getContext(), R.string.precio_mayor_cero, Toast.LENGTH_LONG).show();
                                } else {
                                    detallePedidoDAO = new DetallePedidoDAO(getContext());
                                    DetallePedido detallePedido = new DetallePedido();
                                    detallePedido.Cantidad = cantidad;
                                    detallePedido.ProductoID = ProductoID;
                                    detallePedido.Precio = Precio;
                                    detallePedido.iPedido = pedido.iPedido;
                                    if (!cbDescuento1.isChecked()) {
                                        producto.Descuento1 = 0;
                                    }
                                    if (!cbDescuento2.isChecked()) {
                                        producto.Descuento2 = 0;
                                    }
                                    if (!cbDescuento3.isChecked()) {
                                        producto.Descuento5 = 0;
                                    }
                                    detallePedido.Descuento1 = (100 * 100 - (100 - producto.Descuento1) * (100 - producto.Descuento2)) / 100 * 1.00;
                                    detallePedido.Descuento2 = producto.Descuento5;
                                    boolean registro = detallePedidoDAO.actualizarCantidadYPrecio(detallePedido);
                                    if (registro) {
                                        dialog_producto_cantidad.hide();
                                        if (detallePedidoFragment != null) {
                                            detallePedidoFragment.onResume();
                                        }
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
                    dialog_producto_cantidad.show();
                }
            });


        }


        if (pedido.Enviado) {
            viewHolder.etPrecio.setEnabled(false);
        } else {

            viewHolder.etPrecio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog_producto_cantidad = new Dialog(getContext());

                    dialog_producto_cantidad.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

                    dialog_producto_cantidad.setContentView(R.layout.dialogo_producto_precio);

                    final TextView lblCancelar = (TextView) dialog_producto_cantidad.findViewById(R.id.lblCancelar);
                    final TextView lblAgregar = (TextView) dialog_producto_cantidad.findViewById(R.id.lblAgregar);
                    final EditText etPrecio = (EditText) dialog_producto_cantidad.findViewById(R.id.etPrecio);

                    etPrecio.setText(String.valueOf(detallePedido.Precio));
                    lblCancelar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog_producto_cantidad.hide();
                        }
                    });
                    lblAgregar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String etPrecioString = etPrecio.getText().toString().trim();
                            if (etPrecioString.length() == 0) {
                                Toast.makeText(getContext(), R.string.iprecio, Toast.LENGTH_LONG).show();
                            } else {

                                double Precio = Double.parseDouble(etPrecio.getText().toString());

                                if (Precio <= 0) {
                                    Toast.makeText(getContext(), R.string.precio_mayor_cero, Toast.LENGTH_LONG).show();
                                } else {
                                    detallePedidoDAO = new DetallePedidoDAO(getContext());
                                    DetallePedido detallePedidoAux = new DetallePedido();
                                    detallePedidoAux.Precio = Precio;
                                    detallePedidoAux.ProductoID = detallePedido.ProductoID;
                                    detallePedidoAux.iPedido = pedido.iPedido;
                                    boolean registro = detallePedidoDAO.actualizarPrecio(detallePedidoAux);
                                    if (registro) {
                                        dialog_producto_cantidad.hide();
                                        if (detallePedidoFragment != null) {
                                            detallePedidoFragment.onResume();
                                        }
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
                    dialog_producto_cantidad.show();
                }
            });

        }


        return convertview;
    }

    public void mostrarDetallePedido(DetallePedido detallePedido)
    {
        final Dialog dialogo_detallepedido = new Dialog(getContext());
        dialogo_detallepedido.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogo_detallepedido.setContentView(R.layout.dialogo_detalle_pedido);

        TextView tvDescDist = (TextView) dialogo_detallepedido.findViewById(R.id.tvDescDist);
        TextView tvDescPro = (TextView) dialogo_detallepedido.findViewById(R.id.tvDescPro);
        TextView lblcerrar = (TextView)  dialogo_detallepedido.findViewById(R.id.lblcerrar);
        DecimalFormat df = new DecimalFormat("0.00");
        tvDescDist.setText(df.format(detallePedido.Descuento11) + "% + " + df.format(detallePedido.Descuento12) + "% = " + df.format(detallePedido.Descuento1) + "%");
        tvDescPro.setText(df.format(detallePedido.Descuento2) + "%");

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        Window window = dialogo_detallepedido.getWindow();
        layoutParams.copyFrom(window.getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);

        lblcerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogo_detallepedido.hide();
            }
        });

        dialogo_detallepedido.show();
    }

    public double obtenerTotalDescuento(boolean desc1, boolean desc2, boolean desc3, boolean desc4, boolean desc5, double TotalImporte) {
        int desc1int = 0, desc2int = 0, desc3int = 0, desc4int = 0, desc5int = 0;
        if (desc1) {
            String descuento = descuentos.get(0).Descripcion.replace("%", "");
            try {
                desc1int = Integer.parseInt(descuento);
            } catch (NumberFormatException e) {
                desc1int = 0;
            }
        }
        if (desc2) {
            String descuento = descuentos.get(1).Descripcion.replace("%", "");
            try {
                desc2int = Integer.parseInt(descuento);
            } catch (NumberFormatException e) {
                desc2int = 0;
            }
        }
        if (desc3) {
            String descuento = descuentos.get(2).Descripcion.replace("%", "");
            try {
                desc3int = Integer.parseInt(descuento);
            } catch (NumberFormatException e) {
                desc3int = 0;
            }
        }
        if (desc4) {
            String descuento = descuentos.get(3).Descripcion.replace("%", "");
            try {
                desc4int = Integer.parseInt(descuento);
            } catch (NumberFormatException e) {
                desc4int = 0;
            }
        }
        if (desc5) {
            String descuento = descuentos.get(4).Descripcion.replace("%", "");
            try {
                desc5int = Integer.parseInt(descuento);
            } catch (NumberFormatException e) {
                desc5int = 0;
            }
        }
        double TotalFinal = (double) TotalImporte * ((double) (100 - desc1int) / 100) * ((double) (100 - desc2int) / 100) * ((double) (100 - desc3int) / 100) * ((double) (100 - desc4int) / 100) * ((double) (100 - desc5int) / 100);
        double TotalDescuentos = TotalImporte - TotalFinal;
        return TotalDescuentos;
    }

    static class ViewHolder {
        TextView tvCodigo;
        EditText etPrecio;
        EditText etCantidad;
        TextView tvValor;
        ImageView ivEliminar;
    }
}
