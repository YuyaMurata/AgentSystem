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
 * @author kaeru
 */
public class ResultsAgentTransition {
    private static final String filename =".\\logs\\results\\AGCT.csv";
    
    public static void main(String[] args) throws IOException {
        FileInput f = FileInput.getInstance();
        
        CSVWriter csvData = setCSVData(f.getFile("AgentSystem_info_MQLength"), new File(filename+".tmp"));
        csvData.close();
        
        CSVWriter csvFields = setCSVClone(new File(filename+".tmp"), f.getFile("AgentSystem_info_MQEvent"),
                                          new File(filename));
        csvFields.close();
        
        File file = new File(filename+".tmp");
        file.delete();
    }
    
    private static CSVWriter setCSVData(File in, File out){
        try(FileOutputStream fout = new FileOutputStream(out);
            FileInputStream fin = new FileInputStream(in)){
            CSVReader data = new CSVReader(new InputStreamReader(fin));
            CSVWriter csv = new CSVWriter(new OutputStreamWriter(fout));
            
            csv.writeNext(new String[]{"F#TIME", "AGENT_NUM", "STATE", "CLONE_ID", "DELETE_ID"});
            
            String fline[];
            while((fline = data.readNext()) != null){
                if(!(fline.length > 3) || !(fline[2].contains("MessageQueue")) || !(fline[1].contains("field"))) continue;
                
                //Time
                String time = fline[0];
                
                csv.writeNext(new String[]{time, "", "", "", ""});
                csv.flush();
            }
            
            return csv;
        } catch (IOException ex) {
            return null;
        }
    }
    
    private static CSVWriter setCSVClone(File in1, File in2, File out){
        try(FileOutputStream fout = new FileOutputStream(out);
            FileInputStream fin1 = new FileInputStream(in1);
            FileInputStream fin2 = new FileInputStream(in2)){
            CSVReader tmpdata = new CSVReader(new InputStreamReader(fin1));
            CSVReader data = new CSVReader(new InputStreamReader(fin2));
            CSVWriter csv = new CSVWriter(new OutputStreamWriter(fout));
            
            //Fields
            csv.writeNext(tmpdata.readNext());
            
            String[] line = data.readNext(), beforeline = tmpdata.readNext(), afterline;
            String numClone = "10";
            while((afterline = tmpdata.readNext()) != null){
                if(afterline.length < 1) continue;
                
                String state = "", cloning = "", delete = "";
                while(true){
                    if(line == null) break;
                    if((beforeline[0].compareTo(line[0]) < 0) && (afterline[0].compareTo(line[0]) >= 0)){
                        if(line[1].contains("state")){ 
                            if(state.length() > 0) state = state + ",";
                            state = state + line[3];
                            
                            if(line[3].contains("cloning")){
                                if(cloning.length() > 0) cloning = cloning +",";
                                cloning = cloning + line[4];
                            }
                            if(line[3].contains("delete")){
                                if(delete.length() > 0) delete = delete +","; 
                                delete = delete + line[4];
                            }
                            
                            numClone = line[line.length-1];
                        }
                        line = data.readNext();
                    }
                    else break;
                }
                
                beforeline[1] = numClone;
                beforeline[2] = state;
                beforeline[3] = cloning;
                beforeline[4] = delete;
                    
                csv.writeNext(beforeline);
                csv.flush();
                
                beforeline = afterline;
                afterline[1] = numClone;
                afterline[2] = state;
                afterline[3] = cloning;
                afterline[4] = delete;
            }
            
            csv.writeNext(beforeline);
            csv.flush();
            
            return csv;
        } catch (IOException ex) {
            return null;
        }
    }
}
