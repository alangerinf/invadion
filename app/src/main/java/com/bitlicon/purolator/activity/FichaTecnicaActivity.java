package com.bitlicon.purolator.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bitlicon.purolator.R;
import com.bitlicon.purolator.entities.Cliente;
import com.bitlicon.purolator.entities.Producto;
import com.bitlicon.purolator.lib.SessionManager;
import com.bitlicon.purolator.util.Util;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.w3c.dom.Text;

public class FichaTecnicaActivity extends ControlActivity {

    private Producto producto;
    private ImageView imgProducto;
    private ImageView imgFull;
    private TextView txtDiamInt;
    private TextView txtDiamExt;
    private TextView txtAltura;
    private TextView txtLongitud;
    private TextView txtAncho;
    private TextView txtRosca;
    private TextView txtPrimario;
    private TextView txtValvulaDerivacion;
    private TextView txtValvulaAntiRetorno;
    private TextView txtDiametroExteriorMax;
    private TextView txtDiametroExteriorMin;
    private TextView txtDiametroInteriorMax;
    private TextView txtDiametroInteriorMin;
    private TextView txtFiltro;
    private EditText txtAplicacion;
    private TextView txtClase;
    private TextView txtSubClase;
    private TextView txtLinea;
    private TextView txtForma;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.ficha_tecnica_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                FichaTecnicaActivity.super.onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_tecnica);
        Util.setIconMenu(R.drawable.ic_back, this);
        setTitle(R.string.ficha_tecnica);

        imgProducto = (ImageView)findViewById(R.id.imgProducto);
        txtDiamInt = (TextView)findViewById(R.id.txtDiamInt);
        txtDiamExt = (TextView)findViewById(R.id.txtDiamExt);
        txtAltura = (TextView)findViewById(R.id.txtAltura);
        txtLongitud = (TextView)findViewById(R.id.txtLongitud);
        txtAncho = (TextView)findViewById(R.id.txtAncho);
        txtRosca = (TextView)findViewById(R.id.txtRosca);
        txtAplicacion = (EditText)findViewById(R.id.txtAplicacion);
        txtPrimario = (TextView)findViewById(R.id.txtPrimario);
        txtValvulaDerivacion = (TextView)findViewById(R.id.txtValvulaDerivacion);
        txtValvulaAntiRetorno = (TextView)findViewById(R.id.txtValvulaAntiRetorno);
        txtDiametroExteriorMax = (TextView)findViewById(R.id.txtDiametroExteriorMax);
        txtDiametroExteriorMin = (TextView)findViewById(R.id.txtDiametroExteriorMin);
        txtDiametroInteriorMax = (TextView)findViewById(R.id.txtDiametroInteriorMax);
        txtDiametroInteriorMin = (TextView)findViewById(R.id.txtDiametroInteriorMin);
        txtFiltro = (TextView)findViewById(R.id.txtFiltro);
        txtClase = (TextView)findViewById(R.id.txtClase);
        txtSubClase = (TextView)findViewById(R.id.txtSubClase);
        txtLinea = (TextView)findViewById(R.id.txtLinea);
        txtForma = (TextView)findViewById(R.id.txtForma);
        imgFull = (ImageView) findViewById(R.id.imgFullview);
        producto= (Producto) getIntent().getSerializableExtra("producto");

        txtDiamInt.setText("Diametro Interior: " + producto.DiametroInterior);
        txtDiamExt.setText("Diametro Exterior: " + producto.DiametroExterior);
        txtAltura.setText("Altura: " + String.valueOf(producto.Altura));
        txtLongitud.setText("Longitud: " + String.valueOf(producto.Longitud));
        txtAncho.setText("Ancho: " + String.valueOf(producto.Ancho));
        txtRosca.setText("Rosca: " + String.valueOf(producto.Rosca));
        txtAplicacion.setSingleLine(false);
        txtAplicacion.setText(String.valueOf(producto.Aplicaciones));
        txtDiametroInteriorMin.setText("Diámetro Interior  Mín.: " + producto.DiametroInteriorMinimo);
        txtDiametroInteriorMax.setText("Diámetro Interior  Máx.: " + producto.DiametroInteriorMaximo);
        txtDiametroExteriorMin.setText("Diámetro Exterior  Mín.: " + producto.DiametroExteriorMinimo);
        txtDiametroExteriorMax.setText("Diámetro Exterior  Máx.: " + producto.DiametroExteriorMaximo);
        if(producto.ValvulaAntiRetorno)
        {
            txtValvulaAntiRetorno.setText("Válvula Anti Retorno : " + "SI");
        }
        else
        {
            txtValvulaAntiRetorno.setText("Válvula Anti Retorno : " + "NO");
        }
        if(producto.ValvulaDerivacion)
        {
            txtValvulaDerivacion.setText("Válvula Derivacion : " + "SI");
        }
        else
        {
            txtValvulaDerivacion.setText("Válvula Derivacion : " + "NO");
        }

        txtPrimario.setText("Primario: " + producto.CodigoPrimario);
        txtFiltro.setText("Filtro: " + producto.Nombre);
        if(producto.ClaseWeb.equals("")) {
            txtClase.setText("Clase: " + producto.Clase);
        }else
        {
            txtClase.setText("Clase: " + producto.ClaseWeb);
        }
        if(producto.SubClaseWeb.equals("")) {
            txtSubClase.setText("Sub Clase: " + producto.SubClase);
        }else
        {
            txtSubClase.setText("Sub Clase: " + producto.SubClaseWeb);
        }
        txtLinea.setText("Linea: " + producto.Linea);
        if(producto.Forma!=null)
        {
            txtForma.setText("Forma: " + producto.Forma);
        }
        else
        {
            txtForma.setText("Forma: ");
        }

        DisplayImageOptions options1 = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.loading45)
                .showImageOnFail(R.drawable.nd)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        String filePathimg = "http://www.purolator.com.pe/Catalogo/Fotos/" + producto.Nombre + ".jpg";
        ImageLoader.getInstance().displayImage(filePathimg, imgProducto, options1);

        imgProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imgFull.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imgFull.setImageDrawable(imgProducto.getDrawable());

                imgFull.setVisibility(View.VISIBLE);
            }
        });

        imgFull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgFull.setVisibility(View.GONE);
            }
        });
    }


}
