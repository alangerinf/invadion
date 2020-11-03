package com.bitlicon.purolator.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bitlicon.purolator.R;
import com.bitlicon.purolator.activity.BaseActivity;
import com.bitlicon.purolator.adapter.MenuUsuarioAdapter;
import com.bitlicon.purolator.entities.Opcion;
import com.bitlicon.purolator.lib.SessionManager;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import java.util.ArrayList;


public class MenuListFragment extends Fragment {


    private TextView txtNombreUsuario;
    private SessionManager sessionManager;
    private ListView lstOpciones;
    private MenuUsuarioAdapter mUsuarioAdapter;
    private Button btnSalir;
    private Button btnConfiguracion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        sessionManager = new SessionManager(getActivity().getApplicationContext());

        View root = inflater.inflate(R.layout.menu_usuario, container, false);

        txtNombreUsuario = (TextView) root.findViewById(R.id.txtNombreUsuario);


        txtNombreUsuario.setText(sessionManager.getNombreUsuario());

        btnConfiguracion = (Button) root.findViewById(R.id.btnConfiguracion);
        btnSalir = (Button) root.findViewById(R.id.btnSalir);

        lstOpciones = (ListView) root.findViewById(R.id.lstOpciones);

        ArrayList<Opcion> opciones = new ArrayList<Opcion>();

        Opcion opcion1 = new Opcion();
        opcion1.setIdOpcion(0);
        opcion1.setNombreOpcion("Clientes");
        opciones.add(opcion1);
        Opcion opcion2 = new Opcion();
        opcion2.setIdOpcion(1);
        opcion2.setNombreOpcion("Pedidos");
        opciones.add(opcion2);
        Opcion opcion3 = new Opcion();
        opcion3.setIdOpcion(2);
        opcion3.setNombreOpcion("Stock");
        opciones.add(opcion3);
        Opcion opcion4 = new Opcion();
        opcion4.setIdOpcion(3);
        opcion4.setNombreOpcion("Resumen");
        opciones.add(opcion4);

        mUsuarioAdapter = new MenuUsuarioAdapter(getActivity().getApplicationContext(), opciones);

        lstOpciones.setAdapter(mUsuarioAdapter);

        lstOpciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                Opcion opcion = (Opcion) parent.getItemAtPosition(position);
                if (getActivity() instanceof SlidingFragmentActivity) {
                    SlidingFragmentActivity factivity = (SlidingFragmentActivity) getActivity();
                    factivity.showContent();
                }
                switch (opcion.getIdOpcion()) {
                    case 0: // Clientes
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.content_main, ClienteFragment.nuevaInstancia((BaseActivity) getActivity(), 0, false))
                                .commit();
                        break;
                    case 1:
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.content_main, ClienteFragment.nuevaInstancia((BaseActivity) getActivity(),0 , true))
                                .commit();
                        break;
                    case 2:
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.content_main, StockFragment.nuevaInstancia((BaseActivity) getActivity()))
                                .commit();
                        break;
                    case 3: // Resumen
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.content_main, ResumenFragment.nuevaInstancia((BaseActivity) getActivity()))
                                .commit();
                        break;

                }
            }
        });

        btnConfiguracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getActivity() instanceof SlidingFragmentActivity) {
                    SlidingFragmentActivity factivity = (SlidingFragmentActivity) getActivity();
                    factivity.showContent();
                }

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_main, ConfiguracionFragment.nuevaInstancia((BaseActivity) getActivity()))
                        .commit();
            }
        });

        btnSalir.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Confirmación");
                builder.setMessage("¿Esta seguro de salir de la aplicación?");

                builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        sessionManager.logoutUser();
                        getActivity().finish();
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
        });

        return root;
    }
}
