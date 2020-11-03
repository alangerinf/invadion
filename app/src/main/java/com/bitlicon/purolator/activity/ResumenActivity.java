package com.bitlicon.purolator.activity;

import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;

import com.bitlicon.purolator.R;
import com.bitlicon.purolator.fragment.ClienteFragment;
import com.bitlicon.purolator.fragment.ResumenFragment;
import com.bitlicon.purolator.fragment.StockFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import android.content.Intent;

public class ResumenActivity extends BaseActivity {

    public ResumenActivity() {
        super(R.string.resumen, R.menu.resumen_fragment_actions);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSlidingMenu().setMode(SlidingMenu.LEFT);
        getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);

        setContentView(R.layout.activity_general);

        Intent i = getIntent();
        int var = i.getIntExtra("Fragment", 3);

        switch (var)
        {
            case 1:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_main, ClienteFragment.nuevaInstancia(this, 0, false))
                        .commit();
                break;
            case 2:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_main, StockFragment.nuevaInstancia(this))
                        .commit();
                break;
            case 3:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_main,
                                ResumenFragment.nuevaInstancia(this)).commit();
                break;
            default:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_main,
                                ResumenFragment.nuevaInstancia(this)).commit();
                break;

        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int navBarHeight = getNavigationBarHeight();
            findViewById(R.id.content_main).setPadding(0, 0, 0, navBarHeight);
            findViewById(R.id.menu_frame).setPadding(0, 0, 0, navBarHeight);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {

        if (this instanceof SlidingFragmentActivity) {
            SlidingFragmentActivity factivity = (SlidingFragmentActivity) this;
            factivity.showMenu();
        }
    }

    private int getNavigationBarHeight() {
        Resources resources = getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

}
