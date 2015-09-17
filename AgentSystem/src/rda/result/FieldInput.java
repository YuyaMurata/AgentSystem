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
    }
    
    public Boolean cehckTime(String field) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        System.out.println("time1:"+sdf.parse(this.time));
        System.out.println("time2:"+sdf.parse(field));
        
        return false;
    }
    
    public void setCPUField(String[] field){
        cpuField.addAll(Arrays.asList(field));
        cpuField.set(0, "<CPU>:");
    }
    
    public void setLengthData(String[] field){
        lengthField.addAll(Arrays.asList(field));
    }
    
    public void setEventData(String[] field){
        eventField.addAll(Arrays.asList(field));
    }
    
    public void setCPUData(String[] field){
        cpuField.addAll(Arrays.asList(field));
    }
    
    public String[] formingData(){
        ArrayList<String> data = new ArrayList<>();
        
        data.add(time);
        data.addAll(lengthField);
        data.addAll(eventField);
        data.addAll(cpuField);
                
        return data.toArray(new String[0]);
    }
}
