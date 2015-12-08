/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.result;

import com.ibm.commons.collections.BidiMap;
import com.ibm.commons.collections.bidimap.DualHashBidiMap;
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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import rda.data.SetDataType;
import rda.property.SetProperty;

/**
 *
 * @author kaeru
 */
public class ResultsDataForming implements SetProperty, SetDataType{
    private static BidiMap bmapMQToAgent = new DualHashBidiMap();
    
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
            
            //Sort Data
            HashMap<String, Integer> idToDataMap = new LinkedHashMap<>();
            for(int i=0; i < fieldsList.get(1).length; i++){
                bmapMQToAgent.put(fieldsList.get(1)[i], fieldsList.get(2)[i]);
                idToDataMap.put(fieldsList.get(3)[i], i);
            }
            fieldsList.remove(3);
            
            String[] tranData = new String[fieldsList.get(2).length];
            String[] connData = new String[fieldsList.get(2).length];
            int i=0;
            for(String field : fieldsList.get(2)){
                int index = idToDataMap.get(field);
                tranData[i] = dataList.get(2)[index];
                connData[i] = dataList.get(3)[index];
                i++;
            }
            dataList.remove(2); dataList.remove(2);
            dataList.add(tranData); dataList.add(connData);
            
            for(String[] title  : titleList)   csv.writeNext(title);
            for(String[] result : resultsList) csv.writeNext(result);
            for(String[] field  : fieldsList)  csv.writeNext(field);
            for(String[] data   : dataList)    csv.writeNext(data);
            
            //Prepared csvWriteMQandCPU
            for(String f : fieldsList.get(1))
                if(f.contains("RMQ")) fields.add(f);
        }
    }
    
    //Aggregate Log(MQLength, Events, CPU)
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
            
            //Info_MQLength.csv
            Integer digit = TIME_PERIOD.toString().length();
            Integer numOfAgents = 0;
            String[] line;
            while((line =csvMQLReader.readNext()) != null){
                if(line.length < 1) continue;
                switch(line[1].replace(" ", "")){
                    case "field":
                        numOfAgents = line.length - 3;
                        break;
                    case "data":
                        timeList.add(line[0].substring(0, line[0].length()-digit));
                        line[0] = ""; line[1] = ""; line[2] = "";
                        dataMQLList.add(String.join(",", line).substring(3));
                        break;
                }
                agentList.add(numOfAgents.toString());
            }
            
            //Info_MQEvent.csv
            HashMap<String, HashMap<String, Integer>> eventMap = new HashMap<>();
            while((line =csvMQEReader.readNext()) != null){
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
                    for(String f : fields)
                        if(mqMap.get(f) != null)
                            event = event+","+mqMap.get(f);
                        else event = event+",0";
                } else 
                    for(String f : fields) event = event+",0";
                dataMQEList.add(event.substring(1));
            }
            
            //CPU (vmstat.csv)
            int nextime = 0;
            while((line =csvCPUReader.readNext()) != null){
                if(line.length < 1 || timeList.size() == nextime) continue;
                if(line[0].substring(0, line[0].length()-digit).contains(timeList.get(nextime))) {
                    dataCPUList.add(line[1]+","+line[2]);
                    nextime++;
                }
            }
            
            System.out.println("MQL:"+dataMQLList.size());
            System.out.println("MQE:"+dataMQEList.size());
            System.out.println("CPU:"+dataCPUList.size());
            
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
                
                //Number Of Agent
                csvList.add(agentList.get(i));
                
                //MQLength
                for(String len : dataMQLList.get(i).split(","))
                    if(len.length() > 0) csvList.add(len);
                for(int j = 0; j < fields.size() - dataMQLList.get(i).split(",").length; j++)
                    csvList.add("0");
                
                //MQEvent
                for(String event : dataMQEList.get(i).split(","))
                    if(event.length() > 0) csvList.add(event);
                
                csv.writeNext(csvList.toArray(new String[csvList.size()]));
            }                
        }    
    }
    
    //Create AgentMap (MQEvents)
    public static void csvWriteAgentTree(HashMap<String, File> map, CSVWriter csv) 
                throws UnsupportedEncodingException, FileNotFoundException, IOException{
        try (CSVReader csvMQLReader = new CSVReader(new InputStreamReader(new FileInputStream(map.get(LOG_MQL)), "UTF-8"))) {
            List<String[]> agentTreeList = new ArrayList<>();
            
            Integer digit = TIME_PERIOD.toString().length();
            String line[];
            Boolean flg = false;
            List<String> initList = new ArrayList<>();
            while((line = csvMQLReader.readNext()) != null){
                if(line.length < 1) continue;
                if(!line[1].contains("field")) continue;
                
                String time = line[0].substring(0, line[0].length() - digit);
                List<String> nameList = new ArrayList<>();
                List<String> numList = new ArrayList<>();
                if(flg == false){
                    for(String mq : line)
                        if(mq.contains("RMQ")){
                            initList.add((String) bmapMQToAgent.get(mq));
                            nameList.add((String) bmapMQToAgent.get(mq));
                        }
                    flg = true;
                } else {
                    String mq = (String)bmapMQToAgent.get(line[line.length-1]);
                    for(String ag : initList){
                        if(mq.split("-")[0].contains(ag.split("-")[0])) nameList.add(mq);
                        else nameList.add("");
                    }
                }
                for(String name : nameList){
                    if(name.contains("R#")) numList.add(String.valueOf(fields.indexOf((String)bmapMQToAgent.getKey(name.split("-")[0]))+1));
                    else numList.add("#N/A");
                }
                
                List<String> list = new ArrayList<>();
                list.add(time);
                list.addAll(nameList);
                list.add("");
                list.add(time);
                list.addAll(numList);
                agentTreeList.add(list.toArray(new String[list.size()]));
            }
            
            csv.writeNext(new String[]{"Time", "AgentList"});
            for(String[] agent : agentTreeList)
                csv.writeNext(agent);
        }
    }
}
