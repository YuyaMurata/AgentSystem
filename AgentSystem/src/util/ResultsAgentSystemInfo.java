package util;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Map;

public class ResultsAgentSystemInfo {
    private static String filename =".\\logs\\results\\INFO.csv";
    
    public static void main(String[] args) {
        FileInput f = FileInput.getInstance();
        CSVWriter csv = setCSVData(f.getFile("AgentSystem_info_Results"));
        
    }
    
    private static CSVWriter setCSVData(File file){
        try(FileOutputStream fout = new FileOutputStream(filename);
            FileInputStream fin = new FileInputStream(file)){
            CSVReader data = new CSVReader(new InputStreamReader(fin));
            CSVWriter csv = new CSVWriter(new OutputStreamWriter(fout));
            
            String line[];
            while((line = data.readNext()) != null){
                String[] csvLine = null;
                
                if(line[1].contains("title"))
                    csvLine = line[3].split("=");
                if(line[1].contains("data") && line[2].contains("-1sec")){
                    csvLine = line[2].split("=")[1].split("_");
                }
                
                if(csvLine != null){
                    csv.writeNext(csvLine);
                    csv.flush();
                }
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
