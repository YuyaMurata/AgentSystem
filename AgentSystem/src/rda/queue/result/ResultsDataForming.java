/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.queue.result;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import rda.data.SetDataType;
import rda.property.SetProperty;

/**
 *
 * @author kaeru
 */
public class ResultsDataForming implements SetProperty, SetDataType{
    
    public static void main(String[] args) 
            throws FileNotFoundException, UnsupportedEncodingException, IOException, ParseException{
        String folderName = "user10";
        if(args.length == 1)
            folderName = args[0];
        
        String path = LOG_DIR+folderName;
        HashMap<String, File> map = getFileList(path);

        System.out.println(path+createCSVFileName());
        
        try (CSVWriter csvSummary = new CSVWriter(new OutputStreamWriter(new FileOutputStream(path+createCSVFileName()+"-Summary.csv")))) {
            csvTitle(map, csvSummary);
            csvTransactionData(map, csvSummary);
            
            csvSummary.flush();
        }
        
        try (CSVWriter csvSystem = new CSVWriter(new OutputStreamWriter(new FileOutputStream(path+createCSVFileName()+"-SystemLog.csv")))) {
            csvMQLength(map, csvSystem);
            
            csvSystem.flush();
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
                + "t" + DATA_TYPE.getName() + ","
                + "L" + QUEUE_LENGTH + ","
                + "ws" + WINDOW_SIZE + ","
                + "w(" + AGENT_WAIT + "," + QUEUE_WAIT +")"
                +"]";
        
        return fileName;
    }
    
    // Marker init, end -> CSV Write
    public static void csvTitle(HashMap<String, File> map, CSVWriter csv) 
                    throws UnsupportedEncodingException, FileNotFoundException, IOException{
        try (CSVReader csvMQLReader = new CSVReader(new InputStreamReader(new FileInputStream(map.get(LOG_MQL)), "UTF-8"))) {
            Boolean titleSetFlg = true;
            
            String[] strArr;
            while((strArr =csvMQLReader.readNext()) != null)
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
        }
    }
    
    //Result Marker data -> CSV Write
    public static void csvTransactionData(HashMap<String, File> map, CSVWriter csv) 
                    throws FileNotFoundException, UnsupportedEncodingException, IOException{
        try (CSVReader csvResultsReader = new CSVReader(new InputStreamReader(new FileInputStream(map.get(LOG_RESULTS)), "UTF-8"))) {
            List<String[]> list = csvResultsReader.readAll();
            
            //Error Check
            if(list.isEmpty()){
                System.out.println("System Results Log does not exist.");
                return ;
            }
            
            for(String[] strArr : list)
                if(strArr.length > 2)
                    if(strArr[1].contains("data") && strArr[2].contains("Time"))
                        csv.writeNext(new String[]{"Transaction Time", strArr[3],"[ms]"});
                        
            csv.writeNext(new String[]{""});
            
            List<String> total = new ArrayList<>(Arrays.asList("Total"));
            
            for(String[] strArr : list){
                if(strArr.length > 3){
                    if(strArr[1].contains("data") && !strArr[2].contains("Time")){
                        String[] strArrSub = new String[strArr.length - 3];
                        System.arraycopy(strArr, 2, strArrSub, 0, strArrSub.length);
                        
                        total.add(strArr[strArr.length-1]);
                        
                        csv.writeNext(strArrSub);
                    }
                }
            }
                        
            csv.writeNext(new String[]{""});
            csv.writeNext(total.toArray(new String[total.size()]));
        }
    }
    
    //MQL & CPU Marker data -> CSV Write
    public static void csvMQLength(HashMap<String, File> map, CSVWriter csv) 
            throws FileNotFoundException, UnsupportedEncodingException, IOException, ParseException{
        try (   CSVReader csvMQLReader = new CSVReader(new InputStreamReader(new FileInputStream(map.get(LOG_MQL)), "UTF-8"));
                CSVReader csvMQEReader = new CSVReader(new InputStreamReader(new FileInputStream(map.get(LOG_MQE)), "UTF-8")); 
                CSVReader csvCPUReader = new CSVReader(new InputStreamReader(new FileInputStream(map.get(LOG_CPU)), "UTF-8"))) {
            
            //Error Check
            /*if(csvMQLReader.readAll().isEmpty()){
                System.out.println("Message Queue Length Log does not exists.");
                return ;
            } else if(csvMQEReader.readAll().isEmpty()){
                System.out.println("Message Queue Event Log does not exists.");
                return ;
            } else if(csvCPUReader.readAll().isEmpty()){
                System.out.println("CPU Availability Log does not exists.");
                return ;
            }*/
            
            FieldInput fieldIn = new FieldInput();
            
            //Set Field Name
            fieldIn.setTimeField("Time");
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

            //Set Data
            fieldIn.eventMapToList();
            int i=0 , j=0;
            String[] mql, eventList, cpuList;
            eventList = csvMQEReader.readNext();
            cpuList = csvCPUReader.readNext();
            while((mql = csvMQLReader.readNext()) != null){
                if(mql.length > 1){
                    if(mql[1].contains("data")){
                        fieldIn.setTime(mql[0]);
                        
                        fieldIn.setLengthData(mql);
                        while(fieldIn.setEventData(eventList)) eventList = csvMQEReader.readNext();
                        while(fieldIn.setCPUData(cpuList)) cpuList = csvCPUReader.readNext();
                        
                        csv.writeNext((String[]) fieldIn.formingData());
                    }
                }          
            }
        }
    }
}
