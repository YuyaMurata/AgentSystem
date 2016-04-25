/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author kaeru
 */
public class FileInput {
    private static final String path = ".\\logs";
    private static final FileInput fileinput = new FileInput();
    private Map<String, File> map = new HashMap<>();
    
    private FileInput(){
        getFileMap();
    }
    
    public static FileInput getInstance(){
        return fileinput;
    }
    
    private void getFileMap(){
        File file = new File(path);
        File folder = file.listFiles()[0];
        File[] log = folder.listFiles();
        
        System.out.println("> Logs FileList");
        
        for(File key : log){
            System.out.println(key.toString());
            map.put(key.getName(), key);
        }
    }
    
    public File getFile(String filename){
        String csvname = filename+".csv";
        return map.get(csvname);
    }
}
