/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.queue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import rda.property.SetProperty;

/**
 *
 * @author kaeru
 */
public class MQSpecificStorage implements SetProperty{
    private static final ConcurrentHashMap<String, Integer> mqSSCollection = new ConcurrentHashMap<>();
    private static final Marker mqSSMarker = MarkerFactory.getMarker("MessageQueueSSLength");
    
    public static void setMQSSMap(String mqName, Integer mqLength){
        mqSSCollection.put(mqName, mqLength);
    }
    
    public static void mqLengthLogging(){
        //if(mqSSCollection.size() != NUMBER_OF_QUEUE) return;
        
        ArrayList<String> str = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        
        for(String key : mqSSCollection.keySet()){
            str.add(String.valueOf(mqSSCollection.get(key)));
            sb.append("{} ");
        }
        
        logger.debug(mqSSMarker, sb.toString(), str.toArray());
    }
}