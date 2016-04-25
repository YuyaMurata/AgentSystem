/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author kaeru
 */
public class ResultsAgentSystemInfo {
    private static String filename ="";
    
    public static void main(String[] args) {
        FileInput f = FileInput.getInstance();
        CSVWriter csv = setCSVData(f.getFile("AgentSystem_info_Results"));
        
    }
    
    private static CSVWriter setCSVData(File file){
        try(FileOutputStream fout = new FileOutputStream(filename);
            FileInputStream fin = new FileInputStream(file)){
            CSVReader data = new CSVReader(new InputStreamReader(fin));
            CSVWriter csv = new CSVWriter(new OutputStreamWriter(fout));
            
            Map map = new HashMap();
            
            String line[];
            while((line = data.readNext()) != null){
                
            }
            
            return csv;
        } catch (IOException ex) {
            return null;
        }
    }
    
    private static void title(String[] line, Map map){
        if(!line[1].contains("title")) return;
    }
}
