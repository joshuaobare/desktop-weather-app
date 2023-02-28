import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Converter {

    public String tempConverter(String temp) {
        double tempValue = Double.valueOf(temp);
        double tempValueInC = Math.round(tempValue - 273.15);

        return String.valueOf(tempValueInC);
    }

    public String dateConverter(String timezone){
        System.out.println(timezone);
        Date currentDate = new Date();
        int timezoneOffset = currentDate.getTimezoneOffset();
        Double tz = Double.valueOf(timezone);
        System.out.println(tz);
        Long currentTime = currentDate.getTime() + (timezoneOffset * 60000) + (tz.longValue() * 1000);
        DateFormat format = new SimpleDateFormat("EEE MMM dd kk:mm");
        Date finalUnformatedDate = new Date(currentTime);
        String finalDate = format.format(finalUnformatedDate);

        return finalDate;
    }
}
