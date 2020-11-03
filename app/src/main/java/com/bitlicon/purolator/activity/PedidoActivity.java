package com.bitlicon.purolator.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bitlicon.purolator.R;
import com.bitlicon.purolator.dao.PedidoDAO;
import com.bitlicon.purolator.dao.VendedorDAO;
import com.bitlicon.purolator.entities.Cliente;
import com.bitlicon.purolator.entities.Pedido;
import com.bitlicon.purolator.entities.Vendedor;
import com.bitlicon.purolator.fragment.FacturasPorClienteFragment;
import com.bitlicon.purolator.fragment.PedidosPorClienteFragment;
import com.bitlicon.purolator.util.Util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class PedidoActivity extends ControlFragmentActivity {

    private static final int NUM_PAGES = 2;
    private ViewPager viewPagerPedido;
    private PedidoSlidePagerAdapter mPedidoSlidePagerAdapter;
    private Cliente cliente;
    private TextView txtPedidos;
    private VendedorDAO vendedorDAO;
    private Vendedor vendedor;
    private PedidoDAO pedidoDAO;
    private List<Pedido> pedidos;
    private int mLayout;
    private int[] menu = {R.menu.detalle_pedido_activity_actions, R.menu.factura_pedido_activity_actions};
    private PedidosPorClienteFragment pedidosPorClienteFragment;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(mLayout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                PedidoActivity.super.onBackPressed();
                break;
            case R.id.action_nuevo:
                Pedido pedido = new Pedido();
                pedido.ClienteID = cliente.ClienteID;
                pedido.VendedorID = vendedor.Linea1;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
                String fechaCreacionPedido = sdf.format(new Date());
                pedido.FechaCreacionPedido = fechaCreacionPedido;
                pedido.Descuento1 = false; pedido.Descuento2 = false; pedido.Descuento3 = false; pedido.Descuento4 = false;
                pedido.Descuento5 = false; pedido.TerminoVenta = "";pedido.Observacion = ""; pedido.TotalImporte = 0; pedido.TotalDescuento = 0; pedido.TotalImpuestos = 0;
                pedido.iPedido = pedidoDAO.registrarPedido(pedido);
                pedido.NumeroPedido = String.valueOf(pedido.iPedido);
                pedidoDAO.actualizarNumeroPedido(String.valueOf(pedido.iPedido),  pedido.iPedido);
                Intent intent = new Intent(getApplicationContext(), AgregarPedidoActivity.class);
                intent.putExtra("cliente", cliente);
                intent.putExtra("pedido", pedido);
                startActivity(intent);
                break;
            case R.id.action_ordenar_monto:
                pedidos = pedidoDAO.listarPedidosPorMonto(cliente.ClienteID);
                pedidosPorClienteFragment.actualizarPedidos(pedidos);
                break;
            case R.id.action_ordenar_fecha:
                pedidos = pedidoDAO.listarPedidos(cliente.ClienteID);
                pedidosPorClienteFragment.actualizarPedidos(pedidos);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        vendedorDAO = new VendedorDAO(getApplicationContext());
        pedidoDAO = new PedidoDAO(getApplicationContext());

        vendedor = vendedorDAO.buscarVendedor();
        pedidos = pedidoDAO.listarPedidos(cliente.ClienteID);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);
        Util.setIconMenu(R.drawable.ic_back, this);
        setTitle(R.string.pedidos);
        invalidateOptionsMenu();
        mLayout = menu[0];


        cliente = (Cliente) getIntent().getSerializableExtra("cliente");

        viewPagerPedido = (ViewPager) findViewById(R.id.viewPagerPedido);
        mPedidoSlidePagerAdapter = new PedidoSlidePagerAdapter(getSupportFragmentManager(), cliente);
        viewPagerPedido.setAdapter(mPedidoSlidePagerAdapter);
        viewPagerPedido.setOnPageChangeListener(mPedidoSlidePagerAdapter);


        LinearLayout lnPedidosFranja = (LinearLayout) findViewById(R.id.lnPedidosFranja);
        lnPedidosFranja.setVisibility(View.VISIBLE);
        txtPedidos = (TextView) findViewById(R.id.txtPedidos);
        txtPedidos.setTextColor(getResources().getColor(R.color.celeste));

        LinearLayout lnPedidos = (LinearLayout) findViewById(R.id.lnPedidos);
        lnPedidos.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                viewPagerPedido.setCurrentItem(0);
            }
        });

        LinearLayout lnFacturas = (LinearLayout) findViewById(R.id.lnFacturas);
        lnFacturas.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                viewPagerPedido.setCurrentItem(1);
            }
        });


    }

    public class PedidoSlidePagerAdapter extends FragmentStatePagerAdapter implements ViewPager.OnPageChangeListener {

        Cliente cliente;
        public PedidoSlidePagerAdapter(FragmentManager fm, Cliente cliente) {
            super(fm);
            this.cliente = cliente;
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                pedidosPorClienteFragment = PedidosPorClienteFragment.nuevaInstancia(cliente, pedidos);
                return pedidosPorClienteFragment;
            } else if (position == 1) {
                return FacturasPorClienteFragment.nuevaInstancia(cliente);
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
            invalidateOptionsMenu();
            mLayout = menu[position];
            switch (position) {
                case 0:
                    lnfranja = (LinearLayout) findViewById(R.id.lnPedidosFranja);
                    txtTexto = (TextView) findViewById(R.id.txtPedidos);
                    break;
                case 1:
                    lnfranja = (LinearLayout) findViewById(R.id.lnFacturasFranja);
                    txtTexto = (TextView) findViewById(R.id.txtFacturas);
                    break;
            }
            lnfranja.setVisibility(View.VISIBLE);
            txtTexto.setTextColor(getResources().getColor(R.color.celeste));
        }
    }

    public void limpiarseleccionado() {
        LinearLayout lnPedidosFranja = (LinearLayout) findViewById(R.id.lnPedidosFranja);
        lnPedidosFranja.setVisibility(View.INVISIBLE);

        LinearLayout lnFacturasFranja = (LinearLayout) findViewById(R.id.lnFacturasFranja);
        lnFacturasFranja.setVisibility(View.INVISIBLE);

        TextView txtPedidos = (TextView) findViewById(R.id.txtPedidos);
        txtPedidos.setTextColor(getResources().getColor(R.color.plomomenu));
        TextView txtFacturas = (TextView) findViewById(R.id.txtFacturas);
        txtFacturas.setTextColor(getResources().getColor(R.color.plomomenu));

    }
}
