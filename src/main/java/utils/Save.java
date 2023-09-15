package utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Slf4j
public class Save {

    public String nameTest;
    public String folderPath;
    public ArrayList<String> nameTarget = new ArrayList<>();
    public ArrayList<Double> accuracyMetrics = new ArrayList<>();
    public ArrayList<Double> precisionMetrics = new ArrayList<>();

    public Save(){
        this.createSaveFolder();
    }

    public void createSaveFolder(){
        String userName = System.getProperty("user.name");
        this.folderPath = "C:\\Users\\" + userName + "\\Documents\\EyeTry";

        File myFolder = new File(this.folderPath);
        boolean createFolder = myFolder.mkdirs();
        if (createFolder){
            log.info("Folder created !");
        }else {
            log.info("Folder already exist or unknown error");
        }
    }

    public void createNewSave(){
        this.nameTarget = new ArrayList<>();
        this.accuracyMetrics = new ArrayList<>();
    }

    public void saveMetricsValues(){
        Date now = new Date();
        SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
        String nameSaveFile = this.nameTest + "-" + formatDate.format(now);

        FileWriter saveMetrics = null;
        try {
            saveMetrics = new FileWriter(this.folderPath + "\\" + nameSaveFile + ".txt", StandardCharsets.UTF_8);
            saveMetrics.write(this.nameTest + "\r\n");
            saveMetrics.write("\r\n");
            saveMetrics.write("Accuracy \r\n");
            for (int i = 0; i<this.accuracyMetrics.size(); i++){
                saveMetrics.write(this.nameTarget.get(i) + " -> " + this.accuracyMetrics.get(i) + "%");
                saveMetrics.write("\r\n");
            }
            saveMetrics.write("\r\n");
            saveMetrics.write("Precision \r\n");
            for (int i = 0; i<this.precisionMetrics.size(); i++){
                saveMetrics.write(this.nameTarget.get(i) + " -> " + this.precisionMetrics.get(i) + "%");
                saveMetrics.write("\r\n");
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                if (saveMetrics != null){
                    saveMetrics.close();
                }
            }catch (IOException e2){
                e2.printStackTrace();
            }
        }
    }

    public void saveConfig(int dwellTime, int nbPointsToGet, int animationTime){
        JSONObject json = new JSONObject();
        try {
            json.put("DwellTime", dwellTime);
            json.put("FixationLength", nbPointsToGet);
            json.put("AnimationTime", animationTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try (PrintWriter out = new PrintWriter(new FileWriter(this.folderPath + "\\configuration.json", StandardCharsets.UTF_8))) {
            out.write(json.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int[] loadConfig(){
        File config = new File(this.folderPath + "\\configuration.json");
        if (config.exists()){
            try {
                FileReader fileReader = new FileReader(config, StandardCharsets.UTF_8);
                Object settings = JsonParser.parseReader(fileReader);
                JsonObject jsonSettings = (JsonObject) settings;

                String dwellTime = String.valueOf(jsonSettings.get("DwellTime"));
                String fixationLength = String.valueOf(jsonSettings.get("FixationLength"));
                String animationTime = String.valueOf(jsonSettings.get("AnimationTime"));

                fileReader.close();

                return new int[]{Integer.parseInt(dwellTime), Integer.parseInt(fixationLength), Integer.parseInt(animationTime)};
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else {
            return new int[]{500, 10, 2000};
        }
    }
}
