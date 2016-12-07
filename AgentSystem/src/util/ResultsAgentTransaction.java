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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class ResultsAgentTransaction {
    private static final String filename =".\\logs\\results\\AGT.csv";
    
    public static void main(String[] args) throws IOException {
        FileInput f = FileInput.getInstance();
        
        CSVWriter csvData = setCSVData(f.getFile("AgentSystem_info_MQLength"), new File(filename+".tmp1"));
        csvData.close();
        
        CSVWriter csvFields = insertCSVField(new File(filename+".tmp1"), new File(filename+".tmp2"));
        csvFields.close();
        
        CSVWriter csvTestData = joinCSVTestData(new File(filename+".tmp2"), f.getFile("AgentSystem_info_Results"), new File(filename));
        csvTestData.close();
        
        // Delete TempFiles
        File file = new File(filename+".tmp1");
        file.delete();
        file = new File(filename+".tmp2");
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
                if(!((fline.length > 3) || (dline.length > 3)) || !(fline[2].contains("Transaction"))) continue;
                
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
            ex.printStackTrace();
            return null;
        }
    }
    
    private static Set<String> field;
    private static CSVWriter insertCSVField(File in, File out){
        try(FileOutputStream fout = new FileOutputStream(out);
            FileInputStream fin = new FileInputStream(in)){
            CSVReader data = new CSVReader(new InputStreamReader(fin));
            CSVWriter csv = new CSVWriter(new OutputStreamWriter(fout));
            
            //Insert Fields
            csv.writeNext(field.toArray(new String[field.size()]));
            
            String[] line;
            while((line = data.readNext()) != null){
                if(line.length < 1) continue;
                List<String> list = new ArrayList<>();
                list.addAll(Arrays.asList(line));
                
                // Interpolation rows
                if(list.size() != field.size())
                    for(int j = list.size(); j < field.size(); j++)
                        list.add("");
                
                String total = line[line.length-1];
                list.set(line.length-1, "");
                list.set(list.size()-1, total);
                
                csv.writeNext(list.toArray(new String[list.size()]));
                csv.flush();
            }
            
            return csv;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    private static CSVWriter joinCSVTestData(File in1, File in2, File out){
        try(FileOutputStream fout = new FileOutputStream(out);
            FileInputStream fin1 = new FileInputStream(in1);
            FileInputStream fin2 = new FileInputStream(in2)){
            CSVReader tmpdata = new CSVReader(new InputStreamReader(fin1));
            CSVReader testdata = new CSVReader(new InputStreamReader(fin2));
            CSVWriter csv = new CSVWriter(new OutputStreamWriter(fout));
            
            //TestCase Data Read
            String[] line;
            Long value, grand=0L;
            List<String> testValue =  new ArrayList<>(); testValue.add("Z#TEST");
            List<String> cumlativeValue =  new ArrayList<>(); cumlativeValue.add("Z#GTEST");
            while((line = testdata.readNext()) != null){
                if(!line[1].contains("data")) continue;
                if(line[2].split("=")[0].contains("-1")) continue;
                
                value = Long.parseLong(line[2].split("=")[1]);
                testValue.add(value.toString());
                
                grand = grand + value;
                cumlativeValue.add(grand.toString());
            }
            
            //Join Transaction TestCase Data
            int i=0;
            while((line = tmpdata.readNext()) != null){
                List<String> list = new ArrayList<>();
                list.addAll(Arrays.asList(line));
                
                list.add(testValue.get(i));
                list.add(cumlativeValue.get(i));
                i++;
                
                csv.writeNext(list.toArray(new String[list.size()]));
                csv.flush();
            }
            
            return csv;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
