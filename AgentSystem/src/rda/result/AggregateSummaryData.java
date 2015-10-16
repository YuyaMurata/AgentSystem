/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.result;

import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import rda.property.SetProperty;

/**
 *
 * @author kaeru
 */
public class AggregateSummaryData implements SetProperty{
    public static void main(String[] args) throws FileNotFoundException {
        String path = "C:\\Users\\kaeru\\Desktop\\logs";
        HashMap<String, File> map = getFileList(path);
        
        CSVWriter csvSummary = new CSVWriter(new OutputStreamWriter(new FileOutputStream("Aggregate_Summary.csv")));
        setCsvFields(map, csvSummary);
        
        for(String key: map.keySet()){
            System.out.println("key:"+key+"_"+map.get(key));
        }
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
            else key = String.valueOf(list.toString().hashCode());
            
            map.put(key, list);
        }
        
        return map;
    }
    
    public static void setCsvFields(HashMap map, CSVWriter csv){
        
    }
}
