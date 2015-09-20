/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.result;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author kaeru
 */
public class FieldInput {
    private String time;
    private List<String> lengthField  = new ArrayList<>();
    private List<String> eventField  = new ArrayList<>();
    private List<String> cpuField = new ArrayList<>();
    private LinkedHashMap<String, Integer> eventCount = new LinkedHashMap<>();
    
    public void setTime(String field){
        this.time = field;
    }
    
    public void setLengthField(String[] field){
        lengthField.addAll(Arrays.asList(field));
        lengthField.remove(0);lengthField.remove(0);
        lengthField.set(0, "<Length>:");
    }
    
    public void setEventField(String[] field){
        eventField.addAll(Arrays.asList(field));
        eventField.remove(0);eventField.remove(0);
        eventField.set(0, "<Event>:");
        
        //Init EventCount
        for(String fieldName : eventField)
            eventCount.put(fieldName, 0);
    }
    
    public Boolean checkTime(String field) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date time1 = sdf.parse(this.time);
        Date time2 = sdf.parse(field);
        
        return time1.compareTo(time2) >= 0;
    }
    
    public void setCPUField(String[] field){
        cpuField.addAll(Arrays.asList(field));
        cpuField.set(0, "<CPU>:");
    }
    
    public void setLengthData(String[] field){
        lengthField.clear();
        lengthField.addAll(Arrays.asList(field));
        
        //Data 以外を削除
        lengthField.remove(0);lengthField.remove(0);lengthField.set(0, "");
    }
    
    public void setEventData(String[] field){
        String mqName = field[3];
        Integer value = eventCount.get(mqName) + 1;
        eventCount.put(mqName, value);

        eventField.clear();
        eventField.add("");
        for(String key : eventCount.keySet())
            eventField.add(eventCount.get(key).toString());
    }
    
    public void setCPUData(String[] field){
        cpuField.clear();
        cpuField.addAll(Arrays.asList(field));
        
        //Data 以外を削除
        cpuField.set(0, "");
    }
    
    public String[] formingData(){
        ArrayList<String> data = new ArrayList<>();
        
        data.add(time);
        data.addAll(lengthField);
        data.addAll(eventField);
        data.addAll(cpuField);
        
        //init EventCount
        for(String key : eventCount.keySet()) eventCount.put(key, 0);
        
        return data.toArray(new String[0]);
    }
}
