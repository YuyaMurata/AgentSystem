/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.result;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import rda.property.SetProperty;

/**
 *
 * @author kaeru
 */
public class ResutsDataForming implements SetProperty{
    
    public static void main(String[] args) 
            throws FileNotFoundException, UnsupportedEncodingException, IOException, ParseException{
        String folderName = "user10";
        if(args.length == 1)
            folderName = folderName + args[0];
        
        String path = LOG_DIR+folderName;
        HashMap<String, File> map = getFileList(path);

        System.out.println(path+createCSVFileName());
        
        CSVWriter csvSummary = new CSVWriter(new OutputStreamWriter(new FileOutputStream(path+createCSVFileName()+"-Summary.csv")));
        csvTitle(map, csvSummary);
        
        csvTransactionData(map, csvSummary);
        
        CSVWriter csvSystem = new CSVWriter(new OutputStreamWriter(new FileOutputStream(path+createCSVFileName()+"-SystemLog.csv")));
        csvMQLength(map, csvSystem);
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
            else key = "test";
            
            map.put(key, list);
        }
        
        return map;
    }
    
    public static final String createCSVFileName() {
        String fileName = "/"+LOG_ALL;
        fileName = fileName +"_para["
                + TIME_RUN + "s," 
                + TIME_PERIOD + "ms,"
                + "u" + NUMBER_OF_USER_AGENTS + "," 
                + "s" + NUMBER_OF_SERVER + ","
                + "st" + SERVER_THREAD + ","
                + "t" + DATA_TYPE.name + ","
                + "L" + QUEUE_LENGTH + ","
                + "ws" + WINDOW_SIZE + ","
                + "w(" + AGENT_WAIT + "," + QUEUE_WAIT +")"
                +"]";
        
        return fileName;
    }
    
    // Marker init, end -> CSV Write
    public static void csvTitle(HashMap<String, File> map, CSVWriter csv) 
                    throws UnsupportedEncodingException, FileNotFoundException, IOException{
        CSVReader csvReader = new CSVReader(new InputStreamReader(new FileInputStream(map.get(LOG_MQL)), "UTF-8"));
        Boolean titleSetFlg = true;
                
        List<String[]> list = csvReader.readAll();
        for(String[] strArr : list)
            if(strArr.length > 1)
                if(strArr[1].contains("init")){
                    if(titleSetFlg) {
                        titleSetFlg = false;
                        csv.writeNext(new String[]{"Settings Parameter"});
                    }
                    System.out.println(strArr[0] +" " + strArr[2]);
                    
                    strArr[0] = "";
                    csv.writeNext(new String[]{"", strArr[2]});
                } else if(strArr[1].contains("end")){
                    if(!titleSetFlg) {
                        titleSetFlg = true;
                        csv.writeNext(new String[]{"Results Transaction Time"});
                    }
                    System.out.println(strArr[0] +" " + strArr[2]);
                    
                    strArr[0] = "";
                    csv.writeNext(new String[]{"", strArr[2]});
                }
        
        csv.writeNext(new String[]{""});
        csv.flush();
        
    }
    
    //Result Marker data -> CSV Write
    public static void csvTransactionData(HashMap<String, File> map, CSVWriter csv) 
                    throws FileNotFoundException, UnsupportedEncodingException, IOException{
        CSVReader csvReader = new CSVReader(new InputStreamReader(new FileInputStream(map.get(LOG_RESULTS)), "UTF-8"));
        
        List<String[]> list = csvReader.readAll();
        for(String[] strArr : list)
            if(strArr.length > 2)
                if(strArr[1].contains("data") && strArr[2].contains("Time"))
                    csv.writeNext(new String[]{"Transaction Time", strArr[3],"[ms]"});
        
        csv.writeNext(new String[]{""});
        
        for(String[] strArr : list)
            if(strArr.length > 2){
                if(strArr[1].contains("field")){
                    String[] strArrSub = new String[strArr.length - 3];
                    System.arraycopy(strArr, 2, strArrSub, 0, strArrSub.length);
                    csv.writeNext(strArrSub);
                } else if(strArr[1].contains("data") && !strArr[2].contains("Time")){
                    String[] strArrSub = new String[strArr.length - 3];
                    System.arraycopy(strArr, 2, strArrSub, 0, strArrSub.length);
                    csv.writeNext(strArrSub);
                }
            }
        
        csv.writeNext(new String[]{""});
        
        csv.flush();
    }
    
    //MQL & CPU Marker data -> CSV Write
    public static void csvMQLength(HashMap<String, File> map, CSVWriter csv) 
            throws FileNotFoundException, UnsupportedEncodingException, IOException, ParseException{
        CSVReader csvMQLReader = new CSVReader(new InputStreamReader(new FileInputStream(map.get(LOG_MQL)), "UTF-8"));
        CSVReader csvMQEReader = new CSVReader(new InputStreamReader(new FileInputStream(map.get(LOG_MQE)), "UTF-8"));
        CSVReader csvCPUReader = new CSVReader(new InputStreamReader(new FileInputStream(map.get(LOG_CPU)), "UTF-8"));
        
        FieldInput fieldIn = new FieldInput();
        
        //Set Field Name
        fieldIn.setTime("Time");
        
        String[] field = new String[]{"",""};
        while(!field[1].contains("field"))
            field = csvMQLReader.readNext();
        fieldIn.setLengthField(field);
        
        while(!csvMQEReader.readNext()[1].contains("field")) ;
        fieldIn.setEventField(field);
       
        field = new String[]{"",""};
        while(!field[1].contains("us"))
            field = csvCPUReader.readNext();
        fieldIn.setCPUField(field);
        
        csv.writeNext((String[]) fieldIn.formingData());
        //--
        csv.flush();
        //--
        
        //initialise index and setData
        List<String[]> eventList = csvMQEReader.readAll();
        List<String[]> cpuList = csvCPUReader.readAll();
        fieldIn.eventMapToList();
        int i=0 , j=0;

        for(String[] mql: csvMQLReader.readAll()){
            if(mql.length > 1)
                if(mql[1].contains("data")){
                    fieldIn.setTime(mql[0]);
                    
                    fieldIn.setLengthData(mql);
                    while(fieldIn.setEventData(eventList.get(i))) i++;
                    while(fieldIn.setCPUData(cpuList.get(j))){
                        j++;
                        
                    }
                    System.out.println("CPUList index:"+j);
                    
                    csv.writeNext((String[]) fieldIn.formingData());
                }
        }
        
        csv.flush();
          
    }
}
