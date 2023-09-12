package utils;

public class Settings {

    public int dwellTime = 500;
    public int nbPointsToGet = 10;

    public Settings(){}

    public String checkValue(String newValue){
        if (!newValue.matches("\\d*")) {
            newValue = newValue.replaceAll("[^\\d]", "");
        }
        if (newValue.equals("")) {
            newValue = "0";
        }
        return newValue;
    }
}
