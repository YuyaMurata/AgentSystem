/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.result;

import com.ibm.commons.collections.BidiMap;
import com.ibm.commons.collections.bidimap.DualHashBidiMap;
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
import java.util.Map;
import java.util.TreeMap;
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
        
        //Create Summary
        try (CSVWriter csvSummary = new CSVWriter(new OutputStreamWriter(new FileOutputStream(path+"/Summary-"+createCSVFileName()+".csv")))) {
            csvWriteSummary(map, csvSummary);           
            csvSummary.flush();
        }
        
        //Create LogAggregate(Length Event CPU)
        try (CSVWriter csvSystem = new CSVWriter(new OutputStreamWriter(new FileOutputStream(path+"/System-"+createCSVFileName()+".csv")))) {
            csvWriteMQandCPU(map, csvSystem);
            csvSystem.flush();
        }
        
        //Create AgentTree
        try (CSVWriter csvAgentTree = new CSVWriter(new OutputStreamWriter(new FileOutputStream(path+"/AgentTree-"+createCSVFileName()+".csv")))) {
            csvWriteAgentTree(map, csvAgentTree);
            csvAgentTree.flush();
        }
    }
    
    //UserN folder - getfile
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
    
    //Commons Output File Name
    public static final String createCSVFileName() {
        String fileName = LOG_ALL;
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
    private static List<String> rootAGIDList = new ArrayList<>();
    private static List<String> agIDList = new ArrayList<>();
    public static void csvWriteSummary(HashMap<String, File> map, CSVWriter csv) 
                throws UnsupportedEncodingException, FileNotFoundException, IOException{
        try (CSVReader csvResultsReader = new CSVReader(new InputStreamReader(new FileInputStream(map.get(LOG_RESULTS)), "UTF-8"))) {
            List<String[]> titleList = new ArrayList<String[]>(){{add(new String[]{"<Setting Parameter>"});}};
            List<String[]> resultsList = new ArrayList<String[]>(){{add(new String[]{"<Results>"});}};
            List<String[]> fieldsList = new ArrayList<String[]>(){{add(new String[]{"<Fields>"});}};
            List<String[]> dataList = new ArrayList<String[]>(){{add(new String[]{"<Data>"});}};
            
            String[] line;
            while((line =csvResultsReader.readNext()) != null){
                if(line.length < 1) continue;
                switch(line[1].replace(" ", "")){
                    case "title":
                        line[0] = ""; line[1]=" ";
                        titleList.add(line);
                        break;
                    case "result":
                        line[0] = ""; line[1]=" ";
                        resultsList.add(line);
                        break;
                    case "field":
                        line[0] = ""; line[1]=" ";
                        fieldsList.add(line);
                        break;
                    case "data":
                        line[0] = ""; line[1]=" ";
                        dataList.add(line);    
                        break; 
                }
            }
            
            for(String[] title  : titleList)   csv.writeNext(title);
            for(String[] result : resultsList) csv.writeNext(result);
            for(String[] field  : fieldsList)  csv.writeNext(field);
            for(String[] data   : dataList)    csv.writeNext(data);
            
            String[] field = fieldsList.get(1);
            rootAGIDList = Arrays.asList(field).subList(3, field.length);
            field = fieldsList.get(2);
            agIDList = Arrays.asList(field).subList(3, field.length);
        }
    }
    
    //Aggregate Log(MQLength, Events, CPU)
    public static void csvWriteMQandCPU(HashMap<String, File> map, CSVWriter csv)
            throws FileNotFoundException, UnsupportedEncodingException, IOException, ParseException{
    
        //Info Time
        List<String> timeList = dataToTime(new CSVReader(new InputStreamReader(new FileInputStream(map.get(LOG_MQL)), "UTF-8")));
            
        //Info NumberOfAgents
        List<String> agentList = dataToNumAgents(new CSVReader(new InputStreamReader(new FileInputStream(map.get(LOG_MQL)), "UTF-8")));
            
        //Info_MQLength.csv
        List<String> dataMQLList = dataToMQL(new CSVReader(new InputStreamReader(new FileInputStream(map.get(LOG_MQL)), "UTF-8")));
            
        //Info_MQEvent.csv
        List<String> dataMQEList = dataToMQE(new CSVReader(new InputStreamReader(new FileInputStream(map.get(LOG_MQE)), "UTF-8")), timeList);
            
        //CPU (vmstat.csv)
        List<String> dataCPUList = dataToCPU(new CSVReader(new InputStreamReader(new FileInputStream(map.get(LOG_CPU)), "UTF-8")), timeList);

        System.out.println("MQL:"+dataMQLList.size());
        System.out.println("MQE:"+dataMQEList.size());
        System.out.println("CPU:"+dataCPUList.size());
            
        //List -> CSV
        List<String> csvList = new ArrayList<>();
        csvList.add("Time"); 
        csvList.add("CPU:us"); csvList.add("CPU:sy");
        csvList.add("Numeber Of Agents");
        for(String agID : agIDList) csvList.add(agID+"Length");
        csvList.add("");
        for(String agID : agIDList) csvList.add(agID+"Event");
        csv.writeNext(csvList.toArray(new String[csvList.size()]));
            
        for(int i = 0; i < timeList.size(); i++){
            csvList = new ArrayList<>();
                
            //Time
            csvList.add(timeList.get(i));
                
            //CPU
            for(String cpu : dataCPUList.get(i).split(","))
                csvList.add(cpu);
                
            //Number Of Agent
            csvList.add(agentList.get(i));
                
            //MQLength
            for(String len : dataMQLList.get(i).split(","))
                if(len.length() > 0) csvList.add(len);
            for(int j = 0; j < agIDList.size() - dataMQLList.get(i).split(",").length; j++)
                csvList.add("0");
                
            //Colomn Space
            csvList.add("");
                
            //MQEvent
            for(String event : dataMQEList.get(i).split(","))
                if(event.length() > 0) csvList.add(event);
                
            csv.writeNext(csvList.toArray(new String[csvList.size()]));
        }                   
    }
    
    /**
     * Create AggregateLog elements
     * csv_Data - List
     */
    //TimeList
    public static List dataToTime(CSVReader csvMQLReader) throws IOException{
        CSVReader reader = csvMQLReader;
        List<String> timeList = new ArrayList<>();
        
        Integer digit = TIME_PERIOD.toString().length();
        String[] line;
        while((line = reader.readNext()) != null){
            if(line.length < 1) continue;
            switch(line[1].replace(" ", "")){
                case "data":
                    timeList.add(line[0].substring(0, line[0].length()-digit));
                    break;
            }
        }
        
        return timeList;
    }
    
    //Number Of Agents
    public static List dataToNumAgents(CSVReader csvMQLReader) throws IOException{
        CSVReader reader = csvMQLReader;
        List<String> agentList = new ArrayList<>();
        
        String[] line;
        while((line = reader.readNext()) != null){
            if(line.length < 1) continue;
            switch(line[1].replace(" ", "")){
                case "field":
                    agentList.add(String.valueOf(line.length-3));
                    break;
            }
        }
        
        return agentList;
    }
    
    //MessageQueueLength
    public static List dataToMQL(CSVReader csvMQLReader) throws IOException{
        CSVReader reader = csvMQLReader;
        List<String> dataMQLList = new ArrayList<>();
        
        Integer numOfAgents = 0;
        String[] line, field = null, data;
        Map dataToFieldMap = new TreeMap();
        while((line = reader.readNext()) != null){
            if(line.length < 1) continue;
            switch(line[1].replace(" ", "")){
                case "field":
                    field = line;
                    numOfAgents = line.length-3;
                    break;
                case "data":
                    data = line;
                    for(int i=3; i < data.length; i++)
                        dataToFieldMap.put(field[i], data[i]);
                    break;
            }
                
            if(!dataToFieldMap.isEmpty()){
                List<String> mql = new ArrayList<>();
                for(String agID : agIDList)
                    if(dataToFieldMap.get(agID) == null) mql.add("0");
                    else mql.add((String) dataToFieldMap.get(agID));
                dataMQLList.add(String.join(",", mql.toArray(new String[mql.size()])));
                dataToFieldMap.clear();
            }
                        
        }
        
        return dataMQLList;
    }
    
    //MessageQueueEvent
    public static List dataToMQE(CSVReader csvMQEReader, List<String> timeList) throws IOException{
        CSVReader reader = csvMQEReader;
        List<String> dataMQEList = new ArrayList<>();
        
        Integer digit = TIME_PERIOD.toString().length();
        String[] line;
        HashMap<String, HashMap<String, Integer>> eventMap = new HashMap<>();
        while((line = reader.readNext()) != null){
            if(line.length < 1) continue;
            if(line[1].contains("field")) continue;
                
            String subLine = line[0].substring(0, line[0].length()-digit);
            if(eventMap.get(subLine) == null)
                eventMap.put(subLine, new HashMap<String, Integer>());
            if(eventMap.get(subLine) != null){
                HashMap<String, Integer> mqMap = eventMap.get(subLine);
                if(mqMap.get(line[3]) != null)
                    mqMap.put(line[3], mqMap.get(line[3])+1);
                else
                    mqMap.put(line[3], 1);
            }
        }

        for(String time : timeList){
            String event = "";
            if(eventMap.get(time) != null){
                HashMap mqMap = eventMap.get(time);
                for(String agID : agIDList)
                    if(mqMap.get(agID) != null){
                        event = event+","+mqMap.get(agID);
                    }
                    else event = event+",0";
            } else 
                for(String agID : agIDList) event = event+",0";
            dataMQEList.add(event.substring(1));
        }
        
        return dataMQEList;
    }
    
    //CPU Availability
    public static List dataToCPU(CSVReader csvCPUReader, List timeList) throws IOException{
        CSVReader reader = csvCPUReader;
        List<String> dataCPUList = new ArrayList<>();
        
        Integer digit = TIME_PERIOD.toString().length();
        String[] line;
        int nextime = 0;
        while((line = reader.readNext()) != null){
            if(line.length < 1 || timeList.size() == nextime) continue;
            String time = line[0].substring(0, line[0].length()-digit);
            if(time.compareTo((String)timeList.get(nextime)) >= 0){
                dataCPUList.add(line[1]+","+line[2]);
                nextime++;
            }
        }
        while(timeList.size() - dataCPUList.size() > 0)
            dataCPUList.add(dataCPUList.get(dataCPUList.size()-1));
        
        return dataCPUList;
    }
    
    //Create AgentMap (MQEvents)
    private static HashMap<String, String> agentTreeMap = new HashMap<>();
    public static void csvWriteAgentTree(HashMap<String, File> map, CSVWriter csv) 
                throws UnsupportedEncodingException, FileNotFoundException, IOException{
        try (CSVReader csvMQEReader = new CSVReader(new InputStreamReader(new FileInputStream(map.get(LOG_MQE)), "UTF-8"))) {
            List<String[]> agentTreeList = new ArrayList<>();
            
            //Field
            List<String> fields = new ArrayList<>();
            fields.add("Time");
            for(String id : rootAGIDList) fields.add(id);
            agentTreeList.add(fields.toArray(new String[fields.size()]));
            
            //Data
            String[] line;
            while((line =csvMQEReader.readNext()) != null){
                if(line.length < 1) continue;
                if(line[1].contains("field")) continue;
                
                List<String> data = new ArrayList<>();
                agentTreeMap.put(line[4], line[3]);
                
                String regID = searchParent(line[4]);
                data.add(line[0]);
                for(String id : rootAGIDList){
                    if(id.equals(regID)) data.add("1");
                    else data.add("");
                }
                
                agentTreeList.add(data.toArray(new String[data.size()]));
            }
            
            csv.writeNext(new String[]{"Time", "AgentList"});
            for(String[] agent : agentTreeList)
                csv.writeNext(agent);
        }
    }
    
    //Search Root AgentID
    private static String searchParent(String pid){
        if(agentTreeMap.get(pid) == null){
            return pid;
        }else{
            return searchParent(agentTreeMap.get(pid));
        }
    }

    public Boolean checkTime(String cputime, String time) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date time1 = sdf.parse(time);
        Date time2 = sdf.parse(cputime);
        
        return time1.compareTo(time2) < 0;
    }
}
