package utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Slf4j
public class Save {

    public String nameTest;
    public String folderPath;
    public String nameSaveFile;
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

    public void createSaveFile(){
        Date now = new Date();
        SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
        this.nameSaveFile = this.nameTest + "-" + formatDate.format(now);

        new File(this.folderPath + "\\" + this.nameSaveFile);
        this.saveMetricsValues();
    }

    public void createNewSave(){
        this.nameTarget = new ArrayList<>();
        this.accuracyMetrics = new ArrayList<>();
    }

    public void saveMetricsValues(){

        FileWriter save = null;
        try {
            save = new FileWriter(this.folderPath + "\\" + this.nameSaveFile + ".txt", StandardCharsets.UTF_8);
            save.write(this.nameTest + "\r\n");
            save.write("\r\n");
            save.write("Accuracy \r\n");
            for (int i = 0; i<this.accuracyMetrics.size(); i++){
                save.write(this.nameTarget.get(i) + " -> " + this.accuracyMetrics.get(i) + "%");
                save.write("\r\n");
            }
            save.write("\r\n");
            save.write("Precision \r\n");
            for (int i = 0; i<this.precisionMetrics.size(); i++){
                save.write(this.nameTarget.get(i) + " -> " + this.precisionMetrics.get(i) + "%");
                save.write("\r\n");
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                if (save != null){
                    save.close();
                }
            }catch (IOException e2){
                e2.printStackTrace();
            }
        }
    }
}
