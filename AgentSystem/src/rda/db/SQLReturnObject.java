/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.db;

import java.util.Map;

/**
 *
 * @author kaeru
 */
public class SQLReturnObject {
    private Map results;
    public void setResultSet(Map results){
        this.results.putAll(results);
    }
    
    public void print(){
        System.out.println("\n--");
        
        Long total= 0L;
        for(Object id : results.keySet()){
            System.out.println(id+"="+results.get(id));
            total = total+ (Long)results.get(id);
        }
        System.out.println("> Total : "+total);
    }
}
