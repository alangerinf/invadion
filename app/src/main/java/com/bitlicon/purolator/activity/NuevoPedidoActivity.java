package com.bitlicon.purolator.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.bitlicon.purolator.R;
import com.bitlicon.purolator.entities.Cliente;
import com.bitlicon.purolator.entities.Factura;
import com.bitlicon.purolator.fragment.CondicionFragment;
import com.bitlicon.purolator.fragment.PedidoFragment;
import com.bitlicon.purolator.util.Util;

/**
 * Created by dianewalls on 25/05/2015.
 */
public class NuevoPedidoActivity extends ControlFragmentActivity {
    private static final int NUM_PAGES = 2;
    private ViewPager viewPager;
    private FacturaSlidePagerAdapter mFacturaSlidePagerAdapter;
    private TextView txtPedido;
    private Cliente mCliente;
    private Factura mFactura;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detalle_factura_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NuevoPedidoActivity.super.onBackPressed();
                break;
            case R.id.action_nuevo:
                Toast.makeText(getApplicationContext(), "Pantalla en desarrollo", Toast.LENGTH_SHORT).show();
               /* Intent intent = new Intent(getApplicationContext(), AgregarPedidoActivity.class);
                intent.putExtra("cliente", mCliente);
                intent.putExtra("factura", mFactura);
                startActivity(intent);*/
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_factura);
        Util.setIconMenu(R.drawable.ic_back, this);

        mCliente = (Cliente) getIntent().getSerializableExtra("cliente");
        mFactura = (Factura) getIntent().getSerializableExtra("factura");
        setTitle("Factura N°: " + mFactura.NumeroFactura);

        viewPager = (ViewPager) findViewById(R.id.viewPager);

        mFacturaSlidePagerAdapter = new FacturaSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mFacturaSlidePagerAdapter);

        viewPager.setOnPageChangeListener(mFacturaSlidePagerAdapter);


        LinearLayout lnPedidosFranja = (LinearLayout) findViewById(R.id.lnPedidoFranja);
        lnPedidosFranja.setVisibility(View.VISIBLE);
        txtPedido = (TextView) findViewById(R.id.txtPedido);
        txtPedido.setTextColor(getResources().getColor(R.color.celeste));


        LinearLayout lnPedido = (LinearLayout) findViewById(R.id.lnPedido);
        lnPedido.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
            }
        });

        LinearLayout lnCondicion = (LinearLayout) findViewById(R.id.lnCondicion);
        lnCondicion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
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

    public class FacturaSlidePagerAdapter extends FragmentStatePagerAdapter implements ViewPager.OnPageChangeListener {
        public FacturaSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("cliente", mCliente);
            bundle.putSerializable("factura", mFactura);
            if (position == 0) {
                PedidoFragment pedidoFragment = new PedidoFragment();
                pedidoFragment.setArguments(bundle);
                return pedidoFragment;
            } else if (position == 1) {
                CondicionFragment condicionFragment = new CondicionFragment();
                condicionFragment.setArguments(bundle);
                return condicionFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int position) {
            LinearLayout lnfranja = null;
            TextView txtTexto = null;
            limpiarseleccionado();
            switch (position) {
                case 0:
                    lnfranja = (LinearLayout) findViewById(R.id.lnPedidoFranja);
                    txtTexto = (TextView) findViewById(R.id.txtPedido);
                    break;
                case 1:
                    lnfranja = (LinearLayout) findViewById(R.id.lnCondicionFranja);
                    txtTexto = (TextView) findViewById(R.id.txtCondicion);
                    break;
            }
            lnfranja.setVisibility(View.VISIBLE);
            txtTexto.setTextColor(getResources().getColor(R.color.celeste));
        }
    }


}
