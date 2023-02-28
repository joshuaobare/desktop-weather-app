public class Converter {

    public String tempConverter(String temp) {
        double tempValue = Double.valueOf(temp);
        double tempValueInC = Math.round(tempValue - 273.15);

        return String.valueOf(tempValueInC);
    }

    public String dateConverter(String dt , String timezone){
        return "";
    }
}
