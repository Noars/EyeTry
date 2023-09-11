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
    public ArrayList<Double> metrics = new ArrayList<>();

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
        this.metrics = new ArrayList<>();
    }

    public void saveMetricsValues(){

        FileWriter myWritter = null;
        try {
            myWritter = new FileWriter(this.folderPath + "\\" + this.nameSaveFile + ".txt", StandardCharsets.UTF_8);
            myWritter.write(this.nameTest);
            myWritter.write("\r\n");
            for (int i=0; i<this.metrics.size(); i++){
                myWritter.write(this.nameTarget.get(i) + " -> " + this.metrics.get(i) + "%");
                myWritter.write("\r\n");
            }
            myWritter.write("\r\n");
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                if (myWritter != null){
                    myWritter.close();
                }
            }catch (IOException e2){
                e2.printStackTrace();
            }
        }
    }
}
