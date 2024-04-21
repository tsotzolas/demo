package utils;

import org.apache.commons.math3.util.Precision;
import org.primefaces.util.LangUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class TimeUtils {

    /**
     * Επιστρέφει ένα string date σε μορφοποιημένο string.
     * 20191106231520
     * 06/11/2019 15:20
     *
     * @param dateString
     * @return
     */
    public static String formatUnixDateToString(String dateString) {

        Date date = utils.TimeUtils.stringUnixDateToJavaDate(dateString);

        if (!Objects.isNull(date) && !dateString.isEmpty()) {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String strDate = dateFormat.format(date);
            return strDate;
        } else {
            return "";
        }
    }

    /**
     * Μέθοδος που έπιστρέφει την ημερομηνία σε java date απο το unix time
     *
     * @param
     * @return base64 String
     */
    public static Date stringUnixDateToJavaDate(String stringDate) {


        if (!stringDate.isEmpty()) {
            String encodedString = "";
            Date date = null;
            try {
//            long unixSeconds = Long.valueOf(stringDate);
                // convert seconds to milliseconds
//            date = new java.util.Date(unixSeconds * 1000L);
                // the format of your date
                SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
                // give a timezone reference for formatting (see comment at the bottom)
                date = sdf.parse(stringDate);


//            sdf.setTimeZone(java.util.TimeZone.getDefault());
//            String formattedDate = sdf.format(date);
//            System.out.println(formattedDate);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return date;
        } else {
            return null;
        }

    }


    /**
     * Μέθοδος που έπιστρέφει την ημερομηνία σε java date απο το unix time
     *
     * @param
     * @return base64 String
     */
    public static String javaDateTostringUnix(Date date) {


        String stringDate = "";
        try {
//            long unixSeconds = Long.valueOf(stringDate);
            // convert seconds to milliseconds
//            date = new java.util.Date(unixSeconds * 1000L);
            // the format of your date
            SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
            // give a timezone reference for formatting (see comment at the bottom)
//            date = sdf.parse(stringDate);
            stringDate = sdf.format(date);


//            sdf.setTimeZone(java.util.TimeZone.getDefault());
//            String formattedDate = sdf.format(date);
//            System.out.println(formattedDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringDate;
    }

    /**
     * Μέθοδος που έπιστρέφει την ημερομηνία σε SQLDate από java date
     *
     * @param
     * @return base64 String
     */
    public static String javaDateToSQLDate(Date date) {


        String stringDate = "";
        try {

            SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            stringDate = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringDate;
    }

    /**
     * Μέθοδος που έπιστρέφει την ημερομηνία σε SQLDate από java date
     *
     * @param
     * @return base64 String
     */
    public static String javaDateToSQLDateNoTime(Date date) {


        String stringDate = "";
        try {

            SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            stringDate = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringDate;
    }

    //Convert Date to Calendar
    private static Calendar dateToCalendar(Date date) {

        if (!Objects.isNull(date)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        } else {
            return null;
        }

    }

    //Convert Calendar to Date
    private Date calendarToDate(Calendar calendar) {
        return calendar.getTime();
    }

    /**
     * Επιστρέφει τον χρόνο σε λεπτά από τον αρχικό σταθμό ενός North mission μέχρι τον σταθμό
     * που δίδεται σε ένα route
     *
     * @param route, station
     * @return
     */
    public static Integer northTTstation(Integer route, Integer station) {

        Integer misTTstat = 0;
        Object o;
        String strSQL = "SELECT sum(AverageTime) + sum(StopTime) as stime FROM tblroutestations " +
                "where idRoute = " + route + " and StationsOrder <= (select min(StationsOrder) from tblroutestations " +
                "where idStationTo = " + station + " and idRoute = " + route + ") order by StationsOrder";
        o = db.dbTransactions.getObjectsBySqlQry1(strSQL);
        if (!Objects.isNull(o) && o instanceof Double) {
            misTTstat = ((Double) o).intValue();
        }

        return misTTstat;
    }

    /**
     * Επιστρέφει τον χρόνο σε λεπτά από τον αρχικό σταθμό ενός South mission μέχρι τον σταθμό
     * που δίδεται σε ένα route
     *
     * @param route, station
     * @return
     */
    public static Integer southTTstation(Integer route, Integer station) {

        Integer misTTstat = 0;
        Object o;
        String strSQL = "SELECT sum(AverageTime) + sum(StopTime) as stime FROM tblroutestations " +
                "where idRoute = " + route + " and StationsOrder >= (select min(StationsOrder) from tblroutestations " +
                "where idStationFrom = " + station + " and idRoute = " + route + ") order by StationsOrder";
        o = db.dbTransactions.getObjectsBySqlQry1(strSQL);
        if (!Objects.isNull(o) && o instanceof Double) {
            misTTstat = ((Double) o).intValue();
        }

        return misTTstat;
    }


    /**
     * Υπολογίζει την διαφορά σε μέρες μεταξύ 2 ημερομηνιών
     *
     * @param d1
     * @param d2
     * @return
     */
    @Deprecated
    public static Integer daysBetween(Date d1, Date d2) {
        if(Objects.nonNull(d1) && Objects.nonNull(d2)) {
            int daysdiff = 0;
            long diff = d2.getTime() - d1.getTime();
            long diffDays = diff / (24 * 60 * 60 * 1000) + 1;
            daysdiff = (int) diffDays;
            return daysdiff;
        }else{
            return null;
        }
    }

    /**
     * Υπολογίζει την απόλυτη διαφορά σε μέρες μεταξύ 2 ημερομηνιών
     *
     * @param d1
     * @param d2
     * @return
     */
    public static Integer daysBetweenabs(Date d1, Date d2) throws ParseException {
        if(Objects.nonNull(d1) && Objects.nonNull(d2)) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date firstDate = sdf.parse(sdf.format(d1));
            Date secondDate = sdf.parse(sdf.format(d2));

            long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS)+1;
            return (int) diff;
        }else{
            return 0;
        }
    }

    /**
     * Υπολογίζει την διαφορά σε ώρες μεταξύ 2 ημερομηνιών
     *
     * @param d1
     * @param d2
     * @return
     */
    public static Integer hoursBetween(Date d1, Date d2) {
        if(Objects.nonNull(d1) && Objects.nonNull(d2)) {
            int daysdiff = 0;
            long diff = d2.getTime() - d1.getTime();
            long diffDays = diff / (60 * 60 * 1000) + 1;
            daysdiff = (int) diffDays;
            return daysdiff;
        }else{
            return 0;
        }
    }

    /**
     * Υπολογίζει την διαφορά σε μέρες μεταξύ 2 ημερομηνιών σε double με δύο δεκαδικούς αριθμούς.
     * @param d1
     * @param d2
     * @return
     */
    public static Double hoursBetweenDouble(Date d1, Date d2) {
        if(Objects.nonNull(d1) && Objects.nonNull(d2)) {
            long diff = d2.getTime() - d1.getTime();
            float daysBetween = (float) diff / (1000*60*60);
//            DecimalFormat df2 = new DecimalFormat("#,##");
            return Math.abs(Precision.round((double) daysBetween,1));
        }else{
            return 0d;
        }
    }


    /**
     * Μετaτρεπει το Date σε LocalDateTime.
     *
     * @param dateToConvert
     * @return
     */
    public static LocalDateTime localDateTimeFromDate(Date dateToConvert) {
        return Objects.nonNull(dateToConvert) ?
                dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                 .toLocalDateTime()
                : null;


    }





    /**
     * Μετaτρεπει το Date σε LocalDateTime.
     *
     * @param dateToConvert
     * @return
     */
    public static Date dateFromLocalDateTime(LocalDateTime dateToConvert) {
        return java.util.Date.from(dateToConvert
                .atZone(ZoneOffset.systemDefault())
                .toInstant());


    }

    /**
     * Μετaτρεπει το Date σε LocalDate.
     *
     * @param dateToConvert
     * @return
     */
    public static Date dateFromLocalDate(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert
                .atStartOfDay(                       // Let java.time determine the first moment of the day on that date in that zone. Never assume the day starts at 00:00:00.
                        ZoneId.systemDefault()
                ).toInstant());


    }


    /**
     * Μετατρέπει το Date σε LocalDateTime πει
     *
     * @param dateToConvert
     * @return
     */
    public static LocalDateTime convertToLocalDateTime(Date dateToConvert) {
//
//        Instant instant = Instant.ofEpochMilli(dateToConvert.getTime());
//        LocalDateTime ldt = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
        return dateToConvert.toInstant()
                .atZone(ZoneOffset.systemDefault())
                .toLocalDateTime();
    }


    /**
     * Επιστρέφει την UTC απο την απο την ώρα Ελλάδος.
     *
     * @param grDate
     * @return UTC time.
     */
    public static Date grTimeToUTCTime(Date grDate) {

        //Μετρατρέπουμε το unix string se date

        //Μετατρέπουμε το date σε calendar
        Calendar calendar = dateToCalendar(grDate);

        //Βάζουμε το timezoneId του σταθμού που έχουμε το localTime
        TimeZone toTimezone = TimeZone.getTimeZone("UTC");

        //Βάζουμε το timezoneId  της Ελλάδας.
        TimeZone fromTimezone = TimeZone.getTimeZone("Europe/Athens");

        //Βρίσκουμε τα offset του καθενος.
        long fromOffset = fromTimezone.getOffset(calendar.getTimeInMillis());
        long toOffset = toTimezone.getOffset(calendar.getTimeInMillis());

        Date returnDate = new Date(grDate.getTime() + (toOffset - fromOffset));

        return returnDate;
    }

    /**
     * Επιστρέφει την UTC απο την απο την ώρα Ελλάδος.
     *
     * @param grDate
     * @return UTC time.
     */
    public static Date utcTimeToGRTime(Date grDate) {

        //Μετρατρέπουμε το unix string se date

        //Μετατρέπουμε το date σε calendar
        Calendar calendar = dateToCalendar(grDate);

        //Βάζουμε το timezoneId του σταθμού που έχουμε το localTime
        TimeZone toTimezone = TimeZone.getTimeZone("UTC");

        //Βάζουμε το timezoneId  της Ελλάδας.
        TimeZone fromTimezone = TimeZone.getTimeZone("Europe/Athens");

        //Βρίσκουμε τα offset του καθενος.
        long fromOffset = fromTimezone.getOffset(calendar.getTimeInMillis());
        long toOffset = toTimezone.getOffset(calendar.getTimeInMillis());

        Date returnDate = new Date(grDate.getTime() + (fromOffset - toOffset));

        return returnDate;
    }


    /**
     * Converts the given <code>date</code> from the <code>fromTimeZone</code> to the
     * <code>toTimeZone</code>.  Since java.util.Date has does not really store time zome
     * information, this actually converts the date to the date that it would be in the
     * other time zone.
     * @param date
     * @param fromTimeZone
     * @param toTimeZone
     * @return
     */
    public static Date convertTimeZone(Date date, TimeZone fromTimeZone, TimeZone toTimeZone)
    {
        long fromTimeZoneOffset = getTimeZoneUTCAndDSTOffset(date, fromTimeZone);
        long toTimeZoneOffset = getTimeZoneUTCAndDSTOffset(date, toTimeZone);

        return new Date(date.getTime() + (toTimeZoneOffset - fromTimeZoneOffset));
    }

    /**
     * Calculates the offset of the <code>timeZone</code> from UTC, factoring in any
     * additional offset due to the time zone being in daylight savings time as of
     * the given <code>date</code>.
     * @param date
     * @param timeZone
     * @return
     */
    private static long getTimeZoneUTCAndDSTOffset(Date date, TimeZone timeZone)
    {
        long timeZoneDSTOffset = 0;
        if(timeZone.inDaylightTime(date))
        {
            timeZoneDSTOffset = timeZone.getDSTSavings();
        }

        return timeZone.getRawOffset() + timeZoneDSTOffset;
    }


    /**
     * Προσθέτει μέρες σε μία ημερομηνία.
     *
     * @param date
     * @param days
     * @return
     */
    public static Date addSubtractDaysToDate(Date date, Integer days) {
        // convert date to calendar
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        // manipulate date
//        c.add(Calendar.YEAR, 1);
//        c.add(Calendar.MONTH, 1);
        c.add(Calendar.DATE, days); //same with c.add(Calendar.DAY_OF_MONTH, 1);
//        c.add(Calendar.HOUR, 1);
//        c.add(Calendar.MINUTE, 1);
//        c.add(Calendar.SECOND, 1);

        // convert calendar to date
        return c.getTime();
    }


    /**
     * Θέτει την ώρα σε μια ημερομηνία.
     *
     * @param date
     * @param hour
     * @return
     */
    public static Date setTimeToDate(Date date, Integer hour) {
        // convert date to calendar
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, 00);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        // convert calendar to date
        return cal.getTime();
    }


    /**
     * Βρίσκει άμα 2 ημερομηνίες είναι την ίδια μέρα και ας έχουν και διαφορετική ώρα.
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDay(Date date1, Date date2) {
        LocalDate localDate1 = date1.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate localDate2 = date2.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        return localDate1.isEqual(localDate2);
    }

    /**
     * βρίσκει την τελευταία ημερομηνία απο του τρέχω μήνα της ημερομηνία που του δίνουμε
     * ΠΧ άμα του δώσεις την ημερομηνία 15/1/2022 θα σου επιστρέψει 31/1/2022 23:59
     * @param currentDate
     * @return
     */
    public static Date findLastDate(Date currentDate){

        if(Objects.nonNull(currentDate)){
            Calendar c = Calendar.getInstance();
            c.setTime(currentDate);
            c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
            c.set(Calendar.HOUR_OF_DAY, 23);
            c.set(Calendar.MINUTE, 59);
            return c.getTime();
        }else{
            return null;
        }
    }


    /**
     * βρίσκει την πρώτη ημερομηνία απο του τρέχω μήνα της ημερομηνία που του δίνουμε
     * ΠΧ άμα του δώσεις την ημερομηνία 15/1/2022 θα σου επιστρέψει 31/1/2022 23:59
     * @param currentDate
     * @return
     */
    public static Date findFirstDate(Date currentDate){

        if(Objects.nonNull(currentDate)){
            Calendar c = Calendar.getInstance();
            c.setTime(currentDate);
            c.set(Calendar.DAY_OF_MONTH,1);
            c.set(Calendar.HOUR_OF_DAY, 00);
            c.set(Calendar.MINUTE, 00);
            return c.getTime();
        }else{
            return null;
        }
    }

    /**
     * Μέδοδος που κάνει custom filter για τις ημερομηνίες για το όπου η ημερομηνία
     * του value είναι μεγαλύτερη απο την ημερομηνία του φίλτρου.
     * @param value
     * @param filter
     * @param locale
     * @return
     * @throws ParseException
     */
    public static boolean customFilterGreater(Object value, Object filter, Locale locale){
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (LangUtils.isBlank(filterText)) {
            return true;
        }
        Timestamp timestamp = (Timestamp) value;
        LocalDate valueLocalDate =  timestamp.toLocalDateTime().toLocalDate();
        LocalDate filterLocalDate = (LocalDate) filter;

        return valueLocalDate.isAfter(filterLocalDate);
    }

    /**
     * Μέδοδος που κάνει custom filter για τις ημερομηνίες για το όπου η ημερομηνία
     * του value είναι μικρότερη απο την ημερομηνία του φίλτρου.
     * @param value
     * @param filter
     * @param locale
     * @return
     * @throws ParseException
     */
    public static boolean customFilterLower(Object value, Object filter, Locale locale){
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (LangUtils.isBlank(filterText)) {
            return true;
        }
        Timestamp timestamp = (Timestamp) value;
        LocalDate valueLocalDate =  timestamp.toLocalDateTime().toLocalDate();
        LocalDate filterLocalDate = (LocalDate) filter;

        return valueLocalDate.isBefore(filterLocalDate);
    }

    /**
     * Μέδοδος που κάνει custom filter για τις ημερομηνίες με επιλογή μόνο του μήνα.
     * @param value
     * @param filter
     * @param locale
     * @return
     * @throws ParseException
     */
    public static boolean customFilterMonth(Object value, Object filter, Locale locale){
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (LangUtils.isBlank(filterText)) {
            return true;
        }
        Timestamp timestamp = (Timestamp) value;
        LocalDate valueLocalDate =  timestamp.toLocalDateTime().toLocalDate();
        LocalDate filterLocalDate = (LocalDate) filter;

        return valueLocalDate.equals(filterLocalDate);
    }

    /**
     * Μέδοδος που κάνει custom filter για τις ημερομηνίες για το όπου η ημερομηνία
     * του value είναι μικρότερη απο την ημερομηνία του φίλτρου.
     * @param value
     * @param filter
     * @param locale
     * @return
     * @throws ParseException
     */
    public static boolean customFilterRange(Object value, Object filter, Locale locale){
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (LangUtils.isBlank(filterText)) {
            return true;
        }
        Timestamp timestampValue = (Timestamp) value;
        LocalDate valueLocalDate =  timestampValue.toLocalDateTime().toLocalDate();
        LocalDate filterLocalDateFrom = (LocalDate)((ArrayList) filter).get(0);
        LocalDate filterLocalDateTo = (LocalDate)((ArrayList) filter).get(1);

        return valueLocalDate.isBefore(filterLocalDateTo) && valueLocalDate.isAfter(filterLocalDateFrom);
    }

}
