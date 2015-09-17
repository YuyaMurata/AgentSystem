/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.result;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import rda.property.SetProperty;

/**
 *
 * @author kaeru
 */
public class ResutsDataForming implements SetProperty{
    
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException{
        String folderName = "user10";
        if(args.length == 1)
            folderName = folderName + args[0];
        
        String path = LOG_DIR+folderName;
        HashMap<String, File> map = getFileList(path);

        System.out.println(path+createCSVFileName());
        
        CSVWriter csv = new CSVWriter(new OutputStreamWriter(new FileOutputStream(path+createCSVFileName())));
        //csvTitle(map, "init", csv);
    }
    
    public static HashMap<String, File> getFileList(String path){
        File file = new File(path);
        File[] fileList = file.listFiles();
        HashMap<String, File> map = new HashMap<>();
        String key = "";
        
        for(File list : fileList){
            System.out.println(list.toString());
            
            if (list.toString().contains(LOG_MQL)) key = LOG_MQL;
            else if (list.toString().contains(LOG_MQE)) key = LOG_MQE;
            else if (list.toString().contains(LOG_CPU+".csv")) key = LOG_CPU;
            else if (list.toString().contains(LOG_RESULTS)) key = LOG_RESULTS;
            else key = "test";
            
            map.put(key, list);
        }
        
        return map;
    }
    
    public static final String createCSVFileName() {
        String fileName = "/"+LOG_ALL;
        fileName = fileName +"_para["
                + TIME_RUN + "s," 
                + TIME_PERIOD + "ms,"
                + "u" + NUMBER_OF_USER_AGENTS + "," 
                + "s" + NUMBER_OF_SERVER + ","
                + "st" + SERVER_THREAD + ","
                + "t" + DATA_TYPE.name + ","
                + "L" + QUEUE_LENGTH + ","
                + "ws" + WINDOW_SIZE + ","
                + "w(" + AGENT_WAIT + "," + QUEUE_WAIT +")"
                +"].csv";
        
        return fileName;
    }
    
    public static void csvTitle(HashMap<String, File> map, String marker, CSVWriter csv) throws UnsupportedEncodingException, FileNotFoundException{
        CSVReader csvReader = new CSVReader(new InputStreamReader(new FileInputStream(map.get(LOG_MQL)), "UTF-8"));
        
        for(int i=0; i < 10; i++)
            try {
                System.out.println(csvReader.readNext());
            } catch (IOException e) {
                
            }
    }
}
