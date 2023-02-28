import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Converter {

    public String tempConverter(String temp) {
        double tempValue = Double.valueOf(temp);
        double tempValueInC = Math.round(tempValue - 273.15);

        return String.valueOf(tempValueInC);
    }

    public String dateConverter(String dt , String timezone){
        Double dtTime = Double.valueOf(dt);
        Long dTT = dtTime.longValue() * 1000;
        Date currentDate = new Date(dTT);
        Long currentTime = currentDate.getTime() + Long.valueOf(timezone);
        DateFormat format = new SimpleDateFormat("EEE MMM dd kk:mm");
        Date finalUnformatedDate = new Date(currentTime);
        String finalDate = format.format(finalUnformatedDate);

        return finalDate;
    }
}
