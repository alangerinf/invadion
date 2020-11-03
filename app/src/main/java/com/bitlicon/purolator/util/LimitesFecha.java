package com.bitlicon.purolator.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;

public class LimitesFecha {
    private Calendar calendarInicio;
    private Calendar calendarFin;

    public Calendar getCalendarInicio() {
        return calendarInicio;
    }

    public Calendar getCalendarFin() {
        return calendarFin;
    }

    public LimitesFecha invoke(int opcion,String Fecha) {
        calendarInicio = Calendar.getInstance();
        calendarFin = Calendar.getInstance();
        String myFormat = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        try {
            Date parseddate = sdf.parse(Fecha);
            calendarInicio.setTime(parseddate);
            calendarFin.setTime(parseddate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }



        //TODO Comentar o retirar
        //calendarFin.set(Calendar.MONTH, Calendar.FEBRUARY);

        switch (opcion) {
            case Constantes.RESUMEN_MES:
                calendarInicio.set(Calendar.DAY_OF_MONTH, 1);
                //calendarFin.set(Calendar.DAY_OF_MONTH, calendarFin.getActualMaximum(Calendar.DAY_OF_MONTH));
                break;
            case Constantes.RESUMEN_DIA:
                break;
            case Constantes.RESUMEN_SEMANA:
                calendarInicio.set(Calendar.DAY_OF_WEEK, calendarFin.getFirstDayOfWeek());
                //calendarFin.set(Calendar.DAY_OF_WEEK, calendarFin.getFirstDayOfWeek() + 6);
                break;
        }
        return this;
    }
}