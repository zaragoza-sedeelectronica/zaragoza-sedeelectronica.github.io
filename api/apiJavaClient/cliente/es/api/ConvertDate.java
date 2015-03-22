package es.api;
import java.util.Date;
import java.util.Locale;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.text.ParseException;

/**
 * Clase encargada de la conversion de Fechas a cadena y viceversa
 *
 * @author Marian
 */
public class ConvertDate {
 
    /** DATE_FORMAT */
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
    public static final SimpleDateFormat DATEEN_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    
    public static final SimpleDateFormat MONTH_FORMAT = new SimpleDateFormat("yyyy-MM");
    
    public static final SimpleDateFormat HOUR_FORMAT = new SimpleDateFormat("HH:mm");
    /** DATE_FORMAT */
    public static final SimpleDateFormat DATE_FORMAT_BARRA = new SimpleDateFormat("dd/MM/yyyy");
    public static final SimpleDateFormat DATE_FORMAT_SEGUIDO = new SimpleDateFormat("yyyyMMdd");
    /** DATETIME_FORMAT */
    public static final SimpleDateFormat DATETIME_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    public static final SimpleDateFormat DATETIME_FORMAT_BARRA = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    /** DATEMONTHYEAR_FORMAT */
    public static final SimpleDateFormat DATEMONTHYEAR_FORMAT = new SimpleDateFormat("MM-yyyy");
    public static final SimpleDateFormat ISO8601_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz");
    public static final SimpleDateFormat REST_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
//    public static final SimpleDateFormat ISO8601_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    
    public static final SimpleDateFormat RFC1123 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
    
    public static final SimpleDateFormat ISO8601MS_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    public static final SimpleDateFormat FORMATLARGO = new SimpleDateFormat("yyyyMMdd-HHmmss");

    public static final SimpleDateFormat FORMATTEXTO = new SimpleDateFormat("MMMM yyyy");
    
    /**
     * Convierte una fecha a una cadena.
     *
     * @param date  Fecha
     * @param dt SimpleDateFormat
     * @return Cadena Fecha formateada
     */
    public static String date2String(Date date, SimpleDateFormat dt) {
    	if (date != null) {
    		return dt.format(date);
    	} else {
    		return "";
    	}
    }
 
    /**
     * Convierte una cadena a una fecha.
     *
     * @param dateString Cadena a convertir
     * @param dt SimpleDateFormat
     * @return  Fecha
     * @throws java.text.ParseException Si la cadena no puede ser formateada
     */
    public static Date string2Date(String dateString, SimpleDateFormat dt) throws ParseException {
    	if (dateString.length() < 16 && dt == DATETIME_FORMAT) {
    		dateString = dateString.substring(0, 9) + " 00:00";
    	} else if(dateString.length() < 29 && dt == ISO8601MS_FORMAT) {
    		dateString = dateString + "T23:59:00.000Z";
    	}
        return dt.parse(dateString);
    }
    
    
    /**
     * Añade dias a una fecha
     * @param dt fecha
     * @param days dias
     * @return  Date
     */
    public static Date addDaysToDate(Date dt, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime (dt);
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return calendar.getTime();
    }
    public static Date getDateHoy() {
        return Calendar.getInstance().getTime();
    }
    public static Date getFechaHoy() throws ParseException{
           final Calendar cal    = Calendar.getInstance();
           cal.set(Calendar.HOUR, 0);
           cal.set(Calendar.HOUR_OF_DAY, 0);
           cal.set(Calendar.MINUTE, 0);
           cal.set(Calendar.SECOND, 0);
           cal.set(Calendar.MILLISECOND, 0);
           return cal.getTime();

       }

   
}


