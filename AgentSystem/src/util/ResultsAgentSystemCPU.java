package util;

import com.ibm.ws.xs.jdk5.java.util.Arrays;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class ResultsAgentSystemCPU {
    private static String filename =".\\logs\\results\\CPU.csv";
    
    public static void main(String[] args) {
        FileInput f = FileInput.getInstance();
        CSVWriter csv = setCSVData(f.getFile("vmstat"), new File(filename));      
    }
    
    private static CSVWriter setCSVData(File in, File out){
        try(FileOutputStream fout = new FileOutputStream(out);
            FileInputStream fin = new FileInputStream(in)){
            CSVReader data = new CSVReader(new InputStreamReader(fin));
            CSVWriter csv = new CSVWriter(new OutputStreamWriter(fout));
            
            String line[];
            Boolean flg = false;
            while((line = data.readNext()) != null){
                List<String> list = new ArrayList<>();
                list.addAll(Arrays.asList(line));
                if(list.get(1).contains("us")){
                    list.set(0, "");
                    flg = true;
                    list.add("total");
                    csv.writeNext(list.toArray(new String[list.size()]));
                    csv.flush();
                }
                else if(flg){
                    Integer total = Integer.valueOf(list.get(1)) + Integer.valueOf(list.get(2));
                    list.add(total.toString());
                    csv.writeNext(list.toArray(new String[list.size()]));
                    csv.flush();
                }
            }
            
            return csv;
        } catch (IOException ex) {
            return null;
        }
    }
}