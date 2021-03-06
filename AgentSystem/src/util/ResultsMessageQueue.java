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
import java.util.Set;
import java.util.TreeMap;

public class ResultsMessageQueue {
    private static final String filename =".\\logs\\results\\MQL.csv";
    
    public static void main(String[] args) throws IOException {
        FileInput f = FileInput.getInstance();
        
        CSVWriter csvData = setCSVData(f.getFile("AgentSystem_info_MQLength"), new File(filename+".tmp"));
        csvData.close();
        
        CSVWriter csvFields = setCSVField(new File(filename+".tmp"), new File(filename));
        csvFields.close();
        
        File file = new File(filename+".tmp");
        file.delete();
    }
    
    private static CSVWriter setCSVData(File in, File out){
        
        try(FileOutputStream fout = new FileOutputStream(out);
            FileInputStream fin = new FileInputStream(in)){
            CSVReader data = new CSVReader(new InputStreamReader(fin));
            CSVWriter csv = new CSVWriter(new OutputStreamWriter(fout));
            
            Map<String, String> map = new TreeMap();
            String fline[], dline[];
            while((fline = data.readNext()) != null){
                dline = data.readNext(); 
                if(!((fline.length > 3) || (dline.length > 3)) || !(fline[2].contains("MessageQueue"))) continue;
                
                //Time
                map.put("F#TIME", fline[0]);
                //Data
                for(int i=3; i < fline.length; i++)
                    map.put(fline[i], dline[i]);
                
                csv.writeNext(map.values().toArray(new String[map.size()]));
                csv.flush();
            }
            
            field = map.keySet();
            
            return csv;
        } catch (IOException ex) {
            return null;
        }
    }
    
    private static Set<String> field;
    private static CSVWriter setCSVField(File in, File out){
        try(FileOutputStream fout = new FileOutputStream(out);
            FileInputStream fin = new FileInputStream(in)){
            CSVReader data = new CSVReader(new InputStreamReader(fin));
            CSVWriter csv = new CSVWriter(new OutputStreamWriter(fout));
            
            //Insert Fields
            csv.writeNext(field.toArray(new String[field.size()]));
            
            String[] line;
            while((line = data.readNext()) != null){
                if(line.length < 1) continue;
                csv.writeNext(line);
                csv.flush();
            }
            
            return csv;
        } catch (IOException ex) {
            return null;
        }
    }
}
