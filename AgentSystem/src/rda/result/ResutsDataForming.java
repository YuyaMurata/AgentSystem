/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.result;

import java.io.File;
import java.util.HashMap;
import rda.property.SetProperty;

/**
 *
 * @author kaeru
 */
public class ResutsDataForming implements SetProperty{
    public static void main(String[] args){
        String folderName = "user10";
        if(args.length == 1)
            folderName = folderName + args[0];
        
        String path = LOG_DIR+"/"+folderName;
        getFileList(path);
        
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
            else if (list.toString().contains(LOG_CPU)) key = LOG_CPU;
            else if (list.toString().contains(LOG_RESULTS)) key = LOG_RESULTS;
            else if (list.toString().contains(LOG_ALL)) key = LOG_ALL;
            
            map.put(key, list);
        }
        
        return map;
    }
}
