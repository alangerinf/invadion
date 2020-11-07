package com.bitlicon.purolator.util;

import android.app.Activity;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by dianewalls on 22/05/2015.
 */
public final class Util {


    public static final String PATTERN = "###,###,##0.00";

    private Util() {


    }

    public static void setIconMenu(int drawable, Activity activity) {
        int upId = Resources.getSystem().getIdentifier("up", "id", "android");
        if (upId > 0) {
            ImageView up = (ImageView) activity.findViewById(upId);
            if (up != null) {
                up.setMinimumWidth(150);
                up.setImageResource(drawable);
                up.setScaleX(1.5f);
                up.setScaleY(1.5f);
            }
        }
    }

    public static int resetDia() {
        Calendar calendar = Calendar.getInstance();
        return getNumberDateOfTheWeek(calendar.getTime());
    }

    public static boolean esSemanaPar() {
        Calendar calendar = Calendar.getInstance();
        int week = calendar.get(Calendar.WEEK_OF_MONTH);
        Log.i("Semana", "Numero:" + week);
        if(week == 6) week = 1;
        return week % 2 == 0;
    }

    public static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static String formatoDinero(double monto) {
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.getDefault());
        otherSymbols.setDecimalSeparator('.');
        otherSymbols.setGroupingSeparator(',');
        DecimalFormat df = new DecimalFormat(PATTERN, otherSymbols);
        return df.format(monto);
    }

    public static String formatoDineroSoles(double monto) {
        return "" + formatoDinero(monto);
    }


    public static String formatoFecha(Date fecha) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        return sdf.format(fecha);
    }

    public static String formatoFechaMes(Date fecha) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");
        return sdf.format(fecha);
    }

    public static String formatoFechaQuery(Date fecha) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(fecha);
    }

    public static String getFechaHoyQuery() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fecha = sdf.format(new Date());
        return fecha;
    }

    public static String getFechaHoyQueryEspanol() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyyy");
        String fecha = sdf.format(new Date());
        return fecha;
    }

    public static String getFechaHoyQueryHora() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM HH:mm");
        String fecha = sdf.format(new Date());
        return fecha;
    }


    public static Date getDateFromString(String fecha) {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = sdf.parse(fecha);
        } catch (Exception e) {
            Log.e("Util", e.getMessage());
            date = null;
        }
        return date;
    }

    public static Date getDate(String fecha) {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        try {
            date = sdf.parse(fecha);
        } catch (Exception e) {
            Log.e("Util", e.getMessage());
            date = null;
        }
        return date;
    }

    public static int getNumberDateOfTheWeek(Date date) {

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = 0;

        int dia = c.get(Calendar.DAY_OF_WEEK);

        Log.d("Util", "CALENDAR DAY OF WEEK: " + dia);
        switch (dia) {
            case Calendar.MONDAY:
                day = 1;
                break;
            case Calendar.TUESDAY:
                day = 2;
                break;
            case Calendar.WEDNESDAY:
                day = 3;
                break;
            case Calendar.THURSDAY:
                day = 4;
                break;
            case Calendar.FRIDAY:
                day = 5;
                break;
            case Calendar.SATURDAY:
                day = 6;
                break;
            case Calendar.SUNDAY:
                day = 7;
                break;
        }
        return day;
    }

}
