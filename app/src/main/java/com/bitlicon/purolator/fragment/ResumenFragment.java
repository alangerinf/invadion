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


public class ResumenFragment extends Fragment {


    private static final int NUM_PAGES = 2;
    private BaseActivity base;
    private View rootView;
    private ViewPager viewPagerResumen;
    private ResumenSlidePagerAdapter mResumenSlidePagerAdapter;
    private TextView txtPedidos;
    private SincronizacionServicio sincronizacionServicio;
    public static final String MOVIMIENTO = "Movimiento";
    public static ResumenFragment nuevaInstancia(BaseActivity base) {
        ResumenFragment h = new ResumenFragment();
        base.setTitle(R.string.resumen);
        base.setLayout(R.menu.resumen_fragment_actions);
        base.invalidateOptionsMenu();
        h.base = base;
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
        rootView = inflater.inflate(R.layout.fragment_resumen, container, false);

        SincronizacionServicioDAO sincronizacionServicioDAO = new SincronizacionServicioDAO(getActivity());
        sincronizacionServicio = sincronizacionServicioDAO.buscarServicio(MOVIMIENTO);

        viewPagerResumen = (ViewPager) rootView.findViewById(R.id.viewPagerResumen);

        mResumenSlidePagerAdapter = new ResumenSlidePagerAdapter(getFragmentManager());
        viewPagerResumen.setAdapter(mResumenSlidePagerAdapter);
        viewPagerResumen.setOnPageChangeListener(mResumenSlidePagerAdapter);


        LinearLayout lnPedidosFranja = (LinearLayout) rootView.findViewById(R.id.lnPedidosFranja);
        lnPedidosFranja.setVisibility(View.VISIBLE);
        txtPedidos = (TextView) rootView.findViewById(R.id.txtPedidos);
        txtPedidos.setTextColor(getResources().getColor(R.color.celeste));


        LinearLayout lnPedidos = (LinearLayout) rootView.findViewById(R.id.lnPedidos);
        lnPedidos.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                viewPagerResumen.setCurrentItem(0);
            }
        });

        LinearLayout lnCobranza = (LinearLayout) rootView.findViewById(R.id.lnCobranza);
        lnCobranza.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                viewPagerResumen.setCurrentItem(1);
            }
        });



        base.setTitle("Resumen" + " " + sincronizacionServicio.Mensaje);
        return rootView;
    }


    public void limpiarseleccionado() {
        LinearLayout lnPedidosFranja = (LinearLayout) rootView.findViewById(R.id.lnPedidosFranja);
        lnPedidosFranja.setVisibility(View.INVISIBLE);

        LinearLayout lnCobranzaFranja = (LinearLayout) rootView.findViewById(R.id.lnCobranzaFranja);
        lnCobranzaFranja.setVisibility(View.INVISIBLE);

        TextView txtPedidos = (TextView) rootView.findViewById(R.id.txtPedidos);
        txtPedidos.setTextColor(getResources().getColor(R.color.plomomenu));
        TextView txtCobranza = (TextView) rootView.findViewById(R.id.txtCobranza);
        txtCobranza.setTextColor(getResources().getColor(R.color.plomomenu));

    }

    public class ResumenSlidePagerAdapter extends FragmentStatePagerAdapter implements ViewPager.OnPageChangeListener {
        public ResumenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new PedidosFragment();
            } else if (position == 1) {
                return new CobranzaFragment();
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
                    lnfranja = (LinearLayout) rootView.findViewById(R.id.lnPedidosFranja);
                    txtTexto = (TextView) rootView.findViewById(R.id.txtPedidos);
                    break;
                case 1:
                    lnfranja = (LinearLayout) rootView.findViewById(R.id.lnCobranzaFranja);
                    txtTexto = (TextView) rootView.findViewById(R.id.txtCobranza);
                    break;
            }
            lnfranja.setVisibility(View.VISIBLE);
            txtTexto.setTextColor(getResources().getColor(R.color.celeste));
        }
    }

}
