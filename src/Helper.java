import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Helper {

    private String time;
    private String date;
    private String day;


    public String tempConverter(String temp) {
        double tempValue = Double.parseDouble(temp);

        // temperatures provided by the API are in K, so they have to be converted to Cs
        double tempValueInC = Math.round(tempValue - 273.15);

        return String.format("%d",(int) Math.round(tempValueInC));
    }

    // the time and date need to be accurate to whichever location is searched for
    public void dateConverter(String timezone, String dt) {
        Date currentDate;

        if(dt == null){
            currentDate = new Date();
        } else {
            Double datetime = Double.valueOf(dt);
            currentDate = new Date(datetime.longValue()*1000);
        }

        // the timezoneOffset is the time difference in minutes with GMT +0
        int timezoneOffset = currentDate.getTimezoneOffset();
        Double tz = Double.valueOf(timezone);

        // to get the location's time, we first get the time at GMT +0 by adding the timezone offset in milliseconds
        // and then adding the timezone in milliseconds provided by the API to get the local time at that timezone
        Long currentTime = currentDate.getTime() + (timezoneOffset * 60000) + (tz.longValue() * 1000);

        // the times are then formatted via the DateFormat class
        DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd yyyy");
        DateFormat timeFormat = new SimpleDateFormat("h:mm aa");
        DateFormat dayFormat = new SimpleDateFormat("EEEEEE");
        Date finalUnformattedDate = new Date(currentTime);
        date = dateFormat.format(finalUnformattedDate);
        time = timeFormat.format(finalUnformattedDate);
        day = dayFormat.format(finalUnformattedDate);

    }

    public void dateConverter(String timezone){
        dateConverter(timezone,null);
    }

    public String capitalizeDescription(String description) {
        String[] splitDescription = description.split(" ");
        String finalDescription = "";

        if (splitDescription.length > 1) {

            for (int x = 0; x < splitDescription.length; x++) {
                finalDescription += splitDescription[x].substring(0, 1).toUpperCase() + splitDescription[x].substring(1);

                if (x != splitDescription.length - 1) {
                    finalDescription += " ";
                }
            }

        } else {
            finalDescription += description.substring(0, 1).toUpperCase() + description.substring(1);
        }

        return finalDescription;
    }

    public String weatherIconFetcher(String icon){
        String iconPath = "";

        switch (icon){
            case "01d":
                iconPath= "./assets/sun.svg";
                break;

            case "02d":
                iconPath= "./assets/cloudy-day.svg";
            break;

            case "03d" ,"04d":
                iconPath= "./assets/cloudy.svg";
            break;

            case "09d","10d", "09n", "10n":
                iconPath= "./assets/rainy.svg";
            break;

            case "11d", "11n":
                iconPath= "./assets/lightning.svg";
            break;

            case "13d", "13n":
                iconPath= "./assets/snow.svg";
            break;

            case "50d", "50n":
                iconPath= "./assets/mist.svg";
            break;

            case "01n":
                iconPath= "./assets/moon.svg";
            break;

            case "02n", "03n", "04n":
                iconPath= "./assets/cloudy-night.svg";
            break;

            default:
                System.out.println("Error");
                break;
        }

        return iconPath;

    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getDay() {
        return day;
    }
}
