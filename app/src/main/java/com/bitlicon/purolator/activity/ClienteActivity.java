package com.bitlicon.purolator.activity;

import android.os.Bundle;
import android.util.Log;

import com.bitlicon.purolator.R;
import com.bitlicon.purolator.fragment.ClienteFragment;
import com.bitlicon.purolator.util.Constantes;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;


public class ClienteActivity extends BaseActivity {


    public ClienteActivity() {
        super(R.string.clientes);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int tab = getIntent().getIntExtra(Constantes.TAB, 0);



        getSlidingMenu().setMode(SlidingMenu.LEFT);

        getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);

        setContentView(R.layout.activity_general);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_main, ClienteFragment.nuevaInstancia(this, tab, false))
                .commit();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
