package util;

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

public class AnalisysAgentFamily {
    private static final String path = ".\\logs\\results\\";
    private static final File newfile = new File(".\\logs\\analyze");
    private static final String filename =".\\logs\\analyze\\analisysAgentFamily.csv";
    
    public static void main(String[] args) throws IOException {
        newfile.mkdir();
        
        CSVWriter csvData = setCSVData(new File(path+"AGC.csv"), new File(filename));
        csvData.close();
    }
    
    private static CSVWriter setCSVData(File in, File out){
        try(FileOutputStream fout = new FileOutputStream(out);
            FileInputStream fin = new FileInputStream(in)){
            CSVReader data = new CSVReader(new InputStreamReader(fin));
            CSVWriter csv = new CSVWriter(new OutputStreamWriter(fout));
            
            String fline[];
            data.readNext();
            fline = data.readNext();
            
            //Field
            List<String> field = new ArrayList<>();
            List<String> datarow = new ArrayList<>();
            
            field.add("TIME");
            datarow.add(fline[0]);
            for(int i=0; i < Integer.valueOf(fline[1]); i++){
                field.add("R#00"+i);
                datarow.add("1");
            }
            csv.writeNext(field.toArray(new String[field.size()]));
            csv.writeNext(datarow.toArray(new String[datarow.size()]));
            
            String before = fline[1];
            while((fline = data.readNext()) != null){
                datarow.set(0, fline[0]);
                
                if(!fline[0].equals(before)){
                    String[] source = fline[2].split(",");
                    for(String s : source){
                        int i = 0;
                        for(String f : field){
                            if(s.contains(f)){
                                datarow.set(i, String.valueOf(Integer.valueOf(datarow.get(i))+1));
                                break;
                            }
                            i++;
                        }
                    }
                }
                csv.writeNext(datarow.toArray(new String[datarow.size()]));
                csv.flush();
                before = fline[1];
            }
            
            return csv;
        } catch (IOException ex) {
            return null;
        }
    }
}
