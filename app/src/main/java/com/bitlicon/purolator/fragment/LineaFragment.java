package com.bitlicon.purolator.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bitlicon.purolator.R;
import com.bitlicon.purolator.activity.ResumenActivity;
import com.bitlicon.purolator.entities.Vendedor;
import com.bitlicon.purolator.lib.SessionManager;
import com.bitlicon.purolator.util.Constantes;


public class LineaFragment extends Fragment {

    public static final String VENDEDOR = "vendedor";
    private int position = 0;
    private View root;

    // TODO: Rename and change types of parameters
    private Vendedor vendedor;
    private String mParam2;


    private int[] gallery = {R.drawable.ic_purolator, R.drawable.ic_filtech};
    private int[] option = {R.id.slide1, R.id.slide2};
    private OnFragmentInteractionListener mListener;


    public LineaFragment() {
    }

    public static LineaFragment newInstance(Vendedor vendedor) {
        LineaFragment fragment = new LineaFragment();
        Bundle args = new Bundle();
        args.putSerializable(VENDEDOR, vendedor);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            vendedor = (Vendedor) getArguments().getSerializable(VENDEDOR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        root = inflater.inflate(R.layout.fragment_linea, container, false);

        Button btnIngresar = (Button) root.findViewById(R.id.btnIngresar);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarLinea();
            }
        });


        ViewPager pager = (ViewPager) root.findViewById(R.id.slider);
        PagerAdapter adapter = new HomeSlidePagerAdapter(((FragmentActivity) getActivity()).getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.setOnPageChangeListener((ViewPager.OnPageChangeListener) adapter);

        return root;
    }

    private void validarLinea() {
        String linea = "";
        switch (position) {
            case Constantes.PUROLATOR:
                linea = vendedor.Linea1;
                break;
            case Constantes.FILTECH:
                linea = vendedor.Linea2;
                break;
        }

        SessionManager sessionManager = new SessionManager(getActivity().getApplicationContext());
        // sessionManager.createLoginSession(vendedor.Usuario, vendedor.Nombre, linea, position);
        Intent intent = new Intent(getActivity().getApplicationContext(), ResumenActivity.class);
        startActivity(intent);


    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    public class HomeSlidePagerAdapter extends FragmentStatePagerAdapter implements ViewPager.OnPageChangeListener {
        public HomeSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public androidx.core.app.Fragment getItem(int position) {

            return ImageFragment.getInstance(position);
        }

        @Override
        public int getCount() {
            return 2;
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
            // TODO Auto-generated method stub
            LinearLayout linearLayout = null;
            switch (LineaFragment.this.position) {
                case 0:
                    linearLayout = (LinearLayout) root.findViewById(R.id.slide1);
                    break;
                case 1:
                    linearLayout = (LinearLayout) root.findViewById(R.id.slide2);
                    break;

            }
            linearLayout.setBackground(getResources().getDrawable(R.drawable.ic_off));
            LineaFragment.this.position = position;

            root.findViewById(option[position]).setBackground(getActivity().getResources().getDrawable(R.drawable.ic_on));

        }
    }


}

