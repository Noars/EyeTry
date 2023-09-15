package utils;

public class Settings {

    Save save;

    public int dwellTime = 500;
    public int nbPointsToGet = 10;

    public Settings(Save save){
        this.save = save;
        this.updateSettings();
    }

    public void saveSettings(){
        this.save.saveConfig(this.dwellTime, this.nbPointsToGet);
    }

    public void updateSettings(){
        int[] config = this.save.loadConfig();
        this.dwellTime = config[0];
        this.nbPointsToGet = config[1];
    }

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
