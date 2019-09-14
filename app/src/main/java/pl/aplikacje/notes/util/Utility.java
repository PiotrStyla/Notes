package pl.aplikacje.notes.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {

    public static String getCurrentTimeStamp(){

        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String currentDateTime = dateFormat.format(new Date());

            return currentDateTime;

        }
        catch (Exception e){
            return null;
        }
    }

    public  static String getMonthFromNumber(String monthNumber) {

        switch (monthNumber) {
            case "01": {
                return "Sty";
            }
            case "02": {
                return "Lut";
            }
            case "03": {
                return "Mar";
            }
            case "04": {
                return "Kwi";
            }
            case "05": {
                return "Maj";
            }
            case "06": {
                return "Cze";
            }
            case "07": {
                return "Lip";
            }
            case "08": {
                return "Sie";
            }
            case "09": {
                return "Wrz";
            }
            case "10": {
                return "Paż";
            }
            case "11": {
                return "Lis";
            }
            case "12": {
                return "Gru";
            }
            default:{
                return "error";
            }
        }
    }

}
