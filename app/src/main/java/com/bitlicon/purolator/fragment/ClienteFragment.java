package com.bitlicon.purolator.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bitlicon.purolator.R;
import com.bitlicon.purolator.activity.BaseActivity;
import com.bitlicon.purolator.dao.SincronizacionServicioDAO;
import com.bitlicon.purolator.entities.SincronizacionServicio;
import com.bitlicon.purolator.util.Constantes;
import com.bitlicon.purolator.util.Util;

import java.util.Calendar;

public class ClienteFragment extends Fragment {
    private static final int NUM_TABS = 3;
    public View rootView;
    LinearLayout lnRutaFranja;
    LinearLayout lnRutaCliente, lnGeneralCliente, lnNuevoCliente, lnGeneralFranja, lnNuevoFranja;
    private BaseActivity base;
    public ViewPager viewPagerClientes;
    private ClienteSlidePagerAdapter mClienteSlidePagerAdapter;
    private TextView txtRutaTitulo, txtNuevoTitulo, txtGeneralTitulo;
    private int tab;
    private boolean pedido;
    private int[] menu = {R.menu.cliente_ruta_fragment_actions, R.menu.cliente_fragment_actions, R.menu.cliente_nuevo_fragment_actions};
    private SincronizacionServicio sincronizacionServicio;
    public static final String CLIENTE = "Cliente";

    public static ClienteFragment nuevaInstancia(BaseActivity base, int tab, boolean pedido) {
        ClienteFragment h = new ClienteFragment();
        h.tab = tab;
        h.base = base;
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_clientes, container, false);

        SincronizacionServicioDAO sincronizacionServicioDAO = new SincronizacionServicioDAO(getActivity());
        sincronizacionServicio = sincronizacionServicioDAO.buscarServicio(CLIENTE);

        viewPagerClientes = (ViewPager) rootView.findViewById(R.id.viewPagerClientes);

        mClienteSlidePagerAdapter = new ClienteSlidePagerAdapter(getFragmentManager());
        viewPagerClientes.setAdapter(mClienteSlidePagerAdapter);
        viewPagerClientes.setOnPageChangeListener(mClienteSlidePagerAdapter);

        lnRutaFranja = (LinearLayout) rootView.findViewById(R.id.lnRutaFranja);
        lnRutaFranja.setVisibility(View.VISIBLE);
        txtRutaTitulo = (TextView) rootView.findViewById(R.id.txtRutaTitulo);
        txtRutaTitulo.setTextColor(getResources().getColor(R.color.celeste));
        final Calendar c = Calendar.getInstance();
        txtRutaTitulo.setText("RUTA " + Util.formatoFechaMes(c.getTime()));

        lnRutaCliente = (LinearLayout) rootView.findViewById(R.id.lnRutaCliente);
        lnRutaCliente.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                viewPagerClientes.setCurrentItem(Constantes.RUTA);
                base.getClienteRutaFragment().reset();
                final Calendar c = Calendar.getInstance();
                txtRutaTitulo.setText("RUTA " + Util.formatoFechaMes(c.getTime()));
            }
        });
        lnGeneralCliente = (LinearLayout) rootView.findViewById(R.id.lnGeneralCliente);
        lnGeneralCliente.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                viewPagerClientes.setCurrentItem(Constantes.GENERAL);
                base.getClienteGeneralFragment().reset();
            }
        });

        lnNuevoCliente = (LinearLayout) rootView.findViewById(R.id.lnNuevoCliente);
        lnNuevoCliente.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                viewPagerClientes.setCurrentItem(Constantes.NUEVO);
                if (base.getClienteNuevoFragment() != null) {
                    base.getClienteNuevoFragment().reset();
                }
            }
        });

        if (tab > 0) {
            viewPagerClientes.setCurrentItem(tab);
        }

        if(pedido)
        {
            lnNuevoCliente.setVisibility(View.GONE);
        }
        if(base!=null) {
            base.setTitle("Clientes" + " " + sincronizacionServicio.Mensaje);
            base.invalidateOptionsMenu();
            base.setLayout(menu[tab]);
        }
        return rootView;
    }

    public void limpiarseleccionado() {
        lnRutaFranja = (LinearLayout) rootView.findViewById(R.id.lnRutaFranja);
        lnRutaFranja.setVisibility(View.INVISIBLE);

        lnGeneralFranja = (LinearLayout) rootView.findViewById(R.id.lnGeneralFranja);
        lnGeneralFranja.setVisibility(View.INVISIBLE);

        lnNuevoFranja = (LinearLayout) rootView.findViewById(R.id.lnNuevoFranja);
        lnNuevoFranja.setVisibility(View.INVISIBLE);

        txtRutaTitulo = (TextView) rootView.findViewById(R.id.txtRutaTitulo);
        txtRutaTitulo.setTextColor(getResources().getColor(R.color.plomomenu));
        txtGeneralTitulo = (TextView) rootView.findViewById(R.id.txtGeneralTitulo);
        txtGeneralTitulo.setTextColor(getResources().getColor(R.color.plomomenu));
        txtNuevoTitulo = (TextView) rootView.findViewById(R.id.txtNuevoTitulo);
        txtNuevoTitulo.setTextColor(getResources().getColor(R.color.plomomenu));
    }

    public class ClienteSlidePagerAdapter extends FragmentStatePagerAdapter implements ViewPager.OnPageChangeListener {
        public ClienteSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            if (position == Constantes.RUTA) {
                return ClienteRutaFragment.nuevaInstancia(base ,pedido);
            } else if (position == Constantes.GENERAL) {
                return ClienteGeneralFragment.nuevaInstancia(base, pedido);
            } else if (position == Constantes.NUEVO) {
                return ClienteNuevoFragment.nuevaInstancia(base);
            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_TABS;
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }

        public ClienteFragment getFragment() {
            return ClienteFragment.this;
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageSelected(int position) {
            base.setTitle("Clientes" + " " + sincronizacionServicio.Mensaje);
            base.invalidateOptionsMenu();
            base.setLayout(menu[position]);
            LinearLayout lnfranja = null;
            TextView txtTexto = null;
            limpiarseleccionado();
            switch (position) {
                case Constantes.RUTA:
                    lnfranja = (LinearLayout) rootView.findViewById(R.id.lnRutaFranja);
                    txtTexto = (TextView) rootView.findViewById(R.id.txtRutaTitulo);
                    if (base.getClienteRutaFragment() != null) {
                        base.getClienteRutaFragment().reset();
                        final Calendar c = Calendar.getInstance();
                        txtRutaTitulo.setText("RUTA " + Util.formatoFechaMes(c.getTime()));
                    }
                    break;
                case Constantes.GENERAL:
                    lnfranja = (LinearLayout) rootView.findViewById(R.id.lnGeneralFranja);
                    txtTexto = (TextView) rootView.findViewById(R.id.txtGeneralTitulo);
                    if (base.getClienteGeneralFragment() != null) {
                        base.getClienteGeneralFragment().reset();
                    }
                    break;
                case Constantes.NUEVO:
                    lnfranja = (LinearLayout) rootView.findViewById(R.id.lnNuevoFranja);
                    txtTexto = (TextView) rootView.findViewById(R.id.txtNuevoTitulo);
                    if (base.getClienteNuevoFragment() != null) {
                        base.getClienteNuevoFragment().reset();
                    }
                    break;
            }
            lnfranja.setVisibility(View.VISIBLE);
            txtTexto.setTextColor(getResources().getColor(R.color.celeste));

        }
    }

}
