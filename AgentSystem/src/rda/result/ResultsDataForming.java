/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.result;

import com.ibm.ws.xs.jdk5.java.util.Arrays;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        
        try (CSVWriter csvSummary = new CSVWriter(new OutputStreamWriter(new FileOutputStream(path+"Summary-"+createCSVFileName()+".csv")))) {
            csvWriteSummary(map, csvSummary);           
            csvSummary.flush();
        }
        
        try (CSVWriter csvSystem = new CSVWriter(new OutputStreamWriter(new FileOutputStream(path+"System-"+createCSVFileName()+".csv")))) {
            //csvMQLength(map, csvSystem);
            csvWriteMQandCPU(map, csvSystem);
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
                + "ag" + NUMBER_OF_RANK_AGENTS + ","
                + "s" + NUMBER_OF_SERVER + ","
                + "st" + SERVER_THREAD + ","
                + "t" + DATA_TYPE.getName() + ","
                + "L" + QUEUE_LENGTH + ","
                + "ws" + WINDOW_SIZE + ","
                + "w(" + AGENT_WAIT + "," + QUEUE_WAIT +")"
                +"]";
        
        return fileName;
    }
    
    //Results (title, field, data ,result) -> CSV Summary
    public static void csvWriteSummary(HashMap<String, File> map, CSVWriter csv) 
                throws UnsupportedEncodingException, FileNotFoundException, IOException{
        try (CSVReader csvResultsReader = new CSVReader(new InputStreamReader(new FileInputStream(map.get(LOG_RESULTS)), "UTF-8"))) {
            List<String> titleList = new ArrayList<String>(){{add("<Setting Parameter>");}};
            List<String> resultsList = new ArrayList<String>(){{add("<Results>");}};
            List<String> fieldsList = new ArrayList<String>(){{add("<Fields>");}};
            List<String> dataList = new ArrayList<String>(){{add("<Data>");}};
            
            String[] line;
            while((line =csvResultsReader.readNext()) != null){
                if(line.length < 1) continue;
                switch(line[1].replace(" ", "")){
                    case "title":
                        line[0] = "\0"; line[1]=" ";
                        titleList.add(String.join(",", line));
                        break;
                    case "result":
                        line[0] = "\0"; line[1]=" ";
                        resultsList.add(String.join(",", line));
                        break;
                    case "field":
                        line[0] = "\0"; line[1]=" ";
                        fieldsList.add(String.join(",", line));
                        break;
                    case "data":
                        line[0] = "\0"; line[1]=" ";
                        dataList.add(String.join(",", line));
                        break; 
                }
            }
            
            for(String title  : titleList)   csv.writeNext(title.split(","));
            for(String result : resultsList) csv.writeNext(result.split(","));
            for(String field  : fieldsList)  csv.writeNext(field.split(","));
            for(String data   : dataList)    csv.writeNext(data.split(","));
            
            //Prepared csvWriteMQandCPU
            fields = Arrays.asList(fieldsList.get(0).split(","));
            fields.remove(0); fields.remove(0);
        }
    }
    
    private static List<String> fields = new ArrayList<>();
    public static void csvWriteMQandCPU(HashMap<String, File> map, CSVWriter csv)
            throws FileNotFoundException, UnsupportedEncodingException, IOException, ParseException{
        try (   CSVReader csvMQLReader = new CSVReader(new InputStreamReader(new FileInputStream(map.get(LOG_MQL)), "UTF-8"));
                CSVReader csvMQEReader = new CSVReader(new InputStreamReader(new FileInputStream(map.get(LOG_MQE)), "UTF-8")); 
                CSVReader csvCPUReader = new CSVReader(new InputStreamReader(new FileInputStream(map.get(LOG_CPU)), "UTF-8"))) {
            List<String> timeList = new ArrayList<>();
            List<String> dataMQLList = new ArrayList<>();
            List<String> dataMQEList = new ArrayList<>();
            List<String> dataCPUList = new ArrayList<>();
            List<String> agentList = new ArrayList<>();
            
            Integer numOfAgents = 0;
            String[] line;
            while((line =csvMQLReader.readNext()) != null){
                if(line.length < 1) continue;
                switch(line[1].replace(" ", "")){
                    case "field":
                        numOfAgents = line.length - 3;
                        break;
                    case "data":
                        timeList.add(line[0]);
                        line[0] = "\0"; line[1] = "\0";
                        dataMQLList.add(String.join(",", line));
                        break;
                }
                agentList.add(numOfAgents.toString());
            }
            
            int nextime = 0;
            HashMap<String, Integer> eventMap = new HashMap<>();
            while((line =csvMQEReader.readNext()) != null){
                if(line.length < 1) continue;
                if(line[1].contains("data")){
                    if(checkTime(timeList.get(nextime), line[0])) {
                        String list = "";
                        for(String f : fields)
                            if(eventMap.get(f) != null) list = list + eventMap.get(f)+",";
                            else list = list + "0,";
                        dataMQEList.add(list);
                        nextime++;
                        eventMap = new HashMap<>();
                    }
                    if(eventMap.get(line[3]) == null) eventMap.put(line[3], 0);
                    eventMap.put(line[3], eventMap.get(line[3])+1);
                }
            }
            
            nextime = 0;
            while((line =csvCPUReader.readNext()) != null){
                if(line.length < 1 || timeList.get(nextime) == null) continue;
                if(checkTime(timeList.get(nextime), line[0])) {
                    dataCPUList.add(line[1]+","+line[2]);
                    nextime++;
                }
            }
            
            //List -> CSV
            List<String> csvList = new ArrayList<>();
            csvList.add("Time"); 
            csvList.add("CPU:us"); csvList.add("CPU:sy");
            csvList.add("Numeber Of Agents");
            for(String f : fields) csvList.add(f+" Length");
            for(String f : fields) csvList.add(f+" Event");
            csv.writeNext(csvList.toArray(new String[csvList.size()]));
            
            for(int i = 0; i < timeList.size(); i++){
                csvList = new ArrayList<>();
                
                //Time
                csvList.add(timeList.get(i));
                
                //CPU
                for(String cpu : dataCPUList.get(i).split(","))
                    csvList.add(cpu);
                csvList.add(agentList.get(i));
                
                //MQLength
                for(String len : dataMQLList.get(i).split(","))
                    if(len.length() > 0) csvList.add(len);
                
                //MQEvent
                for(String event : dataMQEList.get(i).split(","))
                    if(event.length() > 0) csvList.add(event);
                
                csv.writeNext(csvList.toArray(new String[csvList.size()]));
            }                
        }    
    }
    
    // MQL time >= MQE,CPU time "False" MQL time < MQE,CPU time "True"
    public static Boolean checkTime(String time, String anothertime) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date time1 = sdf.parse(time);
        Date time2 = sdf.parse(anothertime);
        
        return time1.compareTo(time2) < 0;
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
