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

/**
 *
 * @author 悠也
 */
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
                if(line[1] == "us") flg = true;
                if(flg){
                    csv.writeNext(line);
                    csv.flush();
                }
            }
            
            return csv;
        } catch (IOException ex) {
            return null;
        }
    }
}