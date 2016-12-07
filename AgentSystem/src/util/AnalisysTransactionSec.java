package util;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class AnalisysTransactionSec {
    private static final String path = ".\\logs\\results\\";
    private static final File newfile = new File(".\\logs\\analyze");
    private static final String filename =".\\logs\\analyze\\analisysAgentTransaction.csv";
    
    public static void main(String[] args) throws IOException {
        newfile.mkdir();
        
        CSVWriter csvData = setCSVData(new File(path+"AGT.csv"), new File(filename));
        csvData.close();
    }
    
    private static CSVWriter setCSVData(File in, File out){
        try(FileOutputStream fout = new FileOutputStream(out);
            FileInputStream fin = new FileInputStream(in)){
            CSVReader data = new CSVReader(new InputStreamReader(fin));
            CSVWriter csv = new CSVWriter(new OutputStreamWriter(fout));
            
            String fline[], before[], temp[];
            Integer t = 0;
            fline = data.readNext();
            
            fline[0] = "TIME";
            csv.writeNext(fline);
            
            fline = data.readNext();
            fline[0] = (t++).toString();
            csv.writeNext(fline);
            
            before = fline.clone();
            while((fline = data.readNext()) != null){
                temp = fline.clone();
                fline[0] = (t++).toString();
                
                for(int i=0; i < fline.length; i++){
                    if(fline[i].equals("")) fline[i] = "0";
                    if(before[i].equals("")) before[i] = "0";
                }
                
                for(int i=1; i < fline.length; i++){
                    Integer value = Integer.valueOf(fline[i]) - Integer.valueOf(before[i]);
                    //System.out.println("> f"+fline[i]+" - "+ before[i]+" = "+value);
                    fline[i] = value.toString();
                    
                }
                
                before = temp;
                
                csv.writeNext(fline);
                csv.flush();
            }
            
            return csv;
        } catch (IOException ex) {
            return null;
        }
    }
}
