package util;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class DetectCloningAgent {
    private static final String path = ".\\logs\\results\\";
    private static final String filename ="DetectClone.csv";
    private static final Integer numhistory = 3;
    
    public static void main(String[] args) throws IOException {
        CSVWriter csvAGTData = setCSVData(new File(path+"AGT.csv"), new File(path+"AGT_"+filename));
        csvAGTData.close();
        
        CSVWriter csvData = setCSVData(new File(path+"MQL.csv"), new File(path+"MQL_"+filename));
        csvData.close();
       
        //File file = new File(filename+".tmp");
        //file.delete();
    }
    
    private static CSVWriter setCSVData(File in, File out){
        try(FileOutputStream fout = new FileOutputStream(out);
            FileInputStream fin = new FileInputStream(in)){
            CSVReader data = new CSVReader(new InputStreamReader(fin));
            CSVWriter csv = new CSVWriter(new OutputStreamWriter(fout));
            
            //Set Field
            csv.writeNext(data.readNext());
            
            int i = numhistory+1;
            String line[], beforeLine[];
            BlockingQueue<String[]> history = new ArrayBlockingQueue<>(numhistory);
            beforeLine = data.readNext();
            while((line = data.readNext()) != null){
                if(!history.offer(line)){
                    history.poll();
                    history.offer(line);
                }
                
                long after = Arrays.stream(line).filter(s -> s.length() > 0).count();
                long before = Arrays.stream(beforeLine).filter(s -> s.length() > 0).count();
                if(after > before){
                    while(true){
                        String[] l = history.poll();
                        if(l == null) break;
                        csv.writeNext(l);
                        csv.flush();
                    }
                    i = 0;
                }else{
                    if(i < numhistory){
                        csv.writeNext(history.poll());
                        csv.flush();
                    }
                    if(i == numhistory){
                        csv.writeNext(new String[]{""});
                        csv.flush();
                    }
                    i++;
                }
                
                beforeLine = line;
            }
            
            return csv;
        } catch (IOException ex) {
            return null;
        }
    }
}
