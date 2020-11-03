package com.bitlicon.purolator.activity;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;

import com.bitlicon.purolator.R;

import com.bitlicon.purolator.dao.LetraPedidoDAO;
import com.bitlicon.purolator.entities.LetraPedido;
import com.bitlicon.purolator.entities.Pedido;
import com.bitlicon.purolator.util.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class SeleccionDiaFacturaActivity extends ControlFragmentActivity {

    private Pedido pedido;
    private CalendarView cFechaSeleccionada;
    private Calendar today;
    private EditText txtFecha1, txtFecha2, txtFecha3, txtFecha4, txtFecha5, txtFecha6, txtFecha7, txtFecha8, txtFecha9, txtFecha10, txtFecha11, txtFecha12, txtFecha13, txtFecha14, txtFecha15;
    LetraPedidoDAO letraPedidoDAO;
    LetraPedido letraPedido;
    Calendar fechaCalendar;
    Date fechaSeleccionadaDate;
    long fechaSeleccionada, inicioTime, diffTime, diffDays;
    Date hoyDate;
    boolean b1c = false, b1t = false, b2c = false, b2t = false, b3c = false, b3t = false;
    boolean b4c = false, b4t = false, b5c = false, b5t = false, b6c = false, b6t = false;
    boolean b7c = false, b7t = false, b8c = false, b8t = false, b9c = false, b9t = false;
    boolean b10c = false, b10t = false, b11c = false, b11t = false, b12c = false, b12t = false;
    boolean b13c = false, b13t = false, b14c = false, b14t = false, b15c = false, b15t = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pedido = (Pedido) getIntent().getSerializableExtra("pedido");
        letraPedidoDAO = new LetraPedidoDAO(getApplicationContext());
        letraPedido = new LetraPedido();
        letraPedido.NumeroPedido = pedido.NumeroPedido;
        letraPedido.iPedido = pedido.iPedido;
        setContentView(R.layout.activity_seleccion_dia_factura);
        Util.setIconMenu(R.drawable.ic_back, this);

        setTitle("N°: " + pedido.NumeroPedido);

        today = Calendar.getInstance();

        cFechaSeleccionada = (CalendarView) findViewById(R.id.cFechaSeleccionada);
        txtFecha1 = (EditText) findViewById(R.id.txtFecha1);
        txtFecha2 = (EditText) findViewById(R.id.txtFecha2);
        txtFecha3 = (EditText) findViewById(R.id.txtFecha3);
        txtFecha4 = (EditText) findViewById(R.id.txtFecha4);
        txtFecha5 = (EditText) findViewById(R.id.txtFecha5);
        txtFecha6 = (EditText) findViewById(R.id.txtFecha6);
        txtFecha7 = (EditText) findViewById(R.id.txtFecha7);
        txtFecha8 = (EditText) findViewById(R.id.txtFecha8);
        txtFecha9 = (EditText) findViewById(R.id.txtFecha9);
        txtFecha10 = (EditText) findViewById(R.id.txtFecha10);
        txtFecha11 = (EditText) findViewById(R.id.txtFecha11);
        txtFecha12 = (EditText) findViewById(R.id.txtFecha12);
        txtFecha13 = (EditText) findViewById(R.id.txtFecha13);
        txtFecha14 = (EditText) findViewById(R.id.txtFecha14);
        txtFecha15 = (EditText) findViewById(R.id.txtFecha15);

        if (pedido.TerminoVenta.equals("05")) // Credito Facturas
        {
            txtFecha2.setEnabled(false);
            txtFecha3.setEnabled(false);
            txtFecha4.setEnabled(false);
            txtFecha5.setEnabled(false);

            txtFecha6.setEnabled(false);
            txtFecha6.setVisibility(View.GONE);
            txtFecha7.setEnabled(false);
            txtFecha7.setVisibility(View.GONE);
            txtFecha8.setEnabled(false);
            txtFecha8.setVisibility(View.GONE);
            txtFecha9.setEnabled(false);
            txtFecha9.setVisibility(View.GONE);
            txtFecha10.setEnabled(false);
            txtFecha10.setVisibility(View.GONE);
            txtFecha11.setEnabled(false);
            txtFecha11.setVisibility(View.GONE);
            txtFecha12.setEnabled(false);
            txtFecha12.setVisibility(View.GONE);
            txtFecha13.setEnabled(false);
            txtFecha13.setVisibility(View.GONE);
            txtFecha14.setEnabled(false);
            txtFecha14.setVisibility(View.GONE);
            txtFecha15.setEnabled(false);
            txtFecha15.setVisibility(View.GONE);
        }

        if (pedido.Enviado) {
            //txtFecha1.setFocusable(false);
            //txtFecha2.setFocusable(false);
            //txtFecha3.setFocusable(false);
        }
        hoyDate = today.getTime();

        // Se hizo una verificación de versión por el problema de calendario en versiones anteriores.
        if (Build.VERSION.SDK_INT<20) {
            cFechaSeleccionada.setEnabled(false);
        }
        cFechaSeleccionada.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                                                       @Override
                                                       public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                                                           if (android.os.Build.VERSION.SDK_INT>=20) {
                                                               if (!pedido.Enviado) {

                                                                   b1c = true;
                                                                   b2c = true;
                                                                   b3c = true;
                                                                   b4c = true;
                                                                   fechaCalendar = Calendar.getInstance();
                                                                   fechaCalendar.set(year, month, dayOfMonth);
                                                                   fechaSeleccionadaDate = fechaCalendar.getTime();
                                                                   fechaSeleccionada = fechaSeleccionadaDate.getTime();
                                                                   inicioTime = hoyDate.getTime();
                                                                   diffTime = fechaSeleccionada - inicioTime;
                                                                   diffDays = diffTime / (1000 * 60 * 60 * 24);
                                                                   if (!b1t) {
                                                                       if (txtFecha1.isFocused()) {
                                                                           txtFecha1.setText(String.valueOf(diffDays));
                                                                       }
                                                                   } else {
                                                                       b1t = false;
                                                                   }
                                                                   if (!b2t) {
                                                                       if (txtFecha2.isFocused()) {
                                                                           txtFecha2.setText(String.valueOf(diffDays));
                                                                       }
                                                                   } else {
                                                                       b2t = false;
                                                                   }
                                                                   if (!b3t) {
                                                                       if (txtFecha3.isFocused()) {
                                                                           txtFecha3.setText(String.valueOf(diffDays));
                                                                       }
                                                                   } else {
                                                                       b3t = false;
                                                                   }
                                                                   if (!b4t) {
                                                                       if (txtFecha4.isFocused()) {
                                                                           txtFecha4.setText(String.valueOf(diffDays));
                                                                       }
                                                                   } else {
                                                                       b4t = false;
                                                                   }
                                                                   if (!b5t) {
                                                                       if (txtFecha5.isFocused()) {
                                                                           txtFecha5.setText(String.valueOf(diffDays));
                                                                       }
                                                                   } else {
                                                                       b5t = false;
                                                                   }
                                                                   if (!b6t) {
                                                                       if (txtFecha6.isFocused()) {
                                                                           txtFecha6.setText(String.valueOf(diffDays));
                                                                       }
                                                                   } else {
                                                                       b6t = false;
                                                                   }
                                                                   if (!b7t) {
                                                                       if (txtFecha7.isFocused()) {
                                                                           txtFecha7.setText(String.valueOf(diffDays));
                                                                       }
                                                                   } else {
                                                                       b7t = false;
                                                                   }
                                                                   if (!b8t) {
                                                                       if (txtFecha8.isFocused()) {
                                                                           txtFecha8.setText(String.valueOf(diffDays));
                                                                       }
                                                                   } else {
                                                                       b8t = false;
                                                                   }
                                                                   if (!b9t) {
                                                                       if (txtFecha9.isFocused()) {
                                                                           txtFecha9.setText(String.valueOf(diffDays));
                                                                       }
                                                                   } else {
                                                                       b9t = false;
                                                                   }
                                                                   if (!b10t) {
                                                                       if (txtFecha10.isFocused()) {
                                                                           txtFecha10.setText(String.valueOf(diffDays));
                                                                       }
                                                                   } else {
                                                                       b10t = false;
                                                                   }
                                                                   if (!b11t) {
                                                                       if (txtFecha11.isFocused()) {
                                                                           txtFecha11.setText(String.valueOf(diffDays));
                                                                       }
                                                                   } else {
                                                                       b11t = false;
                                                                   }
                                                                   if (!b12t) {
                                                                       if (txtFecha12.isFocused()) {
                                                                           txtFecha12.setText(String.valueOf(diffDays));
                                                                       }
                                                                   } else {
                                                                       b12t = false;
                                                                   }
                                                                   if (!b13t) {
                                                                       if (txtFecha13.isFocused()) {
                                                                           txtFecha13.setText(String.valueOf(diffDays));
                                                                       }
                                                                   } else {
                                                                       b13t = false;
                                                                   }
                                                                   if (!b14t) {
                                                                       if (txtFecha14.isFocused()) {
                                                                           txtFecha14.setText(String.valueOf(diffDays));
                                                                       }
                                                                   } else {
                                                                       b14t = false;
                                                                   }
                                                                   if (!b15t) {
                                                                       if (txtFecha15.isFocused()) {
                                                                           txtFecha15.setText(String.valueOf(diffDays));
                                                                       }
                                                                   } else {
                                                                       b15t = false;
                                                                   }
                                                               }
                                                           }

                                                       }
                                                   }

        );


        txtFecha1.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                if (!pedido.Enviado) {
                    letraPedido.Numero = 1;
                    if (s.length() == 0) {
                        letraPedidoDAO.eliminarLetraPedido(letraPedido);
                    } else {
                        letraPedido.Dia = Integer.parseInt(s.toString());
                        Calendar cal = Calendar.getInstance();
                        cal.add(Calendar.DATE, letraPedido.Dia);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        letraPedido.Fecha = dateFormat.format(cal.getTime());
                        if (letraPedidoDAO.verificarLetraPedido(letraPedido)) {
                            letraPedidoDAO.actualizarDia(letraPedido);
                        } else {
                            letraPedido.Picker = false;
                            letraPedidoDAO.registrarLetraPedido(letraPedido);
                        }
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!b1c) {
                    if (s.length() == 0) {
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        b1t = true;
                        cFechaSeleccionada.setDate(inicioTime);
                    } else {
                        long dias = Long.parseLong(s.toString()) * 1000 * 60 * 60 * 24;
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        long aumentar = inicioTime + dias;
                        b1t = true;
                        cFechaSeleccionada.setDate(aumentar);
                    }
                } else {
                    b1c = false;
                }
            }
        });

        txtFecha1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (txtFecha1.getText().length() == 0) {
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        cFechaSeleccionada.setDate(inicioTime);
                    } else {
                        long dias = Long.parseLong(txtFecha1.getText().toString()) * 1000 * 60 * 60 * 24;
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        long aumentar = inicioTime + dias;
                        cFechaSeleccionada.setDate(aumentar);
                    }
                }
            }
        });
        LetraPedido letraPedido1 = new LetraPedido();
        letraPedido1.iPedido = pedido.iPedido;
        letraPedido1.NumeroPedido = pedido.NumeroPedido;
        letraPedido1.Numero = 1;
        letraPedido1 = letraPedidoDAO.obtenerLetraPedido(letraPedido1);
        if (letraPedido1 != null) {
            txtFecha1.setText(String.valueOf(letraPedido1.Dia));
        }

        txtFecha2.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                if (!pedido.Enviado) {
                    letraPedido.Numero = 2;
                    if (s.length() == 0) {
                        letraPedidoDAO.eliminarLetraPedido(letraPedido);
                    } else {
                        letraPedido.Dia = Integer.parseInt(s.toString());
                        Calendar cal = Calendar.getInstance();
                        cal.add(Calendar.DATE, letraPedido.Dia);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        letraPedido.Fecha = dateFormat.format(cal.getTime());
                        if (letraPedidoDAO.verificarLetraPedido(letraPedido)) {
                            letraPedidoDAO.actualizarDia(letraPedido);
                        } else {
                            letraPedido.Picker = false;
                            letraPedidoDAO.registrarLetraPedido(letraPedido);
                        }
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!b2c) {
                    if (s.length() == 0) {
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        cFechaSeleccionada.setDate(inicioTime);
                    } else {
                        long dias = Long.parseLong(s.toString()) * 1000 * 60 * 60 * 24;
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        long aumentar = inicioTime + dias;
                        cFechaSeleccionada.setDate(aumentar);
                    }
                } else {
                    b2c = false;
                }
            }
        });

        txtFecha2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (txtFecha2.getText().length() == 0) {
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        cFechaSeleccionada.setDate(inicioTime);
                    } else {
                        long dias = Long.parseLong(txtFecha2.getText().toString()) * 1000 * 60 * 60 * 24;
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        long aumentar = inicioTime + dias;
                        cFechaSeleccionada.setDate(aumentar);
                    }
                }
            }
        });

        LetraPedido letraPedido2 = new LetraPedido();
        letraPedido2.iPedido = pedido.iPedido;
        letraPedido2.NumeroPedido = pedido.NumeroPedido;
        letraPedido2.Numero = 2;
        letraPedido2 = letraPedidoDAO.obtenerLetraPedido(letraPedido2);
        if (letraPedido2 != null) {
            txtFecha2.setText(String.valueOf(letraPedido2.Dia));
        }

        txtFecha3.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                if (!pedido.Enviado) {
                    letraPedido.Numero = 3;
                    if (s.length() == 0) {
                        letraPedidoDAO.eliminarLetraPedido(letraPedido);
                    } else {
                        letraPedido.Dia = Integer.parseInt(s.toString());
                        Calendar cal = Calendar.getInstance();
                        cal.add(Calendar.DATE, letraPedido.Dia);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        letraPedido.Fecha = dateFormat.format(cal.getTime());
                        if (letraPedidoDAO.verificarLetraPedido(letraPedido)) {
                            letraPedidoDAO.actualizarDia(letraPedido);
                        } else {
                            letraPedido.Picker = false;
                            letraPedidoDAO.registrarLetraPedido(letraPedido);
                        }
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!b3c) {
                    if (s.length() == 0) {
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        cFechaSeleccionada.setDate(inicioTime);
                    } else {
                        long dias = Long.parseLong(s.toString()) * 1000 * 60 * 60 * 24;
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        long aumentar = inicioTime + dias;
                        cFechaSeleccionada.setDate(aumentar);
                    }
                } else {
                    b3c = false;
                }
            }
        });

        txtFecha3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (txtFecha3.getText().length() == 0) {
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        cFechaSeleccionada.setDate(inicioTime);
                    } else {
                        long dias = Long.parseLong(txtFecha3.getText().toString()) * 1000 * 60 * 60 * 24;
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        long aumentar = inicioTime + dias;
                        cFechaSeleccionada.setDate(aumentar);
                    }
                }
            }
        });
        LetraPedido letraPedido3 = new LetraPedido();
        letraPedido3.iPedido = pedido.iPedido;
        letraPedido3.NumeroPedido = pedido.NumeroPedido;
        letraPedido3.Numero = 3;
        letraPedido3 = letraPedidoDAO.obtenerLetraPedido(letraPedido3);
        if (letraPedido3 != null) {
            txtFecha3.setText(String.valueOf(letraPedido3.Dia));
        }

        txtFecha4.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                if (!pedido.Enviado) {
                    letraPedido.Numero = 4;
                    if (s.length() == 0) {
                        letraPedidoDAO.eliminarLetraPedido(letraPedido);
                    } else {

                        letraPedido.Dia = Integer.parseInt(s.toString());
                        Calendar cal = Calendar.getInstance();
                        cal.add(Calendar.DATE, letraPedido.Dia);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        letraPedido.Fecha = dateFormat.format(cal.getTime());
                        if (letraPedidoDAO.verificarLetraPedido(letraPedido)) {
                            letraPedidoDAO.actualizarDia(letraPedido);
                        } else {
                            letraPedido.Picker = false;
                            letraPedidoDAO.registrarLetraPedido(letraPedido);
                        }
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!b4c) {
                    if (s.length() == 0) {
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        cFechaSeleccionada.setDate(inicioTime);
                    } else {
                        long dias = Long.parseLong(s.toString()) * 1000 * 60 * 60 * 24;
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        long aumentar = inicioTime + dias;
                        cFechaSeleccionada.setDate(aumentar);
                    }
                } else {
                    b4c = false;
                }
            }
        });

        txtFecha4.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (txtFecha4.getText().length() == 0) {
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        cFechaSeleccionada.setDate(inicioTime);
                    } else {
                        long dias = Long.parseLong(txtFecha4.getText().toString()) * 1000 * 60 * 60 * 24;
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        long aumentar = inicioTime + dias;
                        cFechaSeleccionada.setDate(aumentar);
                    }
                }
            }
        });

        LetraPedido letraPedido4 = new LetraPedido();
        letraPedido4.iPedido = pedido.iPedido;
        letraPedido4.NumeroPedido = pedido.NumeroPedido;
        letraPedido4.Numero = 4;
        letraPedido4 = letraPedidoDAO.obtenerLetraPedido(letraPedido4);
        if (letraPedido4 != null) {
            txtFecha4.setText(String.valueOf(letraPedido4.Dia));
        }

        txtFecha5.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                if (!pedido.Enviado) {
                    letraPedido.Numero = 5;
                    if (s.length() == 0) {
                        letraPedidoDAO.eliminarLetraPedido(letraPedido);
                    } else {

                        letraPedido.Dia = Integer.parseInt(s.toString());
                        Calendar cal = Calendar.getInstance();
                        cal.add(Calendar.DATE, letraPedido.Dia);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        letraPedido.Fecha = dateFormat.format(cal.getTime());
                        if (letraPedidoDAO.verificarLetraPedido(letraPedido)) {
                            letraPedidoDAO.actualizarDia(letraPedido);
                        } else {
                            letraPedido.Picker = false;
                            letraPedidoDAO.registrarLetraPedido(letraPedido);
                        }
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!b5c) {
                    if (s.length() == 0) {
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        cFechaSeleccionada.setDate(inicioTime);
                    } else {
                        long dias = Long.parseLong(s.toString()) * 1000 * 60 * 60 * 24;
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        long aumentar = inicioTime + dias;
                        cFechaSeleccionada.setDate(aumentar);
                    }
                } else {
                    b5c = false;
                }
            }
        });

        txtFecha5.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (txtFecha5.getText().length() == 0) {
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        cFechaSeleccionada.setDate(inicioTime);
                    } else {
                        long dias = Long.parseLong(txtFecha5.getText().toString()) * 1000 * 60 * 60 * 24;
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        long aumentar = inicioTime + dias;
                        cFechaSeleccionada.setDate(aumentar);
                    }
                }
            }
        });

        LetraPedido letraPedido5 = new LetraPedido();
        letraPedido5.iPedido = pedido.iPedido;
        letraPedido5.NumeroPedido = pedido.NumeroPedido;
        letraPedido5.Numero = 5;
        letraPedido5 = letraPedidoDAO.obtenerLetraPedido(letraPedido5);
        if (letraPedido5 != null) {
            txtFecha5.setText(String.valueOf(letraPedido5.Dia));
        }

        txtFecha6.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                if (!pedido.Enviado) {
                    letraPedido.Numero = 6;
                    if (s.length() == 0) {
                        letraPedidoDAO.eliminarLetraPedido(letraPedido);
                    } else {

                        letraPedido.Dia = Integer.parseInt(s.toString());
                        Calendar cal = Calendar.getInstance();
                        cal.add(Calendar.DATE, letraPedido.Dia);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        letraPedido.Fecha = dateFormat.format(cal.getTime());
                        if (letraPedidoDAO.verificarLetraPedido(letraPedido)) {
                            letraPedidoDAO.actualizarDia(letraPedido);
                        } else {
                            letraPedido.Picker = false;
                            letraPedidoDAO.registrarLetraPedido(letraPedido);
                        }
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!b6c) {
                    if (s.length() == 0) {
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        cFechaSeleccionada.setDate(inicioTime);
                    } else {
                        long dias = Long.parseLong(s.toString()) * 1000 * 60 * 60 * 24;
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        long aumentar = inicioTime + dias;
                        cFechaSeleccionada.setDate(aumentar);
                    }
                } else {
                    b6c = false;
                }
            }
        });

        txtFecha6.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (txtFecha6.getText().length() == 0) {
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        cFechaSeleccionada.setDate(inicioTime);
                    } else {
                        long dias = Long.parseLong(txtFecha6.getText().toString()) * 1000 * 60 * 60 * 24;
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        long aumentar = inicioTime + dias;
                        cFechaSeleccionada.setDate(aumentar);
                    }
                }
            }
        });

        LetraPedido letraPedido6 = new LetraPedido();
        letraPedido6.iPedido = pedido.iPedido;
        letraPedido6.NumeroPedido = pedido.NumeroPedido;
        letraPedido6.Numero = 6;
        letraPedido6 = letraPedidoDAO.obtenerLetraPedido(letraPedido6);
        if (letraPedido6 != null) {
            txtFecha6.setText(String.valueOf(letraPedido6.Dia));
        }

        txtFecha7.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                if (!pedido.Enviado) {
                    letraPedido.Numero = 7;
                    if (s.length() == 0) {
                        letraPedidoDAO.eliminarLetraPedido(letraPedido);
                    } else {

                        letraPedido.Dia = Integer.parseInt(s.toString());
                        Calendar cal = Calendar.getInstance();
                        cal.add(Calendar.DATE, letraPedido.Dia);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        letraPedido.Fecha = dateFormat.format(cal.getTime());
                        if (letraPedidoDAO.verificarLetraPedido(letraPedido)) {
                            letraPedidoDAO.actualizarDia(letraPedido);
                        } else {
                            letraPedido.Picker = false;
                            letraPedidoDAO.registrarLetraPedido(letraPedido);
                        }
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!b7c) {
                    if (s.length() == 0) {
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        cFechaSeleccionada.setDate(inicioTime);
                    } else {
                        long dias = Long.parseLong(s.toString()) * 1000 * 60 * 60 * 24;
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        long aumentar = inicioTime + dias;
                        cFechaSeleccionada.setDate(aumentar);
                    }
                } else {
                    b7c = false;
                }
            }
        });

        txtFecha7.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (txtFecha7.getText().length() == 0) {
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        cFechaSeleccionada.setDate(inicioTime);
                    } else {
                        long dias = Long.parseLong(txtFecha7.getText().toString()) * 1000 * 60 * 60 * 24;
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        long aumentar = inicioTime + dias;
                        cFechaSeleccionada.setDate(aumentar);
                    }
                }
            }
        });

        LetraPedido letraPedido7 = new LetraPedido();
        letraPedido7.iPedido = pedido.iPedido;
        letraPedido7.NumeroPedido = pedido.NumeroPedido;
        letraPedido7.Numero = 7;
        letraPedido7 = letraPedidoDAO.obtenerLetraPedido(letraPedido7);
        if (letraPedido7 != null) {
            txtFecha7.setText(String.valueOf(letraPedido7.Dia));
        }

        txtFecha8.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                if (!pedido.Enviado) {
                    letraPedido.Numero = 8;
                    if (s.length() == 0) {
                        letraPedidoDAO.eliminarLetraPedido(letraPedido);
                    } else {

                        letraPedido.Dia = Integer.parseInt(s.toString());
                        Calendar cal = Calendar.getInstance();
                        cal.add(Calendar.DATE, letraPedido.Dia);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        letraPedido.Fecha = dateFormat.format(cal.getTime());
                        if (letraPedidoDAO.verificarLetraPedido(letraPedido)) {
                            letraPedidoDAO.actualizarDia(letraPedido);
                        } else {
                            letraPedido.Picker = false;
                            letraPedidoDAO.registrarLetraPedido(letraPedido);
                        }
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!b8c) {
                    if (s.length() == 0) {
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        cFechaSeleccionada.setDate(inicioTime);
                    } else {
                        long dias = Long.parseLong(s.toString()) * 1000 * 60 * 60 * 24;
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        long aumentar = inicioTime + dias;
                        cFechaSeleccionada.setDate(aumentar);
                    }
                } else {
                    b8c = false;
                }
            }
        });

        txtFecha8.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (txtFecha8.getText().length() == 0) {
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        cFechaSeleccionada.setDate(inicioTime);
                    } else {
                        long dias = Long.parseLong(txtFecha8.getText().toString()) * 1000 * 60 * 60 * 24;
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        long aumentar = inicioTime + dias;
                        cFechaSeleccionada.setDate(aumentar);
                    }
                }
            }
        });

        LetraPedido letraPedido8 = new LetraPedido();
        letraPedido8.iPedido = pedido.iPedido;
        letraPedido8.NumeroPedido = pedido.NumeroPedido;
        letraPedido8.Numero = 8;
        letraPedido8 = letraPedidoDAO.obtenerLetraPedido(letraPedido8);
        if (letraPedido8 != null) {
            txtFecha8.setText(String.valueOf(letraPedido8.Dia));
        }

        txtFecha9.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                letraPedido.Numero = 9;
                if (s.length() == 0) {
                    letraPedidoDAO.eliminarLetraPedido(letraPedido);
                } else {

                    letraPedido.Dia = Integer.parseInt(s.toString());
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DATE, letraPedido.Dia);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    letraPedido.Fecha = dateFormat.format(cal.getTime());
                    if (letraPedidoDAO.verificarLetraPedido(letraPedido)) {
                        letraPedidoDAO.actualizarDia(letraPedido);
                    } else {
                        letraPedido.Picker = false;
                        letraPedidoDAO.registrarLetraPedido(letraPedido);
                    }
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!b9c) {
                    if (s.length() == 0) {
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        cFechaSeleccionada.setDate(inicioTime);
                    } else {
                        long dias = Long.parseLong(s.toString()) * 1000 * 60 * 60 * 24;
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        long aumentar = inicioTime + dias;
                        cFechaSeleccionada.setDate(aumentar);
                    }
                } else {
                    b9c = false;
                }
            }
        });

        txtFecha9.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (txtFecha9.getText().length() == 0) {
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        cFechaSeleccionada.setDate(inicioTime);
                    } else {
                        long dias = Long.parseLong(txtFecha9.getText().toString()) * 1000 * 60 * 60 * 24;
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        long aumentar = inicioTime + dias;
                        cFechaSeleccionada.setDate(aumentar);
                    }
                }
            }
        });

        LetraPedido letraPedido9 = new LetraPedido();
        letraPedido9.iPedido = pedido.iPedido;
        letraPedido9.NumeroPedido = pedido.NumeroPedido;
        letraPedido9.Numero = 9;
        letraPedido9 = letraPedidoDAO.obtenerLetraPedido(letraPedido9);
        if (letraPedido9 != null) {
            txtFecha9.setText(String.valueOf(letraPedido9.Dia));
        }

        txtFecha10.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                letraPedido.Numero = 10;
                if (s.length() == 0) {
                    letraPedidoDAO.eliminarLetraPedido(letraPedido);
                } else {

                    letraPedido.Dia = Integer.parseInt(s.toString());
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DATE, letraPedido.Dia);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    letraPedido.Fecha = dateFormat.format(cal.getTime());
                    if (letraPedidoDAO.verificarLetraPedido(letraPedido)) {
                        letraPedidoDAO.actualizarDia(letraPedido);
                    } else {
                        letraPedido.Picker = false;
                        letraPedidoDAO.registrarLetraPedido(letraPedido);
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!b10c) {
                    if (s.length() == 0) {
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        cFechaSeleccionada.setDate(inicioTime);
                    } else {
                        long dias = Long.parseLong(s.toString()) * 1000 * 60 * 60 * 24;
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        long aumentar = inicioTime + dias;
                        cFechaSeleccionada.setDate(aumentar);
                    }
                } else {
                    b10c = false;
                }
            }
        });

        txtFecha10.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (txtFecha10.getText().length() == 0) {
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        cFechaSeleccionada.setDate(inicioTime);
                    } else {
                        long dias = Long.parseLong(txtFecha10.getText().toString()) * 1000 * 60 * 60 * 24;
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        long aumentar = inicioTime + dias;
                        cFechaSeleccionada.setDate(aumentar);
                    }
                }
            }
        });

        LetraPedido letraPedido10 = new LetraPedido();
        letraPedido10.iPedido = pedido.iPedido;
        letraPedido10.NumeroPedido = pedido.NumeroPedido;
        letraPedido10.Numero = 10;
        letraPedido10 = letraPedidoDAO.obtenerLetraPedido(letraPedido10);
        if (letraPedido10 != null) {
            txtFecha10.setText(String.valueOf(letraPedido10.Dia));
        }

        txtFecha11.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                letraPedido.Numero = 11;
                if (s.length() == 0) {
                    letraPedidoDAO.eliminarLetraPedido(letraPedido);
                } else {

                    letraPedido.Dia = Integer.parseInt(s.toString());
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DATE, letraPedido.Dia);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    letraPedido.Fecha = dateFormat.format(cal.getTime());
                    if (letraPedidoDAO.verificarLetraPedido(letraPedido)) {
                        letraPedidoDAO.actualizarDia(letraPedido);
                    } else {
                        letraPedido.Picker = false;
                        letraPedidoDAO.registrarLetraPedido(letraPedido);
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!b11c) {
                    if (s.length() == 0) {
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        cFechaSeleccionada.setDate(inicioTime);
                    } else {
                        long dias = Long.parseLong(s.toString()) * 1000 * 60 * 60 * 24;
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        long aumentar = inicioTime + dias;
                        cFechaSeleccionada.setDate(aumentar);
                    }
                } else {
                    b11c = false;
                }
            }
        });

        txtFecha11.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (txtFecha11.getText().length() == 0) {
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        cFechaSeleccionada.setDate(inicioTime);
                    } else {
                        long dias = Long.parseLong(txtFecha11.getText().toString()) * 1000 * 60 * 60 * 24;
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        long aumentar = inicioTime + dias;
                        cFechaSeleccionada.setDate(aumentar);
                    }
                }
            }
        });


        LetraPedido letraPedido11 = new LetraPedido();
        letraPedido11.iPedido = pedido.iPedido;
        letraPedido11.NumeroPedido = pedido.NumeroPedido;
        letraPedido11.Numero = 11;
        letraPedido11 = letraPedidoDAO.obtenerLetraPedido(letraPedido11);
        if (letraPedido11 != null) {
            txtFecha11.setText(String.valueOf(letraPedido11.Dia));
        }

        txtFecha12.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                letraPedido.Numero = 12;
                if (s.length() == 0) {
                    letraPedidoDAO.eliminarLetraPedido(letraPedido);
                } else {

                    letraPedido.Dia = Integer.parseInt(s.toString());
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DATE, letraPedido.Dia);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    letraPedido.Fecha = dateFormat.format(cal.getTime());
                    if (letraPedidoDAO.verificarLetraPedido(letraPedido)) {
                        letraPedidoDAO.actualizarDia(letraPedido);
                    } else {
                        letraPedido.Picker = false;
                        letraPedidoDAO.registrarLetraPedido(letraPedido);
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!b12c) {
                    if (s.length() == 0) {
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        cFechaSeleccionada.setDate(inicioTime);
                    } else {
                        long dias = Long.parseLong(s.toString()) * 1000 * 60 * 60 * 24;
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        long aumentar = inicioTime + dias;
                        cFechaSeleccionada.setDate(aumentar);
                    }
                } else {
                    b12c = false;
                }
            }
        });

        txtFecha12.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (txtFecha12.getText().length() == 0) {
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        cFechaSeleccionada.setDate(inicioTime);
                    } else {
                        long dias = Long.parseLong(txtFecha12.getText().toString()) * 1000 * 60 * 60 * 24;
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        long aumentar = inicioTime + dias;
                        cFechaSeleccionada.setDate(aumentar);
                    }
                }
            }
        });

        LetraPedido letraPedido12 = new LetraPedido();
        letraPedido12.iPedido = pedido.iPedido;
        letraPedido12.NumeroPedido = pedido.NumeroPedido;
        letraPedido12.Numero = 12;
        letraPedido12 = letraPedidoDAO.obtenerLetraPedido(letraPedido12);
        if (letraPedido12 != null) {
            txtFecha12.setText(String.valueOf(letraPedido12.Dia));
        }

        txtFecha13.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                letraPedido.Numero = 13;
                if (s.length() == 0) {
                    letraPedidoDAO.eliminarLetraPedido(letraPedido);
                } else {

                    letraPedido.Dia = Integer.parseInt(s.toString());
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DATE, letraPedido.Dia);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    letraPedido.Fecha = dateFormat.format(cal.getTime());
                    if (letraPedidoDAO.verificarLetraPedido(letraPedido)) {
                        letraPedidoDAO.actualizarDia(letraPedido);
                    } else {
                        letraPedido.Picker = false;
                        letraPedidoDAO.registrarLetraPedido(letraPedido);
                    }
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!b13c) {
                    if (s.length() == 0) {
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        cFechaSeleccionada.setDate(inicioTime);
                    } else {
                        long dias = Long.parseLong(s.toString()) * 1000 * 60 * 60 * 24;
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        long aumentar = inicioTime + dias;
                        cFechaSeleccionada.setDate(aumentar);
                    }
                } else {
                    b13c = false;
                }
            }
        });

        txtFecha13.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (txtFecha13.getText().length() == 0) {
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        cFechaSeleccionada.setDate(inicioTime);
                    } else {
                        long dias = Long.parseLong(txtFecha13.getText().toString()) * 1000 * 60 * 60 * 24;
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        long aumentar = inicioTime + dias;
                        cFechaSeleccionada.setDate(aumentar);
                    }
                }
            }
        });

        LetraPedido letraPedido13 = new LetraPedido();
        letraPedido13.iPedido = pedido.iPedido;
        letraPedido13.NumeroPedido = pedido.NumeroPedido;
        letraPedido13.Numero = 13;
        letraPedido13 = letraPedidoDAO.obtenerLetraPedido(letraPedido13);
        if (letraPedido13 != null) {
            txtFecha13.setText(String.valueOf(letraPedido13.Dia));
        }

        txtFecha14.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                letraPedido.Numero = 14;
                if (s.length() == 0) {
                    letraPedidoDAO.eliminarLetraPedido(letraPedido);
                } else {

                    letraPedido.Dia = Integer.parseInt(s.toString());
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DATE, letraPedido.Dia);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    letraPedido.Fecha = dateFormat.format(cal.getTime());
                    if (letraPedidoDAO.verificarLetraPedido(letraPedido)) {
                        letraPedidoDAO.actualizarDia(letraPedido);
                    } else {
                        letraPedido.Picker = false;
                        letraPedidoDAO.registrarLetraPedido(letraPedido);
                    }
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!b14c) {
                    if (s.length() == 0) {
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        cFechaSeleccionada.setDate(inicioTime);
                    } else {
                        long dias = Long.parseLong(s.toString()) * 1000 * 60 * 60 * 24;
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        long aumentar = inicioTime + dias;
                        cFechaSeleccionada.setDate(aumentar);
                    }
                } else {
                    b14c = false;
                }
            }
        });

        txtFecha14.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (txtFecha14.getText().length() == 0) {
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        cFechaSeleccionada.setDate(inicioTime);
                    } else {
                        long dias = Long.parseLong(txtFecha14.getText().toString()) * 1000 * 60 * 60 * 24;
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        long aumentar = inicioTime + dias;
                        cFechaSeleccionada.setDate(aumentar);
                    }
                }
            }
        });

        LetraPedido letraPedido14 = new LetraPedido();
        letraPedido14.iPedido = pedido.iPedido;
        letraPedido14.NumeroPedido = pedido.NumeroPedido;
        letraPedido14.Numero = 14;
        letraPedido14 = letraPedidoDAO.obtenerLetraPedido(letraPedido14);
        if (letraPedido14 != null) {
            txtFecha14.setText(String.valueOf(letraPedido14.Dia));
        }

        txtFecha15.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                letraPedido.Numero = 15;
                if (s.length() == 0) {
                    letraPedidoDAO.eliminarLetraPedido(letraPedido);
                } else {

                    letraPedido.Dia = Integer.parseInt(s.toString());
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DATE, letraPedido.Dia);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    letraPedido.Fecha = dateFormat.format(cal.getTime());
                    if (letraPedidoDAO.verificarLetraPedido(letraPedido)) {
                        letraPedidoDAO.actualizarDia(letraPedido);
                    } else {
                        letraPedido.Picker = false;
                        letraPedidoDAO.registrarLetraPedido(letraPedido);
                    }
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!b15c) {
                    if (s.length() == 0) {
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        cFechaSeleccionada.setDate(inicioTime);
                    } else {
                        long dias = Long.parseLong(s.toString()) * 1000 * 60 * 60 * 24;
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        long aumentar = inicioTime + dias;
                        cFechaSeleccionada.setDate(aumentar);
                    }
                } else {
                    b15c = false;
                }
            }
        });

        txtFecha15.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (txtFecha15.getText().length() == 0) {
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        cFechaSeleccionada.setDate(inicioTime);
                    } else {
                        long dias = Long.parseLong(txtFecha15.getText().toString()) * 1000 * 60 * 60 * 24;
                        Date hoyDate = today.getTime();
                        long inicioTime = hoyDate.getTime();
                        long aumentar = inicioTime + dias;
                        cFechaSeleccionada.setDate(aumentar);
                    }
                }
            }
        });
        LetraPedido letraPedido15 = new LetraPedido();
        letraPedido15.iPedido = pedido.iPedido;
        letraPedido15.NumeroPedido = pedido.NumeroPedido;
        letraPedido15.Numero = 15;
        letraPedido15 = letraPedidoDAO.obtenerLetraPedido(letraPedido15);
        if (letraPedido15 != null) {
            txtFecha15.setText(String.valueOf(letraPedido15.Dia));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                SeleccionDiaFacturaActivity.super.onBackPressed();
                break;

        }
        return super.onOptionsItemSelected(item);
    }


}
